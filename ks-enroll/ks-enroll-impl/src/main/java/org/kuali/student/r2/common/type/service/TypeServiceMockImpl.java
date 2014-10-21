package org.kuali.student.r2.common.type.service;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.ExamOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;


import java.util.*;

public class TypeServiceMockImpl implements TypeService, MockService {

    private Map<String, TypeInfo> allTypes = new HashMap<String, TypeInfo>();

    /*
     * Store each TypeTypeRelationInfo in a Map by TypeTypeRelation type.
     * The key is the owner type key.
     * The value is a Map with the related type type key as the key and the TypeTypeRelationInfo as the value.
     */

    //  Storage for TypeType relations.
    private Map<String, List<TypeTypeRelationInfo>> typeTypeRelations = new HashMap<String, List<TypeTypeRelationInfo>>();

    public TypeServiceMockImpl() {
		super();
		init();
	}

    @Override
	public void clear() {
        this.allTypes.clear();
        this.typeTypeRelations.clear();
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
        return new ArrayList<TypeInfo>();
    }

    @Override
    public List<String> getRefObjectUris( ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented."); // TODO implement
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectUri(String refObjectUri,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<TypeInfo>(){

        };
    }

    @Override
    public List<TypeInfo> getTypesForGroupType(String groupTypeKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TypeInfo> result = new ArrayList<TypeInfo>();
        TypeInfo typeInfo = new TypeInfo();
        typeInfo.setKey(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
        result.add(typeInfo);
        return result;
    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey,  ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //  Get all of the "allowed" relations.
        List<TypeTypeRelationInfo> allowedRelations = typeTypeRelations.get(TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY);
        List<TypeInfo> typeInfos = new ArrayList<TypeInfo>();
        //  Iterate through the list and return the ones where the owner key matches.
        for (TypeTypeRelationInfo relationInfo : allowedRelations) {
            if (StringUtils.equals(relationInfo.getOwnerTypeKey(), ownerTypeKey)) {
               TypeInfo typeInfo = allTypes.get(relationInfo.getRelatedTypeKey());
                typeInfos.add(typeInfo);
            }
        }
        return typeInfos;
    }

    private TypeInfo getType(String typeKey) {
        return allTypes.get(typeKey);
    }

    //    @Override
    public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI,  ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new OperationFailedException("Method not implemented."); // TODO implement
    }

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
    public List<TypeTypeRelationInfo> getTypeTypeRelationsByOwnerAndType(String ownerTypeKey, String relationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //  Get relations of the given type.
        List<TypeTypeRelationInfo> relationships = typeTypeRelations.get(relationTypeKey);
        if (relationships == null) {
            throw new DoesNotExistException(String.format("No type type relations exist for key [%s].", relationTypeKey));
        }

        //  Iterate through the list and return the ones where the owner key matches.
        List<TypeTypeRelationInfo> typeTypeRelationInfos = new ArrayList<TypeTypeRelationInfo>();
        for (TypeTypeRelationInfo relationInfo : relationships) {
            if (StringUtils.equals(relationInfo.getOwnerTypeKey(), ownerTypeKey)) {
               typeTypeRelationInfos.add(relationInfo);
            }
        }
        return typeTypeRelationInfos;
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeTypeRelationsByRelatedTypeAndType(String relatedTypeKey, String relationTypeKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //  Get relations of the given type.
        List<TypeTypeRelationInfo> relationships = typeTypeRelations.get(relationTypeKey);
        if (relationships == null) {
            throw new DoesNotExistException(String.format("No type type relations exist for key [%s].", relationTypeKey));
        }

        //  Iterate through the list and return the ones where the owner key matches.
        List<TypeTypeRelationInfo> typeTypeRelationInfos = new ArrayList<TypeTypeRelationInfo>();
        for (TypeTypeRelationInfo relationInfo : relationships) {
            if (StringUtils.equals(relationInfo.getRelatedTypeKey(), relationTypeKey)) {
               typeTypeRelationInfos.add(relationInfo);
            }
        }
        return typeTypeRelationInfos;
    }

    /**
     * Appends TypeTypeRelationInfo with a given related type key to a List.
     * @param from
     * @param to
     * @param relatedTypeKey
     */
    private void appendItemsByRelatedTypeKey(Map<String, Map<String, TypeTypeRelationInfo>> from, List<TypeTypeRelationInfo> to, String relatedTypeKey) {
        for (Map<String, TypeTypeRelationInfo> relationTypes : from.values()) {
            for (TypeTypeRelationInfo ttrInfo : relationTypes.values()) {
                if (ttrInfo.getRelatedTypeKey().equals(relatedTypeKey)) {
                    to.add(ttrInfo);
                }
            }
        }
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
        typeArrays.add(new String[]{AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, "Academic Calendar", "Academic Calendar", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{AtpServiceConstants.ATP_HOLIDAY_CALENDAR_TYPE_KEY, "Holiday Calendar", "Holiday Calendar", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{AtpServiceConstants.ATP_FALL_TYPE_KEY, "Fall", "Fall Semester", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{AtpServiceConstants.ATP_SPRING_TYPE_KEY, "Spring", "Spring Semester", AtpServiceConstants.REF_OBJECT_URI_ATP});
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
        typeArrays.add(new String[]{AtpServiceConstants.ATP_SUMMER_TYPE_KEY, "Summer", "Summer Semester", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.SummerEve", "Summer Eve", "Summer Eve", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{AtpServiceConstants.ATP_WINTER_TYPE_KEY, "Winter", "Winter", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.Adhoc", "Ad hoc", "Ad hoc", AtpServiceConstants.REF_OBJECT_URI_ATP});

        //holidays
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_CHRISTMAS_TYPE_KEY, "Christmas", "Christmas", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_CHRISTMAS_OBSERVED_TYPE_KEY, "Christmas Observed", "Christmas Observed", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_FALL_BREAK_TYPE_KEY, "Fall Break", "Fall Break", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_LABOR_DAY_TYPE_KEY, "Labor Day", "Labor Day", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_INDEPENDENCE_DAY_TYPE_KEY, "Independence Day", "Independence Day", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_INDEPENDENCE_DAY_OBSERVED_TYPE_KEY, "Independence Day Observed", "Independence Day Observed", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_MEMORIAL_DAY_TYPE_KEY, "Memorial Day", "Memorial Day", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_MEMORIAL_DAY_OBSERVED_TYPE_KEY, "Memorial Day Observed", "Memorial Day Observed", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_MLK_DAY_TYPE_KEY, "Martin Luther King Day", "Martin Luther King Day", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_MLK_DAY_OBSERVED_TYPE_KEY, "MLK Day Observed", "MLK Day Observed", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_NEW_YEAR_DAY_TYPE_KEY, "New Years Day", "New Years Day", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_NEW_YEAR_DAY_OBSERVED_TYPE_KEY, "New Years Day Observed", "New Years Day Observed", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_PRESIDENTS_DAY_TYPE_KEY, "Presidents Day", "Presidents Day", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_SPRING_BREAK_TYPE_KEY, "Spring Break", "Spring Break", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_THANKSGIVING_BREAK_TYPE_KEY, "Thanksgiving Break", "Thanksgiving Break", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_VETERANS_DAY_TYPE_KEY, "Veterans Day", "Veterans Day", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_VETERANS_DAY_OBSERVED_TYPE_KEY, "Veterans Day Observed", "Veterans Day Observed", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});

        //events    -- type_key, name, type_descr, REF_OBJECT_URI
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_FAMILY_WEEKEND_TYPE_KEY, "Family Weekend", "Family Weekend", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_HOMECOMING_TYPE_KEY, "Homecoming", "Homecoming", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_GRADUATION_APPLICATION_DEADLINE_TYPE_KEY, "Graduation Application Deadline", "Deadline to apply for Graduation", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_ALUMNI_DAY_TYPE_KEY, "Alumni Day", "Alumni Day", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_BACCALAUREATE_TYPE_KEY, "Baccalaureate", "Baccalaureate", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_COMMENCEMENT_TYPE_KEY, "Commencement", "Commencement", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});

        //for AtpAtpRelations
        typeArrays.add(new String[]{AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, "includes", "Includes", TypeServiceConstants.REF_OBJECT_URI_TYPE_TYPE_RELATION});
        typeArrays.add(new String[]{AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY, "associated", "Associated", TypeServiceConstants.REF_OBJECT_URI_TYPE_TYPE_RELATION});

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
        typeArrays.add(new String[] {LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, "Course Offering", "Course Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_AO_TYPE_KEY, "Lui Lui Relation Delivered via FO to AO", "Lui Lui Relation Delivered via FO to AO", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY, "Lui Lui Relation Associated", "Lui Lui Relation Associated", LuiServiceConstants.REF_OBJECT_URI_LUI});
        typeArrays.add(new String[] {LuiServiceConstants.LUI_SET_COLOCATED_OFFERING_TYPE_KEY, "Lui Set Colocated Offering", "Lui Set Colocated Offering", LuiServiceConstants.REF_OBJECT_URI_LUI});

        //Exam Offerings - Lui
        typeArrays.add(new String[] {ExamOfferingServiceConstants.EXAM_OFFERING_FINAL_TYPE_KEY, "Final Exam Offering", "Final Exam Offering", ExamOfferingServiceConstants.REF_OBJECT_URI_EXAM_OFFERING});

        typeArrays.add(new String[] {LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY, "Lpr Instructor Main", "Lpr Instructor Main", LprServiceConstants.REF_OBJECT_URI_LUI_PERSON_RELATION});
        typeArrays.add(new String[] {LprServiceConstants.COURSE_OFFERING_INSTRUCTOR_MAIN_TYPE_KEY, "Lpr Course Offering Instructor Main", "Lpr Course Offering Instructor Main", LprServiceConstants.REF_OBJECT_URI_LUI_PERSON_RELATION});

        String fallTimeSlotType = SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_FULLTERM_FALL;
        String springTimeSlotType = SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_FULLTERM_SPRING;

        //  Time Slot Types
        typeArrays.add(new String[] {fallTimeSlotType, "Fall Full", "Fall Full Time Slot", SchedulingServiceConstants.REF_OBJECT_URI_SCHEDULE_TIME_SLOT });
        typeArrays.add(new String[] {springTimeSlotType, "Spring Full", "Spring Full Time Slot", SchedulingServiceConstants.REF_OBJECT_URI_SCHEDULE_TIME_SLOT });
        typeArrays.add(new String[] {SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_ADHOC, "Ad Hoc", "Ad Hoc Time Slot", SchedulingServiceConstants.REF_OBJECT_URI_SCHEDULE_TIME_SLOT} );
        typeArrays.add(new String[] {SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_TBA, "TBA", "TBA Time Slot", SchedulingServiceConstants.REF_OBJECT_URI_SCHEDULE_TIME_SLOT} );

        //  Schedule, Schedule Request, Schedule Request Set Types
        typeArrays.add(new String[] {SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, "Schedule Request", "Schedule Request", SchedulingServiceConstants.REF_OBJECT_URI_SCHEDULE_REQUEST}  );
        typeArrays.add(new String[] {SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET, "Schedule Request Set", "Schedule Request SEt", SchedulingServiceConstants.REF_OBJECT_URI_SCHEDULE_REQUEST_SET} );
        typeArrays.add(new String[] {SchedulingServiceConstants.SCHEDULE_TYPE_SCHEDULE, "Schedule", "Schedule", SchedulingServiceConstants.REF_OBJECT_URI_SCHEDULE} );

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
            createTypeTypeRelationInfo(TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY, rgGroupType, type);
        }

        // Term Types Grouping
        Set<TypeInfo> termGroup = new HashSet<TypeInfo>();
        TypeInfo termGroupType = createTypeInfo(AtpServiceConstants.ATP_TERM_GROUPING_TYPE_KEY, "Group for Terms", "Group for terms", AtpServiceConstants.REF_OBJECT_URI_ATP);

        termGroup.add(getType(AtpServiceConstants.ATP_FALL_TYPE_KEY));
        termGroup.add(getType("kuali.atp.type.FallSpring"));
        termGroup.add(getType("kuali.atp.type.HalfFall1"));
        termGroup.add(getType("kuali.atp.type.HalfFall2"));
        termGroup.add(getType(AtpServiceConstants.ATP_WINTER_TYPE_KEY));
        termGroup.add(getType(AtpServiceConstants.ATP_SPRING_TYPE_KEY));
        termGroup.add(getType("kuali.atp.type.HalfSpring1"));
        termGroup.add(getType("kuali.atp.type.HalfSpring2"));
        termGroup.add(getType("kuali.atp.type.SpringBreak"));
        termGroup.add(getType(AtpServiceConstants.ATP_SUMMER_TYPE_KEY));
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
            createTypeTypeRelationInfo(TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY, termGroupType, type);
        }

        //keydates
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_ADVANCED_REGISTRATION_PERIOD_TYPE_KEY, "Advance Registration Period", "Advance Registration Period", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY, "Registration Period", "Registration Period", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_REGISTRATION_BEGINS_TRANSFER_TYPE_KEY, "Registration Begins Transfer", "Registration Begins Transfer", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_DROP_DEADLINE_WITHOUT_RECORD_TYPE_KEY, "Drop Deadline Without Record", "Drop Deadline Without Record", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY, "Final Exam Period", "Final Exam Period", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY, "Grades Due", "Grades Due", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY, "Instructional Period", "Instructional Period", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_FINANCIAL_AID_CENSUS_TYPE_KEY, "FinancialAid Census", "FinancialAid Census", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_COURSE_SELECTION_PERIOD_END_TYPE_KEY, "CourseSelectionPeriodEnd", "CourseSelectionPeriodEnd", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_DROP_DATE_TYPE_KEY, "DropDate", "DropDate",  AtpServiceConstants.REF_OBJECT_URI_MILESTONE});

