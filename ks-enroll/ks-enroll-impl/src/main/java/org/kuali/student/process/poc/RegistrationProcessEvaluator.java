package org.kuali.student.process.poc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.service.ProcessService;

public class RegistrationProcessEvaluator implements ProcessEvaluator<CourseRegistrationProcessContextInfo, ContextInfo> {

    AcademicCalendarService acalService;
    ProcessService processService;
    PopulationService populationService;

    public void setAcalService(AcademicCalendarService acalService) {
        this.acalService = acalService;
    }

    public void setProcessService(ProcessService processService) {
        this.processService = processService;
    }

    public void setPopulationService(PopulationService populationService) {
        this.populationService = populationService;
    }

    @Override
    public String evaluate(CourseRegistrationProcessContextInfo processContext, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<InstructionInfo> instructionsList = new ArrayList<InstructionInfo>();
        TermInfo term = acalService.getTerm(processContext.getTermKey(), context);

        for (InstructionInfo instruction : processService.getInstructionsByProcess(processContext.getProcessKey(), context)) {

            if (!instruction.getAppliedAtpTypeKeys().contains(term.getTypeKey()))

                break;

            for (String appliedPopulationKey : instruction.getAppliedPopulationKeys()) {

                if (populationService.isMember(processContext.getStudentId(), appliedPopulationKey, context))
                    break;

            }

            instructionsList.add(instruction);
        }
        /*
         * if not populationService.inPopulation (studentId,
         * instruction.populationKey) continue exemption =
         * exemptionService.getExemption (studentId, CHECK_EXEMPTION_TYPE,
         * processKey, instruction.checkKey) if (exemption != null) continue
         * message = null switch case instruction.type = hold message =
         * holdEvaluator.evaluate(instruction.issueId, studentId, termKey) break
         * case instruction.type = milestone message =
         * milestoneEvaluator.evaluate (instruction.milestoneTypeKey, termKey)
         * break case instruction.type = acknowledgement etc... end-switch if
         * message != null messages.add (errorMessage) if message.getLevel =
         * ERROR and instruction.shortCircuit return messages end-for return
         * messages
         */
        return null;
    }

}
