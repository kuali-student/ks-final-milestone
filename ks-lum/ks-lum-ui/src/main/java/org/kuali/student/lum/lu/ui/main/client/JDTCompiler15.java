package org.kuali.student.lum.lu.ui.main.client;

import org.apache.tools.ant.taskdefs.Javac;
import org.eclipse.jdt.core.JDTCompilerAdapter;

//See http://code.google.com/p/google-web-toolkit/issues/detail?id=3557 for details of solution
public class JDTCompiler15 extends JDTCompilerAdapter {
       @Override
       public void setJavac(Javac attributes) {
               if (attributes.getTarget() == null) {
                       attributes.setTarget("1.5");
               }
               if (attributes.getSource() == null) {
                       attributes.setSource("1.5");
               }
               super.setJavac(attributes);
       }
}