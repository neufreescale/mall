package org.emall.user.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author gaopeng 2021/5/6
 */
@Data
@NoArgsConstructor
public class ScoreResult {

    private Integer numFound;

    private List<ScoreRecord> item;
}
