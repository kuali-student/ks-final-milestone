package org.kuali.student.lum.lu.assembly.data.client;


public class CluIdentifierInfoData extends ModifiableData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Properties implements PropertyEnum {
		CLU_ID("cluId"),
		CODE("code"),
		SHORT_NAME("shortName"),
		LONG_NAME("longName"),
		LEVEL("level"),
		DIVISION("division"),
		VARIATION("variation"),
		SUFFIX_CODE("suffixCode"),
		ORG_ID("orgId"),
		TYPE("type"),
		STATE("state"),
		ID("id");

		private final String key;

		private Properties(final String key) {
			this.key = key;
		}

		@Override
		public String getKey() {
			return this.key;
		}
	}

	public CluIdentifierInfoData() {
		super(CluIdentifierInfoData.class.getName());
	}
	public String getCluId() {
		return super.get(Properties.CLU_ID.getKey());
	}
	public String getCode() {
		return super.get(Properties.CODE.getKey());
	}
	public String getShortName() {
		return super.get(Properties.SHORT_NAME.getKey());
	}
	public String getLongName() {
		return super.get(Properties.LONG_NAME.getKey());
	}
	public String getLevel() {
		return super.get(Properties.LEVEL.getKey());
	}
	public String getDivision() {
		return super.get(Properties.DIVISION.getKey());
	}
	public String getVariation() {
		return super.get(Properties.VARIATION.getKey());
	}
	public String getSuffixCode() {
		return super.get(Properties.SUFFIX_CODE.getKey());
	}
	public String getOrgId() {
		return super.get(Properties.ORG_ID.getKey());
	}
	public String getType() {
		return super.get(Properties.TYPE.getKey());
	}
	public String getState() {
		return super.get(Properties.STATE.getKey());
	}
	public String getId() {
		return super.get(Properties.ID.getKey());
	}

	public void setCluId(String cluId) {
		super.set(Properties.CLU_ID.getKey(), cluId);
	}
	public void setCode(String code) {
		super.set(Properties.CODE.getKey(), code);
	}
	public void setShortName(String shortName) {
		super.set(Properties.SHORT_NAME.getKey(), shortName);
	}
	public void setLongName(String longName) {
		super.set(Properties.LONG_NAME.getKey(), longName);
	}
	public void setLevel(String level) {
		super.set(Properties.LEVEL.getKey(), level);
	}
	public void setDivision(String division) {
		super.set(Properties.DIVISION.getKey(), division);
	}
	public void setVariation(String variation) {
		super.set(Properties.VARIATION.getKey(), variation);
	}
	public void setSuffixCode(String suffixCode) {
		super.set(Properties.SUFFIX_CODE.getKey(), suffixCode);
	}
	public void setOrgId(String orgId) {
		super.set(Properties.ORG_ID.getKey(), orgId);
	}
	public void setType(String type) {
		super.set(Properties.TYPE.getKey(), type);
	}
	public void setState(String state) {
		super.set(Properties.STATE.getKey(), state);
	}
	public void setId(String id) {
		super.set(Properties.ID.getKey(), id);
	}

}
