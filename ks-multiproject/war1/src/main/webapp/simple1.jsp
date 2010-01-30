<%@ page import="com.atlassian.maven.plugin.clover.samples.multiproject.Simple1" %>
<%
out.println("<br>Foo");
Simple1 simple1 = new Simple1();
simple1.someMethod1();
out.println("<br>Bar");
%> 