/*
 * Copyright 2014 Kuali Foundation Licensed under the
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
package org.kuali.student.common.object;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import org.kuali.student.r2.common.exceptions.DoesNotExistException;

/**
 * @author Kuali Student Team (ks.collab@kuali.org)
 *
 */
public final class KSClassUtils {

	/**
	 * 
	 */
	private KSClassUtils() {
	}
	
	/**
	 * Test if the target class has any classes or interfaces in its heirarchy matching the instanceof class.
	 *  
	 * @param target
	 * @param instanceOf
	 * @return true if there is a match false otherwise.
	 */
	public static boolean targetIsInstanceOf(Class<?>target, Class<?>instanceOf) {
		
		Set<Class<?>> targetClasses = getClassHierarchy(target);
		
		if (targetClasses.contains(instanceOf))
			return true; // matched
		
		// no match
		return false;
	}

	/**
	 * Walk the class hierarchy and aggregate into a Set<Class<?>>
	 * 
	 * @param target
	 * @return
	 */
	private static Set<Class<?>> getClassHierarchy(Class<?> target) {
		
		Set<Class<?>> classes = new HashSet<Class<?>>();
		
		Class<?>currentClass = target;
		
		while (currentClass != null) {
			
			classes.add(currentClass);
			
			// add in any implemented interfaces at this level of the class hierarchy
			for (Class<?> interfaceClass : currentClass.getInterfaces()) {
				classes.add(interfaceClass);
			}
			
			// move up the class hierarchy
			currentClass = currentClass.getSuperclass();
		}
			
		return classes;
		
		
	}

	/**
	 * Find and return the instance exception from the target cause stack.
	 * 
	 * We return the actual instance because it can be useful if you want to rethrow it.
	 * 
	 * @param target
	 * @param instanceOf
	 * @return the identified exception that matches instanceof.
	 */
	public static Throwable extractInstanceOf(Throwable target,
			Class<? extends Throwable> instanceOf) {

		Throwable currentException = target;
		
		while (currentException != null) {
			
			if (currentException.getClass().equals(instanceOf))
				return currentException;
		
			currentException = currentException.getCause();
		}
		
		return null;
		
	}

}
