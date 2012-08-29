package org.kuali.student.lum.conversion;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.common.conversion.util.R1R2ConverterUtil;
import org.kuali.student.r2.core.search.dto.SearchParamHelper;
import org.kuali.student.r2.lum.clu.dto.AccreditationInfo;
import org.kuali.student.r2.lum.clu.dto.AdminOrgInfo;
import org.kuali.student.r2.lum.clu.dto.AffiliatedOrgInfo;
import org.kuali.student.r2.lum.clu.dto.CluAccountingInfo;
import org.kuali.student.r2.lum.clu.dto.CluCluRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluFeeInfo;
import org.kuali.student.r2.lum.clu.dto.CluFeeRecordInfo;
import org.kuali.student.r2.lum.clu.dto.CluIdentifierInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluLoRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluPublicationInfo;
import org.kuali.student.r2.lum.clu.dto.CluResultInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetTreeViewInfo;
import org.kuali.student.r2.lum.clu.dto.FieldInfo;
import org.kuali.student.r2.lum.clu.dto.LuCodeInfo;
import org.kuali.student.r2.lum.clu.dto.LuDocRelationInfo;
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;
import org.kuali.student.r2.lum.clu.dto.ResultOptionInfo;

public class LumConverterTest {

    @Test
    public void testAccreditationInfo() {
        org.kuali.student.r1.lum.lu.dto.AccreditationInfo r1 = R1TestDataUtil.getAccreditationInfoData();
        AccreditationInfo r2 = R1R2ConverterUtil.convert(r1, AccreditationInfo.class);
        Assert.assertEquals(r1.getId(), r2.getId());
        Assert.assertEquals(r1.getOrgId(), r2.getOrgId());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getEffectiveDate(), r2.getEffectiveDate());
        Assert.assertEquals(r1.getExpirationDate(), r2.getExpirationDate());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
    }
    @Test
    public void testAdminOrgInfo(){
        org.kuali.student.r1.lum.lu.dto.AdminOrgInfo r1 = R1TestDataUtil.getAdminOrgInfoData();
        AdminOrgInfo r2 = R1R2ConverterUtil.convert(r1, AdminOrgInfo.class);
        Assert.assertEquals(r1.getId(), r2.getId());
        Assert.assertEquals(r1.getOrgId(), r2.getOrgId());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.isPrimary(), r2.getIsPrimary());   
    }
    
    @Test
    public void testAffiliatedOrgInfo(){
    	org.kuali.student.r1.lum.lu.dto.AffiliatedOrgInfo r1 = R1TestDataUtil.getAffiliatedOrgInfoData();
    	AffiliatedOrgInfo r2 = R1R2ConverterUtil.convert(r1, AffiliatedOrgInfo.class);
    	//No matching R1 field: r2.getAttributes()
    	//No matching R1 field: r2.getMeta()
    	Assert.assertEquals(r1.getId(), r2.getId());
    	Assert.assertEquals(r1.getOrgId(), r2.getOrgId());
    	Assert.assertEquals(r1.getEffectiveDate(), r2.getEffectiveDate());
    	Assert.assertEquals(r1.getExpirationDate(), r2.getExpirationDate());
    	Assert.assertEquals(r1.getPercentage(), r2.getPercentage());
    }
    
    @Test
    public void testCluAccountingInfo(){
    	org.kuali.student.r1.lum.lu.dto.CluAccountingInfo r1 = R1TestDataUtil.getCluAccountingInfoData();
    	CluAccountingInfo r2 = R1R2ConverterUtil.convert(r1, CluAccountingInfo.class);
    	Assert.assertEquals(r1.getId(), r2.getId());
    	Assert.assertEquals(r1.getAffiliatedOrgs().get(0).getId(), r2.getAffiliatedOrgs().get(0).getId());
    	Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
	
    }
    
    @Test
    public void testCluCluRelationInfo(){
    	org.kuali.student.r1.lum.lu.dto.CluCluRelationInfo r1 = R1TestDataUtil.getCluCluRelationInfoData();
    	CluCluRelationInfo r2 = R1R2ConverterUtil.convert(r1, CluCluRelationInfo.class);
    	Assert.assertEquals(r1.getCluId(), r2.getCluId());
    	Assert.assertEquals(r1.getId(), r2.getId());
    	Assert.assertEquals(r1.getRelatedCluId(), r2.getRelatedCluId());
    	Assert.assertEquals(r1.getState(), r2.getStateKey());
    	Assert.assertEquals(r1.getType(), r2.getTypeKey());
        Assert.assertEquals(r1.getExpirationDate(), r2.getExpirationDate());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getIsCluRelationRequired(), r2.getIsCluRelationRequired());
    	
    }
    
    @Test
    public void testCluFeeRecordInfo(){
    	org.kuali.student.r1.lum.lu.dto.CluFeeRecordInfo r1 = R1TestDataUtil.getCluFeeRecordInfoData();
    	CluFeeRecordInfo r2 = R1R2ConverterUtil.convert(r1, CluFeeRecordInfo.class);
    	Assert.assertEquals(r1.getId(), r2.getId());
    	Assert.assertEquals(r1.getDescr().getPlain(), r2.getDescr().getPlain());
    	Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
    	Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
    	Assert.assertEquals(r1.getFeeType(), r2.getFeeType());
    	Assert.assertEquals(r1.getRateType(), r2.getRateType());
    	Assert.assertEquals(r1.getAffiliatedOrgs().get(0).getId(), r2.getAffiliatedOrgs().get(0).getId());
    	Assert.assertEquals(r1.getFeeAmounts().get(0).getId(), r2.getFeeAmounts().get(0).getId());
    }
    
    @Test
    public void testCluInfo() {
        org.kuali.student.r1.lum.lu.dto.CluInfo r1 = new org.kuali.student.r1.lum.lu.dto.CluInfo();
        r1.setAccountingInfo(R1TestDataUtil.getCluAccountingInfoData());
        r1.setAccreditations(R1TestDataUtil.getAccreditationInfoDataList());
        r1.setAdminOrgs(R1TestDataUtil.getAdminOrgInfoDataList());
        r1.setAlternateIdentifiers(R1TestDataUtil.getCluIdentifierInfoDataList());
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setCampusLocations(null);
        r1.setCanCreateLui(true);
        r1.setDefaultEnrollmentEstimate(1);
        r1.setDefaultMaximumEnrollment(1);
        r1.setDescr(R1TestDataUtil.getRichTextInfoData());
        r1.setEffectiveDate(new Date());
        r1.setEnrollable(true);
        r1.setExpectedFirstAtp("R1 Expected First Atp");
        r1.setExpirationDate(new Date());
        r1.setFeeInfo(R1TestDataUtil.getCluFeeInfoData());
        r1.setHasEarlyDropDeadline(true);
        r1.setHazardousForDisabledStudents(true);
        r1.setId("R1 Id");
        r1.setInstructors(R1TestDataUtil.getCluInstructorInfoDataList());
        r1.setIntensity(R1TestDataUtil.getAmountInfoData());
        r1.setLastAdmitAtp("R1 Last Admit Atp");
        r1.setLuCodes(R1TestDataUtil.getLuCodeInfoDataList());
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1.setNextReviewPeriod("R1 Next Review Period");
        r1.setOfferedAtpTypes(null);
        r1.setOfficialIdentifier(R1TestDataUtil.getCluIdentifierInfoData());
        r1.setPrimaryInstructor(R1TestDataUtil.getCluInstructorInfoData());
        r1.setReferenceURL("R1 Reference URL");
        r1.setState("R1 State");
        r1.setStdDuration(R1TestDataUtil.getTimeAmountInfoData());
        r1.setStudySubjectArea("R1 Study Subject Area");
        r1.setType("R1 Type");
        r1.setVersionInfo(R1TestDataUtil.getVersionInfoData());
        CluInfo r2 = R1R2ConverterUtil.convert(r1, CluInfo.class);
        Assert.assertEquals(r1.getAccountingInfo().getId(), r2.getAccountingInfo().getId());
        Assert.assertEquals("R1-Value", r2.getAccountingInfo().getAttributes().get(0).getValue());
        //No matching R1 property: r2.getAccountingInfo().getAffiliatedOrgs().get(0).getAttributes().get(0).getValue()
        //No matching R1 property: r2.getAccountingInfo().getDescr()
        Assert.assertEquals(r1.getAccreditations().get(0).getId(), r2.getAccreditations().get(0).getId());
        Assert.assertEquals("R1-Value", r2.getAccreditations().get(0).getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getAdminOrgs().get(0).getId(), r2.getAdminOrgs().get(0).getId());
        Assert.assertEquals("R1-Value", r2.getAdminOrgs().get(0).getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getAlternateIdentifiers().get(0).getId(), r2.getAlternateIdentifiers().get(0).getId());
        Assert.assertEquals("R1-Value", r2.getAlternateIdentifiers().get(0).getAttributes().get(0).getValue());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.isCanCreateLui(), r2.getCanCreateLui());
        Assert.assertEquals(r1.isEnrollable(), r2.getIsEnrollable());
        Assert.assertEquals(r1.isHasEarlyDropDeadline(), r2.getIsHasEarlyDropDeadline());
        Assert.assertEquals(r1.isHazardousForDisabledStudents(), r2.getIsHazardousForDisabledStudents());
        Assert.assertEquals(r1.getDescr().getPlain(), r2.getDescr().getPlain());
        Assert.assertEquals(r1.getFeeInfo().getId(), r2.getFeeInfo().getId());
        Assert.assertEquals("R1-Value", r2.getFeeInfo().getAttributes().get(0).getValue());
        Assert.assertEquals("R1-Value", r2.getFeeInfo().getCluFeeRecords().get(0).getAttributes().get(0).getValue());
        //No matching R1 property: r2.getFeeInfo().getCluFeeRecords().get(0).getAffiliatedOrgs().get(0).getAttributes().get(0).getValue()
        Assert.assertEquals(r1.getFeeInfo().getCluFeeRecords().get(0).getFeeAmounts().get(0).getCurrencyTypeKey(), r2.getFeeInfo().getCluFeeRecords().get(0).getFeeAmounts().get(0).getCurrencyTypeKey());
        Assert.assertEquals(r1.getInstructors().get(0).getPersonId(), r2.getInstructors().get(0).getPersonId());
        Assert.assertEquals("R1-Value", r2.getInstructors().get(0).getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getIntensity().getUnitTypeKey(), r2.getIntensity().getUnitTypeKey());
        Assert.assertEquals(r1.getIntensity().getUnitQuantity(), r2.getIntensity().getUnitQuantity());
        Assert.assertEquals(r1.getLuCodes().get(0).getId(), r2.getLuCodes().get(0).getId());
        Assert.assertEquals("R1-Value", r2.getLuCodes().get(0).getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getOfficialIdentifier().getId(), r2.getOfficialIdentifier().getId());
        Assert.assertEquals("R1-Value", r2.getOfficialIdentifier().getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getPrimaryInstructor().getPersonId(), r2.getPrimaryInstructor().getPersonId());
        Assert.assertEquals("R1-Value", r2.getPrimaryInstructor().getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getStdDuration().getTimeQuantity(), r2.getStdDuration().getTimeQuantity());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
        Assert.assertEquals(r1.getVersionInfo().getVersionIndId(), r2.getVersionInfo().getVersionIndId());
    }
    
    @Test
    public void testCluFeeInfo() {
        org.kuali.student.r1.lum.lu.dto.CluFeeInfo r1 = R1TestDataUtil.getCluFeeInfoData();
        CluFeeInfo r2 = R1R2ConverterUtil.convert(r1, CluFeeInfo.class);
        Assert.assertEquals(r1.getId(), r2.getId());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getCluFeeRecords().get(0).getId(), r2.getCluFeeRecords().get(0).getId());
        Assert.assertEquals(r1.getDescr().getPlain(), r2.getDescr().getPlain());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
    }
    
    @Test
    public void testCluIdentifierInfo() {
        org.kuali.student.r1.lum.lu.dto.CluIdentifierInfo r1 = R1TestDataUtil.getCluIdentifierInfoData();
        CluIdentifierInfo r2 = R1R2ConverterUtil.convert(r1, CluIdentifierInfo.class);
        Assert.assertEquals(r1.getId(), r2.getId());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
    }

    @Test 
    public void testCluLoRelationInfo(){
    	 org.kuali.student.r1.lum.lu.dto.CluLoRelationInfo r1 = R1TestDataUtil.getCluLoRelationInfoData();
    	 CluLoRelationInfo r2 = R1R2ConverterUtil.convert(r1, CluLoRelationInfo.class);
    	 Assert.assertEquals(r1.getId(), r2.getId());
         Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
         Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
         Assert.assertEquals(r1.getType(), r2.getTypeKey());
         Assert.assertEquals(r1.getState(), r2.getStateKey());
         Assert.assertEquals(r1.getCluId(), r2.getCluId());
         Assert.assertEquals(r1.getExpirationDate(), r2.getExpirationDate());
         Assert.assertEquals(r1.getEffectiveDate(), r2.getEffectiveDate());
         Assert.assertEquals(r1.getLoId(), r2.getLoId());
    }
    
    @Test
    public void testCluPublicationInfo(){
    	org.kuali.student.r1.lum.lu.dto.CluPublicationInfo r1 = R1TestDataUtil.getCluPublicationInfoData();
    	CluPublicationInfo r2 = R1R2ConverterUtil.convert(r1, CluPublicationInfo.class);
    	 Assert.assertEquals(r1.getId(), r2.getId());
         Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
         Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
         Assert.assertEquals(r1.getType(), r2.getTypeKey());
         Assert.assertEquals(r1.getState(), r2.getStateKey());
         Assert.assertEquals(r1.getCluId(), r2.getCluId());
         Assert.assertEquals(r1.getExpirationDate(), r2.getExpirationDate());
         Assert.assertEquals(r1.getEffectiveDate(), r2.getEffectiveDate());
         Assert.assertEquals("R1 Field Value", r2.getVariants().get(0).getValue());
         Assert.assertEquals(r1.getStartCycle(), r2.getStartCycle());
         Assert.assertEquals(r1.getEndCycle(), r2.getEndCycle());
    }
    
    @Test
    public void testCluResultInfo(){
    	org.kuali.student.r1.lum.lu.dto.CluResultInfo r1 = R1TestDataUtil.getCluResultInfoData();
    	CluResultInfo r2 = R1R2ConverterUtil.convert(r1, CluResultInfo.class);
    	Assert.assertEquals(r1.getId(), r2.getId());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getCluId(), r2.getCluId());
        Assert.assertEquals(r1.getDesc().getPlain(), r2.getDescr().getPlain());
        Assert.assertEquals(r1.getExpirationDate(), r2.getExpirationDate());
        Assert.assertEquals(r1.getEffectiveDate(), r2.getEffectiveDate());
        Assert.assertEquals(r1.getResultOptions(), r2.getResultOptions());
    }
    
    @Test
    public void testCluSetInfo(){
    	org.kuali.student.r1.lum.lu.dto.CluSetInfo r1 = R1TestDataUtil.getCluSetInfoData();
    	CluSetInfo r2 = R1R2ConverterUtil.convert(r1, CluSetInfo.class);
    	r2.getMembershipQuery().setQueryParamValues(SearchParamHelper.toSearchParamInfos(r1.getMembershipQuery().getQueryParamValueList()));
    	Assert.assertEquals(r1.getId(), r2.getId());
    	Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getDescr().getPlain(), r2.getDescr().getPlain());
        Assert.assertEquals(r1.getCluIds(), r2.getCluIds());
        Assert.assertEquals(r1.getCluSetIds(), r2.getCluSetIds());
        Assert.assertEquals(r1.getExpirationDate(), r2.getExpirationDate());
        Assert.assertEquals(r1.getEffectiveDate(), r2.getEffectiveDate());
        Assert.assertEquals(r1.getIsReferenceable(), r2.getIsReferenceable());
        Assert.assertEquals(r1.getIsReusable(), r2.getIsReusable());
        Assert.assertEquals(r1.getName(), r2.getName());
        Assert.assertEquals(r1.getMembershipQuery().getQueryParamValueList().get(0).getKey(), r2.getMembershipQuery().getQueryParamValues().get(0).getKey());
    }
    
    @Test
    public void testCluSetTreeViewInfo(){
    	org.kuali.student.r1.lum.lu.dto.CluSetTreeViewInfo r1 = R1TestDataUtil.getCluSetTreeViewInfoData();
    	CluSetTreeViewInfo r2 = R1R2ConverterUtil.convert(r1, CluSetTreeViewInfo.class);
    	Assert.assertEquals(r1.getId(), r2.getId());
    	Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getDescr().getPlain(), r2.getDescr().getPlain());
        Assert.assertEquals(r1.getAdminOrg(), r2.getAdminOrg());
        Assert.assertEquals(r1.getName(), r2.getName());
        Assert.assertEquals(r1.getExpirationDate(), r2.getExpirationDate());
        Assert.assertEquals(r1.getEffectiveDate(), r2.getEffectiveDate());
        Assert.assertEquals(r1.getIsReferenceable(), r2.getIsReferenceable());
        Assert.assertEquals(r1.getIsReusable(), r2.getIsReusable());
        Assert.assertEquals(r1.getCluSets(), r2.getCluSets());
        Assert.assertEquals(r1.getClus().get(0).getId(), r2.getClus().get(0).getId());

    }
    
    @Test
    public void testFieldInfo(){
    	org.kuali.student.r1.lum.lu.dto.FieldInfo r1 = R1TestDataUtil.getFieldInfoData();
    	FieldInfo r2 = R1R2ConverterUtil.convert(r1, FieldInfo.class);
    	 Assert.assertEquals(r1.getId(), r2.getId());
    	 Assert.assertEquals(r1.getValue(), r2.getValue());
    	
    }
    
    @Test
    public void testLuCodeInfo(){
    	org.kuali.student.r1.lum.lu.dto.LuCodeInfo r1 = R1TestDataUtil.getLuCodeInfoData();
    	LuCodeInfo r2 = R1R2ConverterUtil.convert(r1, LuCodeInfo.class);
    	 Assert.assertEquals(r1.getId(), r2.getId());
    	 Assert.assertEquals(r1.getValue(), r2.getValue());
    	 Assert.assertEquals(r1.getType(), r2.getTypeKey());
    	 Assert.assertEquals(r1.getDescr().getPlain(), r2.getDescr().getPlain());
    	 Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
    	 Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
    	
    }
    
    @Test
    public void testLuDocRelationInfo(){
    	org.kuali.student.r1.lum.lu.dto.LuDocRelationInfo r1 = R1TestDataUtil.getLuDocRelationInfoData();
    	LuDocRelationInfo r2 = R1R2ConverterUtil.convert(r1, LuDocRelationInfo.class);
    	 Assert.assertEquals(r1.getId(), r2.getId());
    	 Assert.assertEquals(r1.getDocumentId(), r2.getDocumentId());
    	 Assert.assertEquals(r1.getState(), r2.getStateKey());
    	 Assert.assertEquals(r1.getTitle(), r2.getTitle());
    	 Assert.assertEquals(r1.getCluId(), r2.getCluId());
    	 Assert.assertEquals(r1.getType(), r2.getTypeKey());
    	 Assert.assertEquals(r1.getExpirationDate(), r2.getExpirationDate());
         Assert.assertEquals(r1.getEffectiveDate(), r2.getEffectiveDate());
    	 Assert.assertEquals(r1.getDesc().getPlain(), r2.getDescr().getPlain());
    	 Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
    	 Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
   	
    }
    
    @Test
    public void testMembershipQueryInfo(){
        org.kuali.student.r1.lum.lu.dto.MembershipQueryInfo r1 = R1TestDataUtil.getMembershipQueryInfoData();
        MembershipQueryInfo r2 = R1R2ConverterUtil.convert(r1, MembershipQueryInfo.class);
        r2.setQueryParamValues(SearchParamHelper.toSearchParamInfos(r1.getQueryParamValueList()));
        Assert.assertEquals(r1.getId(), r2.getId());
        Assert.assertEquals(r1.getSearchTypeKey(), r2.getSearchTypeKey());
        Assert.assertEquals(r1.getQueryParamValueList().get(0).getKey(), r2.getQueryParamValues().get(0).getKey());
        Object searchParamValue = r1.getQueryParamValueList().get(0).getValue();
        Assert.assertEquals(searchParamValue instanceof List ? ((List<String>) searchParamValue).get(0)
                : (String) searchParamValue, r2.getQueryParamValues().get(0).getValues().get(0));
    	 
    }
    
    @Test
    public void testResultOptionInfo(){
    	 org.kuali.student.r1.lum.lu.dto.ResultOptionInfo r1 = R1TestDataUtil.getResultOptionInfoData();
    	 ResultOptionInfo r2 = R1R2ConverterUtil.convert(r1, ResultOptionInfo.class);
    	 Assert.assertEquals(r1.getId(), r2.getId());
    	 Assert.assertEquals(r1.getResultComponentId(), r2.getResultComponentId());
    	 Assert.assertEquals(r1.getResultUsageTypeKey(), r2.getResultUsageTypeKey());
    	 Assert.assertEquals(r1.getState(), r2.getStateKey());
    	 Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
    	 Assert.assertEquals(r1.getDesc().getPlain(), r2.getDescr().getPlain());
    	 Assert.assertEquals(r1.getExpirationDate(), r2.getExpirationDate());
         Assert.assertEquals(r1.getEffectiveDate(), r2.getEffectiveDate());
    }
    
}
