package org.kuali.student.enrollment.lpr.dao;

import org.kuali.student.enrollment.lpr.model.LuiPersonRelation;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author Igor
 */
public class LprDao extends GenericEntityDao<LuiPersonRelation> {

    @SuppressWarnings({"unchecked"})
    public List<LuiPersonRelation> getByLuiId(String luiId) {
        return (List<LuiPersonRelation>) getJpaTemplate().find("from LuiPersonRelation pr where pr.luiId=?1", luiId);
    }
}
