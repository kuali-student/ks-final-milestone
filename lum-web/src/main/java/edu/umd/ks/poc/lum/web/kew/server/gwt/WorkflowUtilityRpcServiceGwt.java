package edu.umd.ks.poc.lum.web.kew.server.gwt;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.Cookie;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.umd.ks.poc.lum.lu.dto.CluInfo;
import edu.umd.ks.poc.lum.web.kew.client.service.WorkflowUtilityRpcService;
import edu.umd.ks.poc.lum.web.kew.client.service.WorkflowUtilityService;
import edu.umd.ks.poc.lum.web.spring.LumServicesContext;

public class WorkflowUtilityRpcServiceGwt extends RemoteServiceServlet implements WorkflowUtilityRpcService{

	private static final long serialVersionUID = 7963474847089269228L;

	private WorkflowUtilityService service;

//	@Override
//	public ActionItemDTO[] getActionItemsForUser(UuIdDTO userId)
//			throws SerializableException {
//		return service.getActionItemsForUser(userId);
//	}
//
//	@Override
//	public DocumentContentDTO getDocumentContent(Long routeHeaderId)
//			throws SerializableException {
//		return service.getDocumentContent(routeHeaderId);
//	}

	@Override
	public List<CluInfo> getClusForUser(String user) {
		return service.getClusForUser(user);
	}

    /*
    public HttpSession getSession() {
        return this.getThreadLocalRequest().getSession();
    }
     */

    public String getUser(){
        String user = this.getThreadLocalRequest().getRemoteUser();
        
        if(user==null||"".equals(user)){
        	user = (String)this.getThreadLocalRequest().getSession().getAttribute("UserId");
        	System.out.println("User not found in remoteuser, looking in session attribute UserId");
        }
        return user;
    }

    @Override
    public String getCluIdForDocument(String docId) {
        return service.getCluIdForDocument(docId);
    }

	@Override
	public String switchUser(String userId) {
    	System.out.println("Switching user");
    	
		try {
			URL                 url;
		    URLConnection   urlConn;
		    DataOutputStream    printout;
		    DataInputStream     input;
		    // URL of CGI-Bin script.
		    url = new URL ("http://localhost:8080/lum-umd/en/Portal.do");
		    // URL connection channel.
		    urlConn = url.openConnection();
		    // Let the run-time system (RTS) know that we want input.
		    urlConn.setDoInput (true);
		    // Let the RTS know that we want to do output.
		    urlConn.setDoOutput (true);
		    // No caching, we want the real thing.
		    urlConn.setUseCaches (false);
		    // Specify the content type.
		    urlConn.setRequestProperty
		    ("Content-Type", "application/x-www-form-urlencoded");

		    String jsessionid="";
		    for(Cookie c:this.getThreadLocalRequest().getCookies()){
		    	if("jsessionid".equals(c.getName().toLowerCase())){
		    		jsessionid=c.getValue();
		    		System.out.println("Found jsessionid:"+jsessionid);
		    	}
		    }
		    urlConn.setRequestProperty("Cookie", "JSESSIONID="+jsessionid);
		    
		    // Send POST output.
		    printout = new DataOutputStream (urlConn.getOutputStream ());
		    
		    String content;
		    if(userId==null){
			    content = "methodToCall.logout.x=1" +
				          "&methodToCall.logout.y=1";		    	
		    }else{
		    	content = "methodToCall.login.x=" + URLEncoder.encode ("1") +
		    			  "&methodToCall.login.y=" + URLEncoder.encode ("1") +
					      "&backdoorId=" + URLEncoder.encode (userId) +
					      "&__login_user=" + URLEncoder.encode (userId);
		    }
		    printout.writeBytes (content);
		    printout.flush ();
		    printout.close ();
		    // Get response data.
		    input = new DataInputStream (urlConn.getInputStream ());
		    String str;
		    while (null != ((str = input.readLine()))){
		    	//System.out.println (str);
		    }
		    input.close ();


		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		this.getThreadLocalRequest().getSession().setAttribute("UserId",userId);
		return userId;
	}

	/**
	 * @return the service
	 */
	public WorkflowUtilityService getService() {
		return service;
	}

	/**
	 * @param service the service to set
	 */
	public void setService(WorkflowUtilityService service) {
		this.service = service;
	}

}
