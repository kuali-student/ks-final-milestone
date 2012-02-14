package org.kuali.student.process.poc.evaluator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.kuali.student.process.poc.context.CourseRegistrationProcessContextInfo;
import org.kuali.student.process.poc.context.DirectRuleCheckContext;
import org.kuali.student.process.poc.context.HoldCheckContext;
import org.kuali.student.process.poc.context.MilestoneCheckContext;
import org.kuali.student.process.poc.context.SubProcessCheckContext;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.constants.ExemptionServiceConstants;
import org.kuali.student.r2.common.util.constants.ProcessServiceConstants;

import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.exemption.dto.ExemptionInfo;
import org.kuali.student.r2.core.exemption.service.ExemptionService;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.service.ProcessService;

public class RegistrationProcessEvaluator implements ProcessEvaluator<CourseRegistrationProcessContextInfo> {

    private AtpService atpService;
    private ProcessService processService;
    private PopulationService populationService;
    private ExemptionService exemptionService;
    private MilestoneCheckEvaluator milestoneCheckEvaluator;
    private HoldCheckEvaluator holdCheckEvaluator;
    private DirectRuleCheckEvaluator directRuleCheckEvaluator;
    private SubProcessCheckEvaluator subProcessCheckEvaluator;

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    public ExemptionService getExemptionService() {
        return exemptionService;
    }

    public void setExemptionService(ExemptionService exemptionService) {
        this.exemptionService = exemptionService;
    }

    public PopulationService getPopulationService() {
        return populationService;
    }

    public void setPopulationService(PopulationService populationService) {
        this.populationService = populationService;
    }

    public ProcessService getProcessService() {
        return processService;
    }

    public void setProcessService(ProcessService processService) {
        this.processService = processService;
    }

    public HoldCheckEvaluator getHoldCheckEvaluator() {
        return holdCheckEvaluator;
    }

    public void setHoldCheckEvaluator(HoldCheckEvaluator holdCheckEvaluator) {
        this.holdCheckEvaluator = holdCheckEvaluator;
    }

    public MilestoneCheckEvaluator getMilestoneCheckEvaluator() {
        return milestoneCheckEvaluator;
    }

    public void setMilestoneCheckEvaluator(MilestoneCheckEvaluator milestoneCheckEvaluator) {
        this.milestoneCheckEvaluator = milestoneCheckEvaluator;
    }

    public DirectRuleCheckEvaluator getDirectRuleCheckEvaluator() {
        return directRuleCheckEvaluator;
    }

    public void setDirectRuleCheckEvaluator(DirectRuleCheckEvaluator directRuleCheckEvaluator) {
        this.directRuleCheckEvaluator = directRuleCheckEvaluator;
    }

    public SubProcessCheckEvaluator getSubProcessCheckEvaluator() {
        return subProcessCheckEvaluator;
    }

    public void setSubProcessCheckEvaluator(SubProcessCheckEvaluator subProcessCheckEvaluator) {
        this.subProcessCheckEvaluator = subProcessCheckEvaluator;
    }

    @Override
    public List<ValidationResultInfo> evaluate(CourseRegistrationProcessContextInfo processContext, ContextInfo context)
            throws OperationFailedException {
        List<ValidationResultInfo> allResults = new ArrayList<ValidationResultInfo>();
//        TermInfo term = acalService.getTerm(processContext.getTermKey(), context);

        List<InstructionInfo> instructions;
        try {
            instructions = processService.getInstructionsForEvaluation(processContext.getProcessKey(), context);
        } catch (OperationFailedException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OperationFailedException("unexpected", ex);
        }
        for (InstructionInfo instruction : instructions) {
            // TODO: filter on time
//         if (!instruction.getAppliedAtpTypeKeys().contains(term.getTypeKey()))
//                continue;
            boolean matchesPopulation = false;
            for (String appliedPopulationKey : instruction.getAppliedPopulationKeys()) {
                boolean isMember;
                try {
                    isMember = populationService.isMember(processContext.getStudentId(), appliedPopulationKey, context);
                } catch (OperationFailedException ex) {
                    throw ex;
                } catch (Exception ex) {
                    throw new OperationFailedException("unexpected", ex);
                }
                if (isMember) {
                    matchesPopulation = true;
                    break;
                }
            }
            if (!matchesPopulation) {
                continue;
            }
            List<ValidationResultInfo> vrs = this.evaluateAnInstruction(instruction, processContext, context);
            allResults.addAll(vrs);
            if (hasErrors (vrs)) {
                if (instruction.getContinueOnFail() != null) {
                    if (instruction.getContinueOnFail()) {
                        continue;
                    }
                    break;
                }
            }
        }
        return allResults;
    }
    
    private static boolean hasErrors(List<ValidationResultInfo> vrs) {
        return SubProcessCheckEvaluator.hasErrors(vrs);
    }
    

