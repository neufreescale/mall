package org.diwayou.mq.transaction;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author gaopeng 2021/2/4
 */
@Data
@RequiredArgsConstructor
@Accessors(chain = true)
public class MqTransactionContext {

    @NonNull
    private final Runnable callback;
}
