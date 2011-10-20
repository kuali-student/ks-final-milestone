/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package edu.umd.ks.cm.ui.util.spring.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.kuali.student.common.dao.impl.AbstractSearchableCrudDaoImpl;
import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.lum.course.service.assembler.CourseAssemblerConstants;
import org.springframework.transaction.annotation.Transactional;

import edu.umd.ks.cm.ui.util.spring.dao.SisCourseDao;
import edu.umd.ks.cm.ui.util.spring.entity.SisCourse;

/**
 * @Author VG 10/20/11
 * @See https://issues.umd.edu/browse/KSCM-616
 * Used by SisCourseInfoAdvice to update SISCM.UMDCM_CRS table for MF
 */

public class SisCourseDaoImpl extends AbstractSearchableCrudDaoImpl implements SisCourseDao {

	private CourseService courseService;
	private StatementService statementService;
	final static Logger LOG = Logger.getLogger(SisCourseDaoImpl.class);
	
	@PersistenceContext(unitName = "Sis")
	@Override
	public void setEm(EntityManager em) {
		super.setEm(em);
	}

	public List<SisCourse> getSisCourseByCrsTrmStat(String course, String term, String statusInd) {
		Query query = em.createNamedQuery("SisCourse.findSisCourseByCrsTrmStat");
		query.setParameter("course", course);
		query.setParameter("term", term);
		query.setParameter("statusInd", statusInd);
		@SuppressWarnings("unchecked")
		List<SisCourse> resultList = query.getResultList();
		return resultList;
	}

	public List<SisCourse> getSisCourseByCrsIdStat(String courseId,	String statusInd) {
		Query query = em.createNamedQuery("SisCourse.findSisCourseByCrsIdStat");
		query.setParameter("courseId", courseId);
		query.setParameter("statusInd", statusInd);
		@SuppressWarnings("unchecked")
		List<SisCourse> resultList = query.getResultList();
		return resultList;
	}

	public void updateSisCourseInfo(CourseInfo courseInfo, String statusInd) {
		try {
			Query query = em.createNamedQuery("SisCourse.updateSisCourseInfo");
		
			// end term
			String termEnd = getTerm(courseInfo.getExpirationDate());
			query.setParameter("termEnd", termEnd);

			// credits
			String[] credits = getCredits(courseInfo.getCreditOptions());
			int minCredit = 0;
			if (credits[0] != null && credits[0].length() > 0)
				minCredit =  (int)Double.parseDouble(credits[0]);
			query.setParameter("minCredit", minCredit);
			int maxCredit = 0;
			if (credits[1] != null && credits[1].length() > 0)
				maxCredit = (int)Double.parseDouble(credits[1]);
			query.setParameter("maxCredit", maxCredit);
		
			// grading methods
			String gradeMethod = getGradeMethods(courseInfo.getGradingOptions());
			query.setParameter("gradeMethod", gradeMethod);

			// reqs
			List<StatementTreeViewInfo> statements = courseService.getCourseStatements(courseInfo.getId(), null, null); 
			String courseReqs = getStatements(statements);
			query.setParameter("courseReqs", courseReqs);
		
			query.setParameter("courseTitle", courseInfo.getCourseTitle());
			query.setParameter("transcrTitle", courseInfo.getTranscriptTitle());
			query.setParameter("courseDesc", courseInfo.getDescr().getPlain());

			// userId
			query.setParameter("userId", SecurityUtils.getCurrentUserId());		
		
			// params for the conditions
			query.setParameter("courseId", courseInfo.getId());
			query.setParameter("statusInd", statusInd);
		
			query.executeUpdate();
		} catch (Exception e) {
			LOG.error("Error updateSisCourseInfo with courseId: " + courseInfo.getId(), e);
		}
	}

