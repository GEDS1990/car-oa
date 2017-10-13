package com.xiaofan.car.persistence.model;
import lombok.Data;


/**
 * 
 * 
 * 
 **/
@Data
public class CheckInfo{

	/****/
	private Integer id;

	/**设备id**/
	private Integer deviceId;

	/**系统id**/
	private Integer systemId;

	/**系统名称**/
	private String systemName;

	/**检查点**/
	private String checkPoint;

	/**检查/维护内容**/
	private String checkComment;

	/**设定值**/
	private String setValue;

	/**班次：1，早班；2：中班**/
	private Integer shiftsNo;

	/**创建人id**/
	private Integer createUserId;

	/**有效状态：0：无效，1：有效**/
	private Integer deleteStatus;

	/**创建时间**/
	private java.util.Date createTime;

	/**更新时间**/
	private java.util.Date updateTime;



	public void setId(Integer id){
		this.id=id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setDeviceId(Integer deviceId){
		this.deviceId=deviceId;
	}

	public Integer getDeviceId(){
		return this.deviceId;
	}

	public void setSystemId(Integer systemId){
		this.systemId=systemId;
	}

	public Integer getSystemId(){
		return this.systemId;
	}

	public void setSystemName(String systemName){
		this.systemName=systemName;
	}

	public String getSystemName(){
		return this.systemName;
	}

	public void setCheckPoint(String checkPoint){
		this.checkPoint=checkPoint;
	}

	public String getCheckPoint(){
		return this.checkPoint;
	}

	public void setCheckComment(String checkComment){
		this.checkComment=checkComment;
	}

	public String getCheckComment(){
		return this.checkComment;
	}

	public void setSetValue(String setValue){
		this.setValue=setValue;
	}

	public String getSetValue(){
		return this.setValue;
	}

	public void setShiftsNo(Integer shiftsNo){
		this.shiftsNo=shiftsNo;
	}

	public Integer getShiftsNo(){
		return this.shiftsNo;
	}

	public void setCreateUserId(Integer createUserId){
		this.createUserId=createUserId;
	}

	public Integer getCreateUserId(){
		return this.createUserId;
	}

	public void setDeleteStatus(Integer deleteStatus){
		this.deleteStatus=deleteStatus;
	}

	public Integer getDeleteStatus(){
		return this.deleteStatus;
	}

	public void setCreateTime(java.util.Date createTime){
		this.createTime=createTime;
	}

	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime=updateTime;
	}

	public java.util.Date getUpdateTime(){
		return this.updateTime;
	}

}
