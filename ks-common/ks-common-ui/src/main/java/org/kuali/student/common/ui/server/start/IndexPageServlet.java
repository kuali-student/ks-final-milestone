/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.ui.server.start;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kuali.rice.core.config.ConfigContext;
import org.kuali.student.common.util.security.SecurityUtils;

public class IndexPageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		//Get the username
		String username = SecurityUtils.getPrincipalUserName();
		
		//Get the Rice Application Url
		String riceUrl = ConfigContext.getCurrentContextConfig().getProperty("ks.rice.url");
		if(null==riceUrl||riceUrl.isEmpty()){
			riceUrl = ConfigContext.getCurrentContextConfig().getProperty("application.url");
		}
		
		out.println("		<html>\n"
			      + " 		<head>\n"
				  + "	    <style type=\"text/css\">\n"
						+ "        .KS-Header-Logo-Spacer {\n"
						+ "            width: 100%;\n"
						+ "            height:30px;\n"
						+ "            background-color: #dddddd;\n"
						+ "            color: #777777;\n"
						+ "            font: 30px helvetica, sans-serif;\n"
						+ "            padding-top: 8px;\n"
						+ "            padding-bottom: 8px;\n"
						+ "            padding-left: 10px;\n"
						+ "          }\n"
						+ "          .KS-Header-Link {\n"
						+ "            color: #ffffff;\n"
						+ "            text-decoration: none;\n"
						+ "            font: 10px helvetica, sans-serif;\n"
						+ "            text-align: right;\n"
						+ "            padding-right: 0px;\n"
						+ "          }\n"
						+ "\n"
						+ "          .KS-Header-Link-Focus {\n"
						+ "            cursor: pointer;\n"
						+ "            text-decoration: underline;\n"
						+ "          }\n"
						+ "          .page-title {\n"
						+ "            font: bold 18px helvetica, sans-serif;\n"
						+ "            border: solid thin #aaaaaa;\n"
						+ "            padding-top: 5px;\n"
						+ "            padding-bottom: 5px;\n"
						+ "            padding-left: 1em;\n"
						+ "            z-index: 99999999;\n"
						+ "            background-color: #eeeeee;\n"
						+ "          }\n"
						+ "          \n"
						+ "          .user-name {\n"
						+ "            width: 100%;\n"
						+ "            height:2em;\n"
						+ "            background-color: #a70b3b;\n"
						+ "            margin-bottom: .5em;\n"
						+ "            text-align: right;\n"
						+ "          }\n"
						+ "           .user-Link {\n"
						+ "            position: relative;\n"
						+ "            top: 3px;\n"
						+ "            right: 60px;\n"
						+ "            color: #ffffff;\n"
						+ "            text-decoration: none;\n"
						+ "            font: 12px helvetica, sans-serif;\n"
						+ "            padding-right: 5px;\n"
						+ "          }   \n"
						+ "          \n"
						+ "         .user-button {\n"
						+ "            position: relative;\n"
						+ "            top: -16px;\n"
						+ "            right: 10px;\n"
						+ "          }  \n"
						+ "\n"
						+ "          .mock-header {\n"
						+ "            font: 12px helvetica, sans-serif;\n"
						+ "            background-color: #ffffff;\n"
						+ "\n"
						+ "          }\n"
						+ "          \n"
						+ "          .menu-panel{\n"
						+ "          	display:inline;\n"
						+ "          }\n"
						+ "          \n"
						+ "          .mock-Kew {\n"
						+ "			display:inline;\n"
						+ "          }\n"
						+ "          .mock-row {\n"
						+ "            height: 250px;\n"
						+ "          }\n"
						+ "          .mock-actionlist {\n"
						+ "            border-collapse: collapse;\n"
						+ "            border: 1px solid #000;\n"
						+ "            text-align: center;\n"
						+ "            padding: 50px;\n"
						+ "            font: 36px helvetica, san-serif;\n"
						+ "            color: #aaaaaa;\n"
						+ "          }\n"
						+ "          \n"
						+ "          .bottom-layout{\n"
						+ "          	width: 100%;\n"
						+ "          }\n"
						+ "        \n"
						+ "        \n"
						+ ".ExampleStyledList ul {\n"
						+ "padding: 0;\n"
						+ "padding-top: 0px;\n"
						+ "margin: 0px;\n"
						+ " list-style-type: none;\n"
						+ " padding-bottom: 1em;\n"
						+ " border-style: solid;\n"
						+ " border-width: 1px;\n"
						+ "	background-color: white;\n"
						+ " -moz-border-radius: 1em; \n"
						+ " -webkit-border-radius: 1em;\n"
						+ " width: 12em;\n"
						+ "}\n"
						+ ".ExampleStyledList {\n"
						+ "\n"
						+ " margin-top: 1em;\n"
						+ " margin-bottom: 0.1em;\n"
						+ " width: 8em;\n"
						+ " font-weight: bold;\n"
						+ "}\n"
						+ ".ExampleStyledList a {\n"
						+ "	margin-bottom: 3px;\n"
						+ "	border: 1px solid white;\n"
						+ "	border-left: 3px solid white;\n"
						+ "	color: black;\n"
						+ " display: block;\n"
						+ " width: 9.5em;\n"
						+ " margin: 0.5em;\n"
						+ " padding: 0.5em;\n"
						+ "\n"
						+ " -moz-border-radius: 0.5em; \n"
						+ " -webkit-border-radius: 0.5em;\n"
						+ " border-radius: 0.5em;\n"
						+ " text-decoration: none;\n"
						+ "}\n"
						+ ".ExampleStyledList a:hover {\n"
						+ "	background-color: #f4f4f4;\n"
						+ "	border: 1px solid black;\n"
						+ "	border-left: 3px solid #990000;\n"
						+ "	cursor: pointer;\n"
						+ "	color: #ab0000;\n"
						+ "\n"
						+ "}\n"
						+ "\n"
						+ ".task-table{\n"
						+ "margin-left: 4em;\n"
						+ "\n"
						+ "}\n"
						+ "        </style>		\n"
						+ "	</head>\n"
						+ "	<body>\n"
						+ "	<img src=\"kru_banner2.jpg\">\n"
						+ "	<div class=\"user-name\"><a href=\".\" class=\"user-link\">"
						+ username
						+ "</a>\n"
						+ "<a href=\"./j_spring_security_logout\" class=\"user-link\">Logout</a></div>\n"
						+ "<table style=\"vertical-align: top; height: 500px\">\n"
						+ "<tr >\n"
						+ "<td style=\"vertical-align: top;\">\n"
						+ "\n"
						+ "	<span class=\"ExampleStyledList\">\n"
						+ "	<ul> \n"
						+ "		<li><a href=\"org.kuali.student.common.organization.ui.OrgEntry/OrgEntry.jsp\">Organizations</a> </li> \n"
						+ "		<li><a href=\"org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp\">Curriculum Management</a> </li> \n"
						+ "		<li><a href=\""
						+ riceUrl
						+ "/portal.do?selectedTab=main\">Rice</a></li>\n"
						+ "	</ul>\n"
						+ "	</span>\n"
						+ "	</td>\n"
						+ "	<td style=\"vertical-align: top;\">\n"
						+ "    <table class=\"task-table\"><tr class=\"mock-row\"><td class=\"mock-actionList\">KEW action list</td></tr></table> \n"
						+ "    </td>\n"
						+ "</tr>\n"
						+ "</table>\n"
						+ "	\n"
						+ "</body></html>");
	}
}
