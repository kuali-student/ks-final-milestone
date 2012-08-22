package org.kuali.student.r2.core.scheduling.model;

import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.assembler.TransformUtility;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.infc.ScheduleRequest;
import org.kuali.student.r2.core.scheduling.infc.ScheduleRequestComponent;

import javax.persistence.*;
import java.util.*;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@Entity
@Table(name = "KSEN_SCHED_RQST")
public class ScheduleRequestEntity extends MetaEntity implements AttributeOwner<ScheduleRequestAttributeEntity> {

    @Column(name = "REF_OBJECT_ID")
    private String refObjectId;

    @Column(name = "REF_OBJECT_TYPE")
    private String refObjectTypeKey;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scheduleRequest", orphanRemoval=true)
    private List<ScheduleRequestComponentEntity> scheduleRequestComponents;

    // IdEntity fields follow
    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String plain;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String formatted;

    @Column(name = "SCHED_RQST_TYPE")
    private String schedReqType;

    @Column(name = "SCHED_RQST_STATE")
    private String schedReqState;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", orphanRemoval=true)
    private Set<ScheduleRequestAttributeEntity> attributes = new HashSet<ScheduleRequestAttributeEntity>();

    public ScheduleRequestEntity() {
    }

    public ScheduleRequestEntity(ScheduleRequest scheduleRequest) {
        super(scheduleRequest);
        this.setId(scheduleRequest.getId());
        this.setRefObjectId(scheduleRequest.getRefObjectId());
        this.setRefObjectTypeKey(scheduleRequest.getRefObjectTypeKey());
        this.setSchedReqType(scheduleRequest.getTypeKey());

        this.fromDto(scheduleRequest);
    }

    public List<Object> fromDto(ScheduleRequest scheduleRequest) {
        List<Object> orphansToDelete = new ArrayList<Object>();
        this.setSchedReqState(scheduleRequest.getStateKey());
        this.setName(scheduleRequest.getName());
        this.setRefObjectId(scheduleRequest.getRefObjectId());
        this.setRefObjectTypeKey(scheduleRequest.getRefObjectTypeKey());
        if (scheduleRequest.getDescr() != null) {
            this.setFormatted(scheduleRequest.getDescr().getFormatted());
            this.setPlain(scheduleRequest.getDescr().getPlain());
        } else {
            this.setFormatted(null);
            this.setPlain(null);
        }

        //Map the existing cmp relations by their id
        Map<String,ScheduleRequestComponentEntity> existingCmpEntities = new HashMap<String,ScheduleRequestComponentEntity>();
        if(scheduleRequestComponents != null){
            for(ScheduleRequestComponentEntity cmpEntity : scheduleRequestComponents){
                existingCmpEntities.put(cmpEntity.getId(),cmpEntity);
            }
        }

        //Clear out the current list
        scheduleRequestComponents = new ArrayList<ScheduleRequestComponentEntity>();
        if(scheduleRequest.getScheduleRequestComponents() != null) {
            for(ScheduleRequestComponent srComponent :  scheduleRequest.getScheduleRequestComponents()){
                ScheduleRequestComponentEntity srCmpEntity;
                if(existingCmpEntities.containsKey(srComponent.getId())){
                    srCmpEntity = existingCmpEntities.remove(srComponent.getId());
                    orphansToDelete.addAll(srCmpEntity.fromDto(srComponent));
                }else{
                    srCmpEntity = new ScheduleRequestComponentEntity(srComponent);
                    srCmpEntity.setScheduleRequest(this);
                }

                scheduleRequestComponents.add(srCmpEntity);
            }
        }

        //Now we need to delete the leftovers (orphaned components)
        orphansToDelete.addAll(existingCmpEntities.values());

        // Merge attributes into entity and add leftovers to be deleted
        orphansToDelete.addAll(TransformUtility.mergeToEntityAttributes(ScheduleRequestAttributeEntity.class, scheduleRequest, this));

        return orphansToDelete;
    }

    public ScheduleRequestInfo toDto() {
        ScheduleRequestInfo scheduleRequestInfo = new ScheduleRequestInfo();
        scheduleRequestInfo.setRefObjectId(this.getRefObjectId());
        scheduleRequestInfo.setRefObjectTypeKey(this.getRefObjectTypeKey());
        scheduleRequestInfo.setDescr(new RichTextHelper().toRichTextInfo(this.getPlain(), this.getFormatted()));
        scheduleRequestInfo.setName(this.getName());
        scheduleRequestInfo.setId(this.getId()); // id is assumed not null
        scheduleRequestInfo.setTypeKey(this.getSchedReqType()); // type is assumed not null
        scheduleRequestInfo.setStateKey(this.getSchedReqState()); // state is assumed not null
        scheduleRequestInfo.setMeta(super.toDTO());
        // Attributes
        scheduleRequestInfo.setAttributes(TransformUtility.toAttributeInfoList(this));

        List<ScheduleRequestComponentInfo> srComps = new ArrayList<ScheduleRequestComponentInfo>();
        if (this.getScheduleRequestComponents() != null) {
            for (ScheduleRequestComponentEntity sqComp : getScheduleRequestComponents()) {
                srComps.add(sqComp.toDto());
            }
        }
        scheduleRequestInfo.setScheduleRequestComponents(srComps);

        return scheduleRequestInfo;
    }

    public String getRefObjectId() {
        return refObjectId;
    }

    public void setRefObjectId(String refObjectId) {
        this.refObjectId = refObjectId;
    }

    public String getRefObjectTypeKey() {
        return refObjectTypeKey;
    }

    public void setRefObjectTypeKey(String refObjectTypeKey) {
        this.refObjectTypeKey = refObjectTypeKey;
    }

    public List<ScheduleRequestComponentEntity> getScheduleRequestComponents() {
        return scheduleRequestComponents;
    }

    public void setScheduleRequestComponents(List<ScheduleRequestComponentEntity> scheduleRequestComponents) {
        this.scheduleRequestComponents = scheduleRequestComponents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlain() {
        return plain;
    }

    public void setPlain(String plain) {
        this.plain = plain;
    }

    public String getFormatted() {
        return formatted;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }

    public String getSchedReqType() {
        return schedReqType;
    }

    public void setSchedReqType(String schedReqType) {
        this.schedReqType = schedReqType;
    }

    public String getSchedReqState() {
        return schedReqState;
    }

    public void setSchedReqState(String schedReqState) {
        this.schedReqState = schedReqState;
    }

    public Set<ScheduleRequestAttributeEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<ScheduleRequestAttributeEntity> attributes) {
        this.attributes = attributes;
    }
}
