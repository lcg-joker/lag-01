package com.lcg.test;

import com.lcg.dao.UserDao;
import com.lcg.entity.User;
import com.lcg.io.Resources;
import com.lcg.sqlSession.SqlSession;
import com.lcg.sqlSession.SqlSessionFactory;
import com.lcg.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.beans.PropertyVetoException;
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
        user.setId("1");
        user.setUserName("lcg");


        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<User> all = userDao.findAll();
        for (User o : all) {
            System.out.println("id:" + o.getId() + ",username:" + o.getUserName() + ",age:" + o.getAge());
        }
    }
}
