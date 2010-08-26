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

import java.net.MalformedURLException;
import java.net.URL;
import org.kuali.student.dictionary.DictionaryExecutionException;

/**
 *
 * @author nwright
 */
public class UrlHelper
{
 private String path;

 public UrlHelper (String path)
 {
  this.path = path;
 }

 public URL getUrl ()
 {
  URL url;
  try
  {
   url = new URL (path);
  }
  catch (MalformedURLException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  return url;
 }
}
