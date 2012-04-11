package org.kuali.student.r2.lum.lrc.service;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.r2.common.dto.*;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.mock.utilities.MockHelper;

import javax.jws.WebParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jimt
 * Date: 9/30/11
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class LRCServiceMockImpl implements LRCService {
    private Map<String, ResultValuesGroupInfo> rvGroupInfos = new HashMap<String, ResultValuesGroupInfo>();

    @Override
    public ResultValuesGroupInfo getResultValuesGroup(@WebParam(name = "resultValuesGroupId") String resultValuesGroupId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByIds(@WebParam(name = "resultValuesGroupIds") List<String> resultValuesGroupIds, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultValue(@WebParam(name = "resultValueId") String resultValueId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getResultValuesGroupIdsByType(@WebParam(name = "resultValuesGroupTypeKey") String resultValuesGroupTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ResultValuesGroupInfo createResultValuesGroup(@WebParam(name = "resultGroupInfo") ResultValuesGroupInfo gradeValuesGroupInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MockHelper helper = new MockHelper();
        gradeValuesGroupInfo.setMeta(helper.createMeta(context));
        gradeValuesGroupInfo.setKey(UUIDHelper.genStringUUID());
        this.rvGroupInfos.put(gradeValuesGroupInfo.getKey(), gradeValuesGroupInfo);
        return gradeValuesGroupInfo;
    }

    @Override
    public ResultValuesGroupInfo updateResultValuesGroup(@WebParam(name = "resultValuesGroupId") String resultValuesGroupId, @WebParam(name = "resultValuesGroupInfo") ResultValuesGroupInfo gradeValuesGroupInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteResultValuesGroup(@WebParam(name = "resultValuesGroupId") String resultValuesGroupId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validateResultValuesGroup(@WebParam(name = "validationType") String validationType, @WebParam(name = "resultGroupInfo") ResultValuesGroupInfo gradeValuesGroupInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ResultValueInfo getResultValue(@WebParam(name = "resultValueId") String resultValueId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ResultValueInfo> getResultValuesByIds(@WebParam(name = "resultValueIds") List<String> resultValueIds, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ResultValueInfo> getResultValuesForResultValuesGroup(@WebParam(name = "resultValuesGroupId") String resultValuesGroupId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ResultValueInfo createResultValue(@WebParam(name = "resultValueInfo") ResultValueInfo resultValueInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ResultValueInfo updateResultValue(@WebParam(name = "resultValueId") String resultValueId, @WebParam(name = "resultValueInfo") ResultValueInfo resultValueInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteResultValue(@WebParam(name = "resultValueId") String resultValueId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validateResultValue(@WebParam(name = "validationType") String validationType, @WebParam(name = "resultValueInfo") ResultValueInfo resultValueInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ResultScaleInfo getResultScale(@WebParam(name = "resultScaleKey") String resultScaleKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ResultValueInfo> getResultValuesForScale(@WebParam(name = "resultScaleKey") String resultScaleKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
    
}
