package org.kuali.student.authz.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import org.kuali.student.authz.dto.PermissionDTO;
import org.kuali.student.authz.dto.PrincipalDTO;
import org.kuali.student.authz.dto.QualifiedRoleDTO;
import org.kuali.student.authz.dto.QualifierDTO;
import org.kuali.student.authz.dto.QualifierHierarchyDTO;
import org.kuali.student.authz.dto.QualifierTypeDTO;
import org.kuali.student.authz.dto.RoleDTO;
import org.kuali.student.authz.service.AuthorizationService;
import org.kuali.student.poc.common.ws.beans.JaxWsClientFactoryBean;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class TreeViewServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6192855676546961158L;
	//private QualifierDTO qualifier;
	private AuthorizationService authzService;
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		super.init();
			
		try{
			WebApplicationContext ctx;
			ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
			authzService = (AuthorizationService) ctx.getBean("authzServiceImpl");

		}catch(Exception e){
			System.out.println("### Using webservice");
			JaxWsClientFactoryBean clientFactory = new JaxWsClientFactoryBean();
			clientFactory.setServiceEndpointInterface(AuthorizationService.class);
			clientFactory.setServiceName(new QName(
					"http://student.kuali.org/authz",
					"AuthorizationService"));
			
			clientFactory
					.setWsdlLocation("classpath:wsdl/AuthorizationService.wsdl");
	    	clientFactory.setAddress("http://localhost:8080/AuthZ-web-0.0.1-SNAPSHOT/AuthorizationService/AuthorizationService");

	    	try {
				authzService = (AuthorizationService) clientFactory.getObject();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
		}
		

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("action");
		
		if(action==null||"viewPrincipals".equals(action)){
			//clear these session variables
			req.getSession().removeAttribute("pid");
			req.getSession().removeAttribute("rid");
			req.getSession().removeAttribute("qualifierIds");
			req.getSession().removeAttribute("qualifiers");
			req.getSession().removeAttribute("breadcrumbs");
			
			//Get all the principals and list them out
			List<PrincipalDTO> principals = authzService.getPrincipals();
			req.setAttribute("principals", principals);
	        RequestDispatcher dispatcher = req.getRequestDispatcher("viewPrincipals.jsp");
	        dispatcher.forward(req, resp);
	        return;
		}
		
		if("viewPrincipal".equals(action)){

			//view all the qualified Roles for a principal
			String pid=req.getParameter("pid");
			
			PrincipalDTO principal = authzService.getPrincipal(pid);
			List<QualifiedRoleDTO> qualifiedRoles = authzService.getQualifiedRolesForPrincipal(pid);
			List<RoleDTO> roles = authzService.getRoles();
			
			req.setAttribute("principal", principal);
			req.setAttribute("qualifiedRoles", qualifiedRoles);
			req.setAttribute("roles", roles);
			
			RequestDispatcher dispatcher = req.getRequestDispatcher("viewPrincipal.jsp");
	        dispatcher.forward(req, resp);
	        return;
		}

		if("addPrincipal".equals(action)){
			String prName = req.getParameter("prName");
			
			PrincipalDTO principal = new PrincipalDTO();
			principal.setName(prName);
			authzService.savePrincipal(principal);

			//Get all the principals and list them out
			List<PrincipalDTO> principals = authzService.getPrincipals();
			req.setAttribute("principals", principals);
	        RequestDispatcher dispatcher = req.getRequestDispatcher("viewPrincipals.jsp");
	        dispatcher.forward(req, resp);
	        return;
		}
		
		if("viewNewAuth".equals(action)){
			//Get Params
			String pid = req.getParameter("pid");
			String rid = req.getParameter("rid");
			
			//Get the Role
			RoleDTO role = authzService.getRole(rid);
			
			//Set attributes
			
			//Clear out the list of qualifiers selected
			String[] qualifierIds={};
			req.getSession().setAttribute("qualifierIds",qualifierIds);
			
			req.getSession().setAttribute("pid",pid);
			req.getSession().setAttribute("rid",rid);
			req.setAttribute("role", role);
			
			//Set an empty list of qualifiers
			req.getSession().setAttribute("qualifiers",new ArrayList<QualifierDTO>());
			
			RequestDispatcher dispatcher = req.getRequestDispatcher("viewRole.jsp");
	        dispatcher.forward(req, resp);
	        return;
		}

		if("viewQualifiers".equals(action)){
			viewQualifiers(req,resp);
			return;
		}

		if("addQualifierToAuth".equals(action)){
			//Get parameters
			String qid=req.getParameter("qid");
			String qname=req.getParameter("qname");
			String qtype=req.getParameter("qtype");
			
			//Get session variables
			String pid=(String)req.getSession().getAttribute("pid");
			String rid=(String)req.getSession().getAttribute("rid");
			String[] qualifierIds=(String[])req.getSession().getAttribute("qualifierIds");
			
			//Add the qualifierId to the list of ids to add to the role
			List<String> qids=new ArrayList<String>();
			for(String s:qualifierIds){
				qids.add(s);
			}
			qids.add(qid);
			qualifierIds=qids.toArray(qualifierIds);

			req.getSession().setAttribute("qualifierIds",qualifierIds);
			
			//Add the Qualifier to the session list (be nice if there was an service lookup by id)
			QualifierDTO qualifier = authzService.getQualifier(qid); 
			
			((ArrayList<QualifierDTO>)req.getSession().getAttribute("qualifiers")).add(qualifier);
			
			//Get the Role
			RoleDTO role = authzService.getRole(rid);
			req.setAttribute("role", role);
			
	        RequestDispatcher dispatcher = req.getRequestDispatcher("viewRole.jsp");
	        dispatcher.forward(req, resp);
	        return;
		}
		
		if("assignAuth".equals(action)){
			String pid=req.getParameter("pid");
			String rid=req.getParameter("rid");
			String[] qualifierIds=(String[])req.getSession().getAttribute("qualifierIds");
			String descendTreeString = req.getParameter("descendTree");
			boolean descendTree = descendTreeString!=null&&!"".equals(descendTreeString);
			
			authzService.assignQualifiedRoleIdToPrincipal(pid, rid, qualifierIds[0],descendTree);
			
			//view all the qualified Roles for a principal
			PrincipalDTO principal = authzService.getPrincipal(pid);
			List<QualifiedRoleDTO> qualifiedRoles = authzService.getQualifiedRolesForPrincipal(pid);
			List<RoleDTO> roles = authzService.getRoles();
			
			req.setAttribute("principal", principal);
			req.setAttribute("qualifiedRoles", qualifiedRoles);
			req.setAttribute("roles", roles);
			
			//clear these session variables
			req.getSession().removeAttribute("pid");
			req.getSession().removeAttribute("rid");
			req.getSession().removeAttribute("qualifierIds");
			req.getSession().removeAttribute("qualifiers");
			req.getSession().removeAttribute("breadcrumbs");
			
			RequestDispatcher dispatcher = req.getRequestDispatcher("viewPrincipal.jsp");
	        dispatcher.forward(req, resp);
	        return;
		}
		
		if("viewPermissions".equals(action)){
			List<PermissionDTO> permissions = authzService.getPermissions();
			req.setAttribute("permissions", permissions);
			
			RequestDispatcher dispatcher = req.getRequestDispatcher("viewPermissions.jsp");
	        dispatcher.forward(req, resp);
	        return;
		}
		
		if("addPermission".equals(action)){
			String permName = req.getParameter("permName");
			System.out.println("ADDING PERMNAME:"+permName);
			
			PermissionDTO permission = new PermissionDTO();
			permission.setName(permName);
			authzService.savePermission(permission);

			List<PermissionDTO> permissions = authzService.getPermissions();
			req.setAttribute("permissions", permissions);
			
			RequestDispatcher dispatcher = req.getRequestDispatcher("viewPermissions.jsp");
	        dispatcher.forward(req, resp);
	        return;
		}

		if("deletePermission".equals(action)){
			//TODO No service for delete permission
		}
		
		
		// Qualifier Types ////////////////////
		
		if("viewQualifierTypes".equals(action)){
			List<QualifierTypeDTO> qualifierTypes = authzService.getQualifierTypes();
			req.setAttribute("qualifierTypes", qualifierTypes);
			
			RequestDispatcher dispatcher = req.getRequestDispatcher("viewQualifierTypes.jsp");
	        dispatcher.forward(req, resp);
	        return;
		}
		
		if("createNewQualifierType".equals(action)||"Add Key".equals(action)){
			String qtName = req.getParameter("qtName");
			String qtPkId = req.getParameter("qtPkId");
			String[] qtPkIds = req.getParameterValues("qtPkIds");
			
			//add the pk to the list of pks
			List<String> qtPkIdList = new ArrayList<String>();
			if(qtPkIds!=null){
				for(String id:qtPkIds){
					qtPkIdList.add(id);
				}
			}
			if(qtPkId!=null&&!"".equals(qtPkId)){
				qtPkIdList.add(qtPkId);
			}
			qtPkIds = qtPkIdList.toArray(new String[0]);
			
			
			List<QualifierTypeDTO> qualifierTypes = authzService.getQualifierTypes();
			
			List<QualifierTypeDTO> selectedQualifierTypes = new ArrayList<QualifierTypeDTO>();
			
			for(Iterator<QualifierTypeDTO> i = qualifierTypes.iterator();i.hasNext();){
				QualifierTypeDTO qt = i.next();
				//Filter out any Qualifier types that are already composite keys
				if(qt.getPkQualifierTypes().size()>0){
					
					i.remove();
					
				}//Move any qualifier types that have already been selected from the available qts to the selected qts
				else if(qtPkIdList.contains(qt.getId())){
					
					//Add the qualifier to the selected list
					selectedQualifierTypes.add(qt);
					
					//remove the qualifier from the list of available qualifier types
					i.remove();
				}
			}
			
			req.setAttribute("qtName", qtName);
			req.setAttribute("qualifierTypes", qualifierTypes);			
			req.setAttribute("selectedQualifierTypes", selectedQualifierTypes);
			req.setAttribute("qtPkIds", qtPkIds);
			
			RequestDispatcher dispatcher = req.getRequestDispatcher("createNewQualifierType.jsp");
	        dispatcher.forward(req, resp);
	        return;
		}
		
		if("addQualifierType".equals(action)||"Create Qualifier Type".equals(action)){
			String qtName = req.getParameter("qtName");
			String[] qtPkIds = req.getParameterValues("qtPkIds");
			
			//Create a new QT
			QualifierTypeDTO qualifierTypeDTO = new QualifierTypeDTO();
			qualifierTypeDTO.setName(qtName);

			//Add the pk chldren
			if(qtPkIds!=null){
				for(String id:qtPkIds){
					QualifierTypeDTO qt = authzService.getQualifierType(id);
					qualifierTypeDTO.getPkQualifierTypes().add(qt);
				}
			}
			
			//Save
			authzService.saveQualifierType(qualifierTypeDTO);
			
			//Get Display data again
			List<QualifierTypeDTO> qualifierTypes = authzService.getQualifierTypes();
			req.setAttribute("qualifierTypes", qualifierTypes);
			
			RequestDispatcher dispatcher = req.getRequestDispatcher("viewQualifierTypes.jsp");
	        dispatcher.forward(req, resp);
	        return;
		}
		
		// Qualifier Hierarchies //////////////////////
		if("viewQualifierHierarchies".equals(action)){
			List<QualifierHierarchyDTO> qualifierHierarchies = authzService.getQualifierHierarchies();
			req.setAttribute("qualifierHierarchies", qualifierHierarchies);
			
			RequestDispatcher dispatcher = req.getRequestDispatcher("viewQualifierHierarchies.jsp");
	        dispatcher.forward(req, resp);
	        return;
		}
		
		if("createNewQualifierHierarchy".equals(action)||"Add Qualifier To Hierarchy".equals(action)){
			String qhName = req.getParameter("qhName");
			String qtId = req.getParameter("qtId");
			String[] qtIds = req.getParameterValues("qtIds");
			
			//add the pk to the list of pks
			List<String> qtIdList = new ArrayList<String>();
			if(qtIds!=null){
				for(String id:qtIds){
					qtIdList.add(id);
				}
			}
			if(qtId!=null&&!"".equals(qtId)){
				qtIdList.add(qtId);
			}
			qtIds = qtIdList.toArray(new String[0]);
			
			
			List<QualifierTypeDTO> qualifierTypes = authzService.getQualifierTypes();
			
			List<QualifierTypeDTO> selectedQualifierTypes = new ArrayList<QualifierTypeDTO>();
			
			for(Iterator<QualifierTypeDTO> i = qualifierTypes.iterator();i.hasNext();){
				QualifierTypeDTO qt = i.next();
				//Move any qualifier types that have already been selected from the available qts to the selected qts
				if(qtIdList.contains(qt.getId())){
					
					//Add the qualifier to the selected list
					selectedQualifierTypes.add(qt);
					
					//remove the qualifier from the list of available qualifier types
					i.remove();
				}
			}
			
			req.setAttribute("qhName", qhName);
			req.setAttribute("qualifierTypes", qualifierTypes);			
			req.setAttribute("selectedQualifierTypes", selectedQualifierTypes);
			req.setAttribute("qtIds", qtIds);
			
			RequestDispatcher dispatcher = req.getRequestDispatcher("createNewQualifierHierarchy.jsp");
	        dispatcher.forward(req, resp);
	        return;
		}
		
		if("addQualifierHierarchy".equals(action)||"Create Qualifier Hierarchy".equals(action)){
			String qtName = req.getParameter("qhName");
			String[] qtIds = req.getParameterValues("qtIds");
			
			//Create a new QT
			QualifierHierarchyDTO qualifierHierarchyDTO = new QualifierHierarchyDTO();
			qualifierHierarchyDTO.setName(qtName);

			//Add the qualifier types
			if(qtIds!=null){
				for(String id:qtIds){
					QualifierTypeDTO qt = authzService.getQualifierType(id);
					qualifierHierarchyDTO.getQualifierTypes().add(qt);
				}
			}
			
			//Save
			authzService.saveQualifierHierarchy(qualifierHierarchyDTO);
			
			//Get Display data again
			List<QualifierHierarchyDTO> qualifierHierarchies = authzService.getQualifierHierarchies();
			req.setAttribute("qualifierHierarchies", qualifierHierarchies);
			
			RequestDispatcher dispatcher = req.getRequestDispatcher("viewQualifierHierarchies.jsp");
	        dispatcher.forward(req, resp);
	        return;
		}
		
		// Roles ///////////////
		if("viewRoles".equals(action)){
			List<RoleDTO> roles = authzService.getRoles();
			req.setAttribute("roles", roles);
			
			RequestDispatcher dispatcher = req.getRequestDispatcher("viewRoles.jsp");
	        dispatcher.forward(req, resp);
	        return;
		}
		
		if("createNewRole".equals(action)&&"Create".equals(req.getParameter("create"))){
			String rName = req.getParameter("rName");
			String qhId = req.getParameter("qhId");
			String[] permIds = req.getParameterValues("permIds");
			String qtId = req.getParameter("qtId");
			
			RoleDTO role = new RoleDTO();
			role.setName(rName);
			if(permIds!=null){
				for(String id:permIds){
					role.getPermissions().add(authzService.getPermission(id));
				}
			}
			role.setQualifierHierarchy(authzService.getQualifierHierarchy(qhId));
			role.setQualifierType(authzService.getQualifierType(qtId));
			authzService.saveRole(role);
			
			//Redisplay all roles
			List<RoleDTO> roles = authzService.getRoles();
			req.setAttribute("roles", roles);
			
			RequestDispatcher dispatcher = req.getRequestDispatcher("viewRoles.jsp");
	        dispatcher.forward(req, resp);
	        return;
		}

		if("createNewRole".equals(action)){
			String rName = req.getParameter("rName");
			String qhId = req.getParameter("qhId");
			String[] permIds = req.getParameterValues("permIds");
			String qtId = req.getParameter("qtId");
			
			List<QualifierHierarchyDTO> qualifierHierarchies = authzService.getQualifierHierarchies();
			List<PermissionDTO> permissions = authzService.getPermissions();
			List<QualifierTypeDTO> qualifierTypes = authzService.getQualifierTypes();
			
			req.setAttribute("qualifierHierarchies", qualifierHierarchies);
			req.setAttribute("permissions", permissions);
			req.setAttribute("qualifierTypes", qualifierTypes);

			req.setAttribute("rName", rName);
			req.setAttribute("permIds", permIds);
			req.setAttribute("qhId", qhId);
			req.setAttribute("qtId", qtId);
			
			RequestDispatcher dispatcher = req.getRequestDispatcher("createNewRole.jsp");
	        dispatcher.forward(req, resp);
	        return;
		}
		
		
		if("createNewQualifier".equals(action)&&"Create".equals(req.getParameter("create"))){
			String qParentId = req.getParameter("qParentId");
			
			String qName = req.getParameter("qName");
			String qhId = req.getParameter("qhId");
			String qtId = req.getParameter("qtId");

			String[] pkIds = req.getParameterValues("pkIds"); 
			String[] pkValues = req.getParameterValues("pkValues");
			
			QualifierDTO q = new QualifierDTO();
			
			QualifierHierarchyDTO qh = authzService.getQualifierHierarchy(qhId);
			QualifierTypeDTO qt = authzService.getQualifierType(qtId);
			
			q.setName(qName);
			q.setQualifierHierarchy(qh);
			q.setQualifierType(qt);
			q.setParentId(qParentId);
			
			//Add the pk if necessary
			Map<String,String> pkQualifiers = new HashMap<String,String>(); 
			if(pkIds!=null&&pkValues!=null&&pkIds.length==pkValues.length){
				for(int i=0;i<pkIds.length;i++){
					pkQualifiers.put(pkIds[i], pkValues[i]);
				}
			}
			
			q.setPkQualifiers(pkQualifiers);
			authzService.saveQualifier(q);
			
			viewQualifiers(req,resp);
			return;
			
		}
		
		if("createNewQualifier".equals(action)){
			String qParentId = req.getParameter("qParentId");
			
			String qName = req.getParameter("qName");
			String qhId = req.getParameter("qhId");
			String qtId = req.getParameter("qtId");


			List<QualifierHierarchyDTO> qualifierHierarchies = new ArrayList<QualifierHierarchyDTO>();
			//If this has a parent then use the parent's qualifier hierarchy
			if(qParentId!=null&&!"".equals(qParentId)){
				QualifierDTO qParent = authzService.getQualifier(qParentId);
				if(qParent!=null){
					QualifierHierarchyDTO qh = authzService.getQualifierHierarchy(qParent.getQualifierHierarchy().getId());
					qualifierHierarchies.add(qh);
				}
			}
			
			//If no hierarchies were found then get the list of everything
			if(qualifierHierarchies.size()==0){
				qualifierHierarchies = authzService.getQualifierHierarchies();
			}
			
			req.setAttribute("qualifierHierarchies", qualifierHierarchies);
			
			req.setAttribute("qParentId", qParentId);
			req.setAttribute("qName", qName);
			req.setAttribute("qhId", qhId);
			req.setAttribute("qtId", qtId);

			RequestDispatcher dispatcher = req.getRequestDispatcher("createNewQualifier.jsp");
	        dispatcher.forward(req, resp);
	        return;
		}
		
		if("searchPrincipalsByAuth".equals(action)){
			searchPrincipalsByAuth(req,resp);
			return;
		}
		
		if("viewPrincipalsWithAuth".equals(action)){
			viewPrincipalsWithAuth(req,resp);
			return;
		}
		
	}

	private void searchPrincipalsByAuth(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		String qid = req.getParameter("qid");

		List<PermissionDTO> permissions = authzService.getPermissions();
		req.setAttribute("permissions", permissions);
		
		req.setAttribute("qid", qid);
		
		if(qid!=null&&!"".equals(qid)){
			req.setAttribute("qualifier", authzService.getQualifier(qid));
		}
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("searchPrincipalsByAuth.jsp");
        dispatcher.forward(req, resp);
        return;
	}
	
	private void viewPrincipalsWithAuth(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		String qid = req.getParameter("qid");
		String permId = req.getParameter("permId");
		
		List<PrincipalDTO> principals= authzService.getPrincipalsWithQualifierAndPermissions(permId, qid);
		req.setAttribute("principals", principals);

		
        RequestDispatcher dispatcher = req.getRequestDispatcher("viewPrincipals.jsp");
        dispatcher.forward(req, resp);
        return;
	}

	private void viewQualifiers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String qid=req.getParameter("qid");
		String qname=req.getParameter("qname");
		String level=req.getParameter("level");

		//Get the qualifierHierarchy if it's set
		String qhId=null;
		String rid=(String)req.getSession().getAttribute("rid");
		if(rid!=null&&!"".equals(rid)){
			RoleDTO role = authzService.getRole(rid);
			if(role.getQualifierHierarchy()!=null){
				qhId = role.getQualifierHierarchy().getId();
			}
			req.setAttribute("role", role);
		}
		
		if( qid!=null ){
			
			List<QualifierDTO> qchildren = authzService.getQualifiersDirectDescendents(qid,qhId);
			req.setAttribute("qualifiers", qchildren);
			
			QualifierDTO parent = authzService.getQualifier(qid);
						
			List<QualifierDTO> breadcrumbs = (List<QualifierDTO>) req.getSession().getAttribute("breadcrumbs");
			if(breadcrumbs==null){
				breadcrumbs = new ArrayList<QualifierDTO>();
			}
			if(level==null){
				//drilling down
				level = (String)req.getSession().getAttribute("level");
				breadcrumbs.add(parent);
				level=String.valueOf(Integer.valueOf(level)+1);
			}else{
				//clicked on a breadcrumb
				int levelInt = Integer.valueOf(level);
				breadcrumbs=breadcrumbs.subList(0, levelInt+1);
				level=String.valueOf(levelInt);
			}
			req.getSession().setAttribute("breadcrumbs", breadcrumbs);
			req.getSession().setAttribute("level",level);
		}else{
			List<QualifierDTO> qualifiers = authzService.getQualifierRoots(qhId);
			req.setAttribute("qualifiers", qualifiers);
			req.getSession().setAttribute("breadcrumbs", new ArrayList<QualifierDTO>());
			req.getSession().setAttribute("level","0");
		}
		
		
        RequestDispatcher dispatcher = req.getRequestDispatcher("viewQualifiers.jsp");
        dispatcher.forward(req, resp);
        return;
	}
	
}
