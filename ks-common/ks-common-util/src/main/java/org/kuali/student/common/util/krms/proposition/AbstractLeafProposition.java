package org.kuali.student.common.util.krms.proposition;

import org.kuali.rice.krms.framework.engine.Proposition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alubbers
 *
 * Abstraction of propositions with no children
 */
public abstract class AbstractLeafProposition implements Proposition {
    @Override
    public List<Proposition> getChildren() {
        return new ArrayList<Proposition>();
    }

    @Override
    public boolean isCompound() {
        return false;
    }
}
