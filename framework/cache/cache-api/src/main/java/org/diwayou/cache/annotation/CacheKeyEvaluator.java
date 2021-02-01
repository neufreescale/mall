package org.diwayou.cache.annotation;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.expression.EvaluationContext;
import org.springframework.util.StringUtils;

import static org.diwayou.cache.annotation.CacheKeyMeta.SEP;

/**
 * @author gaopeng 2021/2/1
 */
public class CacheKeyEvaluator {

    private final CacheExpressionEvaluator evaluator = new CacheExpressionEvaluator();

    public String generateKey(CacheKeyMeta keyMeta, Object target, Object[] args, BeanFactory beanFactory) {
        if (StringUtils.hasText(keyMeta.getExpress())) {
            EvaluationContext evaluationContext = createEvaluationContext(keyMeta, target, args, beanFactory);

            return keyMeta.getKey() + SEP + evaluator.key(keyMeta.getExpress(), keyMeta.getMethodKey(), evaluationContext);
        }

        return getKey(keyMeta.getKey(), args);
    }

    private EvaluationContext createEvaluationContext(CacheKeyMeta keyMeta, Object target, Object[] args, BeanFactory beanFactory) {
        return evaluator.createEvaluationContext(keyMeta.getMethod(), args, target, keyMeta.getTargetClass(),
                keyMeta.getTargetMethod(), beanFactory);
    }

    private String getKey(String key, Object args[]) {
        if (args != null) {
            StringBuilder keyBuilder = new StringBuilder(key);
            for (Object arg : args) {
                // 忽略复杂类型
                if (arg != null && !BeanUtils.isSimpleValueType(arg.getClass())) {
                    arg = null;
                }

                keyBuilder.append(SEP).append(arg);
            }
            key = keyBuilder.toString();
        }

        return key;
    }
}
