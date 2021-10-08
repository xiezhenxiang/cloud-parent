package indi.shine.web.init;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.ClassUtils;
import java.io.IOException;
import java.util.Properties;

/**
 * 初始化环境变量
 * @author xiezhenxiang 2020/3/17
 */
public class InitEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources propertySources = environment.getPropertySources();
        String path = this.getClass().getResource("").getPath();
        if (path.contains("file:")) {
            path = path.replaceFirst("file:", "jar:file:");
        } else {
            path = "file:" + path;
        }
        path = path.replace(ClassUtils.getPackageName(this.getClass())
                .replace(".", "/"), "META-INF");
        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(new UrlResource(path + "env-init.properties"));
            propertySources.addLast(new PropertiesPropertySource("env-init", properties));
        } catch (IOException e) {
            throw new IllegalArgumentException("cant not load env-init.properties!");
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
