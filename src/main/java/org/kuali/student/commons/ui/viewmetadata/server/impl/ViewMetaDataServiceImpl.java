package org.kuali.student.commons.ui.viewmetadata.server.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.messages.client.MessagesService;
import org.kuali.student.commons.ui.messages.server.impl.MessagesServiceImpl;
import org.kuali.student.commons.ui.validators.client.ValidatorDefinition;
import org.kuali.student.commons.ui.validators.client.ValidatorService;
import org.kuali.student.commons.ui.validators.server.impl.ValidatorServiceImpl;
import org.kuali.student.commons.ui.viewmetadata.client.FieldMetaData;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaDataService;

/**
 * ViewMetaData service implementation. TODO replace with actual service, or use to proxy calls to dictionary service
 */
public class ViewMetaDataServiceImpl implements ViewMetaDataService {
    MessagesService messages = new MessagesServiceImpl();
    ValidatorService validators = new ValidatorServiceImpl();

    /**
     * @see org.kuali.student.commons.ui.viewmetadata.client.ViewMetaDataService#getViewMetaData(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public Map<String, ViewMetaData> getViewMetaData(String locale, String viewName) {
        Map<String, ViewMetaData> result = new HashMap<String, ViewMetaData>();
        loadViewMetadata(locale, viewName, result, null);
        return result;
    }

    /**
     * @see org.kuali.student.commons.ui.viewmetadata.client.ViewMetaDataService#getViewMetaDataMap(java.lang.String,
     *      java.util.List)
     */
    @Override
    public Map<String, ViewMetaData> getViewMetaDataMap(String locale, List<String> viewNames) {
        Map<String, ViewMetaData> result = new HashMap<String, ViewMetaData>();
        for (String s : viewNames) {
            result.putAll(getViewMetaData(locale, s)); 
        }
        return result;
    }

    private void loadViewMetadata(String locale, String viewName, Map<String, ViewMetaData> views, Messages rootLevelMessages) {
        ViewMetaData result = new ViewMetaData(viewName);

        Connection conn = null;
        try {
            conn = getConnection();
            bootstrap(conn, viewName);
            // get view fields
            PreparedStatement stmt = conn.prepareStatement("select vf.field_name, fa.attribute_key, fa.attribute_value " + " from view_fields vf join field_attributes fa on vf.field_name = fa.field_name " + " where vf.view_name = ? order by vf.field_name asc");
            stmt.setString(1, viewName);
            ResultSet rs = stmt.executeQuery();
            FieldMetaData field = null;
            while (rs.next()) {
                String fieldName = rs.getString(1);
                String key = rs.getString(2);
                String value = rs.getString(3);
                if ((field == null) || !field.getFieldName().equals(fieldName)) {
                    field = new FieldMetaData();
                    field.setFieldName(fieldName);
                    result.addField(field);
                }
                field.getAttributes().put(key, value);
                if (key.equals("validatorId")) {
                    ValidatorDefinition validator = validators.getValidatorDefinition(value);
                    if (validator != null) {
                        field.getAttributes().put("validatorScript", validator.getScript());
                    }
                }
            }
            rs.close();
            stmt.close();
            
            // get view configuration
            Map<String, String> config = new HashMap<String, String>();
            stmt = conn.prepareStatement("select attribute_key, attribute_value from view_configuration where view_name = ?");
            stmt.setString(1, viewName);
            rs = stmt.executeQuery();
            while (rs.next()) {
                config.put(rs.getString(1), rs.getString(2));
            }
            result.setConfiguration(config);
            
        } catch (Exception e) {
            throw new RuntimeException("Unable to retrieve view metadata", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    // eat it
                }
            }
        }

        result.setMessages(messages.getMessages(locale, viewName));
        if (rootLevelMessages == null) {
            rootLevelMessages = result.getMessages();
        } else {
            rootLevelMessages.getInheritedMessages().add(result.getMessages());
        }
        views.put(viewName, result);
        
