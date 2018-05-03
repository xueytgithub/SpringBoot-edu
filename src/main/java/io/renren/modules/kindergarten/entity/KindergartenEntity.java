package io.renren.modules.kindergarten.entity;

import java.io.Serializable;
import java.util.Date;

public class KindergartenEntity implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String name;

    private String province;

    private String city;

    private String county;

    private String address;

    private String phone;

    private Long scale;

    private Long buynumbers;

    private Date buytime;

    private Date buytimeEnd;

    private Double divide;
    
    private String kinAdderss;

    /**
     * type 1:代表幼儿园   2：代表代理商
     * 
     */
    private Long type;

    /**
     * 幼儿园对应的代理商id
     */
    private Long proxyid;
    
    private String proxyname;

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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county == null ? null : county.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Long getScale() {
        return scale;
    }

    public void setScale(Long scale) {
        this.scale = scale;
    }

    public Long getBuynumbers() {
        return buynumbers;
    }

    public void setBuynumbers(Long buynumbers) {
        this.buynumbers = buynumbers;
    }

    public Date getBuytime() {
        return buytime;
    }

    public void setBuytime(Date buytime) {
        this.buytime = buytime;
    }

    public Date getBuytimeEnd() {
        return buytimeEnd;
    }

    public void setBuytimeEnd(Date buytimeEnd) {
        this.buytimeEnd = buytimeEnd;
    }

    public Double getDivide() {
        return divide;
    }

    public void setDivide(Double divide) {
        this.divide = divide;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }


	public Long getProxyid() {
		return proxyid;
	}

	public void setProxyid(Long proxyid) {
		this.proxyid = proxyid;
	}

	public String getProxyname() {
		return proxyname;
	}

	public void setProxyname(String proxyname) {
		this.proxyname = proxyname;
	}
    
	public String getKinAdderss() {
		return kinAdderss;
	}

	public void setKinAdderss(String kinAdderss) {
		this.kinAdderss = kinAdderss;
	}

	@Override
	public String toString() {
		return "KindergartenEntity [id=" + id + ", name=" + name + ", province=" + province + ", city=" + city
				+ ", county=" + county + ", address=" + address + ", phone=" + phone + ", scale=" + scale
				+ ", buynumbers=" + buynumbers + ", buytime=" + buytime + ", buytimeEnd=" + buytimeEnd + ", divide="
				+ divide + ", kinAdderss=" + kinAdderss + ", type=" + type + ", proxyid=" + proxyid + ", proxyname="
				+ proxyname + "]";
	}
	
}