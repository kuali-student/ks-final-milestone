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
package org.kuali.student.dictionary.model.impl;

import org.kuali.student.dictionary.model.*;
import java.util.List;

/**
 * This reads the supplied spreadsheet but then caches it so it doesn't have to
 * re-read it again.
 * @author nwright
 */
public class SearchModelCache implements SearchModel
{

 private SearchModel model;
 
 public SearchModelCache (SearchModel model)
 {
  this.model = model;
 }

 private List<SearchType> searchTypes = null;

 @Override
 public List<SearchType> getSearchTypes ()
 {
  if (searchTypes == null)
  {
   searchTypes = model.getSearchTypes ();
  }
  return searchTypes;
 }

 @Override
 public List<String> getSourceNames ()
 {
  return model.getSourceNames ();
 }
}
