package org.kuali.student.core.conversion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.common.conversion.util.R1R2ConverterUtil;
import org.kuali.student.r1.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.dto.TagInfo;
import org.kuali.student.r2.core.document.dto.DocumentInfo;
import org.kuali.student.r2.core.document.dto.RefDocRelationInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumerationInfo;
import org.kuali.student.r2.core.organization.dto.OrgCodeInfo;
import org.kuali.student.r2.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.r2.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.r2.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.r2.core.organization.dto.OrgTreeInfo;
import org.kuali.student.r2.core.proposal.dto.ProposalDocRelationInfo;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r2.core.statement.dto.StatementInfo;
import org.kuali.student.r2.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;

public class CoreConverterTest {
    
    @Test
    public void testVersionDisplayInfo() {
        org.kuali.student.r1.common.versionmanagement.dto.VersionDisplayInfo r1 = new org.kuali.student.r1.common.versionmanagement.dto.VersionDisplayInfo();
        r1.setCurrentVersionEnd(new Date());
        r1.setCurrentVersionStart(new Date());
        //R2's Id property is deprecated and is marked to return null
        //r1.setId("R1 Id");
        r1.setObjectTypeURI("R1 Object Type URI");
        r1.setSequenceNumber(1L);
        r1.setVersionComment("R1 Version Comment");
        r1.setVersionedFromId("R1 Versioned From Id");
        r1.setVersionIndId("R1 Version Ind Id");
        VersionDisplayInfo r2 = R1R2ConverterUtil.convert(r1, VersionDisplayInfo.class);
        //Assert.assertEquals(r1.getId(), r2.getId());
        Assert.assertEquals(r1.getObjectTypeURI(), r2.getRefObjectUri());
        Assert.assertEquals(r1.getVersionComment(), r2.getVersionComment());
        Assert.assertEquals(r1.getVersionedFromId(), r2.getVersionedFromId());
        Assert.assertEquals(r1.getVersionIndId(), r2.getVersionIndId());
        Assert.assertEquals(r1.getCurrentVersionEnd(), r2.getCurrentVersionEnd());
        Assert.assertEquals(r1.getCurrentVersionStart(), r2.getCurrentVersionStart());
        Assert.assertEquals(r1.getSequenceNumber(), r2.getSequenceNumber());
    }
    
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
        r1.setState("R1 State");
        r1.setType("R1 Type");
        CommentInfo r2 = R1R2ConverterUtil.convert(r1, CommentInfo.class);
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getCommentText().getPlain(), r2.getCommentText().getPlain());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
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
        r1.setState("R1 State");
        r1.setType("R1 Type");
        DocumentInfo r2 = R1R2ConverterUtil.convert(r1, DocumentInfo.class);
        Assert.assertEquals(r1.getDocumentBinaryInfo().getBinary(), r2.getDocumentBinary().getBinary());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals("R1 Plain", r2.getDescr().getPlain());
        Assert.assertEquals("R1 Meta Info Version Id", r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
    }
    
    @Test
    public void testRefDocRelationInfo() {
        org.kuali.student.r1.core.document.dto.RefDocRelationInfo r1 = new org.kuali.student.r1.core.document.dto.RefDocRelationInfo();
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setDesc(R1TestDataUtil.getRichTextInfoData());
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1.setState("R1 State");
        r1.setType("R1 Type");
        RefDocRelationInfo r2 = R1R2ConverterUtil.convert(r1, RefDocRelationInfo.class);
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals("R1 Plain", r2.getDescr().getPlain());
        Assert.assertEquals("R1 Meta Info Version Id", r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
    }
    
    /*@Test
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
    }*/
    
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
        r1.setState("R1 State");
        r1.setType("R1 Type");
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
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
    }
    
    @Test
    public void testOrgOrgRelationInfo() {
        org.kuali.student.r1.core.organization.dto.OrgOrgRelationInfo r1 = new org.kuali.student.r1.core.organization.dto.OrgOrgRelationInfo();
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1.setState("R1 State");
        r1.setType("R1 Type");
        OrgOrgRelationInfo r2 = R1R2ConverterUtil.convert(r1, OrgOrgRelationInfo.class);
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
    }
    
    @Test
    public void testOrgPersonRelationInfo() {
        org.kuali.student.r1.core.organization.dto.OrgPersonRelationInfo r1 = new org.kuali.student.r1.core.organization.dto.OrgPersonRelationInfo();
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1.setState("R1 State");
        r1.setType("R1 Type");
        OrgPersonRelationInfo r2 = R1R2ConverterUtil.convert(r1, OrgPersonRelationInfo.class);
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
    }
    
    @Test
    public void testOrgPositionRestrictionInfo() {
        org.kuali.student.r1.core.organization.dto.OrgPositionRestrictionInfo r1 = new org.kuali.student.r1.core.organization.dto.OrgPositionRestrictionInfo();
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        org.kuali.student.r1.common.dto.TimeAmountInfo r1TimeAmountInfo = new org.kuali.student.r1.common.dto.TimeAmountInfo();
        r1TimeAmountInfo.setAtpDurationTypeKey("R1 Duration Key");
        r1TimeAmountInfo.setTimeQuantity(1);
        r1.setStdDuration(r1TimeAmountInfo);
        r1.setDesc("R1 desc");
        OrgPositionRestrictionInfo r2 = R1R2ConverterUtil.convert(r1, OrgPositionRestrictionInfo.class);
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getStdDuration().getAtpDurationTypeKey(), r2.getStdDuration().getAtpDurationTypeKey());
        Assert.assertEquals(r1.getStdDuration().getTimeQuantity(), r2.getStdDuration().getTimeQuantity());
        Assert.assertEquals(r1.getDesc(), r2.getDescr().getPlain());
    }
    
    @Test
    public void testOrgTreeInfo() {
        org.kuali.student.r1.core.organization.dto.OrgTreeInfo r1 = new org.kuali.student.r1.core.organization.dto.OrgTreeInfo();
        r1.setRelationType("R1 Type");
        OrgTreeInfo r2 = R1R2ConverterUtil.convert(r1, OrgTreeInfo.class);
        Assert.assertEquals(r1.getRelationType(), r2.getRelationTypeKey());
    }
    
    @Test
    public void testProposalDocRelationInfo() {
        org.kuali.student.r1.core.proposal.dto.ProposalDocRelationInfo r1 = new org.kuali.student.r1.core.proposal.dto.ProposalDocRelationInfo();
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setDesc(R1TestDataUtil.getRichTextInfoData());
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1.setState("R1 State");
        r1.setType("R1 Type");
        ProposalDocRelationInfo r2 = R1R2ConverterUtil.convert(r1, ProposalDocRelationInfo.class);
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
    }
    
    @Test
    public void testProposalInfo() {
        org.kuali.student.r1.core.proposal.dto.ProposalInfo r1 = new org.kuali.student.r1.core.proposal.dto.ProposalInfo();
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        List<String> r1PropRefList = new ArrayList<String>();
        r1PropRefList.add("R1 Ref");
        r1.setProposalReference(r1PropRefList);
        List<String> r1PropOrgList = new ArrayList<String>();
        r1PropOrgList.add("R1 Org");
        r1.setProposerOrg(r1PropOrgList);
        List<String> r1PropPersonList = new ArrayList<String>();
        r1PropPersonList.add("R1 Prop Person");
        r1.setProposerPerson(r1PropPersonList);
        r1.setDetailDesc("R1 Desc");
        r1.setState("R1 State");
        r1.setType("R1 Type");
        ProposalInfo r2 = R1R2ConverterUtil.convert(r1, ProposalInfo.class);
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getProposalReference().get(0), r2.getProposalReference().get(0));
        Assert.assertEquals(r1.getProposerOrg().get(0), r2.getProposerOrg().get(0));
        Assert.assertEquals(r1.getProposerPerson().get(0), r2.getProposerPerson().get(0));
        Assert.assertEquals(r1.getDetailDesc(), r2.getDetailDesc().getPlain());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
    }
    
    @Test
    public void testReqComponentInfo() {
        org.kuali.student.r1.core.statement.dto.ReqComponentInfo r1 = new org.kuali.student.r1.core.statement.dto.ReqComponentInfo();
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        List<org.kuali.student.r1.core.statement.dto.ReqCompFieldInfo> r1ReqCompList = new ArrayList<org.kuali.student.r1.core.statement.dto.ReqCompFieldInfo>();
        org.kuali.student.r1.core.statement.dto.ReqCompFieldInfo r1ReqComFieldInfo = new org.kuali.student.r1.core.statement.dto.ReqCompFieldInfo();
        r1ReqComFieldInfo.setId("R1 Id");
        r1ReqComFieldInfo.setType("R1 Type");
        r1ReqComFieldInfo.setValue("R1 Value");
        r1ReqCompList.add(r1ReqComFieldInfo);
        r1.setReqCompFields(r1ReqCompList);
        r1.setState("R1 State");
        r1.setType("R1 Type");
        ReqComponentInfo r2 = R1R2ConverterUtil.convert(r1, ReqComponentInfo.class);
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals("R1 Value", r2.getReqCompFields().get(0).getValue());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
    }
    
    @Test
    public void testStatementInfo() {
        org.kuali.student.r1.core.statement.dto.StatementInfo r1 = new org.kuali.student.r1.core.statement.dto.StatementInfo();
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setDesc(R1TestDataUtil.getRichTextInfoData());
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1.setOperator(StatementOperatorTypeKey.AND);
        r1.setState("R1 State");
        r1.setType("R1 Type");
        StatementInfo r2 = R1R2ConverterUtil.convert(r1, StatementInfo.class);
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getDesc().getPlain(), r2.getDescr().getPlain());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getOperator().name(), r2.getOperator().name());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
    }
    
    @Test
    public void testStatementTreeViewInfo() {
        org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo r1 = R1TestDataUtil.getStatementTreeViewInfoData();
        StatementTreeViewInfo r2 = R1R2ConverterUtil.convert(r1, StatementTreeViewInfo.class);
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getDesc().getPlain(), r2.getDescr().getPlain());
        Assert.assertEquals(r1.getId(), r2.getId());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getName(), r2.getName());
        Assert.assertEquals(r1.getOperator().name(), r2.getOperator().name());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
    }

}
