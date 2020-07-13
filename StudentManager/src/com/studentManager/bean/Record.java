package com.studentManager.bean;

import java.util.Date;

public class Record {
	private int id;
	private Integer studentId;
	private Date date;
	private String remark;
	private Integer disabled;

	private String beginDate;
	private String endDate;
	private User user;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getDisabled() {
		return disabled;
	}
	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Record() {
		super();
	}
	public Record(int id, Integer studentId, Date date, String remark, Integer disabled, String beginDate,
			String endDate, User user) {
		super();
		this.id = id;
		this.studentId = studentId;
		this.date = date;
		this.remark = remark;
		this.disabled = disabled;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.user = user;
	}
	@Override
	public String toString() {
		return "Record [id=" + id + ", studentId=" + studentId + ", date=" + date + ", remark=" + remark + ", disabled="
				+ disabled + ", beginDate=" + beginDate + ", endDate=" + endDate + ", user=" + user + ", getId()="
				+ getId() + ", getStudentId()=" + getStudentId() + ", getDate()=" + getDate() + ", getRemark()="
				+ getRemark() + ", getDisabled()=" + getDisabled() + ", getBeginDate()=" + getBeginDate()
				+ ", getEndDate()=" + getEndDate() + ", getUser()=" + getUser() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Record other = (Record) obj;
		if (beginDate == null) {
			if (other.beginDate != null)
				return false;
		} else if (!beginDate.equals(other.beginDate))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (id != other.id)
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (studentId == null) {
			if (other.studentId != null)
				return false;
		} else if (!studentId.equals(other.studentId))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
}
