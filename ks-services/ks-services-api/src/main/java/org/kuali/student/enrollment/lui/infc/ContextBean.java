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
package org.kuali.student.enrollment.lui.infc;

import java.io.Serializable;

public class ContextBean
        implements ContextInfc, Serializable {

 private static final long serialVersionUID = 1L;
 private String principalId;

 /**
  * Set ????
  *
  * Type: String
  *
  * ???
  */
 @Override
 public void setPrincipalId(String principalId) {
  this.principalId = principalId;
 }

 /**
  * Get ????
  *
  * Type: String
  *
  * ???
  */
 @Override
 public String getPrincipalId() {
  return this.principalId;
 }
 private String localeLanguage;

 /**
  * Set ????
  *
  * Type: String
  *
  * ???
  */
 @Override
 public void setLocaleLanguage(String localeLanguage) {
  this.localeLanguage = localeLanguage;
 }

 /**
  * Get ????
  *
  * Type: String
  *
  * ???
  */
 @Override
 public String getLocaleLanguage() {
  return this.localeLanguage;
 }
 private String localeVariant;

 /**
  * Set ????
  *
  * Type: String
  *
  * ???
  */
 @Override
 public void setLocaleVariant(String localeVariant) {
  this.localeVariant = localeVariant;
 }

 /**
  * Get ????
  *
  * Type: String
  *
  * ???
  */
 @Override
 public String getLocaleVariant() {
  return this.localeVariant;
 }
 private String localeRegion;

 /**
  * Set ????
  *
  * Type: String
  *
  * ???
  */
 @Override
 public void setLocaleRegion(String localeRegion) {
  this.localeRegion = localeRegion;
 }

 /**
  * Get ????
  *
  * Type: String
  *
  * ???
  */
 @Override
 public String getLocaleRegion() {
  return this.localeRegion;
 }
 private String localeScript;

 /**
  * Set ????
  *
  * Type: String
  *
  * ???
  */
 @Override
 public void setLocaleScript(String localeScript) {
  this.localeScript = localeScript;
 }

 /**
  * Get ????
  *
  * Type: String
  *
  * ???
  */
 @Override
 public String getLocaleScript() {
  return this.localeScript;
 }
 private String timeZone;

 /**
  * Set ????
  *
  * Type: String
  *
  * ???
  */
 @Override
 public void setTimeZone(String timeZone) {
  this.timeZone = timeZone;
 }

 /**
  * Get ????
  *
  * Type: String
  *
  * ???
  */
 @Override
 public String getTimeZone() {
  return this.timeZone;
 }
}

