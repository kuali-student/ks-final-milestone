package org.kuali.student.lum.lu.assembly.data.client;

import org.kuali.student.common.assembly.client.Data;

public class RichTextInfoData extends Data {
	public enum Properties implements PropertyEnum {
		FORMATTED("formatted"), PLAIN("plain");
		
		private final String key;
		private Properties(final String key) {
			this.key = key;
		}
		@Override
		public String getKey() {
			return this.key;
		}
		
	}
	private static final long serialVersionUID = 1L;
	
	public String getFormatted() {
		return super.get(Properties.FORMATTED.getKey());
	}
	public String getPlain() {
		return super.get(Properties.PLAIN.getKey());
	}
	public void setFormatted(String s) {
		super.set(Properties.FORMATTED.getKey(), s);
	}
	public void setPlain(String s) {
		super.set(Properties.PLAIN.getKey(), s);
	}
}
