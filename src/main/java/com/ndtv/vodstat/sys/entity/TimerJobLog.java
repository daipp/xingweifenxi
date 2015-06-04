package com.ndtv.vodstat.sys.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.ndtv.vodstat.core.entity.BaseEntity;

@Entity
@Table(name = "SYS_TIMERJOB_LOG")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TimerJobLog extends BaseEntity{
	
	private Long jobId;		//任务Id(与系统代码中对应)
	private String jobName;	//任务名称
	private Date crtime;	//执行时间
	private Long milliseconds;	//执行时长(毫秒)
	private String memo;	//备注


	public TimerJobLog(){super();}
	public TimerJobLog(Long jobId, String jobName, Date crtime,
			long milliseconds, String memo) {
		super();
		this.jobId = jobId;
		this.jobName = jobName;
		this.crtime = crtime;
		this.milliseconds = milliseconds;
		this.memo = memo;
	}
	
	@Column(name = "JOB_ID")
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	
	@Column(name = "JOB_NAME")
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	@Column(name = "CRTIME")
	public Date getCrtime() {
		return crtime;
	}
	@Transient
	public String getCrtimeDesc() {
		if(crtime != null){
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(crtime);
		}
		return "";
	}
	public void setCrtime(Date crtime) {
		this.crtime = crtime;
	}

	@Column(name = "MILLISECONDS")
	public Long getMilliseconds() {
		return milliseconds;
	}
	public void setMilliseconds(Long milliseconds) {
		this.milliseconds = milliseconds;
	}

	@Column(name = "MEMO")
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

}
