/*
 * Copyright 2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.object;

/**
 * @author Kuali Student Team 
 *
 */
public final class KSObjectUtils {

	/**
	 * 
	 */
	private KSObjectUtils() {
		// intentionally hidden
	}
	
	/**
	 * Null-Safe comparison of two Boolean fields.
	 * 
	 * @param source Boolean to compare against the target.
	 * @param target Boolean to compare against the source.
	 * @return true if both are null or both are equal; false otherwise.
	 */
	public static boolean nullSafeBooleanEquals (Boolean source, Boolean target) {
		return nullSafeObjectEquals(source, target);
	}
	
	/**
	 * Null-Safe comparison of two Integer fields.
	 * 
	 * @param source Integer to compare against the target.
	 * @param target Integer to compare against the source.
	 * @return true if both are null or both are equal; false otherwise.
	 */
	public static boolean nullSafeIntegerEquals (Integer source, Integer target) {
		return nullSafeObjectEquals(source, target);
	}
	
	private static boolean nullSafeObjectEquals (Object source, Object target) {
		if (source == null && target == null) {
			// both are null
			return true;
		}
		else if (source != null && target != null){
			return source.equals(target);
		}
		else {
			// one is null
			return false;
		}
	}

    /**
     * Find the original cause exception of the given throwable
     */
    public static Throwable unwrapException(int maxDepth, Throwable e) {
        if ((maxDepth > 0) && (e.getCause() != null)) {
            return unwrapException(--maxDepth, e.getCause());
        }
        return e;
    }

}
