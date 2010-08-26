/*
 * Copyright 2010 The Kuali Foundation
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
package org.kuali.student.dictionary.model;

/**
 *
 * @author nwright
 */
public class Project
{

 private String key;

 public String getKey ()
 {
  return key;
 }

 public void setKey (String key)
 {
  this.key = key;
 }

 private String type;

 public String getType ()
 {
  return type;
 }

 public void setType (String type)
 {
  this.type = type;
 }

 private String name;

 public String getName ()
 {
  return name;
 }

 public void setName (String name)
 {
  this.name = name;
 }

 private String description;


 public String getDescription ()
 {
  return description;
 }


 public void setDescription (String description)
 {
  this.description = description;
 }

 private String directory;

 public String getDirectory ()
 {
  return directory;
 }

 public void setDirectory (String directory)
 {
  this.directory = directory;
 }


 private String javaDirectory;

 public String getJavaDirectory ()
 {
  return javaDirectory;
 }

 public void setJavaDirectory (String javaDirectory)
 {
  this.javaDirectory = javaDirectory;
 }

 private String resourcesDirectory;

 public String getResourcesDirectory ()
 {
  return resourcesDirectory;
 }

 public void setResourcesDirectory (String resourcesDirectory)
 {
  this.resourcesDirectory = resourcesDirectory;
 }

 private String status;

 public String getStatus ()
 {
  return status;
 }

 public void setStatus (String status)
 {
  this.status = status;
 }

 private String comments;

 public String getComments ()
 {
  return comments;
 }

 public void setComments (String comments)
 {
  this.comments = comments;
 }


}
