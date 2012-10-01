package org.kuali.student.enrollment.class1.lpr.service.utilities;

import static org.kuali.student.enrollment.class1.lpr.service.utilities.Constants.DA_KEY_1;
import static org.kuali.student.enrollment.class1.lpr.service.utilities.Constants.DA_KEY_2;
import static org.kuali.student.enrollment.class1.lpr.service.utilities.Constants.DA_VALUE_1;
import static org.kuali.student.enrollment.class1.lpr.service.utilities.Constants.DA_VALUE_2;
import static org.kuali.student.enrollment.class1.lpr.service.utilities.Constants.LUI_ID1;
import static org.kuali.student.enrollment.class1.lpr.service.utilities.Constants.PERSON_ID1;
import static org.kuali.student.enrollment.class1.lpr.service.utilities.Constants.TEST_VALUE_1;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.kuali.student.enrollment.class1.lpr.model.LprAttributeEntity;
import org.kuali.student.enrollment.class1.lpr.model.LprEntity;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Igor
 */
public class DataLoader {

    private EntityManager em;

    @Transactional
    public void load() {
        LprEntity luiPersonRelation = createLuiPersonRelation();
        em.persist(luiPersonRelation);
    }

    private LprEntity createLuiPersonRelation() {
        LprEntity personRelation = new LprEntity();
        personRelation.setLuiId(LUI_ID1);
        personRelation.setPersonId(PERSON_ID1);
        personRelation.setEffectiveDate(Calendar.getInstance().getTime());
        personRelation.setExpirationDate(Calendar.getInstance().getTime());
        personRelation.setPersonRelationStateId(TEST_VALUE_1);
        personRelation.setPersonRelationTypeId(TEST_VALUE_1);
        personRelation.setAttributes(createAttributes(personRelation));
        return personRelation;
    }


    private Set<LprAttributeEntity> createAttributes(LprEntity personRelation) {
        Set<LprAttributeEntity> attributes = new HashSet<LprAttributeEntity>();
        
        attributes.add (new LprAttributeEntity(new AttributeInfo(DA_KEY_1, DA_VALUE_1), personRelation));
        attributes.add (new LprAttributeEntity(new AttributeInfo(DA_KEY_2, DA_VALUE_2), personRelation));
        
        return attributes;
    }

   
    public void setEm(EntityManager em) {
        this.em = em;
    }
}
