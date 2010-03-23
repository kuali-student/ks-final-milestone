/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.common.ws.security;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * Since we don't have an authentication service, this is a stupid class that  
 * verifies passwords and returns person ids based on a ks.users.xml file loaded from the classpath.
 *
 */
public class AuthenticationService {

    private static Map<String, String> passwords;
    private static Map<String, String> personids;
    
    //Load personids and passwords from ks.users.xml file into a hash
    static {
        passwords = new HashMap<String, String>();
        personids = new HashMap<String, String>();
        
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(AuthenticationService.class.getClassLoader().getResourceAsStream("ks.users.xml"));        
            NodeList users = doc.getElementsByTagName("user");
            for(int s=0; s < users.getLength() ; s++){
                Element user = (Element)users.item(s);
                passwords.put(user.getAttribute("username"), user.getAttribute("password"));
                personids.put(user.getAttribute("username"), user.getAttribute("personid"));
            }
        } catch (Exception e){
        }
    }
    
    public static boolean validateUsernamePassword(String username, String password){
        return password.equals(passwords.get(username));
    }
    
    public static String getPasswordForUsername(String username){
        return passwords.get(username);
    }
    
    public static String getPersonIdForUsername(String username){
        return personids.get(username);
    }
}
