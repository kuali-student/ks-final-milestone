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
