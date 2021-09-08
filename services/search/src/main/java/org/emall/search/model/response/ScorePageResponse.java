package org.emall.search.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.emall.search.model.domain.Score;

import java.util.List;

/**
 * @author gaopeng 2021/9/7
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ScorePageResponse {

    private Long total;

    private List<Score> scores;
}
