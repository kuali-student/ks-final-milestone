package org.kuali.student.lum.atp.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import org.kuali.student.lum.atp.dto.AtpTypeInfo;
import org.kuali.student.lum.atp.dto.DateRangeInfo;
import org.kuali.student.lum.atp.dto.DateRangeTypeInfo;
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

	public static final String atp_2009FallSemester = "atp.2009FallSemester";
	
	@Test
	public void TestFinds() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException{
		client.fetchMilestoneType(milestoneType_lastDateToDrop);
		
		List<AtpTypeInfo> atpTypes = client.findAtpTypes();
		assertEquals(2,atpTypes.size());
		
		List<DateRangeTypeInfo> dateRangeTypes = client.findDateRangeTypesForAtpType(atpType_fallSemester);
		assertEquals(1,dateRangeTypes.size());
		
		Calendar cal = Calendar.getInstance();
		cal.set(2009, 9, 1);
		List<AtpInfo> atps = client.findAtpsByDate(cal.getTime());
		assertEquals(1,atps.size());
		assertEquals("atp.2009FallSemester", atps.get(0).getKey());
		
		
		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		startCal.set(2008, 1, 1);
		endCal.set(2010, 1, 1);
		atps = client.findAtpsByDates(startCal.getTime(),endCal.getTime());
		assertEquals(2,atps.size());

	}
	
	@Test
	public void TestCreateUpdateDelete(){
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
		
		//Update Atp
		createdAtp.setDesc("Updated Atp for the Fall 2008 Semester");
		try {
			assertEquals("0",createdAtp.getMetaInfo().getVersionInd());
			AtpInfo updatedAtp = client.updateAtp(atp_fall2008Semester, createdAtp);
			assertEquals("1",updatedAtp.getMetaInfo().getVersionInd());
			assertEquals("Updated Atp for the Fall 2008 Semester", updatedAtp.getDesc());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		//now try to update again with the same version
		try {
			client.updateAtp(atp_fall2008Semester, createdAtp);
			fail();
		} catch (Exception e) {
			//This exception should be thrown
			e.printStackTrace();
		}
		
		//Update Date Range
		createdDateRange.setDesc("Updated DateRange for the Finals date range Fall 2008 Semester");
		try {
			assertEquals("0",createdDateRange.getMetaInfo().getVersionInd());
			DateRangeInfo updatedDateRange = client.updateDateRange(dateRange_finalsFall2008, createdDateRange);
			assertEquals("1",updatedDateRange.getMetaInfo().getVersionInd());
			assertEquals("Updated DateRange for the Finals date range Fall 2008 Semester", updatedDateRange.getDesc());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		//Updating with the old version again should fail
		try {
			client.updateDateRange(dateRange_finalsFall2008, createdDateRange);
			fail();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Update Milestone
		createdMilestone.setDesc("Updated Milestone for fall 2008 semester last day to drop");
		try {
			assertEquals("0",createdMilestone.getMetaInfo().getVersionInd());
			MilestoneInfo updatedMilestone = client.updateMilestone(milestone_lastDateToDropFall2008, createdMilestone);
			assertEquals("1",updatedMilestone.getMetaInfo().getVersionInd());
			assertEquals("Updated Milestone for fall 2008 semester last day to drop", updatedMilestone.getDesc());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Try to update again should fail
		try {
			client.updateMilestone(milestone_lastDateToDropFall2008, createdMilestone);
			fail();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Do some deletes that should cascade
		try {
			client.deleteAtp(atp_fall2008Semester);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
}
