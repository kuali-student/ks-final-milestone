/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.core.atp.dao;

import java.util.Date;
import java.util.List;

import org.kuali.student.core.atp.entity.Atp;
import org.kuali.student.core.atp.entity.DateRange;
import org.kuali.student.core.atp.entity.DateRangeType;
import org.kuali.student.core.atp.entity.Milestone;
import org.kuali.student.core.atp.entity.MilestoneType;
import org.kuali.student.core.dao.CrudDao;


public interface AtpDao extends CrudDao {

	List<Atp> findAtpsByAtpType(String atpTypeKey);

	List<Atp> findAtpsByDate(Date searchDate);

	List<Atp> findAtpsByDates(Date startDate, Date endDate);

	List<DateRangeType> findDateRangeTypesForAtpType(String atpTypeKey);

	List<DateRange> findDateRangesByAtp(String atpKey);

	List<DateRange> findDateRangesByDate(Date searchDate);

	List<MilestoneType> findMilestoneTypesForAtpType(String atpTypeKey);

	List<Milestone> findMilestonesByAtp(String atpKey);

	List<Milestone> findMilestonesByDates(Date startDate, Date endDate);

	List<Milestone> findMilestonesByDatesAndType(String milestoneTypeKey,
			Date startDate, Date endDate);

}
