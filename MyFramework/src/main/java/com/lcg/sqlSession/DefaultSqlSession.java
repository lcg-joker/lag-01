package com.lcg.sqlSession;

import com.lcg.invocation.MapperProxy;
import com.lcg.pojo.Configuration;
import com.lcg.pojo.MappedStatement;

import java.lang.reflect.*;
import java.util.List;

/**
 * @author lichenggang
 * @date 2020/2/24 2:40 上午
 * @description
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> List<T> findAll(String statementId, Object... params) throws Exception {

        //获取MappedStatement对象
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        //构造执行对象
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        //查询结果
        List<T> list = simpleExecutor.query(configuration, mappedStatement, params);
        return list;
    }

    @Override
    public <T> T findOne(String statementId, Object... params) throws Exception {

        List<T> list = findAll(statementId, params);

        if (list == null) {
            return null;
        } else if (list.size() == 1) {
            return list.get(0);
        } else {
            throw new RuntimeException("查询存在多个结果");
        }
    }

    @Override
    public void add(String statementId, Object... params) throws Exception {
        //获取MappedStatement对象
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        //构造执行对象
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        //执行sql
        simpleExecutor.update(configuration, mappedStatement, params);
    }

    @Override
    public void update(String statementId, Object... params) throws Exception {
        //获取MappedStatement对象
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        //构造执行对象
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        //执行sql
        simpleExecutor.update(configuration, mappedStatement, params);
    }

    @Override
    public void delete(String statementId, Object... params) throws Exception {
        //获取MappedStatement对象
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        //构造执行对象
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        //执行sql
        simpleExecutor.update(configuration, mappedStatement, params);
    }


    /**
     * 获取JDK
     * 动态代理对象
     *
     * @param className
     * @param <T>
     * @return
     */
    @Override
    public <T> T getMapper(Class<?> className) {
        //创建代理对象
        Object newProxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{className}, new MapperProxy(this));
        return (T) newProxyInstance;
    }
}
