package io.renren.common.exception;

import org.apache.http.HttpStatus;
/**
 * 自定义异常
 * @author joe
 *
 */
public enum ResultEnum {
	
	NO_PERMISSION(1,"没有权限"),
	DATA_EXIST_IN_DB(2,"数据库中已存在该记录"),
	HAVE_CHILD_DEPTMENT(3,"请先删除子部门"),
	HAVE_CHILD_MENU_OR_BUTTON(4,"请先删除子菜单或按钮"),
	VERITICATION_CODE_ERROR(5,"验证码不正确"),
	LOGIN_FAIL_DATA_ERROR(6,"账号或密码不正确"),
	USER_LOCKED(7,"账号已被锁定,请联系管理员"),
	SYS_MENU_CANNOT_DELETE(8,"系统菜单，不能删除"),
	OLD_PWD_ERROR(9,"原密码不正确"),
	CANNOT_DELETE_USER(10,"当前用户不能删除"),
	CANNOT_DELETE_ADMIN_USER(11,"管理员用户不能删除"),
	SYS_ERROR(HttpStatus.SC_INTERNAL_SERVER_ERROR,"未知异常，请联系管理员"),
	SC_UNAUTHORIZED(HttpStatus.SC_INTERNAL_SERVER_ERROR,"invalid token"),
	;
	private Integer code;
	
	private String msg;

	private ResultEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	
	
}
