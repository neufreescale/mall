package org.emall.search.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Collection;

/**
 * @author gaopeng 2021/8/13
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ItemSuggestResponse {

    private Collection<String> suggest;
}
