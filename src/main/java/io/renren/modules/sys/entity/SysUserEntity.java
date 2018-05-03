package io.renren.modules.sys.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:28:55
 */
public class SysUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 用户名
	 */
	@NotBlank(message="用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String username;

	/**
	 * 密码
	 */
	@NotBlank(message="密码不能为空", groups = AddGroup.class)
	private String password;

	/**
	 * 盐
	 */
	private String salt;

	/**
	 * 邮箱
	 */
	@NotBlank(message="邮箱不能为空", groups = {AddGroup.class, UpdateGroup.class})
	@Email(message="邮箱格式不正确", groups = {AddGroup.class, UpdateGroup.class})
	private String email;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 状态  0：禁用   1：正常
	 */
	private Integer status;
	
	/**
	 * 角色ID列表
	 */
	private List<Long> roleIdList;
	
	/**
	 * 创建者ID
	 */
	private Long createUserId;

	/**
	 * 创建时间
	 */
	private Date createTime;
	private Date createTimeEnd;

	/**
	 * 部门ID
	 */
	@NotNull(message="部门不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Long deptId;

	/**
	 * 部门名称
	 */
	private String deptName;
	
	/**
	 * 会员类型 0:半年，1:1年；2:3年
	 */
	private Integer viptype;
	private String vitype;

	/**
	 * 年龄
	 */
	private Integer age;
	
	/**
	 * 幼儿园外键
	 */
	private Integer kindergartenId;
	
	/**
	 * 省
	 */
	private String province;
	
	/**
	 * 市
	 */
	private String city;
	
	/**
	 * 县
	 */
	private String county;
	private String userAddress;
	
	private Date endTime;
	private String rolename;
	private String name;
	private Integer buytype;
	private String showstartTime;
	private String showendTime;
	
	/**
	 * 设置：
	 * @param userId 
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 获取：
	 * @return Long
	 */
	public Long getUserId() {
		return userId;
	}
	
	/**
	 * 设置：用户名
	 * @param username 用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取：用户名
	 * @return String
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * 设置：密码
	 * @param password 密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取：密码
	 * @return String
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * 设置：邮箱
	 * @param email 邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取：邮箱
	 * @return String
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * 设置：手机号
	 * @param mobile 手机号
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 获取：手机号
	 * @return String
	 */
	public String getMobile() {
		return mobile;
	}
	
	/**
	 * 设置：状态  0：禁用   1：正常
	 * @param status 状态  0：禁用   1：正常
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取：状态  0：禁用   1：正常
	 * @return Integer
	 */
	public Integer getStatus() {
		return status;
	}
	
	/**
	 * 设置：创建时间
	 * @param createTime 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取：创建时间
	 * @return Date
	 */
	public Date getCreateTime() {
		return createTime;
	}

	public List<Long> getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(List<Long> roleIdList) {
		this.roleIdList = roleIdList;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	//***********************
	public Integer getViptype() {
		return viptype;
	}

	public void setViptype(Integer viptype) {
		this.viptype = viptype;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getVitype() {
		return vitype;
	}

	public void setVitype(String vitype) {
		this.vitype = vitype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	
	public Integer getKindergartenId() {
		return kindergartenId;
	}

	public void setKindergartenId(Integer kindergartenId) {
		this.kindergartenId = kindergartenId;
	}

	public Integer getBuytype() {
		return buytype;
	}

	public void setBuytype(Integer buytype) {
		this.buytype = buytype;
	}

	public String getShowstartTime() {
		return showstartTime;
	}

	public void setShowstartTime(String showstartTime) {
		this.showstartTime = showstartTime;
	}

	public String getShowendTime() {
		return showendTime;
	}

	public void setShowendTime(String showendTime) {
		this.showendTime = showendTime;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	@Override
	public String toString() {
		return "SysUserEntity [userId=" + userId + ", username=" + username + ", password=" + password + ", salt="
				+ salt + ", email=" + email + ", mobile=" + mobile + ", status=" + status + ", roleIdList=" + roleIdList
				+ ", createUserId=" + createUserId + ", createTime=" + createTime + ", createTimeEnd=" + createTimeEnd
				+ ", deptId=" + deptId + ", deptName=" + deptName + ", viptype=" + viptype + ", vitype=" + vitype
				+ ", age=" + age + ", kindergartenId=" + kindergartenId + ", province=" + province + ", city=" + city
				+ ", county=" + county + ", userAddress=" + userAddress + ", endTime=" + endTime + ", rolename="
				+ rolename + ", name=" + name + ", buytype=" + buytype + ", showstartTime=" + showstartTime
				+ ", showendTime=" + showendTime + "]";
	}
	
}
