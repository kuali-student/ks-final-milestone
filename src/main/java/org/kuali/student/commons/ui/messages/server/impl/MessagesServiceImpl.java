package org.kuali.student.commons.ui.messages.server.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.messages.client.MessagesService;

/**
 * Internationalization messages service implementation, to be replaced by actual messages service.
 */
public class MessagesServiceImpl implements MessagesService {
    /**
     * @see org.kuali.student.commons.ui.messages.client.MessagesService
     */
    @Override
    public Messages getMessages(String locale, String groupName) {
        Messages result = null;
        Connection conn = null;
        try {
            conn = getConnection();
            bootstrap(conn, locale, groupName);

            PreparedStatement stmt = conn.prepareStatement("select messageId, message from messages where locale = ? and groupName = ?");
            stmt.setString(1, locale);
            stmt.setString(2, groupName);
            ResultSet rs = stmt.executeQuery();
            Map<String, String> m = new HashMap<String, String>();
            while (rs.next()) {
                m.put(rs.getString(1), rs.getString(2));
            }
            rs.close();
            result = new Messages(groupName, m);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve messages", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    // eat it
                }
            }
        }
        return result;
    }

    /**
     * @see org.kuali.student.commons.ui.messages.client.MessagesService
     */
    @Override
    public Map<String, Messages> getMessages(String locale, List<String> groupNames) {
        Map<String, Messages> result = new HashMap<String, Messages>();
        for (String s : groupNames) {
            result.put(s, getMessages(locale, s));
        }
        return result;
    }

    /**
     * @see org.kuali.student.commons.ui.messages.client.MessagesService
     */
    @Override
    public List<String> getGroupNames(String locale) {
        List<String> result = new ArrayList<String>();
        Connection conn = null;
        try {
            conn = getConnection();

            PreparedStatement stmt = conn.prepareStatement("select distinct groupName from messages where locale = ? order by groupName asc");
            stmt.setString(1, locale);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(rs.getString(1));
            }
            rs.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve messages", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    // eat it
                }
            }
        }
        return result;
    }

    /**
     * @see org.kuali.student.commons.ui.messages.client.MessagesService
     */
    @Override
    public List<String> getLocales() {
        List<String> result = new ArrayList<String>();
        Connection conn = null;
        try {
            conn = getConnection();

            PreparedStatement stmt = conn.prepareStatement("select distinct locale from messages order by locale asc");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(rs.getString(1));
            }
            rs.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve locales", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    // eat it
                }
            }
        }
        return result;
    }

    private Connection getConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        // System.setProperty("derby.system.home", System.getProperty("java.io.tmpdir") + "/tmp");
        System.setProperty("derby.system.home", "./target");
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();

        Connection result = DriverManager.getConnection("jdbc:derby:MessagesDB;create=true");
        result.setAutoCommit(false);
        initializeDatabase(result);
        return result;
    }

    private void initializeDatabase(Connection conn) throws SQLException {
        ResultSet rs = conn.getMetaData().getTables(null, null, "MESSAGES", null);
        boolean exists = rs.next();
        rs.close();

        if (!exists) {
            Statement stmt = conn.createStatement();

            stmt.execute("create table messages (" + " locale varchar(20) not null," + " groupName varchar(50) not null," + " messageId varchar(50) not null," + " message varchar(4000) not null" + ")");

            stmt.execute("alter table messages add constraint messages_pk primary key (locale, groupName, messageId)");
            stmt.close();
            conn.commit();
        }
    }

    private void bootstrap(Connection conn, String locale, String groupName) throws SQLException {
        System.out.println("Checking bootstrap for " + locale + " " + groupName);
        if (requiresBootstrap(conn, locale, groupName)) {
            System.out.println("Bootstrapping " + locale + " " + groupName);
            try {
                ResourceBundle bundle = ResourceBundle.getBundle("views." + groupName + ".messages", new Locale(locale));
                Enumeration<String> keys = bundle.getKeys();
                if (keys.hasMoreElements()) {
                    PreparedStatement stmt = conn.prepareStatement("insert into messages (locale, groupName, messageId, message) values (?, ?, ?, ?)");
                    while (keys.hasMoreElements()) {
                        String key = keys.nextElement();
                        String value = bundle.getString(key);

                        System.out.println("inserting " + key + "\t=\t" + value);
                        stmt.setString(1, locale);
                        stmt.setString(2, groupName);
                        stmt.setString(3, key);
                        stmt.setString(4, value);
                        stmt.execute();
                    }
                    conn.commit();
                }
            } catch (MissingResourceException mre) {
                System.out.println("No bootstrap messages file found for " + groupName + " locale " + locale);
            }
        }
    }

    private boolean requiresBootstrap(Connection conn, String locale, String groupName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select count(*) from messages where locale = ? and groupName = ?");
        stmt.setString(1, locale);
        stmt.setString(2, groupName);
        ResultSet rs = stmt.executeQuery();
        int count = 0;
        if (rs.next()) {
            count = rs.getInt(1);
        }
        stmt.close();

        return count == 0;
    }

}
