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
import org.kuali.student.lum.lu.ui.krms.dto.CluInformation;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluResultInfo;
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;
import org.kuali.student.r2.lum.clu.dto.ResultOptionInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

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

    public List<CluInformation> getCourseInfos(List<String> cluIds) {
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

    public String getCreditInfo(String cluId) {
        //retrieve credits
        List<CluResultInfo> cluResultInfos = null;
        try {
            cluResultInfos = this.getCluService().getCluResultByClu(cluId, ContextUtils.getContextInfo());
        } catch (Exception e) {
            throw new RuntimeException("Could not retrieve clu results for " + cluId);
        }
        if (cluResultInfos != null) {
            for (CluResultInfo cluResultInfo : cluResultInfos) {
                String cluType = cluResultInfo.getTypeKey();

                //ignore non-credit results
                if ((cluType == null) || (!cluType.equals("kuali.resultType.creditCourseResult"))) {
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
                                throw new RuntimeException("Could not retrieve result values group for " + resultOption.getResultComponentId());
                            }
                        }
                    }
                }
            }
        }
        return StringUtils.EMPTY;
    }

    public List<CluInformation> getCluInfosForQuery(MembershipQueryInfo membershipQuery) {

        if (membershipQuery != null) {

            // Handle Date queries for the course ranges.
            if (membershipQuery.getSearchTypeKey().equals(CluSetRangeHelper.CLU_SEARCH_GENERIC)) {

                String date1 = CluSetRangeHelper.getParmValue(membershipQuery.getQueryParamValues(), CluSetRangeHelper.CLU_SEARCH_PARM_DATE1);
                String date2 = CluSetRangeHelper.getParmValue(membershipQuery.getQueryParamValues(), CluSetRangeHelper.CLU_SEARCH_PARM_DATE2);

                try {
                    QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
                    qbcBuilder.setPredicates(PredicateFactory.greaterThanOrEqual("effectiveDate", CluSetRangeHelper.sdf.parse(date1)),
                            PredicateFactory.lessThanOrEqual("effectiveDate", CluSetRangeHelper.sdf.parse(date2)));

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
                    return resolveCluSearchResultSet(searchResult);
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

    public static SearchParamInfo getApprovedStateSearchParam() {
        SearchParamInfo searchParam = new SearchParamInfo();
        searchParam.setKey("lu.queryParam.luOptionalState");
        searchParam.getValues().add(DtoConstants.STATE_APPROVED);
        searchParam.getValues().add(DtoConstants.STATE_ACTIVE);
        searchParam.getValues().add(DtoConstants.STATE_RETIRED);
        searchParam.getValues().add(DtoConstants.STATE_SUSPENDED);
        return searchParam;
    }

    public static List<CluInformation> resolveCluSearchResultSet(SearchResultInfo searchResult) {
        List<CluInformation> clus = new ArrayList<CluInformation>();
        List<SearchResultRowInfo> rows = searchResult.getRows();
        for (SearchResultRowInfo row : rows) {
            List<SearchResultCellInfo> cells = row.getCells();
            CluInformation cluInformation = new CluInformation();
            for (SearchResultCellInfo cell : cells) {
                if (cell.getKey().equals("lu.resultColumn.cluId")) {
                    cluInformation.setCluId(cell.getValue());
                } else if (cell.getKey().equals("lu.resultColumn.luOptionalCode")) {
                    cluInformation.setCode(cell.getValue());
                } else if (cell.getKey().equals("lu.resultColumn.luOptionalLongName")) {
                    cluInformation.setTitle(cell.getValue());
                } else if (cell.getKey().equals("lu.resultColumn.luOptionalDescr")) {
                    cluInformation.setDescription(cell.getValue());
                } else if (cell.getKey().equals("lu.resultColumn.luOptionalState")) {
                    cluInformation.setState(cell.getValue());
                } else if (cell.getKey().equals("lu.resultColumn.luOptionalVersionIndId")) {
                    cluInformation.setVerIndependentId(cell.getValue());
                } else if (cell.getKey().equals("lu.resultColumn.luOptionalShortName")) {
                    cluInformation.setShortName(cell.getValue());
                }
            }
            clus.add(cluInformation);
        }
        return clus;
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
        cluInformation.setDescription(cluInfo.getDescr().getPlain());
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
