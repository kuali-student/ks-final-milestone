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
package org.kuali.student.loader;

import java.util.List;

/**
 *
 * @author nwright
 */
public class EnumeratedValueLoaderModelCache implements EnumeratedValueLoaderModel
{

 public EnumeratedValueLoaderModelCache (EnumeratedValueLoaderModel loaderModel)
 {
  this.loaderModel = loaderModel;
 }

 private EnumeratedValueLoaderModel loaderModel;

 public EnumeratedValueLoaderModel getLoaderModel ()
 {
  return loaderModel;
 }

 public void setLoaderModel (EnumeratedValueLoaderModel loaderModel)
 {
  this.loaderModel = loaderModel;
 }

 private static List<EnumeratedValue> enumeratedValue = null;

 @Override
 public List<EnumeratedValue> getEnumeratedValues ()
 {
  if (enumeratedValue != null)
  {
   return enumeratedValue;
  }
  enumeratedValue = loaderModel.getEnumeratedValues ();
  return enumeratedValue;
 }


}
