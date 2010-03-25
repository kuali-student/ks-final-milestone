package org.kuali.student.lum.lu.assembly;

import static org.kuali.student.core.assembly.util.AssemblerUtils.addVersionIndicator;
import static org.kuali.student.core.assembly.util.AssemblerUtils.getVersionIndicator;
import static org.kuali.student.core.assembly.util.AssemblerUtils.isDeleted;
import static org.kuali.student.core.assembly.util.AssemblerUtils.isModified;
import static org.kuali.student.core.assembly.util.AssemblerUtils.setUpdated;

import java.util.List;
import java.util.Map;

import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.student.brms.statement.service.StatementService;
import org.kuali.student.core.assembly.Assembler;
import org.kuali.student.core.assembly.BaseAssembler;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.assembly.data.Data.Property;
import org.kuali.student.core.atp.service.AtpService;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.proposal.service.ProposalService;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.service.impl.SearchDispatcherImpl;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lo.service.LearningObjectiveService;
import org.kuali.student.lum.lu.assembly.data.client.LuData;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.RichTextInfoHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalInfoHelper;
import org.kuali.student.lum.lu.assembly.data.server.CluInfoHierarchy;
import org.kuali.student.lum.lu.service.LuService;
import org.springframework.transaction.annotation.Transactional;

/*
 *  ASSEMBLERREVIEW
 *  1) The type/state config in here is a mess.
 *      - some of the type/state info was just copied from other places, 
 *          just a hackjob to get it working
 *      - bigger issue is how to handle state transitions, etc.  not handling 
 *          it right now, but it would be nice to come up with a clean/standard
 *          way of doing it
 *  
 *  2) Need to come up with a clean way of copying properties that are passthrough without a lot of boilerplate code, but...
 *      - many "passthrough" properties will still have a rename along the way, and possibly a transformation of their position within the graph, e.g.
 *          cluInfo/officialIdentifier/shortName -> proposal/transcriptTitle  
 *  
 *  3) Disconnect on assembler type/state config
 *      - some assemblers will be specific to a type
 *      - getMetadata takes in type as a parameter
 * 
 *  4) Propagation of version indicator information is messy
 *      - services require the version indicator for locking purposes
 *      - assemblers need to be stateless, so it needs to be passed down as part of the returned orchestration
 *      - resulting orchestrations will have complex types assembled from multiple objects each having their own version indicator
 */
@Transactional(rollbackFor={Throwable.class})
public class CreditCourseProposalAssembler extends BaseAssembler<Data, Void> {
    // TODO make sure that cluclurelation version indicators are carried over on retrieval
    // TODO verify that the right relation types have been used
    // public static final String FORMAT_RELATION_TYPE =
    // LUConstants.LU_LU_RELATION_TYPE_HAS_COURSE_FORMAT;
    // public static final String ACTIVITY_RELATION_TYPE =
    // LUConstants.LU_LU_RELATION_TYPE_CONTAINS;
    public static final String PROPOSAL_REFERENCE_TYPE = "kuali.proposal.referenceType.clu"; // <- what the service says, but the dictionary says: "kuali.referenceType.CLU";
    public static final String PROPOSAL_TYPE_CREATE_COURSE = "kuali.proposal.type.course.create";
    public static final String CREDIT_COURSE_PROPOSAL_DATA_TYPE = "CreditCourseProposal";
    
    
    private final String proposalState;
    private Assembler<Data, CluInfoHierarchy> courseAssembler ;
//    private CluInfoHierarchyAssembler cluHierarchyAssembler;
    private final RichTextInfoAssembler richtextAssembler = new RichTextInfoAssembler();
//    private final TimeAmountInfoAssembler timeamountAssembler = new TimeAmountInfoAssembler();
//    private final CluIdentifierInfoAssembler cluIdentifierAssembler = new CluIdentifierInfoAssembler();
//    private final CluInstructorInfoDataAssembler instructorAssembler = new CluInstructorInfoDataAssembler();
    private ProposalService proposalService;
    private LuService luService;
    private StatementService statementService;
    private LearningObjectiveService loService;
    private OrganizationService orgService;
    private AtpService atpService;
    
    private SearchDispatcherImpl searchDispatcher;
    
    
    public CreditCourseProposalAssembler(String proposalState) {
        this.proposalState = proposalState;
    }
    
