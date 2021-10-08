package indi.shine.web.init;

import indi.shine.web.util.BeanUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author xiezhenxiang 2021/9/10
 */
public class ApplicationContentRefreshEventListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        BeanUtil.setApplicationContext(contextRefreshedEvent.getApplicationContext());
    }
}
