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
package org.kuali.student.dictionary.writer.service;

import java.util.List;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.Service;
import org.kuali.student.dictionary.model.ServiceMethod;
import org.kuali.student.dictionary.model.ServiceMethodError;
import org.kuali.student.dictionary.model.ServiceMethodParameter;
import org.kuali.student.dictionary.writer.JavaClassWriter;

/**
 *
 * @author nwright
 */
public class ServiceMethodsWriter extends JavaClassWriter
{

 private DictionaryModel model;
 private String directory;
 private String rootPackage;
 private Service service;
 private List<ServiceMethod> methods;

 public ServiceMethodsWriter (DictionaryModel model,
                              String directory,
                              String rootPackage,
                              Service service,
                              List<ServiceMethod> methods)
 {
  super (directory, calcPackage (service, rootPackage), calcClassName (service));
  this.model = model;
  this.directory = directory;
  this.rootPackage = rootPackage;
  this.service = service;
  this.methods = methods;
 }

 public static String calcPackage (Service service, String rootPackage)
 {
  String pack = rootPackage + "." + service.getKey ().
   toLowerCase () + ".";
  StringBuffer buf = new StringBuffer (service.getVersion ().length ());
  for (int i = 0; i < service.getVersion ().length (); i ++)
  {
   char c = service.getVersion ().charAt (i);
   c = Character.toLowerCase (c);
   if (Character.isLetter (c))
   {
    buf.append (c);
    continue;
   }
   if (Character.isDigit (c))
   {
    buf.append (c);
   }
  }
  pack = pack + buf.toString ();
  pack = pack + ".api";
  return pack;
 }

 public static String calcClassName (Service service)
 {
  return service.getKey ().substring (0, 1).toUpperCase () + service.getKey ().
   substring (1).toLowerCase () + "Service";
 }

 /**
  * Write out the entire file
  * @param out
  */
 public void write ()
 {
  indentPrintln ("public interface " + calcClassName (service));
  openBrace ();

  for (ServiceMethod method : methods)
  {
   indentPrintln ("");
   indentPrintln ("/**");
   indentPrintWrappedComment (method.getDescription ());
   indentPrintln ("* ");
   for (ServiceMethodParameter param : method.getParameters ())
   {
    indentPrintWrappedComment ("@param " + param.getName () + " - "
     + param.getType () + " - " + param.getDescription ());
   }
   indentPrintWrappedComment ("@return " + method.getReturnValue ().
    getDescription ());
   indentPrintln ("*/");
   indentPrint ("public " + calcType (method.getReturnValue ().getType ()) + " " + method.
    getName () + "(");
   // now do parameters
   String comma = "";
   for (ServiceMethodParameter param : method.getParameters ())
   {
    print (comma);
    print (calcType (param.getType ()));
    print (" ");
    print (param.getName ());
    comma = ", ";
   }
   println (")");
   // now do exceptions
   comma = "throws ";
   incrementIndent ();
   for (ServiceMethodError error : method.getErrors ())
   {
    indentPrint (comma);
    println (calcExceptionClassName (error.getType ()));
    comma = "      ,";
   }
   decrementIndent ();
   indentPrintln (";");

  }

  closeBrace ();

  this.writeJavaClassAndImportsOutToFile ();
  this.getOut ().close ();
 }

 private String calcExceptionClassName (String type)
 {
  return ServiceExceptionWriter.calcClassName (type);
 }

 private String calcType (String type)
 {
  return ServiceMessageStructureTypeCalculator.calculate (this, model, type, null);
 }

}
