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
package org.kuali.student.loader;

import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author nwright
 */
public class OrganizationLoaderFromCommandLine
{
  /**
  * @param args the command line arguments
  */
 public static void main (String[] args)
 {
  OrganizationLoaderFromCommandLine instance =
   new OrganizationLoaderFromCommandLine ();
  instance.execute (args);

 }

 private void displayVersion ()
 {
  displayVersion (System.out);
 }

 public void displayVersion (PrintStream out)
 {
  //TODO: figure out how to get the version from the Maven property
  out.println ("Kuali Student Organization Loader: Version 0.5");
  out.println ("                     Built on September 9, 2010");
 }

 private void displayParameters (String inFile, String outFile)
 {
  displayParameters (System.out, inFile, outFile);
 }

 public void displayParameters (PrintStream out, String inFile, String hostURL)
 {
  out.println ("Reading: " + inFile);
  out.println ("Updating: " + hostURL);
 }

 private void displayUsage ()
 {
  displayUsage (System.out);
 }

 public void displayUsage (PrintStream out)
 {
  out.println ("Usage: java -jar kuali-organization-loader.jar <inputExcel> <hostUrl>");
  out.println ("\t@param inputExcel the fully qualified file name for the input excel file");
  out.println ("\t@param hostUrl the fully qualified url of the serice");
  out.println ("ex: java -jar kuali-organization-loader-loader.jar AccreditingBodies.xls http://localhost:9393/ks-embedded-dev");
 }

 private void execute (String[] args)
 {
  displayVersion ();
  if (args == null)
  {
   displayUsage ();
   throw new RuntimeException ("args is null");
  }
  if (args.length == 0)
  {
   displayUsage ();
   throw new RuntimeException ("no args specified");
  }
  if (args.length == 1)
  {
   displayUsage ();
   throw new RuntimeException ("no host url specified");
  }
  String in = args[0];
  String host = args[1];
  generate (in, host);
 }

 protected void generate (String inFile, String hostUrl)
 {
  displayParameters (inFile, hostUrl);
  Properties cfg = new Properties ();
  cfg.put (OrganizationLoaderModelFactory.EXCEL_FILES_KEY + "1", inFile);
  cfg.put (OrganizationLoaderModelFactory.SERVICE_HOST_URL, hostUrl);
  OrganizationLoaderModelFactory factory = new OrganizationLoaderModelFactory ();
  factory.setConfig (cfg);
  OrganizationLoaderModel orgModel = factory.getModel ();
  OrganizationLoader ccLoader = new OrganizationLoader ();
  OrgService OrgService = new OrgService ();
  OrgService.setHostUrl (OrganizationLoaderModelFactory.LOCAL_HOST_URL);
  ccLoader.setOrgService (OrgService);

  System.out.println (new Date () + " getting organizations...");
  List<Organization> organizations = orgModel.getOrganizations ();

  System.out.println (new Date () + " loading " + organizations.size ()
                      + " organizations");
//  ccLoader.setSource (organizations.subList (0, 10).iterator ());
  ccLoader.setInputDataSource (organizations.iterator ());
  List<OrganizationLoadResult> results = ccLoader.update ();
  int created = 0;
  int failures = 0;
  for (OrganizationLoadResult result : results)
  {
   if (result.isSuccess ())
   {
    created ++;
    System.out.println (result.getOrgInfo ().getShortName () + " id = " + result.getOrgInfo ().getId ());
   }
   else
   {
    failures ++;
   }
  }
  System.out.println (created + " recordes created out of " + organizations.size () + " organizations");
  System.out.println (failures + " records failed to load");
  for (OrganizationLoadResult result : results)
  {
   if ( ! result.isSuccess ())
   {
    System.out.println (result);
   }
  }
  if (failures > 0)
  {
   throw new RuntimeException (failures + " records failed to load");
  }
 }
}
