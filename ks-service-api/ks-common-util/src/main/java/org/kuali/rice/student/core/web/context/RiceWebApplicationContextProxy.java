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
    
    public boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    public boolean containsBeanDefinition(String beanName) {
        return applicationContext.containsBeanDefinition(beanName);
    }

    public boolean containsLocalBean(String name) {
        return applicationContext.containsLocalBean(name);
    }

    public String[] getAliases(String name) {
        return applicationContext.getAliases(name);
    }

    public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
        return applicationContext.getAutowireCapableBeanFactory();
    }

    public Object getBean(String name, Class requiredType) throws BeansException {
        return applicationContext.getBean(name, requiredType);
    }

    public Object getBean(String name, Object[] args) throws BeansException {
        return applicationContext.getBean(name, args);
    }

    public Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

    public int getBeanDefinitionCount() {
        return applicationContext.getBeanDefinitionCount();
    }

    public String[] getBeanDefinitionNames() {
        return applicationContext.getBeanDefinitionNames();
    }

    public String[] getBeanNamesForType(Class type, boolean includeNonSingletons, boolean allowEagerInit) {
        return applicationContext.getBeanNamesForType(type, includeNonSingletons, allowEagerInit);
    }

    public String[] getBeanNamesForType(Class type) {
        return applicationContext.getBeanNamesForType(type);
    }

    public Map getBeansOfType(Class type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException {
        return applicationContext.getBeansOfType(type, includeNonSingletons, allowEagerInit);
    }

    public Map getBeansOfType(Class type) throws BeansException {
        return applicationContext.getBeansOfType(type);
    }

    public ClassLoader getClassLoader() {
        return applicationContext.getClassLoader();
    }

    public String getDisplayName() {
        return applicationContext.getDisplayName();
    }

    public String getId() {
        return applicationContext.getId();
    }

    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        return applicationContext.getMessage(resolvable, locale);
    }

    public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        return applicationContext.getMessage(code, args, locale);
    }

    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return applicationContext.getMessage(code, args, defaultMessage, locale);
    }

    public ApplicationContext getParent() {
        return applicationContext.getParent();
    }

    public BeanFactory getParentBeanFactory() {
        return applicationContext.getParentBeanFactory();
    }

    public Resource getResource(String location) {
        return applicationContext.getResource(location);
    }

    public Resource[] getResources(String locationPattern) throws IOException {
        return applicationContext.getResources(locationPattern);
    }

    public ServletContext getServletContext() {
        return this.servletContext;
    }

    public long getStartupDate() {
        return applicationContext.getStartupDate();
    }

    public Class getType(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.getType(name);
    }

    public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.isPrototype(name);
    }

    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.isSingleton(name);
    }

    public boolean isTypeMatch(String name, Class targetType) throws NoSuchBeanDefinitionException {
        return applicationContext.isTypeMatch(name, targetType);
    }

    public void publishEvent(ApplicationEvent event) {
        applicationContext.publishEvent(event);
    }

    
}
