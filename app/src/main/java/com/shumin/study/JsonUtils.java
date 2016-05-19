package com.shumin.study;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Guchuan on 2016/5/17.
 */
public class JsonUtils {

    private static ObjectMapper sObjectMapper;

    private static ObjectMapper getMapper() {
        if (sObjectMapper == null) {
            synchronized (JsonUtils.class) {
                if (sObjectMapper == null) {
                    sObjectMapper = new ObjectMapper();
                    sObjectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                }
            }
        }
        return sObjectMapper;
    }

    public static <T> T parseToArrayList(String json, Class<?> elementClass) throws
            JsonGenerationException, JsonMappingException, IOException {
        ObjectMapper mapper = getMapper();
        return mapper.readValue(json,
                mapper.getTypeFactory().constructParametricType(ArrayList.class, elementClass));
    }

    public static String toString(Object object) throws JsonGenerationException, JsonMappingException, IOException {
        return getMapper().writeValueAsString(object);
    }

}
