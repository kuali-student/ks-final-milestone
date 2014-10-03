/*
 * Copyright 2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Lic+ense is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.roster.service.impl;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.enrollment.roster.dto.LprRosterEntryInfo;
import org.kuali.student.enrollment.roster.dto.LprRosterInfo;
import org.kuali.student.enrollment.roster.service.LprRosterService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import javax.jws.WebParam;


public class LprRosterServiceMapImpl implements MockService, LprRosterService
{
	// cache variable 
	// The LinkedHashMap is just so the values come back in a predictable order
	private Map<String, LprRosterInfo> lprRosterMap = new LinkedHashMap<String, LprRosterInfo>();
	private Map<String, LprRosterEntryInfo> lprRosterEntryMap = new LinkedHashMap<String, LprRosterEntryInfo>();

	@Override
	public void clear()
	{
		this.lprRosterMap.clear ();
		this.lprRosterEntryMap.clear ();
	}

	
	@Override
	public LprRosterInfo getLprRoster(String lprRosterId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_BY_ID
		if (!this.lprRosterMap.containsKey(lprRosterId)) {
		   throw new DoesNotExistException(lprRosterId);
		}
		return new LprRosterInfo(this.lprRosterMap.get (lprRosterId));
	}
	
	@Override
	public List<LprRosterInfo> getLprRostersByIds(List<String> lprRosterIds, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_BY_IDS
		List<LprRosterInfo> list = new ArrayList<LprRosterInfo> ();
		for (String id: lprRosterIds) {
		    list.add (this.getLprRoster(id, contextInfo));
		}
		return list;
	}
	
	@Override
	public List<String> getLprRosterIdsByType(String lprRosterTypeKey, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_IDS_BY_TYPE
		List<String> list = new ArrayList<String> ();
		for (LprRosterInfo info: lprRosterMap.values ()) {
			if (lprRosterTypeKey.equals(info.getTypeKey())) {
			    list.add (info.getId ());
			}
		}
		return list;
	}
	
	@Override
	public List<LprRosterInfo> getLprRostersByLui(String luiId, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_INFOS_BY_OTHER
		List<LprRosterInfo> list = new ArrayList<LprRosterInfo> ();
		for (LprRosterInfo info: lprRosterMap.values ()) {
            if(info.getAssociatedLuiIds().contains(luiId)) {
                list.add (new LprRosterInfo(info));
            }
		}
		return list;
	}
	
	@Override
	public List<LprRosterInfo> getLprRostersByTypeAndLui(String lprRosterTypeKey, String luiId, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_INFOS_BY_OTHER
		List<LprRosterInfo> list = new ArrayList<LprRosterInfo> ();
		for (LprRosterInfo info: lprRosterMap.values ()) {
			if (lprRosterTypeKey.equals(info.getTypeKey())) {
                if(info.getAssociatedLuiIds().contains(luiId)) {
                    list.add (new LprRosterInfo(info));
                }
			}
		}
		return list;
	}
	
	@Override
	public List<String> searchForLprRosterIds(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// UNKNOWN
		throw new OperationFailedException ("searchForLprRosterIds has not been implemented");
	}
	
	@Override
	public List<LprRosterInfo> searchForLprRosters(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// UNKNOWN
		throw new OperationFailedException ("searchForLprRosters has not been implemented");
	}
	
	@Override
	public List<ValidationResultInfo> validateLprRoster(String validationTypeKey, String lprRosterTypeKey, LprRosterInfo lprRosterInfo, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// VALIDATE
		return new ArrayList<ValidationResultInfo> ();
	}
	
	@Override
	public LprRosterInfo createLprRoster(String lprRosterTypeKey, LprRosterInfo lprRosterInfo, ContextInfo contextInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,ReadOnlyException
	{
		// CREATE
        lprRosterInfo.setTypeKey(lprRosterTypeKey);
		LprRosterInfo copy = new LprRosterInfo(lprRosterInfo);
		if (copy.getId() == null) {
		   copy.setId(UUIDHelper.genStringUUID());
		}
		copy.setMeta(newMeta(contextInfo));
		lprRosterMap.put(copy.getId(), copy);
		return new LprRosterInfo(copy);
	}
	
	@Override
	public LprRosterInfo updateLprRoster(String lprRosterId, LprRosterInfo lprRosterInfo, ContextInfo contextInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,ReadOnlyException
		      ,VersionMismatchException
	{
		// UPDATE
		if (!lprRosterId.equals (lprRosterInfo.getId())) {
		    throw new InvalidParameterException ("The id parameter does not match the id on the info object");
		}
		LprRosterInfo copy = new LprRosterInfo(lprRosterInfo);
		LprRosterInfo old = this.getLprRoster(lprRosterInfo.getId(), contextInfo);
		if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
		    throw new VersionMismatchException(old.getMeta().getVersionInd());
		}
		copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
		this.lprRosterMap .put(lprRosterInfo.getId(), copy);
		return new LprRosterInfo(copy);
	}
	
	@Override
	public StatusInfo deleteLprRoster(String lprRosterId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// DELETE
		if (this.lprRosterMap.remove(lprRosterId) == null) {
		   throw new OperationFailedException(lprRosterId);
		}
		return newStatus();
	}
	
	@Override
	public LprRosterEntryInfo getLprRosterEntry(String lprRosterEntryId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_BY_ID
		if (!this.lprRosterEntryMap.containsKey(lprRosterEntryId)) {
		   throw new DoesNotExistException(lprRosterEntryId);
		}
		return new LprRosterEntryInfo(this.lprRosterEntryMap.get (lprRosterEntryId));
	}
	
	@Override
	public List<LprRosterEntryInfo> getLprRosterEntriesByIds(List<String> lprRosterEntryIds, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_BY_IDS
		List<LprRosterEntryInfo> list = new ArrayList<LprRosterEntryInfo> ();
		for (String id: lprRosterEntryIds) {
		    list.add (this.getLprRosterEntry(id, contextInfo));
		}
		return list;
	}
	
	@Override
	public List<String> getLprRosterEntryIdsByType(String lprRosterEntryTypeKey, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_IDS_BY_TYPE
		List<String> list = new ArrayList<String> ();
		for (LprRosterEntryInfo info: lprRosterEntryMap.values ()) {
			if (lprRosterEntryTypeKey.equals(info.getTypeKey())) {
			    list.add (info.getId ());
			}
		}
		return list;
	}
	
	@Override
	public List<LprRosterEntryInfo> getLprRosterEntriesByLprRoster(String lprRosterId, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_INFOS_BY_OTHER
		List<LprRosterEntryInfo> list = new ArrayList<LprRosterEntryInfo> ();
		for (LprRosterEntryInfo info: lprRosterEntryMap.values ()) {
			if (lprRosterId.equals(info.getLprRosterId())) {
			    list.add (new LprRosterEntryInfo(info));
			}
		}
        sortLprRosterEntries(list);
		return list;
	}
	
	@Override
	public List<LprRosterEntryInfo> getLprRosterEntriesByLpr(String lprId, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_INFOS_BY_OTHER
		List<LprRosterEntryInfo> list = new ArrayList<LprRosterEntryInfo> ();
		for (LprRosterEntryInfo info: lprRosterEntryMap.values ()) {
			if (lprId.equals(info.getLprId())) {
			    list.add (new LprRosterEntryInfo(info));
			}
		}
		return list;
	}
	
	@Override
	public List<LprRosterEntryInfo> getLprRosterEntriesByLprRosterAndLpr(String lprRosterId, String lprId, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_INFOS_BY_OTHER
		List<LprRosterEntryInfo> list = new ArrayList<LprRosterEntryInfo> ();
		for (LprRosterEntryInfo info: lprRosterEntryMap.values ()) {
			if (lprRosterId.equals(info.getLprRosterId())) {
				if (lprId.equals(info.getLprId())) {
				    list.add (new LprRosterEntryInfo(info));
				}
			}
		}
		return list;
	}
	
	@Override
	public List<String> searchForLprRosterEntryIds(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// UNKNOWN
		throw new OperationFailedException ("searchForLprRosterEntryIds has not been implemented");
	}
	
	@Override
	public List<LprRosterEntryInfo> searchForLprRosterEntries(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// UNKNOWN
		throw new OperationFailedException ("searchForLprRosterEntries has not been implemented");
	}
	
	@Override
    public List<ValidationResultInfo> validateLprRosterEntry(String validationTypeKey, String lprRosterId, String lprId, String lprRosterEntryTypeKey, LprRosterEntryInfo lprRosterEntryInfo, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// VALIDATE
		return new ArrayList<ValidationResultInfo> ();
	}
	
	@Override
	public LprRosterEntryInfo createLprRosterEntry(String lprRosterId, String lprId, String lprRosterEntryTypeKey, LprRosterEntryInfo lprRosterEntryInfo, ContextInfo contextInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,ReadOnlyException
	{
		// CREATE
        lprRosterEntryInfo.setTypeKey(lprRosterEntryTypeKey);
        lprRosterEntryInfo.setLprId(lprId);
        lprRosterEntryInfo.setLprRosterId(lprRosterId);

        List<LprRosterEntryInfo> entries = getLprRosterEntriesByLprRoster(lprRosterId, contextInfo);


		LprRosterEntryInfo copy = new LprRosterEntryInfo(lprRosterEntryInfo);
        int position = 0;
        if(copy.getPosition() == null) {
            position = entries.size() + 1;
            copy.setPosition(position);
        } else {
            position = copy.getPosition();
            copy.setPosition(entries.size() + 1);
        }


		if (copy.getId() == null) {
		   copy.setId(UUIDHelper.genStringUUID());
		}
		copy.setMeta(newMeta(contextInfo));
		lprRosterEntryMap.put(copy.getId(), copy);

        entries.add(copy);
        moveLprRosterEntryToPosition(copy.getId(), entries, position, contextInfo);

		return getLprRosterEntry(copy.getId(), contextInfo);
	}
	
	@Override
	public LprRosterEntryInfo updateLprRosterEntry(String lprRosterEntryId, LprRosterEntryInfo lprRosterEntryInfo, ContextInfo contextInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,ReadOnlyException
		      ,VersionMismatchException
	{
		// UPDATE
		if (!lprRosterEntryId.equals (lprRosterEntryInfo.getId())) {
		    throw new InvalidParameterException ("The id parameter does not match the id on the info object");
		}



		LprRosterEntryInfo copy = new LprRosterEntryInfo(lprRosterEntryInfo);
		LprRosterEntryInfo old = this.getLprRosterEntry(lprRosterEntryInfo.getId(), contextInfo);
		if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
		    throw new VersionMismatchException(old.getMeta().getVersionInd());
		}

        //If the position has changed then update the list.
        if(!copy.getPosition().equals(old.getPosition())) {
            moveLprRosterEntryToPosition(copy.getId(), copy.getPosition(), contextInfo);
        }

		copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
		this.lprRosterEntryMap.put(lprRosterEntryInfo.getId(), copy);

		return new LprRosterEntryInfo(copy);
	}
	
	@Override
	public StatusInfo deleteLprRosterEntry(String lprRosterEntryId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
        LprRosterEntryInfo entry = getLprRosterEntry(lprRosterEntryId, contextInfo);

        //reorder the positions
        List<LprRosterEntryInfo> rosterEntries = getLprRosterEntriesByLprRoster(entry.getLprRosterId(), contextInfo);

        if(entry.getPosition() < rosterEntries.size()) {
            moveLprRosterEntryToPosition(lprRosterEntryId, rosterEntries.size(), contextInfo);
        }

        // DELETE
        if (this.lprRosterEntryMap.remove(lprRosterEntryId) == null) {
            throw new OperationFailedException(lprRosterEntryId);
        }

		return newStatus();
	}
	
	@Override
	public StatusInfo moveLprRosterEntryToPosition(String lprRosterEntryId, Integer position, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{


        LprRosterEntryInfo entry = getLprRosterEntry(lprRosterEntryId, contextInfo);

        if(!entry.getPosition().equals(position)) {
            List<LprRosterEntryInfo> rosterEntries = getLprRosterEntriesByLprRoster(entry.getLprRosterId(), contextInfo);
            moveLprRosterEntryToPosition(lprRosterEntryId, rosterEntries, position, contextInfo);
        }
        return new StatusInfo();
	}

    @Override
    public StatusInfo reorderLprRosterEntries(String lprRosterId, List<String> lprRosterEntryIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException {
        //This implementation is very very slow.  The assumption was made that this will be acceptable for the Map Impl.
        for(int i = 0; i < lprRosterEntryIds.size(); i++) {
            moveLprRosterEntryToPosition(lprRosterEntryIds.get(i), i + 1, contextInfo);
        }
        return new StatusInfo();
    }

    private void moveLprRosterEntryToPosition(String lprRosterEntryId, List<LprRosterEntryInfo> rosterEntries, Integer position, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException {
        if(position <= 0 || position > rosterEntries.size()) {
            throw new InvalidParameterException("The new position " + position + " for the roster entry " + lprRosterEntryId + " is not valid");
        }

        int currentPosition = 1;
        if(currentPosition == position) {
            currentPosition++;
        }

        for(LprRosterEntryInfo currentEntry : rosterEntries) {
            boolean entryChanged = false;
            if(!currentEntry.getId().equals(lprRosterEntryId)) {
                if(!currentEntry.getPosition().equals(currentPosition)) {
                    currentEntry.setPosition(currentPosition);
                    entryChanged = true;
                }
                currentPosition++;
                if(currentPosition == position) {
                    currentPosition++;
                }
            } else {
                currentEntry.setPosition(position);
                entryChanged = true;
            }

            if(entryChanged) {
                //update directly to prevent cycle
                LprRosterEntryInfo updatedEntry = lprRosterEntryMap.get(currentEntry.getId());
                updatedEntry.setPosition(currentEntry.getPosition());
                updatedEntry.setMeta(updateMeta(updatedEntry.getMeta(), contextInfo));
            }
        }
    }

    private void sortLprRosterEntries(List<LprRosterEntryInfo> entries) {

        Collections.sort(entries, new Comparator<LprRosterEntryInfo>() {
            @Override
            public int compare(LprRosterEntryInfo o1, LprRosterEntryInfo o2) {
                return o1.getPosition().compareTo(o2.getPosition());
            }
        });

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
	
	private MetaInfo updateMeta(MetaInfo old, ContextInfo context) {
	     MetaInfo meta = new MetaInfo(old);
	     meta.setUpdateId(context.getPrincipalId());
	     meta.setUpdateTime(new Date());
	     meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
	     return meta;
	}
	
}

