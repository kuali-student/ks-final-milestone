package org.kuali.student.enrollment.lpr.test.utilities;

import org.kuali.student.enrollment.lpr.model.LuiPersonRelation;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import javax.persistence.EntityManager;

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
        personRelation.setLuiId("Lui1");
        personRelation.setPersonId("Person1");
        return personRelation;
    }
}
