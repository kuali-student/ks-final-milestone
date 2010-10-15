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

public class UUIDHelper {

    private static final Logger LOG = Logger.getLogger(UUIDHelper.class);

    public static String genStringUUID() {
        return java.util.UUID.randomUUID().toString();
    }

    public static String genStringUUID(String originalUUID) {
        if (originalUUID != null && !originalUUID.isEmpty()) {
            try {
                return java.util.UUID.fromString(originalUUID).toString();
            } catch (IllegalArgumentException e) {
                LOG.warn("Given ID \"" + originalUUID + "\" is not a valid UUID. ");
            }
            return originalUUID;
        }
        return genStringUUID();
    }
}
