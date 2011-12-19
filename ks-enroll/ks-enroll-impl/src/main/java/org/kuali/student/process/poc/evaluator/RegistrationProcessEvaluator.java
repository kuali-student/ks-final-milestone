package org.kuali.student.process.poc.evaluator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.process.poc.context.CourseRegistrationProcessContextInfo;
import org.kuali.student.process.poc.context.HoldCheckContext;
import org.kuali.student.process.poc.context.MilestoneCheckContext;
import org.kuali.student.process.poc.util.InstructionComparator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.common.util.constants.ProcessServiceConstants;

import org.kuali.student.r2.core.exemption.dto.ExemptionInfo;
import org.kuali.student.r2.core.exemption.service.ExemptionService;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.service.ProcessService;



public class RegistrationProcessEvaluator implements ProcessEvaluator<CourseRegistrationProcessContextInfo, ContextInfo> {

    AcademicCalendarService acalService;
    ProcessService processService;
    PopulationService populationService;
    ExemptionService exemptionService;
    MilestoneCheckEvaluator milestoneCheckEvaluator;
    HoldCheckEvaluator holdCheckEvaluator;

    public void setAcalService(AcademicCalendarService acalService) {
        this.acalService = acalService;
    }

    public void setProcessService(ProcessService processService) {
        this.processService = processService;
    }

    public void setPopulationService(PopulationService populationService) {
        this.populationService = populationService;
    }

    public void setExemptionService(ExemptionService exemptionService) {
        this.exemptionService = exemptionService;
    }

    public void setHoldCheckEvaluator(HoldCheckEvaluator holdCheckEvaluator) {
        this.holdCheckEvaluator = holdCheckEvaluator;
    }

    public void setMilestoneCheckEvaluator(MilestoneCheckEvaluator milestoneCheckEvaluator) {
        this.milestoneCheckEvaluator = milestoneCheckEvaluator;
    }

    
    
    @Override
    public List<ValidationResult> evaluate(CourseRegistrationProcessContextInfo processContext, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<InstructionInfo> instructionsList = new ArrayList<InstructionInfo>();
        TermInfo term = acalService.getTerm(processContext.getTermKey(), context);

        for (InstructionInfo instruction : processService.getInstructionsForEvaluation(processContext.getProcessKey(), context)) {

            if (!instruction.getAppliedAtpTypeKeys().contains(term.getTypeKey()))

                continue;

            for (String appliedPopulationKey : instruction.getAppliedPopulationKeys()) {

                if (!populationService.isMember(processContext.getStudentId(), appliedPopulationKey, context))
                 
                    break;

            }

            instructionsList.add(instruction);
        }

        return evaluateAnInstructionsList(instructionsList, processContext, context);
    }

    private List<ValidationResult> evaluateAnInstructionsList(List<InstructionInfo> instructionsList, CourseRegistrationProcessContextInfo processContext, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {

        // should already be sorted by position
//        Collections.sort(instructionsList, new InstructionComparator());  

        List<ValidationResult> validationResults = new ArrayList<ValidationResult>();

        for (InstructionInfo instruction : instructionsList) {

            List<ExemptionInfo> exemptions = exemptionService.getActiveExemptionsByTypeProcessAndCheckForPerson(null, processContext.getProcessKey(), instruction.getCheckKey(),
                    processContext.getStudentId(), context);

            if (exemptions.isEmpty ()) {

                CheckInfo check = processService.getCheck(instruction.getCheckKey(), context);
                if (check.getTypeKey().equals(ProcessServiceConstants.START_DATE_CHECK_TYPE_KEY)) {

                    validationResults.add(milestoneCheckEvaluator.evaluate(MilestoneCheckContext.createMilestoneCheckContext(processContext.getTermKey(), null, null), context));

                } else if (check.getTypeKey().equals(ProcessServiceConstants.HOLD_CHECK_TYPE_KEY)) {

                    validationResults.add(holdCheckEvaluator.evaluate(HoldCheckContext.createHoldContext(processContext.getTermKey(), null), context));

                }
            } else {
                
                incrementExemptionUsage();
                
                continue;
            }

        }
        return validationResults;
    }

    private void incrementExemptionUsage() {

    }

}
