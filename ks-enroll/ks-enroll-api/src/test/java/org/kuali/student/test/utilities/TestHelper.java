package org.kuali.student.test.utilities;

import org.kuali.student.r2.common.dto.ContextInfo;

public class TestHelper {
	  public static ContextInfo getContext1() {
			return new ContextInfo.Builder().principalId("principalId.1").localeLanguage("en").localeRegion("us").build();
		}

	    public static ContextInfo getContext2() {
			return new ContextInfo.Builder().principalId("principalId.2").localeLanguage("fr").localeRegion("ca").build();
		}
}
