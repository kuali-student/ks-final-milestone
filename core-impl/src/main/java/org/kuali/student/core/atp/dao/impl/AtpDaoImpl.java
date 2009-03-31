package org.kuali.student.core.atp.dao.impl;

import java.util.Date;
import java.util.List;

import org.kuali.student.core.atp.dao.AtpDao;
import org.kuali.student.core.atp.entity.Atp;
import org.kuali.student.core.atp.entity.DateRange;
import org.kuali.student.core.atp.entity.DateRangeType;
import org.kuali.student.core.atp.entity.Milestone;
import org.kuali.student.core.atp.entity.MilestoneType;
import org.kuali.student.core.dao.impl.AbstractCrudDaoImpl;

public class AtpDaoImpl extends AbstractCrudDaoImpl implements AtpDao {

	@Override
	public List<Atp> findAtpsByAtpType(String atpTypeKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Atp> findAtpsByDate(Date searchDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Atp> findAtpsByDates(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DateRangeType> findDateRangeTypesForAtpType(String atpTypeKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DateRange> findDateRangesByAtp(String atpKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DateRange> findDateRangesByDate(Date searchDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MilestoneType> findMilestoneTypesForAtpType(String atpTypeKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Milestone> findMilestonesByAtp(String atpKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Milestone> findMilestonesByDates(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Milestone> findMilestonesByDatesAndType(
			String milestoneTypeKey, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
