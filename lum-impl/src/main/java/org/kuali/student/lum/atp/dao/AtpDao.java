package org.kuali.student.lum.atp.dao;

import java.util.Date;
import java.util.List;

import org.kuali.student.core.dao.CrudDao;
import org.kuali.student.lum.atp.entity.Atp;
import org.kuali.student.lum.atp.entity.DateRange;
import org.kuali.student.lum.atp.entity.DateRangeType;
import org.kuali.student.lum.atp.entity.Milestone;
import org.kuali.student.lum.atp.entity.MilestoneType;

public interface AtpDao extends CrudDao {

	public List<Atp> findAtpsByAtpType(String atpTypeKey);

	public List<Atp> findAtpsByDate(Date searchDate);

	public List<Atp> findAtpsByDates(Date startDate, Date endDate);

	public List<DateRange> findDateRangesByAtp(String atpKey);

	public List<DateRange> findDateRangesByDate(Date searchDate);

	public List<MilestoneType> findMilestoneTypesForAtpType(String atpTypeKey);

	public List<DateRangeType> findDateRangeTypesForAtpType(String atpTypeKey);

	public List<Milestone> findMilestonesByAtp(String atpKey);

	public List<Milestone> findMilestonesByDates(Date startDate, Date endDate);

	public List<Milestone> findMilestonesByDatesAndType(
			String milestoneTypeKey, Date startDate, Date endDate);

}
