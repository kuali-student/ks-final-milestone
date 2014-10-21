/**
 * Copyright 2005-2013 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.r1.core.workflow.dto;

/**
 * Wrapper around the {@link CollaboratorWrapper} instance for use with KRAD UI components like the StackedCollection
 *
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */


public class CollaboratorWrapper implements Cloneable {

        private static final long serialVersionUID = 1L;
        
        private String entityId;
        private String principalName;        
        private String givenName;
        private String displayName;
        private String principalId;
        private String firstName;
        private String lastName;
        private String id;
        
        private String permission;
        private String action;
        
        private boolean author;
        private boolean canRevokeRequest = true;
        private String actionRequestId;
        private String actionRequestStatus;

        public String getPrincipalId() {
            return principalId;
        }

    @Override
    public CollaboratorWrapper clone() throws CloneNotSupportedException {
        return (CollaboratorWrapper) super.clone();
    }

    public void setPrincipalId(String principalId) {
            this.principalId = principalId;
        }
        public String getFirstName() {
            return firstName;
        }
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
        public String getLastName() {
            return lastName;
        }
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
        public String getPermission() {
            return permission;
        }
        public void setPermission(String permission) {
            this.permission = permission;
        }
        public String getAction() {
            return action;
        }
        public void setAction(String action) {
            this.action = action;
        }
        public boolean isAuthor() {
            return author;
        }
        public void setAuthor(boolean isAuthor) {
            this.author = isAuthor;
        }
        public boolean isCanRevokeRequest() {
            return canRevokeRequest;
        }
        public void setCanRevokeRequest(boolean canRevokeRequest) {
            this.canRevokeRequest = canRevokeRequest;
        }
        public String getActionRequestStatus() {
            return actionRequestStatus;
        }
        public void setActionRequestStatus(String actionRequestStatus) {
            this.actionRequestStatus = actionRequestStatus;
        }
        public String getActionRequestId() {
            return actionRequestId;
        }
        public void setActionRequestId(String actionRequestId) {
            this.actionRequestId = actionRequestId;
        }
        public String getDisplayName() {
            return displayName;
        }
        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }
        public String getGivenName() {
            return givenName;
        }
        public void setGivenName(String givenName) {
            this.givenName = givenName;
        }
        public String getPrincipalName() {
            return principalName;
        }
        public void setPrincipalName(String principalName) {
            this.principalName = principalName;
        }
        public String getEntityId() {
            return entityId;
        }
        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
}
