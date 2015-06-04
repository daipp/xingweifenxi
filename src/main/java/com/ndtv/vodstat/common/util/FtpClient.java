package com.ndtv.vodstat.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPHTTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FtpClient {
	/**
	 * 目标文件比较规则, 目标文件比源文件小
	 */
	public static final int TARGET_FILE_SMALLER = -1;
	/**
	 * 目标文件比较规则, 目标文件与源文件相同
	 */	
	public static final int TARGET_FILE_SAME = 0;
	/**
	 * 目标文件比较规则, 目标文件比源文件大
	 */
	public static final int TARGET_FILE_LARGER = 1;
	
	/**
	 * 文件存在规则, 抛出异常
	 */
	public static final int FILE_EXISTS_RULE_THROW = 0;
	/**
	 * 文件存在规则, 覆盖
	 */
	public static final int FILE_EXISTS_RULE_OVERWRITE = 1;
	/**
	 * 文件存在规则, 跳过
	 */
	public static final int FILE_EXISTS_RULE_SKIP = 2;
	

	private String ip;
	private int port;
	private String user;
	private String pass;
	
	public String getIp() {
		return ip;
	}
	public int getPort() {
		return port;
	}
	public String getUser() {
		return user;
	}
	public String getPass() {
		return pass;
	}
	
	private FTPClient ftp;
	private boolean passiveMode = true;//被动模式
	private Map<Integer, Integer> fileExistsRule;//文件存在规则
	private String storeFile;//待保存的文件, 包括完整路径和文件名
	
	//目标文件比较规则是否为指定的文件存在规则
	private boolean isRule(int TARGET_FILE_COMPARE, int FILE_EXISTS_RULE) {
		Object value = fileExistsRule.get(TARGET_FILE_COMPARE);
		if(value == null)
			throw new FTPException("不存在的目标文件比较规则[" + TARGET_FILE_COMPARE + "].");
		
		if(Integer.parseInt(value.toString()) == FILE_EXISTS_RULE)
			return true;
		else
			return false;
	}
	
	/**
	 * 是否抛出异常
	 * @param TARGET_FILE_COMPARE
	 * @return
	 */
	public boolean isThrow(int TARGET_FILE_COMPARE) {
		return this.isRule(TARGET_FILE_COMPARE, FILE_EXISTS_RULE_THROW);
	}
	
	/**
	 * 是否覆盖
	 * @param TARGET_FILE_COMPARE
	 * @return
	 */
	public boolean isOverwrite(int TARGET_FILE_COMPARE) {
		return this.isRule(TARGET_FILE_COMPARE, FILE_EXISTS_RULE_OVERWRITE);
	}
	
	/**
	 * 是否跳过
	 * @param TARGET_FILE_COMPARE
	 * @return
	 */
	public boolean isSkip(int TARGET_FILE_COMPARE) {
		return this.isRule(TARGET_FILE_COMPARE, FILE_EXISTS_RULE_SKIP);
	}

	/**
	 * 文件存在规则
	 * 目标文件比源文件大
	 * @param fielExistsRuleLarger
	 */
	public void setFielExistsRuleLarger(int fielExistsRuleLarger) {
		this.fileExistsRule.put(-1, fielExistsRuleLarger);
	}

	/**
	 * 文件存在规则
	 * 目标文件比源文件相同
	 * @param fielExistsRuleSame
	 */
	public void setFielExistsRuleSame(int fielExistsRuleSame) {
		this.fileExistsRule.put(-1, fielExistsRuleSame);
	}

	/**
	 * 文件存在规则
	 * 目标文件比源文件小
	 * @param fielExistsRuleSmaller
	 */
	public void setFielExistsRuleSmaller(int fielExistsRuleSmaller) {
		this.fileExistsRule.put(-1, fielExistsRuleSmaller);
	}

	/**
	 * 是否是被动模式
	 * @return
	 */
	public boolean isPassiveMode() {
		return passiveMode;
	}

	/**
	 * 设置是否使用被动模式
	 * @param passiveMode
	 */
	public void setPassiveMode(boolean passiveMode) {
		this.passiveMode = passiveMode;
		if(ftp.isConnected()){
			if(passiveMode)
				ftp.enterLocalPassiveMode();
			else
				ftp.enterLocalActiveMode();
		}
	}
	
	//根据目标文件和源文件大小和文件存在规则判断是否覆盖
	private boolean overwrites(long targetFileSize, long srcFileSize) {
		//获取相应文件存在规则
		int fileExistsRuleKey = Long.valueOf(targetFileSize)
				.compareTo(Long.valueOf(srcFileSize));
		
		int fileExistsRuleValue = fileExistsRule.get(fileExistsRuleKey);
		//抛出异常
		if(fileExistsRuleValue == FILE_EXISTS_RULE_THROW)
			throw new FTPException("文件传输失败, 文件[" + this.storeFile + "]已存在.");
		
		//覆盖
		if(fileExistsRuleValue == FILE_EXISTS_RULE_OVERWRITE)
			return true;
		
		//跳过
		if(fileExistsRuleValue == FILE_EXISTS_RULE_SKIP)
			return false;
		
		throw new FTPException("文件传输失败, 未定义的文件存在规则[" + fileExistsRuleValue + "].");
	}
	
	public static FtpClient getFTPHTTPClient(String proxyHost, int proxyPort, String proxyUser, String proxyPass) {
		FtpClient ft = new FtpClient();
		ft.setFtpClient(new FTPHTTPClient(proxyHost, proxyPort, proxyUser, proxyPass));
		//System.out.println("Using HTTP proxy server: " + proxyHost);
        return ft;
	}
	
	public void connect(String ip, int port, String user, String pass){
		if (ftp == null || ftp.isConnected()) {
			return;
		}
		try {
			//连接ftp服务器
			ftp.setControlEncoding("gbk");
			ftp.connect(ip, port);
			
			//检查连接响应
			int reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				this.close();
				throw new FTPException("连接ftp://" + ip + ":" + port + "失败.");
			}
			
			//登录ftp服务器
			if (!ftp.login(user, pass)) {
				this.close();
				throw new FTPException("登录ftp://" + ip + ":" + port + "失败, 用户名或密码错误.");
			}
			
			//传输文件类型改为二进制
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			
			//被动模式
			if(passiveMode)
				ftp.enterLocalPassiveMode();
			else
				ftp.enterLocalActiveMode();
			
		} catch (SocketException e) {
			e.printStackTrace();
			this.close();
			throw new FTPException("ftp://" + ip + ":" + port + "通信异常.", e);
		} catch (IOException e) {
			e.printStackTrace();
			this.close();
			throw new FTPException("ftp://" + ip + ":" + port + "IO操作异常.", e);
		}
	}

	public FtpClient(){
		//文件存在规则初始化, 默认抛出异常
		fileExistsRule = new HashMap<Integer, Integer>();
		fileExistsRule.put(-1, FILE_EXISTS_RULE_THROW);
		fileExistsRule.put(0, FILE_EXISTS_RULE_THROW);
		fileExistsRule.put(1, FILE_EXISTS_RULE_THROW);
	}
	
	

	public FtpClient(String ip, int port, String user, String pass) {
		ftp = new FTPClient();
		try {
			//连接ftp服务器
			ftp.setControlEncoding("GBK");
			ftp.connect(ip, port);
			
			//检查连接响应
			int reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				this.close();
				throw new FTPException("连接ftp://" + ip + ":" + port + "失败.");
			}
			
			//登录ftp服务器
			if (!ftp.login(user, pass)) {
				this.close();
				throw new FTPException("登录ftp://" + ip + ":" + port + "失败, 用户名或密码错误.");
			}
			
			//传输文件类型改为二进制
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			
			//被动模式
			if(passiveMode)
				ftp.enterLocalPassiveMode();
			else
				ftp.enterLocalActiveMode();
			
			//文件存在规则初始化, 默认抛出异常
			fileExistsRule = new HashMap<Integer, Integer>();
			fileExistsRule.put(-1, FILE_EXISTS_RULE_THROW);
			fileExistsRule.put(0, FILE_EXISTS_RULE_THROW);
			fileExistsRule.put(1, FILE_EXISTS_RULE_THROW);
		} catch (SocketException e) {
			this.close();
			throw new FTPException("ftp://" + ip + ":" + port + "通信异常.", e);
		} catch (IOException e) {
			this.close();
			throw new FTPException("ftp://" + ip + ":" + port + "IO操作异常.", e);
		}
		
		this.ip = ip;
		this.user = user;
		this.pass = pass;
		this.port = port;
	}
	
	/**
	 * 上传输入流
	 * @param inStream
	 * @param remoteFile 远程文件的路径和名称
	 */
	public void upload(InputStream inStream, String remoteFile) {
		this.storeFile = remoteFile;
		String[] remoteDirs = this.storeFile.split("/");
		if (remoteDirs.length > 1) {
			for (int i = 0; i < remoteDirs.length - 1; i++) {
				if (remoteDirs[i] != null && !remoteDirs[i].equals(""))
					try {
						//如果不存在该目录则新建
						if (!ftp.changeWorkingDirectory(remoteDirs[i])) {
							ftp.makeDirectory(remoteDirs[i]);
							ftp.changeWorkingDirectory(remoteDirs[i]);
						}
					} catch (IOException e) {
						this.close();
						throw new FTPException("改变当前目录出错, IO操作失败.", e);
					}
			}
		}
		try {
			String remoteFileName = remoteDirs[remoteDirs.length - 1];
			//获取当前目录所有文件
			FTPFile[] ftpFiles = ftp.listFiles();
			
			//遍历所有文件
			for(FTPFile item : ftpFiles) {
				//如果文件存在
				if(item != null && remoteFileName.equals(item.getName())) {
					//如果覆盖
					if(this.overwrites(item.getSize(), inStream.available()))	
						break;
					else
						return;
				}
			}
			
			//上传文件
			if(!ftp.storeFile(remoteFileName, inStream)) {
				throw new FTPException("上传文件[" + this.storeFile + "]失败.");
			}
		} catch (FTPConnectionClosedException e) {
			this.close();
			throw new FTPException("上传文件[" + this.storeFile + "]失败, ftp连接已关闭", e);
		} catch (IOException e) {
			this.close();
			throw new FTPException("上传文件[" + this.storeFile + "]失败, ftp IO操作失败.", e);
		}
	}
	
	public void changeDirectory(String folder){
		try{
			ftp.changeWorkingDirectory(folder);
		}catch(IOException e){
			e.printStackTrace();
			throw new FTPException("改变当前目录" + folder +"出错, IO操作失败.");
		}
	}
	
	public FTPFile getFTPFile(String fileName){
		FTPFile[] fls = null;
		try{
			fls = ftp.listFiles(fileName);
		}catch(IOException e){
			e.printStackTrace();
			throw new FTPException("读取文件" + fileName + "出错, IO操作失败.");
		}
		if(fls == null || fls.length == 0){
			return null;
		}
		if(fls != null && fls.length > 1){
			throw new FTPException("目标文件" + fileName + "个数大于1.");
		}
		return fls[0];
	}
	
	public String[] listFTPFileNames(){
		try {
			return ftp.listNames();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FTPException("列出当前目录的文件名出错, IO操作失败.");
		}
	}

	public FTPFile[] listFTPFiles(){
		try {
			return ftp.listFiles();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FTPException("列出当前目录的文件出错, IO操作失败.");
		}
	}
	
	/**
	 *  You must close the InputStream when you finish reading from it.
	 *  after that You should call: completePendingCommand()
	 * @param folder
	 * @param fileName
	 * @return
	 */
	public InputStream retrieveFile(String fileName) {
		InputStream is = null;
		try {
			is = ftp.retrieveFileStream(fileName);
			if (!ftp.completePendingCommand()) {
			throw new FTPException("读取文件" + fileName + "失败.");
			} 
		} catch (IOException e) {
			e.printStackTrace();
			throw new FTPException("读取文件" + fileName + "出错, IO操作失败.");
		}
		return is;
	}
	
	public FTPClient getFTPClient(){
		return ftp;
	}
	public void setFtpClient(FTPClient ftp) {
		this.ftp = ftp;
	}
	
	/**
	 * 关闭ftp连接
	 *
	 */
	public void close() {
		if (ftp != null && ftp.isConnected()) {
			try {
				ftp.logout();
				ftp.disconnect();
				ftp = null;
			} catch (IOException f) {}
		}
	}
}
