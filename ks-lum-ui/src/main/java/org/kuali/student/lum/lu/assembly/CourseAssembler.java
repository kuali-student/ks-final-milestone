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
import org.kuali.student.common.assembly.client.Metadata.WriteAccess;
import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.core.dto.AmountInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularReferenceException;
import org.kuali.student.core.exceptions.CircularRelationshipException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.search.newdto.SearchRequest;
import org.kuali.student.core.search.newdto.SearchResult;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;
import org.kuali.student.lum.lo.service.LearningObjectiveService;
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
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalMetadata;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseVersionsHelper;
import org.kuali.student.lum.lu.assembly.data.server.CluInfoHierarchy;
import org.kuali.student.lum.lu.assembly.data.server.CluInfoHierarchy.ModificationState;
import org.kuali.student.lum.lu.dto.AcademicSubjectOrgInfo;
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

public class CourseAssembler implements Assembler<Data, CluInfoHierarchy> {
//TODO Split out CluInfo assembly to its own class

    final Logger LOG = Logger.getLogger(CourseAssembler.class);

    public static final String JOINT_RELATION_TYPE = "kuali.lu.relation.type.co-located";
    public static final String PROPOSAL_TYPE_CREATE_COURSE = "kuali.proposal.type.course.create";
    public static final String FORMAT_LU_TYPE = "kuali.lu.type.CreditCourseFormatShell";

    public static final String FORMAT_RELATION_TYPE = "luLuRelationType.hasCourseFormat";
    public static final String ACTIVITY_RELATION_TYPE = "luLuRelationType.contains";

	private SingleUseLoInfoAssembler loAssembler;
    private final RichTextInfoAssembler richtextAssembler = new RichTextInfoAssembler();
    private final TimeAmountInfoAssembler timeamountAssembler = new TimeAmountInfoAssembler();
    private final CluIdentifierInfoAssembler cluIdentifierAssembler = new CluIdentifierInfoAssembler();
    private final CluInstructorInfoDataAssembler instructorAssembler = new CluInstructorInfoDataAssembler();

    private LuService luService;
    private PermissionService permissionService;
    private LearningObjectiveService loService;
    private TranslationService translationService;
    private LoInfoPersistenceBean loInfoBean;

    private SearchDispatcher searchDispatcher;

    @Override
    public Data get(String id) throws AssemblyException {

        LuData luData = new LuData();
        CreditCourseHelper result = CreditCourseHelper.wrap(luData);

        try {

            CluInfoHierarchy hierarchy = getCluInfoHierarchyFromId(id);

            CreditCourseHelper course = buildCourseFromCluInfo(hierarchy);
            
            luData.setData(course.getData());

            luData.setRuleInfos(getRules(id));

		    // TODO - need a SingleUseLoListAssembler that calls SingleUseLoAssembler once for each LO
		    // associated with the course
    		result.setCourseSpecificLOs(getSingleUseLoAssembler().get(course.getId()));

        } catch (Exception e) {
            throw new AssemblyException(
                    "Could not assemble course", e);
        }

        return result.getData();
    }

