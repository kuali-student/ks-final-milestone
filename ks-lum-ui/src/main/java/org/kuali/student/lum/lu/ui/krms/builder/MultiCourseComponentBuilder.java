/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.ui.krms.builder;

import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.builder.ComponentBuilderUtils;
import org.kuali.rice.krms.dto.TermParameterEditor;
import org.kuali.rice.krms.util.KRMSConstants;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.common.krms.exceptions.KRMSOptimisticLockingException;
import org.kuali.student.lum.lu.ui.krms.dto.CluSetRangeInformation;
import org.kuali.student.lum.lu.ui.krms.dto.CluSetWrapper;
import org.kuali.student.lum.lu.ui.krms.dto.LUPropositionEditor;
import org.kuali.student.lum.lu.ui.krms.util.LUKRMSConstants;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kuali Student Team
 */
public class MultiCourseComponentBuilder extends CluComponentBuilder {

    @Override
    public void initialize(LUPropositionEditor propositionEditor) {
        propositionEditor.setCourseSet(new CluSetWrapper());
    }

    @Override
    public List<String> getComponentIds() {
        return null;
    }

    @Override
    public void resolveTermParameters(LUPropositionEditor propositionEditor, Map<String, String> termParameters) {
        String cluSetId = termParameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY);
        if (cluSetId != null) {
            try {
                propositionEditor.setCourseSet(this.getCluInfoHelper().getCluSetWrapper(cluSetId));
                populatePropositionWrapper(propositionEditor);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public Map<String, String> buildTermParameters(LUPropositionEditor propositionEditor) {
        Map<String, String> termParameters = new HashMap<String, String>();
        if (propositionEditor.getCourseSet() != null) {
            termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY, propositionEditor.getCourseSet().getId());
        }
        if (propositionEditor.getGradeScale() != null) {
            termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GRADE_TYPE_KEY, propositionEditor.getGradeScale());
            termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GRADE_KEY, propositionEditor.getTermParameter());
        }
        return termParameters;
    }

    @Override
    public void onSubmit(LUPropositionEditor propositionEditor) {
        //Create the courseset
        try {
            CluSetInfo cluSetInfo = this.buildCourseSet(propositionEditor.getCourseSet());
            if (cluSetInfo.getId() == null) {
                cluSetInfo = this.getCluService().createCluSet(cluSetInfo.getTypeKey(), cluSetInfo, ContextUtils.getContextInfo());

                ComponentBuilderUtils.updateTermParameter(propositionEditor.getTerm(), KSKRMSServiceConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY, cluSetInfo.getId());
                TermDefinition.Builder termBuilder = TermDefinition.Builder.create(propositionEditor.getTerm());
                PropositionTreeUtil.getTermParameter(propositionEditor.getParameters()).setTermValue(termBuilder.build());

            } else {
                this.getCluService().updateCluSet(cluSetInfo.getId(), cluSetInfo, ContextUtils.getContextInfo());
            }
        } catch (Exception ex) {
            if(ex instanceof VersionMismatchException){
                throw new KRMSOptimisticLockingException();
            }else{
                throw new IllegalArgumentException(ex);
            }

        }
    }

    @Override
    public void validate(LUPropositionEditor propositionEditor) {
        CluSetWrapper cluSet = propositionEditor.getCourseSet();
        if(!cluSet.hasClus() && !cluSet.hasMembershipQuery() && cluSet.getCluSets().size()==0){
            String propName = PropositionTreeUtil.getBindingPath(propositionEditor, "multipleCourseType");
            GlobalVariables.getMessageMap().putError(propName, LUKRMSConstants.KSKRMS_MSG_ERROR_MULTICOURSE_REQUIRED);
        }

        //Add warning message if only a single clu is added to the cluset, and no other clusets or cluranges are added.
        if((cluSet.getClus().size()==1)&&(cluSet.getCluSets().isEmpty())&&(cluSet.getCluSetRanges().isEmpty())){
            GlobalVariables.getMessageMap().putWarningForSectionId(KRMSConstants.KRMS_PROPOSITION_DETAILSECTION_ID, LUKRMSConstants.KSKRMS_MSG_WARNING_MULTICOURSE_PLURAL);
        }
    }

    private void populatePropositionWrapper(LUPropositionEditor propositionEditor) {
        for (TermParameterEditor termParameterEditor : (List<TermParameterEditor>) propositionEditor.getTerm().getParameters()) {
            if (termParameterEditor.getName().equals(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GRADE_KEY)) {
                propositionEditor.setTermParameter(termParameterEditor.getValue());
            } else if (termParameterEditor.getName().equals(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_GRADE_TYPE_KEY)) {
                propositionEditor.setGradeScale(termParameterEditor.getValue());
            }
        }

    }

    /**
     * This method build the CluSetInfo object based on the CluSetInformation wrapper object.
     *
     * Calculates if we require a wrapper cluset or not and the create sub clusets for the different types
     * of clusets required to save the individual courses of membershipqueries.
     *
     * @param cluSet
     * @return
     */
    public CluSetInfo buildCourseSet(CluSetWrapper cluSet) {

        CluSetInfo cluSetInfo = super.buildCluSet(cluSet);
        if (cluSetInfo.getTypeKey() == null) {
            cluSetInfo.setTypeKey(CluServiceConstants.CLUSET_TYPE_CREDIT_COURSE);
        }

        boolean hasCluIds = cluSet.hasClus();
        int nrOfMembershipQueries = cluSet.getCluSetRanges().size();

        //Check if we require a wrapper cluset, if not set the information and return.
        if ((cluSet.getCluSets() == null) && (cluSet.getCluSets().isEmpty())) {
            if (hasCluIds && nrOfMembershipQueries==0) {
                cluSetInfo.setCluIds(cluSet.getCluIds());
                return cluSetInfo;
            } else if (!hasCluIds && nrOfMembershipQueries==1) {
                cluSetInfo.setMembershipQuery(cluSet.getCluSetRanges().get(0).getMembershipQueryInfo());
                return cluSetInfo;
            }
        } else {
            for (CluSetWrapper cluset : cluSet.getCluSets()) {
                cluSetInfo.getCluSetIds().add(cluset.getId());
            }
        }

        // Add the individual courses to its own cluset and set the cluset on the wrapper cluset.
        if (hasCluIds) {
            CluSetInfo wrapperCluSet = new CluSetInfo();
            wrapperCluSet.setCluIds(cluSet.getCluIds());
            cluSetInfo.getCluSetIds().add(saveWrapperCluSet(wrapperCluSet, cluSetInfo));
        }

        // Add the course ranges to the wrapper cluset.
        if (nrOfMembershipQueries>0) {
            for(CluSetRangeInformation cluSetRange : cluSet.getCluSetRanges()){
                CluSetInfo wrapperCluSet = new CluSetInfo();
                wrapperCluSet.setMembershipQuery(cluSetRange.getMembershipQueryInfo()); //this.convertDates(cluSetRange.getMembershipQueryInfo(),cluSetInformation ));
                cluSetInfo.getCluSetIds().add(saveWrapperCluSet(wrapperCluSet, cluSetInfo));
            }
        }

        return cluSetInfo;
    }

    /**
     * This method saves the inner cluset to the database and returns the id to add to the list
     * of clusets for the wrapper cluset.
     *
     * @param wrapperCluSet
     * @param cluSetInfo
     * @return
     */
    private String saveWrapperCluSet(CluSetInfo wrapperCluSet, CluSetInfo cluSetInfo) {

        //Set the properties to match parent cluset.
        wrapperCluSet.setAdminOrg(cluSetInfo.getAdminOrg());
        wrapperCluSet.setEffectiveDate(cluSetInfo.getEffectiveDate());
        wrapperCluSet.setExpirationDate(cluSetInfo.getExpirationDate());
        wrapperCluSet.setIsReusable(false);
        wrapperCluSet.setIsReferenceable(false);
        wrapperCluSet.setName(cluSetInfo.getName());
        wrapperCluSet.setStateKey(cluSetInfo.getStateKey());
        wrapperCluSet.setTypeKey(cluSetInfo.getTypeKey());

        try {
            if (wrapperCluSet.getId() == null) {
                wrapperCluSet = this.getCluService().createCluSet(wrapperCluSet.getTypeKey(), wrapperCluSet, ContextUtils.getContextInfo());
            } else {
                this.getCluService().updateCluSet(wrapperCluSet.getId(), wrapperCluSet, ContextUtils.getContextInfo());
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        return wrapperCluSet.getId();
    }

}
