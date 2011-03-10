package org.kuali.student.enrollment.lpr.test.utilities;

import org.kuali.student.enrollment.lpr.model.*;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import static org.kuali.student.enrollment.lpr.test.Constants.*;

/**
 * @author Igor
 */
public class DataLoader extends JpaDaoSupport {

    public void load() {
        LuiPersonRelation luiPersonRelation = createLuiPersonRelation();
        EntityManager em = getJpaTemplate().getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.persist(luiPersonRelation);
        em.getTransaction().commit();

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
}
