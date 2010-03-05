package org.kuali.student.brms.internal.common.agenda;

public class AgendaRequest {
        private String luiPersonRelationType;
        private String luType;
        private String luiStatus;
        private String relationState;

        public AgendaRequest(String luiPersonRelationType, 
                             String luType, 
                             String luiStatus, 
                             String relationState) { 
            this.luiPersonRelationType = luiPersonRelationType;
            this.luType = luType;
            this.luiStatus = luiStatus;
            this.relationState = relationState;
        }

        public String getLuiPersonRelationType() {
            return luiPersonRelationType;
        }

        public String getLuType() {
            return luType;
        }

        public String getLuiStatus() {
            return luiStatus;
        }

        public String getRelationState() {
            return relationState;
        }
}
