/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.lum.lu.ui.tools.server.gwt;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.common.ui.server.gwt.old.AbstractBaseDataOrchestrationRpcGwtServlet;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultCell;
import org.kuali.student.core.search.dto.SearchResultRow;
import org.kuali.student.lum.lrc.service.LrcService;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluSetInfo;
import org.kuali.student.lum.lu.dto.MembershipQueryInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.lu.ui.tools.client.configuration.CluInformation;
import org.kuali.student.lum.lu.ui.tools.client.configuration.CluSetInformation;
import org.kuali.student.lum.lu.ui.tools.client.service.CluSetManagementRpcService;

public class CluSetManagementRpcGwtServlet extends
		AbstractBaseDataOrchestrationRpcGwtServlet implements
		CluSetManagementRpcService {

	private static final long serialVersionUID = 1L;
	final static Logger LOG = Logger.getLogger(CluSetManagementRpcGwtServlet.class);
	private LuService luService;
	private LrcService lrcService;
    
	public LuService getLuService() {
        return luService;
    }

    public void setLuService(LuService luService) {
        this.luService = luService;
    }

    public LrcService getLrcService() {
        return lrcService;
    }

    public void setLrcService(LrcService lrcService) {
        this.lrcService = lrcService;
    }

    @Override
	protected String getDefaultMetaDataState() {
		return null; //DEFAULT_METADATA_STATE;
	}

	@Override
	protected String getDefaultMetaDataType() {
		return null; //DEFAULT_METADATA_TYPE;
	}

	@Override
	protected String getDefaultWorkflowDocumentType() {
		return null; //WF_TYPE_CLU_DOCUMENT;
	}
	
	@Override
	protected String deriveAppIdFromData(Data data) {
		return null;
	}

	@Override
	protected String deriveDocContentFromData(Data data) {
		return null;
	}

    private CluSetInfo getCluSetInfo(String cluSetId) throws OperationFailedException {
        List<String> cluIds = null;
        CluSetInfo cluSetInfo = null;
        try {
            // note: the cluIds returned by luService.getCluSetInfo also contains the clus
            //       that are the result of query parameter search.  Set to null here and
            //       retrieve the clus that are direct members.
            cluSetInfo = luService.getCluSetInfo(cluSetId);
            cluSetInfo.setCluIds(null);
            cluIds = luService.getCluIdsFromCluSet(cluSetId);
            cluSetInfo.setCluIds(cluIds);
            upWrap(cluSetInfo);
        } catch (Exception e) {
            throw new OperationFailedException("Failed to retrieve cluset info for " + cluSetId, e);
        }
        return cluSetInfo;
    }
    
    private List<CluSetInfo> getCluSetInfos(List<String> cluSetIds) throws OperationFailedException {
        List<CluSetInfo> clusetInfos = null;
        if (cluSetIds != null) {
            for (String cluSetId : cluSetIds) {
                clusetInfos = (clusetInfos == null)? new ArrayList<CluSetInfo>() : clusetInfos;
                clusetInfos.add(getCluSetInfo(cluSetId));
            }
        }
        return clusetInfos;
    }

    private void upWrap(CluSetInfo cluSetInfo) throws AssemblyException {
        List<String> cluSetIds = (cluSetInfo == null)? null : cluSetInfo.getCluSetIds();
        List<String> unWrappedCluSetIds = null;
        List<CluSetInfo> wrappedCluSets = null;
        List<CluSetInfo> subCluSets = null;

        try {
            if (cluSetIds != null && !cluSetIds.isEmpty()) {
                subCluSets = luService.getCluSetInfoByIdList(cluSetIds);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new AssemblyException("Failed to retrieve the sub clusets of cluset " +
                    cluSetInfo.getId());
        }
        // goes through the list of sub clusets and ignore the ones that are not reusable
        if (subCluSets != null) {
            for (CluSetInfo subCluSet : subCluSets) {
                if (subCluSet.getIsReusable()) {
                    unWrappedCluSetIds = (unWrappedCluSetIds == null)?
                            new ArrayList<String>() : unWrappedCluSetIds;
                            unWrappedCluSetIds.add(subCluSet.getId());
                } else {
                    wrappedCluSets = (wrappedCluSets == null)?
                            new ArrayList<CluSetInfo>() : wrappedCluSets;
                            wrappedCluSets.add(subCluSet);
                }
            }
        }
        cluSetInfo.setCluSetIds(unWrappedCluSetIds);
        if (wrappedCluSets != null) {
            for (CluSetInfo wrappedCluSet : wrappedCluSets) {
                MembershipQueryInfo mqInfo = wrappedCluSet.getMembershipQuery();
                if (wrappedCluSet.getCluIds() != null && !wrappedCluSet.getCluIds().isEmpty()) {
                    cluSetInfo.setCluIds(wrappedCluSet.getCluIds());
                }
                if (mqInfo != null && mqInfo.getSearchTypeKey() != null && !mqInfo.getSearchTypeKey().isEmpty()) {
                    cluSetInfo.setMembershipQuery(mqInfo);
                }
            }
        }
    }
    
    private List<CluInformation> getCluInformations(List<String> cluIds) throws OperationFailedException {
        List<CluInformation> result = new ArrayList<CluInformation>();
        if (cluIds != null) {
            for (String cluId : cluIds) {
                try {
                    CluInfo cluInfo = luService.getClu(cluId);
                    if (cluInfo != null) {
                        CluInformation cluInformation = new CluInformation();
                        // TODO credits
//                        List<CluResultInfo> cluResultInfos = luService.getCluResultByClu(cluId);
//                        String credits = null;
//                        if (cluResultInfos != null) {
//                            for (CluResultInfo cluResultInfo : cluResultInfos) {
//                                if (cluResultInfo.getType() != null &&
//                                        cluResultInfo.getType().equals(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_FIXED) ||
//                                        cluResultInfo.getType().equals(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_VARIABLE) ||
//                                        cluResultInfo.getType().equals(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_MULTIPLE)) {
//                                    List<ResultOptionInfo> resultOptions = cluResultInfo.getResultOptions();
//                                    if (resultOptions != null) {
//                                        for (ResultOptionInfo resultOption : resultOptions) {
//                                            if (resultOption.getResultComponentId() != null) {
//                                                ResultComponentInfo resultComponentInfo = 
//                                                    lrcService.getResultComponent(resultOption.getResultComponentId());
////                                                resultComponentInfo.get
//                                            }
//                                            
//                                        }
//                                    }
//                                    break;
//                                }
//                            }
//                        }
                        if (cluInfo.getOfficialIdentifier() != null) {
                            cluInformation.setCode(cluInfo.getOfficialIdentifier().getCode());
                            cluInformation.setTitle(cluInfo.getOfficialIdentifier().getShortName());
                        }
                        cluInformation.setId(cluInfo.getId());
                        result.add(cluInformation);
                    }
                } catch (Exception e) {
                    throw new OperationFailedException("Failed to get info for cluId " + cluId, e);
                }
            }
        }
        return result;
    }

    @Override
    public CluSetInformation getCluSetInformation(String cluSetId) throws OperationFailedException {
        CluSetInformation result = new CluSetInformation();
        CluSetInfo cluSetInfo = getCluSetInfo(cluSetId);
        List<String> allCluIds = cluSetInfo.getCluIds();
        List<String> cluSetIds =  cluSetInfo.getCluSetIds();
        final MembershipQueryInfo membershipQueryInfo = cluSetInfo.getMembershipQuery();
        result.setId(cluSetId);
        if (allCluIds != null) {
            List<CluInformation> clus = getCluInformations(allCluIds);
            result.setClus(clus);
        }
        if (cluSetIds != null) {
            List<CluSetInfo> cluSetInfos = getCluSetInfos(cluSetIds);
            result.setCluSets(cluSetInfos);
        }
        if (membershipQueryInfo != null) {
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.setSearchKey(membershipQueryInfo.getSearchTypeKey());
            searchRequest.setParams(membershipQueryInfo.getQueryParamValueList());
            SearchResult searchResult = null;
            try {
                searchResult = luService.search(searchRequest);
            } catch (Exception e) {
                throw new OperationFailedException("Failed to search for clus in clu range", e);
            }
            List<CluInformation> clusInRange = new ArrayList<CluInformation>();
            List<SearchResultRow> rows = searchResult.getRows();
            for(SearchResultRow row : rows) {
                List<SearchResultCell> cells = row.getCells();
                CluInformation cluInformation = new CluInformation();
                for(SearchResultCell cell : cells) {
                    if(cell.getKey().equals("lu.resultColumn.cluId")) {
                        cluInformation.setId(cell.getValue());
                    }
                    if (cell.getKey().equals("lu.resultColumn.luOptionalCode")) {
                        cluInformation.setCode(cell.getValue());
                    }
                    if (cell.getKey().equals("lu.resultColumn.luOptionalShortName")) {
                        cluInformation.setTitle(cell.getValue());
                    }
                }
                clusInRange.add(cluInformation);
            }
            result.setMembershipQueryInfo(membershipQueryInfo);
            result.setClusInRange(clusInRange);
        }
        return result;
    }
	
}
