package org.kuali.student.enrollment.class2.courseoffering.service.transformer;

import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;


public class RegistrationGroupTransformer {

    public RegistrationGroupInfo transform(LuiInfo lui) {

        RegistrationGroupInfo regDateGroup = new RegistrationGroupInfo();
        regDateGroup.setId(lui.getId());
        regDateGroup.setMeta(lui.getMeta());
        regDateGroup.setStateKey(lui.getStateKey());
        regDateGroup.setTypeKey(lui.getTypeKey());
        regDateGroup.setDescr(lui.getDescr());
        regDateGroup.setAttributes(lui.getAttributes());
        regDateGroup.setMaximumEnrollment(lui.getMaximumEnrollment());
        //regDateGroup.setMinimumEnrollment(lui.getMinimumEnrollment());
        regDateGroup.setName(lui.getName());
        regDateGroup.setFormatOfferingId(lui.getCluId());
        regDateGroup.setTermId(lui.getAtpId());
        // TODO: co.setIsHonorsOffering(isHonorsOffering) -- lui.getLuiCodes() ?

        // below undecided
        // co.setHasWaitlist(lui.getHasWaitlist());
        // co.setWaitlistTypeKey(lui.getWaitlistTypeKey());
        // co.setWaitlistMaximum(lui.getWaitlistMaximum());
        // co.setIsWaitlistCheckinRequired(lui.getIsWaitlistCheckinRequired());
        // co.setWaitlistCheckinFrequency(lui.getWaitlistCheckinFrequency());

        // LuiLuiRelation (to set courseOfferingId, activityOfferingIds)
        return regDateGroup;
    }
    
    
    public LuiInfo transform(RegistrationGroupInfo regGroup) {
        
        LuiInfo lui = new LuiInfo();
        lui.setId(regGroup.getId());
        lui.setTypeKey(regGroup.getTypeKey());
        lui.setStateKey(regGroup.getStateKey());
        lui.setDescr(regGroup.getDescr());
        lui.setMeta(regGroup.getMeta());
        lui.setAttributes(regGroup.getAttributes());
        lui.setName(regGroup.getName());
        lui.setCluId(regGroup.getFormatOfferingId());
        lui.setMaximumEnrollment(regGroup.getMaximumEnrollment());
        // lui.setMinimumEnrollment(regGroup.getMinimumEnrollment());
        lui.setAtpId(regGroup.getTermId());
        
        //TODO: co.getIsHonorsOffering() --store in a generic lui luCodes type of field?
        
        //below undecided
        //lui.setHasWaitlist(rg.getHasWaitlist());
        //lui.setIsWaitlistCheckinRequired(rg.getIsWaitlistCheckinRequired());
        //lui.setWaitlistCheckinFrequency(rg.getWaitlistCheckinFrequency());
        //lui.setWaitlistMaximum(rg.getWaitlistMaximum());
        //lui.setWaitlistTypeKey(rg.getWaitlistTypeKey());
        return lui;
    
    }
}
