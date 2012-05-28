package org.kuali.student.enrollment.class1.lpr.service.impl.mock;


import org.kuali.student.enrollment.class1.lpr.dao.LprDao;
import org.kuali.student.enrollment.class1.lpr.model.LprEntity;
import org.kuali.student.enrollment.class1.lui.dao.LuiDao;
import org.kuali.student.enrollment.class1.lui.dao.LuiLuiRelationDao;
import org.kuali.student.enrollment.class1.lui.model.LuiEntity;
import org.kuali.student.enrollment.class1.lui.model.LuiIdentifierEntity;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LprTestDataLoader {


    private LprDao lprDao;

    private  String principalId = LprTestDataLoader.class.getSimpleName();

    public LprTestDataLoader(LprDao lprDao){
     this.lprDao = lprDao;
    }
    public  void loadData() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, ReadOnlyException, VersionMismatchException,
            AlreadyExistsException, CircularRelationshipException {
        loadLpr("Lpr-1", "lui-1","Person-1",80.00F,  "kuali.lpr.type.courseoffering.instructor.main", "kuali.lpr.state.draft");
        loadLpr("Lpr-2", "lui-1","Person-2",20.00F,  "kuali.lpr.type.courseoffering.instructor.ta", "kuali.lpr.state.draft");
        loadLpr("Lpr-3","lui-2", "Person-1",100.00F,  "kuali.lpr.type.courseoffering.instructor.main", "kuali.lpr.state.draft");

    }

    private  void loadLpr(String lprId, String luiId, String personId, Float commitmentPercent, String lprState, String lprType )
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, ReadOnlyException, AlreadyExistsException {

        LprEntity lprEntity  = new LprEntity();
        lprEntity.setId(lprId);
        lprEntity.setCommitmentPercent(commitmentPercent);
        lprEntity.setLuiId(luiId);
        lprEntity.setPersonId(personId);
        lprEntity.setPersonRelationStateId(lprState);
        lprEntity.setPersonRelationTypeId(lprType);
        
        
        lprEntity.setCreateId(principalId);
        Date time;
        lprEntity.setCreateTime(time = new Date());
        
        lprEntity.setUpdateId(principalId);
        lprEntity.setUpdateTime(time);
        
        lprDao.persist(lprEntity);
    }



}
