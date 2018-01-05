package org.xi.quick.codegenerator.utils;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author 郗世豪（xish@cloud-young.com）
 * @date 2018/01/05 22:46
 */
public class PropertiesUtil {

    /**
     * 获取配置Map
     *
     * @param path
     * @param encoding
     * @return
     */
    public static Map<Object, Object> getProperties(String path, String encoding) {

        Map<Object, Object> commonPropertiesMap = new HashMap<>();
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(path);
             Reader reader = new InputStreamReader(inputStream, encoding)) {
            properties.load(reader);
            properties.forEach((key, value) -> commonPropertiesMap.put(key, value));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        commonPropertiesMap.put("now", new Date());

        return commonPropertiesMap;
    }
}
