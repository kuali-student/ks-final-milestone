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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.kuali.student.dictionary.DictionaryExecutionException;

/**
 *
 * @author nwright
 */
public abstract class JavaClassWriter extends XmlWriter
{

 private String rootDirectory;
 private String packageName;
 private String className;
 private String fileName;
 private String directory;
 private ByteArrayOutputStream body;
 private Set<String> imports;

 public JavaClassWriter (String rootDirectory, String packageName,
                         String className)
 {
  super ();
  this.body = new ByteArrayOutputStream (1000);
  this.setOut (new PrintStream (body));
  this.setIndent (0);
  this.rootDirectory = rootDirectory;

  this.packageName = packageName;
  this.className = className;
  this.fileName =
   new JavaClassFileNameBuilder (rootDirectory, packageName, className).build ();
  this.directory =
   new JavaClassFileNameBuilder (rootDirectory, packageName, className).
   buildDirectory ();
  this.imports = new TreeSet ();
 }

 public ByteArrayOutputStream getBody ()
 {
  return body;
 }

 public String getClassName ()
 {
  return className;
 }

 public String getDirectory ()
 {
  return directory;
 }

 public String getFileName ()
 {
  return fileName;
 }

 public String getPackageName ()
 {
  return packageName;
 }

 public String getRootDirectory ()
 {
  return rootDirectory;
 }

 public void importsAdd (String pack)
 {
  this.imports.add (pack);
 }

 public void writeHeader ()
 {
  indentPrintln ("/*");
  indentPrintln (" * Copyright 2010 The Kuali Foundation");
  indentPrintln (" *");
  indentPrintln (" * Licensed under the Educational Community License, Version 2.0 (the \"License\");");
  indentPrintln (" * you may not use this file except in compliance with the License.");
  indentPrintln (" * You may	obtain a copy of the License at");
  indentPrintln (" *");
  indentPrintln (" * 	http://www.osedu.org/licenses/ECL-2.0");
  indentPrintln (" *");
  indentPrintln (" * Unless required by applicable law or agreed to in writing, software");
  indentPrintln (" * distributed under the License is distributed on an \"AS IS\" BASIS,");
  indentPrintln (" * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.");
  indentPrintln (" * See the License for the specific language governing permissions and");
  indentPrintln (" * limitations under the License.");
  indentPrintln (" */");
  indentPrintln ("package " + packageName + ";");
  indentPrintln ("");
 }

 public void writeImports ()
 {
  if (imports.size () == 0)
  {
   return;
  }


  for (String imprt : imports)
  {
   // exclude imports from same package
   if (imprt.startsWith (packageName))
   {
    // don't exclude imports for same package that are including nested classes
    if ( ! imprt.substring (packageName.length () + 1).contains ("."))
    {
     continue;
    }
   }
   indentPrintln ("import " + imprt + ";");
  }
  indentPrintln ("");
 }

 public void writeJavaClassAndImportsOutToFile ()
 {

  File dir = new File (this.directory);
  //System.out.println ("Writing java class: " + fileName + " to " + dir.getAbsolutePath ());

  if ( ! dir.exists ())
  {
   if ( ! dir.mkdirs ())
   {
    throw new DictionaryExecutionException ("Could not create directory "
     + this.directory);
   }
  }
  try
  {
   PrintStream out = new PrintStream (new FileOutputStream (fileName, false));
   this.setOut (out);
  }
  catch (FileNotFoundException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  writeHeader ();
  indentPrintln ("");
  writeImports ();
  indentPrintln ("");
  indentPrintln (body.toString ());
 }

 public void openBrace ()
 {
  indentPrintln ("{");
  incrementIndent ();
 }

 public void closeBrace ()
 {
  decrementIndent ();
  indentPrintln ("}");
 }

 public void indentPrintWrappedComment (String str)
 {
  Pattern pattern = Pattern.compile (".{0,79}(?:\\S(?:-| |$)|$)");
  Matcher m = pattern.matcher (str);
  while (m.find ())
  {
   // suppresss blank lines
   if (m.group ().equals (""))
   {
    continue;
   }
   indentPrint ("* ");
   println (m.group ());
  }
 }

}
