package com.ndtv.vodstat.common.pagemodel;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询结果
 * @author gongyf
 *
 */
public class PageResult implements java.io.Serializable {
	
	/**
	 * 一页数据
	 */
	private List rows = new ArrayList();

	/**
	 * 总行数
	 */
	private Long total = 0L;
	
	public PageResult() {
	}

	public PageResult(List rows, Long total) {
		super();
		this.rows = rows;
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
	public void setTotal(Integer total) {
		this.total = total.longValue();
	}

}
