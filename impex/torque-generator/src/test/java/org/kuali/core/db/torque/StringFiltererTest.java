package org.kuali.core.db.torque;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;

import org.junit.Test;

public class StringFiltererTest {

	@Test
	public void test1() {
		List<String> includePatterns = new ArrayList<String>();
		includePatterns.add("^KR.*");

		String includeTable = "KREN_CHNL_PRODCR_T";
		String excludeTable = "ORA$$ORA";
		List<String> tableNames = new ArrayList<String>();
		tableNames.add(includeTable);
		tableNames.add(excludeTable);

		StringFilterer sf = new StringFilterer();
		sf.setIncludePatterns(includePatterns);

		sf.filterStrings(tableNames.iterator());

		assertTrue(tableNames.contains(includeTable));
		assertFalse(tableNames.contains(excludeTable));

	}

}
