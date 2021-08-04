package com.flame.service;

import com.flame.pojo.Orders;
import com.flame.pojo.Users;
import com.flame.pojo.bo.MerchantOrdersBO;

public interface UserService {

	/**
	 * @Description: 查询用户信息
	 */
	public Users queryUserInfo(String userId, String pwd);

}

