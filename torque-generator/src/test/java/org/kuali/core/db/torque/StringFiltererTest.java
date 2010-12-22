package org.kuali.core.db.torque;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;

import org.junit.Test;

public class StringFiltererTest {

	@Test
	public void testInclude() {
		List<String> includePatterns = new ArrayList<String>();
		includePatterns.add("^KR.*");

		String includeTable = "KREN_CHNL_PRODCR_T";
		String excludeTable = "ORA$$ORA";
		List<String> tableNames = new ArrayList<String>();
		tableNames.add(includeTable);
		tableNames.add(excludeTable);

		StringFilter sf = new StringFilter();
		sf.setIncludePatterns(includePatterns);

		sf.filter(tableNames.iterator());

		assertTrue(tableNames.contains(includeTable));
		assertFalse(tableNames.contains(excludeTable));

	}

	@Test
	public void testExclude() {
		List<String> includePatterns = new ArrayList<String>();
		includePatterns.add("^KR.*");

		List<String> excludePatterns = new ArrayList<String>();
		excludePatterns.add("^ORA.*");
		excludePatterns.add("^ORA.*");

		String includeTable = "KREN_CHNL_PRODCR_T";
		String excludeTable = "ORA$$ORA";
		List<String> tableNames = new ArrayList<String>();
		tableNames.add(includeTable);
		tableNames.add(excludeTable);

		StringFilter sf = new StringFilter();
		sf.setExcludePatterns(excludePatterns);

		sf.filter(tableNames.iterator());

		assertTrue(tableNames.contains(includeTable));
		assertFalse(tableNames.contains(excludeTable));

	}

	@Test
	public void testBoth() {
		List<String> includePatterns = new ArrayList<String>();
		includePatterns.add("^KR.*");

		List<String> excludePatterns = new ArrayList<String>();
		excludePatterns.add("^ORA.*");
		excludePatterns.add("^KRIM.*");

		String includeTable = "KREN_CHNL_PRODCR_T";
		String excludeTable1 = "KRIM_ATTR_DEFN_T";
		String excludeTable2 = "ORA$$ORA";
		List<String> tableNames = new ArrayList<String>();
		tableNames.add(includeTable);
		tableNames.add(excludeTable1);
		tableNames.add(excludeTable2);

		StringFilter sf = new StringFilter();
		sf.setExcludePatterns(excludePatterns);
		sf.setIncludePatterns(includePatterns);

		sf.filter(tableNames.iterator());

		assertTrue(tableNames.contains(includeTable));
		assertFalse(tableNames.contains(excludeTable1));
		assertFalse(tableNames.contains(excludeTable2));

	}

}
