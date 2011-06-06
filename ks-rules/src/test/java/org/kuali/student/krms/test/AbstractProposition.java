package org.kuali.student.krms.test;

import java.util.List;
import java.util.ArrayList;

import org.kuali.rice.krms.framework.engine.Proposition;

public abstract class AbstractProposition implements Proposition {
	
    @Override
	public List<Proposition> getChildren() {
    	return new ArrayList<Proposition>();
    }
    
    @Override
    public boolean isCompound() {
    	return false;
    }

    

}