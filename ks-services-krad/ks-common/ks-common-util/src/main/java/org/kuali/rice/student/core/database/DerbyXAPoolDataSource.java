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

//package org.kuali.rice.student.core.database;

//import java.sql.SQLException;

//import org.kuali.rice.core.database.XAPoolDataSource;

/**
 * This extends the rice XAPoolDataSource and checks to see if a connection is being
 * made using derby client driver. If a client driver is used and a connection cannot
 * be obtained, means a derby server has not been started. In such a case must switch
 * to use the embedded driver instead. If derby is set to startup in server mode, the
 * embedded db will be reachable via a network connection.
 * 
 * @author Kuali Student Team
 *
 */
//@SuppressWarnings("deprecation")
//public class DerbyXAPoolDataSource extends XAPoolDataSource{

//    private static final long serialVersionUID = 1L;  
    
//    public void afterPropertiesSet() throws Exception {
//        super.afterPropertiesSet();
        //If client connection fails, use embedded driver
//        if ("org.apache.derby.jdbc.ClientDriver".equals(getDriverClassName())){
//            try{
//                getConnection();
//            } catch (SQLException e) {
//                super.shutdown(true);
//                setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");                
//            }
//        }
//    }

//}
