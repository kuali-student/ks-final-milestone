/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by venkat on 8/6/14
 */
package org.kuali.student.cm.course.service.impl;

import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.PropositionParameterEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.RuleManagementWrapper;
import org.kuali.rice.krms.dto.TermEditor;
import org.kuali.rice.krms.dto.TermParameterEditor;
import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;
import org.kuali.student.cm.course.service.CourseCopyHelper;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.lum.clu.dto.AffiliatedOrgInfo;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.r2.lum.course.dto.CourseFeeInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.CourseJointInfo;
import org.kuali.student.r2.lum.course.dto.CourseRevenueInfo;
import org.kuali.student.r2.lum.course.dto.CourseVariationInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.List;

/**
 *
 * A helper class to copy course. Configured at ks-lu-no-tx-context.xml and export
 * to service bus. This is to support institutionally configurable the ignore properties
 * on copy and allows institutions to customize if they want to change the out of the box
 * functionality
 *
 * @author Kuali Student Team
 */
public class CourseCopyHelperImpl implements CourseCopyHelper {

    private List<String> ignoreProperties;

    public CourseCopyHelperImpl() {}

    /**
     * This method populates all the data from source to target excluding the properties
     * configured at 'ignoreProperties'. This method uses #BeanUtils.copyProperties() to copy
     * properties.
     *
     * @param source
     * @param target
     */
    @Override
    public void copyCourse(CourseInfo source, CourseInfo target){

        Object[] propertiesToSkip = (Object[]) getIgnoreProperties().toArray();
        String[] stringArray = Arrays.copyOf(propertiesToSkip, propertiesToSkip.length, String[].class);

        BeanUtils.copyProperties(source, target, stringArray);

        target.setCourseTitle("Copy of " + source.getCourseTitle());

        resetCourse(target);
    }

    @Override
    public void cleanUpCourseWrapperOnCopy(CourseInfoWrapper target){


        target.setRefObjectId(null);
        
//        CourseCreateUnitsContentOwner newCourseCreateUnitsContentOwner = new CourseCreateUnitsContentOwner();
//        newCourseCreateUnitsContentOwner.getRenderHelper().setNewRow(true);
//        target.getUnitsContentOwner().add(newCourseCreateUnitsContentOwner);

        resetCourse(target.getCourseInfo());
        resetRequisites(target);

    }

    /**
     * Resets a CourseInfo so that it can be persisted as a new entity.
     *
     * @param course The CourseInfo to reset.
     */
    protected void resetCourse(CourseInfo course) {
        //  Clobber the IDs
        course.setId(null);
        course.setVersion(null);
        course.setMeta(null);
        course.setEffectiveDate(null);
        course.setExpirationDate(null);

        //  Fix the state. Courses start in state draft.
        course.setStateKey(DtoConstants.STATE_DRAFT);

        //  Clobber the IDs in these collections.
        for (AttributeInfo attribute : course.getAttributes()) {
            attribute.setId(null);
        }

        for (CourseJointInfo joint : course.getJoints()) {
            joint.setRelationId(null);
        }

        for (LoDisplayInfo lo : course.getCourseSpecificLOs()) {
            recursivelyClobberLoIds(lo);
        }

        for (CourseCrossListingInfo crossListing : course.getCrossListings()) {
            crossListing.setId(null);
        }

        for (FormatInfo format : course.getFormats()) {
            format.setId(null);
            format.setMeta(null);
            for (ActivityInfo activity : format.getActivities()) {
                activity.setId(null);
                activity.setMeta(null);
            }
        }

        for (AffiliatedOrgInfo orgInfo : course.getExpenditure().getAffiliatedOrgs()) {
            orgInfo.setId(null);
        }

        for (CourseFeeInfo fee : course.getFees()) {
            fee.setId(null);
        }

        for (CourseRevenueInfo revenue : course.getRevenues()) {
            revenue.setId(null);
            for (AffiliatedOrgInfo orgInfo : revenue.getAffiliatedOrgs()) {
                orgInfo.setId(null);
            }
        }

        for (CourseVariationInfo variation : course.getVariations()) {
            variation.setId(null);
        }
    }

    protected void recursivelyClobberLoIds(LoDisplayInfo lo) {
        lo.getLoInfo().setId(null);
        for (LoDisplayInfo nestedLo : lo.getLoDisplayInfoList()) {
            recursivelyClobberLoIds(nestedLo);
        }
    }

    /**
     * Removes IDs and other properties from the data object so that new entities are created on persist.
     */
    public void resetRequisites(RuleManagementWrapper dataObject) {
        for (AgendaEditor agenda : dataObject.getAgendas()) {
            agenda.setContextId(null);
            agenda.setFirstItemId(null);
            agenda.setId(null);
            agenda.setVersionNumber(null);
            agenda.setName(null);

            for (RuleEditor re : agenda.getRuleEditors().values()) {
                re.setId(null);
                re.setVersionNumber(null);
                re.setName(null);
                re.setPropId(null);

                PropositionEditor pe = re.getPropositionEditor();
                if (pe != null) {
                    pe.setId(null);
                    pe.setRuleId(null);
                    pe.setVersionNumber(null);

                    for (PropositionParameterEditor parameter : pe.getParameters()) {
                        parameter.setId(null);
                        parameter.setVersionNumber(null);
                        parameter.setPropId(null);
                    }

                    TermEditor te = pe.getTerm();
                    if (te != null) {
                        te.setId(null);
                        te.setVersionNumber(null);

                        for (TermParameterEditor parameter : te.getEditorParameters()) {
                            parameter.setId(null);
                            parameter.setVersionNumber(null);
                            parameter.setTermId(null);
                        }
                    }
                }
            }
        }
    }

    public List<String> getIgnoreProperties() {
        return ignoreProperties;
    }

    public void setIgnoreProperties(List<String> ignoreProperties) {
        this.ignoreProperties = ignoreProperties;
    }
}
