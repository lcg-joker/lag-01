package com.lcg.sqlSession;

import com.lcg.config.BoundSql;
import com.lcg.pojo.Configuration;
import com.lcg.pojo.MappedStatement;
import com.lcg.utils.GenericTokenParser;
import com.lcg.utils.ParameterMapping;
import com.lcg.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lichenggang
 * @date 2020/2/24 2:55 上午
 * @description
 */
public class SimpleExecutor implements Executor {

    /**
     * 执行JDBC代码
     *
     * @param configuration
     * @param mappedStatement
     * @param params
     * @param <T>
     * @return
     * @throws Exception
     */
    @Override
    public <T> List<T> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        //注册驱动，获取数据库连接
        Connection connection = configuration.getDataSource().getConnection();
        //获取SQL
        String sql = mappedStatement.getSql();
        //解析sql 替换占位符
        BoundSql boundSql = getBound(sql);
        //获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        //region 设置参数

        //region 获取参数对象到全类限定名,进而获取到Class对象
        String paramterType = mappedStatement.getParamterType();
        Class paramterClass = getClassType(paramterType);
        //endregion

        //获取到sql中参数名集合
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();

        //遍历参数赋值
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();

            //利用反射获取到字段
            Field declaredField = paramterClass.getDeclaredField(content);
            //暴力访问
            declaredField.setAccessible(true);
            //拿到对象中这个字段到值
            Object o = declaredField.get(params[0]);
            //设置参数
            preparedStatement.setObject(i + 1, o);
        }
        //endregion

        //执行
        ResultSet resultSet = preparedStatement.executeQuery();

        //region 解析结果集
        List<Object> list = new ArrayList<>();
        String resultType = mappedStatement.getResultType();
        if (resultType != null) {
            //获取返回类型class
            Class resultTypeClass = getClassType(resultType);
            //遍历查询结果集
            while (resultSet.next()) {
                //每行数据就是一个对象
                Object o = resultTypeClass.newInstance();
                //元数据
                ResultSetMetaData metaData = resultSet.getMetaData();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    //列名
                    String columnName = metaData.getColumnName(i);
                    //列值
                    Object value = resultSet.getObject(columnName);

                    //使用反射、内省机制，根据数据库表接口与实体类到映射关系，进行封装数据
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                    //获取写到方法
                    Method writeMethod = propertyDescriptor.getWriteMethod();
                    //把值赋值到对象上
                    writeMethod.invoke(o, value);
                }
                list.add(o);
            }
        }
        //endregion

        return (List<T>) list;
    }

    @Override
    public void update(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {

        //注册驱动，获取数据库连接
        Connection connection = configuration.getDataSource().getConnection();
        //获取SQL
        String sql = mappedStatement.getSql();
        //解析sql 替换占位符
        BoundSql boundSql = getBound(sql);
        //获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        //region 设置参数

        //region 获取参数对象到全类限定名,进而获取到Class对象
        String paramterType = mappedStatement.getParamterType();
        Class paramterClass = getClassType(paramterType);
        //endregion


        //获取到sql中参数名集合
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();

        //遍历参数赋值
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();

            if (isWrapClass(paramterClass)) {
                preparedStatement.setObject(i + 1, params[0]);
            } else {
                //利用反射获取到字段
                Field declaredField = paramterClass.getDeclaredField(content);
                //暴力访问
                declaredField.setAccessible(true);
                //拿到对象中这个字段到值
                Object o = declaredField.get(params[0]);
                //设置参数
                preparedStatement.setObject(i + 1, o);
            }

        }
        //endregion

        //执行
        int i = preparedStatement.executeUpdate();
    }

    private Class<?> getClassType(String paramterType) throws ClassNotFoundException {
        if (paramterType != null) {
            Class<?> aClass = Class.forName(paramterType);
            return aClass;
        }
        return null;
    }

    private BoundSql getBound(String sql) {

        //标记处理类
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();

        //标记解析器
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);

        //获取解析后的SQL
        String sqlText = genericTokenParser.parse(sql);
        //获取参数字段名
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();

        BoundSql boundSql = new BoundSql(sqlText, parameterMappings);

        return boundSql;
    }

    public static boolean isWrapClass(Class clz) {
        if (clz.isPrimitive()) {
            return true;
        } else {
            if (clz.getName().equals("java.lang.String")) {
                return true;
            } else {
                try {
                    return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
                } catch (Exception e) {
                    return false;
                }
            }

        }
    }
}
