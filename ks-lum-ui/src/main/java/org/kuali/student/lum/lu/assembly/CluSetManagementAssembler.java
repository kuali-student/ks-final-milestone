package org.kuali.student.lum.lu.assembly;

import org.apache.log4j.Logger;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.student.core.assembly.BaseAssembler;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.lum.lu.assembly.data.client.LuData;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseHelper;
import org.kuali.student.lum.lu.service.LuService;
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

        LuData luData = new LuData();
        CreditCourseHelper result = CreditCourseHelper.wrap(luData);

        try {


        } catch (Exception e) {
            throw new AssemblyException("Could not ....", e);
        }

        return result.getData();
    }



    @Override
    public SaveResult<Data> save(Data input)     throws AssemblyException {

        try {
            SaveResult<Data> result = new SaveResult<Data>();
            
            return result;
        } catch (Exception e) {
            throw new AssemblyException("Unable to save ....", e);
        }
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
