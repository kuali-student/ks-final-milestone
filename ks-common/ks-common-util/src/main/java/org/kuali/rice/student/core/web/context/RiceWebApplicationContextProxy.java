/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.rice.student.core.web.context;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.web.context.WebApplicationContext;

public class RiceWebApplicationContextProxy implements WebApplicationContext {
    
    private ApplicationContext applicationContext;
    private ServletContext servletContext;


    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
    
    @Override
    public boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return applicationContext.containsBeanDefinition(beanName);
    }

    @Override
    public boolean containsLocalBean(String name) {
        return applicationContext.containsLocalBean(name);
    }

    @Override
    public String[] getAliases(String name) {
        return applicationContext.getAliases(name);
    }

    @Override
    public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
        return applicationContext.getAutowireCapableBeanFactory();
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(name, requiredType);
    }

    @Override
    public Object getBean(String name, Object[] args) throws BeansException {
        return applicationContext.getBean(name, args);
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

    @Override
    public int getBeanDefinitionCount() {
        return applicationContext.getBeanDefinitionCount();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return applicationContext.getBeanDefinitionNames();
    }

    @Override
    public String[] getBeanNamesForType(Class type, boolean includeNonSingletons, boolean allowEagerInit) {
        return applicationContext.getBeanNamesForType(type, includeNonSingletons, allowEagerInit);
    }

    @Override
    public String[] getBeanNamesForType(Class type) {
        return applicationContext.getBeanNamesForType(type);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException {
        return applicationContext.getBeansOfType(type, includeNonSingletons, allowEagerInit);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return applicationContext.getBeansOfType(type);
    }

    @Override
    public ClassLoader getClassLoader() {
        return applicationContext.getClassLoader();
    }

    @Override
    public String getDisplayName() {
        return applicationContext.getDisplayName();
    }

    @Override
    public String getId() {
        return applicationContext.getId();
    }

    @Override
    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        return applicationContext.getMessage(resolvable, locale);
    }

    @Override
    public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        return applicationContext.getMessage(code, args, locale);
    }

    @Override
    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return applicationContext.getMessage(code, args, defaultMessage, locale);
    }

    @Override
    public ApplicationContext getParent() {
        return applicationContext.getParent();
    }

    @Override
    public BeanFactory getParentBeanFactory() {
        return applicationContext.getParentBeanFactory();
    }

    @Override
    public Resource getResource(String location) {
        return applicationContext.getResource(location);
    }

    @Override
    public Resource[] getResources(String locationPattern) throws IOException {
        return applicationContext.getResources(locationPattern);
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public long getStartupDate() {
        return applicationContext.getStartupDate();
    }

    @Override
    public Class getType(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.getType(name);
    }

    @Override
    public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.isPrototype(name);
    }

    @Override
    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.isSingleton(name);
    }

    @Override
    public boolean isTypeMatch(String name, Class targetType) throws NoSuchBeanDefinitionException {
        return applicationContext.isTypeMatch(name, targetType);
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationContext.publishEvent(event);
    }

    /*
     * Stolen from SimpleJndiBeanFactory
     * TODO - is there a refactor candidacy here?
     */
	@Override
	public <T> T getBean(Class<T> requiredType) throws BeansException {
		return getBean(requiredType.getSimpleName(), requiredType);
	}
	
    /*
     * Adapted from StaticListableBeanFactory
     * TODO - is there a refactor candidacy here?
     */
	@Override
	public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {

		Map<String, Object> results = new LinkedHashMap<String, Object>();
		for (String beanName : this.applicationContext.getBeanDefinitionNames()) {
			if (findAnnotationOnBean(beanName, annotationType) != null) {
				results.put(beanName, getBean(beanName));
			}
		}
		return results;
	}
	
    /*
     * Stolen from StaticListableBeanFactory
     * TODO - is there a refactor candidacy here?
     */
	@Override
	public <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) {
		return AnnotationUtils.findAnnotation(getType(beanName), annotationType);
	}

    @Override
    public Environment getEnvironment() {
        return applicationContext.getEnvironment();
    }
}
