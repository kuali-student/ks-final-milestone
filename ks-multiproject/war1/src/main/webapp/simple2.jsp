<%@ page import="com.atlassian.maven.plugin.clover.samples.multiproject.Simple2" %>
<%
out.println("<br>Foo");
Simple2 simple2 = new Simple2();
simple2.someMethod2();
out.println("<br>Bar");
%> 