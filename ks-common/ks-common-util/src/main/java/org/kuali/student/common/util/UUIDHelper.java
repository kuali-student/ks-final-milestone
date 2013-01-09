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

package org.kuali.student.common.util;


import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UUIDHelper {
	
    private static final Logger LOG = Logger.getLogger(UUIDHelper.class);
	private static final String UUID_PATTERN = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";
	public static String genStringUUID() {
		return java.util.UUID.randomUUID().toString();
	}

	public static String genStringUUID(String originalUUID) {
		if (originalUUID != null && !originalUUID.isEmpty()) {
			try {
				return java.util.UUID.fromString(originalUUID).toString();
			} catch (IllegalArgumentException e) {
				LOG.info("Given ID \""+originalUUID+"\" is not a valid UUID. ");
			}
			return originalUUID;
		}
		return genStringUUID();
	}

    /**
     * Determines whether str is a UUID string or not (based on an answer in a website I found)
     * @param str Input string
     * @return true, if it is a UUID
     */
    public static boolean isUUID(String str) {
        if (str == null) {
            return false;
        }
        Pattern p = Pattern.compile(UUID_PATTERN);
        Matcher m = p.matcher(str.toLowerCase());
        return m.matches();
    }
}
