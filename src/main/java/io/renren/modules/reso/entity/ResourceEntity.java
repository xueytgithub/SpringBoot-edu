package io.renren.modules.reso.entity;

import java.io.Serializable;
import java.util.Date;

public class ResourceEntity implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String name;

    private Long sn;

    private Date createTime;
    
    private Date updateTime;

    private Long parentid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getSn() {
        return sn;
    }

    public void setSn(Long sn) {
        this.sn = sn;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "ResourceEntity [id=" + id + ", name=" + name + ", sn=" + sn + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", parentid=" + parentid + "]";
	}
    
}