	public void updateSisCourseInfoStatement(String courseId, String statusInd) {
		try {
			Query query = em.createNamedQuery("SisCourse.updateSisCourseInfoStatement");

			// reqs
			List<StatementTreeViewInfo> statements = courseService.getCourseStatements(courseId, null, null); 
			String courseReqs = getStatements(statements);
			query.setParameter("courseReqs", courseReqs);
			
			// userId
			query.setParameter("userId", SecurityUtils.getCurrentUserId());		

			// params for the conditions
			query.setParameter("courseId", courseId);
			query.setParameter("statusInd", statusInd);
		
			query.executeUpdate();
		} catch (Exception e) {
			LOG.error("Error updateSisCourseInfoStatement with courseId: " + courseId, e);
		}
	}

	@Transactional 
	public void createSisCourseInfo(CourseInfo courseInfo, String statusInd) {
		try {
		    SisCourse sisCourse = new SisCourse();
			
		    sisCourse.setCourse(courseInfo.getCode());
		    
			// term
			String term = getTerm(courseInfo.getEffectiveDate());
			sisCourse.setTerm(term);			
			
			// end term
			String termEnd = getTerm(courseInfo.getExpirationDate());
			sisCourse.setTermEnd(termEnd);

			// credits
			String[] credits = getCredits(courseInfo.getCreditOptions());
			int minCredit = 0;
			if (credits[0] != null && credits[0].length() > 0)
				minCredit =  (int)Double.parseDouble(credits[0]);
			sisCourse.setMinCredit(minCredit);
			int maxCredit = 0;
			if (credits[1] != null && credits[1].length() > 0)
				maxCredit = (int)Double.parseDouble(credits[1]);
			sisCourse.setMaxCredit(maxCredit);
			
			// grading methods
			String gradeMethod = getGradeMethods(courseInfo.getGradingOptions());
			sisCourse.setGradeMethod(gradeMethod);

			sisCourse.setCourseTitle(courseInfo.getCourseTitle());
			sisCourse.setTranscrTitle(courseInfo.getTranscriptTitle());
			sisCourse.setCourseDesc(courseInfo.getDescr().getPlain());

			// reqs
			List<StatementTreeViewInfo> statements = courseService.getCourseStatements(courseInfo.getId(), null, null); 
			String courseReqs = getStatements(statements);
			sisCourse.setCourseReqs(courseReqs);
		
			sisCourse.setStatusInd(statusInd);
			java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
			sisCourse.setModifyDate(sqlDate);
			sisCourse.setUserId(SecurityUtils.getCurrentUserId());				
			sisCourse.setCourseId(courseInfo.getId());
		
		    em.persist(sisCourse);
		} catch (Exception e) {
			LOG.error("Error updateSisCourseInfo with courseId: " + courseInfo.getId(), e);
		}
	}

	@Transactional 
	public void createSisCourseInfoStatement(String courseId, String statusInd) {
		try {
			CourseInfo courseInfo = courseService.getCourse(courseId);
		    SisCourse sisCourse = new SisCourse();
			
		    sisCourse.setCourse(courseInfo.getCode());
		    
			// term
			String term = getTerm(courseInfo.getEffectiveDate());
			sisCourse.setTerm(term);			
			
			// end term
			String termEnd = getTerm(courseInfo.getExpirationDate());
			sisCourse.setTermEnd(termEnd);

			// credits
			String[] credits = getCredits(courseInfo.getCreditOptions());
			int minCredit = 0;
			if (credits[0] != null && credits[0].length() > 0)
				minCredit =  (int)Double.parseDouble(credits[0]);
			sisCourse.setMinCredit(minCredit);
			int maxCredit = 0;
			if (credits[1] != null && credits[1].length() > 0)
				maxCredit = (int)Double.parseDouble(credits[1]);
			sisCourse.setMaxCredit(maxCredit);
			
			// grading methods
			String gradeMethod = getGradeMethods(courseInfo.getGradingOptions());
			sisCourse.setGradeMethod(gradeMethod);

			sisCourse.setCourseTitle(courseInfo.getCourseTitle());
			sisCourse.setTranscrTitle(courseInfo.getTranscriptTitle());
			sisCourse.setCourseDesc(courseInfo.getDescr().getPlain());

			// reqs
			List<StatementTreeViewInfo> statements = courseService.getCourseStatements(courseId, null, null); 
			String courseReqs = getStatements(statements);
			sisCourse.setCourseReqs(courseReqs);
		
			sisCourse.setStatusInd(statusInd);
			java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
			sisCourse.setModifyDate(sqlDate);
			sisCourse.setUserId(SecurityUtils.getCurrentUserId());				
			sisCourse.setCourseId(courseId);
		
		    em.persist(sisCourse);
		} catch (Exception e) {
			LOG.error("Error updateSisCourseInfoStatement with courseId: " + courseId, e);
		}
	}

