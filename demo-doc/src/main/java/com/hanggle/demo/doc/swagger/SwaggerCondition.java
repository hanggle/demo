package com.hanggle.demo.doc.swagger;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Arrays;

/**
 * @description: 配置何时启动swagger
 * @author: hanggle
 * @date: 2019/8/18
 */
public class SwaggerCondition implements Condition {
    /**
     * 只在开发环境时swagger生效
     *
     * @param conditionContext
     * @param annotatedTypeMetadata
     * @return
     */
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String[] activeProfiles = conditionContext.getEnvironment().getActiveProfiles();
        return activeProfiles != null && Arrays.asList(activeProfiles).contains("dev");
    }
}
