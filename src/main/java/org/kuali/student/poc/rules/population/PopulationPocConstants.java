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

import java.util.Arrays;
import java.util.List;

/**
 * This class holds some constants across the Population POC.
 *
 * @author Mezba Mahtab
 */
public class PopulationPocConstants {

    public static final String AFFILIATION_TYPE_STUDENT = "kuali.student.poc.rules.population.affiliation.type.student";
    public static final String AFFILIATION_TYPE_INSTRUCTOR = "kuali.student.poc.rules.population.affiliation.type.instructor";

    public static final String PROGRAM_LEVEL_UNDERGRADUATE = "kuali.student.poc.rules.population.program.level.undergraduate";
    public static final String PROGRAM_LEVEL_GRADUATE = "kuali.student.poc.rules.population.program.level.graduate";

    public static final String PROGRAM_BACHELOR_OF_SCIENCE = "kuali.student.poc.rules.population.program.name.bsc";
    public static final String PROGRAM_BACHELOR_OF_ARTS = "kuali.student.poc.rules.population.program.name.ba";
    public static final String PROGRAM_MASTER_OF_SCIENCE = "kuali.student.poc.rules.population.program.name.msc";
    public static final String PROGRAM_MASTER_OF_ARTS = "kuali.student.poc.rules.population.program.name.ma";

    public static final String MAX_CREDITS_IN_PROGRAM_UNIVERSITY_OF_TORONTO_UNDERGRADUATE = "20";
    public static final String MAX_CREDITS_IN_PROGRAM_UNIVERSITY_OF_UTAH_UNDERGRADUATE = "122";
    public static final String MAX_CREDITS_IN_PROGRAM_MIT_UNDERGRADUATE = "360";

    public static final List<String> YEARS_OF_STUDY_LABELS_UNDERGRADUATE_USA = Arrays.asList("freshman", "sophomore", "junior", "senior");
    public static final List<String> YEARS_OF_STUDY_LABELS_UNDERGRADUATE_CANADA = Arrays.asList("1", "2", "3", "4");
    public static final List<String> YEARS_OF_STUDY_LABELS_GRADUATE_MASTERS = Arrays.asList("1", "2");
    public static final List<String> YEARS_OF_STUDY_LABELS_GRADUATE_DOCTORS = Arrays.asList("1", "2", "3");

}
