package org.kuali.student.enrollment.class2.courseoffering.service.assembler;

import org.kuali.student.enrollment.class2.courseoffering.service.transformer.ActivityOfferingTransformer;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;

import java.util.ArrayList;
import java.util.List;

public class ActivityOfferingAssembler implements DTOAssembler<ActivityOfferingInfo, LuiInfo> {

    private LuiPersonRelationService lprService;

    private ActivityOfferingTransformer activityTransformer;

    public ActivityOfferingTransformer getActivityTransformer() {
        return activityTransformer;
    }

    public void setActivityTransformer(ActivityOfferingTransformer activityTransformer) {
        this.activityTransformer = activityTransformer;
    }

    public LuiPersonRelationService getLprService() {
        return lprService;
    }

    public void setLprService(LuiPersonRelationService lprService) {
        this.lprService = lprService;
    }

    @Override
    public ActivityOfferingInfo assemble(LuiInfo lui, ContextInfo context) throws AssemblyException {
        ActivityOfferingInfo ao = activityTransformer.transform(lui);

        assembleInstructors(ao, lui.getId(), context);

        return ao;

    }

    private void assembleInstructors(ActivityOfferingInfo ao, String luiId, ContextInfo context) throws AssemblyException {
        List<LuiPersonRelationInfo> lprs = null;
        ;
        try {
            lprs = lprService.getLprsByLui(luiId, context);
        } catch (DoesNotExistException e) {
            throw new AssemblyException("DoesNotExistException: " + e.getMessage());
        } catch (InvalidParameterException e) {
            throw new AssemblyException("InvalidParameterException: " + e.getMessage());
        } catch (MissingParameterException e) {
            throw new AssemblyException("MissingParameterException: " + e.getMessage());
        } catch (OperationFailedException e) {
            throw new AssemblyException("OperationFailedException: " + e.getMessage());
        } catch (PermissionDeniedException e) {
            throw new AssemblyException("PermissionDeniedException: " + e.getMessage());
        }

        if (lprs != null && !lprs.isEmpty()) {
            List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();
            for (LuiPersonRelationInfo lpr : lprs) {
                if (lpr != null && lpr.getTypeKey() != null && lpr.getTypeKey().equals(LuiPersonRelationServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY)) {
                    instructors.add( activityTransformer.transformInstructorForActivityOffering(lpr));
                }
            }
            ao.setInstructors(instructors);
        }
        
    }

    @Override
    public LuiInfo disassemble(ActivityOfferingInfo ao, ContextInfo context) throws AssemblyException {

        LuiInfo lui = activityTransformer.transformActivityOfferingToLui(ao);
        return lui;

    }

}
