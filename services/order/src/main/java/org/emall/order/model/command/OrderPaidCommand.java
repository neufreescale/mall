package org.emall.order.model.command;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author gaopeng 2021/2/24
 */
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
public class OrderPaidCommand extends OrderCommand {

}
