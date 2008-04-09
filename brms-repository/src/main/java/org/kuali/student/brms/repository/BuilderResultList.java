package org.kuali.student.brms.repository;

import java.util.ArrayList;

public class BuilderResultList extends ArrayList<BuilderResult> implements java.io.Serializable 
{
	private org.drools.rule.Package pkg;
	
	public BuilderResultList( org.drools.rule.Package pkg )
	{
		this.pkg = pkg;
	}
	
	public org.drools.rule.Package getPackage()
	{
		return this.pkg;
	}
}
