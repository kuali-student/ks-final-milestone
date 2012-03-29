package org.kuali.student.common.conversion;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.common.conversion.util.R1R2ConverterUtil;
import org.kuali.student.r1.common.search.dto.SearchParam;
import org.kuali.student.r1.common.search.dto.SearchResultCell;
import org.kuali.student.r1.common.search.dto.SearchResultRow;
import org.kuali.student.r1.common.search.dto.SortDirection;
import org.kuali.student.r2.common.dto.AmountInfo;
import org.kuali.student.r2.common.dto.CurrencyAmountInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.messages.dto.MessageInfo;
import org.kuali.student.r2.common.search.dto.SearchParamInfo;
import org.kuali.student.r2.common.search.dto.SearchRequestInfo;
import org.kuali.student.r2.common.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.common.search.dto.SearchResultInfo;

public class CommonConverterTest {
    
    @Test
    public void testAmountInfo() {
        org.kuali.student.r1.common.dto.AmountInfo r1 = new org.kuali.student.r1.common.dto.AmountInfo();
        r1.setUnitQuantity("R1 Unit Quantity");
        r1.setUnitType("R1 Unit Type");
        AmountInfo r2 = R1R2ConverterUtil.convert(r1, AmountInfo.class);
        Assert.assertEquals(r1.getUnitQuantity(), r2.getUnitQuantity());
        Assert.assertEquals(r1.getUnitType(), r2.getUnitTypeKey());
    }

    @Test
    public void testCurrencyAmountInfo() {
        org.kuali.student.r1.common.dto.CurrencyAmountInfo r1 = new org.kuali.student.r1.common.dto.CurrencyAmountInfo();
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1.setCurrencyQuantity(1);
        r1.setCurrencyTypeKey("R1 Currency Type Key");
        CurrencyAmountInfo r2 = R1R2ConverterUtil.convert(r1, CurrencyAmountInfo.class);
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getMetaInfo().getCreateTime(), r2.getMeta().getCreateTime());
        Assert.assertEquals(r1.getCurrencyQuantity(), r2.getCurrencyQuantity());
        Assert.assertEquals(r1.getCurrencyTypeKey(), r2.getCurrencyTypeKey());
    }

    @Test
    public void testMetaInfo() {
        org.kuali.student.r1.common.dto.MetaInfo r1 = R1TestDataUtil.getMetadataInfoData();
        MetaInfo r2 = R1R2ConverterUtil.convert(r1, MetaInfo.class);
        Assert.assertEquals(r1.getCreateId(), r2.getCreateId());
        Assert.assertEquals(r1.getCreateTime(), r2.getCreateTime());
        Assert.assertEquals(r1.getUpdateId(), r2.getUpdateId());
        Assert.assertEquals(r1.getUpdateTime(), r2.getUpdateTime());
        Assert.assertEquals(r1.getVersionInd(), r2.getVersionInd());
    }

    @Test
    public void testRichTextInfo() {
        org.kuali.student.r1.common.dto.RichTextInfo r1 = R1TestDataUtil.getRichTextInfoData();
        RichTextInfo r2 = R1R2ConverterUtil.convert(r1, RichTextInfo.class);
        Assert.assertEquals(r1.getFormatted(), r2.getFormatted());
        Assert.assertEquals(r1.getPlain(), r2.getPlain());
    }

    @Test
    public void testStatusInfo() {
        org.kuali.student.r1.common.dto.StatusInfo r1 = new org.kuali.student.r1.common.dto.StatusInfo();
        r1.setMessage("R1 Message");
        r1.setSuccess(true);
        StatusInfo r2 = R1R2ConverterUtil.convert(r1, StatusInfo.class);
        Assert.assertEquals(r1.getMessage(), r2.getMessage());
        Assert.assertEquals(r1.getSuccess(), r2.getIsSuccess());
    }

    @Test
    public void testTimeAmountInfo() {
        org.kuali.student.r1.common.dto.TimeAmountInfo r1 = new org.kuali.student.r1.common.dto.TimeAmountInfo();
        r1.setAtpDurationTypeKey("R1 Atp Duration Key");
        r1.setTimeQuantity(1);
        TimeAmountInfo r2 = R1R2ConverterUtil.convert(r1, TimeAmountInfo.class);
        Assert.assertEquals(r1.getAtpDurationTypeKey(), r2.getAtpDurationTypeKey());
        Assert.assertEquals(r1.getTimeQuantity(), r2.getTimeQuantity());
    }

    @Test
    public void testTypeInfo() {
        org.kuali.student.r1.common.dto.TypeInfo r1 = new org.kuali.student.r1.common.dto.TypeInfo() {
                };
        r1.setId("R1 Id");
        r1.setName("R1 Name");
        r1.setDescr("R1 Descr");
        r1.setEffectiveDate(new Date());
        r1.setExpirationDate(new Date());
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        TypeInfo r2 = R1R2ConverterUtil.convert(r1, TypeInfo.class);
        Assert.assertEquals(r1.getId(), r2.getKey());
        Assert.assertEquals(r1.getName(), r2.getName());
        Assert.assertEquals(r1.getDescr(), r2.getDescr().getPlain());
        Assert.assertEquals(r1.getEffectiveDate(), r2.getEffectiveDate());
        Assert.assertEquals(r1.getExpirationDate(), r2.getExpirationDate());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        //R1 Object has no matching properties for these:
        //r2.setRefObjectUri(refObjectUri)
        //r2.setMeta(metaInfo)
    }

    @Test
    public void testMessageInfo() {
        org.kuali.student.r1.common.messages.dto.Message r1 = new org.kuali.student.r1.common.messages.dto.Message();
        r1.setId("R1 Message Id");
        r1.setGroupName("R1 Group Name");
        r1.setLocale("R1 Locale");
        r1.setValue("R1 Value");
        MessageInfo r2 = R1R2ConverterUtil.convert(r1, MessageInfo.class);
        Assert.assertEquals(r1.getId(), r2.getKey());
        Assert.assertEquals(r1.getGroupName(), r2.getGroupName());
        Assert.assertEquals(r1.getLocale(), r2.getLocale().getLocaleLanguage());
        Assert.assertEquals(r1.getValue(), r2.getValue());
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
