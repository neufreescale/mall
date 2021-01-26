package org.diwayou.jdbc;

import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.infra.hint.HintManager;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author gaopeng 2021/1/20
 */
public class ShardingsphereRWRouter {

    private static final String MASTER_PREFIX = "master";

    public Object determineReadOrWriteDB(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        if (StringUtils.startsWith(methodName, MASTER_PREFIX)) {
            HintManager.getInstance().setPrimaryRouteOnly();
        }
        return pjp.proceed();
    }
}
