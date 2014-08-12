/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.ap.service;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.infc.LearningPlan;
import org.kuali.student.ap.academicplan.service.AcademicPlanService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.core.class1.atp.service.impl.DateUtil;
import org.kuali.student.r2.lum.clu.dto.CluIdentifierInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author nwright
 */
public class AcademicPlanDataLoader {

    private AcademicPlanService planService;
    private ContextInfo contextInfo;

    public AcademicPlanService getPlanService() {
        return planService;
    }

    public void setPlanService(AcademicPlanService planService) {
        this.planService = planService;
    }

    public ContextInfo getContextInfo() {
        return contextInfo;
    }

    public void setContextInfo(ContextInfo contextInfo) {
        this.contextInfo = contextInfo;
    }

    public void load() throws DataValidationErrorException{
        loadPlan("lp1", "0", "student1", AcademicPlanServiceConstants.LEARNING_PLAN_TYPE_PLAN, "student1", "2012-01-01 00:00:00.0",
                "<p>Student 1 Learning Plan 1</p>",  "Student 1 Learning Plan 1", "lp1 plan name");
        loadPlan("lp2", "0", "student1", AcademicPlanServiceConstants.LEARNING_PLAN_TYPE_PLAN, "student1", "2012-02-01 00:00:00.0",
                "<p>Student 1 Learning Plan 2</p>",  "Student 1 Learning Plan 2","plan2 name");
        loadPlan("lp3", "0", "student2", AcademicPlanServiceConstants.LEARNING_PLAN_TYPE_PLAN, "student2", "2012-01-02 00:00:00.0", 
                "<p>Student 2 Learning Plan 3</p>",  "Student 2 Learning Plan 3","plan3 name");
        loadPlan("lp4", "0", "student2", AcademicPlanServiceConstants.LEARNING_PLAN_TYPE_PLAN, "student2", "2012-02-03 00:00:00.0",
                "<p>Student 2 Learning Plan 4</p>",  "Student 2 Learning Plan 4","plan4 name");

        loadItem("lp1-i1", "0", "ENGL101ind",CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY,
                AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE, AcademicPlanServiceConstants.ItemCategory.WISHLIST, "lp1",
                "student1", "2012-01-01 00:00:00.0", "<p>Comment 1</p>",  "Comment 1", "lp1-i1 name","");

        loadItem("lp1-i1.x", "0", "CHEM131ind", CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY,
                AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE, AcademicPlanServiceConstants.ItemCategory.WISHLIST, "lp1",
                "student1", "2012-01-01 00:00:00.0","<p>Comment 1.x</p>",  "Comment 1.x", "lp1-i1.x name","");
        loadItem("lp1-i2", "0", "BSCI121ind", CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY,
                AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE, AcademicPlanServiceConstants.ItemCategory.WISHLIST, "lp1",
                "student1", "2012-01-01 00:00:00.0","","","","");
        loadItem("lp1-i3", "0", "BSCI122ind", CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY,
                AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE, AcademicPlanServiceConstants.ItemCategory.WISHLIST, "lp1",
                "student1", "2012-01-01 00:00:00.0","","","","");
        loadItem("lp1-i4", "0", "ENGL205ind", CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY,
                AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE, AcademicPlanServiceConstants.ItemCategory.PLANNED, "lp1",
                "student1", "2012-01-01 00:00:00.0","","","","testTermId3");

        List<String> termIds = new ArrayList<String>() {{add("testTermId1");add("testTermId2");}};
        loadItem("lp1-i5", "0", "BSCI123ind", CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY,
                AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE, AcademicPlanServiceConstants.ItemCategory.PLANNED, "lp1",
                "student1", "2012-01-01 00:00:00.0","","","",termIds);
        loadItem("lp1-i7", "0", "BSCI123ind", CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY,
                AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE, AcademicPlanServiceConstants.ItemCategory.BACKUP, "lp1",
                "student1", "2012-01-01 00:00:00.0","","","","testTermId3");

        termIds = new ArrayList<String>() {{add("testTermId1");add("testTermId2");add("testTermId3");}};
        loadItem("lp1-i6", "0", "ENGL206ind", CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY,
                AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE, AcademicPlanServiceConstants.ItemCategory.PLANNED, "lp1",
                "student1", "2012-01-01 00:00:00.0", "<p>Very Important</p>",  "Very Important","lp1-i6 name",termIds);
        loadItem("lp2-i1", "0", "CHEM131ind", CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY,
                AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE, AcademicPlanServiceConstants.ItemCategory.PLANNED, "lp2",
                "student1", "2012-01-01 00:00:00.0","","","","testTermId3");
        loadItem("lp2-i2", "0", "BSCI121ind", CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY,
                AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE, AcademicPlanServiceConstants.ItemCategory.PLANNED, "lp2",
                "student1", "2012-01-01 00:00:00.0","","","","testTermId3");
        loadItem("lp2-i3", "0", "BSCI122ind", CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY,
                AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE, AcademicPlanServiceConstants.ItemCategory.PLANNED, "lp2",
                "student1", "2012-01-01 00:00:00.0","","","","testTermId3");
        loadItem("lp3-i1", "0", "ENGL101ind", CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY,
                AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE, AcademicPlanServiceConstants.ItemCategory.WISHLIST, "lp3",
                "student2", "2012-01-01 00:00:00.0","","","","");
        loadItem("lp3-i2", "0", "CHEM131ind", CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY,
                AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE, AcademicPlanServiceConstants.ItemCategory.WISHLIST, "lp3",
                "student2", "2012-01-01 00:00:00.0","<p>Maybe Spring 13</p>",  "Maybe Spring 13", "lp3-i3 name","");
        loadItem("lp3-i3", "0", "BSCI121ind", CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY,
                AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE, AcademicPlanServiceConstants.ItemCategory.WISHLIST, "lp3",
                "student2", "2012-01-01 00:00:00.0","","","","");
        loadItem("lp4-i1", "0", "ENGL101ind", CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY,
                AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE, AcademicPlanServiceConstants.ItemCategory.WHATIF, "lp4",
                "student2", "2012-01-01 00:00:00.0","<p>Comment 2</p>",  "Comment 2", "lp4-i2 name","");

        termIds = new ArrayList<String>() {{add("testTermId1");add("testTermId3");}};
        loadItem("lp4-i2", "0", "BSCI122ind", CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY,
                AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE, AcademicPlanServiceConstants.ItemCategory.PLANNED, "lp4",
                "student2", "2012-01-01 00:00:00.0","","","",termIds);
    }

