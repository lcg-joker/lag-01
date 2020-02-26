package com.lcg.config;

import com.lcg.pojo.Configuration;
import com.lcg.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @author lichenggang
 * @date 2020/2/24 2:22 上午
 * @description
 */
public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }


    /**
     * 解析
     *
     * @param inputStream
     */
    public void parse(InputStream inputStream) throws DocumentException {

        Document document = new SAXReader().read(inputStream);

        Element rootElement = document.getRootElement();

        String namespace = rootElement.attributeValue("namespace");

        addAllMappedStatementMapByType(rootElement, namespace, "select");
        addAllMappedStatementMapByType(rootElement, namespace, "insert");
        addAllMappedStatementMapByType(rootElement, namespace, "update");
        addAllMappedStatementMapByType(rootElement, namespace, "delete");

    }


    private void addAllMappedStatementMapByType(Element rootElement, String namespace, String type) {
        List<Element> list = rootElement.selectNodes("//" + type);
        for (Element element : list) {
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String paramterType = element.attributeValue("paramterType");
            String sql = element.getTextTrim();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setParamterType(paramterType);
            mappedStatement.setSql(sql);
            String key = namespace + "." + id;
            configuration.getMappedStatementMap().put(key, mappedStatement);
        }
    }


}
