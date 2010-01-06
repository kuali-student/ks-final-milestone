/*
 * Copyright 2009 The Kuali Foundation
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
package org.kuali.student.dictionary.writer;

/**
 *
 * @author nwright
 */
public class JavaClassFileNameBuilder
{

 private String rootDirectory;
 private String packageName;
 private String className;

 public JavaClassFileNameBuilder (String rootDirectory,
                                  String packageName,
                                  String className)
 {
  this.rootDirectory = rootDirectory;
  this.packageName = packageName;
  this.className = className;
 }


 public String buildDirectory ()
 {
  String dirName = rootDirectory;
  if ( ! dirName.endsWith ("/"))
  {
   dirName += "/";
  }
  dirName += packageName.replace (".", "/");
  return dirName;
 }

 public String build ()
 {
  String fileName = buildDirectory ();
  fileName += "/" + className;
  fileName += ".java";
  return fileName;
 }
}
