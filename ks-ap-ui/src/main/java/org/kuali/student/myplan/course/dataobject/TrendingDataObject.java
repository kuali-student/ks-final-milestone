package org.kuali.student.myplan.course.dataobject;

import java.io.Serializable;

public class TrendingDataObject implements Serializable {

	private static final long serialVersionUID = -1993776834081388681L;

	public static class Builder {
		private String xid;
		private String keyword;

		public String getXid() {
			return xid;
		}

		public void setXid(String xid) {
			this.xid = xid;
		}

		public String getKeyword() {
			return keyword;
		}

		public void setKeyword(String keyword) {
			this.keyword = keyword;
		}

		public TrendingDataObject build() {
			return new TrendingDataObject(xid, keyword);
		}
	}

	private final String xid;
	private final String keyword;

	private TrendingDataObject(String xid, String keyword) {
		this.xid = xid;
		this.keyword = keyword;

	}

	public String getXid() {
		return xid;
	}

	public String getKeyword() {
		return keyword;
	}

}
