package com.ndtv.vodstat.common.pagemodel;

public class KeyValue implements java.io.Serializable {
	
	Object key;
	Object value;

	public KeyValue(Object key, Object value) {
		super();
		this.key = key;
		this.value = value;
	}
	public Object getKey() {
		return key;
	}
	public void setKey(Object key) {
		this.key = key;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}

}
