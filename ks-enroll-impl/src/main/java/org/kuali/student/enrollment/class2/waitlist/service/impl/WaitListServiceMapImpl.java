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
package org.kuali.student.enrollment.class2.waitlist.service.impl;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.UUIDHelper;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.enrollment.roster.dto.LprRosterEntryInfo;
import org.kuali.student.enrollment.waitlist.dto.WaitListEntryInfo;
import org.kuali.student.enrollment.waitlist.dto.WaitListInfo;
import org.kuali.student.enrollment.waitlist.service.WaitListService;import org.kuali.student.r2.common.dto.ContextInfo;
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


public class WaitListServiceMapImpl implements MockService, WaitListService
{
	// cache variable 
	// The LinkedHashMap is just so the values come back in a predictable order
	private Map<String, WaitListInfo> waitListMap = new LinkedHashMap<String, WaitListInfo>();
	private Map<String, WaitListEntryInfo> waitListEntryMap = new LinkedHashMap<String, WaitListEntryInfo>();

	@Override
	public void clear()
	{
		this.waitListMap.clear ();
		this.waitListEntryMap.clear ();
	}

	
	@Override
	public WaitListInfo getWaitList(String waitListId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_BY_ID
		if (!this.waitListMap.containsKey(waitListId)) {
		   throw new DoesNotExistException(waitListId);
		}
		return new WaitListInfo(this.waitListMap.get (waitListId));
	}
	
	@Override
	public List<WaitListInfo> getWaitListsByIds(List<String> waitListIds, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_BY_IDS
		List<WaitListInfo> list = new ArrayList<WaitListInfo> ();
		for (String id: waitListIds) {
		    list.add (this.getWaitList(id, contextInfo));
		}
		return list;
	}
	
	@Override
	public List<String> getWaitListIdsByType(String waitListTypeKey, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_IDS_BY_TYPE
		List<String> list = new ArrayList<String> ();
		for (WaitListInfo info: waitListMap.values ()) {
			if (waitListTypeKey.equals(info.getTypeKey())) {
			    list.add (info.getId ());
			}
		}
		return list;
	}
	
	@Override
	public List<WaitListInfo> getWaitListsByOffering(String offeringId, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_INFOS_BY_OTHER
		List<WaitListInfo> list = new ArrayList<WaitListInfo> ();
		for (WaitListInfo info: waitListMap.values ()) {
			if (info.getAssociatedOfferingIds().contains(offeringId)) {
			    list.add (new WaitListInfo(info));
			}
		}
		return list;
	}
	
	@Override
	public List<WaitListInfo> getWaitListsByTypeAndOffering(String waitListTypeKey, String offeringId, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_INFOS_BY_OTHER
		List<WaitListInfo> list = new ArrayList<WaitListInfo> ();
		for (WaitListInfo info: waitListMap.values ()) {
			if (waitListTypeKey.equals(info.getTypeKey())) {
                if (info.getAssociatedOfferingIds().contains(offeringId)) {
				    list.add (new WaitListInfo(info));
				}
			}
		}
		return list;
	}
	
	@Override
	public List<String> searchForWaitListIds(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// UNKNOWN
		throw new OperationFailedException ("searchForWaitListIds has not been implemented");
	}
	
	@Override
	public List<WaitListInfo> searchForWaitLists(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// UNKNOWN
		throw new OperationFailedException ("searchForWaitLists has not been implemented");
	}
	
