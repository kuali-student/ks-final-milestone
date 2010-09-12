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
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.student.core.assembly.BaseAssembler;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.MetaInfoHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CluSetHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CluSetRangeHelper;
import org.kuali.student.lum.lu.dto.CluSetInfo;
import org.kuali.student.lum.lu.dto.MembershipQueryInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.lu.ui.tools.client.widgets.itemlist.CluSetRangeModelUtil;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor={Throwable.class})
public class CluSetManagementAssembler extends BaseAssembler<Data, Void> {
//  TODO Split out CluInfo assembly to its own class

    final Logger LOG = Logger.getLogger(CluSetManagementAssembler.class);

    public static final String JOINT_RELATION_TYPE = "kuali.lu.relation.type.co-located";
    public static final String PROPOSAL_TYPE_CREATE_COURSE = "kuali.proposal.type.course.create";
    public static final String FORMAT_LU_TYPE = "kuali.lu.type.CreditCourseFormatShell";

    public static final String FORMAT_RELATION_TYPE = "luLuRelationType.hasCourseFormat";
    public static final String ACTIVITY_RELATION_TYPE = "luLuRelationType.contains";

    public static final String PROPOSAL_REFERENCE_TYPE = "kuali.proposal.referenceType.clu"; // <- what the service says, but the dictionary says: "kuali.referenceType.CLU";
//    public static final String CREDIT_COURSE_PROPOSAL_DATA_TYPE = "CreditCourseProposal";
    public static final String CLUSET_DATA_TYPE = "CluSetManagement";

    private LuService luService;

    @Override
    public Data get(String id) throws AssemblyException {

        CluSetHelper resultCluSetHelper = null;
        Data resultData = null;

        try {
            CluSetInfo cluSetInfo = luService.getCluSetInfo(id);
            resultCluSetHelper = toCluSetHelper(cluSetInfo);
            if (resultCluSetHelper == null) {
                resultData = null;
            } else {
                resultData = new Data();
                resultData.set("cluset", resultCluSetHelper.getData());
            }
        } catch (Exception e) {
            throw new AssemblyException("Could not retrive cluSet with id " + id, e);
        }

        return resultData;
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
    
    private SaveResult<Data> saveCluSet(Data input) throws AssemblyException {
        SaveResult<Data> result = new SaveResult<Data>();
        CluSetHelper cluSetHelper = new CluSetHelper((Data)input.get("cluset"));
        CluSetInfo cluSetInfo = toCluSetInfo(cluSetHelper);
        CluSetInfo updatedCluSetInfo = null;
        CluSetHelper resultCluSetHelper = null;
        Data resultData = null;
        if (cluSetInfo.getId() != null && cluSetInfo.getId().trim().length() > 0) {
            try {
                updatedCluSetInfo = luService.updateCluSet(cluSetInfo.getId(), cluSetInfo);
            } catch (Exception e) {
            	LOG.error("Failed to update cluset",e);
                throw new AssemblyException(e);
            }
        } else {
            try {
                //FIXME should user be specifying the type
                //TODO remove when done testing
                cluSetInfo.setType("kuali.cluSet.type.creditCourse");
                // end of test code
                updatedCluSetInfo = luService.createCluSet(cluSetInfo.getType(), cluSetInfo);
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
            resultData = new Data();
            resultData.set("cluset", resultCluSetHelper.getData());
        }
        result.setValue(resultData);
        return result;
    }
    
    private CluSetHelper toCluSetHelper(CluSetInfo cluSetInfo) throws Exception {
        Data data = new Data();
        Data cluSetDetailData = new Data();
        data.set("cluset", cluSetDetailData);
        CluSetHelper result = CluSetHelper.wrap(cluSetDetailData);
        if (cluSetInfo != null) {
//          result.setClus
            if (cluSetInfo.getCluIds() != null) {
                result.setClus(new Data());
                for (String cluId : cluSetInfo.getCluIds()) {
                    result.getClus().add(cluId);
                }
            }
            result.setDescription(richTextToString(cluSetInfo.getDescr()));
            result.setEffectiveDate(cluSetInfo.getEffectiveDate());
            result.setExpirationDate(cluSetInfo.getExpirationDate());
            result.setId(cluSetInfo.getId());
            result.setMetaInfo(toMetaInfoHelper(cluSetInfo.getMetaInfo()));
            result.setName(cluSetInfo.getName());
            result.setOrganization(cluSetInfo.getAdminOrg());
            result.setState(cluSetInfo.getState());
            result.setType(cluSetInfo.getType());
            result.setCluRangeParams(CluSetRangeModelUtil.INSTANCE.toData(
                    cluSetInfo.getMembershipQuery()));
        }
        return result;
    }
    
    private CluSetInfo toCluSetInfo(CluSetHelper cluSetHelper) {
        CluSetInfo cluSetInfo = new CluSetInfo();
        Data clusData = cluSetHelper.getClus();
        List<String> cluIds = null;
        
        cluSetInfo.setId(cluSetHelper.getId());
        if (clusData != null) {
            for (Data.Property p : clusData) {
                if(!"_runtimeData".equals(p.getKey())){
                    String cluId = p.getValue();
                    cluIds = (cluIds == null)? new ArrayList<String>(3) :
                        cluIds;
                    cluIds.add(cluId);
                }
            }
        }
        if (cluIds != null) {
            cluSetInfo.setCluIds(cluIds);
        }
        cluSetInfo.setAdminOrg(cluSetHelper.getOrganization());
        cluSetInfo.setDescr(toRichTextInfo(cluSetHelper.getDescription()));
        cluSetInfo.setEffectiveDate(cluSetHelper.getEffectiveDate());
        cluSetInfo.setExpirationDate(cluSetHelper.getExpirationDate());
        cluSetInfo.setMembershipQuery(toMembershipQueryInfo(cluSetHelper.getCluRangeParams()));
        
        // TODO cluSetInfo.setMembershipQuery(membershipQuery)
//        TODO should metainfo be set here? cluSetInfo.setMetaInfo(cluSetHelper.getMetaInfo());
        cluSetInfo.setMetaInfo(toMetaInfo(cluSetHelper.getMetaInfo()));
        cluSetInfo.setName(cluSetHelper.getName());
        cluSetInfo.setState(cluSetHelper.getState());
        cluSetInfo.setType(cluSetHelper.getType());
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
    
    public LuService getLuService() {
        return luService;
    }

    public void setLuService(LuService luService) {
        this.luService = luService;
    } 

	@Override
	protected String getDataType() {
		return CLUSET_DATA_TYPE;
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
	protected AttributeSet getQualification(String idType, String id) {   //FIXME
		String DOCUMENT_TYPE_NAME = "documentTypeName";
		AttributeSet qualification = new AttributeSet();
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
