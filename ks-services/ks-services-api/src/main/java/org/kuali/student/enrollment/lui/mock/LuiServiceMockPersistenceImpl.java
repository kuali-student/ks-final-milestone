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
package org.kuali.student.enrollment.lui.mock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.infc.ContextInfc;
import org.kuali.student.common.infc.HoldsLuServiceInfc;
import org.kuali.student.common.infc.StatusInfc;
import org.kuali.student.common.infc.ValidationResultInfc;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularRelationshipException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.enrollment.lpr.mock.CopierHelper;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.infc.LuiInfc;
import org.kuali.student.enrollment.lui.infc.LuiLuiRelationInfc;
import org.kuali.student.enrollment.lui.infc.LuiServiceInfc;
import org.kuali.student.lum.lu.service.LuService;

/**
 * @author nwright
 */
public class LuiServiceMockPersistenceImpl implements
        LuiServiceInfc, HoldsLuServiceInfc {

    private LuService luService;

    @Override
    public LuService getLuService() {
        return luService;
    }

    @Override
    public void setLuService(LuService luService) {
        this.luService = luService;
    }
    private Map<String, LuiInfc> luiCache = new HashMap<String, LuiInfc>();
    private Map<String, LuiLuiRelationInfc> llrCache = new HashMap<String, LuiLuiRelationInfc>();

    @Override
    public LuiInfc createLui(String cluId, String atpKey, LuiInfc luiInfo, ContextInfc context)
            throws AlreadyExistsException,
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
    	LuiInfo.Builder builder = new LuiInfo.Builder(luiInfo);
        CopierHelper helper = new CopierHelper();
    	builder.setId(UUID.randomUUID().toString()).setCluId(cluId);
        builder.setAtpKey(atpKey).setMetaInfo(helper.createMeta(context));
        LuiInfc copy = builder.build();
        this.luiCache.put(copy.getId(), copy);
        return copy;
    }

    @Override
    public LuiLuiRelationInfc createLuiLuiRelation(String luiId, String relatedLuiId, String luLuRelationType, LuiLuiRelationInfc luiLuiRelationInfo, ContextInfc context) throws AlreadyExistsException, CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        CopierHelper helper = new CopierHelper();
        LuiLuiRelationInfo.Builder builder = new LuiLuiRelationInfo.Builder(luiLuiRelationInfo).setId(UUID.randomUUID().toString()).setLuiId(luiId);
        LuiLuiRelationInfc copy = builder.setRelatedLuiId(relatedLuiId).setType(luLuRelationType).setMetaInfo(helper.createMeta(context)).build();
        this.llrCache.put(copy.getId(), copy);
        // mirroring what was done before immutable DTO's; why returning copy of copy?
        return new LuiLuiRelationInfo.Builder(copy).build(); 
    }

    @Override
    public StatusInfc deleteLui(String luiId, ContextInfc context) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (this.luiCache.remove(luiId) == null) {
            throw new DoesNotExistException(luiId);
        }
        return new StatusInfo.Builder().setSuccess(Boolean.TRUE).build();
    }

    @Override
    public StatusInfc deleteLuiLuiRelation(String luiLuiRelationId, ContextInfc context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (this.luiCache.remove(luiLuiRelationId) == null) {
            throw new DoesNotExistException(luiLuiRelationId);
        }
        return new StatusInfo.Builder().setSuccess(Boolean.TRUE).build();
    }

    @Override
    public List<String> getAllowedLuiLuiRelationTypesByLuiId(String luiId, String relatedLuiId, ContextInfc context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LuiInfc getLui(String luiId, ContextInfc context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        LuiInfc bean = this.luiCache.get(luiId);
        if (bean == null) {
            throw new DoesNotExistException(luiId);
        }
        return new CopierHelper().makeCopy(bean);
    }

    @Override
    public List<String> getLuiIdsByCluId(String cluId, ContextInfc context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getLuiIdsByRelation(String relatedLuiId, String luLuRelationType, ContextInfc context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getLuiIdsInAtpByCluId(String cluId, String atpKey, ContextInfc context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LuiLuiRelationInfc getLuiLuiRelation(String luiLuiRelationId, ContextInfc context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        LuiLuiRelationInfc bean = this.llrCache.get(luiLuiRelationId);
        if (bean == null) {
            throw new DoesNotExistException(luiLuiRelationId);
        }
        return new CopierHelper().makeCopy(bean);
    }

    @Override
    public List<LuiLuiRelationInfc> getLuiLuiRelationsByLui(String luiId, ContextInfc context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LuiInfc> getLuisByIdList(List<String> luiIdList, ContextInfc context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LuiInfc> getLuisByRelation(String relatedLuiId, String luLuRelationType, ContextInfc context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LuiInfc> getLuisInAtpByCluId(String cluId, String atpKey, ContextInfc context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getRelatedLuiIdsByLuiId(String luiId, String luLuRelationType, ContextInfc context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LuiInfc> getRelatedLuisByLuiId(String luiId, String luLuRelationType, ContextInfc context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LuiInfc updateLui(String luiId, LuiInfc luiInfo, ContextInfc context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        LuiInfc existing = this.luiCache.get(luiId);
        if (existing == null) {
            throw new DoesNotExistException(luiId);
        }
        if (!luiInfo.getMetaInfo().getVersionInd().equals(
                existing.getMetaInfo().getVersionInd())) {
            throw new VersionMismatchException(
                    "Updated by " + existing.getMetaInfo().getUpdateId() + " on "
                    + existing.getMetaInfo().getUpdateId() + " with version of "
                    + existing.getMetaInfo().getVersionInd());
        }
        CopierHelper helper = new CopierHelper();
        LuiInfc copy = new LuiInfo.Builder(luiInfo).setMetaInfo(helper.updateMeta(existing.getMetaInfo(), context)).build();
        this.luiCache.put(luiId, copy);
        // mirroring what was done before immutable DTO's; why returning copy of copy?
        return new LuiInfo.Builder(copy).build(); 
    }

    @Override
    public LuiLuiRelationInfc updateLuiLuiRelation(String luiLuiRelationId, LuiLuiRelationInfc luiLuiRelationInfo, ContextInfc context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        LuiLuiRelationInfc existing = this.llrCache.get(luiLuiRelationId);
        if (existing == null) {
            throw new DoesNotExistException(luiLuiRelationId);
        }
        if (!luiLuiRelationInfo.getMetaInfo().getVersionInd().equals(
                existing.getMetaInfo().getVersionInd())) {
            throw new VersionMismatchException(
                    "Updated by " + existing.getMetaInfo().getUpdateId() + " on "
                    + existing.getMetaInfo().getUpdateId() + " with version of "
                    + existing.getMetaInfo().getVersionInd());
        }
        CopierHelper helper = new CopierHelper();
        LuiLuiRelationInfc copy = new LuiLuiRelationInfo.Builder(luiLuiRelationInfo).setMetaInfo(helper.updateMeta(existing.getMetaInfo(), context)).build();
        this.llrCache.put(luiLuiRelationId, copy);
        // mirroring what was done before immutable DTO's; why returning copy of copy?
        return new LuiLuiRelationInfo.Builder(copy).build(); 

    }

    @Override
    public LuiInfc updateLuiState(String luiId, String luState, ContextInfc context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ValidationResultInfc> validateLui(String validationType, LuiInfc luiInfo, ContextInfc context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ValidationResultInfc> validateLuiLuiRelation(String validationType, LuiLuiRelationInfc luiLuiRelationInfo, ContextInfc context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

