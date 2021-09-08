package org.emall.user.client.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.diwayou.core.bean.Convertible;

/**
 * @author gaopeng 2021/9/7
 */
@Data
@NoArgsConstructor
public class ScoreDto implements Convertible {

    private String scName;

    private String spName;

    private Integer score;

    private Integer sort;

    private Integer year;
}