        // traverse dependencies
        Set<String> depends = result.getInheritedViews();
        for (String s : depends) {
            System.out.println("LOADING MESSAGES BASED ON DEPENDENCY: " + s);
            if (!views.containsKey(s)) {
                loadViewMetadata(locale, s, views, rootLevelMessages);
            }
        }
    }



    private Connection getConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        // System.setProperty("derby.system.home", System.getProperty("java.io.tmpdir") + "/derby");
        System.setProperty("derby.system.home", "./target");
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();

        Connection result = DriverManager.getConnection("jdbc:derby:ViewMetaDataDB;create=true");
        result.setAutoCommit(false);
        initializeDatabase(result);
        return result;
    }

    private void initializeDatabase(Connection conn) throws SQLException {
        ResultSet rs = conn.getMetaData().getTables(null, null, "VIEW_CONFIGURATION", null);
        boolean exists = rs.next();
        rs.close();

        if (!exists) {
            System.out.println("tables do not exist");
            Statement stmt = conn.createStatement();

            try{
                stmt.execute("create table view_fields (" + " view_name varchar(50) not null," + " field_name varchar(50) not null " + ")");
                conn.commit();
                System.out.println("created view_fields table");
            }catch(SQLException ex){
                System.out.println("unable to create view_fields table");
                ex.printStackTrace();
                conn.rollback();
            }
            try{
                stmt.execute("create table field_attributes (" + " field_name varchar(50) not null," + " attribute_key varchar(50) not null," + " attribute_value varchar(250) not null " + ")");
                conn.commit();
                System.out.println("created field_attributes table");
            }catch(SQLException ex){
                System.out.println("unable to create field_attributes table");
                ex.printStackTrace();
                conn.rollback();
            }
            try{
                stmt.execute("create table view_configuration ( view_name varchar(50) not null, attribute_key varchar(50) not null, attribute_value varchar(250) not null )");
                conn.commit();
                System.out.println("created view_configuration table");
            }catch(SQLException ex){
                System.out.println("unable to create view_configuration table");
                ex.printStackTrace();
                conn.rollback();
            }
            
            stmt.close();
            conn.commit();
        }else {
            System.out.println("tables exist");
        }
    }

    private void bootstrap(Connection conn, String viewName) throws SQLException {
        System.out.println("Checking bootstrap for " + viewName);
        if (requiresBootstrap(conn, viewName)) {
            System.out.println("Bootstrapping " + viewName);
            try {
                ResourceBundle bundle = ResourceBundle.getBundle("views." + viewName + ".fields");
                Enumeration<String> keys = bundle.getKeys();
                if (keys.hasMoreElements()) {
                    Set<String> inserted = new HashSet<String>();
                    PreparedStatement fieldStmt = conn.prepareStatement("insert into view_fields (view_name, field_name) values (?, ?)");
                    PreparedStatement attribStmt = conn.prepareStatement("insert into field_attributes (field_name, attribute_key, attribute_value) values (?, ?, ?)");
                    while (keys.hasMoreElements()) {
                        String key = keys.nextElement();
                        String value = bundle.getString(key);

                        // split out field name and attribute name from "fieldName.attributeName" formatted string
                        String[] pair = key.split("\\.", 2);
                        if (pair.length != 2) {
                            throw new RuntimeException("Invalid field attribute key: " + key);
                        }

                        if (!inserted.contains(pair[0])) {
                            System.out.println("Inserting view_fields record for " + viewName + ", " + pair.toString());
                            fieldStmt.setString(1, viewName);
                            fieldStmt.setString(2, pair[0]);
                            fieldStmt.execute();
                        }

                        System.out.println("Inserting field_attributes record for " + viewName + ", " + pair.toString() + ", " + value);
                        attribStmt.setString(1, pair[0]);
                        attribStmt.setString(2, pair[1]);
                        attribStmt.setString(3, value);
                        attribStmt.execute();
                    }
                    conn.commit();
                }
            } catch (MissingResourceException mre) {
                System.out.println("No bootstrap field metadata file found for " + viewName);
            }
            try {
                ResourceBundle bundle = ResourceBundle.getBundle("views." + viewName + ".configuration");
                Enumeration<String> keys = bundle.getKeys();
                if (keys.hasMoreElements()) {
                    PreparedStatement stmt = conn.prepareStatement("insert into view_configuration (view_name, attribute_key, attribute_value) values (?, ?, ?)");
                    while(keys.hasMoreElements()) {
                        String key = keys.nextElement();
                        stmt.setString(1, viewName);
                        stmt.setString(2, key);
                        stmt.setString(3, bundle.getString(key));
                        stmt.execute();
                    }
                    conn.commit();
                }
                
            } catch (MissingResourceException mre) {
                System.out.println("No bootstrap view configuration file found for " + viewName);
            }
        }
    }

    private boolean requiresBootstrap(Connection conn, String viewName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select count(*) from view_fields where view_name = ?");
        stmt.setString(1, viewName);
        ResultSet rs = stmt.executeQuery();
        int count = 0;
        if (rs.next()) {
            count = rs.getInt(1);
        }
        stmt.close();

        return count == 0;
    }

}
