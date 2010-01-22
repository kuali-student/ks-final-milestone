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
import org.kuali.student.common.ui.server.mvc.dto.BeanMappingException;
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
import org.kuali.student.lum.ui.requirements.client.model.RuleInfo;

/*
 *	ASSEMBLERREVIEW
 *  1) The type/state config in here is a mess.
 *  	- some of the type/state info was just copied from other places, 
 *  		just a hackjob to get it working
 *  	- bigger issue is how to handle state transitions, etc.  not handling 
 *  		it right now, but it would be nice to come up with a clean/standard
 *  		way of doing it
 *  
 *  2) Need to come up with a clean way of copying properties that are passthrough without a lot of boilerplate code, but...
 *  	- many "passthrough" properties will still have a rename along the way, and possibly a transformation of their position within the graph, e.g.
 *  		cluInfo/officialIdentifier/shortName -> proposal/transcriptTitle  
 *  
 *  3) Disconnect on assembler type/state config
 *  	- some assemblers will be specific to a type
 *  	- getMetadata takes in type as a parameter
 * 
 * 	4) Propagation of version indicator information is messy
 * 		- services require the version indicator for locking purposes
 * 		- assemblers need to be stateless, so it needs to be passed down as part of the returned orchestration
 * 		- resulting orchestrations will have complex types assembled from multiple objects each having their own version indicator
 */
public class CreditCourseProposalAssembler implements Assembler<Data, Void> {
	// TODO make sure that cluclurelation version indicators are carried over on retrieval
	// TODO verify that the right relation types have been used
	// public static final String FORMAT_RELATION_TYPE =
	// LUConstants.LU_LU_RELATION_TYPE_HAS_COURSE_FORMAT;
	// public static final String ACTIVITY_RELATION_TYPE =
	// LUConstants.LU_LU_RELATION_TYPE_CONTAINS;
	public static final String FORMAT_RELATION_TYPE = "luLuRelationType.hasCourseFormat";
	public static final String FORMAT_LU_TYPE = "kuali.lu.type.CreditCourseFormatShell";
	public static final String ACTIVITY_RELATION_TYPE = "luLuRelationType.contains";
	public static final String PROPOSAL_REFERENCE_TYPE = "kuali.proposal.referenceType.clu"; // <- what the service says, but the dictionary says: "kuali.referenceType.CLU";
	public static final String PROPOSAL_TYPE_CREATE_COURSE = "kuali.proposal.type.course.create";
	public static final String JOINT_RELATION_TYPE = "kuali.lu.relation.type.co-located";
	
	final Logger LOG = Logger.getLogger(CreditCourseProposalAssembler.class);
	
	private final String proposalState;
	private CluInfoHierarchyAssembler cluHierarchyAssembler;
	private final RichTextInfoAssembler richtextAssembler = new RichTextInfoAssembler();
	private final TimeAmountInfoAssembler timeamountAssembler = new TimeAmountInfoAssembler();
	private final CluIdentifierInfoAssembler cluIdentifierAssembler = new CluIdentifierInfoAssembler();
	private final CluInstructorInfoDataAssembler instructorAssembler = new CluInstructorInfoDataAssembler();
	private ProposalService proposalService;
	private LuService luService;
	private PermissionService permissionService;
	private LearningObjectiveService loService;
	private LoInfoPersistenceBean loInfoBean;
	
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
			
			CreditCourseHelper course = getCourse(proposal);
			
			luData.setRuleInfos(getRules(course.getId()));
			
			luData.setLoModelDTO(new LoModelDTO(getLoInfoPersistenceBean().getLos(course.getId())));
			
