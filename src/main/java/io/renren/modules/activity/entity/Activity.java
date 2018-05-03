package io.renren.modules.activity.entity;

import java.io.Serializable;
import java.util.Date;

public class Activity implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String number;

    private String filename;

    private String fileurl;

    private String thumbnailurl;

    private String materialtype;

    private String type;

    private String classes;

    private String term;

    private String territory;

    private String activityname;

    private String oneclassify;

    private String twoclassify;

    private String threeclassify;

    private String fourclassify;

    private String fiveclassify;

    private String keyword;

    private Long isdownload;

    private Long isfree;

    private Long isprint;

    private Long isappbrowse;

    private Double price;

    private Integer touristplaytime;

    private Integer vipplaytime;

    private Long userid;
    
    private Date createTime;
    
    private Date updateTime;
    
    private Integer flag;
    
    private String showType;
    
    private String filesize;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl == null ? null : fileurl.trim();
    }

    public String getThumbnailurl() {
        return thumbnailurl;
    }

    public void setThumbnailurl(String thumbnailurl) {
        this.thumbnailurl = thumbnailurl == null ? null : thumbnailurl.trim();
    }

    public String getMaterialtype() {
        return materialtype;
    }

    public void setMaterialtype(String materialtype) {
        this.materialtype = materialtype == null ? null : materialtype.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes == null ? null : classes.trim();
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term == null ? null : term.trim();
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory == null ? null : territory.trim();
    }

    public String getActivityname() {
        return activityname;
    }

    public void setActivityname(String activityname) {
        this.activityname = activityname == null ? null : activityname.trim();
    }

    public String getOneclassify() {
        return oneclassify;
    }

    public void setOneclassify(String oneclassify) {
        this.oneclassify = oneclassify == null ? null : oneclassify.trim();
    }

    public String getTwoclassify() {
        return twoclassify;
    }

    public void setTwoclassify(String twoclassify) {
        this.twoclassify = twoclassify == null ? null : twoclassify.trim();
    }

    public String getThreeclassify() {
        return threeclassify;
    }

    public void setThreeclassify(String threeclassify) {
        this.threeclassify = threeclassify == null ? null : threeclassify.trim();
    }

    public String getFourclassify() {
        return fourclassify;
    }

    public void setFourclassify(String fourclassify) {
        this.fourclassify = fourclassify == null ? null : fourclassify.trim();
    }

    public String getFiveclassify() {
        return fiveclassify;
    }

    public void setFiveclassify(String fiveclassify) {
        this.fiveclassify = fiveclassify == null ? null : fiveclassify.trim();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public Long getIsdownload() {
        return isdownload;
    }

    public void setIsdownload(Long isdownload) {
        this.isdownload = isdownload;
    }

    public Long getIsfree() {
        return isfree;
    }

    public void setIsfree(Long isfree) {
        this.isfree = isfree;
    }

    public Long getIsprint() {
        return isprint;
    }

    public void setIsprint(Long isprint) {
        this.isprint = isprint;
    }

    public Long getIsappbrowse() {
        return isappbrowse;
    }

    public void setIsappbrowse(Long isappbrowse) {
        this.isappbrowse = isappbrowse;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTouristplaytime() {
        return touristplaytime;
    }

    public void setTouristplaytime(Integer touristplaytime) {
        this.touristplaytime = touristplaytime;
    }

    public Integer getVipplaytime() {
        return vipplaytime;
    }

    public void setVipplaytime(Integer vipplaytime) {
        this.vipplaytime = vipplaytime;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}


	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public String getFilesize() {
		return filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	@Override
	public String toString() {
		return "Activity [id=" + id + ", number=" + number + ", filename=" + filename + ", fileurl=" + fileurl
				+ ", thumbnailurl=" + thumbnailurl + ", materialtype=" + materialtype + ", type=" + type + ", classes="
				+ classes + ", term=" + term + ", territory=" + territory + ", activityname=" + activityname
				+ ", oneclassify=" + oneclassify + ", twoclassify=" + twoclassify + ", threeclassify=" + threeclassify
				+ ", fourclassify=" + fourclassify + ", fiveclassify=" + fiveclassify + ", keyword=" + keyword
				+ ", isdownload=" + isdownload + ", isfree=" + isfree + ", isprint=" + isprint + ", isappbrowse="
				+ isappbrowse + ", price=" + price + ", touristplaytime=" + touristplaytime + ", vipplaytime="
				+ vipplaytime + ", userid=" + userid + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", flag=" + flag + ", showType=" + showType + ", filesize=" + filesize + "]";
	}
    
	
}