package com.abin.lee.redis.common.exception;

/**      
 * 创建人：subin.li   
 * 创建时间：2014年12月1日 下午5:08:21     
 *     
 */
public class OperateException extends RuntimeException{
	private static final long serialVersionUID = -3053481908517551708L;
	private String code;

	public OperateException() {
		super();
	}

	public OperateException(String message, Throwable cause, String code) {
		super(message, cause);
		this.code = code;
	}

	public OperateException(String message, String code) {
		super(message);
		this.code = code;
	}

	public OperateException(Throwable cause, String code) {
		super(cause);
		this.code = code;
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return super.fillInStackTrace();
	}

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
}
