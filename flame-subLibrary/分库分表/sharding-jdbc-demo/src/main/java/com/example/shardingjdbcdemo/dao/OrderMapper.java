package com.example.shardingjdbcdemo.dao;

import com.example.shardingjdbcdemo.model.Order;
import com.example.shardingjdbcdemo.model.OrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_1
     *
     * @mbg.generated Sat Sep 21 23:03:14 CST 2019
     */
    long countByExample(OrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_1
     *
     * @mbg.generated Sat Sep 21 23:03:14 CST 2019
     */
    int deleteByExample(OrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_1
     *
     * @mbg.generated Sat Sep 21 23:03:14 CST 2019
     */
    int deleteByPrimaryKey(Integer orderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_1
     *
     * @mbg.generated Sat Sep 21 23:03:14 CST 2019
     */
    int insert(Order record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_1
     *
     * @mbg.generated Sat Sep 21 23:03:14 CST 2019
     */
    int insertSelective(Order record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_1
     *
     * @mbg.generated Sat Sep 21 23:03:14 CST 2019
     */
    List<Order> selectByExample(OrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_1
     *
     * @mbg.generated Sat Sep 21 23:03:14 CST 2019
     */
    Order selectByPrimaryKey(Integer orderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_1
     *
     * @mbg.generated Sat Sep 21 23:03:14 CST 2019
     */
    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_1
     *
     * @mbg.generated Sat Sep 21 23:03:14 CST 2019
     */
    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_1
     *
     * @mbg.generated Sat Sep 21 23:03:14 CST 2019
     */
    int updateByPrimaryKeySelective(Order record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_1
     *
     * @mbg.generated Sat Sep 21 23:03:14 CST 2019
     */
    int updateByPrimaryKey(Order record);
}