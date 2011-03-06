	/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.common.infc;

import java.io.Serializable;
import java.util.Date;

public class VersionBean
        implements VersionInfc, Serializable {

 private static final long serialVersionUID = 1L;
 private String versionIndId;

 /**
  * Set ????
  *
  * Type: String
  *
  * Version independent Id that remains the same across all versions
  */
 @Override
 public void setVersionIndId(String versionIndId) {
  this.versionIndId = versionIndId;
 }

 /**
  * Get ????
  *
  * Type: String
  *
  * Version independent Id that remains the same across all versions
  */
 @Override
 public String getVersionIndId() {
  return this.versionIndId;
 }
 private Long sequenceNumber;

 /**
  * Set ????
  *
  * Type: Long
  *
  * The sequence number of the version
  */
 @Override
 public void setSequenceNumber(Long sequenceNumber) {
  this.sequenceNumber = sequenceNumber;
 }

 /**
  * Get ????
  *
  * Type: Long
  *
  * The sequence number of the version
  */
 @Override
 public Long getSequenceNumber() {
  return this.sequenceNumber;
 }
 private Date currentVersionStart;

 /**
  * Set ????
  *
  * Type: Date
  *
  * The date and time this version became current.
  */
 @Override
 public void setCurrentVersionStart(Date currentVersionStart) {
  this.currentVersionStart = currentVersionStart;
 }

 /**
  * Get ????
  *
  * Type: Date
  *
  * The date and time this version became current.
  */
 @Override
 public Date getCurrentVersionStart() {
  return this.currentVersionStart;
 }
 private Date currentVersionEnd;

 /**
  * Set ????
  *
  * Type: Date
  *
  * The date and time when this version stopped being current.
  */
 @Override
 public void setCurrentVersionEnd(Date currentVersionEnd) {
  this.currentVersionEnd = currentVersionEnd;
 }

 /**
  * Get ????
  *
  * Type: Date
  *
  * The date and time when this version stopped being current.
  */
 @Override
 public Date getCurrentVersionEnd() {
  return this.currentVersionEnd;
 }
 private String versionComment;

 /**
  * Set ????
  *
  * Type: String
  *
  * Comments associated with the verison
  */
 @Override
 public void setVersionComment(String versionComment) {
  this.versionComment = versionComment;
 }

 /**
  * Get ????
  *
  * Type: String
  *
  * Comments associated with the verison
  */
 @Override
 public String getVersionComment() {
  return this.versionComment;
 }
 private String versionedFromId;

 /**
  * Set ????
  *
  * Type: String
  *
  * ???
  */
 @Override
 public void setVersionedFromId(String versionedFromId) {
  this.versionedFromId = versionedFromId;
 }

 /**
  * Get ????
  *
  * Type: String
  *
  * ???
  */
 @Override
 public String getVersionedFromId() {
  return this.versionedFromId;
 }
}

