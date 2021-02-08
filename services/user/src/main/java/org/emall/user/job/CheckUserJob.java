package org.emall.user.job;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.diwayou.job.Job;

/**
 * @author gaopeng 2021/2/4
 */
@Job(name = "checkUserJob", overwrite = true, cron = "0 0/10 * * * ?")
@Slf4j
public class CheckUserJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("{}", shardingContext);
    }
}
