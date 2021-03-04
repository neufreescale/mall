package org.emall.user.client.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.diwayou.security.api.UserDetailsAdapter;

/**
 * @author gaopeng 2021/3/4
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Buyer implements UserDetailsAdapter {

    private Long id;

    private String name;

    @Override
    public String getUsername() {
        return name;
    }
}
