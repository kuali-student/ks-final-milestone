package org.kuali.student.enrollment.class2.courseoffering.service.assembler;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.enrollment.class2.courseoffering.service.transformer.RegistrationGroupTransformer;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

public class RegistrationGroupAssembler {

    private LuiService luiService;
    private RegistrationGroupTransformer registrationGroupTransformer;

    public RegistrationGroupTransformer getRegistrationGroupTransformer() {
        return registrationGroupTransformer;
    }

    public void setRegistrationGroupTransformer(RegistrationGroupTransformer registrationGroupTransformer) {
        this.registrationGroupTransformer = registrationGroupTransformer;
    }

    public LuiService getLuiService() {
        return luiService;
    }

    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

    public RegistrationGroupInfo assemble(LuiInfo lui, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (lui != null) {
            RegistrationGroupInfo rg = registrationGroupTransformer.lui2Rg(lui, context);
            assembleLuiLuiRelations(rg, lui.getId(), context);
            return rg;
        } else {
            return null;
        }
    }

    private void assembleLuiLuiRelations(RegistrationGroupInfo rg, String luiId, ContextInfo context) 
        throws DoesNotExistException, InvalidParameterException, MissingParameterException,
               OperationFailedException, PermissionDeniedException {
        String courseOfferingId = null;
        ;
        List<String> activityIds = new ArrayList<String>();
        List<LuiLuiRelationInfo> rels = luiService.getLuiLuiRelationsByLui(luiId, context);
        if (rels != null && !rels.isEmpty()) {
            for (LuiLuiRelationInfo rel : rels) {
                if (rel.getLuiId().equals(luiId)) {
                    if (rel.getTypeKey().equals(LuiServiceConstants.LUI_LUI_RELATION_REGISTEREDFORVIA_TYPE_KEY)) {
                        LuiInfo lui = luiService.getLui(rel.getRelatedLuiId(), context);
                        if (lui != null) {
                            if (lui.getTypeKey().equals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY)) {
                                courseOfferingId = rel.getRelatedLuiId();
                            } else {
                                if (!activityIds.contains(rel.getRelatedLuiId())) {
                                    activityIds.add(rel.getRelatedLuiId());
                                }
                            }
                        }
                    }
                }
            }
        }

        if (null != courseOfferingId)
            rg.setCourseOfferingId(courseOfferingId);
        if (!activityIds.isEmpty())
            rg.setActivityOfferingIds(activityIds);

    }

    public LuiInfo disassemble(RegistrationGroupInfo rg, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException {
        LuiInfo lui = registrationGroupTransformer.rg2Lui(rg, context);
        return lui;
    }
}
