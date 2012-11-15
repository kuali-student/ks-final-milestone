/**
 * Copyright 2012 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by Gordon on 11/01/2012
 */
package org.kuali.student.r2.core.room.dao;

import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.core.room.model.RoomEntity;

import javax.jws.WebParam;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class RoomServiceDao extends GenericEntityDao<RoomEntity> {

//    private Query sqlFindIdsByKey = null;
//    private Query sqlFindIdsByKeyPair = null;
//    private Query sqlFindIdsByIds = null;
//    private Query sqlFindIdsByKeyAndList = null;

    public RoomServiceDao() {
        super();
//        sqlFindIdsByKey = em.createQuery("SELECT id FROM RoomEntity WHERE :keyName = :keyValue");
//        sqlFindIdsByKeyPair = em.createQuery("SELECT id FROM RoomEntity WHERE :keyName0 = :keyValue0 AND :keyName1 = :keyValue1");
//        sqlFindIdsByIds = em.createQuery("SELECT id FROM RoomEntity WHERE :keyName in (:keyValues)");
//        sqlFindIdsByKeyAndList = em.createQuery("SELECT id FROM RoomEntity WHERE :keyName0 = :keyValue0 AND :keyName1 in (:keyValues1)");
    }

    public List<String> findIdsByKey(String keyName, String keyValue) throws DoesNotExistException, InvalidParameterException {
        if (keyName == null || keyName.length() == 0) {
            throw new InvalidParameterException("No keyName specified!");
        }

        if (keyValue == null || keyValue.length() == 0) {
            throw new InvalidParameterException("No value specified for '" + keyName + "'!");
        }

        Query sqlFindIdsByKey = em.createQuery("SELECT id FROM RoomEntity WHERE " + keyName + " = :keyValue");
        return (sqlFindIdsByKey.setParameter("keyValue", keyValue)).getResultList();
    }

    public List<String> findIdsByKeyPair(Map<String, String> kvPair) throws DoesNotExistException, InvalidParameterException {
        if (kvPair.size() == 0) {
            throw new InvalidParameterException("No key/value pairs specified!");
        }

        if (kvPair.size() == 1) {
            String key = kvPair.keySet().iterator().next();
            return findIdsByKey(key, kvPair.get(key));
        }

        if (kvPair.size() > 2) {
            throw new InvalidParameterException("Too many key/value pairs, expected 2, found " + kvPair.size() + ": " + kvPair.toString());
        }

        String[] keys = new String[2];
        kvPair.keySet().toArray(keys);

        Query sqlFindIdsByKeyPair = em.createQuery("SELECT id FROM RoomEntity WHERE " + keys[0] + " = :keyValue0 AND " + keys[1] + " = :keyValue1");
        return sqlFindIdsByKeyPair.setParameter("keyValue0", kvPair.get(keys[0])).setParameter("keyValue1", kvPair.get(keys[1])).getResultList();
    }

    public List<String> findIdsByList(String keyName, List<String> keyValues) throws DoesNotExistException, InvalidParameterException {
        if (keyName == null || keyName.length() == 0) {
            throw new InvalidParameterException("No keyName specified!");
        }

        if (keyValues == null || keyValues.size() == 0 || keyValues.contains(null) || keyValues.contains("")) {
            throw new InvalidParameterException("Invalid value specified for '" + keyName + "': " + keyValues.toString());
        }

        Query sqlFindIdsByIds = em.createQuery("SELECT id FROM RoomEntity WHERE " + keyName + " in (:keyValues)");
        return sqlFindIdsByIds.setParameter("keyValues", keyValues).getResultList();
    }

    public List<String> findIdsByKeyAndList(String keyName0, String keyValue0, String keyName1, List<String> keyValues1) throws DoesNotExistException, InvalidParameterException {
        if (keyName0 == null || keyName0.length() == 0) {
            throw new InvalidParameterException("No keyName specified!");
        }

        if (keyValue0 == null || keyValue0.length() == 0) {
            throw new InvalidParameterException(("No value specified for '" + keyName0 + "'"));
        }

        if (keyName1 == null || keyName1.length() == 0) {
            throw new InvalidParameterException("No listKeyName specified!");
        }

        if (keyValues1 == null || keyValues1.size() == 0 || keyValues1.contains(null) || keyValues1.contains("")) {
            throw new InvalidParameterException("Invalid value specified for '" + keyName1 +"': " + keyValues1.toString());
        }

        Query sqlFindIdsByKeyAndList =  em.createQuery("SELECT id FROM RoomEntity WHERE " + keyName0 + " = :keyValue0 AND " + keyName1 + " in (:keyValues1)");
        return sqlFindIdsByKeyAndList.setParameter("keyValue0", keyValue0).setParameter("keyValues1", keyValues1).getResultList();
    }
}
