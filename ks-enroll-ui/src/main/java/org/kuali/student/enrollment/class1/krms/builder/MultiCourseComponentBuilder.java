package org.kuali.student.enrollment.class1.krms.builder;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.term.TermParameterDefinition;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.rice.krms.dto.TermEditor;
import org.kuali.rice.krms.dto.TermParameterEditor;
import org.kuali.student.enrollment.class1.krms.dto.CluInformation;
import org.kuali.student.enrollment.class1.krms.dto.CluSetInformation;
import org.kuali.student.enrollment.class1.krms.dto.EnrolPropositionEditor;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
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
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.springframework.util.ObjectUtils;

import javax.xml.namespace.QName;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/03/05
 * Time: 1:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class MultiCourseComponentBuilder implements ComponentBuilder<EnrolPropositionEditor> {

    private CluService cluService;

    private LRCService lrcService;

    private static final String CLUSET_KEY = "kuali.term.parameter.type.course.cluSet.id";

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

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public Map<String, String> buildTermParameters(EnrolPropositionEditor propositionEditor) {
        Map<String, String> termParameters = new HashMap<String, String>();
        if (propositionEditor.getCluSet() != null) {
            if(propositionEditor.getCluSet().getCluSetInfo() == null){
                propositionEditor.getCluSet().setCluSetInfo(this.createCourseSet(propositionEditor.getCluSet()));
            }
            termParameters.put(CLUSET_KEY, propositionEditor.getCluSet().getCluSetInfo().getId());
        }
        return termParameters;
    }

    @Override
    public void onSubmit(EnrolPropositionEditor propositionEditor) {
        //Create the courseset
        try {
            CluSetInfo cluSetInfo = propositionEditor.getCluSet().getCluSetInfo();
            if(cluSetInfo.getId()==null){
                cluSetInfo = this.getCluService().createCluSet(cluSetInfo.getTypeKey(), cluSetInfo, ContextUtils.getContextInfo());
            }else{
                this.getCluService().updateCluSet(cluSetInfo.getId(), cluSetInfo, ContextUtils.getContextInfo());
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public CluSetInformation getCluSetInformation(String cluSetId) {
        CluSetInformation result = new CluSetInformation();
        CluSetInfo cluSetInfo = getCluSetInfo(cluSetId);
        result.setCluSetInfo(cluSetInfo);

        //Set the Clu Informations
        List<CluInformation> clus = getCluInformations(cluSetInfo.getCluIds());
        result.setClus(clus);

        //Set the Clu set infos
        List<CluSetInfo> cluSetInfos = getCluSetInfos(cluSetInfo.getCluSetIds());
        result.setCluSets(cluSetInfos);

        //Query info.
        final MembershipQueryInfo membershipQueryInfo = cluSetInfo.getMembershipQuery();
        if (membershipQueryInfo != null) {
            SearchRequestInfo searchRequest = new SearchRequestInfo();
            searchRequest.setSearchKey(membershipQueryInfo.getSearchTypeKey());
            searchRequest.setParams(membershipQueryInfo.getQueryParamValues());
            SearchResultInfo searchResult = null;
            try {
                searchResult = this.getCluService().search(searchRequest, ContextUtils.getContextInfo());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            List<CluInformation> clusInRange = new ArrayList<CluInformation>();
            List<SearchResultRowInfo> rows = searchResult.getRows();
            for (SearchResultRowInfo row : rows) {
                List<SearchResultCellInfo> cells = row.getCells();
                CluInformation cluInformation = new CluInformation();
                for (SearchResultCellInfo cell : cells) {
                    if (cell.getKey().equals("lu.resultColumn.cluId")) {
                        cluInformation.setCluId(cell.getValue());
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
        if (result.getClus() != null)
            Collections.sort(result.getClus());
        return result;
    }

    private List<CluSetInfo> getCluSetInfos(List<String> cluSetIds) {
        List<CluSetInfo> clusetInfos = new ArrayList<CluSetInfo>();
        if (cluSetIds != null) {
            for (String cluSetId : cluSetIds) {
                clusetInfos.add(getCluSetInfo(cluSetId));
            }
        }
        return clusetInfos;
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
        return result;
    }

    private void upWrap(CluSetInfo cluSetInfo, ContextInfo contextInfo) {
        List<String> cluSetIds = (cluSetInfo == null) ? null : cluSetInfo.getCluSetIds();
        List<String> unWrappedCluSetIds = null;
        List<CluSetInfo> wrappedCluSets = null;
        List<CluSetInfo> subCluSets = null;

        try {
            if (cluSetIds != null && !cluSetIds.isEmpty()) {
                subCluSets = this.getCluService().getCluSetsByIds(cluSetIds, contextInfo);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // goes through the list of sub clusets and ignore the ones that are not reusable
        if (subCluSets != null) {
            for (CluSetInfo subCluSet : subCluSets) {
                if (subCluSet.getIsReusable()) {
                    unWrappedCluSetIds = (unWrappedCluSetIds == null) ?
                            new ArrayList<String>() : unWrappedCluSetIds;
                    unWrappedCluSetIds.add(subCluSet.getId());
                } else {
                    wrappedCluSets = (wrappedCluSets == null) ?
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

    private CluSetInfo getCluSetInfo(String cluSetId) {
        List<String> cluIds = null;
        CluSetInfo cluSetInfo = null;
        try {
            // note: the cluIds returned by cluService.getCluSetInfo also contains the clus
            //       that are the result of query parameter search.  Set to null here and
            //       retrieve the clus that are direct members.
            cluSetInfo = this.getCluService().getCluSet(cluSetId, ContextUtils.getContextInfo());
            cluSetInfo.setCluIds(null);
            cluIds = this.getCluService().getCluIdsFromCluSet(cluSetId, ContextUtils.getContextInfo());
            cluSetInfo.setCluIds(cluIds);
            upWrap(cluSetInfo, ContextUtils.getContextInfo());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return cluSetInfo;
    }

    public CluSetInfo createCourseSet(CluSetInformation cluSetInformation) {
        CluSetInfo cluSetInfo = new CluSetInfo();
        cluSetInfo.setTypeKey(CluServiceConstants.CLUSET_TYPE_CREDIT_COURSE);
        cluSetInfo.setStateKey("Active");
        cluSetInfo.setName("adhoc");
        cluSetInfo.setEffectiveDate(new Date());
        cluSetInfo.setIsReferenceable(Boolean.TRUE);
        cluSetInfo.setIsReusable(Boolean.FALSE);

        //Set the clu ids on the cluset
        if(cluSetInformation.getClus()!=null){
            for(CluInformation clu : cluSetInformation.getClus()){
                cluSetInfo.getCluIds().add(clu.getVerIndependentId());
            }
        }

        //Set the cluset ids on the cluset
        if(cluSetInformation.getCluSets()!=null){
            for(CluSetInfo cluset : cluSetInformation.getCluSets()){
                cluSetInfo.getCluSetIds().add(cluset.getId());
            }
        }


        return cluSetInfo;
    }

    protected CluService getCluService() {
        if (cluService == null) {
            cluService = (CluService) GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE, CluServiceConstants.SERVICE_NAME_LOCAL_PART));
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
