package org.kuali.student.core.dto;

public class DtoConstants {
 
	public static final String DTO_STATE = "DtoState";
	public static final String DTO_NEXT_STATE = "DtoNextState"; 

	//FIXME: Need to split out proposal states (ie. workflow states) versus dto states
	public enum DtoState {
		DRAFT, SUBMITTED, APPROVED, ACTIVE, INACTIVE, RETIRED, SUPERCEDED;

		public boolean equalsString(String state){
			if (state != null){
				return this.toString().equals(state.toUpperCase());
			}
			
			return false;
		}
		
		/**
	     * This is used to determine the next state.
	     * 
	     * TODO: Ideally this method should not be hardcoded here.  Also determining next state may
	     * be a more complicated and not just be a simple sequence.
	     * 
	     * @param state
	     * @return the next state
	     */
		public static DtoState getNextState(String state){
			if (DRAFT.equalsString(state)) {
	            return SUBMITTED;
	        } else if (SUBMITTED.equalsString(state)) {
	            return APPROVED;
	        } else if (APPROVED.equalsString(state)) {
	        	return ACTIVE;
	        } else if (ACTIVE.equalsString(state)) {
	        	return INACTIVE;
	        } else if (INACTIVE.equalsString(state)) {
	        	return RETIRED;
	        }
			
			return null;
		}
		
		public static String getNextStateAsString(String state){
			DtoState dtoState = getNextState(state);
			if (dtoState == null){
				return null;
			} else {
				return dtoState.toString();
			}
		}
	}
}
