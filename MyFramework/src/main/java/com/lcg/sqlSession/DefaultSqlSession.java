package com.lcg.sqlSession;

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


        Object newProxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{className}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                //代理对象不管调用什么方法 都 会运行此方法,底层是执行的JDBC代码，所以要再此处调用findAll 或者 fineOne

                //参数1 准备statementId
                //方法名称
                String methodName = method.getName();
                //方法所在类的全路径名
                String className = method.getDeclaringClass().getName();
                String statementId = className + "." + methodName;


                //参数2 params=args

                //取出方法的返回值类型
                Type genericReturnType = method.getGenericReturnType();
                //判断返回值是否泛型参数化
                if (genericReturnType instanceof ParameterizedType) {
                    return findAll(statementId, args);
                }
                return findOne(statementId, args);
            }
        });

        return (T) newProxyInstance;
    }
}
