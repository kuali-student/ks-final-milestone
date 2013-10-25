package org.kuali.student.cm.course.form;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CollaboratorWrapper {

        private static final long serialVersionUID = 1L;
        
        private String personID;
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
        private boolean canRevokeRequest = false;
        private String actionRequestId;
        private String actionRequestStatus;
        
        public String getPrincipalId() {
            return principalId;
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
        public String getPersonID() {
            return personID;
        }
        public void setPersonID(String personID) {
            this.personID = personID;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
}
