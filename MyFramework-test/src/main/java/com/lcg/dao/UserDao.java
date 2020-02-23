package com.lcg.dao;

import com.lcg.entity.User;

import java.util.List;

/**
 * @author lichenggang
 * @date 2020/2/24 3:53 上午
 * @description
 */
public interface UserDao {

    public List<User> findAll();

    public User findOne(User user);
}
