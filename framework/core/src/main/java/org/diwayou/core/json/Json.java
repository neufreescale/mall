package org.diwayou.core.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author gaopeng 2021/1/15
 */
@Slf4j
public class Json {

    private ObjectMapper mapper;

    public Json(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> T fromJson(String jsonString, Type type) throws IOException {
        return mapper.readValue(jsonString, TypeFactory.defaultInstance().constructType(type));
    }

    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            log.error("Json转换出错", e);
            return null;
        }
    }

    public JavaType constructParametricType(Class<?> parametrized, Class<?>... parameterClasses) {
        return mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
    }

    public String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            log.error("Json转换出错", e);
            return null;
        }
    }

    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return mapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            log.error("Json转换出错", e);
            return null;
        }
    }

    public <T> T fromJson(String jsonString, TypeReference<T> javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return mapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            log.error("Json转换出错", e);
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    public <T> Iterable<T> fromJsonToCollection(String jsonString, Class<? extends Iterable> collectionClass, Class<T> clazz) {
        if (jsonString.startsWith("[")) {
            return fromJson(jsonString, constructParametricType(collectionClass, clazz));
        } else {
            return fromJsonToCollection("[" + jsonString + "]", collectionClass, clazz);
        }
    }

    public <T> List<T> fromJsonToList(String jsonString, Class<T> clazz) {
        return (List<T>) this.fromJsonToCollection(jsonString, List.class, clazz);
    }
}