    @Override
    public Metadata getMetadata(String type, String state) throws AssemblyException {
        //TODO: Is there a more specific type we can use?
        Metadata metadata = new CreditCourseProposalMetadata().getMetadata(PROPOSAL_TYPE_CREATE_COURSE, state);

        Map<String, String> permissions = getPermissions("kuali.lu.type.CreditCourse");

        if(permissions!=null){
            Metadata courseMetadata = metadata.getProperties().get("course");

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
    public SaveResult<Data> save(Data input)     throws AssemblyException {

        try {
            SaveResult<Data> result = new SaveResult<Data>();
            List<ValidationResultInfo> validationResults = validate(input);
            result.setValidationResults(validationResults);
            if (validationFailed(validationResults)) {
                result.setValue(input);
                return result;
            }

            //FIXME: This is a bit clunky. Think of a better way to do this!  
            // CreditCourseProposalAssembler passes in LuData which contains proposal, LO and rules.
            // This class needs LO and rules but not proposal.
            CreditCourseHelper course = null;
            if (input instanceof LuData) {
                LuData luData = (LuData)input;
                CreditCourseProposalHelper helper = CreditCourseProposalHelper.wrap(luData.getData());
                course = CreditCourseHelper.wrap(helper.getCourse().getData());               
            }
            else {
              course = CreditCourseHelper.wrap(input);
            }
            // first save all of the clus and relations
            SaveResult<CluInfoHierarchy> courseResult = saveHierarchy(course);
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

            saveRules(courseId, (LuData)input);

            // save the Learning Objective(s)
            // TODO - Need a SingleUseLoListAssembler w/ standard save() method that in turn calls
            // a new single-LO save() method in SingleUseLoAssembler
            SaveResult<Data> loResult = getSingleUseLoAssembler().save(courseId, course.getCourseSpecificLOs());
            if (loResult.getValidationResults() != null){
                validationResults.addAll(loResult.getValidationResults());
            }

            result.setValidationResults(validationResults);
            
            if (courseId != null) {
               Data d = get(courseId);     
               result.setValue(d);
               
            }
            else {
                result.setValue(null);
            }
            
//            result.setValue((courseId == null) ? null : get(courseId));

            return result;
        } catch (Exception e) {
            throw new AssemblyException("Unable to save course", e);
        }
    }

    @Override
    public Data assemble(CluInfoHierarchy input) throws AssemblyException {
        throw new UnsupportedOperationException("Data assembly not supported");
    }

    @Override
    public CluInfoHierarchy disassemble(Data input) throws AssemblyException {
        throw new UnsupportedOperationException("Data disassembly not supported");
    }
    
    @Override
    public List<ValidationResultInfo> validate(Data data)  throws AssemblyException {
        // TODO validate CreditCourseProposal
        return null;
    }
    
    @Override
    public SearchResult search(SearchRequest searchRequest) {
        //TODO Might want to be synchronized, or services should be dependency injected...
        if(null == searchDispatcher){
            searchDispatcher = new SearchDispatcher(luService, loService);
        }
        return searchDispatcher.dispatchSearch(searchRequest);
    }

    private CluInfoHierarchy getCluInfoHierarchyFromId(String id) throws AssemblyException {

        RelationshipHierarchy course = new RelationshipHierarchy();
        RelationshipHierarchy formats = new RelationshipHierarchy(FORMAT_RELATION_TYPE, "Active");
        RelationshipHierarchy activities = new RelationshipHierarchy(ACTIVITY_RELATION_TYPE, "Active");

        course.addChild(formats);
        formats.addChild(activities);

        CluInfoHierarchy result = null;
        try {
            CluInfo clu = luService.getClu(id);
            if (clu != null) {
                result = new CluInfoHierarchy();
                result.setCluInfo(clu);
                for (RelationshipHierarchy h : course.getChildren()) {
                    buildRelatedCourse(result, h);
                }
            }
        } catch (Exception e) {
            throw new AssemblyException(e);
        }
        return result;


    }

    private CreditCourseHelper buildCourseFromCluInfo(CluInfoHierarchy hierarchy) throws AssemblyException {

        CreditCourseHelper result = CreditCourseHelper.wrap(new Data());

        CluInfo course = hierarchy.getCluInfo();

        try {

            //TODO move bulk of this logic to new CluInfoAssembler
            result.setId(course.getId());
            result.setCourseNumberSuffix(course.getOfficialIdentifier()
                    .getSuffixCode());
            result.setCourseTitle(course.getOfficialIdentifier().getLongName());

            result.setEffectiveDate(course.getEffectiveDate());
            result.setExpirationDate(course.getExpirationDate());

            AdminOrgInfo admin = course.getPrimaryAdminOrg();
            if (admin != null) {
                result.setDepartment(admin.getOrgId());
            }

            // FIXME wilj: figure out how to retrieve the version codes and cross listings 
//          for (CluIdentifierInfo alt : course.getAlternateIdentifiers()) {
//          result.getAlternateIdentifiers().add(cluIdentifierAssembler.assemble(alt));
//          }
            result.setDescription(RichTextInfoHelper.wrap(richtextAssembler.assemble(course.getDescr())));
//          result.setRationale(RichTextInfoHelper.wrap(richtextAssembler.assemble(course.getMarketingDesc())));
            
            AmountInfo time = course.getIntensity();
            //TimeAmountInfoHelper time = TimeAmountInfoHelper.wrap(timeamountAssembler.assemble(course.getIntensity()));
            if (time != null) {
                CreditCourseDurationHelper duration = CreditCourseDurationHelper.wrap(new Data());
                if(time.getUnitQuantity() != null) {
                    duration.setQuantity(Integer.valueOf(time.getUnitQuantity()));
                }
                duration.setTermType(time.getUnitType());
                result.setDuration(duration);
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
            
            for (AcademicSubjectOrgInfo org : course.getAcademicSubjectOrgs()) {
                result.getAcademicSubjectOrgs().add(org.getOrgId());
            }

            result.setCampusLocations(new Data());
            for (String campus : course.getCampusLocations()) {
                result.getCampusLocations().add(campus);
            }
            addVersionIndicator(result.getData(), CluInfo.class.getName(), course.getId(), course.getMetaInfo().getVersionInd());

            for (CluInfoHierarchy format : hierarchy.getChildren()) {
                buildFormatsFromCluInfo(result, format);

            }
            addCrossListings(result, hierarchy);
            addVersions(result, hierarchy);

            //Retrieve related clus of type kuali.lu.relation.type.co-located and add the list to the map.
            List<CluInfo> clus = luService.getClusByRelation(course.getId(), JOINT_RELATION_TYPE);
            List<CluCluRelationInfo> cluClus = luService.getCluCluRelationsByClu(course.getId());
            for(CluCluRelationInfo cluRel:cluClus){
                if(cluRel.getType().equals(JOINT_RELATION_TYPE)){
                    CluInfo cluInfo = luService.getClu(cluRel.getRelatedCluId());
                    CreditCourseJointsHelper joint = CreditCourseJointsHelper
                            .wrap(new Data());
                    buildJointsFromCluInfo(cluInfo,joint);
                    joint.setRelationId(cluRel.getId());
                    if(result.getJoints()==null){
                        result.setJoints(new Data());
                    }
                    result.getJoints().add(joint.getData());

                }
            }
            result.setId(course.getId());

//          if (clus != null) {

//          for (CluInfo clu : clus) {
//          CreditCourseJointsHelper joint = CreditCourseJointsHelper
//          .wrap(new Data());
//          buildJointsFromCluInfo(clu,joint);
//          if(result.getJoints()==null){
//          result.setJoints(new Data());
//          }
//          result.getJoints().add(joint.getData());
//          }
//          }

        }
        catch (Exception e) {
            throw new AssemblyException(
                    "Could not assemble course ", e);

        }
        return result;

    }
    private void buildRelatedCourse(CluInfoHierarchy currentClu, RelationshipHierarchy currentRel) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        System.out.println("Retrieving relation: " + currentClu.getCluInfo().getId() + "\t" + currentRel.getRelationshipType());
        List<CluCluRelationInfo> children = luService.getCluCluRelationsByClu(currentClu.getCluInfo().getId());
        if (children != null) {
            for (CluCluRelationInfo rel : children) {
                if(rel.getType().equals(JOINT_RELATION_TYPE)){
                    // if the cluclu realtion is of type jointCourses than dont add that as a child to the CluInfoHierarchy object
                    return;
                }
                CluInfo clu = luService.getClu(rel.getRelatedCluId());
                CluInfoHierarchy c = new CluInfoHierarchy();
                c.setParentRelationType(currentRel.getRelationshipType());
                c.setParentRelationState(currentRel.getRelationshipState());
                c.setCluInfo(clu);
                currentClu.getChildren().add(c);
                for (RelationshipHierarchy h : currentRel.getChildren()) {
                    buildRelatedCourse(c, h);
                }
            }
        }
    }

    private SaveResult<CluInfoHierarchy> saveHierarchy(CreditCourseHelper course) throws AssemblyException {

        CluInfoHierarchy clus = buildCluInfoHierarchyFromData(course);

        CourseAssembler.RelationshipHierarchy hierarchy = new CourseAssembler.RelationshipHierarchy();
        CourseAssembler.RelationshipHierarchy formats = new CourseAssembler.RelationshipHierarchy(FORMAT_RELATION_TYPE, "Active");
        CourseAssembler.RelationshipHierarchy activities = new CourseAssembler.RelationshipHierarchy(ACTIVITY_RELATION_TYPE, "Active");

        hierarchy.addChild(formats);
        formats.addChild(activities);
        SaveResult<CluInfoHierarchy> result = new SaveResult<CluInfoHierarchy>();

        try {
            List<ValidationResultInfo> val = validate(null);
            result.setValidationResults(val);

            if (isValid(val)) {
                saveClus(clus, null);
                saveRelations(null, clus);
                result.setValue(removeOrphans(clus));
            } else {
                result.setValue(clus);
            }

        } catch (Exception e) {
            throw new AssemblyException("Unable to save CluInfoHierarchy", e);
        }

//        CluInfoHierarchyAssembler cluHierarchyAssembler = new CluInfoHierarchyAssembler();
//        cluHierarchyAssembler.setHierarchy(hierarchy);

        buildJointsFromData(result.getValue().getCluInfo().getId(),course);

        return result;
    }

    private CluInfoHierarchy removeOrphans(CluInfoHierarchy input) {
        if (input.getModificationState() == ModificationState.DELETED) {
            return null;
        }
        List<CluInfoHierarchy> children = new ArrayList<CluInfoHierarchy>();
        for (CluInfoHierarchy c : input.getChildren()) {
            c = removeOrphans(c);
            if (c != null) {
                children.add(c);
            }
        }
        input.setChildren(children);
        return input;
    }

    private void saveRules(String courseId, LuData luData) throws Exception{
        LuRuleInfoPersistanceBean ruleInfoBean = new LuRuleInfoPersistanceBean();
        ruleInfoBean.setLuService(luService);
        ruleInfoBean.setTranslationService(translationService);
        ruleInfoBean.updateRules(courseId, luData.getRuleInfos());
    }

    private CluInfoHierarchy buildCluInfoHierarchyFromData(CreditCourseHelper course) throws AssemblyException {
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
            result = getCluInfoHierarchyFromId(course.getId());
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

            List<AcademicSubjectOrgInfo> subjectOrgs = new ArrayList<AcademicSubjectOrgInfo>();
            if (course.getAcademicSubjectOrgs() != null) {
                for (Data.Property p : course.getAcademicSubjectOrgs()) {
                    String org = p.getValue();
                    AcademicSubjectOrgInfo info = new AcademicSubjectOrgInfo();
                    info.setOrgId(org);
                    subjectOrgs.add(info);
                }
            }
            courseClu.setAcademicSubjectOrgs(subjectOrgs);

            List<String> campuses = new ArrayList<String>();
            if (course.getCampusLocations() != null) {
                for (Data.Property p : course.getCampusLocations()) {
                    String campus = p.getValue();
                    campuses.add(campus);
                }
            }
            courseClu.setCampusLocations(campuses);


//			mergeAlternateIdentifiers(course, courseClu);

            //AuthzCheck
            if(courseMetadata.getProperties().get("description").getWriteAccess()!=WriteAccess.NEVER){
                courseClu.setDescr(getRichText(course.getDescription()));
            }

            
            //AuthzCheck
            if(courseMetadata.getProperties().get("duration").getWriteAccess()!=WriteAccess.NEVER){
                AmountInfo time = courseClu.getIntensity();
                if (time == null) {
                    time = new AmountInfo();
                    courseClu.setIntensity(time);
                }
                if (course.getDuration() != null) {
                    time.setUnitType(course.getDuration().getTermType());
                    if(course.getDuration().getQuantity() != null) {
                        time.setUnitQuantity(course.getDuration().getQuantity().toString());
                    }
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

    private void buildJointsFromData(String parentCourseId,CreditCourseHelper course ) throws AssemblyException{
        try {
            if (course.getJoints() == null) {
                return;
            }
            // CreditCourseJointsHelper joints =
            // CreditCourseJointsHelper.wrap(course.getJoints());
            for (Data.Property p : course.getJoints()) {
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

    private void saveClus(CluInfoHierarchy input, String parentId) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DependentObjectsExistException {

        CluInfo result = null;
        CluInfo clu = input.getCluInfo();
        if (input.getModificationState() != null) {
            switch (input.getModificationState()) {
                case CREATED:
                    result = luService.createClu(clu.getType(), clu);
                    break;
                case UPDATED:
                    result = luService.updateClu(clu.getId(), clu);
                    break;
                case DELETED:
                    // back out any relationships in case of RI
                    if (parentId != null){
                        List<CluCluRelationInfo> relations = luService.getCluCluRelationsByClu(parentId);
                        for (CluCluRelationInfo rel : relations) {
                            luService.deleteCluCluRelation(rel.getId());
                        }
                        luService.deleteClu(clu.getId());
                    }
                    break;
                default:
                    // do nothing
            }
        }
        if (result != null) {
            input.setCluInfo(result);
        }
        for (CluInfoHierarchy child : input.getChildren()) {
            saveClus(child, input.getCluInfo().getId());
        }
    }

    private void saveRelations(String parentId, CluInfoHierarchy input) throws AlreadyExistsException, CircularReferenceException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (parentId != null && input.getModificationState() == ModificationState.CREATED) {
            CluCluRelationInfo rel = new CluCluRelationInfo();
            rel.setCluId(parentId);
            rel.setRelatedCluId(input.getCluInfo().getId());
            rel.setType(input.getParentRelationType());
            rel.setState(input.getParentRelationState());
            System.out.println("Creating relation: " + rel.getCluId() + "\t" + rel.getType() + "\t" + rel.getRelatedCluId());
            try {
                luService.createCluCluRelation(rel.getCluId(), rel.getRelatedCluId(), rel.getType(), rel);
            } catch (CircularRelationshipException e) {
                throw new CircularReferenceException(e);
            }
        }
        for (CluInfoHierarchy h : input.getChildren()) {
            saveRelations(input.getCluInfo().getId(), h);
        }
    }

    private boolean isValid(List<ValidationResultInfo> val) {
        boolean result = true;
        if (val != null) {
            for (ValidationResultInfo v : val) {
                if (v.getLevel() == ErrorLevel.ERROR) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    private void buildJointsFromCluInfo(CluInfo clu,CreditCourseJointsHelper joint) throws AssemblyException{
        try{
//          List<CluInfo> clus = luService.getClusByRelation(parentCourseId, JOINT_RELATION_TYPE);
            joint.setCourseId(clu.getId());
            joint.setType(JOINT_RELATION_TYPE);
            joint.setSubjectArea(clu.getStudySubjectArea());
            joint.setCourseTitle(clu.getOfficialIdentifier().getLongName());

        }
        catch(Exception e){
            throw new AssemblyException("Unable to get cluClu Joint Relationship");
        }

    }

    private void buildFormatsFromCluInfo(CreditCourseHelper course, CluInfoHierarchy cluHierarchy) throws DoesNotExistException,
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

        //TimeAmountInfoHelper time = TimeAmountInfoHelper.wrap(timeamountAssembler.assemble(clu.getIntensity()));
        AmountInfo time = clu.getIntensity();
        if (time != null) {
            CreditCourseActivityContactHoursHelper hours = CreditCourseActivityContactHoursHelper.wrap(new Data());
            if(time.getUnitQuantity() != null) {
                hours.setHrs(Integer.valueOf(time.getUnitQuantity()));
            }
            hours.setPer(time.getUnitType());
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

    private void buildFormatUpdates(CluInfoHierarchy courseHierarchy, CreditCourseHelper course) throws AssemblyException {
        if (course.getFormats() == null) {
            return;
        }
        for (Data.Property p : course.getFormats()) {
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
        for (Data.Property p : format.getActivities()) {
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


                AmountInfo time = activityClu.getIntensity();
                if (time == null) {
                    time = new AmountInfo();
                    activityClu.setIntensity(time);
                }
                if (activity.getContactHours() != null) {
                    time.setUnitType(activity.getContactHours().getPer());
                    time.setUnitQuantity(String.valueOf(activity.getContactHours().getHrs()));
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

        for (Data.Property p : course.getCrossListings()) {
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

        for (Data.Property p : course.getVersions()) {
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

    // TODO rewrite alternate identifier code to create separate properties per alternate identifier type
//  private void mergeAlternateIdentifiers(CreditCourse course,
//          CluInfo courseClu) throws AssemblyException {
//      // create map of existing identifiers
//      Map<String, CluIdentifierInfo> ids = new HashMap<String, CluIdentifierInfo>();
//      for (CluIdentifierInfo id : courseClu.getAlternateIdentifiers()) {
//          if (id.getId() != null) {
//              ids.put(id.getId(), id);
//          }
//      }
//      for (Property p : course.getAlternateIdentifiers()) {
//          CluIdentifierInfoData data = p.getValue();
//          CluIdentifierInfo idSource = cluIdentifierAssembler.disassemble(data);
//          CluIdentifierInfo idTarget = ids.get(idSource.getId());
//          if (idTarget == null) {
//              // newly created
//              courseClu.getAlternateIdentifiers().add(idSource);
//          } else {
//              // modified
//              // skipping some fields that shouldn't be reassigned like id, cluId, etc
//              idTarget.setCode(idSource.getCode());
//              idTarget.setDivision(idSource.getDivision());
//              idTarget.setLevel(idSource.getLevel());
//              idTarget.setLongName(idSource.getLongName());
//              idTarget.setOrgId(idSource.getOrgId());
//              idTarget.setShortName(idSource.getShortName());
//              idTarget.setSuffixCode(idSource.getSuffixCode());
//              idTarget.setVariation(idSource.getVariation());
//              
//              ids.remove(idTarget.getId());
//          }
//      }
//      
//      // anything left in the ids map is something that was deleted by the client
//      for (CluIdentifierInfo c : ids.values()) {
//          courseClu.getAlternateIdentifiers().remove(c);
//      }
//  }
    private CluInfoHierarchy findChildByCluId(CluInfoHierarchy parent, String cluId) {
        for (CluInfoHierarchy c : parent.getChildren()) {
            if (c.getCluInfo().getId() != null && c.getCluInfo().getId().equals(cluId)) {
                return c;
            }
        }
        return null;
    }

    private List<RuleInfo> getRules(String courseId) throws Exception{
        LuRuleInfoPersistanceBean ruleInfoBean = new LuRuleInfoPersistanceBean();
        ruleInfoBean.setLuService(luService);
        ruleInfoBean.setTranslationService(translationService);
        return ruleInfoBean.fetchRules(courseId);
    }

    private LoInfoPersistenceBean getLoInfoPersistenceBean() {
        if (null == loInfoBean) {
            loInfoBean = new LoInfoPersistenceBean();
            loInfoBean.setLoService(loService);
            loInfoBean.setLuService(luService);
        }
        return loInfoBean;
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

    private RichTextInfo getRichText(RichTextInfoHelper hlp) throws AssemblyException {
        if (hlp == null || hlp.getData() == null) {
            return null;
        }
        return richtextAssembler.disassemble(hlp.getData());
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
            if (permissions !=null){
	            for(KimPermissionInfo permission:permissions){
	                String dtoFieldKey = permission.getDetails().get("dtoFieldKey");
	                String fieldAccessLevel = permission.getDetails().get("fieldAccessLevel");
	                permMap.put(dtoFieldKey, fieldAccessLevel);
	            }
            }
            return permMap;
        }catch(Exception e){
            LOG.warn("Error calling permission service.",e);
        }
        return null;
    }

	private SingleUseLoInfoAssembler getSingleUseLoAssembler() {
		if (loAssembler == null) {
			loAssembler = new SingleUseLoInfoAssembler();
			loAssembler.setLoService(loService);
			loAssembler.setLuService(luService);
		}
		return loAssembler;
	}

    public LuService getLuService() {
        return luService;
    }

    public void setLuService(LuService luService) {
        this.luService = luService;
    }

    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    public void setLoService(LearningObjectiveService loService) {
        this.loService = loService;
    }

    public void setTranslationService(TranslationService translationService) {
        this.translationService = translationService;
    }

    public void setSearchDispatcher(SearchDispatcher searchDispatcher) {
        this.searchDispatcher = searchDispatcher;
    }

    public static class RelationshipHierarchy {
        private final String relationshipType;
        private final String relationshipState;
        private final List<RelationshipHierarchy> children = new ArrayList<RelationshipHierarchy>();
        public RelationshipHierarchy() {
            this.relationshipType = null;
            this.relationshipState = null;
        }
        public RelationshipHierarchy(final String relationshipType, final String relationshipState) {
            this.relationshipType = relationshipType;
            this.relationshipState = relationshipState;
        }
        public RelationshipHierarchy addChild(RelationshipHierarchy child) {
            children.add(child);
            return this;
        }
        public String getRelationshipType() {
            return relationshipType;
        }
        public List<RelationshipHierarchy> getChildren() {
            return children;
        }
        public String getRelationshipState() {
            return relationshipState;
        }

    }
}
