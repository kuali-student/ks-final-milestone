/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.dictionary;

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
