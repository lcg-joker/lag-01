package com.lcg.io;

import java.io.InputStream;

/**
 * @author lichenggang
 * @date 2020/2/24 12:05 上午
 * @description
 */
public class Resources {


    /**
     * 根据路径， 加载配置文件到内存中
     */
    public static InputStream getResourcesAsSteam(String path) {
        InputStream inputStream = Resources.class.getClassLoader().getResourceAsStream(path);
        return inputStream;
    }

}
