package org.kuali.student.conversion.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.dto.TagInfo;
import org.kuali.student.r2.core.document.dto.DocumentCategoryInfo;
import org.kuali.student.r2.core.document.dto.DocumentInfo;
import org.kuali.student.r2.core.document.dto.RefDocRelationInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumerationInfo;
import org.kuali.student.r2.core.organization.dto.OrgCodeInfo;
import org.kuali.student.r2.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.dto.OrgOrgRelationInfo;

public class CoreConverterTest {
    
    @Test
    public void testAtpInfo() {
        org.kuali.student.r1.core.atp.dto.AtpInfo r1 = new org.kuali.student.r1.core.atp.dto.AtpInfo();
        r1.setId("R1 Atp Id");
        r1.setDesc(R1TestDataUtil.getRichTextInfoData());
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1.setState("R1 State");
        r1.setType("R1 Type");
        AtpInfo r2 = R1R2ConverterUtil.convert(r1, AtpInfo.class);
        Assert.assertEquals(r1.getId(), r2.getId());
        Assert.assertEquals(r1.getDesc().getFormatted(), r2.getDescr().getFormatted());
        Assert.assertEquals(r1.getAttributes().get("R1-Key"), r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
    }
    
    @Test
    public void testMilestoneInfo() {
        org.kuali.student.r1.core.atp.dto.MilestoneInfo r1 = new org.kuali.student.r1.core.atp.dto.MilestoneInfo();
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setDesc(R1TestDataUtil.getRichTextInfoData());
        r1.setId("R1 Id");
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1.setState("R1 State");
        r1.setType("R1 Type");
        MilestoneInfo r2 = R1R2ConverterUtil.convert(r1, MilestoneInfo.class);
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getDesc().getFormatted(), r2.getDescr().getFormatted());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
        //No matching r2 attribute:
        //r1.setAtpId("R1 Atp Id");
    }
    
