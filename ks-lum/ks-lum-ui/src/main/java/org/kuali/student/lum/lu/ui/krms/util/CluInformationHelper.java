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
package org.kuali.student.lum.lu.ui.krms.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.lum.lu.ui.krms.dto.CluInformation;
import org.kuali.student.lum.lu.ui.krms.dto.CluSetRangeInformation;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.lum.lu.ui.krms.dto.CluSetWrapper;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluResultInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;
import org.kuali.student.r2.lum.clu.dto.ResultOptionInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is a helper class to capture and retrieve clu information.
 *
 * @author Kuali Student Team
 */
public class CluInformationHelper {

    private CluService cluService;
    private LRCService lrcService;

    public CluInformationHelper() {
        super();
    }

    public List<CluSetInfo> getCluSetInfos(List<String> cluSetIds) {
        List<CluSetInfo> clusetInfos = new ArrayList<CluSetInfo>();
        if (cluSetIds != null) {
            for (String cluSetId : cluSetIds) {
                clusetInfos.add(this.getCluSetInfo(cluSetId));
            }
        }
        return clusetInfos;
    }

    public CluSetInfo getCluSetInfo(String cluSetId) {
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
     * This methos assumes that there can only be a maximum of one wrapped cluset
     * for cluids and one for membershipqueries.
     *
     * @param cluSetId
     * @return
     */
    public CluSetWrapper getCluSetWrapper(String cluSetId) {

        CluSetInfo cluSetInfo = this.getCluSetInfo(cluSetId);
        CluSetWrapper result = new CluSetWrapper(cluSetInfo);

        if(cluSetInfo.getMembershipQuery() != null){
            result.getCluSetRanges().add(this.createCluSetRange(cluSetInfo.getMembershipQuery()));
        }

        // goes through the list of sub clusets and ignore the ones that are not reusable
        List<CluSetInfo> cluSetInfos = this.getCluSetInfos(cluSetInfo.getCluSetIds());
        if (cluSetInfos != null) {
            List<String> cluIds = cluSetInfo.getCluIds();
            List<CluSetWrapper> unWrappedCluSets = new ArrayList<CluSetWrapper>();
            for (CluSetInfo subCluSet : cluSetInfos) {
                if (subCluSet.getIsReusable()) {
                    //Handle predefined clusets.
                    CluSetWrapper cluSetInformation = new CluSetWrapper(subCluSet);
                    cluSetInformation.setClus(this.getCluInfos(subCluSet.getCluIds()));
                    unWrappedCluSets.add(cluSetInformation);
                } else {

                    //Retrieve the information from the wrapped membership cluset.
                    if (subCluSet.getMembershipQuery() != null) {
                        result.getCluSetRanges().add(this.createCluSetRange(subCluSet.getMembershipQuery()));
                    } else {
                        //Retrieve the information from the wrapped clu cluset.
                        if (subCluSet.getCluIds() != null && !subCluSet.getCluIds().isEmpty()) {
                            cluIds = subCluSet.getCluIds();
                        }
                    }
                }
            }
            result.setCluSets(unWrappedCluSets);
            result.setClus(this.getCluInfos(cluIds));
        }

        return result;
    }

    /**
     * Creates a new clusetrangeinformation wrapper object for each membershipquery that exist in the
     * wrapper cluset.
     *
     * @param clusetInfo
     * @param mqInfo
     */
    private CluSetRangeInformation createCluSetRange(MembershipQueryInfo mqInfo) {

        CluSetRangeInformation cluSetRange = new CluSetRangeInformation();
        cluSetRange.setMembershipQueryInfo(mqInfo);
        cluSetRange.setClusInRange(this.getCluInfosWithDetailForQuery(mqInfo));
        cluSetRange.setCluSetRangeLabel(CluSetRangeHelper.buildLabelFromQuery(mqInfo));
        return cluSetRange;

    }

    public List<CluInformation> getCluInfos(List<String> cluIds) {
        List<CluInformation> result = new ArrayList<CluInformation>();
        if (cluIds != null) {
            for (String cluId : cluIds) {
                try {
                    VersionDisplayInfo versionInfo = this.getCluService().getCurrentVersion(CluServiceConstants.CLU_NAMESPACE_URI, cluId, ContextUtils.getContextInfo());
                    CluInfo cluInfo = this.getCluService().getClu(versionInfo.getId(), ContextUtils.getContextInfo());
                    if (cluInfo != null) {

                        CluInformation cluInformation = new CluInformation();
                        if (cluInfo.getOfficialIdentifier() != null) {
                            cluInformation.setCode(cluInfo.getOfficialIdentifier().getCode());
                            cluInformation.setTitle(cluInfo.getOfficialIdentifier().getLongName());
                            cluInformation.setShortName(cluInfo.getOfficialIdentifier().getShortName());
                        }

                        cluInformation.setCredits(getCreditInfo(cluInfo.getId()));
                        cluInformation.setType(cluInfo.getTypeKey());
                        cluInformation.setParentCluId(this.getParentCluId(cluInfo));

                        cluInformation.setCluId(cluInfo.getId());
                        cluInformation.setVerIndependentId(cluInfo.getVersion().getVersionIndId());
                        result.add(cluInformation);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        Collections.sort(result);
        return result;
    }

    private String getParentCluId(CluInfo cluInfo) {
        //If the clu type is variation, get the parent clu id.
        if (ProgramConstants.VARIATION_TYPE_KEY.equals(cluInfo.getTypeKey())) {
            int firstClu = 0;
            List<String> clus = null;
            try {
                clus = this.getCluService().getCluIdsByRelatedCluAndRelationType(cluInfo.getId(), ProgramConstants.HAS_PROGRAM_VARIATION, ContextUtils.getContextInfo());
            } catch (Exception e) {
                throw new RuntimeException("Could not retrieve parent clu.", e);
            }
            if (clus == null || clus.size() == 0) {
                throw new RuntimeException("Statement Dependency clu found, but no parent Program exists");
            } else if (clus.size() > 1) {
                throw new RuntimeException("Statement Dependency clu can only have one parent Program relation");
            }
            return clus.get(firstClu);
        }
        return null;
    }

    public String getCreditInfo(String cluId) {
        //retrieve credits
        List<CluResultInfo> cluResultInfos = null;
        try {
            cluResultInfos = this.getCluService().getCluResultByClu(cluId, ContextUtils.getContextInfo());
        } catch (Exception e) {
            throw new RuntimeException("Could not retrieve clu results for " + cluId, e);
        }
        if (cluResultInfos != null) {
            for (CluResultInfo cluResultInfo : cluResultInfos) {
                String cluType = cluResultInfo.getTypeKey();

                //ignore non-credit results
                if ((cluType == null) || (!cluType.equals(ProgramConstants.COURSE_RESULT_TYPE_CREDITS))) {
                    continue;
                }

                //retrieve credit type and credit values
                if (cluResultInfo.getResultOptions() != null) {
                    for (ResultOptionInfo resultOption : cluResultInfo.getResultOptions()) {
                        if (resultOption.getResultComponentId() != null) {
                            try {
                                ResultValuesGroupInfo resultComponentInfo = this.getLrcService().getResultValuesGroup(resultOption.getResultComponentId(), ContextUtils.getContextInfo());
                                return resultComponentInfo.getName();
                            } catch (Exception e) {
                                throw new RuntimeException("Could not retrieve result values group for " + resultOption.getResultComponentId(), e);
                            }
                        }
                    }
                }
            }
        }
        return StringUtils.EMPTY;
    }

    public List<CluInformation> getCluInfosForQuery(MembershipQueryInfo membershipQuery) {

        // Do not use DateFormatters' DateTimeFormat as it cannot format timezones.
        DateFormat sdf = new SimpleDateFormat(DateFormatters.QUERY_SERVICE_TIMESTAMP_FORMAT);

        if (membershipQuery != null) {

            // Handle Date queries for the course ranges.
            if (membershipQuery.getSearchTypeKey().equals(CluSetRangeHelper.CLU_SEARCH_GENERIC)) {

                String date1 = CluSetRangeHelper.getParmValue(membershipQuery.getQueryParamValues(), CluSetRangeHelper.CLU_SEARCH_PARM_DATE1);
                String date2 = CluSetRangeHelper.getParmValue(membershipQuery.getQueryParamValues(), CluSetRangeHelper.CLU_SEARCH_PARM_DATE2);

                try {
                    QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
                    qbcBuilder.setPredicates(PredicateFactory.greaterThanOrEqual("effectiveDate", sdf.parse(date1)),
                            PredicateFactory.lessThanOrEqual("effectiveDate", sdf.parse(date2)));

                    List<CluInfo> cluInfos = this.getCluService().searchForClus(qbcBuilder.build(), ContextUtils.getContextInfo());

                    List<CluInformation> cluInformations = new ArrayList<CluInformation>();
                    for (CluInfo cluInfo : cluInfos) {
                        cluInformations.add(cluInformationFromCluInfo(cluInfo));
                    }
                    return cluInformations;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (membershipQuery.getSearchTypeKey().equals(CluSetRangeHelper.LO_SEARCH_LODESC)) {
                //LO search.
                SearchRequestInfo searchRequest = new SearchRequestInfo();
                searchRequest.setSearchKey(membershipQuery.getSearchTypeKey());
                searchRequest.setParams(membershipQuery.getQueryParamValues());
                searchRequest.setSortColumn("lo.resultColumn.loCluCode");

                try {
                    SearchResultInfo searchResult = this.getCluService().search(searchRequest, ContextUtils.getContextInfo());
                    return resolveLoSearchResults(searchResult, this.getCluService());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                //All other search use the r1 search service.
                SearchRequestInfo searchRequest = new SearchRequestInfo();
                searchRequest.setSearchKey(membershipQuery.getSearchTypeKey());
                searchRequest.setParams(membershipQuery.getQueryParamValues());
                searchRequest.setSortColumn("lu.resultColumn.luOptionalCode");

                try {
                    SearchResultInfo searchResult = this.getCluService().search(searchRequest, ContextUtils.getContextInfo());
                    return CluSearchUtil.resolveCluSearchResultSet(searchResult);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        }
        return null;
    }

    public List<CluInformation> getCluInfosWithDetailForQuery(MembershipQueryInfo membershipQuery) {
        List<CluInformation> cluInfos = this.getCluInfosForQuery(membershipQuery);
        if (cluInfos != null) {
            for (CluInformation cluInfo : cluInfos) {
                cluInfo.setCredits(this.getCreditInfo(cluInfo.getCluId()));
            }
        }
        return cluInfos;
    }

    public CluInformation getCluInfoForCodeAndType(String code, List<String> types) throws OperationFailedException {
        try {

            //Setup search criteria.
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            qbcBuilder.setPredicates(PredicateFactory.equal("officialIdentifier.code", code),
                    PredicateFactory.in("state", CluSearchUtil.getApprovedStatesForClus().toArray()),
                    PredicateFactory.in("luType.id", types.toArray()));

            //Perform the search for the clu.
            List<CluInfo> cluInfos = this.getCluService().searchForClus(qbcBuilder.build(), ContextUtils.getContextInfo());
            CluInfo cluInfo = KSCollectionUtils.getOptionalZeroElement(cluInfos);

            if (cluInfo != null) {
                return cluInformationFromCluInfo(cluInfo);
            }
        }catch (OperationFailedException ofe){
            throw ofe;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public CluSetInfo getCluSetForId(String id) {
        try {
            CluSetInfo cluSetInfo = this.getCluService().getCluSet(id, ContextUtils.getContextInfo());
            return cluSetInfo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create a new CluInformation object from the CluInfo dto.
     *
     * @param cluInfo
     * @return
     */
    private static CluInformation cluInformationFromCluInfo(CluInfo cluInfo) {
        CluInformation cluInformation = new CluInformation(cluInfo.getOfficialIdentifier().getCode(), cluInfo.getOfficialIdentifier().getShortName(), "");
        cluInformation.setCluId(cluInfo.getId());
        if(cluInfo.getDescr()!=null){
            cluInformation.setDescription(cluInfo.getDescr().getPlain());
        }
        cluInformation.setState(cluInfo.getStateKey());
        cluInformation.setTitle(cluInfo.getOfficialIdentifier().getLongName());
        cluInformation.setType(cluInfo.getTypeKey());
        cluInformation.setVerIndependentId(cluInfo.getVersion().getVersionIndId());
        return cluInformation;
    }

    public static List<CluInformation> resolveLoSearchResults(SearchResultInfo searchResult, CluService cluService) {
        List<CluInformation> clus = new ArrayList<CluInformation>();
        List<SearchResultRowInfo> rows = searchResult.getRows();
        for (SearchResultRowInfo row : rows) {
            try {
                List<SearchResultCellInfo> cells = row.getCells();
                for (SearchResultCellInfo cell : cells) {
                    if (cell.getKey().equals("lo.resultColumn.loCluId")) {
                        CluInfo cluInfo = cluService.getClu(cell.getValue(), ContextUtils.getContextInfo());
                        clus.add(cluInformationFromCluInfo(cluInfo));
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return clus;
    }

    public CluService getCluService() {
        return cluService;
    }

    public void setCluService(CluService cluService) {
        this.cluService = cluService;
    }

    public LRCService getLrcService() {
        return lrcService;
    }

    public void setLrcService(LRCService lrcService) {
        this.lrcService = lrcService;
    }
}
