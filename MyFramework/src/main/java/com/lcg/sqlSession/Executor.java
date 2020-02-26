package com.lcg.sqlSession;

import com.lcg.pojo.Configuration;
import com.lcg.pojo.MappedStatement;

import java.util.List;

/**
 * @author lichenggang
 * @date 2020/2/24 2:54 上午
 * @description
 */
public interface Executor {

    public <T> List<T> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;

    public void update(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;
}
