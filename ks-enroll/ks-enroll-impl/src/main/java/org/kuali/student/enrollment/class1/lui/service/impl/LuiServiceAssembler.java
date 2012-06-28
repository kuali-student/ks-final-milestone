/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class1.lui.service.impl;

import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.entity.Clu;
import org.kuali.student.lum.lu.entity.Lui;
import org.kuali.student.lum.lu.entity.LuiAttribute;
import org.kuali.student.lum.lu.entity.LuiLuiRelation;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.service.impl.BaseAssembler;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class LuiServiceAssembler extends BaseAssembler {

    public static List<LuiInfo> toLuiInfos(List<Lui> entities) {
        List<LuiInfo> dtos = new ArrayList<LuiInfo>(entities.size());
        if (entities != null) {
            for (Lui entity : entities) {
                dtos.add(toLuiInfo(entity));
            }
        }
        return dtos;

    }

    public static LuiInfo toLuiInfo(Lui entity) {
        if (entity == null) {
            return null;
        }
        LuiInfo luiInfo = new LuiInfo();

        BeanUtils.copyProperties(entity, luiInfo, new String[]{"clu",
                    "meta", "attributes"});

        luiInfo.setCluId(entity.getClu().getId());

        luiInfo.setMeta(BaseAssembler.toMetaInfo(entity.getMeta(), entity.getVersionNumber()));

        luiInfo.setAttributes(BaseAssembler.toAttributeMap(entity.getAttributes()));

        return luiInfo;
    }

    public static Lui toLui(boolean isUpdate, LuiInfo luiInfo, LuDao dao)
            throws DoesNotExistException, VersionMismatchException,
            InvalidParameterException {
        if (luiInfo == null) {
            return null;
        }
        Lui lui;

        if (isUpdate) {
            try {
                lui = dao.fetch(Lui.class, luiInfo.getId());
            } catch (DoesNotExistException ex) {
                throw new DoesNotExistException (luiInfo.getId());
            }
            if (null == lui) {
                throw new DoesNotExistException((new StringBuilder()).append(
                        "Lui does not exist for id: ").append(luiInfo.getId()).toString());
            }
            if (!String.valueOf(lui.getVersionNumber()).equals(
                    luiInfo.getMeta().getVersionInd())) {
                throw new VersionMismatchException(
                        "Lui to be updated is not the current version");
            }
        } else {
            lui = new Lui();
        }

        BeanUtils.copyProperties(luiInfo, lui, new String[]{"cluId",
                    "attributes", "meta"});

        lui.setAttributes(BaseAssembler.toGenericAttributes(LuiAttribute.class, luiInfo.getAttributes(), lui, dao));

        Clu clu;
        try {
            clu = dao.fetch(Clu.class, luiInfo.getCluId());
        } catch (DoesNotExistException ex) {
            throw new DoesNotExistException (luiInfo.getCluId());
        }
        if (null == clu) {
            throw new InvalidParameterException((new StringBuilder()).append(
                    "Clu does not exist for id: ").append(luiInfo.getCluId()).toString());
        }
        lui.setClu(clu);
        return lui;
    }

    public static List<LuiLuiRelationInfo> toLuiLuiRelationInfos(
            List<LuiLuiRelation> entities) {
        List<LuiLuiRelationInfo> dtos = new ArrayList<LuiLuiRelationInfo>(
                entities.size());
        if (entities != null) {
            for (LuiLuiRelation entity : entities) {
                dtos.add(toLuiLuiRelationInfo(entity));
            }
        }
        return dtos;
    }

    public static LuiLuiRelationInfo toLuiLuiRelationInfo(LuiLuiRelation entity) {
        if (entity == null) {
            return null;
        }
        LuiLuiRelationInfo dto = new LuiLuiRelationInfo();

        BeanUtils.copyProperties(entity, dto, new String[]{"lui",
                    "relatedLui", "attributes"});

        dto.setLuiId(entity.getLui().getId());
        dto.setRelatedLuiId(entity.getRelatedLui().getId());
        dto.setTypeKey(entity.getLuLuRelationType().getId());
        dto.setAttributes(BaseAssembler.toAttributeMap(entity.getAttributes()));
        dto.setMeta(BaseAssembler.toMetaInfo(entity.getMeta(), entity.getVersionNumber()));
        return dto;
    }


}
