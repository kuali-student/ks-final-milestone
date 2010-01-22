package org.kuali.student.lum.lu.assembly;

import static org.kuali.student.lum.lu.assembly.AssemblerUtils.addVersionIndicator;
import static org.kuali.student.lum.lu.assembly.AssemblerUtils.getVersionIndicator;
import static org.kuali.student.lum.lu.assembly.AssemblerUtils.isCreated;
import static org.kuali.student.lum.lu.assembly.AssemblerUtils.isDeleted;
import static org.kuali.student.lum.lu.assembly.AssemblerUtils.isModified;
import static org.kuali.student.lum.lu.assembly.AssemblerUtils.isUpdated;
import static org.kuali.student.lum.lu.assembly.AssemblerUtils.setCreated;
import static org.kuali.student.lum.lu.assembly.AssemblerUtils.setUpdated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.rice.kim.bo.role.dto.KimPermissionInfo;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.PermissionService;
import org.kuali.student.common.assembly.Assembler;
import org.kuali.student.common.assembly.client.AssemblyException;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.SaveResult;
import org.kuali.student.common.assembly.client.Data.Property;
import org.kuali.student.common.assembly.client.Metadata.WriteAccess;
import org.kuali.student.common.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.proposal.service.ProposalService;
import org.kuali.student.core.search.newdto.SearchRequest;
import org.kuali.student.core.search.newdto.SearchResult;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;
import org.kuali.student.lum.lo.service.LearningObjectiveService;
import org.kuali.student.lum.lu.assembly.CluInfoHierarchyAssembler.RelationshipHierarchy;
import org.kuali.student.lum.lu.assembly.data.client.LoModelDTO;
import org.kuali.student.lum.lu.assembly.data.client.LuData;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.CluInstructorInfoHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.RichTextInfoHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.TimeAmountInfoHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityContactHoursHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityDurationHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseCrossListingsHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseDurationHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseFormatHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseJointsHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalInfoHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalMetadata;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseVersionsHelper;
import org.kuali.student.lum.lu.assembly.data.server.CluInfoHierarchy;
import org.kuali.student.lum.lu.assembly.data.server.CluInfoHierarchy.ModificationState;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluInstructorInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.lu.ui.course.server.gwt.LoInfoPersistenceBean;
import org.kuali.student.lum.lu.ui.course.server.gwt.LuRuleInfoPersistanceBean;
import org.kuali.student.lum.nlt.service.TranslationService;
import org.kuali.student.lum.ui.requirements.client.model.RuleInfo;

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
public class CreditCourseProposalAssembler implements Assembler<Data, Void> {
    // TODO make sure that cluclurelation version indicators are carried over on retrieval
    // TODO verify that the right relation types have been used
    // public static final String FORMAT_RELATION_TYPE =
    // LUConstants.LU_LU_RELATION_TYPE_HAS_COURSE_FORMAT;
    // public static final String ACTIVITY_RELATION_TYPE =
    // LUConstants.LU_LU_RELATION_TYPE_CONTAINS;
    public static final String PROPOSAL_REFERENCE_TYPE = "kuali.proposal.referenceType.clu"; // <- what the service says, but the dictionary says: "kuali.referenceType.CLU";
    public static final String PROPOSAL_TYPE_CREATE_COURSE = "kuali.proposal.type.course.create";
    public static final String CREDIT_COURSE_PROPOSAL_DATA_TYPE = "CreditCourseProposal";
    
    final Logger LOG = Logger.getLogger(CreditCourseProposalAssembler.class);
    
    private final String proposalState;
    private CourseAssembler courseAssembler ;
    private CluInfoHierarchyAssembler cluHierarchyAssembler;
    private final RichTextInfoAssembler richtextAssembler = new RichTextInfoAssembler();
    private final TimeAmountInfoAssembler timeamountAssembler = new TimeAmountInfoAssembler();
    private final CluIdentifierInfoAssembler cluIdentifierAssembler = new CluIdentifierInfoAssembler();
    private final CluInstructorInfoDataAssembler instructorAssembler = new CluInstructorInfoDataAssembler();
    private ProposalService proposalService;
    private LuService luService;
    private PermissionService permissionService;
    private LearningObjectiveService loService;
    private TranslationService translationService;
    
    private SearchDispatcher searchDispatcher;
    
