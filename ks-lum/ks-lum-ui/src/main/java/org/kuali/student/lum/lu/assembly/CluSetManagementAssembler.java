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

package org.kuali.student.lum.lu.assembly;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.lum.common.client.lo.MetaInfoHelper;
import org.kuali.student.lum.common.client.widgets.CluSetHelper;
import org.kuali.student.lum.common.client.widgets.CluSetRangeHelper;
import org.kuali.student.lum.common.client.widgets.CluSetRangeModelUtil;
import org.kuali.student.r1.common.assembly.data.AssemblyException;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Data.Property;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.r1.common.assembly.old.BaseAssembler;
import org.kuali.student.r1.common.assembly.old.data.SaveResult;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.ValidationResult.ErrorLevel;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly=true,rollbackFor={Throwable.class})
public class CluSetManagementAssembler extends BaseAssembler<Data, Void> {
//  TODO Split out CluInfo assembly to its own class

    private static final Logger LOG = LoggerFactory.getLogger(CluSetManagementAssembler.class);

    public static final String JOINT_RELATION_TYPE = "kuali.lu.relation.type.co-located";
// FIXME: should have it's own proposal types
    public static final String FORMAT_LU_TYPE = "kuali.lu.type.CreditCourseFormatShell";

    public static final String FORMAT_RELATION_TYPE = "luLuRelationType.hasCourseFormat";
    public static final String ACTIVITY_RELATION_TYPE = "luLuRelationType.contains";

    public static final String PROPOSAL_REFERENCE_TYPE = "kuali.proposal.referenceType.clu"; // <- what the service says, but the dictionary says: "kuali.referenceType.CLU";
//    public static final String CREDIT_COURSE_PROPOSAL_DATA_TYPE = "CreditCourseProposal";
    public static final String CLUSET_DATA_TYPE = "cluset";

    private CluService cluService;
    private MetadataServiceImpl metadataService;

    public MetadataServiceImpl getMetadataService() {
        return metadataService;
    }

    public void setMetadataService(MetadataServiceImpl metadataService) {
        this.metadataService = metadataService;
    }

    @Override
    public Data get(String id) throws AssemblyException {

        CluSetHelper resultCluSetHelper = null;
        Data resultData = null;

        try {
            CluSetInfo cluSetInfo = getCluSetInfo(id);
            resultCluSetHelper = toCluSetHelper(cluSetInfo);
            if (resultCluSetHelper == null) {
                resultData = null;
            } else {
//                resultData = new Data();
//                resultData.set("cluset", resultCluSetHelper.getData());
                resultData = resultCluSetHelper.getData();
            }
        } catch (Exception e) {
            throw new AssemblyException("Could not retrive cluSet with id " + id, e);
        }

        return resultData;
    }
    
    public CluSetInfo getCluSetInfo(String cluSetId) throws Exception {
        List<String> cluIds = null;
        CluSetInfo cluSetInfo = null;
        // note: the cluIds returned by cluService.getCluSetInfo also contains the clus
        //       that are the result of query parameter search.  Set to null here and
        //       retrieve the clus that are direct members.
        cluSetInfo = cluService.getCluSet(cluSetId, ContextUtils.getContextInfo());
        cluSetInfo.setCluIds(null);
        cluIds = cluService.getCluIdsFromCluSet(cluSetId, ContextUtils.getContextInfo());
        cluSetInfo.setCluIds(cluIds);
        upWrap(cluSetInfo);
        return cluSetInfo;
    }

    public MetaInfoHelper toMetaInfoHelper(MetaInfo metaInfo) {
        MetaInfoHelper metaInfoHelper = null;
        Data metaData = new Data();
        if (metaInfo == null) return null;
        metaInfoHelper = MetaInfoHelper.wrap(metaData);
        metaInfoHelper.setCreateId(metaInfo.getCreateId());
        metaInfoHelper.setCreateTime(metaInfo.getCreateTime());
        metaInfoHelper.setUpdateId(metaInfo.getUpdateId());
        metaInfoHelper.setUpdateTime(metaInfo.getUpdateTime());
        metaInfoHelper.setVersionInd(metaInfo.getVersionInd());
        return metaInfoHelper;
    }

