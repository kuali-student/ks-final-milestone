/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.lrc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.LrcServiceConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.lrc.service.LrcServiceBusinessLogic;

public class LrcServiceMockImpl implements LRCService {

    private LrcServiceBusinessLogic lrcServiceBusinessLogic;

    public LrcServiceBusinessLogic getLrcServiceBusinessLogic() {
        return lrcServiceBusinessLogic;
    }

    public void setLrcServiceBusinessLogic(LrcServiceBusinessLogic lrcServiceBusinessLogic) {
        this.lrcServiceBusinessLogic = lrcServiceBusinessLogic;
        if (this.lrcServiceBusinessLogic instanceof LrcServiceBusinessLogic) {
            LrcServiceBusinessLogicImpl impl = (LrcServiceBusinessLogicImpl) lrcServiceBusinessLogic;
            impl.setLrcService(this);
        }
    }

    @Override
    public ResultValuesGroupInfo getResultValuesGroup(String resultValuesGroupKey,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        if (!this.resultValuesGroupMap.containsKey(resultValuesGroupKey)) {
            throw new DoesNotExistException(resultValuesGroupKey);
        }
        ResultValuesGroupInfo rvg = this.resultValuesGroupMap.get(resultValuesGroupKey);
        return rvg;
    }
//
//    private void loadResultValuesWithRange(ResultValuesGroupInfo rvg,
//            ContextInfo contextInfo)
//            throws OperationFailedException {
//        if (!rvg.getTypeKey().equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE)) {
//            return;
//        }
//        rvg.getResultValueKeys().clear();
//        List<ResultValueInfo> values;
//        try {
//            values = this.getResultValuesForScale(rvg.getResultScaleKey(), contextInfo);
//        } catch (DoesNotExistException ex) {
//            throw new OperationFailedException("unexpected", ex);
//        } catch (InvalidParameterException ex) {
//            throw new OperationFailedException("unexpected", ex);
//        } catch (MissingParameterException ex) {
//            throw new OperationFailedException("unexpected", ex);
//        } catch (OperationFailedException ex) {
//            throw new OperationFailedException("unexpected", ex);
//        } catch (PermissionDeniedException ex) {
//            throw new OperationFailedException("unexpected", ex);
//        }
//        for (ResultValueInfo rv : values) {
//            if (isWithinRange(rv, rvg)) {
//                rvg.getResultValueKeys().add(rv.getKey());
//            }
//        }
//    }
//
//    private boolean isWithinRange(ResultValueInfo rv,
//            ResultValuesGroupInfo rvg) {
//        float numericValue = Float.parseFloat(rv.getNumericValue());
//        float minValue = Float.parseFloat(rvg.getResultValueRange().getMinValue());
//        if (numericValue < minValue) {
//            return false;
//        }
//
//        float maxValue = Float.parseFloat(rvg.getResultValueRange().getMaxValue());
//        if (numericValue > maxValue) {
//            return false;
//        }
//        float increment = Float.parseFloat(rvg.getResultValueRange().getIncrement());
//        float diff = numericValue - minValue;
//        float remainder = diff % increment;
//        if (remainder != 0) {
//            return false;
//        }
//        return true;
//    }

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByKeys(List<String> resultValuesGroupKeys,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<ResultValuesGroupInfo> list = new ArrayList<ResultValuesGroupInfo>();
        for (String id : resultValuesGroupKeys) {
            list.add(this.getResultValuesGroup(id, context));
        }
        return list;
    }

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultValue(String resultValueKey,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<ResultValuesGroupInfo> list = new ArrayList<ResultValuesGroupInfo>();
        for (ResultValuesGroupInfo info : resultValuesGroupMap.values()) {
            if (info.getResultValueKeys().contains(resultValueKey)) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultScale(String resultScaleKey,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<ResultValuesGroupInfo> list = new ArrayList<ResultValuesGroupInfo>();
        for (ResultValuesGroupInfo info : resultValuesGroupMap.values()) {
            if (info.getResultScaleKey().contains(resultScaleKey)) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<String> getResultValuesGroupKeysByType(String resultValuesGroupTypeKey,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (ResultValuesGroupInfo info : resultValuesGroupMap.values()) {
            if (resultValuesGroupTypeKey.equals(info.getTypeKey())) {
                list.add(info.getKey());
            }
        }
        return list;
    }
    // cache variable 
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, ResultValuesGroupInfo> resultValuesGroupMap = new LinkedHashMap<String, ResultValuesGroupInfo>();

    @Override
    public ResultValuesGroupInfo createResultValuesGroup(String resultScaleKey,
            String resultValuesGroupTypeKey,
            ResultValuesGroupInfo resultValuesGroupInfo,
            ContextInfo context)
            throws AlreadyExistsException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        if (resultValuesGroupMap.containsKey(resultValuesGroupInfo.getKey())) {
            throw new AlreadyExistsException(resultValuesGroupInfo.getKey());
        }
        // create 
        ResultValuesGroupInfo copy = new ResultValuesGroupInfo(resultValuesGroupInfo);
        copy.setResultScaleKey(resultScaleKey);
        copy.setTypeKey(resultValuesGroupTypeKey);
        if (copy.getKey() == null) {
            copy.setKey(resultValuesGroupMap.size() + "");
        }
        copy.setMeta(newMeta(context));
        resultValuesGroupMap.put(copy.getKey(), copy);
        ResultValuesGroupInfo rvgNew = new ResultValuesGroupInfo(copy);
        return rvgNew;
    }

    @Override
    public ResultValuesGroupInfo updateResultValuesGroup(String resultValuesGroupKey,
            ResultValuesGroupInfo gradeValuesGroupInfo,
            ContextInfo context)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException {
        // update
        if (!resultValuesGroupKey.equals(gradeValuesGroupInfo.getKey())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        ResultValuesGroupInfo copy = new ResultValuesGroupInfo(gradeValuesGroupInfo);
        ResultValuesGroupInfo old = this.getResultValuesGroup(gradeValuesGroupInfo.getKey(), context);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), context));
        this.resultValuesGroupMap.put(gradeValuesGroupInfo.getKey(), copy);
        ResultValuesGroupInfo rvgNew = new ResultValuesGroupInfo(copy);
        return rvgNew;
    }

    @Override
    public StatusInfo deleteResultValuesGroup(String resultValuesGroupKey,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        if (this.resultValuesGroupMap.remove(resultValuesGroupKey) == null) {
            throw new DoesNotExistException(resultValuesGroupKey);
        }
        return newStatus();
    }

    @Override
    public List<ValidationResultInfo> validateResultValuesGroup(String validationType,
            ResultValuesGroupInfo gradeValuesGroupInfo,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        // validate
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public ResultValuesGroupInfo getCreateFixedCreditResultValuesGroup(String creditValue,
            String scaleKey,
            ContextInfo context)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return this.lrcServiceBusinessLogic.getCreateFixedCreditResultValuesGroup(creditValue, scaleKey, context);
    }

    @Override
    public ResultValuesGroupInfo getCreateRangeCreditResultValuesGroup(String creditValueMin,
            String creditValueMax,
            String creditValueIncrement,
            String scaleKey,
            ContextInfo context)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        ResultValuesGroupInfo rvg = this.lrcServiceBusinessLogic.getCreateRangeCreditResultValuesGroup(creditValueMin,
                creditValueMax,
                creditValueIncrement, scaleKey, context);
        return rvg;

    }