    @Override
    public Data get(String id) throws AssemblyException {
        LuData luData = new LuData();
        CreditCourseProposalHelper result = CreditCourseProposalHelper.wrap(luData);
        try {
        	CreditCourseProposalInfoHelper proposal = getProposal(id);
            if (proposal == null) {
                return null;
            }
            Data references = proposal.getReferences();
                if (references.size() != 1) {
                    throw new AssemblyException(
                            "Invalid number of referenced courses for proposal: "
                                    + references.size());
                }
            String cluId = references.get(0);
            
            CreditCourseHelper course = getCourse(cluId);
            //FIXME: This is a bit clunky. Think of a better way to do this!    
            // Maybe UI shouldn't expect LuData as the top level container?           
            LuData current = (LuData)course.getData();
            result.setProposal(proposal);
            result.setCourse(CreditCourseHelper.wrap(current.getData()));
            luData.setRuleInfos(current.getRuleInfos());
            
        } catch (Exception e) {
            throw new AssemblyException("Could not assemble credit course proposal", e);
        }

        return result.getData();
    }

    private Assembler<Data, CluInfoHierarchy> getCourseAssembler() {
        return courseAssembler;
    }

    private CreditCourseHelper getCourse(String cluId)
            throws AssemblyException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {

        CreditCourseHelper course = CreditCourseHelper.wrap(getCourseAssembler().get(cluId));

        return course;

    }
   
    private CreditCourseProposalInfoHelper getProposal(String id)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        CreditCourseProposalInfoHelper result = null;

        ProposalInfo prop = proposalService.getProposal(id);
        if (prop != null) {
            result = copyProposalDTO(prop);
        }