    public MetaInfo toMetaInfo(MetaInfoHelper metaInfoHelper) {
        MetaInfo metaInfo = null;
        if (metaInfoHelper == null) return null;
        metaInfo = new MetaInfo();
        metaInfo.setCreateId(metaInfoHelper.getCreateId());
        metaInfo.setCreateTime(metaInfoHelper.getCreateTime());
        metaInfo.setUpdateId(metaInfoHelper.getUpdateId());
        metaInfo.setUpdateTime(metaInfoHelper.getUpdateTime());
        metaInfo.setVersionInd(metaInfoHelper.getVersionInd());
        return metaInfo;
    }

    public String richTextToString(RichTextInfo richTextInfo) {
        String result = null;
        if (richTextInfo == null) return null;
        result = richTextInfo.getPlain();
        return result;
    }

    @Override
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
    public SaveResult<Data> save(Data input)     throws AssemblyException {

        try {
            SaveResult<Data> result = new SaveResult<Data>();
            List<ValidationResultInfo> validationResults = validate(input);
            if (hasValidationErrors(validationResults)) {
                result.setValidationResults(validationResults);
                result.setValue(input);
                return result;
            }

            SaveResult<Data> clusetResult = saveCluSet(input);
            result.setValidationResults(clusetResult.getValidationResults());
            result.setValue(clusetResult.getValue());
            return result;
        } catch (Exception e) {
            throw new AssemblyException("Unable to save ....", e);
        }
    }

