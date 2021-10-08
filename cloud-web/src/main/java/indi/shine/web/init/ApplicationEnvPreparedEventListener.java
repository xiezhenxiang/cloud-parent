package indi.shine.web.init;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;

/**
 * @author xiezhenxiang 2021/9/10
 */
public class ApplicationEnvPreparedEventListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent>, Ordered {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {

        ConfigurableEnvironment env = event.getEnvironment();
        // init log4j properties
        String appName = env.getProperty("spring.application.name");
        String logPath = env.getProperty("logging.file.path");
        String logPattern = env.getProperty("logging.pattern");
        String logLevel = env.getProperty("logging.level.root");
        Assert.notNull(logPath, "default value not set for 'logging.file.path'");
        Assert.notNull(logPattern, "default value not set for 'logging.pattern'");
        Assert.notNull(logLevel, "default value not set for 'logging.level.root'");
        if (logPath.endsWith("/")) {
            logPath = logPath.substring(0, logPath.length() - 1);
        }
        ThreadContext.put("appName", appName);
        ThreadContext.put("logPath", logPath + "/" + appName);
        ThreadContext.put("logPattern", logPattern);
        ThreadContext.put("logLevel", logLevel);
    }

    @Override
    public int getOrder() {
        return -2147483630;
    }
}
