java -version
@echo The messages intraface is creating. Please notify that required java version is: 1.5.0 and higher.
@echo generating MenuPage resources
@java -cp "%~dp0\java;%~dp0\bin;d:/programming/frameworks/gwt-2.0.3/gwt-user.jar;d:/programming/frameworks/gwt-2.0.3/gwt-dev.jar" com.google.gwt.i18n.tools.I18NSync -out "%~dp0\java"  -createMessages  org.kuali.student.lum.program.client.properties.Program
