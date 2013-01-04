package org.kuali.student.r2.common.type.service;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;

import javax.jws.WebParam;
import java.util.*;

public class TypeServiceMockImpl implements TypeService, MockService {

    private Map<String, TypeInfo> allTypes = new HashMap<String, TypeInfo>();
    private Map<String, Map<String, TypeTypeRelationInfo>> relationOwners = new HashMap<String, Map<String, TypeTypeRelationInfo>>();
    private Map<String, Map<String, TypeInfo>> allowedTypes = new HashMap<String, Map<String, TypeInfo>>();
    private Map<String, Map<String, TypeInfo>> groupTypes = new HashMap<String, Map<String, TypeInfo>>();

    
    
    

    public TypeServiceMockImpl() {
		super();
		
		init();
	}

    @Override
	public void clear() {
    	
    	this.allowedTypes.clear();
    	this.allTypes.clear();
    	this.groupTypes.clear();
    	this.relationOwners.clear();

    	init();
		
	}

	@Override
    public TypeInfo getType(String typeKey,  ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        TypeInfo type = getType(typeKey);
        if (type == null) {
            throw new DoesNotExistException(typeKey);
        }
        return type;
    }

    @Override
    public List<TypeInfo> getTypesByKeys(List<String> typeKeys,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented."); // TODO implement
    }

    @Override
    public List<String> getRefObjectUris( ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented."); // TODO implement
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectUri(String refObjectUri,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented."); // TODO implement
    }

    @Override
    public List<TypeInfo> getTypesForGroupType(String groupTypeKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TypeInfo> result = new ArrayList<TypeInfo>();
        TypeInfo typeInfo = new TypeInfo();
        typeInfo.setKey("kuali.lui.type.activity.offering.lecture");
        result.add(typeInfo);
        return result;

    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey,  ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Map<String, TypeInfo> relationTypes = allowedTypes.get(ownerTypeKey);
        if (relationTypes != null) {
            return new ArrayList<TypeInfo>(relationTypes.values());
        } else {
            return new ArrayList<TypeInfo>();
        }
    }

    private TypeInfo getType(String typeKey) {
        return allTypes.get(typeKey);
    }

    //    @Override
    public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI,  ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new OperationFailedException("Method not implemented."); // TODO implement
    }

//    @Override
//    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey, String relatedRefObjectURI,  ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
//        Map<String, TypeInfo> relationTypes = allowedTypes.get(ownerTypeKey);
//        if (relationTypes != null) {
//            return new ArrayList<TypeInfo>(relationTypes.values());
//        } else {
//            return new ArrayList<TypeInfo>();
//        }
//    }
    @Override
    public List<ValidationResultInfo> validateType( String validationTypeKey, TypeInfo typeInfo,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented."); // TODO implement
    }

    @Override
    public TypeInfo createType(String typeKey, TypeInfo typeInfo,  ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented."); // TODO implement
    }

    @Override
    public TypeInfo updateType(String typeKey, TypeInfo typeInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new OperationFailedException("Method not implemented."); // TODO implement
    }

    @Override
    public StatusInfo deleteType(String typeKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented."); // TODO implement
    }

