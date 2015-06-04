package com.ndtv.vodstat.common.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ResponseFileWriter {
	private static final int maxRowsPerPage = 65534;
	private static final int maxRows = 50000;
	
	public static void writeExcel(HttpServletResponse response,String fileName,List<Object[]> dataList) {
		if(dataList == null){
			return;
		}
		if(dataList.size() > maxRows){
			writeText(response,fileName+".csv",dataList,",");
			return;
		}
		try {
			String fname = new String(fileName.getBytes("GB2312"), "ISO_8859_1");  
			response.setContentType("application/octet-stream");
//	        response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("gb2312");
			response.setHeader("content-disposition", "attachment;filename=\"" + fname + ".xls\"");
			
			WritableWorkbook wwb = Workbook.createWorkbook(response.getOutputStream());
			List<WritableSheet> sheetls = new ArrayList();
			
			//正在写入sheet的前面sheet总数
			int sheetsBefore = 0;
			
			Object[] oa;
			for (int i = 0; i < dataList.size(); i++) {
				sheetsBefore = i / maxRowsPerPage;
				if(i % maxRowsPerPage == 0){	//每写满一个sheet则自动新建一个sheet
					sheetls.add(wwb.createSheet("sheet"+(sheetsBefore+1), sheetsBefore));
				}	
				oa = dataList.get(i);
				for (int j = 0; j < oa.length; j++) {
					Label labelC = new Label(j, i-(maxRowsPerPage*sheetsBefore), String.valueOf(oa[j]==null ? "" : oa[j]));
					sheetls.get(sheetsBefore).addCell(labelC);
				}
			}
			wwb.write();
			wwb.close();
			dataList.clear();
		} catch (Exception ex) {
			ex.printStackTrace();
			dataList.clear();
			throw new RuntimeException("写Excel失败", ex);
		}
	}
	
	public static void writeText(HttpServletResponse response,String fileName,List<Object[]> dataList,String gap) {
		try {
			String fname = new String(fileName.getBytes("GB2312"), "ISO_8859_1");
			if(fname.indexOf(".") < 0){
				fname += ".txt";
			}
			response.setContentType("application/octet-stream");
			response.setCharacterEncoding("gb2312");
			response.setHeader("content-disposition", "attachment;filename=\"" + fname + "\"");
			for(Object[] item : dataList){
				for(int i = 0 ; i < item.length; i++){
					response.getWriter().print(item[i] + (i+1 == item.length ? "" : gap));
				}
				response.getWriter().print("\r\n");
			}
			dataList.clear();
		} catch(Exception ex){
			ex.printStackTrace();
			dataList.clear();
			throw new RuntimeException("写文本文件失败", ex);
		}
	}
	
}
