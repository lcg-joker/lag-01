package com.lcg.config;

import com.lcg.io.Resources;
import com.lcg.pojo.Configuration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author lichenggang
 * @date 2020/2/24 12:28 上午
 * @description
 */
public class XMLConfigBuilder {


    private Configuration configuration;

    public XMLConfigBuilder() {
        this.configuration = new Configuration();
    }

    /**
     * 解析配置文件，信息存在Configuration对象中
     *
     * @param inputStream
     * @return
     */
    public Configuration parseConfig(InputStream inputStream) throws DocumentException, PropertyVetoException {

        // region 1.使用dom4j解析核心配置文件
        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();
        //查找所有的property节点
        List<Element> list = rootElement.selectNodes("//property");

        //配置信息容器
        Properties properties = new Properties();
        for (Element element : list) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.put(name, value);
        }

        //创建一个C3P0的数据源对象
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));

        configuration.setDataSource(comboPooledDataSource);

        //endregion

        //region 2.解析映射配置文件 mapper.xml    拿到文件-->读取文件 字节流--> 解析文件
        List<Element> mapperList = rootElement.selectNodes("//mapper");
        for (Element element : mapperList) {
            String resource = element.attributeValue("resource");
            InputStream stream = Resources.getResourcesAsSteam(resource);
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            xmlMapperBuilder.parse(stream);
        }
        //endregion

        return configuration;
    }

}
