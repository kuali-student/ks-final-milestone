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

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.rice.krms.builder.ComponentBuilderUtils;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.lum.lu.ui.krms.dto.CluInformation;
import org.kuali.student.lum.lu.ui.krms.dto.LUPropositionEditor;
import org.kuali.student.lum.lu.ui.krms.dto.ProgramCluSetInformation;
import org.kuali.student.lum.lu.ui.krms.util.LUKRMSConstants;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluResultInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.dto.ResultOptionInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.xml.namespace.QName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kuali Student Team
 */
public class ProgramComponentBuilder implements ComponentBuilder<LUPropositionEditor> {


    private CluService cluService;

    private LRCService lrcService;

    @Override
    public List<String> getComponentIds() {
        return null;
    }

    @Override
    public void resolveTermParameters(LUPropositionEditor propositionEditor, Map<String, String> termParameters) {
        String cluSetId = termParameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_PROGRAM_CLUSET_KEY);
        if (cluSetId != null) {
            try {
                ProgramCluSetInformation cluSetInfo = this.getProgramCluSetInformation(cluSetId);
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

                } else {
                    this.getCluService().updateCluSet(cluSetInfo.getId(), cluSetInfo, ContextUtils.getContextInfo());
                }
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }

    }

    @Override
    public void validate(LUPropositionEditor propositionEditor) {
        ProgramCluSetInformation progCluSet = propositionEditor.getProgCluSet();
        if(progCluSet != null){
            if(!progCluSet.hasClus() && progCluSet.getCluSets().size()==0 ){
                String propName = PropositionTreeUtil.getBindingPath(propositionEditor, "programType");
                GlobalVariables.getMessageMap().putError(propName, LUKRMSConstants.KSKRMS_MSG_ERROR_APPROVED_PROGRAM_REQUIRED);
            }
        }
    }

    /**
     * This methos assumes that there can only be a maximum of one wrapped cluset
     * for cluids and one for membershipqueries.
     *
     * @param cluSetId
     * @return
     */
    public ProgramCluSetInformation getProgramCluSetInformation(String cluSetId) {

        ProgramCluSetInformation result = new ProgramCluSetInformation();
        result.setCluSetInfo(this.getCluSetInfo(cluSetId));

        List<String> cluIds = result.getCluSetInfo().getCluIds();

        // goes through the list of sub clusets and ignore the ones that are not reusable
        List<CluSetInfo> cluSetInfos = getCluSetInfos(result.getCluSetInfo().getCluSetIds());
        if (cluSetInfos != null) {
            List<CluSetInfo> unWrappedCluSets = new ArrayList<CluSetInfo>();
            for (CluSetInfo subCluSet : cluSetInfos) {
                if (subCluSet.getIsReusable()) {
                    unWrappedCluSets.add(subCluSet);
                } else {

                    //Retrieve the information from the wrapped clu cluset.
                    if (subCluSet.getCluIds() != null && !subCluSet.getCluIds().isEmpty()) {
                        cluIds = subCluSet.getCluIds();
                    }
                }
            }
            result.setCluSets(unWrappedCluSets);
        }

        result.setClus(this.getCluInformations(cluIds));

        return result;
    }

    private List<CluSetInfo> getCluSetInfos(List<String> cluSetIds) {
        List<CluSetInfo> clusetInfos = new ArrayList<CluSetInfo>();
        if (cluSetIds != null) {
            for (String cluSetId : cluSetIds) {
                clusetInfos.add(this.getCluSetInfo(cluSetId));
            }
        }
        return clusetInfos;
    }

    private CluSetInfo getCluSetInfo(String cluSetId) {
        CluSetInfo cluSetInfo = null;
        try {
            // note: the cluIds returned by cluService.getCluSetInfo also contains the clus
            //       that are the result of query parameter search.  Set to null here and
            //       retrieve the clus that are direct members.
            cluSetInfo = this.getCluService().getCluSet(cluSetId, ContextUtils.getContextInfo());
            cluSetInfo.setCluIds(this.getCluService().getCluIdsFromCluSet(cluSetId, ContextUtils.getContextInfo()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return cluSetInfo;
    }

    private List<CluInformation> getCluInformations(List<String> cluIds) {
        List<CluInformation> result = new ArrayList<CluInformation>();
        if (cluIds != null) {
            for (String cluId : cluIds) {
                try {
                    VersionDisplayInfo versionInfo = this.getCluService().getCurrentVersion(CluServiceConstants.CLU_NAMESPACE_URI, cluId, ContextUtils.getContextInfo());
                    CluInfo cluInfo = this.getCluService().getClu(versionInfo.getId(), ContextUtils.getContextInfo());
                    if (cluInfo != null) {

                        //retrieve credits
                        String credits = "";
                        List<CluResultInfo> cluResultInfos = this.getCluService().getCluResultByClu(versionInfo.getId(), ContextUtils.getContextInfo());
                        if (cluResultInfos != null) {
                            for (CluResultInfo cluResultInfo : cluResultInfos) {
                                String cluType = cluResultInfo.getTypeKey();

                                //ignore non-credit results
                                if ((cluType == null) || (!cluType.equals("kuali.resultType.creditCourseResult"))) {
                                    continue;
                                }

                                //retrieve credit type and credit values
                                ResultValuesGroupInfo resultComponentInfo = null;
                                List<String> resultValues = null;
                                String creditType = "";
                                if (cluResultInfo.getResultOptions() != null) {
                                    for (ResultOptionInfo resultOption : cluResultInfo.getResultOptions()) {
                                        if (resultOption.getResultComponentId() != null) {
                                            resultComponentInfo = this.getLrcService().getResultValuesGroup(resultOption.getResultComponentId(), ContextUtils.getContextInfo());
                                            resultValues = resultComponentInfo.getResultValueKeys();
                                            creditType = resultComponentInfo.getTypeKey();
                                            break;
                                        }
                                    }
                                }
                                if (resultValues == null) {
                                    continue;
                                }

                                if (!credits.isEmpty()) {
                                    credits = credits + "; ";
                                }

                                if (creditType.equals("kuali.result.values.group.type.fixed")) {
                                    credits = credits + resultValues.get(0).substring(33);
                                } else if (creditType.equals("kuali.result.values.group.type.multiple")) {
                                    boolean firstValue = true;
                                    for (String resultValue : resultValues) {
                                        credits = credits + (firstValue ? "" : ", ") + resultValue.substring(33);
                                        firstValue = false;
                                    }
                                } else if (creditType.equals("kuali.result.values.group.type.range")) {
                                    String minCredits = resultComponentInfo.getResultValueRange().getMinValue();
                                    String maxCredits = resultComponentInfo.getResultValueRange().getMaxValue();
                                    credits += minCredits + " - " + maxCredits;
                                }
                            }
                        }

                        CluInformation cluInformation = new CluInformation();
                        if (cluInfo.getOfficialIdentifier() != null) {
                            cluInformation.setCode(cluInfo.getOfficialIdentifier().getCode());
                            cluInformation.setTitle(cluInfo.getOfficialIdentifier().getShortName());
                            cluInformation.setCredits(credits);
                        }

                        cluInformation.setType(cluInfo.getTypeKey());
                        //If the clu type is variation, get the parent clu id.
                        if ("kuali.lu.type.Variation".equals(cluInfo.getTypeKey())) {
                            List<String> clus = this.getCluService().getCluIdsByRelatedCluAndRelationType(cluInfo.getId(), "kuali.lu.lu.relation.type.hasVariationProgram", ContextUtils.getContextInfo());
                            if (clus == null || clus.size() == 0) {
                                throw new RuntimeException("Statement Dependency clu found, but no parent Program exists");
                            } else if (clus.size() > 1) {
                                throw new RuntimeException("Statement Dependency clu can only have one parent Program relation");
                            }
                            cluInformation.setParentCluId(clus.get(0));
                        }

                        cluInformation.setCluId(cluInfo.getId());
                        cluInformation.setVerIndependentId(cluInfo.getVersion().getVersionIndId());
                        result.add(cluInformation);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (result != null) {
            Collections.sort(result);
        }
        return result;
    }


    public CluSetInfo buildCourseSet(ProgramCluSetInformation programCluSetInformation) {

        // Create a Cluset if not exist.
        if (programCluSetInformation.getCluSetInfo() == null) {
            programCluSetInformation.setCluSetInfo(new CluSetInfo());
        }

        // Set default properties.
        CluSetInfo cluSetInfo = programCluSetInformation.getCluSetInfo();
        cluSetInfo.setTypeKey(CluServiceConstants.CLUSET_TYPE_PROGRAM);
        cluSetInfo.setStateKey("Active");
        cluSetInfo.setName("AdHock");
        cluSetInfo.setEffectiveDate(new Date());
        cluSetInfo.setIsReferenceable(Boolean.TRUE);
        cluSetInfo.setIsReusable(Boolean.FALSE);

        //Clear all current values
        cluSetInfo.getCluSetIds().clear();
        cluSetInfo.setMembershipQuery(null);
        cluSetInfo.getCluIds().clear();

        boolean hasCluIds = programCluSetInformation.hasClus();

        //Set the cluset ids on the cluset
        if ((programCluSetInformation.getCluSets() == null) && (programCluSetInformation.getCluSets().isEmpty())) {
            if (hasCluIds) {
                cluSetInfo.setCluIds(programCluSetInformation.getCluIds());
                return cluSetInfo;
            }
        } else {
            for (CluSetInfo cluset : programCluSetInformation.getCluSets()) {
                cluSetInfo.getCluSetIds().add(cluset.getId());
            }
        }

        if (hasCluIds) {
            CluSetInfo wrapperCluSet = new CluSetInfo();
            wrapperCluSet.setCluIds(programCluSetInformation.getCluIds());
            cluSetInfo.getCluSetIds().add(saveWrapperCluSet(wrapperCluSet, programCluSetInformation.getCluSetInfo()));
        }
        return cluSetInfo;
    }

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

    protected CluService getCluService() {
        if (cluService == null) {
            cluService = GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE, CluServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return cluService;
    }

    protected LRCService getLrcService() {
        if (lrcService == null) {
            lrcService = (LRCService) GlobalResourceLoader.getService(new QName(LrcServiceConstants.NAMESPACE, LrcServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lrcService;
    }
}
