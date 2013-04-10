package org.kuali.student.enrollment.class1.krms.builder;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krms.builder.ComponentBuilder;
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
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/03/01
 * Time: 11:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProgramComponentBuilder implements ComponentBuilder<EnrolPropositionEditor> {


    private CluService cluService;

    private LRCService lrcService;

    private static final String PROGRAM_CLUSET_KEY = "kuali.term.parameter.type.program.cluSet.id";

    @Override
    public List<String> getComponentIds() {
        return null;
    }

    @Override
    public void resolveTermParameters(EnrolPropositionEditor propositionEditor, Map<String, String> termParameters) {
        String cluSetId = termParameters.get(PROGRAM_CLUSET_KEY);
        if (cluSetId != null) {
            try {
                propositionEditor.setCluSet(this.getCluSetInformation(cluSetId));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public Map<String, String> buildTermParameters(EnrolPropositionEditor propositionEditor) {
        return null;
    }

    public CluSetInformation getCluSetInformation(String cluSetId) {
        CluSetInformation result = new CluSetInformation();
        CluSetInfo cluSetInfo = getCluSetInfo(cluSetId, ContextUtils.getContextInfo());
        result.setCluSetInfo(cluSetInfo);

        //Set the Clu Informations
        List<CluInformation> clus = getCluInformations(cluSetInfo.getCluIds(), ContextUtils.getContextInfo());
        result.setClus(clus);

        //Set the Clu set infos
        List<CluSetInfo> cluSetInfos = getCluSetInfos(cluSetInfo.getCluSetIds(), ContextUtils.getContextInfo());
        result.setCluSets(cluSetInfos);

        if (result.getClus() != null)
            Collections.sort(result.getClus());
        return result;
    }

    private List<CluSetInfo> getCluSetInfos(List<String> cluSetIds, ContextInfo contextInfo) {
        List<CluSetInfo> clusetInfos = new ArrayList<CluSetInfo>();
        if (cluSetIds != null) {
            for (String cluSetId : cluSetIds) {
                clusetInfos.add(getCluSetInfo(cluSetId, contextInfo));
            }
        }
        return clusetInfos;
    }

    private List<CluInformation> getCluInformations(List<String> cluIds, ContextInfo contextInfo) {
        List<CluInformation> result = new ArrayList<CluInformation>();
        if (cluIds != null) {
            for (String cluId : cluIds) {
                try {
                    VersionDisplayInfo versionInfo = this.getCluService().getCurrentVersion(CluServiceConstants.CLU_NAMESPACE_URI, cluId, contextInfo);
                    CluInfo cluInfo = this.getCluService().getClu(versionInfo.getId(), contextInfo);
                    if (cluInfo != null) {

                        //retrieve credits
                        String credits = "";
                        List<CluResultInfo> cluResultInfos = this.getCluService().getCluResultByClu(versionInfo.getId(), contextInfo);
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
                                            resultComponentInfo = this.getLrcService().getResultValuesGroup(resultOption.getResultComponentId(), contextInfo);
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
                            List<String> clus = this.getCluService().getCluIdsByRelatedCluAndRelationType(cluInfo.getId(), "kuali.lu.lu.relation.type.hasVariationProgram", contextInfo);
                            if (clus == null || clus.size() == 0) {
                                throw new RuntimeException("Statement Dependency clu found, but no parent Program exists");
                            } else if (clus.size() > 1) {
                                throw new RuntimeException("Statement Dependency clu can only have one parent Program relation");
                            }
                            cluInformation.setParentCluId(clus.get(0));
                        }

                        cluInformation.setVerIndependentId(cluInfo.getId());
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

    private CluSetInfo getCluSetInfo(String cluSetId, ContextInfo contextInfo) {
        List<String> cluIds = null;
        CluSetInfo cluSetInfo = null;
        try {
            // note: the cluIds returned by cluService.getCluSetInfo also contains the clus
            //       that are the result of query parameter search.  Set to null here and
            //       retrieve the clus that are direct members.
            cluSetInfo = this.getCluService().getCluSet(cluSetId, contextInfo);
            cluSetInfo.setCluIds(null);
            cluIds = this.getCluService().getCluIdsFromCluSet(cluSetId, contextInfo);
            cluSetInfo.setCluIds(cluIds);
            upWrap(cluSetInfo, contextInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
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
