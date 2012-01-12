package org.kuali.student.enrollment.class1.lui.model;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.infc.Lui;
import org.kuali.student.enrollment.lui.infc.LuiIdentifier;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.MeetingScheduleInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.MeetingSchedule;
import org.kuali.student.r2.core.class1.state.model.StateEntity;
import org.kuali.student.r2.lum.lu.dto.LuCodeInfo;
import org.kuali.student.r2.lum.lu.infc.LuCode;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "KSEN_LUI")
public class LuiEntity extends MetaEntity implements AttributeOwner<LuiAttributeEntity> {
    @Column(name = "NAME")
    private String name;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private LuiRichTextEntity descr;   

    @ManyToOne(optional=false)
    @JoinColumn(name = "TYPE_ID")
    private LuiTypeEntity luiType;

    @ManyToOne(optional=false)
    @JoinColumn(name = "STATE_ID")
    private StateEntity luiState;
    
	@Column(name = "CLU_ID")
	private String cluId;
	
	@Column(name="ATP_ID")
	private String atpId;
    
	@Column(name="REF_URL")
	private String referenceURL;
	
	@Column(name = "MAX_SEATS")
	private Integer maxSeats;
	
	@Column(name = "MIN_SEATS")
	private Integer minSeats;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXP_DT")
	private Date expirationDate;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="lui")
	private List<LuCodeEntity> luCodes;
      
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "OFFIC_LUI_ID")
    private LuiIdentifierEntity officialIdentifier;
    
    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "KSEN_LUI_JN_LUI_IDENT", joinColumns = @JoinColumn(name = "LUI_ID"), inverseJoinColumns = @JoinColumn(name = "ALT_LUI_ID"))
    private List<LuiIdentifierEntity> alternateIdentifiers;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="lui")
	private List<MeetingScheduleEntity> meetingSchedules;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="lui",orphanRemoval = true)
    private List<LuiResultValuesGroupRelationEntity> resultValuesGroupRelationEntities;
	
