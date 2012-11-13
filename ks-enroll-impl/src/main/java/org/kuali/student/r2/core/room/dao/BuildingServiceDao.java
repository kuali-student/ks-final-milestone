package org.kuali.student.r2.core.room.dao;

import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.core.room.model.RoomBuildingEntity;
import org.kuali.student.r2.core.room.model.RoomEntity;

import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Gordon
 * Date: 11/5/12
 * Time: 3:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildingServiceDao extends GenericEntityDao<RoomBuildingEntity> {

//    private Query sqlFindIdsByKey = null;
//    private Query sqlFindIdsByKeyPair = null;
//    private Query sqlFindIdsByIds = null;
//    private Query sqlFindIdsByKeyAndList = null;

//    public BuildingServiceDao() {
//        super();
//        sqlFindIdsByKey = em.createQuery("SELECT id FROM RoomBuildingEntity WHERE :keyName = :keyValue");
//        sqlFindIdsByKeyPair = em.createQuery("SELECT id FROM RoomBuildingEntity WHERE :keyName0 = :keyValue0 AND :keyName1 = :keyValue1");
//        sqlFindIdsByIds = em.createQuery("SELECT id FROM RoomBuildingEntity WHERE :keyName in (:keyValues)");
//        sqlFindIdsByKeyAndList = em.createQuery("SELECT id FROM RoomBuildingEntity WHERE :keyName0 = :keyValue0 AND :keyName1 in (:keyValues1)");
//    }

    public List<String> findIdsByKey(String keyName, String keyValue) throws DoesNotExistException, InvalidParameterException {
        if (keyName == null || keyName.length() == 0) {
            throw new InvalidParameterException("No keyName specified!");
        }

        if (keyValue == null || keyValue.length() == 0) {
            throw new InvalidParameterException("No value specified for '" + keyName + "'!");
        }

        Query sqlFindIdsByKey = em.createQuery("SELECT id FROM RoomBuildingEntity WHERE " + keyName + " = :keyValue");
        return (sqlFindIdsByKey.setParameter("keyValue", keyValue)).getResultList();
    }

}
