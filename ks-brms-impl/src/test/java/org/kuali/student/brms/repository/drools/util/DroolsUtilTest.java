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
