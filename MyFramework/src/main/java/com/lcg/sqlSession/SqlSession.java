package com.lcg.sqlSession;

import java.util.List;

/**
 * @author lichenggang
 * @date 2020/2/24 2:39 上午
 * @description
 */
public interface SqlSession {

    //查询所有
    public <T> List<T> findAll(String statementId, Object... params) throws Exception;

    //查询单个
    public <T> T findOne(String statementId, Object... params) throws Exception;

    //获取代理对象
    public <T> T getMapper(Class<?> className);
}
