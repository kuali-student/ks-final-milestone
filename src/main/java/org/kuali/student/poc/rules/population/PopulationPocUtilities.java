/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by mahtabme on 12/9/13
 */
package org.kuali.student.poc.rules.population;

/**
 * This class holds some basic utilties for the POC.
 *
 * @author Mezba Mahtab
 */
public class PopulationPocUtilities {

    /**
     * Gets a properly formatted name from various parts of the name.
     */
    public static String getFormattedName(String firstName, String middleName, String lastName, String suffix) {
        return firstName + (middleName != null?" " + middleName:"") + lastName + (suffix!=null?" " + suffix:"");
    }
}