    public LearningPlanInfo loadPlan(String id, String version, String studentId, String typeKey,
            String createId, String createTime, String descrFormatted, String descrPlain,  String Name) {
        LearningPlanInfo plan = new LearningPlanInfo();
        plan.setId(id);
        MetaInfo meta = new MetaInfo();
        meta.setVersionInd(version);
        plan.setMeta(meta);
        plan.setStudentId(studentId);
        plan.setTypeKey(typeKey);
           RichTextInfo rt = new RichTextInfo();
        rt.setPlain(descrPlain);
        rt.setFormatted(descrFormatted);
        plan.setDescr(rt);
        plan.setStateKey("Active");
        Date createDateTime = str2Date(createTime,"in create plan with id="+id);
        this.getContextInfo().setCurrentDate(createDateTime);
        this.getContextInfo().setPrincipalId(createId);

        try {
            return this.planService.createLearningPlan(plan, contextInfo);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public PlanItemInfo loadItem(String id, String version, String refObjectId, String refObjectType, String itemType,
            AcademicPlanServiceConstants.ItemCategory category, String planId, String createdById, String createTime, String descrPlain,
            String descrFormatted, String Name, String termId) throws DataValidationErrorException {
        List<String> termIdList = new ArrayList<String>();
        if (StringUtils.isNotEmpty(termId )) {
            termIdList.add(termId);
        }
        return this.loadItem(id, version,  refObjectId,  refObjectType,  itemType, category,  planId,  createdById,  createTime,  descrPlain,
                 descrFormatted,  Name, termIdList);
    }

    public PlanItemInfo loadItem(String id, String version, String refObjectId, String refObjectType, String itemType,
            AcademicPlanServiceConstants.ItemCategory category, String planId, String createdById, String createTime, String descrPlain,
            String descrFormatted, String Name, List<String> termIdList) throws DataValidationErrorException{
        PlanItemInfo item = new PlanItemInfo();
        item.setId(id);
        MetaInfo meta = new MetaInfo();
        meta.setVersionInd(version);
        item.setMeta(meta);
        item.setRefObjectId(refObjectId);
        item.setRefObjectType(refObjectType);
        item.setTypeKey(itemType);
        item.setCategory(category);
        item.setLearningPlanId(planId);
        Date createDateTime = str2Date(createTime,"in create plan with id="+id);
        this.getContextInfo().setCurrentDate(createDateTime);
        this.getContextInfo().setPrincipalId(createdById);
        RichTextInfo rt = new RichTextInfo();
        rt.setPlain(descrPlain);
        rt.setFormatted(descrFormatted);
        item.setDescr(rt);
        item.setPlanTermIds(termIdList);
        item.setStateKey("Active");
        try {
            return this.planService.createPlanItem(item, contextInfo);
        } catch (DataValidationErrorException dvee){
            throw dvee;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    private Date str2Date(String str, String context) {
        if (str == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.S");
        try {
            Date date = df.parse(str);
            return date;
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Bad date " + str + " in " + context,ex);
        }
    }
}
