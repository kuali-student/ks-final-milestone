package org.kuali.student.enrollment.class2.exam.service.decorators;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.CourseOfferingServiceDecorator;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.enrollment.exam.dto.ExamInfo;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.infc.HoldsValidator;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.class1.search.ActivityOfferingSearchServiceImpl;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.class1.util.ValidationUtils;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExamServiceValidationDecorator
        extends ExamServiceDecorator
        implements HoldsValidator {

    private DataDictionaryValidator validator;

    @Override
    public DataDictionaryValidator getValidator() {
        return validator;
    }

    @Override
    public void setValidator(DataDictionaryValidator validator) {
        this.validator = validator;
    }

    @Override
    public List<ValidationResultInfo> validateExam(String validationTypeKey, String examTypeKey, ExamInfo examInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(validationTypeKey, "validationTypeKey");
        checkForMissingParameter(examTypeKey, "examTypeKey");
        checkForMissingParameter(examInfo, "examInfo");
        checkForMissingParameter(contextInfo, "contextInfo");

        // validate
        List<ValidationResultInfo> errors;
        try {
            errors = ValidationUtils.validateInfo(validator, validationTypeKey, examInfo, contextInfo);
            List<ValidationResultInfo> nextDecoratorErrors = getNextDecorator().validateExam(validationTypeKey,
                    examTypeKey, examInfo, contextInfo);
            errors.addAll(nextDecoratorErrors);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("Error validating", ex);
        }
        return errors;
    }

    @Override
    public ExamInfo createExam(String examTypeKey, ExamInfo examInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        checkForMissingParameter(examTypeKey, "examTypeKey");
        checkForMissingParameter(examInfo, "examInfo");
        if (!examTypeKey.equals(examInfo.getTypeKey())) {
            throw new InvalidParameterException(examTypeKey + " does not match the corresponding value in the object " + examInfo.getTypeKey());
        }
        List<ValidationResultInfo> errors = validateExam(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), examTypeKey, examInfo, contextInfo);
        if (checkForErrors(errors)) {
            throw new DataValidationErrorException("Error(s) occurred validating", errors);
        }
        return getNextDecorator().createExam(examTypeKey, examInfo, contextInfo);
    }

    @Override
    public ExamInfo updateExam(String examId, ExamInfo examInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        checkForMissingParameter(examId, "examId");
        checkForMissingParameter(examInfo, "examInfo");
        if (!examId.equals(examInfo.getId())) {
            throw new InvalidParameterException(examId + " does not match the corresponding value in the object " + examInfo.getId());
        }
        List<ValidationResultInfo> errors = validateExam(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), examInfo.getTypeKey(), examInfo, contextInfo);
        if (checkForErrors(errors)) {
            throw new DataValidationErrorException("Error(s) occurred validating", errors);
        }
        return getNextDecorator().updateExam(examId, examInfo, contextInfo);
    }

    private static boolean checkForErrors(List<ValidationResultInfo> errors) {

        if (errors != null && !errors.isEmpty()) {
            for (ValidationResultInfo error : errors) {
                if (error.isError()) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Check for missing parameter and throw localized exception if missing
     *
     * @param param
     * @param paramName
     * @throws MissingParameterException
     */
    private void checkForMissingParameter(Object param, String paramName) throws MissingParameterException {
        if (param == null) {
            throw new MissingParameterException(paramName + " can not be null");
        }
    }


}