			result.setProposal(proposal);
			result.setCourse(getCourse(proposal));
		} catch (Exception e) {
			throw new AssemblyException(
					"Could not assemble credit course proposal", e);
		}

		return result.getData();
	}

	private CluInfoHierarchyAssembler getCluHierarchyAssembler() {
		if (cluHierarchyAssembler == null) {
			RelationshipHierarchy course = new RelationshipHierarchy();
			RelationshipHierarchy formats = new RelationshipHierarchy(FORMAT_RELATION_TYPE, "Active");
			RelationshipHierarchy activities = new RelationshipHierarchy(ACTIVITY_RELATION_TYPE, "Active");
			
			course.addChild(formats);
			formats.addChild(activities);
			
			cluHierarchyAssembler = new CluInfoHierarchyAssembler();
			cluHierarchyAssembler.setHierarchy(course);
			cluHierarchyAssembler.setLuService(luService);
		}
		return cluHierarchyAssembler;
	}

	private CreditCourseHelper getCourse(CreditCourseProposalInfoHelper proposal)
			throws AssemblyException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		CreditCourseHelper result = CreditCourseHelper.wrap(new Data());

		//Get permissions for course
		Map<String, String> permissions = getPermissions("kuali.lu.type.CreditCourse");
		
		Data references = proposal.getReferences();
		if (references.size() != 1) {
			throw new AssemblyException(
					"Invalid number of referenced courses for proposal: "
							+ references.size());
		}
		String cluId = references.get(0);
		CluInfoHierarchy cluHierarchy = getCluHierarchyAssembler().get(cluId);

		if (cluHierarchy == null || cluHierarchy.getCluInfo() == null) {
			throw new AssemblyException("Unable to retrieve course for id: "
					+ cluId);
		}
		CluInfo course = cluHierarchy.getCluInfo();
		result.setId(cluId);
		result.setCourseNumberSuffix(course.getOfficialIdentifier()
				.getSuffixCode());
		//Authz Check
		if(permissions==null||permissions.get("courseTitle")==null||"edit".equals(permissions.get("courseTitle"))||"readOnly".equals(permissions.get("courseTitle"))){
			result.setCourseTitle(course.getOfficialIdentifier().getLongName());
		}
		result.setEffectiveDate(course.getEffectiveDate());
		result.setExpirationDate(course.getExpirationDate());
		
		//Authz Check
		if(permissions==null||permissions.get("department")==null||"edit".equals(permissions.get("department"))||"readOnly".equals(permissions.get("department"))){
			AdminOrgInfo admin = course.getPrimaryAdminOrg();
			if (admin != null) {
				result.setDepartment(admin.getOrgId());
			}
		}
		
		// FIXME wilj: figure out how to retrieve the version codes and cross listings 
//		for (CluIdentifierInfo alt : course.getAlternateIdentifiers()) {
//			result.getAlternateIdentifiers().add(cluIdentifierAssembler.assemble(alt));
//		}
		//Authz Check
		if(permissions==null||permissions.get("description")==null||"edit".equals(permissions.get("description"))||"readOnly".equals(permissions.get("description"))){
			result.setDescription(RichTextInfoHelper.wrap(richtextAssembler.assemble(course.getDesc())));
		}
//		result.setRationale(RichTextInfoHelper.wrap(richtextAssembler.assemble(course.getMarketingDesc())));
		//Authz Check
		if(permissions==null||permissions.get("duration")==null||"edit".equals(permissions.get("duration"))||"readOnly".equals(permissions.get("duration"))){
			TimeAmountInfoHelper time = TimeAmountInfoHelper.wrap(timeamountAssembler.assemble(course.getIntensity()));
			if (time != null) {
				CreditCourseDurationHelper duration = CreditCourseDurationHelper.wrap(new Data());
				duration.setQuantity(time.getTimeQuantity());
				duration.setTermType(time.getAtpDurationTypeKey());
				result.setDuration(duration);
			}
		}
		CluInstructorInfoHelper instr = CluInstructorInfoHelper.wrap(instructorAssembler.assemble(course.getPrimaryInstructor()));
		if (instr != null) {
			result.setPrimaryInstructor(instr.getPersonId());
		}
		result.setState(course.getState());
		result.setSubjectArea(course.getOfficialIdentifier().getDivision());
		result.setTranscriptTitle(course.getOfficialIdentifier().getShortName());
		result.setType(course.getType());
		
		result.setAcademicSubjectOrgs(new Data());
		for (String org : course.getAcademicSubjectOrgs()) {
			result.getAcademicSubjectOrgs().add(org);
		}
		
		result.setCampusLocations(new Data());
		for (String campus : course.getCampusLocationList()) {
			result.getCampusLocations().add(campus);
		}
		addVersionIndicator(result.getData(), CluInfo.class.getName(), course.getId(), course.getMetaInfo().getVersionInd());

		for (CluInfoHierarchy format : cluHierarchy.getChildren()) {
			addFormats(result, format);	
			
		}
		addCrossListings(result, cluHierarchy);
        addVersions(result, cluHierarchy);
        
		//Retrieve related clus of type kuali.lu.relation.type.co-located and add the list to the map.
		List<CluInfo> clus = luService.getClusByRelation(cluId, JOINT_RELATION_TYPE);
		List<CluCluRelationInfo> cluClus = luService.getCluCluRelationsByClu(cluId);
		for(CluCluRelationInfo cluRel:cluClus){
		    if(cluRel.getType().equals(JOINT_RELATION_TYPE)){
		        CluInfo cluInfo = luService.getClu(cluRel.getRelatedCluId());
		        CreditCourseJointsHelper joint = CreditCourseJointsHelper
                .wrap(new Data());
		        buildJoints(cluInfo,joint);
		        joint.setRelationId(cluRel.getId());
		        if(result.getJoints()==null){
                    result.setJoints(new Data());
                }
                result.getJoints().add(joint.getData());
                
		    }
		}
