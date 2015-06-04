import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;


public class LocalTest {
	
	public static void main(String[] sa){
		String a = "10.8.33.123";
		String b = "10.8.\\d{1,3}.\\d{1,3}";
		boolean x = a.matches(b);
		System.out.println(x);
		
		
	}
	
	//@Test
	public void aaa(){
		//org.hibernate.engine.spi.SessionFactoryImplementor;//.getConnectionProvider()
		//List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
		//Collections.sort(names, (a, b) -> b.compareTo(a));
		//names.forEach(n -> System.out.println(n));
		
		
//		Long[] a = new Long[]{1l,2l,3l,4l};
//		List b = Arrays.asList(a);
//		System.out.println(b.size());
		
//		String str = "2014-01-01 00:00:05 10.254.208.66 GET /PlayHuashu.aspx stbid=01560000101110000093138674&film_file=2009082601/16/0/269102839/20131220946472595.mp4&token=8959A960C0DBDFF7DD9FBF2C9FC7DCF4E3F33BF3CEC691D5C2977DC44D8A9A76357EEAF9F83E53E419FE0F94CABB583DA52735A9C759D092B444DDAB182F85B7AF8B31DFDDF8E3A768D2E2082DD33A3B21&duration=&backurl=http://ningbo2-stbepg.wasu.cn:8080/tvportal/ningbo-Tvportal,0,cs_stb_01020414,11,3049545.page?c=1 80 - 10.196.22.9 Mozilla/4.0+(compatible;+MSIE+6.0;+EIS+iPanel+2.0+;+ali) 200 0 0";
//		System.out.println(matchStringEpg2(str));
		
		//String localPath = "D:/vodstat/epglog/20140801/";
		//String fileName = "71_u_ex140801.log";
		
		//readFile(localPath,fileName,fileName+".txt");
		
		List<Long> customerIdsTmp = new ArrayList();
		customerIdsTmp.add(1L);
		customerIdsTmp.add(2L);
		customerIdsTmp.add(3L);
		customerIdsTmp.add(2L);
		
		List<Long> customerIds = new ArrayList(new HashSet(customerIdsTmp));
		for(Long l : customerIdsTmp){
			System.out.println(l);
		}
		System.out.println("--------------");
		for(Long l : customerIds){
			System.out.println(l);
		}
	}
	


	//step1:ftpDownload iislog
	//step2:read iislog and write to a local file
	//step3:load data from local file to DB

