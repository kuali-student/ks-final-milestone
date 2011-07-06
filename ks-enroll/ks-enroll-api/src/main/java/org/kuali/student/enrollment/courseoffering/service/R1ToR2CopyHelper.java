package org.kuali.student.enrollment.courseoffering.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.CurrencyAmountInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.lum.course.dto.AffiliatedOrgInfo;
import org.kuali.student.r2.lum.course.dto.CourseExpenditureInfo;
import org.kuali.student.r2.lum.course.dto.CourseFeeInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultComponentInfo;

/**
 * Utility to copy R1 to R2 structures
 * 
 * @author nwright
 */
public class R1ToR2CopyHelper {

    public CourseExpenditureInfo copyCourseExpenditure(org.kuali.student.lum.course.dto.CourseExpenditureInfo r1) {
        if (r1 == null) {
            return null;
        }
        CourseExpenditureInfo r2 = new CourseExpenditureInfo();
        // TODO: worry about using the toString method for the id 
        r2.setId(r1.toString());
        r2.setAffiliatedOrgs(copyAffiliatedOrgList(r1.getAffiliatedOrgs()));
        r2.setAttributes(copyAttributes(r1.getAttributes()));
        return r2;
    }

    public List<AffiliatedOrgInfo> copyAffiliatedOrgList(List<org.kuali.student.lum.lu.dto.AffiliatedOrgInfo> r1List) {
        if (r1List == null) {
            return null;
        }
        List<AffiliatedOrgInfo> r2List = new ArrayList<AffiliatedOrgInfo>(r1List.size());
        for (org.kuali.student.lum.lu.dto.AffiliatedOrgInfo r1 : r1List) {
            r2List.add(copyAffiliatedOrg(r1));
        }
        return r2List;
    }

    public AffiliatedOrgInfo copyAffiliatedOrg(org.kuali.student.lum.lu.dto.AffiliatedOrgInfo r1) {
        if (r1 == null) {
            return null;
        }
        AffiliatedOrgInfo r2 = new AffiliatedOrgInfo();
        r2.setId(r1.getId());
        // TODO: deep copy the dates
        r2.setEffectiveDate(r1.getEffectiveDate());
        r2.setExpirationDate(r1.getExpirationDate());
        r2.setOrgId(r1.getOrgId());
        r2.setPercentage(r1.getPercentage());
        return r2;
    }

    public List<AttributeInfo> copyAttributes(Map<String, String> r1Map) {
        if (r1Map == null) {
            return null;
        }
        List<AttributeInfo> r2List = new ArrayList<AttributeInfo>(r1Map.size());
        for (String key : r1Map.keySet()) {
            AttributeInfo r2 = new AttributeInfo();
            r2.setId(key);
            r2.setKey(key);
            r2.setValue(r1Map.get(key));
            r2List.add(r2);
        }
        return r2List;
    }

    public List<CourseFeeInfo> copyCourseFeeList(List<org.kuali.student.lum.course.dto.CourseFeeInfo> r1List) {
        if (r1List == null) {
            return null;
        }
        List<CourseFeeInfo> r2List = new ArrayList<CourseFeeInfo>(r1List.size());
        for (org.kuali.student.lum.course.dto.CourseFeeInfo r1 : r1List) {
            r2List.add(copyCourseFee(r1));
        }
        return r2List;
    }

    public CourseFeeInfo copyCourseFee(org.kuali.student.lum.course.dto.CourseFeeInfo r1) {
        if (r1 == null) {
            return null;
        }
        CourseFeeInfo r2 = new CourseFeeInfo();
        r2.setId(r1.getId());
        r2.setAttributes(copyAttributes(r1.getAttributes()));
        r2.setDescr(copyRichText(r1.getDescr()));
        r2.setFeeAmounts(copyCurrencyAmountList(r1.getFeeAmounts()));
        return r2;
    }

    public RichTextInfo copyRichText(org.kuali.student.common.dto.RichTextInfo r1) {
        if (r1 == null) {
            return null;
        }
        RichTextInfo r2 = new RichTextInfo();
        r2.setFormatted(r1.getFormatted());
        r2.setPlain(r1.getPlain());
        return r2;
    }

    public List<CurrencyAmountInfo> copyCurrencyAmountList(List<org.kuali.student.common.dto.CurrencyAmountInfo> r1List) {
        if (r1List == null) {
            return null;
        }
        List<CurrencyAmountInfo> r2List = new ArrayList<CurrencyAmountInfo>(r1List.size());
        for (org.kuali.student.common.dto.CurrencyAmountInfo r1 : r1List) {
            r2List.add(copyCurrencyAmountInfo(r1));
        }
        return r2List;
    }

    public CurrencyAmountInfo copyCurrencyAmountInfo(org.kuali.student.common.dto.CurrencyAmountInfo r1) {
        if (r1 == null) {
            return null;
        }
        CurrencyAmountInfo r2 = new CurrencyAmountInfo();
        r2.setId(r1.getId());
        r2.setCurrencyTypeKey(r1.getCurrencyTypeKey());
        r2.setCurrencyQuantity(r1.getCurrencyQuantity());
        r2.setMeta(copyMetaInfo(r1.getMetaInfo()));
        return r2;
    }

    public MetaInfo copyMetaInfo(org.kuali.student.common.dto.MetaInfo r1) {
        if (r1 == null) {
            return null;
        }
        MetaInfo r2 = new MetaInfo();
        r2.setCreateId(r1.getCreateId());
        r2.setCreateTime(r1.getCreateTime());
        r2.setUpdateId(r1.getUpdateId());
        r2.setUpdateTime(r1.getUpdateTime());
        r2.setVersionInd(r1.getVersionInd());
        return r2;
    }

    public List<ResultComponentInfo> copyResultComponentList(List<org.kuali.student.lum.lrc.dto.ResultComponentInfo> r1List) {
        if (r1List == null) {
            return null;
        }
        List<ResultComponentInfo> r2List = new ArrayList<ResultComponentInfo>(r1List.size());
        for (org.kuali.student.lum.lrc.dto.ResultComponentInfo r1 : r1List) {
            r2List.add(copyResultComponent(r1));
        }
        return r2List;
    }

    public ResultComponentInfo copyResultComponent(org.kuali.student.lum.lrc.dto.ResultComponentInfo r1) {
        if (r1 == null) {
            return null;
        }
        ResultComponentInfo r2 = new ResultComponentInfo();
        r2.setAttributes(copyAttributes(r1.getAttributes()));
        r2.setDescr(copyRichText(r1.getDesc()));
        // TODO: Deep copy the dates
        r2.setEffectiveDate(r1.getEffectiveDate());
        r2.setExpirationDate(r1.getExpirationDate());
        r2.setId(r1.getId());
        r2.setMeta(copyMetaInfo(r1.getMetaInfo()));
        r2.setName(r1.getName());
        r2.setResultValueIds(r1.getResultValues());
        // TODO: deail with ranges perhaps by interrogating the result values to see if it is a range?
        r2.setResultValueRange(null);
        r2.setStateKey(r1.getState());
        r2.setTypeKey(r1.getType());
        return r2;

    }
}
