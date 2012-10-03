/**
 * 
 */
package org.kuali.student.common.spring;

import java.lang.annotation.Annotation;
import java.math.MathContext;
import java.util.HashSet;
import java.util.Set;

import javax.jws.WebService;

/**
 * @author Kuali Student
 *
 */
public final class AnnotationUtils {

	private AnnotationUtils() {
	}
	

	public static Set<Class<?>> extractInterfaceContainingAnnotation(Object bean, Class <? extends Annotation> annotation) {
		
		Class<? extends Object> beanClass = bean.getClass();
		
		Class<?>[] interfaces = beanClass.getInterfaces();
		
		Set<Class<?>>matchingInterfaces = new HashSet<Class<?>>();
		
		for (Class<?> candidateInterface : interfaces) {
			
			Annotation ws = candidateInterface.getAnnotation(annotation);
			
			if (ws != null)
				matchingInterfaces.add(candidateInterface);
		}
		
		return matchingInterfaces;
	}
	
}
