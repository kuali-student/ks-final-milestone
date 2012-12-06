/*
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * 
 */
package org.kuali.student.krms.proposition;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.Term;
import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.krms.KRMSConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Kuali Student Team
 * 
 * Rule Name: "Must have successfully completed <course> between <term1> and <term2>"
 * Proposition Type Key: kuali.krms.proposition.type.success.compl.course.between.terms
 * 
 * Terms: 
 * 
 * kuali.krms.term.type.person.student.specific.id
 * kuali.krms.term.type.course.specific.id
 * kuali.krms.term.type.date.start
 * kuali.krms.term.type.date.end
 * 
 * 
 */
public class CompletedCourseBetweenTermsProposition implements Proposition {

    private static final Logger log = LoggerFactory.getLogger(CompletedCourseBetweenTermsProposition.class);
    
	private Term studentTerm = new Term(KRMSConstants.STUDENT_ID);
	
	private Term courseTerm = new Term(KRMSConstants.COURSE_ID);
	
	private Term startDateTerm = new Term (KRMSConstants.START_DATE);
	
	private Term endDateTerm = new Term (KRMSConstants.END_DATE);
	
	private final AcademicRecordService recordService;
	
	private final AtpService atpService;
	
	

	/**
     * @param recordService
     * @param atpService
     */
    public CompletedCourseBetweenTermsProposition(AtpService atpService, AcademicRecordService recordService) {
        super();
        this.recordService = recordService;
        this.atpService = atpService;
    }



    /* (non-Javadoc)
	 * @see org.kuali.rice.krms.framework.engine.Proposition#evaluate(org.kuali.rice.krms.api.engine.ExecutionEnvironment)
	 */
	@Override
	public PropositionResult evaluate(ExecutionEnvironment environment) {
		
        
		String studentId = environment.resolveTerm(studentTerm, this);
		
		String courseId = environment.resolveTerm(courseTerm, this);
		
		Date startDateRange = environment.resolveTerm(startDateTerm, this);
		
		Date endDateRange = environment.resolveTerm(endDateTerm, this);
		
		ContextInfo contextInfo = new ContextInfo();
		contextInfo.setCurrentDate(new Date());
		contextInfo.setAuthenticatedPrincipalId("test-krms");
		contextInfo.setPrincipalId("test-krms");
		
		boolean exception = false;
		boolean matchedTerm = false;
		
		try {
			List<StudentCourseRecordInfo> completedCourses = recordService.getCompletedCourseRecordsForCourse(studentId, courseId, contextInfo);
			
			// this is the list of time periods that we want to constrain the results by
			List<AtpInfo> atps = atpService.getAtpsByDates(startDateRange, endDateRange, contextInfo);
			
			Set<String>atpNames = new HashSet<String>();
			
			
			for (AtpInfo atpInfo : atps) {
				
				// extract the names to make the comparisons easier.
				atpNames.add(atpInfo.getName());
			}
			
			
			
			for (StudentCourseRecordInfo courseRecordInfo : completedCourses) {
				
				/*
				 * We want to find out if any of the completed courses were completed between the terms given.
				 */
				
				if (atpNames.contains(courseRecordInfo.getTermName())) {
					// we take the first match we find
				    matchedTerm = true;
					break;
				}
					
			}
			
			// intentionally fall through
			
		} catch (DoesNotExistException e) {
		    log.error("exception = ", e);
			exception = true;
		} catch (InvalidParameterException e) {
		    log.error("exception = ", e);
			exception = true;
		} catch (MissingParameterException e) {
		    log.error("exception = ", e);
			exception = true;
		} catch (OperationFailedException e) {
		    log.error("exception = ", e);
			exception = true;
		} catch (PermissionDeniedException e) {
		    log.error("exception = ", e);
			exception = true;
		
		}
		
		if (exception) {
		   
			return new PropositionResult(false);
		}
		else {
		    
		    if (matchedTerm)
		        return new PropositionResult(true);
		    else
		        return new PropositionResult(false);
		}
	}

	

	/* (non-Javadoc)
	 * @see org.kuali.rice.krms.framework.engine.Proposition#getChildren()
	 */
	@Override
	public List<Proposition> getChildren() {
		// no child propositions
		return new ArrayList<Proposition>();
	}

	/* (non-Javadoc)
	 * @see org.kuali.rice.krms.framework.engine.Proposition#isCompound()
	 */
	@Override
	public boolean isCompound() {
		// false because we do not embed other propositions
		return false;
	}

}