    private void upWrap(CluSetInfo cluSetInfo) throws AssemblyException {
        List<String> cluSetIds = (cluSetInfo == null)? null : cluSetInfo.getCluSetIds();
        List<String> unWrappedCluSetIds = null;
        List<CluSetInfo> wrappedCluSets = null;
        List<CluSetInfo> subCluSets = null;

        try {
            if (cluSetIds != null && !cluSetIds.isEmpty()) {
                subCluSets = cluService.getCluSetsByIds(cluSetIds, ContextUtils.getContextInfo());
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

    private void wrap(CluSetInfo cluSetInfo) throws AssemblyException {
        int numCluSetElementTypes = 0;
        boolean hasCluIds = false;
        boolean hasCluSetIds = false;
        boolean hasMembershipQuery = false;
        List<String> wrapperCluSetIds = new ArrayList<String>();
        MembershipQueryInfo mqInfo = null;
        if (cluSetInfo.getCluIds() != null && !cluSetInfo.getCluIds().isEmpty()) {
            numCluSetElementTypes++;
            hasCluIds = true;
        }
        if (cluSetInfo.getCluSetIds() != null && !cluSetInfo.getCluSetIds().isEmpty()) {
            numCluSetElementTypes++;
            hasCluSetIds = true;
        }
        mqInfo = cluSetInfo.getMembershipQuery();
        if (mqInfo != null && mqInfo.getSearchTypeKey() != null && !mqInfo.getSearchTypeKey().isEmpty()) {
            numCluSetElementTypes++;
            hasMembershipQuery = true;
        }
        // if more than one type
        if (numCluSetElementTypes > 1) {
            if (hasCluIds) {
                CluSetInfo wrapperCluSet = new CluSetInfo();
                setWrapperCluSetInfoValues(wrapperCluSet, cluSetInfo);
                // copy the clus into the wrapper CluSet
                wrapperCluSet.setCluIds(cluSetInfo.getCluIds());
                cluSetInfo.setCluIds(null);
                try {
                    if (wrapperCluSet.getTypeKey() == null) {
                	    wrapperCluSet.setTypeKey("kuali.cluSet.type.CreditCourse");
                    }
                    wrapperCluSet = cluService.createCluSet(wrapperCluSet.getTypeKey(), wrapperCluSet, ContextUtils.getContextInfo());
                } catch (Exception e) {
                    LOG.error("Failed to create wrapper cluset",e);
                    throw new AssemblyException(e);
                }
                wrapperCluSetIds.add(wrapperCluSet.getId());
            }
            if (hasMembershipQuery) {
                CluSetInfo wrapperCluSet = new CluSetInfo();
                setWrapperCluSetInfoValues(wrapperCluSet, cluSetInfo);
                // copy the MembershipQuery into the wrapper CluSet
                wrapperCluSet.setMembershipQuery(mqInfo);
                cluSetInfo.setMembershipQuery(null);
                try {
                    wrapperCluSet = cluService.createCluSet(wrapperCluSet.getTypeKey(), wrapperCluSet, ContextUtils.getContextInfo());
                } catch (Exception e) {
                    LOG.error("Failed to create wrapper cluset",e);
                    throw new AssemblyException(e);
                }
                wrapperCluSetIds.add(wrapperCluSet.getId());
            }
            if (hasCluSetIds) {
                wrapperCluSetIds.addAll(cluSetInfo.getCluSetIds());
            }
            cluSetInfo.setCluSetIds(wrapperCluSetIds);
        }
    }

    private void setWrapperCluSetInfoValues(CluSetInfo wrapperCluSet, CluSetInfo cluSetInfo) {
        wrapperCluSet.setAdminOrg(cluSetInfo.getAdminOrg());
        wrapperCluSet.setEffectiveDate(cluSetInfo.getEffectiveDate());
        wrapperCluSet.setExpirationDate(cluSetInfo.getExpirationDate());
        wrapperCluSet.setIsReusable(false);
        wrapperCluSet.setIsReferenceable(false);
        wrapperCluSet.setName(cluSetInfo.getName());
        wrapperCluSet.setStateKey(cluSetInfo.getStateKey());
        wrapperCluSet.setTypeKey(cluSetInfo.getTypeKey());
    }

    private SaveResult<Data> saveCluSet(Data input) throws AssemblyException {
        SaveResult<Data> result = new SaveResult<Data>();
//        CluSetHelper cluSetHelper = new CluSetHelper((Data)input.get("cluset"));
        List<ValidationResultInfo> saveValidationResults = null;
        CluSetHelper cluSetHelper = new CluSetHelper(input);
        CluSetInfo cluSetInfo = toCluSetInfo(cluSetHelper);
        CluSetInfo updatedCluSetInfo = null;
        CluSetHelper resultCluSetHelper = null;
        Data resultData = null;
        wrap(cluSetInfo);
        
        if ((cluSetInfo.getCluIds() == null || cluSetInfo.getCluIds().isEmpty()) &&
                (cluSetInfo.getCluSetIds() == null || cluSetInfo.getCluSetIds().isEmpty()) &&
                (cluSetInfo.getMembershipQuery() == null)){
            ValidationResultInfo cluSetCannotBeEmpty = new ValidationResultInfo();
            saveValidationResults = (saveValidationResults == null)? new ArrayList<ValidationResultInfo>() :
                saveValidationResults;
            result.setValue(null);
            cluSetCannotBeEmpty.setElement("");
            cluSetCannotBeEmpty.setMessage("Clu set cannot be empty");
            cluSetCannotBeEmpty.setError("Clu set cannot be empty");
            cluSetCannotBeEmpty.setLevel(ErrorLevel.ERROR);
            saveValidationResults.add(cluSetCannotBeEmpty);
            result.setValidationResults(saveValidationResults);
            return result;
        }
        
        if (cluSetInfo.getId() != null && cluSetInfo.getId().trim().length() > 0) {
            try {
                updatedCluSetInfo = cluService.updateCluSet(cluSetInfo.getId(), cluSetInfo, ContextUtils.getContextInfo());
            } catch (Exception e) {
            	LOG.error("Failed to update cluset",e);
                throw new AssemblyException(e);
            }
        } else {
            try {
                if (cluSetInfo.getTypeKey() == null) {
                    cluSetInfo.setTypeKey("kuali.cluSet.type.CreditCourse");
                }
                updatedCluSetInfo = cluService.createCluSet(cluSetInfo.getTypeKey(), cluSetInfo, ContextUtils.getContextInfo());
            } catch (Exception e) {
                LOG.error("Failed to create cluset",e);
                throw new AssemblyException(e);
            }
        }
        try {
            resultCluSetHelper = toCluSetHelper(updatedCluSetInfo);
        } catch (Exception e) {
            throw new AssemblyException(e);
        }
        if (resultCluSetHelper == null) {
            resultData = null;
        } else {
//            resultData = new Data();
//            resultData.set("cluset", resultCluSetHelper.getData());
            resultData = resultCluSetHelper.getData();
        }
        result.setValue(resultData);
        return result;
    }

    private List<String> getMembershipQuerySearchResult(MembershipQueryInfo query) throws MissingParameterException, PermissionDeniedException, OperationFailedException, InvalidParameterException {
        if(query == null) {
            return null;
        }
        SearchRequestInfo sr = new SearchRequestInfo();
        sr.setSearchKey(query.getSearchTypeKey());
        sr.setParams(query.getQueryParamValues());

        SearchResultInfo result = cluService.search(sr, ContextUtils.getContextInfo());

        List<String> cluIds = new ArrayList<String>();
        List<SearchResultRowInfo> rows = result.getRows();
        for(SearchResultRowInfo row : rows) {
            List<SearchResultCellInfo> cells = row.getCells();
            for(SearchResultCellInfo cell : cells) {
                if(cell.getKey().equals("lu.resultColumn.cluId")) {
                    cluIds.add(cell.getValue());
                }
            }
        }
        return cluIds;
    }

    private CluSetHelper toCluSetHelper(CluSetInfo cluSetInfo) throws Exception {
        Data data = new Data();
        Data cluSetDetailData = new Data();
        data.set("cluset", cluSetDetailData);
        CluSetHelper result = CluSetHelper.wrap(cluSetDetailData);
        if (cluSetInfo != null) {
            if (cluSetInfo.getCluIds() != null && !cluSetInfo.getCluIds().isEmpty()) {
            	List<CluInfo> cluInfos = new ArrayList<CluInfo>();
            	for(String id:cluSetInfo.getCluIds()){
            		VersionDisplayInfo versionInfo = cluService.getCurrentVersion(CluServiceConstants.CLU_NAMESPACE_URI, id, ContextUtils.getContextInfo());
            		cluInfos.add(cluService.getClu(versionInfo.getId(), ContextUtils.getContextInfo()));
            	}
                result.setApprovedClus(new Data());
                for (CluInfo cluInfo : cluInfos) {
                    if (cluInfo.getStateKey().equals("Active")) {
                        result.getApprovedClus().add(cluInfo.getVersion().getVersionIndId());
                    } else {
                        result.getProposedClus().add(cluInfo.getVersion().getVersionIndId());
                    }
                    result.getAllClus().add(cluInfo.getVersion().getVersionIndId());
                }
            }
            if (cluSetInfo.getCluSetIds() != null && !cluSetInfo.getCluSetIds().isEmpty()) {
                result.setCluSets(new Data());
                for (String cluSetId : cluSetInfo.getCluSetIds()) {
                    result.getCluSets().add(cluSetId);
                }
            }
            if (cluSetInfo.getMembershipQuery() != null) {
                MembershipQueryInfo mq = cluSetInfo.getMembershipQuery();
                List<String> cluRangeCluIds = getMembershipQuerySearchResult(mq);
                if (cluRangeCluIds != null) {
                    result.setCluRangeViewDetails(new Data());
                    for (String cluRangeCluId : cluRangeCluIds) {
                        result.getCluRangeViewDetails().add(cluRangeCluId);
                    }
                }
            }
            result.setDescription(richTextToString(cluSetInfo.getDescr()));
            result.setEffectiveDate(cluSetInfo.getEffectiveDate());
            result.setExpirationDate(cluSetInfo.getExpirationDate());
            result.setId(cluSetInfo.getId());
            result.setMetaInfo(toMetaInfoHelper(cluSetInfo.getMeta()));
            result.setName(cluSetInfo.getName());
            result.setOrganization(cluSetInfo.getAdminOrg());
            result.setState(cluSetInfo.getStateKey());
            result.setType(cluSetInfo.getTypeKey());
            result.setCluRangeParams(CluSetRangeModelUtil.INSTANCE.toData(
                    cluSetInfo.getMembershipQuery()));
            
            //Add all dynamic attributes
            for (AttributeInfo attrInfo : cluSetInfo.getAttributes()) {
                result.getData().set(attrInfo.getKey(), attrInfo.getValue());
            }
        }
        return result;
    }

    private void addToCluIds(Data clusData, final List<String> cluIds) {
        if (clusData != null) {
            for (Data.Property p : clusData) {
                if(!"_runtimeData".equals(p.getKey())){
                    String cluId = p.getValue();
                    cluIds.add(cluId);
                }
            }
        }
    }

    private CluSetInfo toCluSetInfo(CluSetHelper cluSetHelper) {
        CluSetInfo cluSetInfo = new CluSetInfo();
        Data approvedClusData = cluSetHelper.getApprovedClus();
        Data proposedClusData = cluSetHelper.getProposedClus();
        Data cluSetsData = cluSetHelper.getCluSets();
        final List<String> cluIds = new ArrayList<String>();
        List<String> cluSetIds = null;

        cluSetInfo.setId(cluSetHelper.getId());
        if (approvedClusData != null) {
            addToCluIds(approvedClusData, cluIds);
        }
        if (proposedClusData != null) {
            addToCluIds(proposedClusData, cluIds);
        }
        if (cluIds != null && !cluIds.isEmpty()) {
            cluSetInfo.setCluIds(cluIds);
        }
        if (cluSetsData != null) {
            for (Data.Property p : cluSetsData) {
                if(!"_runtimeData".equals(p.getKey())){
                    String cluSetId = p.getValue();
                    cluSetIds = (cluSetIds == null)? new ArrayList<String>(3) :
                        cluSetIds;
                    cluSetIds.add(cluSetId);
                }
            }
        }
        if (cluSetIds != null) {
            cluSetInfo.setCluSetIds(cluSetIds);
        }
        cluSetInfo.setAdminOrg(cluSetHelper.getOrganization());
        cluSetInfo.setDescr(toRichTextInfo(cluSetHelper.getDescription()));
        cluSetInfo.setEffectiveDate(cluSetHelper.getEffectiveDate());
        cluSetInfo.setExpirationDate(cluSetHelper.getExpirationDate());
        cluSetInfo.setMembershipQuery(toMembershipQueryInfo(cluSetHelper.getCluRangeParams()));

        cluSetInfo.setMeta(toMetaInfo(cluSetHelper.getMetaInfo()));
        cluSetInfo.setName(cluSetHelper.getName());
        cluSetInfo.setStateKey(cluSetHelper.getState());
        if (cluSetInfo.getStateKey() == null) {
            cluSetInfo.setStateKey("Active");
        }
        cluSetInfo.setTypeKey(cluSetHelper.getType());
        cluSetInfo.setIsReusable(cluSetHelper.getReusable());
        cluSetInfo.setIsReferenceable(cluSetHelper.getReferenceable());
        
        //Put in any additional properties
        for(Iterator<Property> iter=cluSetHelper.getData().realPropertyIterator();iter.hasNext();){
            Property property = iter.next();
            if(property.getValue()!=null && !(property.getValue() instanceof Data ) && !CluSetHelper.getProperties().contains((String)property.getKey())){
                //Add this as a dynamic attribute
                cluSetInfo.getAttributes().add(new AttributeInfo((String)property.getKey(), property.getValue().toString()));
            }
        }
        
        return cluSetInfo;
    }

    private MembershipQueryInfo toMembershipQueryInfo(CluSetRangeHelper cluSetRangeHelper) {
        return CluSetRangeModelUtil.INSTANCE.toMembershipQueryInfo(cluSetRangeHelper.getData());
    }

    private RichTextInfo toRichTextInfo(String text) {
        RichTextInfo result = new RichTextInfo();
        if (text == null) return null;
        result.setPlain(text);
        result.setFormatted(text);
        return result;
    }

    @Override
    public Data assemble(Void input) throws AssemblyException {
        throw new UnsupportedOperationException("Data assembly not supported");
    }

    @Override
    public Void disassemble(Data input) throws AssemblyException {
        throw new UnsupportedOperationException("Data disassembly not supported");
    }

    public CluService getCluService() {
        return cluService;
    }

    public void setCluService(CluService cluService) {
        this.cluService = cluService;
    }

	@Override
	protected String getDataType() {
		return CLUSET_DATA_TYPE;
	}
	
	@Override
    public Metadata getDefaultMetadata() {
        // TODO Auto-generated method stub
        return metadataService.getMetadata(getDataType());
    }

    @Override
	protected String getDocumentPropertyName() {
        return "course";							//FIXME
	}

	@Override
	protected String getDtoName() {
        return "kuali.lu.type.CreditCourse";		//FIXME
	}

	@Override
	protected Map<String,String> getQualification(String idType, String id) {   //FIXME
		String DOCUMENT_TYPE_NAME = "documentTypeName";
		Map<String,String> qualification = new LinkedHashMap<String,String>();
		qualification.put(DOCUMENT_TYPE_NAME, "CluCreditCourse");
		/*
		 *	This commented out for permission changes
		 *
		 *         String QUALIFICATION_PROPOSAL_ID = "courseId";
		 *         qualification.put(QUALIFICATION_PROPOSAL_ID, id);
		 */
		qualification.put(idType, id);
		return qualification;
	}
	
}
