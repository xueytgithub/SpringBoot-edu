package io.renren.modules.life.entity;

import java.io.Serializable;
import java.util.Date;

public class LivingEntity implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7838612704578848372L;

	private Integer id;

    private String username;

    private String text;
    
    private String date;

    private String week;

    private Long type;

    private Double money;

    private Date createtime;

    private Date updatetime;
    
    private String typetext;
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }


    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week == null ? null : week.trim();
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

	public String getTypetext() {
		return typetext;
	}

	public void setTypetext(String typetext) {
		this.typetext = typetext;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "LivingEntity [id=" + id + ", username=" + username + ", text=" + text + ", date=" + date + ", week="
				+ week + ", type=" + type + ", money=" + money + ", createtime=" + createtime + ", updatetime="
				+ updatetime + ", typetext=" + typetext + "]";
	}

}