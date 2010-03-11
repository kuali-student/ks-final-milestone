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
package org.kuali.student.dictionary.writer.dict;

/**
 *
 * @author nwright
 */
public class ClassNameDecorator
{
 private String className;

 public ClassNameDecorator (String className)
 {
  this.className = className;
 }

 public String decorate ()
 {
  if (className.startsWith ("core") || className.startsWith ("lum"))
  {
   className = "org.kuali.student." + className;
  }
  if (className.indexOf ("validation") != -1)
  {
   className = className + "Validator";
  }
  else if (className.indexOf ("calculation") != -1)
  {
   className = className + "Calculator";
  }
  return className;
 }
}