    private MetadataServiceImpl metadataService;
    
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
            luData.setLoModelDTO(current.getLoModelDTO());
            luData.setRuleInfos(current.getRuleInfos());
            
        } catch (Exception e) {
            throw new AssemblyException(
                    "Could not assemble credit course proposal", e);
        }

        return result.getData();
    }

    private CourseAssembler getCourseAssembler() {
        if (courseAssembler == null) {
            courseAssembler = new CourseAssembler();
            courseAssembler.setLuService(luService);
            courseAssembler.setLoService(loService);
            courseAssembler.setTranslationService(translationService);
            courseAssembler.setPermissionService(permissionService);
        }
        return courseAssembler;
    }

    private CreditCourseHelper getCourse(String cluId)
            throws AssemblyException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {


        //Get permissions for course
        Map<String, String> permissions = getPermissions("kuali.lu.type.CreditCourse");
        
        CreditCourseHelper course = CreditCourseHelper.wrap(getCourseAssembler().get(cluId));

        //TODO what to do about auth checks
//      //Authz Check
//      if(permissions==null||permissions.get("department")==null||"edit".equals(permissions.get("department"))||"readOnly".equals(permissions.get("department"))){
//          AdminOrgInfo admin = result.getPrimaryAdminOrg();
//          if (admin != null) {
//              result.setDepartment(admin.getOrgId());
//          }
//      }
//
//      if(permissions==null||permissions.get("description")==null||"edit".equals(permissions.get("description"))||"readOnly".equals(permissions.get("description"))){
//          result.setDescription(RichTextInfoHelper.wrap(richtextAssembler.assemble(course.getDesc())));
//      }
//
//      if(permissions==null||permissions.get("duration")==null||"edit".equals(permissions.get("duration"))||"readOnly".equals(permissions.get("duration"))){
//          TimeAmountInfoHelper time = TimeAmountInfoHelper.wrap(timeamountAssembler.assemble(course.getIntensity()));
//          if (time != null) {
//              CreditCourseDurationHelper duration = CreditCourseDurationHelper.wrap(new Data());
//              duration.setQuantity(time.getTimeQuantity());
//              duration.setTermType(time.getAtpDurationTypeKey());
//              result.setDuration(duration);
//          }
//      }


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

    private Map<String,String> getPermissions(String dtoName){
        try{    
            //get permissions and turn into a map of fieldName=>access
            String principalId = SecurityUtils.getCurrentUserId();
            String namespaceCode = "KS-SYS";
            String permissionTemplateName = "Field Access";
            AttributeSet qualification = null;
            AttributeSet permissionDetails = new AttributeSet("dtoName",dtoName);
            List<KimPermissionInfo> permissions = permissionService.getAuthorizedPermissionsByTemplateName(principalId, namespaceCode, permissionTemplateName, permissionDetails, qualification);
            Map<String, String> permMap = new HashMap<String,String>();
            for(KimPermissionInfo permission:permissions){
                String dtoFieldKey = permission.getDetails().get("dtoFieldKey");
                String fieldAccessLevel = permission.getDetails().get("fieldAccessLevel");
                permMap.put(dtoFieldKey, fieldAccessLevel);
            }
            return permMap;
        }catch(Exception e){
            LOG.warn("Error calling permission service.",e);
        }
        return null;
    }
    
    @Override
    public Metadata getMetadata(String type, String state) throws AssemblyException {
        // TODO overriding the specified type isn't a good thing, but in other cases the type needs to be specified by the caller
        //this needs to be filtered, but not sure how to do that if an assembler needs metadata itself
        Metadata metadata = metadataService.getMetadata(CREDIT_COURSE_PROPOSAL_DATA_TYPE, PROPOSAL_TYPE_CREATE_COURSE, state);
        
        //Metadata metadata = new CreditCourseProposalMetadata().getMetadata(PROPOSAL_TYPE_CREATE_COURSE, state);
        
        //Get permissions for course
        Map<String, String> permissions = getPermissions("kuali.lu.type.CreditCourse");
        
        
        if(permissions!=null){
            //Get course metadata
            Metadata courseMetadata = metadata.getProperties().get("course");
            
            //Apply permissions to course metadata access...
            for(Map.Entry<String, String> permission:permissions.entrySet()){
                String dtoFieldKey = permission.getKey();
                String fieldAccessLevel = permission.getValue();
                Metadata fieldMetadata = courseMetadata.getProperties().get(dtoFieldKey);
                if(fieldMetadata!=null){
                    if("edit".equals(fieldAccessLevel)){//TODO better translation of access
                        //Don't do anything, yield to default behavior
                    }else{
                        fieldMetadata.setWriteAccess(WriteAccess.NEVER);
                    }
                }
            }   
        }
        
        return metadata;
    }

    @Override
    public SaveResult<Data> save(Data data)
            throws AssemblyException {
        try {
            SaveResult<Data> result = new SaveResult<Data>();
            List<ValidationResultInfo> validationResults = validate(data);
            result.setValidationResults(validationResults);
            if (validationFailed(validationResults)) {
                result.setValue(data);
                return result;
            }

            CreditCourseProposalHelper root = CreditCourseProposalHelper.wrap(data);
             if (root.getCourse() == null) {
                    throw new AssemblyException("Cannot save proposal without course");
                }
            // first save all of the clus and relations
            SaveResult<Data> courseResult = saveCourse(data);
            if (result.getValidationResults() == null) {
                result.setValidationResults(courseResult.getValidationResults());
            } else if (courseResult.getValidationResults() != null){
                result.getValidationResults().addAll(courseResult.getValidationResults());
            }
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
                    
            result.setValidationResults(validationResults);
            
            result.setValue((proposalId == null) ? null : get(proposalId));
            
            return result;
        } catch (Exception e) {
            throw new AssemblyException("Unable to save proposal", e);
        }
    }

    
    private boolean validationFailed(
            List<ValidationResultInfo> validationResults) {
        boolean result = false;
        if (validationResults != null) {
            for (ValidationResultInfo info : validationResults) {
                if (info.getLevel() == ErrorLevel.ERROR) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    private String saveProposal(CreditCourseProposalHelper inputProposal) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, DependentObjectsExistException, PermissionDeniedException, AlreadyExistsException, DataValidationErrorException, VersionMismatchException {
        String result = null;
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
            searchDispatcher = new SearchDispatcher(luService, loService, proposalService);
        }
        return searchDispatcher.dispatchSearch(searchRequest);
    }   
    
    public LuService getLuService() {
        return luService;
    }

    public void setLuService(LuService luService) {
        this.luService = luService;
    }

    public void setProposalService(ProposalService proposalService) {
        this.proposalService = proposalService;
    }       
    
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
    
    public void setLearningObjectiveService(LearningObjectiveService loService) {
        this.loService = loService;
    }   
    
    public void setTranslationService(TranslationService translationService) {
        this.translationService = translationService;
    }

    public void setSearchDispatcher(SearchDispatcher searchDispatcher) {
        this.searchDispatcher = searchDispatcher;
    }

    public void setMetadataService(MetadataServiceImpl metadataService) {
        this.metadataService = metadataService;
    }   
}