    @Test
    public void testCommentInfo() {
        org.kuali.student.r1.core.comment.dto.CommentInfo r1 = new org.kuali.student.r1.core.comment.dto.CommentInfo();
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setCommentText(R1TestDataUtil.getRichTextInfoData());
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        CommentInfo r2 = R1R2ConverterUtil.convert(r1, CommentInfo.class);
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getCommentText().getPlain(), r2.getCommentText().getPlain());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
    }
    
    @Test
    public void testTagInfo() {
        org.kuali.student.r1.core.comment.dto.TagInfo r1 = new org.kuali.student.r1.core.comment.dto.TagInfo();
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1.setState("R1 State");
        r1.setType("R1 Type");
        TagInfo r2 = R1R2ConverterUtil.convert(r1, TagInfo.class);
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals("R1 Meta Info Version Id", r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
    }
    
    @Test
    public void testDocumentInfo() {
        org.kuali.student.r1.core.document.dto.DocumentInfo r1 = new org.kuali.student.r1.core.document.dto.DocumentInfo();
        org.kuali.student.r1.core.document.dto.DocumentBinaryInfo r1BinaryInfo = new org.kuali.student.r1.core.document.dto.DocumentBinaryInfo();
        r1BinaryInfo.setBinary("R1 Binary Info");
        r1.setDocumentBinaryInfo(r1BinaryInfo);
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setDesc(R1TestDataUtil.getRichTextInfoData());
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        DocumentInfo r2 = R1R2ConverterUtil.convert(r1, DocumentInfo.class);
        Assert.assertEquals(r1.getDocumentBinaryInfo().getBinary(), r2.getDocumentBinary().getBinary());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals("R1 Plain", r2.getDescr().getPlain());
        Assert.assertEquals("R1 Meta Info Version Id", r2.getMeta().getVersionInd());
    }
    
    @Test
    public void testRefDocRelationInfo() {
        org.kuali.student.r1.core.document.dto.RefDocRelationInfo r1 = new org.kuali.student.r1.core.document.dto.RefDocRelationInfo();
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setDesc(R1TestDataUtil.getRichTextInfoData());
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        RefDocRelationInfo r2 = R1R2ConverterUtil.convert(r1, RefDocRelationInfo.class);
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals("R1 Plain", r2.getDescr().getPlain());
        Assert.assertEquals("R1 Meta Info Version Id", r2.getMeta().getVersionInd());
    }
    
    @Test
    public void testEnumerationInfo() {
        org.kuali.student.r1.core.enumerationmanagement.dto.EnumerationInfo r1 = new org.kuali.student.r1.core.enumerationmanagement.dto.EnumerationInfo();
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        List<String> r1ContextDescriptors = new ArrayList<String>();
        r1ContextDescriptors.add("R1 Context Descriptor");
        r1.setContextDescriptors(r1ContextDescriptors);
        r1.setDescr("R1 Descr");
        EnumerationInfo r2 = R1R2ConverterUtil.convert(r1, EnumerationInfo.class);
        Assert.assertEquals(r1.getAttributes().get("R1-Key"), r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getContextDescriptors().get(0), r2.getContextDescriptors().get(0));
        Assert.assertEquals(r1.getDescr(), r2.getDescr().getPlain());
    }
    
    @Test
    public void testOrgCodeInfo() {
        org.kuali.student.r1.core.organization.dto.OrgCodeInfo r1 = new org.kuali.student.r1.core.organization.dto.OrgCodeInfo();
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setDesc("R1 Desc");
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        OrgCodeInfo r2 = R1R2ConverterUtil.convert(r1, OrgCodeInfo.class);
        Assert.assertEquals(r1.getDesc(), r2.getDescr().getPlain());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals("R1 Meta Info Version Id", r2.getMeta().getVersionInd());
    }
    
    @Test
    public void testOrgHierarchyInfo() {
        org.kuali.student.r1.core.organization.dto.OrgHierarchyInfo r1 = new org.kuali.student.r1.core.organization.dto.OrgHierarchyInfo();
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setDescr("R1 Descr");
        OrgHierarchyInfo r2 = R1R2ConverterUtil.convert(r1, OrgHierarchyInfo.class);
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getDescr(), r2.getDescr().getPlain());
    }
    
    @Test
    public void testOrgInfo() {
        org.kuali.student.r1.core.organization.dto.OrgInfo r1 = new org.kuali.student.r1.core.organization.dto.OrgInfo();
        r1.setLongDesc("R1 Long Desc");
        r1.setLongName("R1 Long Name");
        List<org.kuali.student.r1.core.organization.dto.OrgCodeInfo> r1OrgCodeList = new ArrayList<org.kuali.student.r1.core.organization.dto.OrgCodeInfo>();
        org.kuali.student.r1.core.organization.dto.OrgCodeInfo r1OrgCodeInfo = new org.kuali.student.r1.core.organization.dto.OrgCodeInfo();
        r1OrgCodeInfo.setAttributes(R1TestDataUtil.getAttributeData());
        r1OrgCodeInfo.setDesc("R1 Org Code Info");
        r1OrgCodeInfo.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1OrgCodeInfo.setValue("R1 Org Code Value");
        r1OrgCodeList.add(r1OrgCodeInfo);
        r1.setOrgCodes(r1OrgCodeList);
        r1.setShortDesc("R1 Short Desc");
        r1.setShortName("R1 Short Name");
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        OrgInfo r2 = R1R2ConverterUtil.convert(r1, OrgInfo.class);
        Assert.assertEquals(r1.getLongDesc(), r2.getLongDescr().getPlain());
        Assert.assertEquals(r1.getLongName(), r2.getLongName());
        Assert.assertEquals("R1-Value", r2.getOrgCodes().get(0).getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getOrgCodes().get(0).getDesc(), r2.getOrgCodes().get(0).getDescr().getPlain());
        Assert.assertEquals(r1.getOrgCodes().get(0).getMetaInfo().getVersionInd(), r2.getOrgCodes().get(0).getMeta().getVersionInd());
        Assert.assertEquals(r1.getShortDesc(), r2.getShortDescr().getPlain());
        Assert.assertEquals(r1.getShortName(), r2.getShortName());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
    }
    
    @Test
    public void testOrgOrgRelationInfo() {
        org.kuali.student.r1.core.organization.dto.OrgOrgRelationInfo r1 = new org.kuali.student.r1.core.organization.dto.OrgOrgRelationInfo();
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        OrgOrgRelationInfo r2 = R1R2ConverterUtil.convert(r1, OrgOrgRelationInfo.class);
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
    }

}