        //curriculum
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_COORDINATORS_KICKOFF_MEETING_TYPE_KEY, "Coordinators Kickoff Meeting", "Coordinators Kickoff Meeting", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_PROPOSAL_PERIOD_TYPE_KEY, "Proposal Period", "Proposal Period", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_REVIEW_PERIOD_TYPE_KEY, "Review Period", "Review Period", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});

        for (String[] typeArray : typeArrays) {
            createTypeInfo(typeArray[0], typeArray[1], typeArray[2], typeArray[3]);
        }

        //Keydates grouping
        Set<TypeInfo> keydateGroup = new HashSet<TypeInfo>();
        TypeInfo keydateGroupType = createTypeInfo(AtpServiceConstants.MILESTONE_KEYDATE_GROUP, "Group for key dates", "Group for key dates", AtpServiceConstants.REF_OBJECT_URI_MILESTONE);
        keydateGroup.add(getType(AtpServiceConstants.MILESTONE_ADVANCED_REGISTRATION_PERIOD_TYPE_KEY));
        keydateGroup.add(getType(AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY));
        keydateGroup.add(getType(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY));
        keydateGroup.add(getType(AtpServiceConstants.MILESTONE_REGISTRATION_BEGINS_TRANSFER_TYPE_KEY));
        keydateGroup.add(getType(AtpServiceConstants.MILESTONE_DROP_DEADLINE_WITHOUT_RECORD_TYPE_KEY));
        keydateGroup.add(getType(AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY));
        keydateGroup.add(getType(AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY));
        for (TypeInfo type : keydateGroup) {
            createTypeTypeRelationInfo(TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY, keydateGroupType, type);
        }

        //Instructional Keydates grouping
        Set<TypeInfo> inskeydateGroup = new HashSet<TypeInfo>();
        TypeInfo inskeydateGroupType = createTypeInfo("kuali.milestone.type.group.registration", "Group for instructional key dates", "Group for instructional key dates", AtpServiceConstants.REF_OBJECT_URI_MILESTONE);
        inskeydateGroup.add(getType(AtpServiceConstants.MILESTONE_ADVANCED_REGISTRATION_PERIOD_TYPE_KEY));
        inskeydateGroup.add(getType(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY));
        inskeydateGroup.add(getType(AtpServiceConstants.MILESTONE_REGISTRATION_BEGINS_TRANSFER_TYPE_KEY));
        inskeydateGroup.add(getType(AtpServiceConstants.MILESTONE_DROP_DEADLINE_WITHOUT_RECORD_TYPE_KEY));
        for (TypeInfo type : inskeydateGroup) {
            createTypeTypeRelationInfo(TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY, inskeydateGroupType, type);
        }

        //kuali.milestone.type.group.registration
        //registration Keydates grouping
        Set<TypeInfo> regkeydateGroup = new HashSet<TypeInfo>();
        TypeInfo regkeydateGroupType = createTypeInfo("kuali.milestone.type.group.instructional", "Group for registration key dates", "Group for registration key dates", AtpServiceConstants.REF_OBJECT_URI_MILESTONE);
        regkeydateGroup.add(getType(AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY));
        regkeydateGroup.add(getType(AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY));
        regkeydateGroup.add(getType(AtpServiceConstants.MILESTONE_FINANCIAL_AID_CENSUS_TYPE_KEY));
        regkeydateGroup.add(getType(AtpServiceConstants.MILESTONE_COMMENCEMENT_TYPE_KEY));
        regkeydateGroup.add(getType(AtpServiceConstants.MILESTONE_COURSE_SELECTION_PERIOD_END_TYPE_KEY));
        regkeydateGroup.add(getType(AtpServiceConstants.MILESTONE_DROP_DATE_TYPE_KEY));
        regkeydateGroup.add(getType(AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY));
        for (TypeInfo type : regkeydateGroup) {
            createTypeTypeRelationInfo(TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY, regkeydateGroupType, type);
        }

        //curriculum grouping
        Set<TypeInfo> curriculumGroup = new HashSet<TypeInfo>();
        TypeInfo curriculumGroupType = createTypeInfo("kuali.milestone.type.group.curriculum", "Curriculum", "Curriculum", AtpServiceConstants.REF_OBJECT_URI_MILESTONE);

        curriculumGroup.add(getType(AtpServiceConstants.MILESTONE_COORDINATORS_KICKOFF_MEETING_TYPE_KEY));
        curriculumGroup.add(getType(AtpServiceConstants.MILESTONE_PROPOSAL_PERIOD_TYPE_KEY));
        curriculumGroup.add(getType(AtpServiceConstants.MILESTONE_REVIEW_PERIOD_TYPE_KEY));
        for (TypeInfo type : curriculumGroup) {
            createTypeTypeRelationInfo(TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY, curriculumGroupType, type);
        }

        //Holiday types Grouping
        Set<TypeInfo> holidayGroup = new HashSet<TypeInfo>();
        TypeInfo holidayGroupType = createTypeInfo(AtpServiceConstants.MILESTONE_HOLIDAY_GROUPING_TYPE_KEY, "Holidays", "Holidays", AtpServiceConstants.REF_OBJECT_URI_MILESTONE);

        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_CHRISTMAS_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_CHRISTMAS_OBSERVED_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_FALL_BREAK_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_LABOR_DAY_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_INDEPENDENCE_DAY_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_INDEPENDENCE_DAY_OBSERVED_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_MEMORIAL_DAY_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_MEMORIAL_DAY_OBSERVED_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_MLK_DAY_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_MLK_DAY_OBSERVED_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_NEW_YEAR_DAY_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_NEW_YEAR_DAY_OBSERVED_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_PRESIDENTS_DAY_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_SPRING_BREAK_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_THANKSGIVING_BREAK_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_VETERANS_DAY_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_VETERANS_DAY_OBSERVED_TYPE_KEY));

        for (TypeInfo type : holidayGroup) {
            createTypeTypeRelationInfo(TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY, holidayGroupType, type);
        }

        //Event types Grouping
        Set<TypeInfo> eventGroup = new HashSet<TypeInfo>();
        TypeInfo eventGroupType = createTypeInfo(AtpServiceConstants.MILESTONE_EVENT_GROUPING_TYPE_KEY, "Acal Events", "Acal Events", AtpServiceConstants.REF_OBJECT_URI_MILESTONE);

        eventGroup.add(getType(AtpServiceConstants.MILESTONE_FAMILY_WEEKEND_TYPE_KEY));
        eventGroup.add(getType(AtpServiceConstants.MILESTONE_HOMECOMING_TYPE_KEY));
        eventGroup.add(getType(AtpServiceConstants.MILESTONE_GRADUATION_APPLICATION_DEADLINE_TYPE_KEY));
        eventGroup.add(getType(AtpServiceConstants.MILESTONE_ALUMNI_DAY_TYPE_KEY));
        eventGroup.add(getType(AtpServiceConstants.MILESTONE_BACCALAUREATE_TYPE_KEY));
        eventGroup.add(getType(AtpServiceConstants.MILESTONE_COMMENCEMENT_TYPE_KEY));
        for (TypeInfo type : eventGroup) {
            createTypeTypeRelationInfo(TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY, eventGroupType, type);
        }

        // Allowed type relations
        List<String[]> allowedArrays = new ArrayList<String[]>();