	@Override
	public List<ValidationResultInfo> validateWaitList(String validationTypeKey, String waitListTypeKey, WaitListInfo waitListInfo, ContextInfo contextInfo)
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
	public WaitListInfo createWaitList(String waitListTypeKey, WaitListInfo waitListInfo, ContextInfo contextInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,ReadOnlyException
	{
		// CREATE
		if (!waitListTypeKey.equals (waitListInfo.getTypeKey())) {
		    throw new InvalidParameterException ("The type parameter does not match the type on the info object");
		}
		WaitListInfo copy = new WaitListInfo(waitListInfo);
		if (copy.getId() == null) {
		   copy.setId(UUIDHelper.genStringUUID());
		}
		copy.setMeta(newMeta(contextInfo));
		waitListMap.put(copy.getId(), copy);
		return new WaitListInfo(copy);
	}
	
	@Override
	public WaitListInfo updateWaitList(String waitListId, WaitListInfo waitListInfo, ContextInfo contextInfo)
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
		if (!waitListId.equals (waitListInfo.getId())) {
		    throw new InvalidParameterException ("The id parameter does not match the id on the info object");
		}
		WaitListInfo copy = new WaitListInfo(waitListInfo);
		WaitListInfo old = this.getWaitList(waitListInfo.getId(), contextInfo);
		if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
		    throw new VersionMismatchException(old.getMeta().getVersionInd());
		}
		copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
		this.waitListMap .put(waitListInfo.getId(), copy);
		return new WaitListInfo(copy);
	}
	
	@Override
	public StatusInfo deleteWaitList(String waitListId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// DELETE
		if (this.waitListMap.remove(waitListId) == null) {
		   throw new OperationFailedException(waitListId);
		}
		return newStatus();
	}
	
	@Override
	public WaitListEntryInfo getWaitListEntry(String waitListEntryId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_BY_ID
		if (!this.waitListEntryMap.containsKey(waitListEntryId)) {
		   throw new DoesNotExistException(waitListEntryId);
		}
		return new WaitListEntryInfo(this.waitListEntryMap.get (waitListEntryId));
	}
	
	@Override
	public List<WaitListEntryInfo> getWaitListEntriesByIds(List<String> waitListEntryIds, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_BY_IDS
		List<WaitListEntryInfo> list = new ArrayList<WaitListEntryInfo> ();
		for (String id: waitListEntryIds) {
		    list.add (this.getWaitListEntry(id, contextInfo));
		}
		return list;
	}
	
	@Override
	public List<String> getWaitListEntryIdsByType(String waitListEntryTypeKey, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_IDS_BY_TYPE
		List<String> list = new ArrayList<String> ();
		for (WaitListEntryInfo info: waitListEntryMap.values ()) {
			if (waitListEntryTypeKey.equals(info.getTypeKey())) {
			    list.add (info.getId ());
			}
		}
		return list;
	}
	
	@Override
	public List<WaitListEntryInfo> getWaitListEntriesByStudent(String studentId, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_INFOS_BY_OTHER
		List<WaitListEntryInfo> list = new ArrayList<WaitListEntryInfo> ();
		for (WaitListEntryInfo info: waitListEntryMap.values ()) {
			if (studentId.equals(info.getStudentId())) {
			    list.add (new WaitListEntryInfo(info));
			}
		}
		return list;
	}
	
	@Override
	public List<WaitListEntryInfo> getWaitListEntriesByWaitList(String waitListId, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_INFOS_BY_OTHER
		List<WaitListEntryInfo> list = new ArrayList<WaitListEntryInfo> ();
		for (WaitListEntryInfo info: waitListEntryMap.values ()) {
			if (waitListId.equals(info.getWaitListId())) {
			    list.add (new WaitListEntryInfo(info));
			}
		}

        sortWaitListEntries(list);

		return list;
	}
	
	@Override
	public List<WaitListEntryInfo> getWaitListEntriesByWaitListAndStudent(String waitListId, String studentId, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_INFOS_BY_OTHER
		List<WaitListEntryInfo> list = new ArrayList<WaitListEntryInfo> ();
		for (WaitListEntryInfo info: waitListEntryMap.values ()) {
			if (waitListId.equals(info.getWaitListId())) {
				if (studentId.equals(info.getStudentId())) {
				    list.add (new WaitListEntryInfo(info));
				}
			}
		}
		return list;
	}
	
	@Override
	public List<String> searchForWaitListEntryIds(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// UNKNOWN
		throw new OperationFailedException ("searchForWaitListEntryIds has not been implemented");
	}
	
	@Override
	public List<WaitListEntryInfo> searchForWaitListEntries(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// UNKNOWN
		throw new OperationFailedException ("searchForWaitListEntries has not been implemented");
	}

    @Override
    public List<ValidationResultInfo> validateWaitListEntry(String validationTypeKey, String waitListId, String studentId, String waitListEntryTypeKey, WaitListEntryInfo waitListEntryInfo, ContextInfo contextInfo)
            throws DoesNotExistException
                  ,InvalidParameterException
                  ,MissingParameterException
                  ,OperationFailedException
                  ,PermissionDeniedException {
        return new ArrayList<ValidationResultInfo> ();
    }

    @Override
	public WaitListEntryInfo createWaitListEntry(String waitListId, String studentId, String waitListEntryTypeKey, WaitListEntryInfo waitListEntryInfo, ContextInfo contextInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,ReadOnlyException
	{
		// CREATE
		if (!waitListEntryTypeKey.equals (waitListEntryInfo.getTypeKey())) {
		    throw new InvalidParameterException ("The type parameter does not match the type on the info object");
		}
        if(!waitListId.equals(waitListEntryInfo.getWaitListId())) {
            throw new InvalidParameterException ("The WaitList id parameter does not match the WaitList Id on the info object");
        }
        if(!studentId.equals(waitListEntryInfo.getStudentId())) {
            throw new InvalidParameterException ("The Student Id parameter does not match the Student Id on the info object");
        }

        List<WaitListEntryInfo> entries = getWaitListEntriesByWaitList(waitListId, contextInfo);

		WaitListEntryInfo copy = new WaitListEntryInfo(waitListEntryInfo);
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
		waitListEntryMap.put(copy.getId(), copy);

        entries.add(copy);
        moveWaitListEntryToPosition(copy.getId(), entries, position, contextInfo);

		return new WaitListEntryInfo(copy);
	}
	
	@Override
	public WaitListEntryInfo updateWaitListEntry(String waitListEntryId, WaitListEntryInfo waitListEntryInfo, ContextInfo contextInfo)
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
		if (!waitListEntryId.equals (waitListEntryInfo.getId())) {
		    throw new InvalidParameterException ("The id parameter does not match the id on the info object");
		}
		WaitListEntryInfo copy = new WaitListEntryInfo(waitListEntryInfo);
		WaitListEntryInfo old = this.getWaitListEntry(waitListEntryInfo.getId(), contextInfo);
		if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
		    throw new VersionMismatchException(old.getMeta().getVersionInd());
		}

        //If the position has changed then update the list.
        if(!copy.getPosition().equals(old.getPosition())) {
            moveWaitListEntryToPosition(copy.getId(), copy.getPosition(), contextInfo);
        }

		copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
		this.waitListEntryMap .put(waitListEntryInfo.getId(), copy);
		return new WaitListEntryInfo(copy);
	}
	
	@Override
	public StatusInfo deleteWaitListEntry(String waitListEntryId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
        WaitListEntryInfo entry = getWaitListEntry(waitListEntryId, contextInfo);

        //reorder the positions
        List<WaitListEntryInfo> waitListEntries = getWaitListEntriesByWaitList(entry.getWaitListId(), contextInfo);

        if(entry.getPosition() < waitListEntries.size()) {
            moveWaitListEntryToPosition(waitListEntryId, waitListEntries.size(), contextInfo);
        }

		// DELETE
		if (this.waitListEntryMap.remove(waitListEntryId) == null) {
		   throw new OperationFailedException(waitListEntryId);
		}
		return newStatus();
	}
	
	@Override
	public StatusInfo reorderWaitListEntries(String waitListId, List<String> waitListEntryIds, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
        //This implementation is very very slow.  The assumption was made that this will be acceptable for the Map Impl.
        for(int i = 0; i < waitListEntryIds.size(); i++) {
            moveWaitListEntryToPosition(waitListEntryIds.get(i), i + 1, contextInfo);
        }
        return new StatusInfo();
	}
	
	@Override
	public StatusInfo moveWaitListEntryToPosition(String waitListEntryId, Integer position, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
        WaitListEntryInfo entry = getWaitListEntry(waitListEntryId, contextInfo);

        if(!entry.getPosition().equals(position)) {
            List<WaitListEntryInfo> waitListEntries = getWaitListEntriesByWaitList(entry.getWaitListId(), contextInfo);
            moveWaitListEntryToPosition(waitListEntryId, waitListEntries, position, contextInfo);
        }
        return new StatusInfo();

	}

    private void moveWaitListEntryToPosition(String waitListEntryId, List<WaitListEntryInfo> waitListEntries, Integer position, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException {
        if(position <= 0 || position > waitListEntries.size()) {
            throw new InvalidParameterException("The new position " + position + " for the wait list entry " + waitListEntryId + " is not valid");
        }

        int currentPosition = 1;
        if(currentPosition == position) {
            currentPosition++;
        }

        for(WaitListEntryInfo currentEntry : waitListEntries) {
            boolean entryChanged = false;
            if(!currentEntry.getId().equals(waitListEntryId)) {
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
                WaitListEntryInfo updatedEntry = waitListEntryMap.get(currentEntry.getId());
                updatedEntry.setPosition(currentEntry.getPosition());
                updatedEntry.setMeta(updateMeta(updatedEntry.getMeta(), contextInfo));
            }
        }
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

    private void sortWaitListEntries(List<WaitListEntryInfo> entries) {

        Collections.sort(entries, new Comparator<WaitListEntryInfo>() {
            @Override
            public int compare(WaitListEntryInfo o1, WaitListEntryInfo o2) {
                return o1.getPosition().compareTo(o2.getPosition());
            }
        });

    }
	
}

