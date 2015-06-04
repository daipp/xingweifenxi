package com.ndtv.vodstat.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * MappedSuperclass：设置子类继承父类的注解
 *
 */
@MappedSuperclass
public class BaseEntity implements Serializable,Comparable<BaseEntity> {

	protected static final long serialVersionUID = 1L;
	
	protected Long id;
	
	@Id
	@GeneratedValue //(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int compareTo(BaseEntity o) {
		if(o != null){
			return this.id.compareTo(((BaseEntity)o).getId());
		}
		return 0;
	}	

}
