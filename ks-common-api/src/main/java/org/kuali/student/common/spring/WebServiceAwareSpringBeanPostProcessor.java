/*
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.spring;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.util.ReflectionUtils;

/**
 * 
 * A Spring Bean PostProcessor designed for automatic lookup of auto wired
 * dependencies through the Rice GlobalResourceLoader.
 * 
 * In Kuali Student when we create services they are registered onto the Kuali
 * Service Bus where clients like the KRAD UI needs to consume them from.
 * 
 * This is a helper for resolving the services through the KSB using the
 * namespace and local name defined on the ks-api service @WebService
 * annotation.
 * 
 * @author Kuali Student Team
 * 
 */
public class WebServiceAwareSpringBeanPostProcessor extends AutowiredAnnotationBeanPostProcessor implements PriorityOrdered, BeanFactoryAware {

	private static final Logger log = LoggerFactory
			.getLogger(WebServiceAwareSpringBeanPostProcessor.class);

	/**
	 * 
	 */
	public WebServiceAwareSpringBeanPostProcessor() {
	}

	
	
	/* (non-Javadoc)
	 * @see org.springframework.core.Ordered#getOrder()
	 */
	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}



	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		
		// intentionally does nothing
		return bean;
	}

	
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter#postProcessAfterInstantiation(java.lang.Object, java.lang.String)
	 */
	@Override
	public boolean postProcessAfterInstantiation(Object bean, String beanName)
			throws BeansException {
		
		Class<? extends Object> beanClass = bean.getClass();
		
		try {
			super.processInjection(bean);
			
		} catch (BeansException e2) {
			// any resolved beans from the local applicationContext will have been injected into the bean by now
			// so fall through.
			// we will only update the @Autowired fields that are null and declare @WebService annotations.
		}

		Field[] fields = beanClass.getDeclaredFields();

		for (Field field : fields) {

			// check for the @Autowired, @Resource, or @Value annotation
			if (!fieldHasInjectionAnnotation(field))
				continue; // skip fields that don't have an injection annotation

			String fieldName = field.getName();

			String setterMethodName = "set" + StringUtils.capitalize(fieldName);

			Object currentValue = null;
			try {

				ReflectionUtils.makeAccessible(field);

				currentValue = field.get(bean);

			} catch (IllegalArgumentException e1) {
				log.warn("get error", e1);
				continue;

			} catch (SecurityException e1) {
				log.warn("get error", e1);
				continue;
			} catch (IllegalAccessException e1) {
				log.warn("get error", e1);
				continue;
			}

			// Only resolve using KSB if the object value has not been set.
			if (currentValue != null)
				continue; // was handled already

			Class<?> fieldType = field.getType();
			
			WebService webServiceAnnotation = (WebService) fieldType.getAnnotation(
					WebService.class);
			
			if (webServiceAnnotation != null) {

				// we can only auto resolve if the @WebService annotation is
				// present.

				
				String namespace = webServiceAnnotation.targetNamespace();
				String serviceName = webServiceAnnotation.serviceName();

				if (namespace != null) {

					final QName name = new QName(namespace, serviceName);

					try {

						Object service = null;
						
						try {
								SerializableProxyInvokationHandler invocationHandler = new SerializableProxyInvokationHandler();
								
								invocationHandler.setServiceName(name);
								
								service = Proxy
										.newProxyInstance(
												getClass().getClassLoader(),
												new Class[] { fieldType },
												invocationHandler);

							
								
						} catch (NullPointerException e1) {
							log.warn("RiceResourceLoader is not configured/initialized properly.", e1);
							// skip to the next field.
							continue;
						}

						try {

							/*
							 * We use the set method because even though we can reach in and set the bean
							 * if it is final we still get an exception.
							 * This way it will always work.
							 * And since the bean's are presently being wired using XML the setters will exist.
							 * 
							 */
							Method setterMethod = beanClass.getMethod(
									setterMethodName, field.getType());

							ReflectionUtils.invokeMethod(setterMethod, bean,
									service);

							log.warn("RESOLVED: beanName = "  + beanName + ", fieldName = " + field.getName() + ", fieldType = " + fieldType.getName() + " from KSB using QName: " + name);
							
							continue;
							
						} catch (IllegalArgumentException e) {
							log.warn("set error", e);
						} catch (SecurityException e) {
							log.warn("set error", e);
						} catch (NoSuchMethodException e) {
							
							// try the field
							ReflectionUtils.makeAccessible(field);
							
							field.set(bean, service);
							log.warn("RESOLVED: beanName = "  + beanName + ", fieldName = " + field.getName() + ", fieldType = " + fieldType.getName() + " from KSB using QName: " + name);
							continue;
						}
					} catch (Exception e) {
						log.error(
								"failed to lookup resource in GlobalResourceLoader ("
										+ namespace + ", " + serviceName + ")",
								e);
						continue;
					}
				}

			}
		}
		
		// skip over any of the other post processors
		return false;
	}

	

	



	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {

		
		return bean;
	}

	private boolean fieldHasInjectionAnnotation(Field field) {

		// @Autowired, @Resource, or @Value annotation
		if (field.getAnnotation(Autowired.class) != null) {
			log.debug("detected @Autowired annotation on field: "
					+ field.getName());
			return true;
		}
		if (field.getAnnotation(Value.class) != null) {
			log.debug("detected @Value annotation on field: " + field.getName());
			return true;
		}
		if (field.getAnnotation(Resource.class) != null) {
			log.debug("detected @Resource annotation on field: "
					+ field.getName());
			return true;
		}
		if (field.getAnnotation(Inject.class) != null) {
			log.debug("detected @Inject annotation on field: "
					+ field.getName());
			return true;
		}

		// not an injectable annotation.
		return false;

	}
	
	

}
