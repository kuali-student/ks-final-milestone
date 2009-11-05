package org.kuali.student.lum.lu.assembly.data.client.atp;
import org.kuali.student.lum.lu.assembly.data.client.ModifiableData;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;
public class TimeAmountInfoData extends ModifiableData {
	private static final long serialVersionUID = 1L;

	public enum Properties implements PropertyEnum {
		ATP_DURATION_TYPE_KEY("atpDurationTypeKey"),
		TIME_QUANTITY("timeQuantity");

		private final String key;
		private Properties(final String key) {
			this.key = key;
		}
		@Override
		public String getKey() {
			return this.key;
		}
	}
	
	public TimeAmountInfoData() {
		super(TimeAmountInfoData.class.getName());
	}
	public String getAtpDurationTypeKey(){
		return super.get(Properties.ATP_DURATION_TYPE_KEY.getKey());
	}
	public void setAtpDurationTypeKey(String atpDurationTypeKey){
		super.set(Properties.ATP_DURATION_TYPE_KEY.getKey(), atpDurationTypeKey);
	}
	public Integer getTimeQuantity(){
		return super.get(Properties.TIME_QUANTITY.getKey());
	}
	public void setTimeQuantity(Integer timeQuantity){
		super.set(Properties.TIME_QUANTITY.getKey(), timeQuantity);
	}
	
}
