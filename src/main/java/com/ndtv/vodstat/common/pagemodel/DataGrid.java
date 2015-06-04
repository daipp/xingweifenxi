package com.ndtv.vodstat.common.pagemodel;

import java.util.ArrayList;
import java.util.List;

/**
 * EasyUI DataGrid模型
 * 
 */
public class DataGrid implements java.io.Serializable {


	private Long total = 0L;
	private List rows = new ArrayList();

	public DataGrid() {
		super();
	}
	public DataGrid(Long total, List rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
	public DataGrid(PageResult pr) {
		super();
		if(pr != null){
			this.total = pr.getTotal();
			this.rows = pr.getRows();
		}
	}
	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}
	
	

}