    private List<ValidationResultInfo> evaluateAnInstruction(InstructionInfo instruction, CourseRegistrationProcessContextInfo processContext, ContextInfo context)
            throws OperationFailedException {

        List<ExemptionInfo> exemptions;
        try {
            exemptions = exemptionService.getActiveExemptionsByTypeProcessAndCheckForPerson(ExemptionServiceConstants.CHECK_EXEMPTION_TYPE_KEY,
                    processContext.getProcessKey(),
                    instruction.getCheckKey(),
                    processContext.getStudentId(),
                    context);
        } catch (OperationFailedException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OperationFailedException("unexpected", ex);
        }

        if (!exemptions.isEmpty()) {
            ValidationResultInfo vr = new ValidationResultInfo();
            vr.setElement(processContext.getProcessKey() + "-" + instruction.getCheckKey());
            vr.setLevel(ValidationResultInfo.ErrorLevel.OK);
            vr.setMessage("exempted from check");
            return Arrays.asList(vr);
        }

        CheckInfo check;
        try {
            check = processService.getCheck(instruction.getCheckKey(), context);
        } catch (OperationFailedException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OperationFailedException("unexpected", ex);
        }

        // Set a calendar instance back to the end of 2011, since the POC assumes student is attempting to register before the end of the year
        Calendar registerDate = Calendar.getInstance();
        registerDate.set(2011, 11, 30);

        // start date check
        if (check.getTypeKey().equals(ProcessServiceConstants.START_DATE_CHECK_TYPE_KEY)) {
            MilestoneCheckContext checkContext = new MilestoneCheckContext();
            checkContext.setInstruction(instruction);
            checkContext.setCheck(check);
            checkContext.setAtpKey(processContext.getTermKey());
            checkContext.setStudentId(processContext.getStudentId());
            // TODO: get the as of date from the context once it is added there

            checkContext.setDateToTest(registerDate.getTime());
            checkContext.setComparison(MilestoneCheckContext.START_DATE);
            List<ValidationResultInfo> vrs = milestoneCheckEvaluator.evaluate(checkContext, context);
            return vrs;
        }
        // deadline date check
        if (check.getTypeKey().equals(ProcessServiceConstants.DEADLINE_CHECK_TYPE_KEY)) {
            MilestoneCheckContext checkContext = new MilestoneCheckContext();
            checkContext.setInstruction(instruction);
            checkContext.setCheck(check);
            checkContext.setAtpKey(processContext.getTermKey());
            checkContext.setStudentId(processContext.getStudentId());
            // TODO: get the as of date from the context once it is added there
            checkContext.setDateToTest(registerDate.getTime());
            checkContext.setComparison(MilestoneCheckContext.END_DATE);
            List<ValidationResultInfo> vrs = milestoneCheckEvaluator.evaluate(checkContext, context);
            return vrs;
        }
        // date range/time period
        if (check.getTypeKey().equals(ProcessServiceConstants.TIME_PERIOD_CHECK_TYPE_KEY)) {
            MilestoneCheckContext checkContext = new MilestoneCheckContext();
            checkContext.setInstruction(instruction);
            checkContext.setCheck(check);
            checkContext.setAtpKey(processContext.getTermKey());
            checkContext.setStudentId(processContext.getStudentId());
            // TODO: get the as of date from the context once it is added there
            checkContext.setDateToTest(registerDate.getTime());
            checkContext.setComparison(MilestoneCheckContext.PERIOD);
            List<ValidationResultInfo> vrs = milestoneCheckEvaluator.evaluate(checkContext, context);
            return vrs;
        }
        // hold check
        if (check.getTypeKey().equals(ProcessServiceConstants.HOLD_CHECK_TYPE_KEY)) {
            HoldCheckContext checkContext = new HoldCheckContext();
            checkContext.setInstruction(instruction);
            checkContext.setCheck(check);
            checkContext.setAtpKey(processContext.getTermKey());
            checkContext.setStudentId(processContext.getStudentId());
            List<ValidationResultInfo> vrs = holdCheckEvaluator.evaluate(checkContext, context);
            return vrs;
        }
        // direct rule
        if (check.getTypeKey().equals(ProcessServiceConstants.DIRECT_RULE_CHECK_TYPE_KEY)) {
            DirectRuleCheckContext checkContext = new DirectRuleCheckContext();
            checkContext.setInstruction(instruction);
            checkContext.setCheck(check);
            checkContext.setAtpKey(processContext.getTermKey());
            checkContext.setStudentId(processContext.getStudentId());
            List<ValidationResultInfo> vrs = directRuleCheckEvaluator.evaluate(checkContext, context);
            return vrs;
        }
        // sub-process
        if (check.getTypeKey().equals(ProcessServiceConstants.PROCESS_CHECK_TYPE_KEY)) {
            SubProcessCheckContext checkContext = new SubProcessCheckContext();
            checkContext.setInstruction(instruction);
            checkContext.setCheck(check);
            checkContext.setAtpKey(processContext.getTermKey());
            checkContext.setStudentId(processContext.getStudentId());
            checkContext.setProcessEvaluator(this);
            checkContext.setProcessContext(processContext);
            List<ValidationResultInfo> vrs = subProcessCheckEvaluator.evaluate(checkContext, context);
            return vrs;
        }
        throw new OperationFailedException(check.getTypeKey() + " is not yet supported");
    }
}
