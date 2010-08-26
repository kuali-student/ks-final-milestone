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
package org.kuali.student.dictionary.model.wiki;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import org.kuali.student.dictionary.DictionaryExecutionException;
import org.w3c.dom.Document;

/**
 *
 * @author nwright
 */
public class PageHelper
{
 private PageTrimmer trimmer = null;

 public PageHelper ()
 {

 }

 public PageTrimmer getTrimmer ()
 {
  return trimmer;
 }

 public void setTrimmer (PageTrimmer trimmer)
 {
  this.trimmer = trimmer;
 }


 private URL url = null;

 public Document getDocument (URL url, String jSessionId)
 {
  URLConnection connection;
  try
  {
   connection = url.openConnection ();
  }
  catch (IOException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  connection.setRequestProperty ("Cookie", "JSESSIONID=" + jSessionId);
  InputStream in;
  try
  {
   in = connection.getInputStream ();
  }
  catch (IOException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  return getDocument (in);
 }

 public Document getDocument (File file)
 {
  InputStream in;
  try
  {
   in = new FileInputStream (file);
  }
  catch (FileNotFoundException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  return getDocument (in);
 }

 public Document getDocument (InputStream in)
 {
  if (trimmer != null)
  {
   in = trimmer.trim (in);
  }
  return new NodeHelper ().getDocument (in);
 }

}
