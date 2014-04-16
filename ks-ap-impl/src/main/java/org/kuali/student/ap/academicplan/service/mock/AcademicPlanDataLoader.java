/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.ap.academicplan.service.mock;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.infc.LearningPlan;
import org.kuali.student.ap.academicplan.service.AcademicPlanService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
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

    public void load() {
        loadPlan("lp1", "0", "student1", "kuali.academicplan.type.plan", "student1", "2012-01-01 00:00:00.0",
                "<p>Student 1 Learning Plan 1</p>",  "Student 1 Learning Plan 1", "lp1 plan name");
        loadPlan("lp2", "0", "student1", "kuali.academicplan.type.plan", "student1", "2012-02-01 00:00:00.0",
                "<p>Student 1 Learning Plan 2</p>",  "Student 1 Learning Plan 2","plan2 name");
        loadPlan("lp3", "0", "student2", "kuali.academicplan.type.plan", "student2", "2012-01-02 00:00:00.0", 
                "<p>Student 2 Learning Plan 3</p>",  "Student 2 Learning Plan 3","plan3 name");
        loadPlan("lp4", "0", "student2", "kuali.academicplan.type.plan", "student2", "2012-02-03 00:00:00.0",
                "<p>Student 2 Learning Plan 4</p>",  "Student 2 Learning Plan 4","plan4 name");

        loadItem("lp1-i1", "0", "006476b5-18d8-4830-bbb6-2bb9e79600fb","kuali.lu.type.CreditCourse",
                "kuali.academicplan.type.item.course", AcademicPlanServiceConstants.ItemCategory.WISHLIST, "lp1",
                "student1", "2012-01-01 00:00:00.0", "<p>Comment 1</p>",  "Comment 1", "lp1-i1 name","");

        loadItem("lp1-i1.x", "0", "006476b5-18d8-4830-bbb6-2bb9e79600fb", "kuali.lu.type.NonCreditCourse",
                "kuali.academicplan.type.item.course", AcademicPlanServiceConstants.ItemCategory.WISHLIST, "lp1",
                "student1", "2012-01-01 00:00:00.0","<p>Comment 1.x</p>",  "Comment 1.x", "lp1-i1.x name","");
        loadItem("lp1-i2", "0", "008d8bea-d63d-43fc-8a77-e52634202f6e", "kuali.lu.type.CreditCourse", 
                "kuali.academicplan.type.item.course", AcademicPlanServiceConstants.ItemCategory.WISHLIST, "lp1",
                "student1", "2012-01-01 00:00:00.0","","","","");
        loadItem("lp1-i3", "0", "00ac5436-7014-4d54-95d6-98a6aeaa70c7", "kuali.lu.type.CreditCourse", 
                "kuali.academicplan.type.item.course", AcademicPlanServiceConstants.ItemCategory.WISHLIST, "lp1",
                "student1", "2012-01-01 00:00:00.0","","","","");
        loadItem("lp1-i4", "0", "00ee833d-f4d6-4aca-b40f-8f80854f8cb3", "kuali.lu.type.CreditCourse",
                "kuali.academicplan.type.item.course", AcademicPlanServiceConstants.ItemCategory.PLANNED, "lp1",
                "student1", "2012-01-01 00:00:00.0","","","","testTermId3");

        List<String> termIds = new ArrayList<String>() {{add("testTermId1");add("testTermId2");}};
        loadItem("lp1-i5", "0", "0005df5d-82e9-4663-8440-aee5ad8046d4", "kuali.lu.type.CreditCourse",
                "kuali.academicplan.type.item.course", AcademicPlanServiceConstants.ItemCategory.PLANNED, "lp1",
                "student1", "2012-01-01 00:00:00.0","","","",termIds);
        loadItem("lp1-i7", "0", "0005df5d-82e9-4663-8440-aee5ad8046d4", "kuali.lu.type.CreditCourse",
                "kuali.academicplan.type.item.course", AcademicPlanServiceConstants.ItemCategory.BACKUP, "lp1",
                "student1", "2012-01-01 00:00:00.0","","","","");

        termIds = new ArrayList<String>() {{add("testTermId1");add("testTermId2");add("testTermId3");}};
        loadItem("lp1-i6", "0", "00edd03d-d7c0-4151-b7f0-8977951c75bd", "kuali.lu.type.CreditCourse",
                "kuali.academicplan.type.item.course", AcademicPlanServiceConstants.ItemCategory.PLANNED, "lp1",
                "student1", "2012-01-01 00:00:00.0", "<p>Very Important</p>",  "Very Important","lp1-i6 name",termIds);
        loadItem("lp2-i1", "0", "00b3683b-fd14-4771-af6c-3c69a43f4592", "kuali.lu.type.CreditCourse", 
                "kuali.academicplan.type.item.course", AcademicPlanServiceConstants.ItemCategory.PLANNED, "lp2",
                "student1", "2012-01-01 00:00:00.0","","","","");
        loadItem("lp2-i2", "0", "00a4c70c-051c-42f9-b45e-6488986a374d", "kuali.lu.type.CreditCourse", 
                "kuali.academicplan.type.item.course", AcademicPlanServiceConstants.ItemCategory.PLANNED, "lp2",
                "student1", "2012-01-01 00:00:00.0","","","","");
        loadItem("lp2-i3", "0", "015f969b-9c18-4938-bcad-39c8a51f230d", "kuali.lu.type.CreditCourse", 
                "kuali.academicplan.type.item.course", AcademicPlanServiceConstants.ItemCategory.PLANNED, "lp2",
                "student1", "2012-01-01 00:00:00.0","","","","");
        loadItem("lp3-i1", "0", "012cc945-7fa1-4df0-9124-8913f250eaf6", "kuali.lu.type.CreditCourse", 
                "kuali.academicplan.type.item.course", AcademicPlanServiceConstants.ItemCategory.WISHLIST, "lp3",
                "student2", "2012-01-01 00:00:00.0","","","","");
        loadItem("lp3-i2", "0", "008739f0-8e62-4f98-88d1-30e6ac779b11", "kuali.lu.type.CreditCourse", 
                "kuali.academicplan.type.item.course", AcademicPlanServiceConstants.ItemCategory.WISHLIST, "lp3",
                "student2", "2012-01-01 00:00:00.0","<p>Maybe Spring 13</p>",  "Maybe Spring 13", "lp3-i3 name","");
        loadItem("lp3-i3", "0", "0131b128-d2dd-47f5-bdc0-3950b81f00e9", "kuali.lu.type.CreditCourse", 
                "kuali.academicplan.type.item.course", AcademicPlanServiceConstants.ItemCategory.WISHLIST, "lp3",
                "student2", "2012-01-01 00:00:00.0","","","","");
        loadItem("lp4-i1", "0", "0099ce3a-0fe8-4586-96b6-c42f6db7c11e", "kuali.lu.type.CreditCourse", 
                "kuali.academicplan.type.item.course", AcademicPlanServiceConstants.ItemCategory.WHATIF, "lp4",
                "student2", "2012-01-01 00:00:00.0","<p>Comment 2</p>",  "Comment 2", "lp4-i2 name","");

        termIds = new ArrayList<String>() {{add("testTermId1");add("testTermId3");}};
        loadItem("lp4-i2", "0", "02711400-c66d-4ecb-aca5-565118f167cf", "kuali.lu.type.CreditCourse",
                "kuali.academicplan.type.item.course", AcademicPlanServiceConstants.ItemCategory.PLANNED, "lp4",
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
            String descrFormatted, String Name, String termId) {
        List<String> termIdList = new ArrayList<String>();
        if (!StringUtils.isNotEmpty(termId )) {
            termIdList.add(termId);
        }
        return this.loadItem(id, version,  refObjectId,  refObjectType,  itemType, category,  planId,  createdById,  createTime,  descrPlain,
                 descrFormatted,  Name, termIdList);
    }

    public PlanItemInfo loadItem(String id, String version, String refObjectId, String refObjectType, String itemType,
            AcademicPlanServiceConstants.ItemCategory category, String planId, String createdById, String createTime, String descrPlain,
            String descrFormatted, String Name, List<String> termIdList) {
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
            throw new IllegalArgumentException("Bad date " + str + " in " + context);
        }
    }
}
