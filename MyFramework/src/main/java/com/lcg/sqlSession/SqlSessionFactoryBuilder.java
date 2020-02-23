package com.lcg.sqlSession;

import com.lcg.config.XMLConfigBuilder;
import com.lcg.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @author lichenggang
 * @date 2020/2/24 12:25 上午
 * @description
 */
public class SqlSessionFactoryBuilder {


    /**
     * 构建SqlSessionFactory对象
     *
     * @param in
     * @return
     */
    public SqlSessionFactory build(InputStream in) throws PropertyVetoException, DocumentException {

        //1.用dom4j解析xml信息，保存到Configuration对象中
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(in);


        //2.创建SqlSessionFactory对象，并返回
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);

        return defaultSqlSessionFactory;

    }

}
