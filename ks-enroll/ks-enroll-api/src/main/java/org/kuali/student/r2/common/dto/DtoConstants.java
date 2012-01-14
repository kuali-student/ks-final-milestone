/*
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.dto;

/**
 * These constants should be in the Constants files for their
 * respective services. This is used quite a bit in lum so left here
 * for compatibility.
 */

@Deprecated
public class DtoConstants {
 
    public final static String STATE_DRAFT = "Draft"; 
    public final static String STATE_SUBMITTED = "Submitted";
    public final static String STATE_WITHDRAWN = "Withdrawn";
    public final static String STATE_APPROVED = "Approved";
    public final static String STATE_NOT_APPROVED = "Not Approved";
    public final static String STATE_ACTIVE = "Active";
    public final static String STATE_INACTIVE = "Inactive";
    public final static String STATE_SUPERSEDED  = "Superseded";
    public final static String STATE_SUSPENDED  = "Suspended";
    public final static String STATE_RETIRED = "Retired";

	
    public static final String DTO_STATE = "DtoState";
    public static final String DTO_NEXT_STATE = "DtoNextState"; 
    
    //FIXME: Need to split out proposal states (ie. workflow states) versus dto states
    public enum DtoState {
        DRAFT, SUBMITTED, APPROVED, ACTIVE, INACTIVE, RETIRED, SUPERSEDED, SAVED, ENROUTE;
        
        public boolean equalsString(String state) {
            if (state != null) {
                return this.toString().equals(state.toUpperCase());
            }
            
            return false;
        }
	
        /**
         * This is used to determine the next state.
         * 
         * TODO: Ideally this method should not be hardcoded here.
         * Also determining next state may be a more complicated and
         * not just be a simple sequence.
         * 
         * @param state
         * @return the next state
         */
        public static DtoState getNextState(String state) {
            // Element States
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
	        
	        // Proposal States
            } else if (SAVED.equalsString(state)) {
                return ENROUTE;
            } else if (ENROUTE.equalsString(state)) {
                return APPROVED;
            }
                    
            return null;
        }
	
        public static String getNextStateAsString(String state) {
            DtoState dtoState = getNextState(state);
            if (dtoState == null) {
                return null;
            } else {
                return dtoState.toString();
            }
        }
    }
}
