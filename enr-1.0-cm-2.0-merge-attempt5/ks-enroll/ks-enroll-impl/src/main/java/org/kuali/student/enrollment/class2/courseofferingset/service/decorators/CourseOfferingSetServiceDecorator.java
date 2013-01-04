/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseofferingset.service.decorators;

import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import javax.jws.WebParam;

/**
 * Decorator for course offering set
 * @author nwright
 */
public class CourseOfferingSetServiceDecorator implements CourseOfferingSetService {

    private CourseOfferingSetService nextDecorator;

    public CourseOfferingSetService getNextDecorator() {
        return nextDecorator;
    }

    public void setNextDecorator(CourseOfferingSetService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public List<ValidationResultInfo> validateSocRolloverResultItem(String validationType, SocRolloverResultItemInfo socRolloverResultItemInfo, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateSocRolloverResultItem(validationType, socRolloverResultItemInfo, context);
    }

    @Override
    public List<String> searchForSocRolloverResultIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForSocRolloverResultIds(criteria, context);
    }

    @Override
    public List<SocRolloverResultInfo> searchForSocRolloverResults(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForSocRolloverResults(criteria, context);
    }


    @Override
    public List<SocInfo> searchForSocs(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForSocs(criteria, context);
    }

    @Override
    public List<String> searchForSocIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForSocIds(criteria, context);
    }

    @Override
    public List<ValidationResultInfo> validateSocRolloverResult(String validationType, SocRolloverResultInfo socRolloverResultInfo, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateSocRolloverResult(validationType, socRolloverResultInfo, context);
    }

    @Override
    public List<ValidationResultInfo> validateSoc(String validationType, SocInfo socInfo, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateSoc(validationType, socInfo, context);
    }

    @Override
    public SocRolloverResultItemInfo updateSocRolloverResultItem(String socRolloverResultItemId, SocRolloverResultItemInfo socRolloverResultItemInfo, ContextInfo context) throws
            DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateSocRolloverResultItem(socRolloverResultItemId, socRolloverResultItemInfo, context);
    }

    @Override
    public SocRolloverResultInfo updateSocRolloverResult(String socRolloverResultId, SocRolloverResultInfo socRolloverResultInfo, ContextInfo context) throws
            DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateSocRolloverResult(socRolloverResultId, socRolloverResultInfo, context);
    }

    @Override
    public SocRolloverResultInfo updateSocRolloverProgress(String socRolloverResultId, Integer itemsProcessed, ContextInfo context) throws
            DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateSocRolloverProgress(socRolloverResultId, itemsProcessed, context);
    }

    @Override
    public SocInfo updateSoc(String socId, SocInfo socInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateSoc(socId, socInfo, context);
    }

    @Override
    public StatusInfo startScheduleSoc(String socId, List<String> optionKeys, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().startScheduleSoc(socId, optionKeys, context);
    }

    @Override
    public SocInfo rolloverSoc(String sourceSocId, String targetTermId, List<String> optionKeys, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().rolloverSoc(sourceSocId, targetTermId, optionKeys, context);
    }

    @Override
    public SocRolloverResultInfo reverseRollover(String rolloverResultId, List<String> optionKeys, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().reverseRollover(rolloverResultId, optionKeys, context);
    }

    @Override
    public Boolean isCourseOfferingInSoc(String socId, String courseOfferingId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().isCourseOfferingInSoc(socId, courseOfferingId, context);
    }

    @Override
    public List<String> getUnscheduledActivityOfferingIdsBySoc(String socId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getUnscheduledActivityOfferingIdsBySoc(socId, context);
    }

    @Override
    public List<String> getUnpublishedCourseOfferingIdsBySoc(String socId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getUnpublishedCourseOfferingIdsBySoc(socId, context);
    }

    @Override
    public List<String> getUnpublishedActivityOfferingIdsBySoc(String socId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getUnpublishedActivityOfferingIdsBySoc(socId, context);
    }

    @Override
    public List<SocInfo> getSocsByIds(List<String> socIds, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getSocsByIds(socIds, context);
    }

    @Override
    public List<SocRolloverResultInfo> getSocRolloverResultsByIds(List<String> rolloverResultIds, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getSocRolloverResultsByIds(rolloverResultIds, context);
    }

    @Override
    public SocRolloverResultItemInfo getSocRolloverResultItem(String socRolloverResultItemId, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getSocRolloverResultItem(socRolloverResultItemId, context);
    }

    @Override
    public List<String> getSocRolloverResultIdsByTargetSoc(String targetSocId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getSocRolloverResultIdsByTargetSoc(targetSocId, context);
    }

    @Override
    public List<String> getSocRolloverResultIdsBySourceSoc(String sourceSocId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getSocRolloverResultIdsBySourceSoc(sourceSocId, context);
    }

    @Override
    public SocRolloverResultInfo getSocRolloverResult(String rolloverResultId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getSocRolloverResult(rolloverResultId, context);
    }

    @Override
    public List<String> getSocIdsByType(String typeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getSocIdsByType(typeKey, context);
    }


    @Override
    public List<String> getSocIdsByTermAndUnitsContentOwner(String termId, String unitsContentOwnerId, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getSocIdsByTermAndUnitsContentOwner(termId, unitsContentOwnerId, context);
    }

    @Override
    public List<String> getSocIdsByTermAndSubjectArea(String termId, String subjectArea, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getSocIdsByTermAndSubjectArea(termId, subjectArea, context);
    }

    @Override
    public List<String> getSocIdsByTerm(String termId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getSocIdsByTerm(termId, context);
    }

    @Override
    public List<String> getSocIdsByCourseOffering(String courseOfferingId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getSocIdsByCourseOffering(courseOfferingId, context);
    }

    @Override
    public SocInfo getSoc(String socId, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getSoc(socId, context);
    }

    @Override
    public List<SocRolloverResultItemInfo> getSocRolloverResultItemsByResultId(String rolloverResultId, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getSocRolloverResultItemsByResultId(rolloverResultId, context);
    }

    @Override
    public List<SocRolloverResultItemInfo> getSocRolloverResultItemsByResultIdAndTargetCourseOfferingId(String socRolloverResultId, String targetCourseOfferingId, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getSocRolloverResultItemsByResultIdAndTargetCourseOfferingId(socRolloverResultId,
                targetCourseOfferingId, context);
    }

    @Override
    public List<SocRolloverResultItemInfo> getSocRolloverResultItemsByResultIdAndSourceCourseOfferingId(String socRolloverResultId, String sourceCourseOfferingId, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getSocRolloverResultItemsByResultIdAndSourceCourseOfferingId(socRolloverResultId,
                sourceCourseOfferingId, context);
    }

    @Override
    public List<String> getPublishedCourseOfferingIdsBySoc(String socId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getPublishedCourseOfferingIdsBySoc(socId, context);
    }

    @Override
    public List<String> getCourseOfferingIdsWithUnscheduledFinalExamsBySoc(String socId, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseOfferingIdsWithUnscheduledFinalExamsBySoc(socId, context);
    }

    @Override
    public List<String> getCourseOfferingIdsBySoc(String socId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseOfferingIdsBySoc(socId, context);
    }

    @Override
    public StatusInfo deleteSocRolloverResultItem(String socRolloverResultItemId, ContextInfo context) throws
            DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteSocRolloverResultItem(socRolloverResultItemId, context);
    }

    @Override
    public StatusInfo deleteSocRolloverResult(String socRolloverResultId, ContextInfo context) throws DoesNotExistException,
            DependentObjectsExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().deleteSocRolloverResult(socRolloverResultId, context);
    }

    @Override
    public StatusInfo deleteSoc(String socId, ContextInfo context) throws DependentObjectsExistException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteSoc(socId, context);
    }

    @Override
    public Integer deleteCourseOfferingsBySoc(String socId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteCourseOfferingsBySoc(socId, context);
    }

    @Override
    public Integer createSocRolloverResultItems(String socRolloverResultId, String rolloverResultItemTypeKey, List<SocRolloverResultItemInfo> socRolloverResultItemInfos, ContextInfo context) throws
            DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createSocRolloverResultItems(socRolloverResultId, rolloverResultItemTypeKey,
                socRolloverResultItemInfos, context);
    }

    public SocRolloverResultItemInfo createSocRolloverResultItem(String socRolloverResultId, String socRolloverResultItemTypeKey, SocRolloverResultItemInfo socRolloverResultItemInfo, ContextInfo context) throws
            DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createSocRolloverResultItem(socRolloverResultId, socRolloverResultItemTypeKey,
                socRolloverResultItemInfo, context);
    }

    @Override
    public SocRolloverResultInfo createSocRolloverResult(String socRolloverResultTypeKey, SocRolloverResultInfo socRolloverResultInfo, ContextInfo context) throws
            DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createSocRolloverResult(socRolloverResultTypeKey, socRolloverResultInfo, context);
    }

    @Override
    public SocInfo createSoc(String termId, String socTypeKey, SocInfo socInfo, ContextInfo context) throws DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createSoc(termId, socTypeKey, socInfo, context);
    }

    @Override
    public List<SocRolloverResultItemInfo> getSocRolloverResultItemsByIds(List<String> rolloverResultItemIds, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getSocRolloverResultItemsByIds(rolloverResultItemIds, context);
    }

    @Override
    public List<SocRolloverResultInfo> getSocRolloverResultsBySourceAndTargetSocs(String sourceSocId, String targetSocId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator ().getSocRolloverResultsBySourceAndTargetSocs(sourceSocId, targetSocId, context);
    }

	@Override
	public StatusInfo updateSocState(@WebParam(name = "socId") String socId,
			@WebParam(name = "nextStateKey") String nextStateKey,
			@WebParam(name = "contextInfo") ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return getNextDecorator().updateSocState(socId, nextStateKey, contextInfo);
	}

	@Override
	public StatusInfo updateSocRolloverResultState(
			@WebParam(name = "socId") String socId,
			@WebParam(name = "nextStateKey") String nextStateKey,
			@WebParam(name = "contextInfo") ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return getNextDecorator().updateSocRolloverResultState(socId, nextStateKey, contextInfo);
	}

	@Override
	public StatusInfo updateSocRolloverResultItemState(
			@WebParam(name = "socId") String socId,
			@WebParam(name = "nextStateKey") String nextStateKey,
			@WebParam(name = "contextInfo") ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return getNextDecorator().updateSocRolloverResultState(socId, nextStateKey, contextInfo);
	}

    @Override
    public List<SocRolloverResultItemInfo> searchForSocRolloverResultItems(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForSocRolloverResultItems(criteria, context);
    }

    @Override
    public List<String> searchForSocRolloverResultItemIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForSocRolloverResultItemIds(criteria, context);
    }
    
    
    
    
}
