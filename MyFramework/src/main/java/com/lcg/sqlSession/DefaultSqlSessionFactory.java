package com.lcg.sqlSession;

import com.lcg.pojo.Configuration;

/**
 * @author lichenggang
 * @date 2020/2/24 2:36 上午
 * @description
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {


    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }


    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
