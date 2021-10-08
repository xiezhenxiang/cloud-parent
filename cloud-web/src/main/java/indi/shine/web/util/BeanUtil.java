package indi.shine.web.util;

import org.springframework.context.ApplicationContext;

public class BeanUtil {

    private static ApplicationContext ac;

    public static Object getBean(String name) {
        checkApplicationContext();
        return ac.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        checkApplicationContext();
        return ac.getBean(name, clazz);
    }

    public static <T> T getBean(Class<T> clazz) {
        checkApplicationContext();
        return ac.getBean(clazz);
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ac = applicationContext;
    }

    private static void checkApplicationContext() {
        if (ac == null) {
            throw new IllegalStateException("applicationContext not inject yet");
        }
    }
}