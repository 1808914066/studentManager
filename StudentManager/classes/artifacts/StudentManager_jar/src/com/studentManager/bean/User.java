package com.studentManager.bean;

import java.util.List;

public class User {

	private Integer id;//id
	private String name;//名字
	private String passWord;//密码
	private String stuCode;//学号
	private String sex;//性别
	private String tel;//联系方式
	private Integer dormBuildId;//宿舍ID
	private String dormCode;//宿舍号
	private Integer roleId;//0 管理员 1宿舍管理员 2学生
	private Integer disabled;
	private Integer creatUserId;//创建人
	
	private DormBuild dormBuild;
	private List<DormBuild> dormBuilds;
	
	public User() {
		super();
	}
	
	public User(String name, String passWord, String sex, String tel, Integer dormBuildId,
			Integer roleId) {
		super();		
		this.name = name;
		this.passWord = passWord;		
		this.sex = sex;
		this.tel = tel;
		this.dormBuildId = dormBuildId;	
		this.roleId = roleId;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getStuCode() {
		return stuCode;
	}
	public void setStuCode(String stuCode) {
		this.stuCode = stuCode;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Integer getDormBuildId() {
		return dormBuildId;
	}
	public void setDormBuildId(Integer dormBuildId) {
		this.dormBuildId = dormBuildId;
	}
	public String getDormCode() {
		return dormCode;
	}
	public void setDormCode(String dormCode) {
		this.dormCode = dormCode;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getDisabled() {
		return disabled;
	}
	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}
	public Integer getCreatUserId() {
		return creatUserId;
	}
	public void setCreatUserId(Integer creatUserId) {
		this.creatUserId = creatUserId;
	}
	public DormBuild getDormBuild() {
		return dormBuild;
	}
	public void setDormBuild(DormBuild dormBuild) {
		this.dormBuild = dormBuild;
	}

	public List<DormBuild> getDormBuilds() {
		return dormBuilds;
	}

	public void setDormBuilds(List<DormBuild> dormBuilds) {
		this.dormBuilds = dormBuilds;
	}

	

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", passWord=" + passWord + ", stuCode=" + stuCode + ", sex=" + sex
				+ ", tel=" + tel + ", dormBuildId=" + dormBuildId + ", dormCode=" + dormCode + ", roleId=" + roleId
				+ ", disabled=" + disabled + ", creatUserId=" + creatUserId + ", dormBuild=" + dormBuild
				+ ", dormBuilds=" + dormBuilds + ", getId()=" + getId() + ", getName()=" + getName()
				+ ", getPassWord()=" + getPassWord() + ", getStuCode()=" + getStuCode() + ", getSex()=" + getSex()
				+ ", getTel()=" + getTel() + ", getDormBuildId()=" + getDormBuildId() + ", getDormCode()="
				+ getDormCode() + ", getRoleId()=" + getRoleId() + ", getDisabled()=" + getDisabled()
				+ ", getCreatUserId()=" + getCreatUserId() + ", getDormBuild()=" + getDormBuild() + ", getDormBuilds()="
				+ getDormBuilds() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creatUserId == null) ? 0 : creatUserId.hashCode());
		result = prime * result + ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((dormBuild == null) ? 0 : dormBuild.hashCode());
		result = prime * result + ((dormBuildId == null) ? 0 : dormBuildId.hashCode());
		result = prime * result + ((dormBuilds == null) ? 0 : dormBuilds.hashCode());
		result = prime * result + ((dormCode == null) ? 0 : dormCode.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((passWord == null) ? 0 : passWord.hashCode());
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result + ((stuCode == null) ? 0 : stuCode.hashCode());
		result = prime * result + ((tel == null) ? 0 : tel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (creatUserId == null) {
			if (other.creatUserId != null)
				return false;
		} else if (!creatUserId.equals(other.creatUserId))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (dormBuild == null) {
			if (other.dormBuild != null)
				return false;
		} else if (!dormBuild.equals(other.dormBuild))
			return false;
		if (dormBuildId == null) {
			if (other.dormBuildId != null)
				return false;
		} else if (!dormBuildId.equals(other.dormBuildId))
			return false;
		if (dormBuilds == null) {
			if (other.dormBuilds != null)
				return false;
		} else if (!dormBuilds.equals(other.dormBuilds))
			return false;
		if (dormCode == null) {
			if (other.dormCode != null)
				return false;
		} else if (!dormCode.equals(other.dormCode))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (passWord == null) {
			if (other.passWord != null)
				return false;
		} else if (!passWord.equals(other.passWord))
			return false;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		if (stuCode == null) {
			if (other.stuCode != null)
				return false;
		} else if (!stuCode.equals(other.stuCode))
			return false;
		if (tel == null) {
			if (other.tel != null)
				return false;
		} else if (!tel.equals(other.tel))
			return false;
		return true;
	}

	
	
}
