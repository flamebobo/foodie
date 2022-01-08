package com.bfxy.rabbit.api;

/**
 * 	$MessageListener 消费者监听消息
 * @author Alienware
 *
 */
public interface MessageListener {

	/**
	 * 监听消息
	 * @param message
	 */
	void onMessage(Message message);
	
}
