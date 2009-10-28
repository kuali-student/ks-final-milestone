package org.kuali.student.lum.lu.assembly.data.client;

import org.kuali.student.common.assembly.Data;

public class CluInfoData extends Data {

	private static final long serialVersionUID = 1L;
	public static final String CHILD_CLUS = "childClus";

	public CluInfoData getChild(final int index) {
		CluInfoData result = null;
		final Data children = getChildren();
		if (children != null && index < children.size()) {
			result = children.get(index);
		}
		return result;
	}

	public Data getChildren() {
		return super.get(CHILD_CLUS);
	}
}
