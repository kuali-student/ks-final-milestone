/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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
package org.kuali.student.r2.core.constants;

import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;

/**
 * This class holds the constants used by the Population service.
 *
 * @author tom
 */
public class PopulationServiceConstants {

    /**
     * Reference Object URI's
     */
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "population";
    public static final String REF_OBJECT_URI_POPULATION = NAMESPACE + "/" + PopulationInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_POPULATION_RULE = NAMESPACE + "/" + PopulationRuleInfo.class.getSimpleName();

    //////////////////////////////////
    // POPULATION RULE
    //////////////////////////////////

    /**
     * PopulationRule types
     */
    public static final String POPULATION_RULE_TYPE_PERSON_KEY = "kuali.population.rule.type.person";
    public static final String POPULATION_RULE_TYPE_RULE_KEY = "kuali.population.rule.type.rule";
    public static final String POPULATION_RULE_TYPE_SEARCH_KEY = "kuali.population.rule.type.search";
    public static final String POPULATION_RULE_TYPE_UNION_KEY = "kuali.population.rule.type.union";
    public static final String POPULATION_RULE_TYPE_INTERSECTION_KEY = "kuali.population.rule.type.intersection";
    public static final String POPULATION_RULE_TYPE_EXCLUSION_KEY = "kuali.population.rule.type.exclusion";

    /**
     * States for Population Rule
     */
    public static final String POPULATION_RULE_LIFECYCLE_KEY = "kuali.population.population.rule.lifecycle";
    public static final String POPULATION_RULE_ACTIVE_STATE_KEY = "kuali.population.population.rule.state.active";
    public static final String POPULATION_RULE_INACTIVE_STATE_KEY = "kuali.population.population.rule.state.inactive";
    public static final String[] POPULATION_RULE_LIFECYCLE_KEYS = {
        POPULATION_RULE_ACTIVE_STATE_KEY,
        POPULATION_RULE_INACTIVE_STATE_KEY
    };

    //////////////////////////////////
    // POPULATION CATEGORY
    //////////////////////////////////

    /**
     * PopulationCategory types
     */
    public static final String POPULATION_CATEGORY_TYPE_KEY = "kuali.population.type.population.category";

    /**
     * States for Population Category
     */
    public static final String POPULATION_CATEGORY_LIFECYCLE_KEY = "kuali.population.population.category.lifecycle";
    public static final String POPULATION_CATEGORY_ACTIVE_STATE_KEY = "kuali.population.population.category.state.active";
    public static final String POPULATION_CATEGORY_INACTIVE_STATE_KEY = "kuali.population.population.category.state.inactive";
    public static final String[] POPULATION_CATEGORY_LIFECYCLE_KEYS = {
            POPULATION_CATEGORY_ACTIVE_STATE_KEY,
            POPULATION_CATEGORY_INACTIVE_STATE_KEY
    };

    //////////////////////////////////
    // POPULATION
    //////////////////////////////////

    /**
     * Population types
     */
    public static final String POPULATION_TYPE_KEY = "kuali.population.type.population";
    public static final String POPULATION_STUDENT_TYPE_KEY = "kuali.population.type.student";

    /**
     * States for Populations
     */
    public static final String POPULATION_LIFECYCLE_KEY = "kuali.population.population.lifecycle";
    public static final String POPULATION_ACTIVE_STATE_KEY = "kuali.population.population.state.active";
    public static final String POPULATION_INACTIVE_STATE_KEY = "kuali.population.population.state.inactive";
    public static final String[] POPULATION_LIFECYCLE_KEYS = {
        POPULATION_ACTIVE_STATE_KEY,
        POPULATION_INACTIVE_STATE_KEY
    };

    /**
     * known population keys
     */
    public static final String SUMMER_ONLY_STUDENTS_POPULATION_KEY = "kuali.population.summer.only.student";
    public static final String EVERYONE_POPULATION_KEY = "kuali.population.everyone";
}
