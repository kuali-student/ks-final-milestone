package org.kuali.student.r2.core.class1.type.service.impl;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;

/**
 * Loader for type data.
 */
public class TypeServiceDataLoader {

    private ContextInfo contextInfo;
    private String principalId = "test";

    private TypeService typeService;

    public TypeServiceDataLoader() {
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
        contextInfo.setCurrentDate(new java.util.Date());
    }

    public TypeServiceDataLoader(TypeService typeService) {
        this();
        this.typeService = typeService;
    }

    public TypeService getTypeService() {
        return this.typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    public void load() {
        String fallTimeSlotType =  SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_FULLTERM_FALL;
        String springTimeSlotType =  SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_FULLTERM_SPRING;

        //  {key, name, desc, refObjUri}
        String[][] typeData = {
            //  Time Slot Types
            {fallTimeSlotType, "Fall Full", "Fall Full Time Slot", SchedulingServiceConstants.REF_OBJECT_URI_SCHEDULE_TIME_SLOT },
            {springTimeSlotType, "Spring Full", "Spring Full Time Slot", SchedulingServiceConstants.REF_OBJECT_URI_SCHEDULE_TIME_SLOT },
            {SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_ADHOC, "Ad Hoc", "Ad Hoc Time Slot", SchedulingServiceConstants.REF_OBJECT_URI_SCHEDULE_TIME_SLOT},
            {SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_TBA, "TBA", "TBA Time Slot", SchedulingServiceConstants.REF_OBJECT_URI_SCHEDULE_TIME_SLOT},
            //  ATP Types
            {AtpServiceConstants.ATP_FALL_TYPE_KEY, "Fall Term", "Fall Term", AtpServiceConstants.REF_OBJECT_URI_ATP},
            {AtpServiceConstants.ATP_SPRING_TYPE_KEY, "Spring Term", "Spring Term", AtpServiceConstants.REF_OBJECT_URI_ATP},
            //  Schedule, Schedule Request, Schedule Request Set Types
            {SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, "Schedule Request", "Schedule Request", SchedulingServiceConstants.REF_OBJECT_URI_SCHEDULE_REQUEST},
            {SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET, "Schedule Request Set", "Schedule Request SEt", SchedulingServiceConstants.REF_OBJECT_URI_SCHEDULE_REQUEST_SET},
            {SchedulingServiceConstants.SCHEDULE_TYPE_SCHEDULE, "Schedule", "Schedule", SchedulingServiceConstants.REF_OBJECT_URI_SCHEDULE}
        };

        for (String[] t: typeData) {
            TypeInfo typeInfo = new TypeInfo();
            typeInfo.setKey(t[0]);
            typeInfo.setName(t[1]);
            typeInfo.setDescr(new RichTextInfo(t[2], t[2]));
            typeInfo.setRefObjectUri(t[3]);
            try {
                getTypeService().createType(t[0], typeInfo, contextInfo);
            } catch(Exception e) {
                throw new RuntimeException("Type data load failed.", e);
            }
        }

        // Type type relationships for Time Slots.
        String tsGroupingType = SchedulingServiceConstants.TIME_SLOT_TYPE_GROUPING;
        String ttRelationGroupType = TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY;
        String ttTimeSlotToATPType = TypeServiceConstants.TYPE_TYPE_RELATION_ATP2TIMESLOT_TYPE_KEY;

        //  {ownerKey, relatedKey, typeTypeTypeKey}
        String[][] typeTypeData = {
            //  Group Time Slot types.
            {tsGroupingType, fallTimeSlotType, ttRelationGroupType},
            {tsGroupingType, springTimeSlotType, ttRelationGroupType},
            {tsGroupingType, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_ADHOC, ttRelationGroupType},
            {tsGroupingType, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_TBA, ttRelationGroupType},

            //  Relate Time Slots types to ATP types.
            {AtpServiceConstants.ATP_FALL_TYPE_KEY, fallTimeSlotType, ttTimeSlotToATPType},
            {AtpServiceConstants.ATP_SPRING_TYPE_KEY, springTimeSlotType, ttTimeSlotToATPType}
        };

        for (String[] tt: typeTypeData) {
            TypeTypeRelationInfo typeTypeRelationInfo = new TypeTypeRelationInfo();
            typeTypeRelationInfo.setOwnerTypeKey(tt[0]);
            typeTypeRelationInfo.setRelatedTypeKey(tt[1]);
            typeTypeRelationInfo.setTypeKey(tt[2]);
            typeTypeRelationInfo.setStateKey(TypeServiceConstants.TYPE_TYPE_RELATION_ACTIVE_STATE_KEY);
            typeTypeRelationInfo.setRank(1); //  This may have to be made dynamic.
            try {
                getTypeService().createTypeTypeRelation(tt[2], tt[0],tt[1], typeTypeRelationInfo, contextInfo);
            } catch(Exception e) {
                throw new RuntimeException("Type-type data load failed.", e);
            }
        }
    }

    public static TypeTypeRelationInfo createTypeTypeRelationInfo(String ownerTypeKey, String relatedTypeKey, String typeTypeTypeKey) {
        TypeTypeRelationInfo typeTypeRelationInfo = new TypeTypeRelationInfo();
        typeTypeRelationInfo.setStateKey(TypeServiceConstants.TYPE_TYPE_RELATION_ACTIVE_STATE_KEY);
        typeTypeRelationInfo.setTypeKey(typeTypeTypeKey);
        typeTypeRelationInfo.setOwnerTypeKey(ownerTypeKey);
        typeTypeRelationInfo.setRelatedTypeKey(relatedTypeKey);
        typeTypeRelationInfo.setRank(1);
        return typeTypeRelationInfo;
    }

    public static TypeInfo makeTypeInfo(String typeKey, String typeName, String descr, String refObjectUri) {
        TypeInfo type = new TypeInfo();
        type.setKey(typeKey);
        type.setName(typeName);
        type.setDescr(new RichTextHelper().fromPlain(descr));
        type.setRefObjectUri(refObjectUri);
        type.setEffectiveDate(new java.util.Date());
        return type;
    }
}
