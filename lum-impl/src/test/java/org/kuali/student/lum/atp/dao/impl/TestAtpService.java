package org.kuali.student.lum.atp.dao.impl;

import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.atp.dto.AtpInfo;
import org.kuali.student.lum.atp.dto.DateRangeInfo;
import org.kuali.student.lum.atp.dto.MilestoneInfo;
import org.kuali.student.lum.atp.service.AtpService;

@Daos( { @Dao(value = "org.kuali.student.lum.atp.dao.impl.AtpDaoImpl", testDataFile = "classpath:test-beans.xml") })
@PersistenceFileLocation("classpath:META-INF/atp-persistence.xml")
public class TestAtpService extends AbstractServiceTest {
	@Client(value = "org.kuali.student.lum.atp.service.impl.AtpServiceImpl", port = "8181")
	public AtpService client;

	public static final String atpType_fallSemester = "atp.atpType.fallSemester";
	public static final String milestoneType_lastDateToDrop = "atp.milestoneType.lastDateToDrop";
	public static final String dateRangeType_finals = "atp.dateRangeType.finals";
	public static final String atpAttribute_notes = "atp.attribute.notes";
	public static final String dateRangeAttribute_notes = "atp.dateRangeAttribute.notes";
	public static final String milestoneAttribute_notes = "atp.milestoneAttribute.notes";
	public static final String atp_fall2008Semester = "atp.fall2008Semester";
	public static final String milestone_lastDateToDropFall2008 = "atp.milestone.lastDateToDropFall2008";
	public static final String dateRange_finalsFall2008 = "atp.dateRange.finalsFall2008";

	@Test
	public void TestFinds() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException{
		client.fetchMilestoneType(milestoneType_lastDateToDrop);
		client.findAtpTypes();
	}
	
	@Test
	public void TestNothing(){
		//Make an ATP
		AtpInfo atpInfo = new AtpInfo();
		atpInfo.setDesc("Atp for fall 2008 semester");
		atpInfo.setName("Fall 2008 Semester");
		atpInfo.setEffectiveDate(new Date());
		atpInfo.setExpirationDate(new Date());
		atpInfo.setState("new");
		
		atpInfo.getAttributes().put(atpAttribute_notes, "Notes for the Fall 2008 Semester");
		
		AtpInfo createdAtp=null;
		try {
			createdAtp = client.createAtp(atpType_fallSemester, atp_fall2008Semester, atpInfo);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		//Make a DateRange
		DateRangeInfo dateRangeInfo=new DateRangeInfo();
		dateRangeInfo.setDesc("Date Range for fall 2008 semester finals");
		dateRangeInfo.setName("Finals Fall 2008 Semester");
		dateRangeInfo.setStartDate(new Date());
		dateRangeInfo.setEndDate(new Date());
		dateRangeInfo.setState("new");
		dateRangeInfo.setAtpKey(atp_fall2008Semester);
		dateRangeInfo.setType(dateRangeType_finals);

		dateRangeInfo.getAttributes().put(dateRangeAttribute_notes, "Notes for the Finals date range Fall 2008 Semester");
		
		DateRangeInfo createdDateRange=null;
		try {
			createdDateRange = client.addDateRange(atp_fall2008Semester, dateRange_finalsFall2008, dateRangeInfo);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} 
		
		//Make a Milestone
		MilestoneInfo milestoneInfo=new MilestoneInfo();
		milestoneInfo.setDesc("Milestone for fall 2008 semester last day to drop");
		milestoneInfo.setName("Last Day to Drop Fall 2008 Semester");
		milestoneInfo.setMilestoneDate(new Date());
		milestoneInfo.setState("new");
		milestoneInfo.setAtpKey(atp_fall2008Semester);
		milestoneInfo.setType(milestoneType_lastDateToDrop);

		milestoneInfo.getAttributes().put(milestoneAttribute_notes, "Notes for the Last Day to Drop Fall 2008 Semester");
		
		MilestoneInfo createdMilestone=null;
		try {
			createdMilestone = client.addMilestone(atp_fall2008Semester, milestone_lastDateToDropFall2008, milestoneInfo);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} 
		
		//Do some updates
		createdAtp.setDesc("Updated Atp for the Fall 2008 Semester");
		try {
			client.updateAtp(atp_fall2008Semester, createdAtp);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		createdDateRange.setDesc("Updated DateRange for the Finals date range Fall 2008 Semester");
		try {
			client.updateDateRange(dateRange_finalsFall2008, createdDateRange);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		createdMilestone.setDesc("Updated Milestone for fall 2008 semester last day to drop");
		try {
			client.updateMilestone(milestone_lastDateToDropFall2008, createdMilestone);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		//Do some deletes
		try {
			client.deleteAtp(atp_fall2008Semester);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
