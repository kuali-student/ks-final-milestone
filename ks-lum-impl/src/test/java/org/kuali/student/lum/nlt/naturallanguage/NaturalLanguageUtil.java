package org.kuali.student.lum.nlt.naturallanguage;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.entity.Clu;
import org.kuali.student.lum.lu.entity.CluIdentifier;
import org.kuali.student.lum.lu.entity.CluSet;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.lu.entity.ReqComponentField;
import org.kuali.student.lum.nlt.naturallanguage.ContextRegistry;
import org.kuali.student.lum.nlt.naturallanguage.context.Context;
import org.kuali.student.lum.nlt.naturallanguage.context.CourseListContextImpl;
import org.kuali.student.lum.nlt.naturallanguage.context.GradeCheckContextImpl;
import org.kuali.student.lum.nlt.naturallanguage.context.GradeConditionCourseListContextImpl;
import org.kuali.student.lum.nlt.naturallanguage.context.HeaderContextImpl;
import org.kuali.student.lum.nlt.naturallanguage.util.LuStatementAnchor;

public class NaturalLanguageUtil {
	public static String createCluSet(LuDao luDao) {
    	Clu clu1 = new Clu();
    	CluIdentifier cluId1 = new CluIdentifier();
    	cluId1.setCode("MATH152");
    	cluId1.setDivision("MATH");
    	cluId1.setLevel("152");
    	cluId1.setShortName("MATH 152");
    	cluId1.setLongName("MATH 152 Linear Systems");
    	clu1.setOfficialIdentifier(cluId1);
    	
    	clu1 = luDao.create(clu1);

    	Clu clu2 = new Clu();
    	CluIdentifier cluId2 = new CluIdentifier();
    	cluId2.setCode("MATH221");
    	cluId2.setDivision("MATH");
    	cluId2.setLevel("221");
    	cluId2.setShortName("MATH 221");
    	cluId2.setLongName("MATH 221 Matrix Algebra");
    	clu2.setOfficialIdentifier(cluId2);
    	
    	clu2 = luDao.create(clu2);

    	List<Clu> cluList = new ArrayList<Clu>();
    	cluList.add(clu1);
    	cluList.add(clu2);

    	
    	CluSet cluSet = new CluSet();
    	cluSet.setName("Math 158,221 CLU Set");
    	cluSet.setClus(cluList);
    	
    	cluSet = luDao.create(cluSet);
    	return cluSet.getId();
    }

    public static void createReqComponentFields(LuDao luDao, ReqComponent reqComponent, String expectedValue, String operator, String target) {
		List<ReqComponentField> fieldList = new ArrayList<ReqComponentField>();
		ReqComponentField field1 = new ReqComponentField();
		field1.setKey("reqCompFieldType.requiredCount");
		field1.setValue(expectedValue);
		field1.prePersist();
		fieldList.add(field1);
		luDao.create(field1);
		
		ReqComponentField field2 = new ReqComponentField();
		field2.setKey("reqCompFieldType.operator");
		field2.setValue(operator);
		field2.prePersist();
		fieldList.add(field2);
		luDao.create(field2);
				
		ReqComponentField field3 = new ReqComponentField();
		field3.setKey("reqCompFieldType.cluSet");
		field3.setValue(target);
		field3.prePersist();
		fieldList.add(field3);
		luDao.create(field3);
		
		reqComponent.setReqCompField(fieldList);
		reqComponent = luDao.update(reqComponent);
    }

    public static ContextRegistry<Context<ReqComponent>> getReqComponentContextRegistry(LuDao luDao) {
    	ContextRegistry<Context<ReqComponent>> contextRegistry = new ContextRegistry<Context<ReqComponent>>();

    	CourseListContextImpl courseListContext = new CourseListContextImpl();
    	courseListContext.setLuDao(luDao);
    	
    	contextRegistry.add("kuali.reqCompType.courseList.none", courseListContext);
    	contextRegistry.add("kuali.reqCompType.courseList.all", courseListContext);
    	contextRegistry.add("kuali.reqCompType.courseList.nof", courseListContext);
    	contextRegistry.add("kuali.reqCompType.courseList.1of2", courseListContext);

    	GradeConditionCourseListContextImpl gradeConditionCourseListContext = new GradeConditionCourseListContextImpl();
    	gradeConditionCourseListContext.setLuDao(luDao);
    	contextRegistry.add("kuali.reqCompType.grdCondCourseList", gradeConditionCourseListContext);
    	
    	GradeCheckContextImpl gradeCheckContext = new GradeCheckContextImpl();
    	gradeConditionCourseListContext.setLuDao(luDao);
    	contextRegistry.add("kuali.reqCompType.gradecheck", gradeCheckContext);
    	
    	return contextRegistry;
    }

    public static ContextRegistry<Context<LuStatementAnchor>> getStatementContextRegistry(LuDao luDao) {
    	ContextRegistry<Context<LuStatementAnchor>> contextRegistry = new ContextRegistry<Context<LuStatementAnchor>>();

    	HeaderContextImpl headerContext = new HeaderContextImpl();
    	headerContext.setLuDao(luDao);

    	contextRegistry.add("kuali.luStatementType.prereqAcademicReadiness", headerContext);

    	return contextRegistry;
    }
}
