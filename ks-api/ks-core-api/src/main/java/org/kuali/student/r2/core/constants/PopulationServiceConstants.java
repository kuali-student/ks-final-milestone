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
import org.kuali.student.r2.core.population.service.PopulationService;

/**
 * This class holds the constants used by the Population service.
 *
 * @author tom
 */
public class PopulationServiceConstants {

    /**
     * Reference Object URI's
     */
    public static final String SERVICE_NAME_LOCAL_PART = PopulationService.class.getSimpleName ();
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "population";
    public static final String REF_OBJECT_URI_POPULATION = NAMESPACE + "/" + PopulationInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_POPULATION_RULE = NAMESPACE + "/" + PopulationRuleInfo.class.getSimpleName();
    //////////////////////////////////
    // POPULATION RULE
    //////////////////////////////////
    /**
     * PopulationRule types
     */
    public static final String POPULATION_RULE_TYPE_EVERYONE_KEY = "kuali.population.rule.type.everyone";
    public static final String POPULATION_RULE_TYPE_PERSON_KEY = "kuali.population.rule.type.person";
    public static final String POPULATION_RULE_TYPE_RULE_KEY = "kuali.population.rule.type.rule";
    public static final String POPULATION_RULE_TYPE_SEARCH_KEY = "kuali.population.rule.type.search";
    public static final String POPULATION_RULE_TYPE_UNION_KEY = "kuali.population.rule.type.union";
    public static final String POPULATION_RULE_TYPE_INTERSECTION_KEY = "kuali.population.rule.type.intersection";
    public static final String POPULATION_RULE_TYPE_EXCLUSION_KEY = "kuali.population.rule.type.exclusion";

    public static final String[] POPULATION_RULE_TYPE_KEYS = {
        PopulationServiceConstants.POPULATION_RULE_TYPE_RULE_KEY,
        PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY,
        PopulationServiceConstants.POPULATION_RULE_TYPE_SEARCH_KEY,
        PopulationServiceConstants.POPULATION_RULE_TYPE_UNION_KEY,
        PopulationServiceConstants.POPULATION_RULE_TYPE_INTERSECTION_KEY,
        PopulationServiceConstants.POPULATION_RULE_TYPE_EXCLUSION_KEY
    };

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
     * known population ids/keys
     */
    public static final String EVERYONE_POPULATION_KEY = "kuali.population.everyone";
    public static final String ATHLETES_POPULATION_KEY = "kuali.population.athletes";
    public static final String DISABLED_POPULATION_KEY = "kuali.population.disabled";
    public static final String CURRENT_STUDENTS_POPULATION_KEY = "kuali.population.current.students";
    public static final String CONTINUING_STUDENTS_POPULATION_KEY = "kuali.population.continuing.students";
    public static final String NEW_STUDENTS_POPULATION_KEY = "kuali.population.new.students";
    public static final String RETURNING_STUDENTS_POPULATION_KEY = "kuali.population.returning.students";
    public static final String FRESHMAN_POPULATION_KEY = "kuali.population.freshman";
    public static final String SOPHOMORE_POPULATION_KEY = "kuali.population.sophomore";
    public static final String JUNIOR_POPULATION_KEY = "kuali.population.junior";
    public static final String SENIOR_POPULATION_KEY = "kuali.population.senior";
    public static final String UPPERCLASS_POPULATION_KEY = "kuali.population.upperclass";
    public static final String FINAL_TERM_SENIORS_POPULATION_KEY = "kuali.population.final.term.seniors";
    public static final String GRADUATE_POPULATION_KEY = "kuali.population.graduate";
    public static final String PROFESSIONAL_POPULATION_KEY = "kuali.population.professional";
    public static final String LAW_SCHOOL_STUDENTS_POPULATION_KEY = "kuali.population.law.school.students";
    public static final String UNDERGRADUATE_POPULATION_KEY = "kuali.population.undergraduate";
    public static final String FIN_AID_RECIPIENTS_POPULATION_KEY = "kuali.population.fin.aid.recipients";
    public static final String IN_A_DEGREE_GRANTING_PROGRAM_POPULATION_KEY = "kuali.population.in.a.degree.granting.program";
    public static final String NOT_IN_A_DEGREE_GRANTING_PROGRAM_POPULATION_KEY = "kuali.population.not.in.a.degree.granting.program";
    public static final String INTERNATIONAL_STUDENT_POPULATION_KEY = "kuali.population.international.student";
    public static final String SUMMER_ONLY_STUDENT_POPULATION_KEY = "kuali.population.summer.only.student";
    public static final String IN_A_PART_TIME_PROGRAM_POPULATION_KEY = "kuali.population.in.a.part-time.program";
    public static final String NORTH_CAMPUS_STUDENTS_POPULATION_KEY = "kuali.population.north.campus.students";
    public static final String SOUTH_CAMPUS_STUDENTS_POPULATION_KEY = "kuali.population.south.campus.students";
    public static final String SENIOR_CITIZENS_POPULATION_KEY = "kuali.population.senior.citizens";
    public static final String TUITION_EXEMPT_EMPLOYEES_POPULATION_KEY = "kuali.population.tuition.exempt.employees";
    public static final String TUITION_EXEMPT_OTHERS_POPULATION_KEY = "kuali.population.tuition.exempt.others";
    public static final String ODD_NUMBERED_POPULATION_KEY = "kuali.population.odd.numbered";
    public static final String EVEN_NUMBERED_POPULATION_KEY = "kuali.population.even.numbered";
}
