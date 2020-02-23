package com.lcg.config;

import com.lcg.utils.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lichenggang
 * @date 2020/2/24 3:16 上午
 * @description
 */
public class BoundSql {

    //解析过后的 SQL
    private String sqlText;
    //参数字段名
    private List<ParameterMapping> parameterMappingList = new ArrayList<>();


    public BoundSql(String sqlText, List<ParameterMapping> parameterMappingList) {
        this.sqlText = sqlText;
        this.parameterMappingList = parameterMappingList;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public List<ParameterMapping> getParameterMappingList() {
        return parameterMappingList;
    }

    public void setParameterMappingList(List<ParameterMapping> parameterMappingList) {
        this.parameterMappingList = parameterMappingList;
    }
}
