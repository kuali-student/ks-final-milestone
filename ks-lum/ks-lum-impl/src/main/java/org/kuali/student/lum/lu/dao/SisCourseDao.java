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

package edu.umd.ks.cm.ui.util.spring.dao;

import java.util.List;

import org.kuali.student.common.dao.CrudDao;
import org.kuali.student.common.dao.SearchableDao;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.lum.course.dto.CourseInfo;

import edu.umd.ks.cm.ui.util.spring.entity.SisCourse;

/**
 * @Author VG 10/20/11
 * @See https://issues.umd.edu/browse/KSCM-616
 * Used by SisCourseInfoAdvice to update SISCM.UMDCM_CRS table for MF
 */

public interface SisCourseDao extends CrudDao, SearchableDao  {
	public List<SisCourse> getSisCourseByCrsTrmStat(String course, String term, String statusInd);
	public List<SisCourse> getSisCourseByCrsIdStat(String courseId, String statusInd);
	public void updateSisCourseInfo(CourseInfo courseInfo, String statusInd);
	public void updateSisCourseInfoStatement(String courseId, String statusInd);
	public void createSisCourseInfo(CourseInfo courseInfo, String statusInd);
	public void createSisCourseInfoStatement(String courseId, String statusInd);
}
