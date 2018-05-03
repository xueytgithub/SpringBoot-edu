package io.renren.modules.attendance.entity;

import java.io.Serializable;
import java.util.Date;

public class AttendanceEntity implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7156430145537288878L;

	private Long id;

    private Date checkin;

    private String statusin;

    private Date checkout;

    private String statusout;

    private String remark;

    private Long userId;
    
    private String userName;
    
    private String showTime;//日期
    private String week;  //周几
    private String workTime; //工作时间
    private String deptname; //部门名称
    private String poiin; //poi导出签到时间
    private String poiout; //poi导出签退时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCheckin() {
        return checkin;
    }

    public void setCheckin(Date checkin) {
        this.checkin = checkin;
    }

    public String getStatusin() {
        return statusin;
    }

    public void setStatusin(String statusin) {
        this.statusin = statusin == null ? null : statusin.trim();
    }

    public Date getCheckout() {
        return checkout;
    }

    public void setCheckout(Date checkout) {
        this.checkout = checkout;
    }

    public String getStatusout() {
        return statusout;
    }

    public void setStatusout(String statusout) {
        this.statusout = statusout == null ? null : statusout.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	@Override
	public String toString() {
		return "AttendanceEntity [id=" + id + ", checkin=" + checkin + ", statusin=" + statusin + ", checkout="
				+ checkout + ", statusout=" + statusout + ", remark=" + remark + ", userId=" + userId + ", userName="
				+ userName + ", showTime=" + showTime + ", week=" + week + ", workTime=" + workTime + ", deptname="
				+ deptname + "]";
	}

	public String getPoiin() {
		return poiin;
	}

	public void setPoiin(String poiin) {
		this.poiin = poiin;
	}

	public String getPoiout() {
		return poiout;
	}

	public void setPoiout(String poiout) {
		this.poiout = poiout;
	}

    
}