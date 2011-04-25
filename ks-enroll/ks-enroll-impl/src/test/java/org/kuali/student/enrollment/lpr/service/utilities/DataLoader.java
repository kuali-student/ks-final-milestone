package org.kuali.student.enrollment.lpr.service.utilities;

import static org.kuali.student.enrollment.lpr.service.utilities.Constants.DA_KEY_1;
import static org.kuali.student.enrollment.lpr.service.utilities.Constants.DA_KEY_2;
import static org.kuali.student.enrollment.lpr.service.utilities.Constants.DA_VALUE_1;
import static org.kuali.student.enrollment.lpr.service.utilities.Constants.DA_VALUE_2;
import static org.kuali.student.enrollment.lpr.service.utilities.Constants.LUI_ID1;
import static org.kuali.student.enrollment.lpr.service.utilities.Constants.PERSON_ID1;
import static org.kuali.student.enrollment.lpr.service.utilities.Constants.TEST_VALUE_1;
import static org.kuali.student.enrollment.lpr.service.utilities.Constants.TEST_VALUE_2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import org.kuali.student.enrollment.classI.lpr.model.LuiPersonRelationAttributeEntity;
import org.kuali.student.enrollment.classI.lpr.model.LuiPersonRelationEntity;
import org.kuali.student.enrollment.classI.lpr.model.LuiPersonRelationStateEntity;
import org.kuali.student.enrollment.classI.lpr.model.LuiPersonRelationTypeEntity;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Igor
 */
public class DataLoader {

    private EntityManager em;

    @Transactional
    public void load() {
        LuiPersonRelationEntity luiPersonRelation = createLuiPersonRelation();
        em.persist(luiPersonRelation);
    }

    private LuiPersonRelationEntity createLuiPersonRelation() {
        LuiPersonRelationEntity personRelation = new LuiPersonRelationEntity();
        personRelation.setLuiId(LUI_ID1);
        personRelation.setPersonId(PERSON_ID1);
        personRelation.setEffectiveDate(Calendar.getInstance().getTime());
        personRelation.setExpirationDate(Calendar.getInstance().getTime());
        personRelation.setPersonRelationState(createPersonRelationState());
        personRelation.setPersonRelationType(createPersonRelationType());
        personRelation.setAttributes(createAttributes());
        return personRelation;
    }

    private List<LuiPersonRelationAttributeEntity> createAttributes() {
        List<LuiPersonRelationAttributeEntity> attributes = new ArrayList<LuiPersonRelationAttributeEntity>();
        attributes.add(new LuiPersonRelationAttributeEntity(DA_KEY_1, DA_VALUE_1));
        attributes.add(new LuiPersonRelationAttributeEntity(DA_KEY_2, DA_VALUE_2));
        return attributes;
    }


    private LuiPersonRelationTypeEntity createPersonRelationType() {
        LuiPersonRelationTypeEntity personRelationType = new LuiPersonRelationTypeEntity();
        personRelationType.setName(TEST_VALUE_1);
        personRelationType.setDescr(TEST_VALUE_2);
        return personRelationType;
    }

    private LuiPersonRelationStateEntity createPersonRelationState() {
        LuiPersonRelationStateEntity personRelationState = new LuiPersonRelationStateEntity();
        personRelationState.setName(TEST_VALUE_1);
        personRelationState.setDescription(TEST_VALUE_2);
        return personRelationState;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
