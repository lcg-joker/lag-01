package com.lcg.test;

import com.lcg.dao.UserDao;
import com.lcg.entity.User;
import com.lcg.io.Resources;
import com.lcg.sqlSession.SqlSession;
import com.lcg.sqlSession.SqlSessionFactory;
import com.lcg.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * @author lichenggang
 * @date 2020/2/24 12:11 上午
 * @description
 */
public class MyFrameworkTest {


    @Test
    public void test() throws Exception {
        InputStream resourcesAsSteam = Resources.getResourcesAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourcesAsSteam);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = new User();
        user.setId("3");
        user.setUserName("ddd");
        user.setAge(11);
        //获取代理对象
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        userDao.addUser(user);

    }


    @Test
    public void test1() throws Exception {
        InputStream resourcesAsSteam = Resources.getResourcesAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourcesAsSteam);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = new User();
        user.setId("3");
        user.setUserName("dd3d");
        user.setAge(111);
        //获取代理对象
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        userDao.updateUser(user);

    }


    @Test
    public void test2() throws Exception {
        InputStream resourcesAsSteam = Resources.getResourcesAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourcesAsSteam);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //获取代理对象
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        userDao.deleteUser("3");

    }




}