    @Override
    public TypeTypeRelationInfo getTypeTypeRelation( String typeTypeRelationKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented."); // TODO implement
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeTypeRelationsByIds(List<String> typeTypeRelationIds,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented."); // TODO implement
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeTypeRelationsByOwnerAndType( String ownerTypeKey,  String relationTypeKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Map<String, TypeTypeRelationInfo> relationTypes = relationOwners.get(ownerTypeKey);
        if (relationTypes != null) {
            return new ArrayList<TypeTypeRelationInfo>(relationTypes.values());
        } else {
            return new ArrayList<TypeTypeRelationInfo>();
        }
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeTypeRelationsByRelatedTypeAndType( String relatedTypeKey,  String relationTypeKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TypeTypeRelationInfo> list = new ArrayList<TypeTypeRelationInfo>();
        for (Map<String, TypeTypeRelationInfo> relationTypes : relationOwners.values()) {
            for (TypeTypeRelationInfo rel : relationTypes.values()) {
                if (rel.getTypeKey().equals(relationTypeKey)) {
                    if (rel.getRelatedTypeKey().equals(relatedTypeKey)) {
                        list.add(rel);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<ValidationResultInfo> validateTypeTypeRelation( String validationTypeKey, String typeKey,  String typePeerKey, String typeTypeRelationTypeKey,  TypeTypeRelationInfo typeTypeRelationInfo,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented."); // TODO implement
    }

    @Override
    public TypeTypeRelationInfo createTypeTypeRelation( String typeTypeRelationKey, String typeKey,  String typePeerKey,  TypeTypeRelationInfo typeTypeRelationInfo,  ContextInfo contextInfo) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new OperationFailedException("Method not implemented."); // TODO implement
    }

    @Override
    public TypeTypeRelationInfo updateTypeTypeRelation( String typeTypeRelationKey,  TypeTypeRelationInfo typeTypeRelationInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new OperationFailedException("Method not implemented."); // TODO implement
    }

    @Override
    public StatusInfo deleteTypeTypeRelation( String typeTypeRelationKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented."); // TODO implement
    }

    private void init() {
        List<String[]> typeArrays = new ArrayList<String[]>();
        typeArrays.add(new String[]{"kuali.atp.type.AcademicCalendar", "Academic Calendar", "Academic Calendar", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.HolidayCalendar", "Holiday Calendar", "Holiday Calendar", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.Fall", "Fall", "Fall Semester", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.Spring", "Spring", "Spring Semester", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.FallSpring", "Fall-Spring", "Fall & Spring Semesters", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.AY", "AY", "Full Academic Year", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.FY", "FY", "Fiscal Year", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.Holiday", "Holiday", "Holiday", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.SpringBreak", "SpringBreak", "Spring Break", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.Thanksgiving", "Thanksgiving", "Thanksgiving", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.HalfFall1", "Fall Half-Semester 1", "Fall Half-Semester 1", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.HalfFall2", "Fall Half-Semester 2", "Fall Half-Semester 2", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.HalfSpring1", "Spring Half-Semester 1", "Spring Half-Semester 1", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.HalfSpring2", "Spring Half-Semester 1", "Spring Half-Semester 2", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.Mini-mester1A", "Mini Semester 1A", "Mini Semester 1A", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.Mini-mester1B", "Mini Semester 1B", "Mini Semester 1B", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.Mini-mester2C", "Mini Semester 2C", "Mini Semester 2C", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.Mini-mester2D", "Mini Semester 2D", "Mini Semester 2D", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.Session1", "Session 1", "Session 1", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.Session2", "Session 2", "Session 2", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.SessionG1", "Session G1", "Session G1", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.SessionG2", "Session G2", "Session G2", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.Summer", "Summer", "Summer Semester", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.SummerEve", "Summer Eve", "Summer Eve", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.Winter", "Winter", "Winter", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.Adhoc", "Ad hoc", "Ad hoc", AtpServiceConstants.REF_OBJECT_URI_ATP});

        //holidays
        typeArrays.add(new String[]{"kuali.atp.milestone.Christmas", "Christmas", "Christmas", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.ChristmasObserved", "Christmas Observed", "Christmas Observed", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.FallBreak", "Fall Break", "Fall Break", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.LaborDay", "Labor Day", "Labor Day", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.IndependenceDay", "Independence Day", "Independence Day", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.IndependenceDayObserved", "Independence Day Observed", "Independence Day Observed", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.MemorialDay", "Memorial Day", "Memorial Day", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.MemorialDayObserved", "Memorial Day Observed", "Memorial Day Observed", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.MLKDay", "Martin Luther King Day", "Martin Luther King Day", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.MLKDayObserved", "MLK Day Observed", "MLK Day Observed", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.NewYearsDay", "New Years Day", "New Years Day", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.NewYearsDayObserved", "New Years Day Observed", "New Years Day Observed", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.PresidentsDay", "Presidents Day", "Presidents Day", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.SpringBreak", "Spring Break", "Spring Break", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.ThanksgivingBreak", "Thanksgiving Break", "Thanksgiving Break", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.VeteransDay", "Veterans Day", "Veterans Day", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.VeteransDayObserved", "Veterans Day Observed", "Veterans Day Observed", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});

        //events    -- type_key, name, type_descr, REF_OBJECT_URI
        typeArrays.add(new String[]{"kuali.atp.milestone.FamilyWeekend", "Family Weekend", "Family Weekend", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.Homecoming", "Homecoming", "Homecoming", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.GraduationApplicationDeadline", "Graduation Application Deadline", "Deadline to apply for Graduation", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.AlumniDay", "Alumni Day", "Alumni Day", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.Baccalaureate", "Baccalaureate", "Baccalaureate", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.Commencement", "Commencement", "Commencement", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});

        //for AtpAtpRelations
        typeArrays.add(new String[]{"kuali.atp.atp.relation.includes", "includes", "Includes", TypeServiceConstants.REF_OBJECT_URI_TYPE_TYPE_RELATION});
        typeArrays.add(new String[]{"kuali.atp.atp.relation.associated", "associated", "Associated", TypeServiceConstants.REF_OBJECT_URI_TYPE_TYPE_RELATION});

        // Lui
        typeArrays.add(new String[] {LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, "Lecture Activity Offering", "Lecture Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY, "Lab Activity Offering", "Lab Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.DISCUSSION_ACTIVITY_OFFERING_TYPE_KEY, "Discussion Activity Offering", "Discussion Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.TUTORIAL_ACTIVITY_OFFERING_TYPE_KEY, "Tutorial Activity Offering", "Tutorial Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.WEB_LECTURE_ACTIVITY_OFFERING_TYPE_KEY, "Web Lecture Activity Offering", "Web Lecture Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.WEB_DISCUSS_ACTIVITY_OFFERING_TYPE_KEY, "Web Discuss Activity Offering", "Web Discuss Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.DIRECTED_ACTIVITY_OFFERING_TYPE_KEY, "Directed Activity Offering", "Directed Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.STUDIO_ACTIVITY_OFFERING_TYPE_KEY, "Studio Activity Offering", "Studio Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.CORRESPOND_ACTIVITY_OFFERING_TYPE_KEY, "Correspond Activity Offering", "Correspond Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.ACTIVITY_ACTIVITY_OFFERING_TYPE_KEY, "Activity Activity Offering", "Activity Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.COLLOQUIUM_ACTIVITY_OFFERING_TYPE_KEY, "Colloquium Activity Offering", "Colloquium Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.DEMONSTRATION_ACTIVITY_OFFERING_TYPE_KEY, "Demonstration Activity Offering", "Demonstration Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.FIELD_ACTIVITY_OFFERING_TYPE_KEY, "Field Activity Offering", "Field Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.HOMEWORK_ACTIVITY_OFFERING_TYPE_KEY, "Homework Activity Offering", "Homework Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.INDEPEND_ACTIVITY_OFFERING_TYPE_KEY, "Independ Activity Offering", "Independ Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.INTERNSHIP_ACTIVITY_OFFERING_TYPE_KEY, "Internship Activity Offering", "Internship Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.PRIVATE_ACTIVITY_OFFERING_TYPE_KEY, "Private Activity Offering", "Private Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.RECITATION_ACTIVITY_OFFERING_TYPE_KEY, "Recitation Activity Offering", "Recitation Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.RESEARCH_ACTIVITY_OFFERING_TYPE_KEY, "Research Activity Offering", "Research Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.SELF_PACED_ACTIVITY_OFFERING_TYPE_KEY, "Self Paced Activity Offering", "Self Paced Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.COMP_BASED_ACTIVITY_OFFERING_TYPE_KEY, "Comp Based Activity Offering", "Comp Based Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.VIDEO_CONF_ACTIVITY_OFFERING_TYPE_KEY, "Video Conf Activity Offering", "Video Conf Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.CLERKSHIP_ACTIVITY_OFFERING_TYPE_KEY, "Clerkship Activity Offering", "Clerkship Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.CLINIC_ACTIVITY_OFFERING_TYPE_KEY, "Clinic Activity Offering", "Clinic Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.CONFERENCE_ACTIVITY_OFFERING_TYPE_KEY, "Conference Activity Offering", "Conference Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.PRACTICUM_ACTIVITY_OFFERING_TYPE_KEY, "Practicum Activity Offering", "Practicum Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.QUIZ_ACTIVITY_OFFERING_TYPE_KEY, "Quiz Activity Offering", "Quiz Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.SEMINAR_ACTIVITY_OFFERING_TYPE_KEY, "Seminar Activity Offering", "Seminar Activity Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});

        for (String[] typeArray : typeArrays) {
            createTypeInfo(typeArray[0], typeArray[1], typeArray[2], typeArray[3]);
        }
        // Registration Groups Grouping
        Set<TypeInfo> rgGroup = new HashSet<TypeInfo>();
        TypeInfo rgGroupType = createTypeInfo(LuiServiceConstants.ACTIVITY_OFFERING_GROUP_TYPE_KEY, "Group for Registration Groups", "Group for Registration Groups", AtpServiceConstants.REF_OBJECT_URI_ATP);
        rgGroup.add(getType(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY));
        rgGroup.add(getType(LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY));
        rgGroup.add(getType(LuiServiceConstants.DISCUSSION_ACTIVITY_OFFERING_TYPE_KEY));
        rgGroup.add(getType(LuiServiceConstants.TUTORIAL_ACTIVITY_OFFERING_TYPE_KEY));
        rgGroup.add(getType(LuiServiceConstants.WEB_LECTURE_ACTIVITY_OFFERING_TYPE_KEY));
        rgGroup.add(getType(LuiServiceConstants.WEB_DISCUSS_ACTIVITY_OFFERING_TYPE_KEY));
        rgGroup.add(getType(LuiServiceConstants.DIRECTED_ACTIVITY_OFFERING_TYPE_KEY));
        rgGroup.add(getType(LuiServiceConstants.STUDIO_ACTIVITY_OFFERING_TYPE_KEY));
        rgGroup.add(getType(LuiServiceConstants.CORRESPOND_ACTIVITY_OFFERING_TYPE_KEY));
        rgGroup.add(getType(LuiServiceConstants.ACTIVITY_ACTIVITY_OFFERING_TYPE_KEY));
        rgGroup.add(getType(LuiServiceConstants.COLLOQUIUM_ACTIVITY_OFFERING_TYPE_KEY));
        rgGroup.add(getType(LuiServiceConstants.DEMONSTRATION_ACTIVITY_OFFERING_TYPE_KEY));
        rgGroup.add(getType(LuiServiceConstants.FIELD_ACTIVITY_OFFERING_TYPE_KEY));
        rgGroup.add(getType(LuiServiceConstants.HOMEWORK_ACTIVITY_OFFERING_TYPE_KEY));
        rgGroup.add(getType(LuiServiceConstants.INDEPEND_ACTIVITY_OFFERING_TYPE_KEY));
        rgGroup.add(getType(LuiServiceConstants.INTERNSHIP_ACTIVITY_OFFERING_TYPE_KEY));
        rgGroup.add(getType(LuiServiceConstants.PRIVATE_ACTIVITY_OFFERING_TYPE_KEY));
        rgGroup.add(getType(LuiServiceConstants.RECITATION_ACTIVITY_OFFERING_TYPE_KEY));
        rgGroup.add(getType(LuiServiceConstants.RESEARCH_ACTIVITY_OFFERING_TYPE_KEY));
        rgGroup.add(getType(LuiServiceConstants.SELF_PACED_ACTIVITY_OFFERING_TYPE_KEY));
        rgGroup.add(getType(LuiServiceConstants.COMP_BASED_ACTIVITY_OFFERING_TYPE_KEY));
        rgGroup.add(getType(LuiServiceConstants.VIDEO_CONF_ACTIVITY_OFFERING_TYPE_KEY));
        rgGroup.add(getType(LuiServiceConstants.CLERKSHIP_ACTIVITY_OFFERING_TYPE_KEY));
        rgGroup.add(getType(LuiServiceConstants.CLINIC_ACTIVITY_OFFERING_TYPE_KEY));
        rgGroup.add(getType(LuiServiceConstants.CONFERENCE_ACTIVITY_OFFERING_TYPE_KEY));
        rgGroup.add(getType(LuiServiceConstants.PRACTICUM_ACTIVITY_OFFERING_TYPE_KEY));
        rgGroup.add(getType(LuiServiceConstants.QUIZ_ACTIVITY_OFFERING_TYPE_KEY));
        rgGroup.add(getType(LuiServiceConstants.SEMINAR_ACTIVITY_OFFERING_TYPE_KEY));
        for (TypeInfo type : rgGroup) {
            createTypeTypeRelationInfo(rgGroupType, type);
        }

        // Term Types Grouping
        Set<TypeInfo> termGroup = new HashSet<TypeInfo>();
        TypeInfo termGroupType = createTypeInfo("kuali.atp.type.group.term", "Group for Terms", "Group for terms", AtpServiceConstants.REF_OBJECT_URI_ATP);

        termGroup.add(getType("kuali.atp.type.Fall"));
        termGroup.add(getType("kuali.atp.type.FallSpring"));
        termGroup.add(getType("kuali.atp.type.HalfFall1"));
        termGroup.add(getType("kuali.atp.type.HalfFall2"));
        termGroup.add(getType("kuali.atp.type.Winter"));
        termGroup.add(getType("kuali.atp.type.Spring"));
        termGroup.add(getType("kuali.atp.type.HalfSpring1"));
        termGroup.add(getType("kuali.atp.type.HalfSpring2"));
        termGroup.add(getType("kuali.atp.type.SpringBreak"));
        termGroup.add(getType("kuali.atp.type.Summer"));
        termGroup.add(getType("kuali.atp.type.SummerEve"));
        termGroup.add(getType("kuali.atp.type.Session1"));
        termGroup.add(getType("kuali.atp.type.Mini-mester1A"));
        termGroup.add(getType("kuali.atp.type.Mini-mester1B"));
        termGroup.add(getType("kuali.atp.type.Session2"));
        termGroup.add(getType("kuali.atp.type.Mini-mester2C"));
        termGroup.add(getType("kuali.atp.type.Mini-mester2D"));
        termGroup.add(getType("kuali.atp.type.SessionG1"));
        termGroup.add(getType("kuali.atp.type.SessionG2"));
        termGroup.add(getType("kuali.atp.type.Adhoc"));
        for (TypeInfo type : termGroup) {
            createTypeTypeRelationInfo(termGroupType, type);
        }

        //keydates
        typeArrays.add(new String[]{"kuali.atp.milestone.AdvanceRegistrationPeriod", "Advance Registration Period", "Advance Registration Period", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.RegistrationPeriod", "Registration Period", "Registration Period", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.RegistrationBeginsTransfer", "Registration Begins Transfer", "Registration Begins Transfer", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.DropDeadlineWithoutRecord", "Drop Deadline Without Record", "Drop Deadline Without Record", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.FinalExamPeriod", "Final Exam Period", "Final Exam Period", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.GradesDue", "Grades Due", "Grades Due", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.InstructionalPeriod", "Instructional Period", "Instructional Period", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.FinancialAidCensus", "FinancialAid Census", "FinancialAid Census", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.CourseSelectionPeriodEnd", "CourseSelectionPeriodEnd", "CourseSelectionPeriodEnd", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.DropDate", "DropDate", "DropDate",  AtpServiceConstants.REF_OBJECT_URI_MILESTONE});

        //curriculum
        typeArrays.add(new String[]{"kuali.atp.milestone.CoordinatorsKickoffMeeting", "Coordinators Kickoff Meeting", "Coordinators Kickoff Meeting", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.ProposalPeriod", "Proposal Period", "Proposal Period", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{"kuali.atp.milestone.ReviewPeriod", "Review Period", "Review Period", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});

        for (String[] typeArray : typeArrays) {
            createTypeInfo(typeArray[0], typeArray[1], typeArray[2], typeArray[3]);
        }

        //Keydates grouping
        Set<TypeInfo> keydateGroup = new HashSet<TypeInfo>();
        TypeInfo keydateGroupType = createTypeInfo("kuali.milestone.type.group.keydate", "Group for key dates", "Group for key dates", AtpServiceConstants.REF_OBJECT_URI_MILESTONE);
        keydateGroup.add(getType("kuali.atp.milestone.AdvanceRegistrationPeriod"));
        keydateGroup.add(getType("kuali.atp.milestone.InstructionalPeriod"));
        keydateGroup.add(getType("kuali.atp.milestone.RegistrationPeriod"));
        keydateGroup.add(getType("kuali.atp.milestone.RegistrationBeginsTransfer"));
        keydateGroup.add(getType("kuali.atp.milestone.DropDeadlineWithoutRecord"));
        keydateGroup.add(getType("kuali.atp.milestone.FinalExamPeriod"));
        keydateGroup.add(getType("kuali.atp.milestone.GradesDue"));
        for (TypeInfo type : keydateGroup) {
            createTypeTypeRelationInfo(keydateGroupType, type);
        }

        //Instructional Keydates grouping
        Set<TypeInfo> inskeydateGroup = new HashSet<TypeInfo>();
        TypeInfo inskeydateGroupType = createTypeInfo("kuali.milestone.type.group.registration", "Group for instructional key dates", "Group for instructional key dates", AtpServiceConstants.REF_OBJECT_URI_MILESTONE);
        inskeydateGroup.add(getType("kuali.atp.milestone.AdvanceRegistrationPeriod"));
        inskeydateGroup.add(getType("kuali.atp.milestone.RegistrationPeriod"));
        inskeydateGroup.add(getType("kuali.atp.milestone.RegistrationBeginsTransfer"));
        inskeydateGroup.add(getType("kuali.atp.milestone.DropDeadlineWithoutRecord"));
        for (TypeInfo type : inskeydateGroup) {
            createTypeTypeRelationInfo(inskeydateGroupType, type);
        }

        //kuali.milestone.type.group.registration
        //registration Keydates grouping
        Set<TypeInfo> regkeydateGroup = new HashSet<TypeInfo>();
        TypeInfo regkeydateGroupType = createTypeInfo("kuali.milestone.type.group.instructional", "Group for registration key dates", "Group for registration key dates", AtpServiceConstants.REF_OBJECT_URI_MILESTONE);
        regkeydateGroup.add(getType("kuali.atp.milestone.FinalExamPeriod"));
        regkeydateGroup.add(getType("kuali.atp.milestone.GradesDue"));
        regkeydateGroup.add(getType("kuali.atp.milestone.FinancialAidCensus"));
        regkeydateGroup.add(getType("kuali.atp.milestone.Commencement"));
        regkeydateGroup.add(getType("kuali.atp.milestone.CourseSelectionPeriodEnd"));
        regkeydateGroup.add(getType("kuali.atp.milestone.DropDate"));
        regkeydateGroup.add(getType("kuali.atp.milestone.InstructionalPeriod"));
        for (TypeInfo type : regkeydateGroup) {
            createTypeTypeRelationInfo(regkeydateGroupType, type);
        }

        //curriculum grouping
        Set<TypeInfo> curriculumGroup = new HashSet<TypeInfo>();
        TypeInfo curriculumGroupType = createTypeInfo("kuali.milestone.type.group.curriculum", "Curriculum", "Curriculum", AtpServiceConstants.REF_OBJECT_URI_MILESTONE);

        curriculumGroup.add(getType("kuali.atp.milestone.CoordinatorsKickoffMeeting"));
        curriculumGroup.add(getType("kuali.atp.milestone.ProposalPeriod"));
        curriculumGroup.add(getType("kuali.atp.milestone.ReviewPeriod"));
        for (TypeInfo type : curriculumGroup) {
            createTypeTypeRelationInfo(curriculumGroupType, type);
        }

        //Holiday types Grouping
        Set<TypeInfo> holidayGroup = new HashSet<TypeInfo>();
        TypeInfo holidayGroupType = createTypeInfo("kuali.milestone.type.group.holiday", "Holidays", "Holidays", AtpServiceConstants.REF_OBJECT_URI_MILESTONE);

        holidayGroup.add(getType("kuali.atp.milestone.Christmas"));
        holidayGroup.add(getType("kuali.atp.milestone.ChristmasObserved"));
        holidayGroup.add(getType("kuali.atp.milestone.FallBreak"));
        holidayGroup.add(getType("kuali.atp.milestone.LaborDay"));
        holidayGroup.add(getType("kuali.atp.milestone.IndependenceDay"));
        holidayGroup.add(getType("kuali.atp.milestone.IndependenceDayObserved"));
        holidayGroup.add(getType("kuali.atp.milestone.MemorialDay"));
        holidayGroup.add(getType("kuali.atp.milestone.MemorialDayObserved"));
        holidayGroup.add(getType("kuali.atp.milestone.MLKDay"));
        holidayGroup.add(getType("kuali.atp.milestone.MLKDayObserved"));
        holidayGroup.add(getType("kuali.atp.milestone.NewYearsDay"));
        holidayGroup.add(getType("kuali.atp.milestone.NewYearsDayObserved"));
        holidayGroup.add(getType("kuali.atp.milestone.PresidentsDay"));
        holidayGroup.add(getType("kuali.atp.milestone.SpringBreak"));
        holidayGroup.add(getType("kuali.atp.milestone.ThanksgivingBreak"));
        holidayGroup.add(getType("kuali.atp.milestone.VeteransDay"));
        holidayGroup.add(getType("kuali.atp.milestone.VeteransDayObserved"));

        for (TypeInfo type : holidayGroup) {
            createTypeTypeRelationInfo(holidayGroupType, type);
        }

        //Event types Grouping
        Set<TypeInfo> eventGroup = new HashSet<TypeInfo>();
        //MILESTONE_EVENT_GROUPING_TYPE_KEY = "kuali.milestone.type.group.event";
        TypeInfo eventGroupType = createTypeInfo(AtpServiceConstants.MILESTONE_EVENT_GROUPING_TYPE_KEY, "Acal Events", "Acal Events", AtpServiceConstants.REF_OBJECT_URI_MILESTONE);

        eventGroup.add(getType("kuali.atp.milestone.FamilyWeekend"));
        eventGroup.add(getType("kuali.atp.milestone.Homecoming"));
        eventGroup.add(getType("kuali.atp.milestone.GraduationApplicationDeadline"));
        eventGroup.add(getType("kuali.atp.milestone.AlumniDay"));
        eventGroup.add(getType("kuali.atp.milestone.Baccalaureate"));
        eventGroup.add(getType("kuali.atp.milestone.Commencement"));
        for (TypeInfo type : eventGroup) {
            createTypeTypeRelationInfo(eventGroupType, type);
        }

        // Allowed type relations
        List<String[]> allowedArrays = new ArrayList<String[]>();
//        allowedArrays.add(new String[] {"kuali.atp.type.type.relation.allowed.1", "kuali.type.type.relation.type.allowed", "kuali.atp.type.AcademicCalendar", "kuali.atp.type.FallSpring", "1", "AcademicCalendar can contain FallSpring"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.2", "kuali.type.type.relation.type.allowed", "kuali.atp.type.AcademicCalendar", "kuali.atp.type.Fall", "2", "AcademicCalendar can contain Fall"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.3", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Fall", "kuali.atp.type.HalfFall1", "1", "Fall can contain HalfFall1"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.4", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Fall", "kuali.atp.type.HalfFall2", "2", "Fall can contain HalfFall2"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.5", "kuali.type.type.relation.type.allowed", "kuali.atp.type.AcademicCalendar", "kuali.atp.type.Winter", "3", "AcademicCalendar can contain Winter"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.6", "kuali.type.type.relation.type.allowed", "kuali.atp.type.AcademicCalendar", "kuali.atp.type.Spring", "4", "AcademicCalendar can contain Spring"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.7", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Spring", "kuali.atp.type.HalfSpring1", "1", "Spring can contain HalfSpring1"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.8", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Spring", "kuali.atp.type.SpringBreak", "2", "Spring can contain SpringBreak"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.9", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Spring", "kuali.atp.type.HalfSpring2", "3", "Spring can contain HalfSpring2"});
//        allowedArrays.add(new String[] {"kuali.atp.type.type.relation.allowed.10", "kuali.type.type.relation.type.allowed", "kuali.atp.type.AcademicCalendar", "kuali.atp.type.Session1", "5", "AcademicCalendar can contain Session1"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.11", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Session1", "kuali.atp.type.Mini-mester1A", "1", "Session1 can contain Mini-mester1A"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.12", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Session1", "kuali.atp.type.Mini-mester1B", "2", "Session1 can contain Mini-mester1B"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.13", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Summer", "kuali.atp.type.Session2", "2", "Summer can contain Session2"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.14", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Session2", "kuali.atp.type.Mini-mester2C", "1", "Session2 can contain Mini-mester2C"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.15", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Session2", "kuali.atp.type.Mini-mester2D", "2", "Session2 can contain Mini-mester2D"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.16", "kuali.type.type.relation.type.allowed", "kuali.atp.type.AcademicCalendar", "kuali.atp.type.Summer", "6", "AcademicCalendar can contain SummerEve"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.17", "kuali.type.type.relation.type.allowed", "kuali.atp.type.SummerEve", "kuali.atp.type.SessionG1", "1", "SummerEve can contain SessionG1"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.18", "kuali.type.type.relation.type.allowed", "kuali.atp.type.SummerEve", "kuali.atp.type.SessionG2", "2", "SummerEve can contain SessionG2"});
        // key dates for fall term
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.20", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Fall", "kuali.atp.milestone.AdvanceRegistrationPeriod", "1", "Fall can have an advanced reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.21", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Fall", "kuali.atp.milestone.InstructionalPeriod", "2", "Fall can have an instructional period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.22", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Fall", "kuali.atp.milestone.RegistrationPeriod", "3", "Fall can have an reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.23", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Fall", "kuali.atp.milestone.RegistrationBeginsTransfer", "4", "Fall can transfer reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.24", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Fall", "kuali.atp.milestone.DropDeadlineWithoutRecord", "6", "Fall can have drop deadline"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.25", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Fall", "kuali.atp.milestone.FinalExamPeriod", "6", "Fall can have a final exam period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.26", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Fall", "kuali.atp.milestone.GradesDue", "7", "Fall can have a grading period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.27", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Fall", "kuali.atp.milestone.FinancialAidCensus", "7", "Fall can have a financialaid"});
        // key dates for winter
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.30", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Winter", "kuali.atp.milestone.AdvanceRegistrationPeriod", "1", "Winter can have an advanced reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.31", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Winter", "kuali.atp.milestone.InstructionalPeriod", "2", "Winter can have an instructional period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.32", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Winter", "kuali.atp.milestone.RegistrationPeriod", "3", "Winter can have an reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.33", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Winter", "kuali.atp.milestone.RegistrationBeginsTransfer", "4", "Winter can transfer reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.34", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Winter", "kuali.atp.milestone.DropDeadlineWithoutRecord", "6", "Winter can have drop deadline"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.35", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Winter", "kuali.atp.milestone.FinalExamPeriod", "6", "Winter can have a final exam period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.36", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Winter", "kuali.atp.milestone.GradesDue", "7", "Winter can have a grading period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.37", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Fall", "kuali.atp.milestone.FinancialAidCensus", "7", "Fall can have a financialaid"});
        // key dates for spring
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.40", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Spring", "kuali.atp.milestone.AdvanceRegistrationPeriod", "1", "Spring can have an advanced reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.41", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Spring", "kuali.atp.milestone.InstructionalPeriod", "2", "Spring can have an instructional period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.42", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Spring", "kuali.atp.milestone.RegistrationPeriod", "3", "Spring can have an reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.43", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Spring", "kuali.atp.milestone.RegistrationBeginsTransfer", "4", "Spring can transfer reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.44", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Spring", "kuali.atp.milestone.DropDeadlineWithoutRecord", "6", "Spring can have drop deadline"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.45", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Spring", "kuali.atp.milestone.FinalExamPeriod", "6", "Spring can have a final exam period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.46", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Spring", "kuali.atp.milestone.GradesDue", "7", "Spring can have a grading period"});

        // key dates for summer
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.50", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Summer", "kuali.atp.milestone.AdvanceRegistrationPeriod", "1", "Summer can have an advanced reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.51", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Summer", "kuali.atp.milestone.InstructionalPeriod", "2", "Summer can have an instructional period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.52", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Summer", "kuali.atp.milestone.RegistrationPeriod", "3", "Summer can have an reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.53", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Summer", "kuali.atp.milestone.RegistrationBeginsTransfer", "4", "Summer can transfer reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.54", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Summer", "kuali.atp.milestone.DropDeadlineWithoutRecord", "6", "Summer can have drop deadline"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.55", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Summer", "kuali.atp.milestone.FinalExamPeriod", "6", "Summer can have a final exam period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.56", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Summer", "kuali.atp.milestone.GradesDue", "7", "Summer can have a grading period"});

        //keydates group
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.60", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Fall", "kuali.milestone.type.group.instructional", "1", "Summer can have a final exam period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.61", "kuali.type.type.relation.type.allowed", "kuali.atp.type.Fall", "kuali.milestone.type.group.registration", "2", "Summer can have a grading period"});


        for (String[] allowedArray : allowedArrays) {
            associate(allowedArray[0], allowedArray[1], allowedArray[2], allowedArray[3], allowedArray[4], allowedArray[5]);
        }

    }

    private TypeInfo createTypeInfo(String typeKey, String typeName, String descr, String refObjectUri) {
        TypeInfo type = new TypeInfo();
        type.setKey(typeKey);
        type.setName(typeName);
        type.setDescr(new RichTextHelper().fromPlain(descr));
        type.setRefObjectUri(refObjectUri);
        type.setEffectiveDate(new Date());
        allTypes.put(type.getKey(), type);
        return type;
    }

    private TypeTypeRelationInfo createTypeTypeRelationInfo(TypeInfo ownerType, TypeInfo relatedType) {
        return createTypeTypeRelationInfo(ownerType.getKey(), relatedType.getKey());
    }

    private TypeTypeRelationInfo createTypeTypeRelationInfo(String ownerTypeKey, String relatedTypeKey) {
        TypeTypeRelationInfo relation = new TypeTypeRelationInfo();
        relation.setOwnerTypeKey(ownerTypeKey);
        relation.setRelatedTypeKey(relatedTypeKey);

        Map<String, TypeTypeRelationInfo> relationTypes = relationOwners.get(relation.getOwnerTypeKey());
        if (null == relationTypes) {
            relationTypes = (Map) new HashMap<String, Map<String, TypeTypeRelationInfo>>();
            relationOwners.put(relation.getOwnerTypeKey(), relationTypes);
        }
        relationTypes.put(relation.getRelatedTypeKey(), relation);

        return relation;
    }

    private void associate(String relationKey, String relationTypeKey, String ownerTypeKey, String relatedTypeKey, String relationRank, String relationName) {
        TypeTypeRelationInfo relation = new TypeTypeRelationInfo();

        relation.setTypeKey(relationTypeKey);
        relation.setOwnerTypeKey(ownerTypeKey);
        relation.setRelatedTypeKey(relatedTypeKey);
        relation.setRank(Integer.parseInt(relationRank));

        Map<String, TypeInfo> types = allowedTypes.get(relation.getOwnerTypeKey());
        if (null == types) {
            types = (Map) new HashMap<String, Map<String, TypeInfo>>();
            allowedTypes.put(relation.getOwnerTypeKey(), types);
        }
        TypeInfo relatedType = allTypes.get(relation.getRelatedTypeKey());
        types.put(relation.getRelatedTypeKey(), relatedType);
    }

    @Override
    public List<String> searchForTypeKeys(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> searchForTypeTypeRelationIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<TypeTypeRelationInfo> searchForTypeTypeRelations(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<TypeInfo> searchForTypes(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}