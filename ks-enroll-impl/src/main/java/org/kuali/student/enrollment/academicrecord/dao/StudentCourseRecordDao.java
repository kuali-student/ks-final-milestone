/*
 * Copyright 2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.academicrecord.dao;


import java.util.List;
import javax.persistence.Query;

import org.kuali.student.enrollment.academicrecord.model.StudentCourseRecordEntity;
import org.kuali.student.r2.common.dao.GenericEntityDao;

public class StudentCourseRecordDao extends GenericEntityDao<StudentCourseRecordEntity> {

	public List<StudentCourseRecordEntity> getAttemptedCourseRecordsForTerm(String personId, String termId) {
		Query query = em.createNamedQuery("StudentCourseRecordEntity.getAttemptedCourseRecordsForTerm");
		query.setParameter("personId", personId);
		query.setParameter("termId", termId);
		return query.getResultList();
	}

    public List<StudentCourseRecordEntity> getCompletedCourseRecords(String personId) {
        Query query = em.createNamedQuery("StudentCourseRecordEntity.getCompletedCourseRecords");
        query.setParameter("personId", personId);
        return query.getResultList();
    }

    public List<StudentCourseRecordEntity> getCompletedCourseRecordsForCourse(String personId, String courseId) {
        Query query = em.createNamedQuery("StudentCourseRecordEntity.getCompletedCourseRecordsForCourse");
        query.setParameter("personId", personId);
        query.setParameter("courseId", courseId);
        return query.getResultList();
    }

	public List<StudentCourseRecordEntity> getCompletedCourseRecordsForTerm(String personId, String termId) {
		Query query = em.createNamedQuery("StudentCourseRecordEntity.getCompletedCourseRecordsForTerm");
        query.setParameter("personId", personId);
        query.setParameter("termId", termId);
		return query.getResultList();
	}

	public List<String> getIdsByType(String typeKey) {
		Query query = em.createNamedQuery("StudentCourseRecordEntity.getIdsByType");
		query.setParameter("typeKey", typeKey);
		return query.getResultList();
	}

	public List<StudentCourseRecordEntity> getForCourse(String personId, String courseId) {
        Query query = em.createNamedQuery("StudentCourseRecordEntity.getForCourse");
        query.setParameter("personId", personId);
        query.setParameter("courseId", courseId);
		return query.getResultList();
	}

	public List<StudentCourseRecordEntity> getForCourses(String personId, List<String> courseIds) {
        Query query = em.createNamedQuery("StudentCourseRecordEntity.getForCourses");
        query.setParameter("personId", personId);
        query.setParameter("courseIds", courseIds);
        return query.getResultList();
	}

}

