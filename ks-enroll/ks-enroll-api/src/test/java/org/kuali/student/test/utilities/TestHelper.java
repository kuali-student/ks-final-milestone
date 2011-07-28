package org.kuali.student.test.utilities;

import org.junit.Ignore;
import org.kuali.student.r2.common.dto.ContextInfo;

@Ignore
public class TestHelper {
	  public static ContextInfo getContext1() {
			return ContextInfo.getInstance("principalId.1", "en", "us");
		}

	    public static ContextInfo getContext2() {
			return ContextInfo.getInstance("principalId.2", "fr", "ca");
		}
}
