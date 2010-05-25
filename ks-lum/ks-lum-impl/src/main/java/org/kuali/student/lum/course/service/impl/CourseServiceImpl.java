package org.kuali.student.lum.course.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.atp.service.AtpService;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.lum.course.dto.ActivityInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.course.service.assembler.BaseDTOAssemblyNode;
import org.kuali.student.lum.course.service.assembler.CourseAssembler;
import org.kuali.student.lum.course.service.assembler.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.lum.lo.service.LearningObjectiveService;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;

public class CourseServiceImpl implements CourseService {
	final static Logger LOG = Logger.getLogger(CourseServiceImpl.class);
	
	private LuService luService;
	private StatementService statementService;
	private LearningObjectiveService loService;
	private OrganizationService orgService;
	private AtpService atpService;
	
	private CourseAssembler courseAssembler;
	
	private CourseServiceMethodInvoker courseServiceMethodInvoker;
	
	public LuService getLuService() {
		return luService;
	}

	public void setLuService(LuService luService) {
		this.luService = luService;
	}

	public StatementService getStatementService() {
		return statementService;
	}

	public void setStatementService(StatementService statementService) {
		this.statementService = statementService;
	}

	public LearningObjectiveService getLoService() {
		return loService;
	}

	public void setLoService(LearningObjectiveService loService) {
		this.loService = loService;
	}

	public OrganizationService getOrgService() {
		return orgService;
	}

	public void setOrgService(OrganizationService orgService) {
		this.orgService = orgService;
	}

	public AtpService getAtpService() {
		return atpService;
	}

	public void setAtpService(AtpService atpService) {
		this.atpService = atpService;
	}

	@Override
	public CourseInfo createCourse(CourseInfo courseInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		try {
			BaseDTOAssemblyNode<CourseInfo,CluInfo> results = courseAssembler.disassemble(courseInfo, NodeOperation.CREATE);
			
			try {
				//Use the results to make the appropriate service calls here
				courseServiceMethodInvoker.invokeServiceCalls(results);
			} catch (Exception e) {
				LOG.error("Error creating course",e);
				throw new OperationFailedException("Error creating course");
			}
		
			return results.getBusinessDTORef();
			
		} catch (AssemblyException e) {
			LOG.error("Error disassembling course",e);
			throw new OperationFailedException("Error disassembling course");
		}
	}

	@Override
	public StatusInfo deleteCourse(String courseId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		
		try {
			CourseInfo course = getCourse(courseId);
			
			BaseDTOAssemblyNode<CourseInfo,CluInfo> results = courseAssembler.disassemble(course, NodeOperation.DELETE);
			
			try {
				//Use the results to make the appropriate service calls here
				courseServiceMethodInvoker.invokeServiceCalls(results);
			} catch (Exception e) {
				LOG.error("Error creating course",e);
				throw new OperationFailedException("Error creating course");
			}
		
			StatusInfo status = new StatusInfo();
			status.setSuccess(true);			
			return status;
			
		} catch (AssemblyException e) {
			LOG.error("Error disassembling course",e);
			throw new OperationFailedException("Error disassembling course");
		}
	}

	@Override
	public CourseInfo getCourse(String courseId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		
		CluInfo clu = luService.getClu(courseId);
		
		CourseInfo course;
		try {
			course = courseAssembler.assemble(clu, null, false);
		} catch (AssemblyException e) {
			LOG.error("Error assembling course",e);
			throw new OperationFailedException("Error assembling course");
		} 
		
		return course;
	
	}

	@Override
	public List<ActivityInfo> getCourseActivities(String formatId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new UnsupportedOperationException("GetCourseActivities");
	}

	@Override
	public List<FormatInfo> getCourseFormats(String courseId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new UnsupportedOperationException("GetCourseFormats");
	}

	@Override
	public List<LoDisplayInfo> getCourseLos(String courseId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new UnsupportedOperationException("GetCourseLos");
	}

	@Override
	public List<StatementTreeViewInfo> getCourseStatements(String courseId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new UnsupportedOperationException("GetCourseStatements");
	}

	@Override
	public CourseInfo updateCourse(CourseInfo courseInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			VersionMismatchException, OperationFailedException,
			PermissionDeniedException {
		try {
			BaseDTOAssemblyNode<CourseInfo,CluInfo> results = courseAssembler.disassemble(courseInfo, NodeOperation.UPDATE);
			
			try {
				//Use the results to make the appropriate service calls here
				courseServiceMethodInvoker.invokeServiceCalls(results);
			} catch (Exception e) {
				LOG.error("Error creating course",e);
				throw new OperationFailedException("Error creating course");
			}
		
			return results.getBusinessDTORef();
			
		} catch (AssemblyException e) {
			LOG.error("Error disassembling course",e);
			throw new OperationFailedException("Error disassembling course");
		}
	}

	@Override
	public List<LoDisplayInfo> updateCourseLos(String courseId,
			List<LoDisplayInfo> loDisplayInfoList)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new UnsupportedOperationException("updateCourseLos");
	}

	@Override
	public List<StatementTreeViewInfo> updateCourseStatements(String courseId,
			List<StatementTreeViewInfo> statementTreeViewInfoList)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new UnsupportedOperationException("updateCourseStatements");
	}

	public CourseAssembler getCourseAssembler() {
		return courseAssembler;
	}

	public void setCourseAssembler(CourseAssembler courseAssembler) {
		this.courseAssembler = courseAssembler;
	}

	public CourseServiceMethodInvoker getCourseServiceMethodInvoker() {
		return courseServiceMethodInvoker;
	}

	public void setCourseServiceMethodInvoker(
			CourseServiceMethodInvoker courseServiceMethodInvoker) {
		this.courseServiceMethodInvoker = courseServiceMethodInvoker;
	}
	

}
