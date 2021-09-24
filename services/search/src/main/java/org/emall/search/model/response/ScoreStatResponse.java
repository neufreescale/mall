package org.emall.search.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author gaopeng 2021/9/8
 */
@Data
@RequiredArgsConstructor
@Accessors(chain = true)
public class ScoreStatResponse {

    private final String sortRangeStart;

    private List<Stat> statList;

    @Data
    @AllArgsConstructor
    public static class Stat {

        private final String schoolName;

        private final long count;

        private final String detailUrl;
    }
}
