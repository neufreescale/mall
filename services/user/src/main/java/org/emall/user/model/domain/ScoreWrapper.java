package org.emall.user.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaopeng 2021/5/6
 */
@Data
@NoArgsConstructor
public class ScoreWrapper {

    private String code;

    private String message;

    private DataResult data;

    @Data
    @NoArgsConstructor
    public static class DataResult {

        private String method;

        private String text;
    }
}
