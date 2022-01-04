package com.bfxy.rabbit.api;

/**
 * 	$SendCallback 回调函数处理
 * @author Alienware
 *
 */
public interface SendCallback {

	/**
	 * 成功时回调方法
	 */
	void onSuccess();

	/**
	 * 失败时回调方法
	 */
	void onFailure();
	
}
