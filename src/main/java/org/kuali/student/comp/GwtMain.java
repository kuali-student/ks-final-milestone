/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.comp;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dev.HostedMode;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import org.kuali.student.comp.impl.Context;
import org.kuali.student.comp.impl.gwt.GwtContextConfigurer;
import org.kuali.student.comp.infc.KSContext;
import org.kuali.student.comp.infc.KSDisplayHomePage;
import org.kuali.student.service.impl.mock.MockContextConfigurer;

/**
 *
 * @author nwright
 */
public class GwtMain implements EntryPoint,
                                Runnable
{

 protected KSContext context;

 public GwtMain ()
 {
  context = new Context ();
  new MockContextConfigurer ().configure (context);
  new GwtContextConfigurer ().configure (context);
 }

 @Override
 public void onModuleLoad ()
 {
  run ();
 }

 @Override
 public void run ()
 {
  KSDisplayHomePage home =
   (KSDisplayHomePage) context.getInstance (KSDisplayHomePage.class);
  RootPanel.get ().add ((Widget) home.getImpl ());
  home.display (new KSDisplayHomePage.Callback (this)
  {

   @Override
   public void onDone ()
   {
    System.out.println ("Exiting GwtMain");
    System.exit (0);
    return;
   }

   @Override
   public void onError (Exception ex)
   {
    throw new RuntimeException (ex);
   }

  });
 }

 public static void main (String[] args)
 {
//  HostedMode [-noserver] [-port port-number | "auto"] [-whitelist whitelist-string] [-blacklist blacklist-string] [-logLevel level] [-gen dir] [-style style] [-ea] [-server servletContainerLauncher] [-startupUrl url] [-war dir] [-extra dir] [-workDir dir] [-localWorkers count] module[s]
//
//where
//  -noserver      Prevents the embedded web server from running
//  -port          Specifies the TCP port for the embedded web server (defaults to 8888)
//  -whitelist     Allows the user to browse URLs that match the specified regexes (comma or space separated)
//  -blacklist     Prevents the user browsing URLs that match the specified regexes (comma or space separated)
//  -logLevel      The level of logging detail: ERROR, WARN, INFO, TRACE, DEBUG, SPAM, or ALL
//  -gen           The directory into which generated files will be written for review
//  -style         Script output style: OBF[USCATED], PRETTY, or DETAILED (defaults to OBF)
//  -ea            Debugging: causes the compiled output to check assert statements.
//  -server        Specifies a different embedded web server to run (must implement ServletContainerLauncher)
//  -startupUrl    Automatically launches the specified URL
//  -war           The war directory to write output files into (defaults to war)
//  -extra         The directory into which extra, non-deployed files will be written
//  -workDir       The compiler work directory (must be writeable; defaults to a system temp dir)
//  -localWorkers  Specifies the number of local workers to use when compiling permutations
//and
//  module[s]      Specifies the name(s) of the module(s) to host

  String[] myArgs = new String[2];
  myArgs[0] = "-noserver";
  myArgs[1] = "GwtMain";
  HostedMode.main (myArgs);
 }

}
