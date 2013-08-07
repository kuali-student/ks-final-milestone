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
import org.kuali.rice.krms.dto.TermParameterEditor;
import org.kuali.rice.krms.util.KRMSConstants;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.lum.lu.ui.krms.dto.CluSetInformation;
import org.kuali.student.lum.lu.ui.krms.dto.CluSetRangeInformation;
import org.kuali.student.lum.lu.ui.krms.dto.LUPropositionEditor;
import org.kuali.student.lum.lu.ui.krms.util.CluInformationHelper;
import org.kuali.student.lum.lu.ui.krms.util.CluSetRangeHelper;
import org.kuali.student.lum.lu.ui.krms.util.LUKRMSConstants;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
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

/**
 * @author Kuali Student Team
 */
public abstract class CluComponentBuilder implements ComponentBuilder<LUPropositionEditor> {

    private CluService cluService;
    private LRCService lrcService;

    private CluInformationHelper cluInfoHelper;

    /**
     * This method build the CluSetInfo object based on the CluSetInformation wrapper object.
     *
     * Calculates if we require a wrapper cluset or not and the create sub clusets for the different types
     * of clusets required to save the individual courses of membershipqueries.
     *
     * @param cluSetInformation
     * @return
     */
    public CluSetInfo buildCourseSet(CluSetInformation cluSetInformation) {

        // Create a Cluset if not exist.
        if (cluSetInformation.getCluSetInfo() == null) {
            cluSetInformation.setCluSetInfo(new CluSetInfo());
        }

        // Set default properties.
        CluSetInfo cluSetInfo = cluSetInformation.getCluSetInfo();
        cluSetInfo.setStateKey("Active");
        cluSetInfo.setName("AdHock");
        cluSetInfo.setEffectiveDate(new Date());
        cluSetInfo.setIsReferenceable(Boolean.TRUE);
        cluSetInfo.setIsReusable(Boolean.FALSE);

        //Clear all current values
        cluSetInfo.getCluSetIds().clear();
        cluSetInfo.setMembershipQuery(null);
        cluSetInfo.getCluIds().clear();

        return cluSetInfo;
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
        this.createCluSetRange(result, result.getCluSetInfo().getMembershipQuery());

        // goes through the list of sub clusets and ignore the ones that are not reusable
        List<CluSetInfo> cluSetInfos = getCluSetInfos(result.getCluSetInfo().getCluSetIds());
        if (cluSetInfos != null) {
            List<CluSetInformation> unWrappedCluSets = new ArrayList<CluSetInformation>();
            for (CluSetInfo subCluSet : cluSetInfos) {
                if (subCluSet.getIsReusable()) {

                    //Handle predefined clusets.
                    CluSetInformation cluSetInformation = new CluSetInformation(subCluSet);
                    cluSetInformation.setClus(this.getCluInfoHelper().getCluInfos(subCluSet.getCluIds()));
                    unWrappedCluSets.add(cluSetInformation);
                } else {

                    //Retrieve the information from the wrapped membership cluset.
                    if(subCluSet.getMembershipQuery()!=null){
                        this.createCluSetRange(result, subCluSet.getMembershipQuery());
                    } else {

                        //Retrieve the information from the wrapped clu cluset.
                        if (subCluSet.getCluIds() != null && !subCluSet.getCluIds().isEmpty()) {
                            cluIds = subCluSet.getCluIds();
                        }
                    }
                }
            }
            result.setCluSets(unWrappedCluSets);
        }

        result.setClus(this.getCluInfoHelper().getCluInfos(cluIds));

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

    /**
     * Creates a new clusetrangeinformation wrapper object for each membershipquery that exist in the
     * wrapper cluset.
     *
     * @param clusetInfo
     * @param mqInfo
     */
    private void createCluSetRange(CluSetInformation clusetInfo, MembershipQueryInfo mqInfo) {
        if (mqInfo == null || mqInfo.getSearchTypeKey() == null || mqInfo.getSearchTypeKey().isEmpty()) {
            return;
        }
        CluSetRangeInformation cluSetRange = new CluSetRangeInformation();
        cluSetRange.setMembershipQueryInfo(mqInfo);
        cluSetRange.setClusInRange(this.getCluInfoHelper().getCluInfosWithDetailForQuery(mqInfo));
        cluSetRange.setCluSetRangeLabel(clusetInfo.getRangeHelper().buildLabelFromQuery(mqInfo));

        clusetInfo.getCluSetRanges().add(cluSetRange);
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

    protected CluInformationHelper getCluInfoHelper() {
        if (cluInfoHelper == null) {
            cluInfoHelper = new CluInformationHelper();
            cluInfoHelper.setCluService(this.getCluService());
            cluInfoHelper.setLrcService(this.getLrcService());
        }
        return cluInfoHelper;
    }

}