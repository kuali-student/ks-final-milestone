package org.kuali.student.r2.core.class1.atp.dao;

import java.util.List;

import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.atp.model.AtpEntity;
import org.kuali.student.r2.core.class1.atp.model.AtpRichTextEntity;

public class AtpRichTextDao extends GenericEntityDao<AtpRichTextEntity>{
    @SuppressWarnings({"unchecked"})
    public List<AtpEntity> getAtpEntitiesUsingAtpRichText(String rtKey) {
        return (List<AtpEntity>) em.createQuery("select atp from AtpEntity atp where atp.descr.id=:rtKey").setParameter("rtKey", rtKey).getResultList();
    }


}
