package com.flame.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Users {
    @Id
    private Integer id;

    /**
     * 用户在慕课网的用户id
     */
    @Column(name = "flame_user_id")
    private String flameUserId;

    /**
     * 由我分配给用户的密码，这个密码存入数据库需要加密
     */
    private String password;

    /**
     * 用户访问有效期，20元/月
     */
    @Column(name = "end_date")
    private Date endDate;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户在慕课网的用户id
     *
     * @return flame_user_id - 用户在慕课网的用户id
     */
    public String getflameUserId() {
        return flameUserId;
    }

    /**
     * 设置用户在慕课网的用户id
     *
     * @param flameUserId 用户在慕课网的用户id
     */
    public void setflameUserId(String flameUserId) {
        this.flameUserId = flameUserId;
    }

    /**
     * 获取由我分配给用户的密码，这个密码存入数据库需要加密
     *
     * @return password - 由我分配给用户的密码，这个密码存入数据库需要加密
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置由我分配给用户的密码，这个密码存入数据库需要加密
     *
     * @param password 由我分配给用户的密码，这个密码存入数据库需要加密
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取用户访问有效期，20元/月
     *
     * @return end_date - 用户访问有效期，20元/月
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 设置用户访问有效期，20元/月
     *
     * @param endDate 用户访问有效期，20元/月
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}