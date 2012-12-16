package org.kuali.student.myplan.audit.service;

import java.util.Date;
import java.util.List;

import org.kuali.student.myplan.academicplan.dto.LearningPlanInfo;
import org.kuali.student.myplan.audit.dto.AuditProgramInfo;
import org.kuali.student.myplan.audit.dto.AuditReportInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

public class DegreeAuditServiceImpl implements DegreeAuditService {

	@Override
	public AuditReportInfo runAudit(String studentId, String programId,
			String auditTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		throw new UnsupportedOperationException("TODO");
	}

	@Override
	public String runAuditAsync(String studentId, String programId,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException("TODO");
	}

	@Override
	public StatusInfo getAuditRunStatus(String auditId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException("TODO");
	}

	@Override
	public AuditReportInfo getAuditReport(String auditId, String auditTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		throw new UnsupportedOperationException("TODO");
	}

	@Override
	public List<AuditReportInfo> getAuditsForStudentInDateRange(
			String studentId, Date startDate, Date endDate, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		throw new UnsupportedOperationException("TODO");
	}

	@Override
	public AuditReportInfo runWhatIfAudit(String studentId, String programId,
			String auditTypeKey, LearningPlanInfo academicPlan,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException("TODO");
	}

	@Override
	public String runWhatIfAuditAsync(String studentId, String programId,
			LearningPlanInfo academicPlan, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		throw new UnsupportedOperationException("TODO");
	}

	@Override
	public AuditReportInfo runEmptyAudit(String programId, String auditTypeKey,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException("TODO");
	}

	@Override
	public String runEmptyAuditAsync(String programId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		throw new UnsupportedOperationException("TODO");
	}

	@Override
	public List<AuditProgramInfo> getAuditPrograms(ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		throw new UnsupportedOperationException("TODO");
	}

	@Override
	public String getAuditStatus(String studentId, String programId,
			String recentAuditId) throws InvalidParameterException,
			MissingParameterException, OperationFailedException {
		throw new UnsupportedOperationException("TODO");
	}

}