    @Override
    public ResultValuesGroupInfo getCreateMultipleCreditResultValuesGroup(List<String> creditValues,
            String scaleKey,
            ContextInfo context)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return this.lrcServiceBusinessLogic.getCreateMultipleCreditResultValuesGroup(creditValues, scaleKey, context);
    }

    @Override
    public ResultValueInfo getResultValue(String resultValueKey,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        if (!this.resultValueMap.containsKey(resultValueKey)) {
            throw new DoesNotExistException(resultValueKey);
        }
        return this.resultValueMap.get(resultValueKey);
    }

    @Override
    public List<ResultValueInfo> getResultValuesByKeys(List<String> resultValueKeys,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<ResultValueInfo> list = new ArrayList<ResultValueInfo>();
        for (String id : resultValueKeys) {
            list.add(this.getResultValue(id, context));
        }
        return list;
    }

    @Override
    public List<String> getResultValueKeysByType(String resultValueTypeKey,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (ResultValueInfo info : resultValueMap.values()) {
            if (resultValueTypeKey.equals(info.getTypeKey())) {
                list.add(info.getKey());
            }
        }
        return list;
    }

    @Override
    public List<ResultValueInfo> getResultValuesForResultValuesGroup(String resultValuesGroupKey,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        ResultValuesGroupInfo info = this.getResultValuesGroup(resultValuesGroupKey, contextInfo);
        return this.getResultValuesByKeys(info.getResultValueKeys(), contextInfo);
    }
    // cache variable 
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, ResultValueInfo> resultValueMap = new LinkedHashMap<String, ResultValueInfo>();

    @Override
    public ResultValueInfo createResultValue(String resultScaleKey,
            String resultValueTypeKey,
            ResultValueInfo resultValueInfo,
            ContextInfo context)
            throws AlreadyExistsException,
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        if (resultValueMap.containsKey(resultValueInfo.getKey())) {
            throw new AlreadyExistsException(resultValueInfo.getKey());
        }
        // create 
        ResultValueInfo copy = new ResultValueInfo(resultValueInfo);
        copy.setResultScaleKey(resultScaleKey);
        copy.setTypeKey(resultValueTypeKey);
        if (copy.getKey() == null) {
            copy.setKey(resultValueMap.size() + "");
        }
        copy.setMeta(newMeta(context));
        resultValueMap.put(copy.getKey(), copy);
        return new ResultValueInfo(copy);
    }

    @Override
    public ResultValueInfo updateResultValue(String resultValueKey,
            ResultValueInfo resultValueInfo,
            ContextInfo context)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException {
        // update
        if (!resultValueKey.equals(resultValueInfo.getKey())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        ResultValueInfo copy = new ResultValueInfo(resultValueInfo);
        ResultValueInfo old = this.getResultValue(resultValueInfo.getKey(), context);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), context));
        this.resultValueMap.put(resultValueInfo.getKey(), copy);
        return new ResultValueInfo(copy);
    }

    @Override
    public StatusInfo deleteResultValue(String resultValueKey,
            ContextInfo context)
            throws DoesNotExistException,
            DependentObjectsExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<ResultValuesGroupInfo> list = this.getResultValuesGroupsByResultValue(resultValueKey, context);
        if (!list.isEmpty()) {
            throw new DependentObjectsExistException(list.size() + " rvgs exist");
        }
        if (this.resultValueMap.remove(resultValueKey) == null) {
            throw new DoesNotExistException(resultValueKey);
        }
        return newStatus();
    }

    @Override
    public List<ValidationResultInfo> validateResultValue(String validationType,
            ResultValueInfo resultValueInfo,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        // validate
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public ResultValueInfo getCreateResultValueForScale(String resultValue,
            String scaleKey,
            ContextInfo context)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return this.lrcServiceBusinessLogic.getCreateResultValueForScale(resultValue, scaleKey, context);
    }

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultScaleType(String resultScaleTypeKey,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<ResultValuesGroupInfo> list = new ArrayList<ResultValuesGroupInfo>();
        List<String> scaleKeys = this.getResultScaleKeysByType(resultScaleTypeKey, context);
        for (String scaleKey : scaleKeys) {
            list.addAll(this.getResultValuesGroupsByResultScale(scaleKey, context));
        }
        return list;
    }

    @Override
    public ResultScaleInfo getResultScale(String resultScaleKey,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        if (!this.resultScaleMap.containsKey(resultScaleKey)) {
            throw new DoesNotExistException(resultScaleKey);
        }
        return this.resultScaleMap.get(resultScaleKey);
    }

    @Override
    public List<ResultScaleInfo> getResultScalesByKeys(List<String> resultScaleKeys,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<ResultScaleInfo> list = new ArrayList<ResultScaleInfo>();
        for (String id : resultScaleKeys) {
            list.add(this.getResultScale(id, context));
        }
        return list;
    }

    @Override
    public List<String> getResultScaleKeysByType(String resultScaleTypeKey,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (ResultScaleInfo info : resultScaleMap.values()) {
            if (resultScaleTypeKey.equals(info.getTypeKey())) {
                list.add(info.getKey());
            }
        }
        return list;
    }
    // cache variable 
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, ResultScaleInfo> resultScaleMap = new LinkedHashMap<String, ResultScaleInfo>();

    @Override
    public ResultScaleInfo createResultScale(String resultScaleTypeKey,
            ResultScaleInfo resultScaleInfo,
            ContextInfo context)
            throws AlreadyExistsException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        if (resultScaleMap.containsKey(resultScaleInfo.getKey())) {
            throw new AlreadyExistsException(resultScaleInfo.getKey());
        }
        // create 
        ResultScaleInfo copy = new ResultScaleInfo(resultScaleInfo);
        copy.setTypeKey(resultScaleTypeKey);
        if (copy.getKey() == null) {
            copy.setKey(resultScaleMap.size() + "");
        }
        copy.setMeta(newMeta(context));
        resultScaleMap.put(copy.getKey(), copy);
        return new ResultScaleInfo(copy);
    }

    @Override
    public ResultScaleInfo updateResultScale(String resultScaleKey,
            ResultScaleInfo gradeScaleInfo,
            ContextInfo context)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException {
        // update
        if (!resultScaleKey.equals(gradeScaleInfo.getKey())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        ResultScaleInfo copy = new ResultScaleInfo(gradeScaleInfo);
        ResultScaleInfo old = this.getResultScale(gradeScaleInfo.getKey(), context);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), context));
        this.resultScaleMap.put(gradeScaleInfo.getKey(), copy);
        return new ResultScaleInfo(copy);
    }

    @Override
    public StatusInfo deleteResultScale(String resultScaleKey,
            ContextInfo context)
            throws DoesNotExistException,
            DependentObjectsExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<ResultValueInfo> values = this.getResultValuesForScale(resultScaleKey, context);
        if (!values.isEmpty()) {
            throw new DependentObjectsExistException(values.size() + " ResultValue(s) exist");
        }
        List<ResultValuesGroupInfo> groups = this.getResultValuesGroupsByResultScale(resultScaleKey, context);
        if (!groups.isEmpty()) {
            throw new DependentObjectsExistException(groups.size() + " ResultValuesGroup(s) exist");
        }
        if (this.resultScaleMap.remove(resultScaleKey) == null) {
            throw new DoesNotExistException(resultScaleKey);
        }
        return newStatus();
    }

    @Override
    public List<ValidationResultInfo> validateResultScale(String validationType,
            ResultScaleInfo gradeScaleInfo,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        // validate
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public List<ResultValueInfo> getResultValuesForScale(String resultScaleKey,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<ResultValueInfo> list = new ArrayList<ResultValueInfo>();
        for (ResultValueInfo info : this.resultValueMap.values()) {
            if (info.getResultScaleKey().equals(resultScaleKey)) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public ResultValueInfo getResultValueForScaleAndValue(String resultScaleKey,
            String value,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<ResultValueInfo> list = new ArrayList<ResultValueInfo>();
        for (ResultValueInfo info : this.resultValueMap.values()) {
            if (info.getResultScaleKey().equals(resultScaleKey)) {
                if (info.getValue().equals(value)) {
                    list.add(info);
                }
            }
        }
        if (list.isEmpty()) {
            throw new DoesNotExistException(resultScaleKey + " " + value);
        }
        if (list.size() > 1) {
            throw new OperationFailedException("too many matches " + list.size());
        }
        return list.get(0);
    }

    @Override
    public List<ResultValueInfo> getResultValuesForResultValuesGroups(List<String> resultValuesGroupKeys,
            ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<ResultValueInfo> list = new ArrayList<ResultValueInfo>();
        Set<String> keys = new HashSet<String>();
        for (String rvgKey : resultValuesGroupKeys) {
            ResultValuesGroupInfo rvg = this.getResultValuesGroup(rvgKey, context);
            for (String rvKey : rvg.getResultValueKeys()) {
                if (keys.add(rvKey)) {
                    list.add(this.getResultValue(rvKey, context));
                }
            }
        }
        return list;
    }

    private StatusInfo newStatus() {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    private MetaInfo newMeta(ContextInfo context) {
        MetaInfo meta = new MetaInfo();
        meta.setCreateId(context.getPrincipalId());
        meta.setCreateTime(new Date());
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(meta.getCreateTime());
        meta.setVersionInd("0");
        return meta;
    }

    private MetaInfo updateMeta(MetaInfo old,
            ContextInfo context) {
        MetaInfo meta = new MetaInfo(old);
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(new Date());
        meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
        return meta;
    }
}
