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

package org.kuali.student.brms.repository.drools.util;

import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.brms.repository.drools.util.DroolsUtil;

public class DroolsUtilTest {

	@Test
	public void testBuildKnowledgePackage_Invalid() throws Exception {
		String drl = 
			"rule \"rule1\"\n" +
			"     when\n" +
			"         invalid source\n" +
			"     then\n" +
			"          System.out.println( \"Hello\");\n" +
			"end";
		StringReader sr = new StringReader(drl);
		try {
			DroolsUtil.getInstance().buildKnowledgePackage(sr);
			Assert.fail("Building package from invalid source should have failed");
		} catch(Exception e) {
			Assert.assertTrue(e.getMessage().startsWith("Compiling DRL failed:"));
		}
	}

	@Test
	public void testBuildKnowledgePackage_Valid() throws Exception {
		String drl = 
			"rule \"rule1\"\n" +
			"     when\n" +
			"         eval(true)\n" +
			"     then\n" +
			"          System.out.println( \"Hello\");\n" +
			"end";
		StringReader sr = new StringReader(drl);
		DroolsUtil.getInstance().buildKnowledgePackage(sr);
		Assert.assertTrue(true);
	}
}