	private void readFile(String filePath,String fileRead,String fileWrite) {
		File file = new File(filePath+fileRead);
		BufferedInputStream fis = null;
		try {
			fis = new BufferedInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(
					new InputStreamReader(fis, "utf-8"), 5 * 1024 * 1024);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}// 用5M的缓冲读取文本文件

		String line = "";
		FileWriter fw = null;
		try {
			fw = new FileWriter(filePath+fileWrite);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			int i = 0;
			String str;
			while ((line = reader.readLine()) != null) {
				if (line.contains("stbid")) {
					str = matchStringEpg2(line);
					if(!"".equals(str)){
						fw.append(str + "\r\n");
						i++;
						if(i%10000 == 0){
							System.out.println("已经匹配:"+i+"行");
						}
					}
					//System.out.println(line);
					//fw.append(line + "\r\n");
				}
			}
			System.out.println("合计匹配:"+i+"行");//68218行 //68473行
			reader.close();
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String matchStringEpg2(String str) {
		try {
			StringBuffer sb = new StringBuffer();
			Pattern p = Pattern
					.compile(
							"(\\d{1,4}[-|\\/|年|\\.]\\d{1,2}[-|\\/|月|\\.]\\d{1,2}([日|号])?(\\s)*(\\d{1,2}([点|时])?((:)?\\d{1,2}(分)?((:)?\\d{1,2}(秒)?)?)?)?(\\s)*(PM|AM)?)",
							Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
			Matcher dateM = p.matcher(str);
			if (dateM.find() && dateM.groupCount() >= 1) {
				sb.append(dateM.group(1) + ",");
			}
			Matcher stbidM = Pattern.compile("stbid=?(.*?)(\\s+)").matcher(str);
			//Matcher stbidM = Pattern.compile("/tvportal/index.aspx.*stbid=?(\\S+)(\\s+)").matcher(str);
			if (stbidM.find() && stbidM.groupCount() >= 1) {
//				sb.append(stbidM.group(1));
//				if(sb.toString().split("[,]").length!=2){
//					System.out.println(str);
//				}
				Matcher stbidM0 = Pattern.compile("/tvportal/index.aspx.*stbid=?(\\S+)(\\s+)").matcher(str);
				if (!stbidM0.find() || stbidM.groupCount() < 1) {
					return str;
				} else {
					return "";
				}
			} else {
				return "";
			}
			//return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	private static String matchStringEpg1(String str) {
		try {
			StringBuffer sb = new StringBuffer();
			// List matches = null;
			Pattern p = Pattern
					.compile(
							"(\\d{1,4}[-|\\/|年|\\.]\\d{1,2}[-|\\/|月|\\.]\\d{1,2}([日|号])?(\\s)*(\\d{1,2}([点|时])?((:)?\\d{1,2}(分)?((:)?\\d{1,2}(秒)?)?)?)?(\\s)*(PM|AM)?)",
							Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
			Matcher dateM = p.matcher(str);
			if (dateM.find() && dateM.groupCount() >= 1) {
				sb.append(dateM.group(1) + ",");
			}
			Matcher shortSimM = Pattern.compile("index[.]jsp.*[Uu]ser=(\\d{8})(\\D)").matcher(str);
			if (shortSimM.find() && shortSimM.groupCount() >= 1) {
				sb.append(shortSimM.group(1));
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
//	@Test
	public void test2() {
		String a = "20131507 23:59:42 ,01560000101110000010890647";
		//boolean b = a.matches("\\d{4}-\\d{2}-\\d{2}.*");
		if(a.matches("\\d{4}-\\d{2}-\\d{2}.*")){
			String tmpMonths = a.substring(0, 7).replace("-", "");
			System.out.println(tmpMonths);
			
		}
		if(a.matches("\\d{8}.*")){
			String tmpMonths = a.substring(0, 6);
			System.out.println("="+tmpMonths);
		}
	}

	//@Test
	public void test1() {
//		String a = "2014-07-23 01:12:59 10.254.208.66 GET /tvportal/index.jsp aasdfas=dfasdfsdfasdf23434&user=10136785&stbid=574160090513040019689B580B&NTID=0019689b580b&ip=10.246.15.76&DHCPOption=NBDTV00015&Sversion=V160.5.1.76&Hversion=V160&Bversion=41431.0.0.0.10088 80 - 10.246.15.76 Mozilla/4.0+(compatible;+MSIE+6.0;+EIS+iPanel+3.0;+unknown;+STBID+574160090513040019689B580B;+HWVer+V160;+SWVer+V160.5.1.76;+UIVer+1.1;+User_extension+;+User_NO+) 302 0 0";
//		System.out.println(matchStringEpg1(a));
		String b = "2013-12-06 09:28:59 10.254.208.78 GET /tvportal/index.aspx stbid=01560000101110000010917367 80 - 10.253.20.153 Wasu/1.0(mwver+20310.0.0.0.10086;hwver+6371666f;swver+60574003;uiver+1.35;caver+0x11104450) 200 0 0 0";
		System.out.println(matchStringEpg2(b));
		
//		String text = "abcdebcadxbc"; 
//        Pattern pattern = Pattern.compile(".bc"); 
//        Matcher matcher = pattern.matcher(text); 
//        matcher.find();
//        System.out.println("1="+matcher.group());
//        System.out.println("2="+matcher.group(1));
		
		
//		Pattern p3 = Pattern.compile("(19|20)\\d\\d([- /.])(0[1-9]|1[012])\\2(0[1-9]|[12][0-9]|3[01])"); 
//        Matcher m3 = p3.matcher("1900-01-01 2007/08/13 1900.01.01 1900 01 01 1900-01.01 1900 13 01 1900 02 31"); 
//        while (m3.find()) { 
//                System.out.println(m3.group()); 
//        } 
		
	}
}
