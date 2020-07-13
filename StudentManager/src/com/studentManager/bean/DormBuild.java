package com.studentManager.bean;
/*@ËŞÉáÂ¥Àà£º
 * ËŞÉáÂ¥¶°
 * ËŞÉáÂ¥Ãû×Ö
 * ËŞÉáÂ¥±¸×¢
 * 
 * 
 */
public class DormBuild {
	private Integer id;
	private String name;
	private String remark;
	private Integer disabled;
	
	public DormBuild() {
		super();
	}
	
	public DormBuild(Integer id, String name, String remark, Integer disabled) {
		super();
		this.id = id;
		this.name = name;
		this.remark = remark;
		this.disabled = disabled;
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
	
	@Override
	public String toString() {
		return "DormBuild [id=" + id + ", name=" + name + ", remark=" + remark + ", disabled="
				+ disabled + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DormBuild other = (DormBuild) obj;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
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
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		
		return true;
	}
	
}
