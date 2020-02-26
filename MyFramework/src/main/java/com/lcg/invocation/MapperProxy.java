package com.lcg.invocation;

import com.lcg.sqlSession.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author lichenggang
 * @date 2020/2/26 2:21 下午
 * @description
 */
public class MapperProxy<T> implements InvocationHandler {


    private SqlSession sqlSession;

    public MapperProxy(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    /**
     * 代理对象在目标方法执行时，会执行invoke方法
     */
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

        if (methodName.contains("find")) {
            //判断返回值是否泛型参数化
            if (genericReturnType instanceof ParameterizedType) {
                return sqlSession.findAll(statementId, args);
            } else {
                return sqlSession.findOne(statementId, args);
            }
        } else if (methodName.contains("add")) {
            sqlSession.add(statementId, args);
        } else if (methodName.contains("update")) {
            sqlSession.update(statementId, args);
        } else if (methodName.contains("delete")) {
            sqlSession.delete(statementId, args);
        }

        return null;
    }


}
