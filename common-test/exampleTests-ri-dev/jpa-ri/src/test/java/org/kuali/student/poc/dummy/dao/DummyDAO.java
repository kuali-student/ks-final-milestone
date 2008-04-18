package org.kuali.student.poc.dummy.dao;

import org.kuali.student.poc.dummy.Dummy;
import org.kuali.student.poc.dummy.DummyAttribute;
import java.util.List;

public interface DummyDAO {

	public DummyAttribute createDummyAttribute(DummyAttribute dummyAttribute);
	public boolean deleteDummyAttribute(DummyAttribute dummyAttribute);
	public Dummy createDummy(Dummy dummy);
	public Dummy updateDummy(Dummy dummy);
	public Dummy lookupDummy(long id);
	public boolean deleteDummy(Dummy dummy);

}
