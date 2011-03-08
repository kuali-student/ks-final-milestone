/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.enrollment.lpr.service.adapter.authorization;




import java.util.ArrayList;
import java.util.List;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationServiceInfc;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationServiceMockImpl;
import org.kuali.student.enrollment.lpr.service.adapter.LuiPersonRelationAdapter;


/**
 * A example of an adapter that might sit at the top of the stack and converts any
 * runtime exceptions into the formal OperationFailedException
 *
 * This could be genrated automatically from the contract definitions too.
 *
 * @Author Norm
 */
public class ExampleLuiPersonRelationAdaptorConfigurer
 {
 private List<LuiPersonRelationAdapter> adapters;
 private LuiPersonRelationServiceInfc bottom;

 public List<LuiPersonRelationAdapter> getAdapters() {
  return adapters;
 }

 public void setAdapters(List<LuiPersonRelationAdapter> adapters) {
  this.adapters = adapters;
 }

 public LuiPersonRelationServiceInfc getBottom() {
  return bottom;
 }

 public void setBottom(LuiPersonRelationServiceInfc bottom) {
  this.bottom = bottom;
 }


 public LuiPersonRelationServiceInfc configure ()
 {
  int i = 0;
  LuiPersonRelationAdapter top = this.adapters.get(i);
  LuiPersonRelationAdapter current = top;
  for (i = 1; i < this.adapters.size (); i++)
  {
    current.setProvider(this.adapters.get(i));
    current = this.adapters.get(i);
   }
  current.setProvider(bottom);
  return top;
 }

 public static List<LuiPersonRelationAdapter> getStandardAdapters ()
 {
   List<LuiPersonRelationAdapter> adapters = new ArrayList ();
  adapters.add(new LuiPersonRelationRuntimeExceptionCatcherAdapter ());
  adapters.add(new LuiPersonRelationRuntimeExceptionCatcherAdapter ());
  adapters.add(new LuiPersonRelationAuthorizationAdapter ());
  return adapters;
 }

  public static LuiPersonRelationServiceInfc getReal ()
 {
  ExampleLuiPersonRelationAdaptorConfigurer config = new ExampleLuiPersonRelationAdaptorConfigurer ();
  config.setAdapters(getStandardAdapters ());
  config.setBottom(new LuiPersonRelationServiceMockImpl ());
  return config.configure();
 }

 public static LuiPersonRelationServiceInfc getMock ()
 {
  ExampleLuiPersonRelationAdaptorConfigurer config = new ExampleLuiPersonRelationAdaptorConfigurer ();
  config.setAdapters(getStandardAdapters ());
  config.setBottom(new LuiPersonRelationServiceMockImpl ());
  return config.configure();
 }
}