//		if (clus != null) {
//			
//			for (CluInfo clu : clus) {
//				CreditCourseJointsHelper joint = CreditCourseJointsHelper
//						.wrap(new Data());
//				buildJoints(clu,joint);
//				if(result.getJoints()==null){
//					result.setJoints(new Data());
//				}
//				result.getJoints().add(joint.getData());
//			}
//		}
		
		return result;
	}

	private void addFormats(CreditCourseHelper course, CluInfoHierarchy cluHierarchy) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, AssemblyException {

		CreditCourseFormatHelper format = CreditCourseFormatHelper.wrap(new Data());
		CluInfo clu = cluHierarchy.getCluInfo();
		format.setId(clu.getId());
		format.setState(clu.getState());
		addVersionIndicator(format.getData(), CluInfo.class.getName(), clu.getId(), clu.getMetaInfo().getVersionInd());
		for (CluInfoHierarchy activity : cluHierarchy.getChildren()) {
			addActivities(format, activity);
		}
		if (course.getFormats() == null) {
			course.setFormats(new Data());
		}
		course.getFormats().add(format.getData());
	}

	private void addActivities(CreditCourseFormatHelper format, CluInfoHierarchy cluHierarchy)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException, AssemblyException {

		CreditCourseActivityHelper activity = CreditCourseActivityHelper.wrap(new Data());
		CluInfo clu = cluHierarchy.getCluInfo();
		
		activity.setId(clu.getId());
		activity.setActivityType(clu.getType());
		
		TimeAmountInfoHelper time = TimeAmountInfoHelper.wrap(timeamountAssembler.assemble(clu.getIntensity()));
		if (time != null) {
			CreditCourseActivityContactHoursHelper hours = CreditCourseActivityContactHoursHelper.wrap(new Data());
			hours.setHrs(time.getTimeQuantity());
			hours.setPer(time.getAtpDurationTypeKey());
			activity.setContactHours(hours);
		}

		TimeAmountInfoHelper stdDuration = TimeAmountInfoHelper.wrap(timeamountAssembler.assemble(clu.getStdDuration()));
		if (stdDuration != null) {
			CreditCourseActivityDurationHelper duration = CreditCourseActivityDurationHelper.wrap(new Data());
			duration.setQuantity(stdDuration.getTimeQuantity());
			duration.setTimeUnit(stdDuration.getAtpDurationTypeKey());
			activity.setDuration(duration);
		}
		
		activity.setDefaultEnrollmentEstimate(clu.getDefaultEnrollmentEstimate());
		activity.setState(clu.getState());
		addVersionIndicator(activity.getData(), CluInfo.class.getName(), clu.getId(), clu.getMetaInfo().getVersionInd());

		if (format.getActivities() == null) {
			format.setActivities(new Data());
		}
		format.getActivities().add(activity.getData());
	}

    private void addCrossListings(CreditCourseHelper course, CluInfoHierarchy cluHierarchy) 
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, 
            OperationFailedException, AssemblyException {
        
        CreditCourseCrossListingsHelper xListings = null;
        CluInfo clu = cluHierarchy.getCluInfo();
        List<CluIdentifierInfo> cluIdentifiers = clu.getAlternateIdentifiers();
        
        for(CluIdentifierInfo cluIdentifier : cluIdentifiers){
            xListings = CreditCourseCrossListingsHelper.wrap(new Data());
            xListings.setId(cluIdentifier.getId());
            xListings.setType(cluIdentifier.getType());
            xListings.setDepartment(cluIdentifier.getOrgId());
            xListings.setSubjectArea(cluIdentifier.getDivision());
            xListings.setCourseNumberSuffix(cluIdentifier.getSuffixCode());
            
            Data data = course.getCrossListings();
            if(data == null){
                data = new Data();
                course.setCrossListings(data);
            }
            data.add(xListings.getData());
        }   
    }
    
    private void addVersions(CreditCourseHelper course, CluInfoHierarchy cluHierarchy) 
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, 
            OperationFailedException, AssemblyException {

        CreditCourseVersionsHelper versions = null;
        CluInfo clu = cluHierarchy.getCluInfo();
        List<CluIdentifierInfo> cluIdentifiers = clu.getAlternateIdentifiers();

        for(CluIdentifierInfo cluIdentifier : cluIdentifiers){
            versions = CreditCourseVersionsHelper.wrap(new Data());
            versions.setId(cluIdentifier.getId());
            versions.setType(cluIdentifier.getType());
            versions.setVersionTitle(cluIdentifier.getLongName());
            versions.setSubjectArea(cluIdentifier.getDivision());
            versions.setCourseNumberSuffix(cluIdentifier.getSuffixCode());
            versions.setVersionCode(cluIdentifier.getVariation());
            
            Data data = course.getVersions();
            if(data == null){
                data = new Data();
                course.setVersions(data);
            }
            data.add(versions.getData());
        }   
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
		Metadata metadata = new CreditCourseProposalMetadata().getMetadata(PROPOSAL_TYPE_CREATE_COURSE, state);
		
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
			// first save all of the clus and relations
			SaveResult<CluInfoHierarchy> courseResult = saveCourse(root.getCourse());
			if (result.getValidationResults() == null) {
				result.setValidationResults(courseResult.getValidationResults());
			} else if (courseResult.getValidationResults() != null){
				result.getValidationResults().addAll(courseResult.getValidationResults());
			}
			String courseId = null;
			if (courseResult.getValue() != null && courseResult.getValue().getCluInfo() != null) {
				courseId = courseResult.getValue().getCluInfo().getId();
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
			
			saveRules(courseId, (LuData)data);
			
			saveLearningObjectives(courseId, (LuData) data);
			
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

	private void saveRules(String courseId, LuData luData) throws Exception{
		LuRuleInfoPersistanceBean ruleInfoBean = new LuRuleInfoPersistanceBean();
		ruleInfoBean.setLuService(luService);
		ruleInfoBean.updateRules(courseId, luData.getRuleInfos());			
	}
	
	private List<RuleInfo> getRules(String courseId) throws Exception{
		LuRuleInfoPersistanceBean ruleInfoBean = new LuRuleInfoPersistanceBean();
		return ruleInfoBean.fetchRules(courseId);
	}

	private void saveLearningObjectives(String courseId, LuData data) throws Exception {
		getLoInfoPersistenceBean().updateLos(courseId, data.getLoModelDTO());			
	}

	private LoInfoPersistenceBean getLoInfoPersistenceBean() {
		if (null == loInfoBean) {
			loInfoBean = new LoInfoPersistenceBean();
			loInfoBean.setLoService(loService);
			loInfoBean.setLuService(luService);
		}
		return loInfoBean;
	}

	private String saveProposal(CreditCourseProposalHelper inputProposal) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, DependentObjectsExistException, PermissionDeniedException, AlreadyExistsException, DataValidationErrorException, VersionMismatchException {
		String result = null;
		if (isDeleted(inputProposal.getProposal().getData())) {
			proposalService.deleteProposal(inputProposal.getProposal().getId());
		} else if (isModified(inputProposal.getProposal().getData()) || inputProposal.getProposal().getId() == null) { // FIXME wilj: use modification flags once the client enforces them
			ProposalInfo prop = null;
			// FIXME wilj: use modification flags once the client enforces them
			if (inputProposal.getProposal().getId() == null) {
//			if (inputProposal.getModifications().isCreated()) {
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
//			if (inputProposal.getModifications().isCreated()) {
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

	private SaveResult<CluInfoHierarchy> saveCourse(CreditCourseHelper course) throws AssemblyException {
		if (course == null) {
			throw new AssemblyException("Cannot save proposal without course");
		}
		CluInfoHierarchy clus = buildCluInfoHiearchy(course);
		
		SaveResult<CluInfoHierarchy> result = getCluHierarchyAssembler().save(clus);
		
//		Uncomment this line when the screen elements for adding join courses is added.
		addJoints(result.getValue().getCluInfo().getId(),course);
		
		return result;
	}

	private void addJoints(String parentCourseId,CreditCourseHelper course ) throws AssemblyException{
		try {
			if (course.getJoints() == null) {
				return;
			}
			// CreditCourseJointsHelper joints =
			// CreditCourseJointsHelper.wrap(course.getJoints());
			for (Property p : course.getJoints()) {
				CreditCourseJointsHelper joint = CreditCourseJointsHelper
						.wrap((Data) p.getValue());
				//If user has not entered a joint course on the screen then return back
				if(joint.getCourseId()==null){
					return;
				}
				if (isCreated(joint.getData())) {
					CluCluRelationInfo rel = new CluCluRelationInfo();
					rel.setCluId(parentCourseId);
					rel.setRelatedCluId(joint.getCourseId());
//					rel.setType(joint.getType());
//					Remove hardcoded Type
					rel.setType(JOINT_RELATION_TYPE);
					
					CluCluRelationInfo result= luService.createCluCluRelation(parentCourseId, joint
							.getCourseId(), JOINT_RELATION_TYPE, rel);
					joint.setRelationId(result.getId());
					
				}
				else if(isUpdated(joint.getData())){
				    String relationId = joint.getRelationId();
				    CluInfo clu = luService.getClu(joint.getCourseId());
				    if(!(clu.getOfficialIdentifier().getLongName().equals(joint.getCourseTitle()))){
				        CluCluRelationInfo cluCluRelation = new CluCluRelationInfo();
				        cluCluRelation.setId(relationId);
				        cluCluRelation.setCluId(parentCourseId);
				        cluCluRelation.setRelatedCluId(joint.getCourseId());
				        cluCluRelation.setType(JOINT_RELATION_TYPE);
				        
				        luService.updateCluCluRelation(relationId, cluCluRelation);
				    }
					
				}
			}
		} catch (Exception e) {
			throw new AssemblyException("Unable to save cluClu Joint Relationship", e);
		}

	}
	
	private void buildJoints(CluInfo clu,CreditCourseJointsHelper joint) throws AssemblyException{
		try{
//		List<CluInfo> clus = luService.getClusByRelation(parentCourseId, JOINT_RELATION_TYPE);
		joint.setCourseId(clu.getId());
		joint.setType(JOINT_RELATION_TYPE);
		joint.setSubjectArea(clu.getStudySubjectArea());
		joint.setCourseTitle(clu.getOfficialIdentifier().getLongName());
		
		}
		catch(Exception e){
			throw new AssemblyException("Unable to get cluClu Joint Relationship");
		}
		
	}
	
	private CluInfoHierarchy buildCluInfoHiearchy(CreditCourseHelper course) throws AssemblyException {
		//metadata for authz
		Metadata courseMetadata = this.getMetadata(course.getType(), course.getState()).getProperties().get("course");//TODO cache the metadata
		
		CluInfoHierarchy result = null;
		CluInfo courseClu = null;
		// FIXME wilj: temp check for id, client needs to enforce modification flags once we get the basics working
		if (course.getId() == null) {
			setCreated(course.getData(), true);
			result = new CluInfoHierarchy();
			courseClu = new CluInfo();
			result.setCluInfo(courseClu);
		} else {
			// FIXME wilj: forcing update for now, until client enforces modification flags
			setUpdated(course.getData(), true);
			result = getCluHierarchyAssembler().get(course.getId());
			courseClu = result.getCluInfo();
		}
		
		if (isModified(course.getData())) {
			if (isCreated(course.getData())) {
				result.setModificationState(ModificationState.CREATED);
			} else if (isUpdated(course.getData())) {
				result.setModificationState(ModificationState.UPDATED);
			} else if (isDeleted(course.getData())) {
				result.setModificationState(ModificationState.DELETED);
			} 
			
			CluIdentifierInfo cluId = courseClu.getOfficialIdentifier();
			if (cluId == null) {
				cluId = new CluIdentifierInfo();
				courseClu.setOfficialIdentifier(cluId);
			}
			cluId.setSuffixCode(course.getCourseNumberSuffix());
			//AuthzCheck
			if(courseMetadata.getProperties().get("courseTitle").getWriteAccess()!=WriteAccess.NEVER){
				cluId.setLongName(course.getCourseTitle());
			}
			cluId.setDivision(course.getSubjectArea());
			cluId.setShortName(course.getTranscriptTitle());
			
			String instrId = course.getPrimaryInstructor();
			CluInstructorInfo instr = courseClu.getPrimaryInstructor();
			if (instr == null) {
				instr = new CluInstructorInfo();
				courseClu.setPrimaryInstructor(instr);
			}
			instr.setPersonId(instrId);
			
			//AuthzCheck
			if(courseMetadata.getProperties().get("department").getWriteAccess()!=WriteAccess.NEVER){
				AdminOrgInfo admin = courseClu.getPrimaryAdminOrg();
				if (admin == null) {
					admin = new AdminOrgInfo();
					courseClu.setPrimaryAdminOrg(admin);
				}
				admin.setOrgId(course.getDepartment());
			}
			
			List<String> subjectOrgs = new ArrayList<String>();
			if (course.getAcademicSubjectOrgs() != null) {
				for (Property p : course.getAcademicSubjectOrgs()) {
					String org = p.getValue();
					subjectOrgs.add(org);
				}
			}
			courseClu.setAcademicSubjectOrgs(subjectOrgs);
			
			List<String> campuses = new ArrayList<String>();
			if (course.getCampusLocations() != null) {
				for (Property p : course.getCampusLocations()) {
					String campus = p.getValue();
					campuses.add(campus);
				}
			}
			courseClu.setCampusLocationList(campuses);
			
	
//			mergeAlternateIdentifiers(course, courseClu);
			
			//AuthzCheck
			if(courseMetadata.getProperties().get("description").getWriteAccess()!=WriteAccess.NEVER){
				courseClu.setDesc(getRichText(course.getDescription()));
			}
			
			//AuthzCheck
			if(courseMetadata.getProperties().get("duration").getWriteAccess()!=WriteAccess.NEVER){
				TimeAmountInfo time = courseClu.getIntensity();
				if (time == null) {
					time = new TimeAmountInfo();
					courseClu.setIntensity(time);
				}
				if (course.getDuration() != null) {
					time.setAtpDurationTypeKey(course.getDuration().getTermType());
					time.setTimeQuantity(course.getDuration().getQuantity());
				}
			}
	
			courseClu.setEffectiveDate(course.getEffectiveDate());
			courseClu.setExpirationDate(course.getExpirationDate());
			
			// TODO unhardcode this stuff
			courseClu.setState("draft");
			courseClu.setType("kuali.lu.type.CreditCourse");
			
			if (courseClu.getMetaInfo() != null) {
				courseClu.getMetaInfo().setVersionInd(getVersionIndicator(course.getData()));
			}
		}
		
		buildFormatUpdates(result, course);
		buildCrossListingsUpdates(result, course);
		buildVersionsUpdates(result, course);
		
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
	// TODO rewrite alternate identifier code to create separate properties per alternate identifier type
//	private void mergeAlternateIdentifiers(CreditCourse course,
//			CluInfo courseClu) throws AssemblyException {
//		// create map of existing identifiers
//		Map<String, CluIdentifierInfo> ids = new HashMap<String, CluIdentifierInfo>();
//		for (CluIdentifierInfo id : courseClu.getAlternateIdentifiers()) {
//			if (id.getId() != null) {
//				ids.put(id.getId(), id);
//			}
//		}
//		for (Property p : course.getAlternateIdentifiers()) {
//			CluIdentifierInfoData data = p.getValue();
//			CluIdentifierInfo idSource = cluIdentifierAssembler.disassemble(data);
//			CluIdentifierInfo idTarget = ids.get(idSource.getId());
//			if (idTarget == null) {
//				// newly created
//				courseClu.getAlternateIdentifiers().add(idSource);
//			} else {
//				// modified
//				// skipping some fields that shouldn't be reassigned like id, cluId, etc
//				idTarget.setCode(idSource.getCode());
//				idTarget.setDivision(idSource.getDivision());
//				idTarget.setLevel(idSource.getLevel());
//				idTarget.setLongName(idSource.getLongName());
//				idTarget.setOrgId(idSource.getOrgId());
//				idTarget.setShortName(idSource.getShortName());
//				idTarget.setSuffixCode(idSource.getSuffixCode());
//				idTarget.setVariation(idSource.getVariation());
//				
//				ids.remove(idTarget.getId());
//			}
//		}
//		
//		// anything left in the ids map is something that was deleted by the client
//		for (CluIdentifierInfo c : ids.values()) {
//			courseClu.getAlternateIdentifiers().remove(c);
//		}
//	}

	private void buildFormatUpdates(CluInfoHierarchy courseHierarchy, CreditCourseHelper course) throws AssemblyException {
		if (course.getFormats() == null) {
			return;
		}
		for (Property p : course.getFormats()) {
			CreditCourseFormatHelper format = CreditCourseFormatHelper.wrap((Data)p.getValue());
			CluInfoHierarchy formatHierarchy = null;
			CluInfo formatClu = null;
			
			if (isCreated(format.getData())) {
				formatHierarchy = new CluInfoHierarchy();
				formatClu = new CluInfo();
				formatHierarchy.setCluInfo(formatClu);
				courseHierarchy.getChildren().add(formatHierarchy);
			} else {
				formatHierarchy = findChildByCluId(courseHierarchy, format.getId());
				formatClu = formatHierarchy.getCluInfo();
			}
			
			if (isModified(format.getData())) {
				if (isCreated(format.getData())) {
					formatHierarchy.setModificationState(ModificationState.CREATED);
				} else if (isUpdated(format.getData())) {
					formatHierarchy.setModificationState(ModificationState.UPDATED);
				} else if (isDeleted(format.getData())) {
					formatHierarchy.setModificationState(ModificationState.DELETED);
				} 
				
				// TODO un-hardcode
				formatClu.setState("draft");
				formatClu.setType(FORMAT_LU_TYPE);
				formatHierarchy.setParentRelationType(FORMAT_RELATION_TYPE);
				formatHierarchy.setParentRelationState("Active");
				if (formatClu.getMetaInfo() != null) {
					formatClu.getMetaInfo().setVersionInd(getVersionIndicator(format.getData()));
				}
				
			}
			buildActivityUpdates(formatHierarchy, format);
		}
	}
	
	
	
	private void buildActivityUpdates(CluInfoHierarchy formatHierarchy,
			CreditCourseFormatHelper format) throws AssemblyException {
		if (format.getActivities() == null) {
			return;
		}
		for (Property p : format.getActivities()) {
			CreditCourseActivityHelper activity = CreditCourseActivityHelper.wrap((Data)p.getValue());
			CluInfoHierarchy activityHierarchy = null;
			CluInfo activityClu = null;
			
			if (isCreated(activity.getData())) {
				activityHierarchy = new CluInfoHierarchy();
				activityClu = new CluInfo();
				activityHierarchy.setCluInfo(activityClu);
				formatHierarchy.getChildren().add(activityHierarchy);
			} else {
				activityHierarchy = findChildByCluId(formatHierarchy, activity.getId());
				activityClu = activityHierarchy.getCluInfo();
			}
			
			if (isModified(activity.getData())) {
				if (isCreated(activity.getData())) {
					activityHierarchy.setModificationState(ModificationState.CREATED);
				} else if (isUpdated(activity.getData())) {
					activityHierarchy.setModificationState(ModificationState.UPDATED);
				} else if (isDeleted(activity.getData())) {
					activityHierarchy.setModificationState(ModificationState.DELETED);
				} 

				activityClu.setType(activity.getActivityType());
				
				
				TimeAmountInfo time = activityClu.getIntensity();
				if (time == null) {
					time = new TimeAmountInfo();
					activityClu.setIntensity(time);
				}
				if (activity.getContactHours() != null) {
					time.setAtpDurationTypeKey(activity.getContactHours().getPer());
					time.setTimeQuantity(activity.getContactHours().getHrs());
				}
				
				TimeAmountInfo stdDuration = activityClu.getStdDuration();
				if (stdDuration == null){
					stdDuration = new TimeAmountInfo();
					activityClu.setStdDuration(stdDuration);
				}
				if (activity.getDuration() != null){
					stdDuration.setAtpDurationTypeKey(activity.getDuration().getTimeUnit());
					stdDuration.setTimeQuantity(activity.getDuration().getQuantity());
				}
				
				Integer enrEst = activity.getDefaultEnrollmentEstimate();
				activityClu.setDefaultEnrollmentEstimate(enrEst == null ? 0 : enrEst);
				
				// TODO un-hardcode
				activityClu.setState("draft");
				activityHierarchy.setParentRelationType(ACTIVITY_RELATION_TYPE);
				activityHierarchy.setParentRelationState("Active");

				
				if (activityClu.getMetaInfo() != null) {
					activityClu.getMetaInfo().setVersionInd(getVersionIndicator(activity.getData()));
				}
			}
		}
	}

	private void buildCrossListingsUpdates(CluInfoHierarchy courseHierarchy, CreditCourseHelper course) throws AssemblyException {
	    if (course.getCrossListings() == null) {
	        return;
	    }
	    
        CluInfo cluInfoToStore = courseHierarchy.getCluInfo();
        List<CluIdentifierInfo> alternateIdentifiers = cluInfoToStore.getAlternateIdentifiers();
        
        if (isModified(course.getData())) {
            // clear the list because the screen should have all loaded all these AltIdentifiers and
            // they may have been modified by the user and will be populated in for() below.
            Iterator<CluIdentifierInfo> iterator = alternateIdentifiers.iterator();
            while(iterator.hasNext()){
                CluIdentifierInfo cluIdentifierInfo = iterator.next();
                String identifierType = cluIdentifierInfo.getType();
                if(identifierType.equals("kuali.lu.type.CreditCourse.identifier.cross-listed")){
                    alternateIdentifiers.remove(cluIdentifierInfo);
                }
            }
        }
        
        for (Property p : course.getCrossListings()) {
            CreditCourseCrossListingsHelper xListings = CreditCourseCrossListingsHelper.wrap((Data)p.getValue());
            CluIdentifierInfo cluIdentifier = new CluIdentifierInfo();
            cluIdentifier.setCluId(xListings.getId());
            cluIdentifier.setType("kuali.lu.type.CreditCourse.identifier.cross-listed");
            cluIdentifier.setOrgId(xListings.getDepartment());
            cluIdentifier.setDivision(xListings.getSubjectArea());
            cluIdentifier.setSuffixCode(xListings.getCourseNumberSuffix());
            alternateIdentifiers.add(cluIdentifier);
        }
        cluInfoToStore.setAlternateIdentifiers(alternateIdentifiers);
	}

    private void buildVersionsUpdates(CluInfoHierarchy courseHierarchy, CreditCourseHelper course) throws AssemblyException {
        if (course.getVersions() == null) {
            return;
        }
        
        CluInfo cluInfoToStore = courseHierarchy.getCluInfo();
        List<CluIdentifierInfo> alternateIdentifiers = cluInfoToStore.getAlternateIdentifiers();
        
        if (isModified(course.getData())) {
            // clear the list because the screen should have all loaded all these AltIdentifiers and
            // they may have been modified by the user and will be populated in for() below.
            Iterator<CluIdentifierInfo> iterator = alternateIdentifiers.iterator();
            while(iterator.hasNext()){
                CluIdentifierInfo cluIdentifierInfo = iterator.next();
                String identifierType = cluIdentifierInfo.getType();
                if(identifierType.equals("kuali.lu.type.CreditCourse.identifier.version")){
                    alternateIdentifiers.remove(cluIdentifierInfo);
                }
            }
        }
        
        for (Property p : course.getVersions()) {
            CreditCourseVersionsHelper versions = CreditCourseVersionsHelper.wrap((Data)p.getValue());
            CluIdentifierInfo cluIdentifier = new CluIdentifierInfo();
            cluIdentifier.setCluId(versions.getId());
            cluIdentifier.setType("kuali.lu.type.CreditCourse.identifier.version");
            cluIdentifier.setLongName(versions.getVersionTitle());
            cluIdentifier.setVariation(versions.getVersionCode());
            alternateIdentifiers.add(cluIdentifier);
        }
        cluInfoToStore.setAlternateIdentifiers(alternateIdentifiers);
    }
    
	private CluInfoHierarchy findChildByCluId(CluInfoHierarchy parent, String cluId) {
		for (CluInfoHierarchy c : parent.getChildren()) {
			if (c.getCluInfo().getId() != null && c.getCluInfo().getId().equals(cluId)) {
				return c;
			}
		}
		return null;
	}

	@Override
	public List<ValidationResultInfo> validate(Data data)
			throws AssemblyException {
		// TODO validate CreditCourseProposal
		return null;
	}

	


	public void setProposalService(ProposalService proposalService) {
		this.proposalService = proposalService;
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

	public LuService getLuService() {
		return luService;
	}

	public void setLuService(LuService luService) {
		this.luService = luService;
	}

	
	public void setLearningObjectiveService(LearningObjectiveService loService) {
		this.loService = loService;
	}

	@Override
	public SearchResult search(SearchRequest searchRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}
	

}
