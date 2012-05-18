package org.kuali.student.core.conversion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.common.conversion.util.R1R2ConverterUtil;
import org.kuali.student.core.conversion.R1TestDataUtil;
import org.kuali.student.r1.common.search.dto.SearchParam;
import org.kuali.student.r1.common.search.dto.SearchResultCell;
import org.kuali.student.r1.common.search.dto.SearchResultRow;
import org.kuali.student.r1.common.search.dto.SortDirection;
import org.kuali.student.r1.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.dto.TagInfo;
import org.kuali.student.r2.core.document.dto.DocumentInfo;
import org.kuali.student.r2.core.document.dto.RefDocRelationInfo;
import org.kuali.student.r2.core.organization.dto.OrgCodeInfo;
import org.kuali.student.r2.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.r2.core.proposal.dto.ProposalDocRelationInfo;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
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
        r1.setId("R1 Id");
        r1.setObjectTypeURI("R1 Object Type URI");
        r1.setSequenceNumber(1L);
        r1.setVersionComment("R1 Version Comment");
        r1.setVersionedFromId("R1 Versioned From Id");
        r1.setVersionIndId("R1 Version Ind Id");
        VersionDisplayInfo r2 = R1R2ConverterUtil.convert(r1, VersionDisplayInfo.class);
        Assert.assertEquals(r1.getId(), r2.getId());
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
    
    @Test
    public void testSearchParamInfo() {
        org.kuali.student.r1.common.search.dto.SearchParam r1 = new org.kuali.student.r1.common.search.dto.SearchParam();
        r1.setKey("R1 Key");
        double chance = Math.random();
        if (chance <= 0.5) {
            List<String> r1ValueList = new ArrayList<String>();
            r1ValueList.add("R1 Value in List");
            r1.setValue(r1ValueList);
        } else {
            r1.setValue("R1 Value Not in List");
        }
        
        SearchParamInfo r2 = R1R2ConverterUtil.convert(r1, SearchParamInfo.class);
        Assert.assertEquals(r1.getKey(), r2.getKey());
        if (r1.getValue() instanceof List<?>) {
            Assert.assertEquals(((List<String>) r1.getValue()).get(0), ((List<String>) r2.getValue()).get(0));
        } else {
            Assert.assertEquals(r1.getValue(), r2.getValue());
        }
    }
    
    @Test
    public void testSearchRequestInfo() {
        org.kuali.student.r1.common.search.dto.SearchRequest r1 = new org.kuali.student.r1.common.search.dto.SearchRequest();
        r1.setMaxResults(1);
        r1.setNeededTotalResults(false);
        List<SearchParam> r1SearchParams = new ArrayList<SearchParam>();
        SearchParam r1SearchParam = new SearchParam();
        r1SearchParam.setKey("R1 Search Param");
        List<String> r1ListValue = new ArrayList<String>();
        r1ListValue.add("R1 List Value");
        r1SearchParam.setValue(r1ListValue);
        r1SearchParam.setValue("R1 String Value");
        r1SearchParams.add(r1SearchParam);
        r1.setParams(r1SearchParams);
        r1.setSearchKey("R1 Search Key");
        r1.setSortColumn("R1 Sort Column");
        r1.setSortDirection(SortDirection.ASC);
        r1.setStartAt(1);
        SearchRequestInfo r2 = R1R2ConverterUtil.convert(r1, SearchRequestInfo.class);
        Assert.assertEquals(r1.getMaxResults(), r2.getMaxResults());
        Assert.assertEquals(r1.getNeededTotalResults(), r2.getNeededTotalResults());
        Assert.assertEquals(r1.getParams().get(0).getKey(), r2.getParams().get(0).getKey());
        Assert.assertEquals(r1.getParams().get(0).getValue(), r2.getParams().get(0).getValue());
        Assert.assertEquals(r1.getSearchKey(), r2.getSearchKey());
        Assert.assertEquals(r1.getSortColumn(), r2.getSortColumn());
        Assert.assertEquals(r1.getSortDirection().name(), r2.getSortDirection().name());
        Assert.assertEquals(r1.getStartAt(), r2.getStartAt());
    }
    
    @Test
    public void testSearchResultCellInfo() {
        org.kuali.student.r1.common.search.dto.SearchResultCell r1 = new org.kuali.student.r1.common.search.dto.SearchResultCell();
        r1.setKey("R1 Key");
        r1.setValue("R1 Value");
        SearchResultCellInfo r2 = R1R2ConverterUtil.convert(r1, SearchResultCellInfo.class);
        Assert.assertEquals(r1.getKey(), r2.getKey());
        Assert.assertEquals(r1.getValue(), r2.getValue());
    }
    
    @Test
    public void testSearchResultInfo() {
        org.kuali.student.r1.common.search.dto.SearchResult r1 = new org.kuali.student.r1.common.search.dto.SearchResult();
        List<SearchResultRow> r1SearchResultRows = new ArrayList<SearchResultRow>();
        SearchResultRow r1SearchResultRow = new SearchResultRow();
        List<SearchResultCell> r1SearchResultCellList = new ArrayList<SearchResultCell>();
        SearchResultCell r1SearchResultCell = new SearchResultCell();
        r1SearchResultCell.setKey("R1 Cell Key");
        r1SearchResultCell.setValue("R1 Cell Value");
        r1SearchResultCellList.add(r1SearchResultCell);
        r1SearchResultRow.setCells(r1SearchResultCellList);
        r1SearchResultRows.add(r1SearchResultRow);
        r1.setRows(r1SearchResultRows);
        r1.setSortColumn("R1 Sort Column");
        r1.setSortDirection(SortDirection.ASC);
        r1.setStartAt(1);
        r1.setTotalResults(1);
        SearchResultInfo r2 = R1R2ConverterUtil.convert(r1, SearchResultInfo.class);
        Assert.assertEquals(r1.getRows().get(0).getCells().get(0).getKey(), r2.getRows().get(0).getCells().get(0).getKey());
        Assert.assertEquals(r1.getSortColumn(), r2.getSortColumn());
        Assert.assertEquals(r1.getSortDirection().name(), r2.getSortDirection().name());
        Assert.assertEquals(r1.getStartAt(), r2.getStartAt());
        Assert.assertEquals(r1.getTotalResults(), r2.getTotalResults());
    }
}