        return result;
    }

    private CreditCourseProposalInfoHelper copyProposalDTO(ProposalInfo prop) {
        CreditCourseProposalInfoHelper result = CreditCourseProposalInfoHelper.wrap(new Data());

        result.setId(prop.getId());
        result.setRationale(prop.getRationale());
        List<String> tmp = prop.getProposerPerson();
        if (tmp != null) {
            result.setProposerPerson(new Data());
            for (String s : tmp) {
                result.getProposerPerson().add(s);
            }
        }
        result.setTitle(prop.getName());
        result.setReferenceType(prop.getProposalReferenceType());
        result.setReferences(new Data());
        for (String s : prop.getProposalReference()) {
            result.getReferences().add(s);
        }
        
        addVersionIndicator(result.getData(), ProposalInfo.class.getName(), prop.getId(), prop.getMetaInfo().getVersionInd());

        return result;
    }

    @Override
    public SaveResult<Data> save(Data data) throws AssemblyException {
    	try {
            SaveResult<Data> result = new SaveResult<Data>();
            List<ValidationResultInfo> validationResults = validate(data);
            if (hasValidationErrors(validationResults)) {
                result.setValidationResults(validationResults);
            	result.setValue(data);
                return result;
            }

            CreditCourseProposalHelper root = CreditCourseProposalHelper.wrap(data);
            if (root.getCourse() == null) {
            	throw new AssemblyException("Cannot save proposal without course");
            }
            
	        // first save all of the clus and relations
            SaveResult<Data> courseResult = saveCourse(data);
            result.addValidationResults(courseResult.getValidationResults());

            String courseId = null;
            if (courseResult.getValue() != null) {
                CreditCourseHelper helper = CreditCourseHelper.wrap(courseResult.getValue());
                courseId = helper.getId();
            }
            if (courseId == null) {
                throw new AssemblyException("Course ID was null after save");
            }
            
            // make sure that the proposal's reference info is properly set
            CreditCourseProposalInfoHelper inputProposal = root.getProposal();
            inputProposal.setReferenceType(PROPOSAL_REFERENCE_TYPE);
            Data references = inputProposal.getReferences();
            if (references == null) {
                references = new Data();
                references.add(courseId);
                inputProposal.setReferences(references);
            } else if (!references.containsValue(new Data.StringValue(courseId))) {
                references.add(courseId);
            }

            String proposalId = saveProposal(root);
                                
            result.setValue((proposalId == null) ? null : get(proposalId));
            
            return result;
        } catch (Exception e) {
            throw new AssemblyException("Unable to save proposal", e);
        }
    }
    
    private String saveProposal(CreditCourseProposalHelper inputProposal) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, DependentObjectsExistException, PermissionDeniedException, AlreadyExistsException, DataValidationErrorException, VersionMismatchException {
        String result = null;
        //FIXME Forcing update here no matter what
        setUpdated(inputProposal.getProposal().getData(), true);
        if (isDeleted(inputProposal.getProposal().getData())) {
            proposalService.deleteProposal(inputProposal.getProposal().getId());
        } else if (isModified(inputProposal.getProposal().getData()) || inputProposal.getProposal().getId() == null) { // FIXME wilj: use modification flags once the client enforces them
            ProposalInfo prop = null;
            // FIXME wilj: use modification flags once the client enforces them
            if (inputProposal.getProposal().getId() == null) {
//          if (inputProposal.getModifications().isCreated()) {
                prop = new ProposalInfo();
                prop.setType(PROPOSAL_TYPE_CREATE_COURSE);
                prop.setProposalReferenceType("kuali.proposal.referenceType.clu");
            } else {
                prop = proposalService.getProposal(inputProposal.getProposal().getId());
            }

            prop.setRationale(inputProposal.getProposal().getRationale());
            prop.setState(inputProposal.getState());
            prop.setName(inputProposal.getProposal().getTitle());
            for (Property p : inputProposal.getProposal().getReferences()) {
                String ref = p.getValue();
                if (!prop.getProposalReference().contains(ref)) {
                    prop.getProposalReference().add(ref);
                }
            }
            if (prop.getMetaInfo() != null) {
                prop.getMetaInfo().setVersionInd(getVersionIndicator(inputProposal.getProposal().getData()));
            }
            
            ProposalInfo saved = null;
            // FIXME wilj: use modification flags once the client enforces them
            if (inputProposal.getProposal().getId() == null) {
//          if (inputProposal.getModifications().isCreated()) {
                saved = proposalService.createProposal(prop.getType(), prop);
            } else {
                saved = proposalService.updateProposal(prop.getId(), prop);
            }
            if (saved != null) {
                result = saved.getId();
            }
        } else {
            result = inputProposal.getProposal().getId();
        }
        return result;
    }

    private SaveResult<Data> saveCourse(Data data) throws AssemblyException {        
        SaveResult<Data> result = getCourseAssembler().save(data);
                    
        return result;
    }

    private boolean isAuthorized(String string, String string2, String string3) {
        // TODO Auto-generated method stub
        return false;
    }

    private RichTextInfo getRichText(RichTextInfoHelper hlp) throws AssemblyException {
        if (hlp == null || hlp.getData() == null) {
            return null;
        }
        return richtextAssembler.disassemble(hlp.getData());
    }


    @Override
    public List<ValidationResultInfo> validate(Data data)
            throws AssemblyException {
        // TODO validate CreditCourseProposal
        return null;
    }

    @Override
    public Data assemble(Void input) throws AssemblyException {
        throw new UnsupportedOperationException("CreditCourseProposalAssember does not support assembly from source type");
    }

    @Override
    public Void disassemble(Data input)
            throws AssemblyException {
        throw new UnsupportedOperationException("CreditCourseProposalAssember does not support disassembly to source type");
    }

    @Override
    public SearchResult search(SearchRequest searchRequest) {
        //TODO Might want to be synchronized, or services should be dependency injected...
        if(null == searchDispatcher){
            searchDispatcher = new SearchDispatcherImpl(luService, loService, proposalService);
        }
        return searchDispatcher.dispatchSearch(searchRequest);
    }   
    
    public LuService getLuService() {
        return luService;
    }

    public void setLuService(LuService luService) {
        this.luService = luService;
    }
    
    public StatementService getStatementService() {
        return statementService;
    }

    public void setStatementService(StatementService statementService) {
        this.statementService = statementService;
    }

    public void setProposalService(ProposalService proposalService) {
        this.proposalService = proposalService;
    }       
    
    
    public void setLearningObjectiveService(LearningObjectiveService loService) {
        this.loService = loService;
    }   
    
    public void setSearchDispatcher(SearchDispatcherImpl searchDispatcher) {
        this.searchDispatcher = searchDispatcher;
    }


    public OrganizationService getOrgService() {
        return orgService;
    }

    public void setOrgService(OrganizationService orgService) {
        this.orgService = orgService;
    }

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }
    
    @Override
    protected String getDataType() {
        return CREDIT_COURSE_PROPOSAL_DATA_TYPE;
    }

    @Override
    protected String getDocumentPropertyName() {
        return "course";
    }

    @Override
    protected String getDtoName() {
        return "kuali.lu.type.CreditCourse";
    }

    @Override
    public boolean checkDocumentLevelPermissions() {
    	return true;
    }

    @Override
    protected AttributeSet getQualification(String idType, String id) {
        AttributeSet qualification = new AttributeSet();
    	// FIXME: change this value to use constants from rice
        String DOCUMENT_TYPE_NAME = "documentTypeName";
        qualification.put(DOCUMENT_TYPE_NAME, "CluCreditCourseProposal");
        qualification.put(idType, id);
        return qualification;
    }

    public void setCourseAssembler(Assembler<Data, CluInfoHierarchy> courseAssembler) {
        this.courseAssembler = courseAssembler;
    }
}