//	@OneToMany(cascade = CascadeType.ALL, mappedBy="lui")
//	private List<LuiCluRelationEntity> cluCluRelationIds;
	
	//TODO: unitsContentOwner
	//TODO:unitsDeployment
	//TODO:resultOptionIds -- <LuiResultOptionEntity> see r1 ResultOption
	//TODO:fees
	//TODO:revenues
	//TODO:expenditure
	
	//	TODO: decide if this this should be stored on the Lui or on a waitlist object?
   /* @Column(name="HAS_WTLST")
    private boolean hasWaitlist;
    
    @Column(name="IS_WTLSTCHK_REQ")
    private boolean isWaitlistCheckinRequired;
    
	@Column(name = "WTLST_MAX")
	private Integer waitlistMaximum; */
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<LuiAttributeEntity> attributes;
    
    public LuiEntity (){}
    
    public LuiEntity(Lui lui){
    	super(lui); 
        try {   
        	this.setId(lui.getId());
        	this.setName(lui.getName());
        	this.setAtpId(lui.getAtpId());
        	this.setCluId(lui.getCluId());
        	this.setMaxSeats(lui.getMaximumEnrollment());        	
        	this.setMinSeats(lui.getMinimumEnrollment());     
        	this.setReferenceURL(lui.getReferenceURL());
		/*
		 * decide if this this should be stored on the Lui or on a waitlist object?.
        	this.setHasWaitlist(lui.getHasWaitlist());
        	this.setWaitlistCheckinRequired(lui.getIsWaitlistCheckinRequired());
        	this.setWaitlistMaximum(lui.getWaitlistMaximum());
		*/
        	if(lui.getEffectiveDate() != null)
        		this.setEffectiveDate(lui.getEffectiveDate());
        	if(lui.getExpirationDate() != null)
        		this.setExpirationDate(lui.getExpirationDate());
        	
	        if(lui.getDescr() != null)
	            this.setDescr(new LuiRichTextEntity(lui.getDescr()));
	        	        
	        if(lui.getOfficialIdentifier() != null)
	        	this.setOfficialIdentifier(new LuiIdentifierEntity(lui.getOfficialIdentifier()));

	        this.setLuCodes(new ArrayList<LuCodeEntity>());
	        if (null != lui.getLuiCodes()){
	        	for(LuCode luCode : lui.getLuiCodes()){
	        		LuCodeEntity lcdEntity = new LuCodeEntity(luCode);
	        		this.getLuCodes().add(lcdEntity);
	        	}
	        }
	        
	        this.setAlternateIdentifiers(new ArrayList<LuiIdentifierEntity>());
	        if (null != lui.getAlternateIdentifiers()){
	        	for(LuiIdentifier luiIdentifier : lui.getAlternateIdentifiers()){
	        		LuiIdentifierEntity liEntity = new LuiIdentifierEntity(luiIdentifier);
	        		this.getAlternateIdentifiers().add(liEntity);
	        	}
	        }
	        
	        this.setMeetingSchedules(new ArrayList<MeetingScheduleEntity>());
	        if (null != lui.getMeetingSchedules()) {
	        	for(MeetingSchedule ms : lui.getMeetingSchedules()){
	        		MeetingScheduleEntity msEntity = new MeetingScheduleEntity(ms);
	        		this.getMeetingSchedules().add(msEntity);
	        	}
	        }

            List<LuiResultValuesGroupRelationEntity> resultValuesGroupRelationList = new ArrayList<LuiResultValuesGroupRelationEntity>();
            if (lui.getResultValuesGroupKeys() != null){
                for (String resValueGroupKey : lui.getResultValuesGroupKeys()) {
                    LuiResultValuesGroupRelationEntity resultValuesGroupRelationEntity = new LuiResultValuesGroupRelationEntity(this,resValueGroupKey);
                    resultValuesGroupRelationEntity.setId(UUIDHelper.genStringUUID());
                    resultValuesGroupRelationList.add(resultValuesGroupRelationEntity);
                }
            }
            this.setResultValuesGroupRelationEntities(resultValuesGroupRelationList);

	        this.setAttributes(new ArrayList<LuiAttributeEntity>());
	        if (null != lui.getAttributes()) {
	            for (Attribute att : lui.getAttributes()) {
	            	LuiAttributeEntity attEntity = new LuiAttributeEntity(att);
	                this.getAttributes().add(attEntity);
	            }
	        }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public LuiInfo toDto(){
    	LuiInfo obj = new LuiInfo();
    	obj.setId(getId());
    	obj.setName(name);
    	obj.setAtpId(atpId);
    	obj.setCluId(cluId);
        if (null != officialIdentifier) {
            obj.setOfficialIdentifier(officialIdentifier.toDto());
        }
        
        List<LuCodeInfo> codes = new ArrayList<LuCodeInfo>();
        for (LuCodeEntity code : luCodes) {
        	LuCodeInfo codeInfo = code.toDto();
        	codes.add(codeInfo);
        }
        obj.setLuiCodes(codes);
        
	/*
	 * decide if this this should be stored on the Lui or on a waitlist object?
    	obj.setHasWaitlist(hasWaitlist);
    	obj.setIsWaitlistCheckinRequired(isWaitlistCheckinRequired);
    	if(waitlistMaximum != null)
    		obj.setWaitlistMaximum(waitlistMaximum);
	*/

    	if(maxSeats != null)
    		obj.setMaximumEnrollment(maxSeats);
    	if(minSeats != null)
    		obj.setMinimumEnrollment(minSeats);
        obj.setEffectiveDate(effectiveDate);
        obj.setExpirationDate(expirationDate);
        if(luiType != null)
            obj.setTypeKey(luiType.getId());
        if(luiState != null)
            obj.setStateKey(luiState.getId());
        obj.setMeta(super.toDTO());
        if(descr != null)
            obj.setDescr(descr.toDto());
 
        List<MeetingScheduleInfo> schedules = new ArrayList<MeetingScheduleInfo>();
        for (MeetingScheduleEntity ms : meetingSchedules) {
        	MeetingScheduleInfo msInfo = ms.toDto();
        	schedules.add(msInfo);
        }
        obj.setMeetingSchedules(schedules);

        List<String> rvGroupIds = new ArrayList();
        if (null != getResultValuesGroupRelationEntities()) {
            for (LuiResultValuesGroupRelationEntity relationEntity : getResultValuesGroupRelationEntities()){
                rvGroupIds.add(relationEntity.getResultValuesGroupKey());
            }
        }
        obj.setResultValuesGroupKeys(rvGroupIds);

        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (LuiAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        obj.setAttributes(atts);
        
        return obj;
    }

    public Integer getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(Integer maxSeats) {
        this.maxSeats = maxSeats;
    }

    public Integer getMinSeats() {
		return minSeats;
	}

	public void setMinSeats(Integer minSeats) {
		this.minSeats = minSeats;
	}

	public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

	public String getCluId() {
		return cluId;
	}

	public void setCluId(String cluId) {
		this.cluId = cluId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAtpId() {
		return atpId;
	}

	public void setAtpId(String atpId) {
		this.atpId = atpId;
	}

	public LuiRichTextEntity getDescr() {
		return descr;
	}

	public void setDescr(LuiRichTextEntity descr) {
		this.descr = descr;
	}

	public LuiTypeEntity getLuiType() {
		return luiType;
	}

	public void setLuiType(LuiTypeEntity luiType) {
		this.luiType = luiType;
	}

	public StateEntity getLuiState() {
		return luiState;
	}

	public void setLuiState(StateEntity luiState) {
		this.luiState = luiState;
	}

	public String getReferenceURL() {
		return referenceURL;
	}

	public void setReferenceURL(String referenceURL) {
		this.referenceURL = referenceURL;
	}
//	public boolean isHasWaitlist() {
//		return hasWaitlist;
//	}
//
//	public void setHasWaitlist(boolean hasWaitlist) {
//		this.hasWaitlist = hasWaitlist;
//	}
//
//	public boolean isWaitlistCheckinRequired() {
//		return isWaitlistCheckinRequired;
//	}
//
//	public void setWaitlistCheckinRequired(boolean isWaitlistCheckinRequired) {
//		this.isWaitlistCheckinRequired = isWaitlistCheckinRequired;
//	}
//
//	public Integer getWaitlistMaximum() {
//		return waitlistMaximum;
//	}
//
//	public void setWaitlistMaximum(Integer waitlistMaximum) {
//		this.waitlistMaximum = waitlistMaximum;
//	}

	@Override
	public void setAttributes(List<LuiAttributeEntity> attributes) {
		this.attributes = attributes;			
	}

	public List<LuCodeEntity> getLuCodes() {
		return luCodes;
	}

	public void setLuCodes(List<LuCodeEntity> luCodes) {
		this.luCodes = luCodes;
	}

	public LuiIdentifierEntity getOfficialIdentifier() {
		return officialIdentifier;
	}

	public void setOfficialIdentifier(LuiIdentifierEntity officialIdentifier) {
		this.officialIdentifier = officialIdentifier;
	}

	public List<LuiIdentifierEntity> getAlternateIdentifiers() {
		return alternateIdentifiers;
	}

	public void setAlternateIdentifiers(
			List<LuiIdentifierEntity> alternateIdentifiers) {
		this.alternateIdentifiers = alternateIdentifiers;
	}

	@Override
	public List<LuiAttributeEntity> getAttributes() {
		return attributes;
	}

	public List<MeetingScheduleEntity> getMeetingSchedules() {
		return meetingSchedules;
	}

	public void setMeetingSchedules(List<MeetingScheduleEntity> meetingSchedules) {
		this.meetingSchedules = meetingSchedules;
	}

    public List<LuiResultValuesGroupRelationEntity> getResultValuesGroupRelationEntities() {
        return resultValuesGroupRelationEntities;
    }

    public void setResultValuesGroupRelationEntities(List<LuiResultValuesGroupRelationEntity> resultValuesGroupRelationEntities) {
        this.resultValuesGroupRelationEntities = resultValuesGroupRelationEntities;
    }

/*	public List<LuiCluRelationEntity> getCluCluRelationIds() {
		return cluCluRelationIds;
	}

	public void setCluCluRelationIds(List<LuiCluRelationEntity> cluCluRelationIds) {
		this.cluCluRelationIds = cluCluRelationIds;
	}*/
    
}
