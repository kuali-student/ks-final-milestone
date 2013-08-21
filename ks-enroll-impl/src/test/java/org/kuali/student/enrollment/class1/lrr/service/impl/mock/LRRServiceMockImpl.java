/*
 * Copyright 2012 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.enrollment.class1.lrr.service.impl.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.mock.MockService;
import org.kuali.student.enrollment.lrr.dto.LearningResultRecordInfo;
import org.kuali.student.enrollment.lrr.dto.ResultSourceInfo;
import org.kuali.student.enrollment.lrr.service.LearningResultRecordService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

/**
 * A java.util.Map based implementation of the {@link LearningResultRecordService}.
 * 
 * @author Kuali Student Team
 *
 */
public class LRRServiceMockImpl implements LearningResultRecordService, MockService {

	private Map<String, LearningResultRecordInfo> learningResultCache = new HashMap<String, LearningResultRecordInfo>();
	private Map<String, ResultSourceInfo> resultSourcesCache = new HashMap<String, ResultSourceInfo>();

    @Override
    public void clear() {
        learningResultCache.clear();
        resultSourcesCache.clear();
    }

    @Override
	public LearningResultRecordInfo getLearningResultRecord(
			String learningResultRecordId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return learningResultCache.get(learningResultRecordId);
	}

	@Override
	public List<LearningResultRecordInfo> getLearningResultRecordsForLpr(
			String lprId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		List<LearningResultRecordInfo> matchingLrrs = new ArrayList<LearningResultRecordInfo>();
		for (LearningResultRecordInfo lrrInfo : learningResultCache.values()) {
			if (lrrInfo.getLprId().equals(lprId)) {
				matchingLrrs.add(lrrInfo);
			}
		}

		return matchingLrrs;
	}

	@Override
	public List<LearningResultRecordInfo> getLearningResultRecordsForLprIds(
			List<String> lprIds, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		List<LearningResultRecordInfo> allLRRsForLPR = new ArrayList<LearningResultRecordInfo>();
		for (String lprId : lprIds) {
			allLRRsForLPR.addAll(getLearningResultRecordsForLpr(lprId));
		}
		return allLRRsForLPR;
	}

	@Override
	public List<LearningResultRecordInfo> getLearningResultRecordsBySourceId(
			List<String> sourceIds, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		List<LearningResultRecordInfo> lrrsMatchingSource = new ArrayList<LearningResultRecordInfo>();
		for (LearningResultRecordInfo lrr : learningResultCache.values()) {
			if (lrr.getResultSourceIds().retainAll(sourceIds)  ) {
				lrrsMatchingSource.add(lrr);
			}
		}

		return lrrsMatchingSource;
	}

	@Override
	public LearningResultRecordInfo createLearningResultRecord(
			LearningResultRecordInfo learningResultRecord, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		learningResultRecord.setId(String.valueOf(Math.random()));
		learningResultCache.put(learningResultRecord.getId(),
				learningResultRecord);
		return learningResultRecord;

	}

	@Override
	public LearningResultRecordInfo updateLearningResultRecord(
			String learningResultRecordId,
			LearningResultRecordInfo learningResultRecordInfo,
			ContextInfo context) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, VersionMismatchException {

		learningResultCache.put(learningResultRecordId,
				learningResultRecordInfo);
		return learningResultRecordInfo;
	}

	@Override
	public StatusInfo deleteLearningResultRecord(String learningResultRecordId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		learningResultCache.remove(learningResultRecordId);
		return new StatusInfo();
	}

	@Override
	public List<ValidationResultInfo> validateLearningResultRecord(
			String validationType,
			LearningResultRecordInfo learningResultRecordInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
    	return new ArrayList<ValidationResultInfo>();
	}

	@Override
	public ResultSourceInfo getResultSource(String resultSourceId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return resultSourcesCache.get(resultSourceId);
	}

	@Override
	public List<ResultSourceInfo> getResultSourcesByIds(
			List<String> resultSourceIds, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		List<ResultSourceInfo> resultSources = new ArrayList<ResultSourceInfo>();
		for (String sourceId : resultSourceIds) {
			resultSources.add(getResultSource(sourceId, context));

		}
		return resultSources;

	}

	@Override
	public List<ResultSourceInfo> getResultSourcesByType(
			String resultSourceTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List <ResultSourceInfo> resultSources = new ArrayList<ResultSourceInfo>();
			for(ResultSourceInfo resultSource: resultSourcesCache.values() ){
				if(resultSource.getTypeKey().equals(resultSourceTypeKey)){
					resultSources.add(resultSource);
				}
			}
		return resultSources;
	}

	@Override
	public ResultSourceInfo createResultSource(ResultSourceInfo sourceInfo,
			ContextInfo context) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		sourceInfo.setId(String.valueOf(Math.random()));
		resultSourcesCache.put(sourceInfo.getId(),sourceInfo);
		return sourceInfo;
	}

	@Override
	public ResultSourceInfo updateResultSource(String resultSourceId,
			ResultSourceInfo resultSourceInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		resultSourcesCache.put(resultSourceId, resultSourceInfo);
		return resultSourceInfo;
	}

	@Override
	public StatusInfo deleteResultSource(String resultSourceId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		resultSourcesCache.remove(resultSourceId);
		return new StatusInfo();
	}

	@Override
	public List<ValidationResultInfo> validateResultSource(
			String validationType, ResultSourceInfo resultSourceInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
    	return new ArrayList<ValidationResultInfo>();
	}

    @Override
    public List<LearningResultRecordInfo> getLearningResultRecordsForLprAndType(String lprId, String lrrType) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return new ArrayList<LearningResultRecordInfo>();
    }

}
