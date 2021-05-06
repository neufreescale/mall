package org.emall.user.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.emall.user.model.entity.Score;

import java.util.List;

/**
 * @author gaopeng 2021/5/6
 */
@Data
@NoArgsConstructor
public class ScoreResult {

    private Integer numFound;

    private List<Score> item;
}
