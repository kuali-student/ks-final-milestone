package org.kuali.student.lum.lrc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.entity.RichText;
import org.kuali.student.core.service.impl.BaseAssembler;
import org.kuali.student.lum.lrc.dto.CreditInfo;
import org.kuali.student.lum.lrc.dto.RichTextInfo;
import org.kuali.student.lum.lrc.entity.Credit;
import org.springframework.beans.BeanUtils;

public class LrcServiceAssembler extends BaseAssembler {
    public static CreditInfo toCreditInfo(Credit entity) {
        CreditInfo dto = new CreditInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] { "desc", "attributes", "type" });

        dto.setDesc(toRichTextInfo(entity.getDesc()));
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setType(entity.getType().getId());
        return dto;
    }

    public static List<CreditInfo> toCreditInfos(List<Credit> entities) {
        List<CreditInfo> dtos = new ArrayList<CreditInfo>(entities.size());
        for (Credit entity : entities) {
            dtos.add(toCreditInfo(entity));
        }
        return dtos;
    }


    public static RichTextInfo toRichTextInfo(RichText entity) {
        if(entity==null){
            return null;
        }

        RichTextInfo dto = new RichTextInfo();

        BeanUtils.copyProperties(entity, dto, new String[] { "id" });

        return dto;

    }

}