//        allowedArrays.add(new String[] {"kuali.atp.type.type.relation.allowed.1", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, "kuali.atp.type.FallSpring", "1", "AcademicCalendar can contain FallSpring"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.2", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, "2", "AcademicCalendar can contain Fall"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.3", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, "kuali.atp.type.HalfFall1", "1", "Fall can contain HalfFall1"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.4", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, "kuali.atp.type.HalfFall2", "2", "Fall can contain HalfFall2"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.5", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, AtpServiceConstants.ATP_WINTER_TYPE_KEY, "3", "AcademicCalendar can contain Winter"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.6", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, AtpServiceConstants.ATP_SPRING_TYPE_KEY, "4", "AcademicCalendar can contain Spring"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.7", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SPRING_TYPE_KEY, "kuali.atp.type.HalfSpring1", "1", "Spring can contain HalfSpring1"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.8", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SPRING_TYPE_KEY, "kuali.atp.type.SpringBreak", "2", "Spring can contain SpringBreak"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.9", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SPRING_TYPE_KEY, "kuali.atp.type.HalfSpring2", "3", "Spring can contain HalfSpring2"});
//        allowedArrays.add(new String[] {"kuali.atp.type.type.relation.allowed.10", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, "kuali.atp.type.Session1", "5", "AcademicCalendar can contain Session1"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.11", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, "kuali.atp.type.Session1", "kuali.atp.type.Mini-mester1A", "1", "Session1 can contain Mini-mester1A"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.12", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, "kuali.atp.type.Session1", "kuali.atp.type.Mini-mester1B", "2", "Session1 can contain Mini-mester1B"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.13", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SUMMER_TYPE_KEY, "kuali.atp.type.Session2", "2", "Summer can contain Session2"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.14", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, "kuali.atp.type.Session2", "kuali.atp.type.Mini-mester2C", "1", "Session2 can contain Mini-mester2C"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.15", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, "kuali.atp.type.Session2", "kuali.atp.type.Mini-mester2D", "2", "Session2 can contain Mini-mester2D"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.16", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, AtpServiceConstants.ATP_SUMMER_TYPE_KEY, "6", "AcademicCalendar can contain SummerEve"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.17", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, "kuali.atp.type.SummerEve", "kuali.atp.type.SessionG1", "1", "SummerEve can contain SessionG1"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.18", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, "kuali.atp.type.SummerEve", "kuali.atp.type.SessionG2", "2", "SummerEve can contain SessionG2"});
        // key dates for fall term
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.20", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.MILESTONE_ADVANCED_REGISTRATION_PERIOD_TYPE_KEY, "1", "Fall can have an advanced reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.21", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY, "2", "Fall can have an instructional period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.22", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY, "3", "Fall can have an reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.23", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.MILESTONE_REGISTRATION_BEGINS_TRANSFER_TYPE_KEY, "4", "Fall can transfer reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.24", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.MILESTONE_DROP_DEADLINE_WITHOUT_RECORD_TYPE_KEY, "6", "Fall can have drop deadline"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.25", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY, "6", "Fall can have a final exam period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.26", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY, "7", "Fall can have a grading period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.27", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.MILESTONE_FINANCIAL_AID_CENSUS_TYPE_KEY, "7", "Fall can have a financialaid"});
        // key dates for winter
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.30", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_WINTER_TYPE_KEY, AtpServiceConstants.MILESTONE_ADVANCED_REGISTRATION_PERIOD_TYPE_KEY, "1", "Winter can have an advanced reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.31", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_WINTER_TYPE_KEY, AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY, "2", "Winter can have an instructional period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.32", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_WINTER_TYPE_KEY, AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY, "3", "Winter can have an reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.33", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_WINTER_TYPE_KEY, AtpServiceConstants.MILESTONE_REGISTRATION_BEGINS_TRANSFER_TYPE_KEY, "4", "Winter can transfer reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.34", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_WINTER_TYPE_KEY, AtpServiceConstants.MILESTONE_DROP_DEADLINE_WITHOUT_RECORD_TYPE_KEY, "6", "Winter can have drop deadline"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.35", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_WINTER_TYPE_KEY, AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY, "6", "Winter can have a final exam period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.36", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_WINTER_TYPE_KEY, AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY, "7", "Winter can have a grading period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.37", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.MILESTONE_FINANCIAL_AID_CENSUS_TYPE_KEY, "7", "Fall can have a financialaid"});
        // key dates for spring
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.40", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SPRING_TYPE_KEY, AtpServiceConstants.MILESTONE_ADVANCED_REGISTRATION_PERIOD_TYPE_KEY, "1", "Spring can have an advanced reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.41", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SPRING_TYPE_KEY, AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY, "2", "Spring can have an instructional period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.42", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SPRING_TYPE_KEY, AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY, "3", "Spring can have an reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.43", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SPRING_TYPE_KEY, AtpServiceConstants.MILESTONE_REGISTRATION_BEGINS_TRANSFER_TYPE_KEY, "4", "Spring can transfer reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.44", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SPRING_TYPE_KEY, AtpServiceConstants.MILESTONE_DROP_DEADLINE_WITHOUT_RECORD_TYPE_KEY, "6", "Spring can have drop deadline"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.45", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SPRING_TYPE_KEY, AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY, "6", "Spring can have a final exam period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.46", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SPRING_TYPE_KEY, AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY, "7", "Spring can have a grading period"});

        // key dates for summer
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.50", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SUMMER_TYPE_KEY, AtpServiceConstants.MILESTONE_ADVANCED_REGISTRATION_PERIOD_TYPE_KEY, "1", "Summer can have an advanced reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.51", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SUMMER_TYPE_KEY, AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY, "2", "Summer can have an instructional period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.52", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SUMMER_TYPE_KEY, AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY, "3", "Summer can have an reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.53", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SUMMER_TYPE_KEY, AtpServiceConstants.MILESTONE_REGISTRATION_BEGINS_TRANSFER_TYPE_KEY, "4", "Summer can transfer reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.54", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SUMMER_TYPE_KEY, AtpServiceConstants.MILESTONE_DROP_DEADLINE_WITHOUT_RECORD_TYPE_KEY, "6", "Summer can have drop deadline"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.55", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SUMMER_TYPE_KEY, AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY, "6", "Summer can have a final exam period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.56", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SUMMER_TYPE_KEY, AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY, "7", "Summer can have a grading period"});

        //keydates group
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.60", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, "kuali.milestone.type.group.instructional", "1", "Summer can have a final exam period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.61", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, "kuali.milestone.type.group.registration", "2", "Summer can have a grading period"});

        //  Activity -> ActivityOffering
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.62", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, "1", ""});

        for (String[] allowedArray : allowedArrays) {
            associate(allowedArray[0], allowedArray[1], allowedArray[2], allowedArray[3], allowedArray[4], allowedArray[5]);
        }

        /*
         *  Type type relationships for Time Slots.
         */
        List<String[]> tsTypeTypeArrays = new ArrayList<String[]>();

        String tsGroupingType = SchedulingServiceConstants.TIME_SLOT_TYPE_GROUPING;
        String ttRelationGroupType = TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY;
        String ttTimeSlotToATPType = TypeServiceConstants.TYPE_TYPE_RELATION_ATP2TIMESLOT_TYPE_KEY;

        //  Group Time Slot types.
        tsTypeTypeArrays.add(new String[] {ttRelationGroupType, tsGroupingType, fallTimeSlotType}  );
        tsTypeTypeArrays.add(new String[] {ttRelationGroupType, tsGroupingType, springTimeSlotType}  );
        tsTypeTypeArrays.add(new String[] {ttRelationGroupType, tsGroupingType, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_ADHOC} );
        tsTypeTypeArrays.add(new String[] {ttRelationGroupType, tsGroupingType, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_TBA}  );

        //  Relate Time Slots types to ATP types.
        tsTypeTypeArrays.add(new String[] {ttTimeSlotToATPType, AtpServiceConstants.ATP_FALL_TYPE_KEY, fallTimeSlotType} );
        tsTypeTypeArrays.add(new String[] {ttTimeSlotToATPType, AtpServiceConstants.ATP_SPRING_TYPE_KEY, springTimeSlotType} );

        for (String[] tsTt : tsTypeTypeArrays) {
            createTypeTypeRelationInfo(tsTt[0], tsTt[1], tsTt[2]);
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

    /**
     * Creates a TypeTypeRelationInfo.
     * @param typeTypeTypeKey The type key of the TypeTypeRelationInfo.
     * @param ownerType The owner TypeInfo.
     * @param relatedType The related TypeInfo.
     * @return A TypeTypeRelationInfo.
     */
    private TypeTypeRelationInfo createTypeTypeRelationInfo(String typeTypeTypeKey, TypeInfo ownerType, TypeInfo relatedType) {
        return createTypeTypeRelationInfo(typeTypeTypeKey, ownerType.getKey(), relatedType.getKey());
    }

    /**
     * Creates a TypeTypeRelationInfo.
     *
     * @param typeTypeTypeKey  The type key of the TypeTypeRelationInfo.
     * @param ownerTypeKey The type key of the owner TypeInfo.
     * @param relatedTypeKey  The type key of the related TypeInfo.
     * @return A TypeTypeRelationInfo.
     */
    private TypeTypeRelationInfo createTypeTypeRelationInfo(String typeTypeTypeKey, String ownerTypeKey, String relatedTypeKey) {
        return associate(UUID.randomUUID().toString(), typeTypeTypeKey, ownerTypeKey, relatedTypeKey, "1", "");
    }

    private TypeTypeRelationInfo associate(String relationId, String typeTypeTypeKey, String ownerTypeKey, String relatedTypeKey, String relationRank, String name) {
        //  Initialize storage for the typeTypeType if necessary.
        List<TypeTypeRelationInfo> relationshipTypeList = typeTypeRelations.get(typeTypeTypeKey);
        if (relationshipTypeList == null) {
            relationshipTypeList = new ArrayList<TypeTypeRelationInfo>();
            typeTypeRelations.put(typeTypeTypeKey, relationshipTypeList);
        }

        TypeTypeRelationInfo typeTypeRelationInfo = new TypeTypeRelationInfo();
        typeTypeRelationInfo.setId(relationId);
        typeTypeRelationInfo.setTypeKey(typeTypeTypeKey);
        typeTypeRelationInfo.setOwnerTypeKey(ownerTypeKey);
        typeTypeRelationInfo.setRelatedTypeKey(relatedTypeKey);
        typeTypeRelationInfo.setRank(Integer.parseInt(relationRank));

        relationshipTypeList.add(typeTypeRelationInfo);

        return typeTypeRelationInfo;
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