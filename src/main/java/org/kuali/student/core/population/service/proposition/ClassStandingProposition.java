package org.kuali.student.core.population.service.proposition;


import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.rice.krms.framework.engine.result.BasicResult;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.common.util.krms.proposition.AbstractLeafProposition;
import org.kuali.student.core.process.evaluator.KRMSEvaluator;
import org.kuali.student.enrollment.academicrecord.dto.StudentProgramRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;




import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ClassStandingProposition extends AbstractLeafProposition {

    private String standing;

    public ClassStandingProposition(){}

    public ClassStandingProposition(String standing){
        this.standing = standing;
    }

    public String getStanding() {
        return standing;
    }

    public void setStanding(String standing) {
        this.standing = standing;
    }

    @Override
    public PropositionResult evaluate(ExecutionEnvironment environment) {

        String personId = environment.resolveTerm(RulesExecutionConstants.PERSON_ID_TERM, this);
        ContextInfo contextInfo = environment.resolveTerm(RulesExecutionConstants.CONTEXT_INFO_TERM, this);
        AcademicRecordService academicRecordService = environment.resolveTerm(RulesExecutionConstants.ACADEMIC_RECORD_SERVICE_TERM,this);
        boolean result = false;

        List<StudentProgramRecordInfo> studentProgramRecords = null;
        try {
            studentProgramRecords = academicRecordService.getProgramRecords(personId,contextInfo);
            for (StudentProgramRecordInfo info : studentProgramRecords){
                if(info.getClassStanding().equals(standing)){
                    result = true;
                }
            }
        } catch (DoesNotExistException e) {

            return KRMSEvaluator.constructExceptionPropositionResult(environment, e, this);
        } catch (InvalidParameterException e) {
            return KRMSEvaluator.constructExceptionPropositionResult(environment, e, this);
        } catch (MissingParameterException e) {
            return KRMSEvaluator.constructExceptionPropositionResult(environment, e, this);
        } catch (OperationFailedException e) {
            return KRMSEvaluator.constructExceptionPropositionResult(environment, e, this);
        } catch (PermissionDeniedException e) {
            return KRMSEvaluator.constructExceptionPropositionResult(environment, e, this);
        }

        Map<String, Object> executionDetails = new LinkedHashMap<String, Object>();
        PropositionResult propResult = new PropositionResult(result, executionDetails);

        BasicResult br = new BasicResult(executionDetails, RulesExecutionConstants.STUDENT_CLASS_STANDING,
                this,
                environment,
                propResult.getResult());
        environment.getEngineResults().addResult(br);


        return propResult;

    }

}

