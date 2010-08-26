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

import java.util.Date;

/**
 *
 * @author nwright
 */
public class DateHelper
{

 public Date asDate (String str)
 {
  // Can't use SimpleDateFormat inside GWT
  // but don't want to use GWT specific code so this is indepdent of GWT
  // so...
  int yyyy = Integer.parseInt (str.substring (0, 4)) - 1900;
  int mm = Integer.parseInt (str.substring (5, 7)) - 1;
  int dd = Integer.parseInt (str.substring (8, 10));
  Date date = new Date (yyyy, mm, dd);
  return date;
 }

 public String asYYYYMMDD (Date date)
 {
  // Can't use SimpleDateFormat inside GWT
  // but don't want to use GWT specific code so this is indepdent of GWT
  // so...
  int yyyy = date.getYear () + 1900;
  int mm = date.getMonth () + 1;
  int dd = date.getDate ();
  String yr = yyyy + "-";
  String mnth = mm + "-";
  if (mm < 10)
  {
   mnth = "0" + mnth;
  }
  String dom = "" + dd;
  if (dd < 10)
  {
   dom = "0" + dom;
  }
  return yr + mnth + dom;
 }

}
