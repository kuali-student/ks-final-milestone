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
 * Created by Daniel on 5/30/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.criteria.transform;

import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PropertyPathPredicate;
import org.kuali.rice.core.api.criteria.SingleValuedPredicate;
import org.kuali.rice.core.framework.persistence.jpa.criteria.Criteria;
import org.kuali.student.r2.common.criteria.transform.BaseTransform;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * This class adds a join so that users can search for course offerings by code and by subject area
 *
 * @author Kuali Student Team
 */
public class CourseOfferingTransform extends BaseTransform{

    private static final String IDENT_PROPERTY = "identifiers";
    private static final String IDENT_ALIAS = "ident";
    private static final String PROPERTY_CO_CODE = "courseOfferingCode";
    private static final String PROPERTY_LUI_IDENT_CODE = "code";
    private static final String PROPERTY_LUI_IDENT_TYPE = "type";
    private static final String PROPERTY_CO_SUBJECT_AREA = "subjectArea";
    private static final String PROPERTY_LUI_IDENT_DIVISION = "division";

    @Override
    public Predicate apply(final Predicate input, Criteria criteria) {
        if (input instanceof PropertyPathPredicate) {
            String pp = ((PropertyPathPredicate) input).getPropertyPath();
            //Apply only to the courseOfferingCode
            if (PROPERTY_CO_CODE.equals(pp)) {
                //Add a join to the ident table
                criteria.join(IDENT_PROPERTY, IDENT_ALIAS, false, true);
                //Rename the property using the alias
                Predicate codePredicate = this.createPredicate(input, getPropertyDesc() + PROPERTY_LUI_IDENT_CODE);
                //Make sure only official identifiers are matched
                return and(equal(getPropertyDesc() + PROPERTY_LUI_IDENT_TYPE, LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY), codePredicate);
            }else if(PROPERTY_CO_SUBJECT_AREA.equals(pp)){
                //Add a join to the ident table
                criteria.join(IDENT_PROPERTY, IDENT_ALIAS, false, true);
                //Rename the property using the alias
                Predicate codePredicate = this.createPredicate(input, getPropertyDesc() + PROPERTY_LUI_IDENT_DIVISION);
                //Make sure only official identifiers are matched
                return and(equal(getPropertyDesc() + PROPERTY_LUI_IDENT_TYPE, LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY), codePredicate);
            }
        }

        return input;
    }

    private String getPropertyDesc(){
        return Criteria.JPA_ALIAS_PREFIX + "'" + IDENT_ALIAS + "'" + Criteria.JPA_ALIAS_SUFFIX + ".";
    }

}