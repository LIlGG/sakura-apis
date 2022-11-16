package com.lixingyong.common.listener;

import java.lang.reflect.Method;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.event.ApplicationListenerMethodAdapter;

/**
 * @author LIlGG
 * @since 2022-10-27
 */
public class ApiApplicationListenerMethodAdapter extends ApplicationListenerMethodAdapter {

    private BeanFactory beanFactory;

    private final String beanName;

    /**
     * Construct a new ApplicationListenerMethodAdapter.
     *
     * @param beanName    the name of the bean to invoke the listener method on
     * @param targetClass the target class that the method is declared on
     * @param method      the listener method to invoke
     */
    public ApiApplicationListenerMethodAdapter(String beanName, Class<?> targetClass,
                                               Method method) {
        super(beanName, targetClass, method);
        this.beanName = beanName;
    }

    @Override
    protected Object getTargetBean() {
        return this.beanFactory.getBean(this.beanName);
    }

    /**
     * Initialize this instance.
     */
    void init(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }


}
