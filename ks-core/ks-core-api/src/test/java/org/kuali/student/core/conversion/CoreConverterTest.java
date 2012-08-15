package org.kuali.student.core.conversion;

import java.util.ArrayList;
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
import org.kuali.student.r2.core.organization.dto.OrgCodeInfo;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r2.core.statement.dto.StatementInfo;
import org.kuali.student.r2.core.statement.dto.StatementTreeViewInfo;

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
        //r1.setDetailDesc("R1 Desc"); No matching R2 property exists
        r1.setState("R1 State");
        r1.setType("R1 Type");
        ProposalInfo r2 = R1R2ConverterUtil.convert(r1, ProposalInfo.class);
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getProposalReference().get(0), r2.getProposalReference().get(0));
        Assert.assertEquals(r1.getProposerOrg().get(0), r2.getProposerOrg().get(0));
        Assert.assertEquals(r1.getProposerPerson().get(0), r2.getProposerPerson().get(0));
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
    }
    
    @Test
    public void testReqComponentInfo() {
        org.kuali.student.r1.core.statement.dto.ReqComponentInfo r1 = R1TestDataUtil.getReqComponentInfoData();
        ReqComponentInfo r2 = R1R2ConverterUtil.convert(r1, ReqComponentInfo.class);
        Assert.assertEquals(r1.getId(), r2.getId());
        Assert.assertEquals(r1.getNaturalLanguageTranslation(), r2.getNaturalLanguageTranslation());
        Assert.assertEquals(r1.getDesc().getPlain(), r2.getDescr().getPlain());
        Assert.assertEquals(r1.getEffectiveDate(), r2.getEffectiveDate());
        Assert.assertEquals(r1.getExpirationDate(), r2.getExpirationDate());
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
        Assert.assertEquals(r1.getReqComponents().get(0).getState(), r2.getReqComponents().get(0).getStateKey());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getStatements().get(0).getReqComponents().get(0).getDesc().getPlain(), r2.getStatements().get(0).getReqComponents().get(0).getDescr().getPlain());
        Assert.assertEquals(r1.getStatements().get(0).getReqComponents().get(0).getType(), r2.getStatements().get(0).getReqComponents().get(0).getTypeKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
    }
    
}
