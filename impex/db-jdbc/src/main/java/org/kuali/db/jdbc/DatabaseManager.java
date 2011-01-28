package org.kuali.db.jdbc;

public class DatabaseManager {
    Credentials credentials;
    Credentials dbaCredentials;
    DatabaseManagerMode mode;

    /**
     * @return the credentials
     */
    public Credentials getCredentials() {
        return credentials;
    }

    /**
     * @param credentials
     * the credentials to set
     */
    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    /**
     * @return the dbaCredentials
     */
    public Credentials getDbaCredentials() {
        return dbaCredentials;
    }

    /**
     * @param dbaCredentials
     * the dbaCredentials to set
     */
    public void setDbaCredentials(final Credentials dbaCredentials) {
        this.dbaCredentials = dbaCredentials;
    }

    /**
     * @return the mode
     */
    public DatabaseManagerMode getMode() {
        return mode;
    }

    /**
     * @param mode
     * the mode to set
     */
    public void setMode(final DatabaseManagerMode mode) {
        this.mode = mode;
    }

}
