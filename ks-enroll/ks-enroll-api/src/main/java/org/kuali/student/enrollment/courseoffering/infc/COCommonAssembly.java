package org.kuali.student.enrollment.courseoffering.infc;


import org.kuali.student.r2.common.dto.TimeAmountInfo;

public interface COCommonAssembly {
	public Integer getMaximumEnrollment();
	public void setMaximumEnrollment(Integer maximumEnrollment);
	
	public Integer getMinimumEnrollment();    
	public void setMinimumEnrollment(Integer minimumEnrollment);
	
	public String getWaitlistTypeKey();
	public void setWaitlistTypeKey(String waitlistTypeKey);
	
    public Integer getWaitlistMaximum();
    public void setWaitlistMaximum(Integer waitlistMaximum);
    
    public Boolean getHasWaitlist();
    public void setHasWaitlist(Boolean hasWaitlist);
    
    public Boolean getIsWaitlistCheckinRequired();
    public void setIsWaitlistCheckinRequired(Boolean isWaitlistCheckinRequired);
    
    public TimeAmountInfo getWaitlistCheckinFrequency();
    public void setWaitlistCheckinFrequency(TimeAmountInfo waitlistCheckinFrequency);
    
    public Boolean getIsHonorsOffering();
    public void setIsHonorsOffering(Boolean isHonorsOffering);
    
}
