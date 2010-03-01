package org.kuali.student.common.assembly;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.kuali.student.core.assembly.AssemblerFilterManager;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.SaveResult;

public class TestFilterStuff {

	@Test
	public void testFilter() throws AssemblyException{
		AddOneAssembler assembler = new AddOneAssembler();
		MultiplyFilter filter = new MultiplyFilter();
		AssemblerFilterManager<Integer,Integer> mgr = new AssemblerFilterManager<Integer,Integer>(assembler);
		mgr.addFilter(filter);
		mgr.addFilter(new MultiplyFilter());
		SaveResult<Integer> result = mgr.save(new Integer(4));
		System.out.println("Final result is:"+result.getValue());
		assertEquals(28,result.getValue().intValue());
		
	}
}
