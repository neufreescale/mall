package org.diwayou.core.util;

import org.diwayou.core.json.Json;
import org.diwayou.core.result.ResultWrapper;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author gaopeng 2021/3/2
 */
public class WebResponseUtil {

    public static void writeJson(HttpServletResponse response, ResultWrapper result) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        StreamUtils.copy(Json.toJson(result), StandardCharsets.UTF_8, response.getOutputStream());
    }
}
