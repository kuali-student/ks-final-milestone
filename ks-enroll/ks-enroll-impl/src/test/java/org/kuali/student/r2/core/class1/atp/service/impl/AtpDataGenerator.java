package org.kuali.student.r2.core.class1.atp.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import org.kuali.student.r2.core.class1.atp.model.AtpAttributeEntity;
import org.kuali.student.r2.core.class1.atp.model.AtpEntity;
import org.kuali.student.r2.core.class1.atp.model.AtpRichTextEntity;
import org.kuali.student.r2.core.class1.atp.model.AtpStateEntity;
import org.kuali.student.r2.core.class1.atp.model.AtpTypeEntity;
import org.springframework.transaction.annotation.Transactional;

public class AtpDataGenerator {
    private EntityManager em;

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void load() {
        AtpEntity atp = createAtpEntity();
        em.persist(atp);
    }
    
    private AtpEntity createAtpEntity(){
        AtpEntity atp = new AtpEntity();
        atp.setId("testId");
        atp.setName("testId");
        atp.setDescr(new AtpRichTextEntity("testId plain descr", "testId formatted descr"));
        atp.setStartDate(Calendar.getInstance().getTime());
        atp.setEndDate(Calendar.getInstance().getTime());
        atp.setAtpState(createState());
        atp.setAtpType(createType());
        atp.setAttributes(createAttributes());
        return atp;
    }
    
    
    private List<AtpAttributeEntity> createAttributes() {
        List<AtpAttributeEntity> attributes = new ArrayList<AtpAttributeEntity>();
        attributes.add(new AtpAttributeEntity("key1", "value1"));
        attributes.add(new AtpAttributeEntity("key2", "value2"));
        return attributes;
    }


    private AtpTypeEntity createType() {
        AtpTypeEntity atpType = new AtpTypeEntity();
        atpType.setName("type1");
        atpType.setDescr("type1 descr");
        return atpType;
    }

    private AtpStateEntity createState() {
        AtpStateEntity atpState = new AtpStateEntity();
        atpState.setName("state1");
        atpState.setDescription("state1 descr");
        return atpState;
    }

}
