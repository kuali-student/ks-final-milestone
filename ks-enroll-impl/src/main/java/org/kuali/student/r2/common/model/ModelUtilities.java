/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by Mezba Mahtab on 7/12/12
 */
package org.kuali.student.r2.common.model;

/**
 * This class represents some common utilities that can be performed on JPA entities.
 *
 * @author Kuali Student Team
 */
public class ModelUtilities {

    /**
     * Converts a one-char Y/N String to Boolean.
     */
    private static Boolean toBoolean(String flag) throws IllegalArgumentException {
        if ("Y".equals(flag)) { return true; }
        if ("N".equals(flag)) { return false; }
        throw new IllegalArgumentException("Flag should be Y/N");
    }

    /**
     * Converts a boolean to Y/N String.
     */
    private static String toYNString (Boolean flag) {
        if (flag) return "Y";
        return "N";
    }
}
