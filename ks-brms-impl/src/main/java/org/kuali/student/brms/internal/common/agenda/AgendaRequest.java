/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

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
