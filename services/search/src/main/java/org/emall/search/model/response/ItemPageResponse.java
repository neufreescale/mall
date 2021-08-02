package org.emall.search.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.emall.search.model.domain.Item;

import java.util.List;

/**
 * @author gaopeng 2021/7/28
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ItemPageResponse {

    private Long total;

    private List<Item> items;
}
