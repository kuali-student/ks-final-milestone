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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.kuali.student.dictionary.DictionaryExecutionException;


import org.w3c.dom.Document;

/**
 * login to the wiki and get the session id from the cookie
 * @author nwright
 */
public class Login 
{

 private String contractPath;
 private String userId;
 private String password;

 public Login (String contractPath, String userId, String password)
 {
  this.contractPath = contractPath;
  this.userId = userId;
  this.password = password;
 }

 public String getJSessionId ()
 {
  URL url;
  try
  {
   url = new URL (contractPath);
  }
  catch (MalformedURLException ex)
  {
   throw new DictionaryExecutionException (ex);
  }

  URLConnection connection;
  try
  {
   connection = url.openConnection ();
  }
  catch (IOException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  //connection.setRequestProperty ("Cookie", "JSESSIONID=" + jSessionId);

  InputStream in;
  try
  {
   in = connection.getInputStream ();
  }
  catch (IOException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  Document doc = new NodeHelper ().getDocument (in);
  new NodeHelper ().dump (doc, System.out);
  return "TODO";
 }

}
