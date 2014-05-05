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
public class ProgramComponentBuilder extends CluComponentBuilder {


    @Override
    public void initialize(LUPropositionEditor propositionEditor) {
        propositionEditor.setProgramSet(new CluSetWrapper());
    }

    @Override
    public List<String> getComponentIds() {
        return null;
    }

    @Override
    public void resolveTermParameters(LUPropositionEditor propositionEditor, Map<String, String> termParameters) {
        String cluSetId = termParameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_PROGRAM_CLUSET_KEY);
        if (cluSetId != null) {
            try {
                propositionEditor.setProgramSet(this.getCluInfoHelper().getCluSetWrapper(cluSetId));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public Map<String, String> buildTermParameters(LUPropositionEditor propositionEditor) {
        Map<String, String> termParameters = new HashMap<String, String>();
        if (propositionEditor.getProgramSet() != null) {
            termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_PROGRAM_CLUSET_KEY, propositionEditor.getProgramSet().getId());
        }

        return termParameters;
    }

    @Override
    public void onSubmit(LUPropositionEditor propositionEditor) {
        //Create the courseset
        try {
            if (propositionEditor.getProgramSet()!= null) {
                CluSetInfo cluSetInfo = this.buildProgramSet(propositionEditor.getProgramSet());
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
        CluSetWrapper progCluSet = propositionEditor.getProgramSet();
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
     * @param programSetInformation
     * @return
     */
    public CluSetInfo buildProgramSet(CluSetWrapper programSetInformation) {

        CluSetInfo cluSetInfo = super.buildCluSet(programSetInformation);
        if (cluSetInfo.getTypeKey() == null) {
            cluSetInfo.setTypeKey(CluServiceConstants.CLUSET_TYPE_CREDIT_COURSE);
        }

        boolean hasCluIds = programSetInformation.hasClus();

        //Set the cluset ids on the cluset
        if ((programSetInformation.getCluSets() == null) && (programSetInformation.getCluSets().isEmpty())) {
            if (hasCluIds) {
                cluSetInfo.setCluIds(programSetInformation.getCluIds());
                return cluSetInfo;
            }
        } else {
            for (CluSetWrapper cluset : programSetInformation.getCluSets()) {
                cluSetInfo.getCluSetIds().add(cluset.getId());
            }
        }

        if (hasCluIds) {
            CluSetInfo wrapperCluSet = new CluSetInfo();
            wrapperCluSet.setCluIds(programSetInformation.getCluIds());
            cluSetInfo.getCluSetIds().add(saveWrapperCluSet(wrapperCluSet, cluSetInfo));
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
