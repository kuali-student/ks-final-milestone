/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.assembly;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.kuali.student.common.assembly.data.AssemblyException;
import org.kuali.student.common.assembly.old.AssemblerFilterManager;
import org.kuali.student.common.assembly.old.data.SaveResult;

public class TestOldFilterStuff {
	final Logger LOG = Logger.getLogger(TestOldFilterStuff.class);
	
	@Test
	public void testFilter() throws AssemblyException{
		AddOneAssembler assembler = new AddOneAssembler();
		MultiplyFilter filter = new MultiplyFilter();
		AssemblerFilterManager<Integer,Integer> mgr = new AssemblerFilterManager<Integer,Integer>(assembler);
		mgr.addFilter(filter);
		mgr.addFilter(new MultiplyFilter());
		SaveResult<Integer> result = mgr.save(new Integer(4));
		LOG.warn("Final result is:"+result.getValue());
		assertEquals(28,result.getValue().intValue());
		
	}
		
}
