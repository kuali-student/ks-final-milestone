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

import java.util.Date;

public interface VersionInfc {

 /**
  * Set ????
  *
  * Type: String
  *
  * Version independent Id that remains the same across all versions
  */
 public void setVersionIndId(String versionIndId);

 /**
  * Get ????
  *
  * Type: String
  *
  * Version independent Id that remains the same across all versions
  */
 public String getVersionIndId();

 /**
  * Set ????
  *
  * Type: Long
  *
  * The sequence number of the version
  */
 public void setSequenceNumber(Long sequenceNumber);

 /**
  * Get ????
  *
  * Type: Long
  *
  * The sequence number of the version
  */
 public Long getSequenceNumber();

 /**
  * Set ????
  *
  * Type: Date
  *
  * The date and time this version became current.
  */
 public void setCurrentVersionStart(Date currentVersionStart);

 /**
  * Get ????
  *
  * Type: Date
  *
  * The date and time this version became current.
  */
 public Date getCurrentVersionStart();

 /**
  * Set ????
  *
  * Type: Date
  *
  * The date and time when this version stopped being current.
  */
 public void setCurrentVersionEnd(Date currentVersionEnd);

 /**
  * Get ????
  *
  * Type: Date
  *
  * The date and time when this version stopped being current.
  */
 public Date getCurrentVersionEnd();

 /**
  * Set ????
  *
  * Type: String
  *
  * Comments associated with the verison
  */
 public void setVersionComment(String versionComment);

 /**
  * Get ????
  *
  * Type: String
  *
  * Comments associated with the verison
  */
 public String getVersionComment();

 /**
  * Set ????
  *
  * Type: String
  *
  * ???
  */
 public void setVersionedFromId(String versionedFromId);

 /**
  * Get ????
  *
  * Type: String
  *
  * ???
  */
 public String getVersionedFromId();
}