	private String getTerm(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");				
		String term = null;
		if (date != null)
			term = df.format(date);
		
		return term;		
	}
	
	private String[] getCredits(List<ResultComponentInfo> creditOptions) {
		String minCredits = "";
		String maxCredits = "";
		for (ResultComponentInfo creditInfo:creditOptions) {

			if (creditInfo.getType().equals(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_FIXED)) {
				minCredits = creditInfo.getAttributes().get(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_FIXED_CREDIT_VALUE);
				maxCredits = minCredits;
			} else {
				minCredits = creditInfo.getAttributes().get(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_MIN_CREDIT_VALUE);
				maxCredits = creditInfo.getAttributes().get(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_MAX_CREDIT_VALUE);
			}
		}
		String[] credits = {minCredits, maxCredits};
		return credits;
	}
	
	private String getGradeMethods(List<String> gradMeths) {
		String gradeMethod = "";
		if (!gradMeths.isEmpty()) {
			int j = 0;
			for (String grade : gradMeths)
			{
				if (grade.equals("kuali.resultComponent.grade.letter"))
					grade = "R";
				if (grade.equals("kuali.resultComponent.grade.passFail"))
					grade = "P";
				if (grade.equals("kuali.resultComponent.grade.satisfactory"))
					grade = "S";
				if (grade.equals("kuali.resultComponent.grade.audit"))
					grade = "A";
				if (grade.equals("kuali.resultComponent.grade.completedNotation"))
					grade = "N";
			
				if (j == 0)
					gradeMethod = grade;
				else
					gradeMethod = gradeMethod + "," + grade;
				j++;
			}
		}
		return gradeMethod;
	}	

	private String getStatements(List<StatementTreeViewInfo> statements) throws Exception  {
		// Requirements
		String courseReqs = "";
		int j = 0;
		for(StatementTreeViewInfo statement:statements){					
			String nl = statementService.getNaturalLanguageForStatement(statement.getId(), "KUALI.RULE", "en");
			List<ReqComponentInfo> reqComponents = statement.getReqComponents();
			if (statement.getType().equals("kuali.statement.type.course.academicReadiness.studentEligibilityPrereq")) {
				if (reqComponents.size() > 1)
					nl = "Prerequisites: " + nl;
				else
					nl = "Prerequisite: " + nl;
			} else if (statement.getType().equals("kuali.statement.type.course.academicReadiness.coreq")) {
				if (reqComponents.size() > 1)
					nl = "Corequisites: " + nl;
				else
					nl = "Corequisite: " + nl;
			} else if (statement.getType().equals("kuali.statement.type.course.recommendedPreparation"))
				nl = "Preparation: " + nl; 
			else if (statement.getType().equals("kuali.statement.type.course.academicReadiness.antireq")) {
				if (reqComponents.size() > 1)
					nl = "Antirequisites: " + nl; 
				else
					nl = "Antirequisite: " + nl;
			} else if (statement.getType().equals("kuali.statement.type.course.credit.restriction")) {
				if (reqComponents.size() > 1)
					nl = "Restrictions: " + nl;
				else
					nl = "Restriction: " + nl;
			} else if (statement.getType().equals("kuali.statement.type.course.credit.repeatable"))
				nl = "Repeatable: " + nl;
			
			if (j == 0)
				courseReqs = nl;
			else
				courseReqs = courseReqs + ";" + nl;
			j++;
		}		

		return courseReqs;
	}	
		
	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}
	
	public void setStatementService(StatementService statementService) {
		this.statementService = statementService;
	}

}
