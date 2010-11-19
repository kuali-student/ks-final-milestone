package org.kuali.student.deployment.monitor.http;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.sql.Timestamp;

import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


public class RequestDumper {

    //~ Constructors ---------------------------------------------------------------------

    /**
     * Never instantiate this utility class
     */
    private RequestDumper() {
       
    }

    //~ Methods --------------------------------------------------------------------------

    /**
     * Dump contents of a ServletRequest
     *
     * @param request request to process
     *
     * @return the request dump
     */
    public static String dump(ServletRequest request) {
        // Render the generic servlet request properties
        StringWriter sw = new StringWriter();
        PrintWriter writer = new PrintWriter(sw);
        
        executeDump(request, writer);

        return writer.toString();
    }
    public static void dumpToFile(ServletRequest request, PrintWriter writer,
    		boolean closeWriter) {
    	executeDump(request, writer);
    	if (closeWriter)
    		writer.close();
    }

	/**
	 * @param request
	 * @param writer
	 */
	private static void executeDump(ServletRequest request, PrintWriter writer) {
		writer.println(" characterEncoding=" + request.getCharacterEncoding());
        writer.println("     contentLength=" + request.getContentLength());
        writer.println("       contentType=" + request.getContentType());
        writer.println("            locale=" + request.getLocale());
        writer.print("           locales=");

        Enumeration locales = request.getLocales();
        boolean first = true;

        while (locales.hasMoreElements()) {
            Locale locale = (Locale) locales.nextElement();

            if (first) {
                first = false;
            } else {
                writer.print(", ");
            }

            writer.print(locale.toString());
        }

        writer.println();

        Enumeration names = request.getParameterNames();

        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            writer.print("         parameter=" + name + "=");

            String[] values = request.getParameterValues(name);

            for (int i = 0; i < values.length; i++) {
                if (i > 0) {
                    writer.print(", ");
                }

                writer.print(values[i]);
            }

            writer.println();
        }

        names = request.getAttributeNames();

        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();

            Object o = request.getAttribute(name);
            writer.print("         attribute=" + name + "=" + o.toString());
            writer.println();
        }

        writer.println("          protocol=" + request.getProtocol());
        writer.println("        remoteAddr=" + request.getRemoteAddr());
        writer.println("        remoteHost=" + request.getRemoteHost());
        writer.println("            scheme=" + request.getScheme());
        writer.println("        serverName=" + request.getServerName());
        writer.println("        serverPort=" + request.getServerPort());
        writer.println("          isSecure=" + request.isSecure());

        // Render the HTTP servlet request properties
        if (request instanceof HttpServletRequest) {
            writer.println("---------------------------------------------");

            HttpServletRequest hrequest = (HttpServletRequest) request;
            writer.println("       contextPath=" + hrequest.getContextPath());

            Cookie[] cookies = hrequest.getCookies();

            if (cookies == null) {
                cookies = new Cookie[0];
            }

            for (int i = 0; i < cookies.length; i++) {
                writer.println(
                    "            cookie=" + cookies[i].getName() + "="
                    + cookies[i].getValue());
            }

            names = hrequest.getHeaderNames();

            while (names.hasMoreElements()) {
                String name = (String) names.nextElement();
                String value = hrequest.getHeader(name);
                writer.println("            header=" + name + "=" + value);
            }

            writer.println("            method=" + hrequest.getMethod());
            writer.println("          pathInfo=" + hrequest.getPathInfo());
            writer.println("       queryString=" + hrequest.getQueryString());
            writer.println("        remoteUser=" + hrequest.getRemoteUser());
            writer.println("requestedSessionId=" + hrequest.getRequestedSessionId());
            writer.println("        requestURI=" + hrequest.getRequestURI());
            writer.println("       servletPath=" + hrequest.getServletPath());
        }

        writer.println("=============================================");

        // Log the resulting string
        writer.flush();
	}
}
