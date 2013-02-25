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
import org.kuali.rice.core.framework.persistence.jpa.criteria.Criteria;
import org.kuali.rice.core.framework.persistence.jpa.criteria.QueryByCriteria;
import org.kuali.student.enrollment.class1.lui.model.LuiLuiRelationEntity;
import org.kuali.student.r2.common.criteria.transform.BaseTransform;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * This class adds a join so that users can search for course offerings by code and by subject area
 *
 * @author Kuali Student Team
 */
public class CourseOfferingCriteriaTransform extends BaseTransform{

    private static final String IDENT_PROPERTY = "identifiers";
    private static final String IDENT_ALIAS = "ident";
    private static final String AO_REL_ALIAS = "aoRel";
    private static final String FO_REL_ALIAS = "foRel";
    private static final String LUI_ORG_ALIAS= "luiOrg";

    private static final String PROPERTY_LLR_LUI_ID = "lui.id";
    private static final String PROPERTY_LLR_RELATED_LUI_ID = "relatedLui.id";
    private static final String PROPERTY_CO_CODE = "courseOfferingCode";
    private static final String PROPERTY_LUI_ID = "id";
    private static final String PROPERTY_LUI_IDENT_CODE = "code";
    private static final String PROPERTY_LUI_IDENT_TYPE = "type";
    private static final String PROPERTY_CO_SUBJECT_AREA = "subjectArea";
    private static final String PROPERTY_LUI_IDENT_DIVISION = "division";
    private static final String PROPERTY_AO_ID = "aoid";
    private static final String PROPERTY_LUI_CONTENT_OWNER = "luiContentOwner";
    private static final String PROPERTY_LUI_IDENT_LNG_NAME = "longName";

    @Override
    public Predicate apply(final Predicate input, Criteria criteria) {
        if (input instanceof PropertyPathPredicate) {
            String pp = ((PropertyPathPredicate) input).getPropertyPath();
            //Apply only to the courseOfferingCode
            if (PROPERTY_CO_CODE.equals(pp)) {
                //Add a join to the ident table
                criteria.join(IDENT_PROPERTY, IDENT_ALIAS, false, true);
                //Rename the property using the alias
                Predicate codePredicate = this.createPredicate(input, getPropertyDesc(IDENT_ALIAS, PROPERTY_LUI_IDENT_CODE));
                //Make sure only official identifiers are matched
                return and(equal(getPropertyDesc(IDENT_ALIAS, PROPERTY_LUI_IDENT_TYPE), LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY), codePredicate);
            } else if (PROPERTY_LUI_IDENT_LNG_NAME.equals(pp)) {
                //Add a join to the ident table
                criteria.join(IDENT_PROPERTY, IDENT_ALIAS, false, true);
                //Rename the property using the alias
                Predicate codePredicate = this.createPredicate(input, getPropertyDesc(IDENT_ALIAS, PROPERTY_LUI_IDENT_LNG_NAME));
                //Make sure only official identifiers are matched
                return and(equal(getPropertyDesc(IDENT_ALIAS, PROPERTY_LUI_IDENT_TYPE), LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY), codePredicate);
            }
            else if(PROPERTY_CO_SUBJECT_AREA.equals(pp)){
                //Add a join to the ident table
                criteria.join(IDENT_PROPERTY, IDENT_ALIAS, false, true);
                //Rename the property using the alias
                Predicate codePredicate = this.createPredicate(input, getPropertyDesc(IDENT_ALIAS, PROPERTY_LUI_IDENT_DIVISION));
                //Make sure only official identifiers are matched
                return and(equal(getPropertyDesc(IDENT_ALIAS, PROPERTY_LUI_IDENT_TYPE), LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY), codePredicate);
            }else if(PROPERTY_AO_ID.equals(pp)){
                //This takes the aoid property and looks for COs with related Aos that have that id
                //Add two joins to dereference COLui->FOLui->AOLui
                criteria.from(LuiLuiRelationEntity.class.getSimpleName(), AO_REL_ALIAS, false);
                criteria.from(LuiLuiRelationEntity.class.getSimpleName(), FO_REL_ALIAS, false);

                //Rename the property and alias
                Predicate aoidPredicate = this.createPredicate(input, getPropertyDesc(AO_REL_ALIAS, PROPERTY_LLR_RELATED_LUI_ID));
                String s = criteria.toQuery(QueryByCriteria.QueryByCriteriaType.SELECT); //Get the query so far and add an "AND" or not
                criteria.rawJpql((s.contains(" WHERE ")?"AND ":"") + getPropertyDesc(FO_REL_ALIAS,PROPERTY_LLR_RELATED_LUI_ID) + " = " + getPropertyDesc(AO_REL_ALIAS, PROPERTY_LLR_LUI_ID) +
                        " AND " + getPropertyDesc(criteria.getAlias(),PROPERTY_LUI_ID) + " = " + getPropertyDesc(FO_REL_ALIAS, PROPERTY_LLR_LUI_ID));

                return aoidPredicate;
            }else if (PROPERTY_LUI_CONTENT_OWNER.equals(pp)){
                criteria.join(PROPERTY_LUI_CONTENT_OWNER, LUI_ORG_ALIAS, false, true);
                return this.createPredicate(input, Criteria.JPA_ALIAS_PREFIX +"'"+LUI_ORG_ALIAS+"'"+Criteria.JPA_ALIAS_SUFFIX);
            }
        }

        return input;
    }

    private String getPropertyDesc(String alias, String property){
        return Criteria.JPA_ALIAS_PREFIX + "'" + alias + "'" + Criteria.JPA_ALIAS_SUFFIX + "." + property;
    }

}