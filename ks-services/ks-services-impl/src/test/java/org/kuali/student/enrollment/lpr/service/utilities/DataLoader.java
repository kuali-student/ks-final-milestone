package org.kuali.student.enrollment.lpr.service.utilities;

import org.kuali.student.enrollment.lpr.model.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.kuali.student.enrollment.lpr.service.utilities.Constants.*;

/**
 * @author Igor
 */
public class DataLoader {

    private EntityManager em;

    @Transactional
    public void load() {
        LuiPersonRelation luiPersonRelation = createLuiPersonRelation();
        em.persist(luiPersonRelation);
    }

    private LuiPersonRelation createLuiPersonRelation() {
        LuiPersonRelation personRelation = new LuiPersonRelation();
        personRelation.setLuiId(LUI_ID1);
        personRelation.setPersonId(PERSON_ID1);
        personRelation.setEffectiveDate(Calendar.getInstance().getTime());
        personRelation.setExpirationDate(Calendar.getInstance().getTime());
        personRelation.setPersonRelationState(createPersonRelationState());
        personRelation.setPersonRelationType(createPersonRelationType());
        personRelation.setDynamicAttributes(createDynamicAttributes());
        return personRelation;
    }

    private List<DynamicAttribute> createDynamicAttributes() {
        List<DynamicAttribute> dynamicAttributes = new ArrayList<DynamicAttribute>();
        dynamicAttributes.add(new DynamicAttribute(DynamicType.LUI_PERSON_RELATION, DA_KEY_1, DA_VALUE_1));
        dynamicAttributes.add(new DynamicAttribute(DynamicType.LUI_PERSON_RELATION, DA_KEY_2, DA_VALUE_2));
        return dynamicAttributes;
    }


    private LuiPersonRelationType createPersonRelationType() {
        LuiPersonRelationType personRelationType = new LuiPersonRelationType();
        personRelationType.setName(TEST_VALUE_1);
        personRelationType.setDescription(TEST_VALUE_2);
        return personRelationType;
    }

    private LuiPersonRelationState createPersonRelationState() {
        LuiPersonRelationState personRelationState = new LuiPersonRelationState();
        personRelationState.setName(TEST_VALUE_1);
        personRelationState.setDescription(TEST_VALUE_2);
        return personRelationState;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
