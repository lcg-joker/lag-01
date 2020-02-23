package com.lcg.pojo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 核心配置类
 *
 * @author lichenggang
 * @date 2020/2/24 12:17 上午
 * @description
 */
public class Configuration {

    private DataSource dataSource;
    /**
     * key:statementId  value: mappedStatement对象
     */
    private Map<String, MappedStatement> mappedStatementMap = new HashMap<>();


    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }
}
