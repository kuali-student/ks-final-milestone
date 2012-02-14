package org.kuali.student.common.ui.client.mvc;

import java.util.HashSet;

public interface HasCrossConstraints {
	public HashSet<String> getCrossConstraints();
	public void reprocessWithUpdatedConstraints();
}
