/**
 * Copyright 2005-2012 The Kuali Foundation
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
package org.kuali.student.enrollment.class1.krms.builder;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.rice.krms.builder.ComponentBuilderUtils;
import org.kuali.student.enrollment.class1.krms.dto.CluSetInformation;
import org.kuali.student.enrollment.class1.krms.dto.EnrolPropositionEditor;
import org.kuali.student.enrollment.class1.krms.util.CluInformationHelper;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiCourseComponentBuilder implements ComponentBuilder<EnrolPropositionEditor> {

    private CluService cluService;
    private CourseService courseService;
    private LRCService lrcService;

    private CluInformationHelper cluInfoHelper;

    private static final String CLUSET_KEY = "kuali.term.parameter.type.course.cluSet.id";
    private static final String GRADE_TYPE_KEY = "kuali.term.parameter.type.gradeType.id";
    private static final String GRADE_KEY = "kuali.term.parameter.type.grade.id";

    @Override
    public List<String> getComponentIds() {
        return null;
    }

    @Override
    public void resolveTermParameters(EnrolPropositionEditor propositionEditor, Map<String, String> termParameters) {
        String cluSetId = termParameters.get(CLUSET_KEY);
        if (cluSetId != null) {
            try {
                CluSetInformation cluSetInfo = this.getCluSetInformation(cluSetId);
                propositionEditor.setCluSet(cluSetInfo);
                if(cluSetInfo.hasMembershipQuery()){
                    propositionEditor.getCluSet().getCluSetRange().resetFromQuery(cluSetInfo.getMembershipQueryInfo());
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public Map<String, String> buildTermParameters(EnrolPropositionEditor propositionEditor) {
        Map<String, String> termParameters = new HashMap<String, String>();
        if (propositionEditor.getCluSet() != null) {
            if (propositionEditor.getCluSet().getCluSetInfo() != null) {
                termParameters.put(CLUSET_KEY, propositionEditor.getCluSet().getCluSetInfo().getId());
            } else {
                termParameters.put(CLUSET_KEY, null);
            }
        }
        if (propositionEditor.getGradeScale() != null) {
            termParameters.put(GRADE_TYPE_KEY, propositionEditor.getGradeScale());
            termParameters.put(GRADE_KEY, propositionEditor.getTermParameter());
        }
        return termParameters;
    }

    @Override
    public void onSubmit(EnrolPropositionEditor propositionEditor) {
        //Create the courseset
        try {
            propositionEditor.getCluSet().setCluSetInfo(this.buildCourseSet(propositionEditor.getCluSet()));
            CluSetInfo cluSetInfo = propositionEditor.getCluSet().getCluSetInfo();
            if (cluSetInfo.getId() == null) {
                cluSetInfo = this.getCluService().createCluSet(cluSetInfo.getTypeKey(), cluSetInfo, ContextUtils.getContextInfo());
                ComponentBuilderUtils.updateTermParameter(propositionEditor.getTerm(), CLUSET_KEY, cluSetInfo.getId());

            } else {
                this.getCluService().updateCluSet(cluSetInfo.getId(), cluSetInfo, ContextUtils.getContextInfo());
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * This methos assumes that there can only be a maximum of one wrapped cluset
     * for cluids and one for membershipqueries.
     *
     * @param cluSetId
     * @return
     */
    public CluSetInformation getCluSetInformation(String cluSetId) {

        CluSetInformation result = new CluSetInformation();
        result.setCluSetInfo(this.getCluSetInfo(cluSetId));

        List<String> cluIds = result.getCluSetInfo().getCluIds();
        result.setMembershipQueryInfo(result.getCluSetInfo().getMembershipQuery());

        // goes through the list of sub clusets and ignore the ones that are not reusable
        List<CluSetInfo> cluSetInfos = getCluSetInfos(result.getCluSetInfo().getCluSetIds());
        if (cluSetInfos != null) {
            List<CluSetInformation> unWrappedCluSets = new ArrayList<CluSetInformation>();
            for (CluSetInfo subCluSet : cluSetInfos) {
                if (subCluSet.getIsReusable()) {
                    CluSetInformation cluSetInformation = new CluSetInformation(subCluSet);
                    cluSetInformation.setClus(this.getCluInfoHelper().getCourseInfos(subCluSet.getCluIds()));
                    unWrappedCluSets.add(cluSetInformation);
                } else {

                    //Retrieve the information from the wrapped clu cluset.
                    if (subCluSet.getCluIds() != null && !subCluSet.getCluIds().isEmpty()) {
                        cluIds = subCluSet.getCluIds();
                    }

                    //Retrieve the information from the wrapped membership cluset.
                    MembershipQueryInfo mqInfo = subCluSet.getMembershipQuery();
                    if (mqInfo != null && mqInfo.getSearchTypeKey() != null && !mqInfo.getSearchTypeKey().isEmpty()) {
                        result.setMembershipQueryInfo(mqInfo);
                    }
                }
            }
            result.setCluSets(unWrappedCluSets);
        }

        result.setClus(this.getCluInfoHelper().getCourseInfos(cluIds));
        result.setClusInRange(this.getCluInfoHelper().getCluInfosWithDetailForQuery(result.getMembershipQueryInfo()));

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

    public CluSetInfo buildCourseSet(CluSetInformation cluSetInformation) {

        // Create a Cluset if not exist.
        if (cluSetInformation.getCluSetInfo() == null) {
            cluSetInformation.setCluSetInfo(new CluSetInfo());
        }

        // Set default properties.
        CluSetInfo cluSetInfo = cluSetInformation.getCluSetInfo();
        cluSetInfo.setTypeKey(CluServiceConstants.CLUSET_TYPE_CREDIT_COURSE);
        cluSetInfo.setStateKey("Active");
        cluSetInfo.setName("AdHock");
        cluSetInfo.setEffectiveDate(new Date());
        cluSetInfo.setIsReferenceable(Boolean.TRUE);
        cluSetInfo.setIsReusable(Boolean.FALSE);

        //Clear all current values
        cluSetInfo.getCluSetIds().clear();
        cluSetInfo.setMembershipQuery(null);
        cluSetInfo.getCluIds().clear();

        boolean hasCluIds = cluSetInformation.hasClus();
        boolean hasMembershipQuery = cluSetInformation.hasMembershipQuery();

        //Set the cluset ids on the cluset
        if ((cluSetInformation.getCluSets() == null) && (cluSetInformation.getCluSets().isEmpty())) {
            if (hasCluIds && !hasMembershipQuery) {
                cluSetInfo.setCluIds(cluSetInformation.getCluIds());
                return cluSetInfo;
            } else if (!hasCluIds && hasMembershipQuery) {
                cluSetInfo.setMembershipQuery(cluSetInformation.getMembershipQueryInfo());
                return cluSetInfo;
            }
        } else {
            for (CluSetInformation cluset : cluSetInformation.getCluSets()) {
                cluSetInfo.getCluSetIds().add(cluset.getCluSetInfo().getId());
            }
        }

        if (hasCluIds) {
            CluSetInfo wrapperCluSet = new CluSetInfo();
            wrapperCluSet.setCluIds(cluSetInformation.getCluIds());
            cluSetInfo.getCluSetIds().add(saveWrapperCluSet(wrapperCluSet, cluSetInformation.getCluSetInfo()));
        }

        if (hasMembershipQuery) {
            CluSetInfo wrapperCluSet = new CluSetInfo();
            wrapperCluSet.setMembershipQuery(cluSetInformation.getMembershipQueryInfo());
            cluSetInfo.getCluSetIds().add(saveWrapperCluSet(wrapperCluSet, cluSetInformation.getCluSetInfo()));
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

    protected CluInformationHelper getCluInfoHelper() {
        if (cluInfoHelper == null) {
            cluInfoHelper = new CluInformationHelper();
            cluInfoHelper.setCluService(this.getCluService());
            cluInfoHelper.setLrcService(this.getLrcService());
        }
        return cluInfoHelper;
    }

    protected CourseService getCourseService() {
        if (courseService == null) {
            courseService = CourseOfferingResourceLoader.loadCourseService();
        }
        return courseService;
    }

    protected CluService getCluService() {
        if (cluService == null) {
            cluService = CourseOfferingResourceLoader.loadCluService();
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
