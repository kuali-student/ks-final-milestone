package org.kuali.student.r2.core.class1.atp.service.decorators;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.class1.atp.dao.MilestoneDao;
import org.kuali.student.r2.core.class1.atp.model.MilestoneEntity;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AtpServiceCalculationDecorator extends AtpServiceDecorator {
    private MilestoneDao milestoneDao;

    public MilestoneDao getMilestoneDao() {
        return milestoneDao;
    }

    public void setMilestoneDao(MilestoneDao milestoneDao) {
        this.milestoneDao = milestoneDao;
    }

    @Override
    public MilestoneInfo calculateMilestone(String milestoneId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        MilestoneInfo milestone = getMilestone(milestoneId, contextInfo);
        if (milestone == null) {
            throw new DoesNotExistException("milestone: " + milestoneId);
        }

        String typeKey = milestone.getTypeKey();
        if (AtpServiceConstants.MILESTONE_FINANCIAL_AID_CENSUS_TYPE_KEY.equals(typeKey)) {
            calculateCensus(milestone, contextInfo);
        }

        return milestone;
    }

    private void calculateCensus(MilestoneInfo census, ContextInfo contextInfo) throws OperationFailedException {
        MilestoneInfo instructionPeriod = null;
        try {
            instructionPeriod = getMilestone(census.getRelativeAnchorMilestoneId(), contextInfo);
            if (!AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY.equals(instructionPeriod.getTypeKey())) {
                throw new OperationFailedException("Census milestone's relative anchor mile is of incorrect type: " + instructionPeriod.getTypeKey());
            }
        } catch (DoesNotExistException e) {
            throw new OperationFailedException("Could not retrieve instruction period for census '" + census.getId() + "'");
        } catch (InvalidParameterException e) {
            throw new OperationFailedException("Could not retrieve instruction period for census '" + census.getId() + "'");
        } catch (MissingParameterException e) {
            throw new OperationFailedException("Could not retrieve instruction period for census '" + census.getId() + "'");
        } catch (OperationFailedException e) {
            throw new OperationFailedException("Could not retrieve instruction period for census '" + census.getId() + "'");
        } catch (PermissionDeniedException e) {
            throw new OperationFailedException("Could not retrieve instruction period for census '" + census.getId() + "'");
        }
        Date censusStartTime = getCensus(instructionPeriod);
        Calendar cal = Calendar.getInstance();
        cal.setTime(censusStartTime);
        cal.add(Calendar.DATE, 1);
        Date censusEndTime = cal.getTime();

        census.setStartDate(censusStartTime);
        census.setEndDate(censusEndTime);
    }

    private Date getCensus(MilestoneInfo instructionPeriod) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(instructionPeriod.getStartDate());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        cal.add(Calendar.DATE, 7);
        return cal.getTime();
    }

    private boolean timespanOccursDuringTimespan(Date startTime1, Date endTime1, Date startTime2, Date endTime2) {
        if (!startTime1.before(startTime2) && startTime1.before(endTime2)) {
            // milestone starts as/after ATP starts (fully contained or extends past ATP end)
            return true;
        }
        if (!endTime1.before(startTime2) && !endTime1.after(endTime2)) {
            // milestone ends while ATP is in progress
            return true;
        }
        if (!startTime1.after(startTime2) && !endTime1.before(endTime2)) {
            // milestone encompasses ATP
            return true;
        }
        return false;
    }

}
