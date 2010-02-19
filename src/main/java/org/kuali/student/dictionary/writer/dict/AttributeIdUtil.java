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

import org.kuali.student.dictionary.model.Type;
import org.kuali.student.dictionary.writer.XmlWriter;

/**
 *
 * @author nwright
 */
public class AttributeIdUtil
{

 public void writeAbstractAttribute (XmlWriter writer, String id)
 {
  writeAttribute (writer, id + ".abstract");
  writer.writeAttribute ("abstract", "true");
 }

 public void writeParentToAbstract (XmlWriter writer, String id)
 {
  writer.writeAttribute ("parent", fixId (id + ".abstract"));
 }

 public void writeAttribute (XmlWriter writer, String id)
 {
  writer.writeAttribute ("id", fixId (id));
 }

 public void writeRefBean (XmlWriter writer, String refType, String id)
 {
  writer.incrementIndent ();
  writeRefBeanNoIndent (writer, refType, id);
  writer.decrementIndent ();
 }

 public void writeRefBeanNoIndent (XmlWriter writer, String refType, String id)
 {
  writer.indentPrint ("<" + refType);
  writer.writeAttribute ("bean", fixId (id));
  writer.println ("/>");
 }

 public String fixId (String id)
 {
  id = id.replace (" ", "");
  id = id.replace ('*', '_');
  id = id.replace (',', '_');
  id = id.replace (Type.DEFAULT, "DEFAULT");
//  if (id.startsWith ("field."))
//  {
   id = id.replace (".DEFAULT.DEFAULT", "");
   id = id.replace (".DEFAULT", "");
   id = id.replace (".DEFAULT", "");
//  }
  return id;
 }

}
