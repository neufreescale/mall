package org.diwayou.core.json;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author gaopeng 2021/1/15
 */
@Slf4j
public class Json {

    private static final ObjectMapper mapper = createObjectMapper(Include.NON_NULL);

    public static ObjectMapper createObjectMapper(Include include) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(include);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);

        return mapper;
    }

    public static <T> T fromJson(String jsonString, Type type) {
        try {
            return mapper.readValue(jsonString, TypeFactory.defaultInstance().constructType(type));
        } catch (JsonProcessingException e) {
            log.error("Json转换出错", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, clazz);
        } catch (JsonProcessingException e) {
            log.error("Json转换出错", e);
            throw new RuntimeException(e);
        }
    }

    public static JavaType constructParametricType(Class<?> parametrized, Class<?>... parameterClasses) {
        return mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
    }

    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Json转换出错", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, javaType);
        } catch (JsonProcessingException e) {
            log.error("Json转换出错", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String jsonString, TypeReference<T> javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, javaType);
        } catch (JsonProcessingException e) {
            log.error("Json转换出错", e);
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("rawtypes")
    public static <T> Iterable<T> fromJsonToCollection(String jsonString, Class<? extends Iterable> collectionClass, Class<T> clazz) {
        if (jsonString.startsWith("[")) {
            return fromJson(jsonString, constructParametricType(collectionClass, clazz));
        } else {
            return fromJsonToCollection("[" + jsonString + "]", collectionClass, clazz);
        }
    }

    public static <T> List<T> fromJsonToList(String jsonString, Class<T> clazz) {
        return (List<T>) fromJsonToCollection(jsonString, List.class, clazz);
    }
}
