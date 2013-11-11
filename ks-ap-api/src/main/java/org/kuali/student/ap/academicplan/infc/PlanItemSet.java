package org.kuali.student.ap.academicplan.infc;

import org.kuali.student.r2.common.infc.HasId;
import org.kuali.student.r2.common.infc.HasType;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.common.infc.TypeStateEntity;
import org.omg.Security.Public;

import java.util.List;

/**
 *  A collection of plan items
 *
 * @Author kmuthu
 * Date: 1/5/12
 */
public interface PlanItemSet extends HasId, TypeStateEntity {

   /**
     * A description of the Learning Plan
     * @name Description
     */

    public RichText getDescr();

    /**
     * List of plan items that make up the set
     * @name
     */
    public List<String> getPlanItemIds();

    /**
     * N (number) of Items in the set that the student is planning for
     * @return
     */
    public Integer getInterestedInItemsCount();

    /**
     * True if student is interested in all of the items in the set
     * @return
     */
    public boolean isInterestedInAllItems();
}
