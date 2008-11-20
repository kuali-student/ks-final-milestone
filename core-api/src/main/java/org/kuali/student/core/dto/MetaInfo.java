package org.kuali.student.core.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class MetaInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String versionInd; 
	@XmlElement
	private Date createTime; 
	@XmlElement
	private String createId; 
	@XmlElement
	private Date updateTime; 
	@XmlElement
	private String updateId; 
	public String getVersionInd(){
		return versionInd;
	}
	public void setVersionInd(String versionInd){
		this.versionInd = versionInd;
	}
	public Date getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	public String getCreateId(){
		return createId;
	}
	public void setCreateId(String createId){
		this.createId = createId;
	}
	public Date getUpdateTime(){
		return updateTime;
	}
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	public String getUpdateId(){
		return updateId;
	}
	public void setUpdateId(String updateId){
		this.updateId = updateId;
	}
}