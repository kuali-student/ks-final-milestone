<%@ page import="java.io.*" %><%

response.setContentType("text/plain");

int display = 25 * 1024;
if (request.getParameter("k") != null) {
  display = new Integer(request.getParameter("k")) * 1024;
}
String filename = "/usr/local/tomcat/logs/catalina.out";
File file = new File(filename);
long length = file.length();
InputStream in = new FileInputStream(file);
long skip = length - display;
if (skip < 0) {
  skip = 0;
}
in.skip(skip);
byte[] buffer = new byte[4096];
int readLength = 0;
while ((readLength = in.read(buffer, 0, buffer.length)) != -1) {
        out.write(new String(buffer, 0, readLength));
}
in.close();
%>
