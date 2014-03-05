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
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.common.krms.exceptions.KRMSOptimisticLockingException;
import org.kuali.student.lum.lu.ui.krms.dto.CluSetInformation;
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
public class ProgramComponentBuilder extends CluComponentBuilder {

    @Override
    public List<String> getComponentIds() {
        return null;
    }

    @Override
    public void resolveTermParameters(LUPropositionEditor propositionEditor, Map<String, String> termParameters) {
        String cluSetId = termParameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_PROGRAM_CLUSET_KEY);
        if (cluSetId != null) {
            try {
                CluSetInformation cluSetInfo = this.getCluInfoHelper().getCluSetInformation(cluSetId);
                propositionEditor.setProgCluSet(cluSetInfo);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public Map<String, String> buildTermParameters(LUPropositionEditor propositionEditor) {
        Map<String, String> termParameters = new HashMap<String, String>();
        if (propositionEditor.getProgCluSet() != null) {
            if (propositionEditor.getProgCluSet().getCluSetInfo() != null) {
                termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_PROGRAM_CLUSET_KEY, propositionEditor.getProgCluSet().getCluSetInfo().getId());
            } else {
                termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_PROGRAM_CLUSET_KEY, null);
            }
        }

        return termParameters;
    }

    @Override
    public void onSubmit(LUPropositionEditor propositionEditor) {
        //Create the courseset
        try {
            if (propositionEditor.getProgCluSet()!= null) {
                propositionEditor.getProgCluSet().setCluSetInfo(this.buildCourseSet(propositionEditor.getProgCluSet()));
                CluSetInfo cluSetInfo = propositionEditor.getProgCluSet().getCluSetInfo();
                if (cluSetInfo.getId() == null) {
                    cluSetInfo = this.getCluService().createCluSet(cluSetInfo.getTypeKey(), cluSetInfo, ContextUtils.getContextInfo());

                    ComponentBuilderUtils.updateTermParameter(propositionEditor.getTerm(), KSKRMSServiceConstants.TERM_PARAMETER_TYPE_PROGRAM_CLUSET_KEY, cluSetInfo.getId());
                    TermDefinition.Builder termBuilder = TermDefinition.Builder.create(propositionEditor.getTerm());
                    PropositionTreeUtil.getTermParameter(propositionEditor.getParameters()).setTermValue(termBuilder.build());

                } else {
                    this.getCluService().updateCluSet(cluSetInfo.getId(), cluSetInfo, ContextUtils.getContextInfo());
                }
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
        CluSetInformation progCluSet = propositionEditor.getProgCluSet();
        if(progCluSet != null){
            if(!progCluSet.hasClus() && progCluSet.getCluSets().size()==0 ){
                String propName = PropositionTreeUtil.getBindingPath(propositionEditor, "programType");
                GlobalVariables.getMessageMap().putError(propName, LUKRMSConstants.KSKRMS_MSG_ERROR_APPROVED_PROGRAM_REQUIRED);
            }
        }
    }

    /**
     * This method build the CluSetInfo object based on the CluSetInformation wrapper object.
     *
     * Calculates if we require a wrapper cluset or not and the create sub clusets for the different types
     * of clusets required to save the individual courses of membershipqueries.
     *
     * @param programCluSetInformation
     * @return
     */
    @Override
    public CluSetInfo buildCourseSet(CluSetInformation programCluSetInformation) {

        CluSetInfo cluSetInfo = super.buildCourseSet(programCluSetInformation);
        if (cluSetInfo.getTypeKey() == null) {
            cluSetInfo.setTypeKey(CluServiceConstants.CLUSET_TYPE_CREDIT_COURSE);
        }

        boolean hasCluIds = programCluSetInformation.hasClus();

        //Set the cluset ids on the cluset
        if ((programCluSetInformation.getCluSets() == null) && (programCluSetInformation.getCluSets().isEmpty())) {
            if (hasCluIds) {
                cluSetInfo.setCluIds(programCluSetInformation.getCluIds());
                return cluSetInfo;
            }
        } else {
            for (CluSetInformation cluset : programCluSetInformation.getCluSets()) {
                cluSetInfo.getCluSetIds().add(cluset.getCluSetInfo().getId());
            }
        }

        if (hasCluIds) {
            CluSetInfo wrapperCluSet = new CluSetInfo();
            wrapperCluSet.setCluIds(programCluSetInformation.getCluIds());
            cluSetInfo.getCluSetIds().add(saveWrapperCluSet(wrapperCluSet, programCluSetInformation.getCluSetInfo()));
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
