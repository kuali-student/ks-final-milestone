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
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lu.assembly.data.client.LuData;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.MetaInfoHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.RichTextInfoHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CluHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CluSetHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseHelper;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluSetInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.lu.ui.tools.client.widgets.itemlist.CluItemValue;
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
            
            SaveResult<Data> cluSetResult = saveCluSet(input);
            result.setValidationResults(cluSetResult.getValidationResults());
            result.setValue(cluSetResult.getValue());
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
                System.out.println("Failed to update cluset");
                e.printStackTrace();
                throw new AssemblyException(e);
            }
        } else {
            try {
                //FIXME should user be specifying the type
                //TODO remove when done testing
                cluSetInfo.setType("Test type");
                // end of test code
                updatedCluSetInfo = luService.createCluSet(cluSetInfo.getType(), cluSetInfo);
            } catch (Exception e) {
                System.out.println("Failed to create cluset");
                e.printStackTrace();
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
                Data clusData = null;
                for (String cluId : cluSetInfo.getCluIds()) {
                    CluInfo cluInfo = null;
                    CluHelper cluHelper = CluHelper.wrap(new Data());
                    cluInfo = luService.getClu(cluId);
                    if (cluInfo != null) {
                        cluHelper.setId(cluInfo.getId());
                        cluHelper.setName(cluInfo.getOfficialIdentifier().getCode());
                    }
                    clusData = (clusData == null)? new Data() : clusData;
                    clusData.add(cluHelper.getData());
                }
                result.setClus(clusData);
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
        }
        return result;
    }
    
    private CluSetInfo toCluSetInfo(CluSetHelper cluSetHelper) {
        CluSetInfo cluSetInfo = new CluSetInfo();
        Data clusData = cluSetHelper.getClus();
        List<String> cluIds = null;
        
        cluSetInfo.setId(cluSetHelper.getId());
        for (Data.Property p : clusData) {
        	if(!"_runtimeData".equals(p.getKey())){
	            CluHelper cluHelper = CluHelper.wrap((Data)p.getValue());
	            cluIds = (cluIds == null)? new ArrayList<String>(3) :
	                cluIds;
	            cluIds.add(cluHelper.getId());
        	}
        }
        if (cluIds != null) {
            cluSetInfo.setCluIds(cluIds);
        }
        cluSetInfo.setAdminOrg(cluSetHelper.getOrganization());
        cluSetInfo.setDescr(toRichTextInfo(cluSetHelper.getDescription()));
        cluSetInfo.setEffectiveDate(cluSetHelper.getEffectiveDate());
        cluSetInfo.setExpirationDate(cluSetHelper.getExpirationDate());
        
        // TODO cluSetInfo.setMembershipQuery(membershipQuery)
//        TODO should metainfo be set here? cluSetInfo.setMetaInfo(cluSetHelper.getMetaInfo());
        cluSetInfo.setMetaInfo(toMetaInfo(cluSetHelper.getMetaInfo()));
        cluSetInfo.setName(cluSetHelper.getName());
        cluSetInfo.setState(cluSetHelper.getState());
        cluSetInfo.setType(cluSetHelper.getType());
        return cluSetInfo;
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

	@Override
	public SearchResult search(SearchRequest searchRequest) {
		// TODO Auto-generated method stub
		return null;
	}
}
