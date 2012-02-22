package org.kuali.student.r1.common.ui.client.mvc;

import java.util.HashSet;

public interface HasCrossConstraints {
	public HashSet<String> getCrossConstraints();
	public void reprocessWithUpdatedConstraints();
}
