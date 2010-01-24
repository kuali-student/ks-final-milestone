package org.kuali.student.commons.ui.validators.server.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.TreeMap;

import org.kuali.student.commons.ui.validators.client.ValidatorDefinition;
import org.kuali.student.commons.ui.validators.client.ValidatorService;

/**
 * Validator service implementation. TODO replace with actual service
 */
public class ValidatorServiceImpl implements ValidatorService {

    /**
     * @see org.kuali.student.commons.ui.validators.client.ValidatorService#createValidator(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public Boolean createValidator(String id, String type, String script) {
        return createValidator(id, type, new ByteArrayInputStream(script.getBytes()));
    }

    private Boolean createValidator(String id, String type, InputStream script) {
        boolean result = false;
        Connection conn = null;
        try {
            conn = getConnection();
            create(conn, id, type, script);
        } catch (Exception e) {
            throw new RuntimeException("Unable to update validator " + id, e);
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

    private boolean create(Connection conn, String id, String type, InputStream script) throws SQLException {
        boolean result = false;
        PreparedStatement stmt = conn.prepareStatement("insert into validators (id, type, script) values (?, ?, ?)");
        stmt.setString(1, id);
        stmt.setString(2, type);
        stmt.setAsciiStream(3, script);
        result = stmt.execute();
        stmt.close();
        conn.commit();
        return result;
    }

    /**
     * @see org.kuali.student.commons.ui.validators.client.ValidatorService#deleteValidator(java.lang.String)
     */
    @Override
    public Boolean deleteValidator(String id) {
        boolean result = false;
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("delete from validators where id = ?");
            stmt.setString(1, id);
            result = stmt.execute();
            stmt.close();
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException("Unable to delete validator " + id, e);
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
     * @see org.kuali.student.commons.ui.validators.client.ValidatorService#updateValidator(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public Boolean updateValidator(String id, String type, String script) {
        boolean result = false;
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("update validators set type = ?, script = ? where id = ?");
            stmt.setString(1, type);
            stmt.setAsciiStream(2, new ByteArrayInputStream(script.getBytes()));
            stmt.setString(3, id);
            result = stmt.execute();
            stmt.close();
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException("Unable to update validator " + id, e);
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
     * @see org.kuali.student.commons.ui.validators.client.ValidatorService#getValidatorDefinition(java.lang.String)
     */
    @Override
    public ValidatorDefinition getValidatorDefinition(String id) {
        ValidatorDefinition result = null;
        Connection conn = null;
        try {
            conn = getConnection();
            bootstrap(conn, id);
            PreparedStatement stmt = conn.prepareStatement("select id, type, script from validators where id = ?");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                result = new ValidatorDefinition(rs.getString(1), rs.getString(2), getString(rs.getAsciiStream(3)));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException("Unable to retrieve validator " + id, e);
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
     * @see org.kuali.student.commons.ui.validators.client.ValidatorService#getValidatorDefinitions(java.lang.String)
     */
    @Override
    public Map<String, ValidatorDefinition> getValidatorDefinitions(String type) {
        Map<String, ValidatorDefinition> result = new TreeMap<String, ValidatorDefinition>();
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("select id, type, script from validators where type = ?");
            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.put(rs.getString(1), new ValidatorDefinition(rs.getString(1), rs.getString(2), getString(rs.getAsciiStream(3))));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException("Unable to retrieve validators for type " + type, e);
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

    private String getString(InputStream stream) throws IOException {
        StringBuilder result = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        return result.toString();
    }

    private Connection getConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        // System.setProperty("derby.system.home", System.getProperty("java.io.tmpdir") + "/derby");
        System.setProperty("derby.system.home", "./target");
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();

        Connection result = DriverManager.getConnection("jdbc:derby:ValidatorsDB;create=true");
        result.setAutoCommit(false);
        initializeDatabase(result);
        return result;
    }

    private void initializeDatabase(Connection conn) throws SQLException {
        ResultSet rs = conn.getMetaData().getTables(null, null, "VALIDATORS", null);
        boolean exists = rs.next();
        rs.close();

        if (!exists) {
            Statement stmt = conn.createStatement();

            stmt.execute("create table validators (" + " id varchar(50) not null," + " type varchar(50) not null, " + " script CLOB(64k) " + ")");

            stmt.close();
            conn.commit();
        }
    }

    private void bootstrap(Connection conn, String id) throws SQLException {
        if (requiresBootstrap(conn, id)) {
            System.out.println("Bootstrapping validator " + id);
            InputStream stream = this.getClass().getClassLoader().getResourceAsStream("validators/" + id + ".js");
            create(conn, id, "javascript", stream);
        }
    }

    private boolean requiresBootstrap(Connection conn, String id) throws SQLException {
        boolean result = false;
        PreparedStatement stmt = conn.prepareStatement("select 1 from validators where id = ?");
        stmt.setString(1, id);
        ResultSet rs = stmt.executeQuery();
        result = !rs.next();
        stmt.close();

        return result;
    }

}
