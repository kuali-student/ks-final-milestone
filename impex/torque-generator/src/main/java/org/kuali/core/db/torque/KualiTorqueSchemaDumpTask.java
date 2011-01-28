package org.kuali.core.db.torque;

import static org.kuali.db.jdbc.JDBCUtils.closeQuietly;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.torque.engine.database.model.TypeMap;
import org.apache.torque.engine.platform.Platform;
import org.apache.torque.engine.platform.PlatformFactory;
import org.apache.xerces.dom.DocumentImpl;
import org.apache.xerces.dom.DocumentTypeImpl;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.kuali.core.db.torque.pojo.Column;
import org.kuali.core.db.torque.pojo.ForeignKey;
import org.kuali.core.db.torque.pojo.Index;
import org.kuali.core.db.torque.pojo.Reference;
import org.w3c.dom.Element;

public class KualiTorqueSchemaDumpTask extends DumpTask {

    boolean processTables = true;
    boolean processViews = true;
    boolean processSequences = true;

    /**
     * The file XML will be written to
     */
    File schemaXMLFile;

    /**
     * DOM document produced.
     */
    DocumentImpl doc;

    /**
     * The document root element.
     */
    Element databaseNode;

    /**
     * Map of columns that have primary keys.
     */
    Map<String, String> primaryKeys;

    @Override
    protected void showConfiguration() {
        super.showConfiguration();
        log("Exporting to: " + schemaXMLFile.getAbsolutePath());
    }

    protected String getSystemId() {
        if (antCompatibilityMode) {
            return "database.dtd";
        } else {
            return ImpexDTDResolver.DTD_LOCATION;
        }
    }

    protected DocumentImpl getDocumentImpl() {
        DocumentTypeImpl docType = new DocumentTypeImpl(null, "database", null, getSystemId());
        DocumentImpl doc = new DocumentImpl(docType);
        doc.appendChild(doc.createComment(" " + getComment() + " "));
        return doc;
    }

    /**
     * Execute the task
     */
    @Override
    public void execute() throws BuildException {
        try {
            log("--------------------------------------");
            log("Impex - Schema Export");
            log("--------------------------------------");
            log("Loading platform for " + getTargetDatabase());
            Platform platform = PlatformFactory.getPlatformFor(targetDatabase);
            updateConfiguration(platform);
            showConfiguration();
            doc = getDocumentImpl();
            generateXML(platform);
            serialize();
        } catch (Exception e) {
            throw new BuildException(e);
        }
        log("Impex - Schema Export finished");
    }

    protected void serialize() throws BuildException {
        Writer out = null;
        try {
            File file = new File(FilenameUtils.getFullPath(getSchemaXMLFile().getCanonicalPath()));
            file.mkdirs();
            out = new PrintWriter(new FileOutputStream(getSchemaXMLFile()));
            OutputFormat format = new OutputFormat(Method.XML, getEncoding(), true);
            XMLSerializer xmlSerializer = new XMLSerializer(out, format);
            xmlSerializer.serialize(doc);
        } catch (Exception e) {
            throw new BuildException("Error serializing", e);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    protected Map<String, String> getPrimaryKeys(final Platform platform, final DatabaseMetaData dbMetaData,
            final String curTable) throws SQLException {
        List<String> primKeys = platform.getPrimaryKeys(dbMetaData, schema, curTable);

        // Set the primary keys.
        Map<String, String> primaryKeys = new HashMap<String, String>();
        for (int k = 0; k < primKeys.size(); k++) {
            String curPrimaryKey = primKeys.get(k);
            primaryKeys.put(curPrimaryKey, curPrimaryKey);
        }
        return primaryKeys;
    }

    protected Element getColumnElement(final Column col, final String curTable) {
        String name = col.getName();
        Integer type = col.getSqlType();
        int size = col.getSize();
        int scale = col.getDecimalDigits();

        Integer nullType = col.getNullType();
        String defValue = col.getDefValue();

        Element column = doc.createElement("column");
        column.setAttribute("name", name);

        column.setAttribute("type", TypeMap.getTorqueType(type).getName());

        if (size > 0
                && (type.intValue() == Types.CHAR || type.intValue() == Types.VARCHAR
                        || type.intValue() == Types.LONGVARCHAR || type.intValue() == Types.DECIMAL || type.intValue() == Types.NUMERIC)) {
            column.setAttribute("size", String.valueOf(size));
        }

        if (scale > 0 && (type.intValue() == Types.DECIMAL || type.intValue() == Types.NUMERIC)) {
            column.setAttribute("scale", String.valueOf(scale));
        }

        if (primaryKeys.containsKey(name)) {
            column.setAttribute("primaryKey", "true");
            // JHK: protect MySQL from excessively long column in the PK
            // System.out.println( curTable + "." + name + " / " + size );
            if (column.getAttribute("size") != null && size > 765) {
                log("updating column " + curTable + "." + name + " length from " + size + " to 255");
                column.setAttribute("size", "255");
            }
        } else {
            if (nullType.intValue() == DatabaseMetaData.columnNoNulls) {
                column.setAttribute("required", "true");
            }
        }

        if (StringUtils.isNotBlank(defValue)) {
            defValue = getDefaultValue(defValue);
            column.setAttribute("default", defValue);
        }
        return column;
    }

    protected String getDefaultValue(String defValue) {
        if (StringUtils.isBlank(defValue)) {
            return null;
        }
        defValue = defValue.trim();
        // trim out parens & quotes out of def value.
        // makes sense for MSSQL. not sure about others.
        if (defValue.startsWith("(") && defValue.endsWith(")")) {
            defValue = defValue.substring(1, defValue.length() - 1);
        }

        if (defValue.startsWith("'") && defValue.endsWith("'")) {
            defValue = defValue.substring(1, defValue.length() - 1);
        }
        if (defValue.equals("NULL")) {
            defValue = "";
        }
        return defValue;
    }

    protected void processColumns(final DatabaseMetaData dbMetaData, final String curTable, final Element table)
            throws SQLException {
        List<Column> columns = getColumns(dbMetaData, curTable);
        for (Column column : columns) {
            Element columnElement = getColumnElement(column, curTable);
            table.appendChild(columnElement);
        }
    }

    protected void processForeignKeys(final DatabaseMetaData dbMetaData, final String curTable, final Element table)
            throws SQLException {
        Map<String, ForeignKey> foreignKeys = getForeignKeys(dbMetaData, curTable);

        // Foreign keys for this table.
        for (String fkName : foreignKeys.keySet()) {
            Element fk = getForeignKeyElement(fkName, foreignKeys);
            table.appendChild(fk);
        }
    }

    protected Element getForeignKeyElement(final String fkName, final Map<String, ForeignKey> foreignKeys) {
        Element fk = doc.createElement("foreign-key");
        fk.setAttribute("name", fkName);
        ForeignKey forKey = foreignKeys.get(fkName);
        String foreignKeyTable = forKey.getRefTableName();
        List<Reference> refs = forKey.getReferences();
        fk.setAttribute("foreignTable", foreignKeyTable);
        String onDelete = forKey.getOnDelete();
        // gmcgrego - just adding onDelete if it's cascade so as not to affect kfs behavior
        if (onDelete == "cascade") {
            fk.setAttribute("onDelete", onDelete);
        }
        for (Reference refData : refs) {
            Element ref = doc.createElement("reference");
            ref.setAttribute("local", refData.getLocalColumn());
            ref.setAttribute("foreign", refData.getForeignColumn());
            fk.appendChild(ref);
        }
        return fk;
    }

    protected void processIndexes(final DatabaseMetaData dbMetaData, final String curTable, final Element table)
            throws SQLException {
        for (Index idx : getIndexes(dbMetaData, curTable)) {
            String tagName = idx.isUnique() ? "unique" : "index";
            Element index = doc.createElement(tagName);
            index.setAttribute("name", idx.getName());
            for (String colName : idx.getColumns()) {
                Element col = doc.createElement(tagName + "-column");
                col.setAttribute("name", colName);
                index.appendChild(col);
            }
            table.appendChild(index);
        }
    }

    protected void processTable(final String curTable, final Platform platform, final DatabaseMetaData dbMetaData)
            throws SQLException {
        long start = System.currentTimeMillis();

        Element table = doc.createElement("table");
        table.setAttribute("name", curTable);

        // Setup the primary keys.
        primaryKeys = getPrimaryKeys(platform, dbMetaData, curTable);

        // Process columns
        processColumns(dbMetaData, curTable, table);

        // Process foreign keys
        processForeignKeys(dbMetaData, curTable, table);

        // Process indexes
        processIndexes(dbMetaData, curTable, table);

        // Add this table to the XML
        databaseNode.appendChild(table);

        log(utils.pad("Processed " + curTable, System.currentTimeMillis() - start));
    }

    protected void processTables(final Platform platform, final DatabaseMetaData dbMetaData) throws SQLException {
        if (!processTables) {
            return;
        }

        List<String> tableList = platform.getTableNames(dbMetaData, schema);
        log("Found " + tableList.size() + " tables");
        StringFilter filterer = new StringFilter(includePatterns, excludePatterns);
        filterer.filter(tableList.iterator());
        log("Processing " + tableList.size() + " tables after filtering is applied");

        for (String curTable : tableList) {
            processTable(curTable, platform, dbMetaData);
        }
    }

    protected void processViews(final Platform platform, final DatabaseMetaData dbMetaData) throws SQLException {
        if (!processViews) {
            return;
        }
        List<String> viewNames = getViewNames(dbMetaData);
        for (String viewName : viewNames) {
            Element view = doc.createElement("view");
            view.setAttribute("name", viewName);
            /**
             * <view name="" viewdefinition="" />
             */
            String definition = platform.getViewDefinition(dbMetaData.getConnection(), schema, viewName);
            definition = definition.replaceAll("\0", "");
            view.setAttribute("viewdefinition", definition);
            databaseNode.appendChild(view);
        }
    }

    protected void processSequences(final Platform platform, final DatabaseMetaData dbMetaData) throws SQLException {
        if (!processSequences) {
            return;
        }
        List<String> sequenceNames = getSequenceNames(dbMetaData);
        for (String sequenceName : sequenceNames) {
            Element sequence = doc.createElement("sequence");
            sequence.setAttribute("name", sequenceName);
            /*
             * <view name="" nextval="" />
             */
            Long nextVal = platform.getSequenceNextVal(dbMetaData.getConnection(), schema, sequenceName);
            sequence.setAttribute("nextval", nextVal.toString());

            databaseNode.appendChild(sequence);
        }
        doc.appendChild(databaseNode);
    }

    protected String getName() {
        return artifactId;
    }

    /**
     * Generates an XML database schema from JDBC metadata.
     *
     * @throws Exception
     * a generic exception.
     */
    protected void generateXML(final Platform platform) throws Exception {

        Connection connection = null;
        try {
            // Attempt to connect to a database.
            connection = getConnection();

            // Get the database Metadata.
            DatabaseMetaData dbMetaData = connection.getMetaData();

            databaseNode = doc.createElement("database");
            databaseNode.setAttribute("name", getName());
            // JHK added naming method
            databaseNode.setAttribute("defaultJavaNamingMethod", "nochange");

            processTables(platform, dbMetaData);
            processViews(platform, dbMetaData);
            processSequences(platform, dbMetaData);
        } finally {
            closeQuietly(connection);
        }
    }

    public List<String> getViewNames(final DatabaseMetaData dbMeta) throws SQLException {
        log("Getting view list...");
        List<String> tables = new ArrayList<String>();
        ResultSet tableNames = null;
        // these are the entity types we want from the database
        String[] types = { "VIEW" }; // JHK: removed views from list
        try {
            tableNames = dbMeta.getTables(null, schema, null, types);
            while (tableNames.next()) {
                String name = tableNames.getString(3);
                tables.add(name);
            }
        } finally {
            if (tableNames != null) {
                tableNames.close();
            }
        }
        log("Found " + tables.size() + " views.");
        return tables;
    }

    public boolean isSequence(final String sequenceName) {
        return sequenceName.toUpperCase().startsWith("SEQ_") || sequenceName.toUpperCase().startsWith("SEQUENCE_")
                || sequenceName.toUpperCase().endsWith("_SEQ") || sequenceName.toUpperCase().endsWith("_SEQUENCE")
                || sequenceName.toUpperCase().endsWith("_ID") || sequenceName.toUpperCase().endsWith("_S");
    }

    public List<String> getSequenceNames(final DatabaseMetaData dbMeta) throws SQLException {
        log("Getting sequence list...");
        List<String> tables = new ArrayList<String>();
        ResultSet tableNames = null;
        // these are the entity types we want from the database
        String[] types = { "TABLE", "SEQUENCE" }; // JHK: removed views from list
        try {
            tableNames = dbMeta.getTables(null, schema, null, types);
            while (tableNames.next()) {
                String name = tableNames.getString(3);
                if (isSequence(name)) {
                    tables.add(name);
                }
            }
        } finally {
            if (tableNames != null) {
                tableNames.close();
            }
        }
        log("Found " + tables.size() + " sequences.");
        return tables;
    }

    // for ( int i = 1; i <= tableNames.getMetaData().getColumnCount(); i++ ) {
    // System.out.print( tableNames.getMetaData().getColumnName( i ) + "," );
    // }
    // System.out.println();
    // for ( int i = 1; i <= tableNames.getMetaData().getColumnCount(); i++ ) {
    // System.out.print( tableNames.getString( i ) + "," );
    // }
    // System.out.println();

    /**
     * Retrieves all the column names and types for a given table from JDBC metadata. It returns a List of Lists. Each
     * element of the returned List is a List with: element 0 => a String object for the column name. element 1 => an
     * Integer object for the column type. element 2 => size of the column. element 3 => null type.
     *
     * @param dbMeta
     * JDBC metadata.
     * @param tableName
     * Table from which to retrieve column information.
     * @return The list of columns in <code>tableName</code>.
     * @throws SQLException
     */
    protected List<Column> getColumns(final DatabaseMetaData dbMeta, final String tableName) throws SQLException {
        List<Column> columns = new ArrayList<Column>();
        ResultSet columnSet = null;
        try {
            columnSet = dbMeta.getColumns(null, schema, tableName, null);
            while (columnSet.next()) {
                String name = columnSet.getString(4);
                Integer sqlType = new Integer(columnSet.getString(5));
                Integer size = new Integer(columnSet.getInt(7));
                Integer decimalDigits = new Integer(columnSet.getInt(9));
                Integer nullType = new Integer(columnSet.getInt(11));
                String defValue = columnSet.getString(13);

                Column col = new Column();
                col.setName(name);
                col.setSqlType(sqlType);
                col.setSize(size);
                col.setNullType(nullType);
                col.setDefValue(defValue);
                col.setDecimalDigits(decimalDigits);
                columns.add(col);
            }
        } finally {
            closeQuietly(columnSet);
        }
        return columns;
    }

    protected String getOnDelete(final ResultSet foreignKeys) throws SQLException {
        int deleteRule = foreignKeys.getInt(11);
        String onDelete = "none";
        if (deleteRule == DatabaseMetaData.importedKeyCascade) {
            onDelete = "cascade";
        } else if (deleteRule == DatabaseMetaData.importedKeyRestrict) {
            onDelete = "restrict";
        } else if (deleteRule == DatabaseMetaData.importedKeySetNull) {
            onDelete = "setnull";
        }
        return onDelete;
    }

    protected String getForeignKeyName(final ResultSet foreignKeys, final String refTableName) throws SQLException {
        String fkName = foreignKeys.getString(12);
        // if FK has no name - make it up (use tablename instead)
        if (fkName == null) {
            fkName = refTableName;
        }
        return fkName;
    }

    protected ForeignKey getNewKualiForeignKey(final String refTableName, final String onDelete) {
        ForeignKey fk = new ForeignKey();
        fk.setRefTableName(refTableName); // referenced table name
        fk.setReferences(new ArrayList<Reference>());
        fk.setOnDelete(onDelete);
        return fk;
    }

    protected void addForeignKey(final Map<String, ForeignKey> fks, final String fkName, final String refTableName,
            final String onDelete, final ResultSet foreignKeys) throws SQLException {
        ForeignKey fk = fks.get(fkName);
        if (fk == null) {
            fk = getNewKualiForeignKey(refTableName, onDelete);
            fks.put(fkName, fk);
        }
        List<Reference> references = fk.getReferences();
        Reference reference = new Reference();
        reference.setLocalColumn(foreignKeys.getString(8)); // local column
        reference.setForeignColumn(foreignKeys.getString(4)); // foreign column
        references.add(reference);
    }

    /**
     * Retrieves a list of foreign key columns for a given table.
     *
     * @param dbMeta
     * JDBC metadata.
     * @param tableName
     * Table from which to retrieve FK information.
     * @return A list of foreign keys in <code>tableName</code>.
     * @throws SQLException
     */
    protected Map<String, ForeignKey> getForeignKeys(final DatabaseMetaData dbMeta, final String tableName)
            throws SQLException {
        Map<String, ForeignKey> fks = new HashMap<String, ForeignKey>();
        ResultSet foreignKeys = null;
        try {
            foreignKeys = dbMeta.getImportedKeys(null, schema, tableName);
            while (foreignKeys.next()) {
                String refTableName = foreignKeys.getString(3);
                String fkName = getForeignKeyName(foreignKeys, refTableName);
                String onDelete = getOnDelete(foreignKeys);
                addForeignKey(fks, fkName, refTableName, onDelete, foreignKeys);
            }
        } catch (SQLException e) {
            // this seems to be happening in some db drivers (sybase)
            // when retrieving foreign keys from views.
            log("Could not read foreign keys for Table " + tableName + " : " + e.getMessage(), Project.MSG_WARN);
        } finally {
            closeQuietly(foreignKeys);
        }
        return fks;
    }

    protected String getPrimaryKeyName(final String tableName, final DatabaseMetaData dbMeta) throws SQLException {
        ResultSet pkInfo = null;
        try {
            pkInfo = dbMeta.getPrimaryKeys(null, schema, tableName);
            if (pkInfo.next()) {
                return pkInfo.getString("PK_NAME");
            }
        } catch (SQLException e) {
            log("Could not locate primary key info for " + tableName + " : " + e.getMessage(), Project.MSG_WARN);
        } finally {
            closeQuietly(pkInfo);
        }
        return null;
    }

    protected Index getTableIndex(final ResultSet indexInfo, final String pkName) throws SQLException {
        Index index = new Index();
        index.setName(indexInfo.getString("INDEX_NAME"));
        index.setUnique(!indexInfo.getBoolean("NON_UNIQUE"));
        return index;
    }

    protected void addIndexIfNotPK(final Index index, final String pkName, final List<Index> indexes) {
        // if has the same name as the PK, don't add it to the index list
        if (pkName == null || !pkName.equals(index.getName())) {
            indexes.add(index);
            log("Added " + index.getName() + " to index list", Project.MSG_DEBUG);
        } else {
            log("Skipping PK: " + index.getName(), Project.MSG_DEBUG);
        }
    }

    public List<Index> getIndexes(final DatabaseMetaData dbMeta, final String tableName) throws SQLException {
        List<Index> indexes = new ArrayList<Index>();

        // need to ensure that the PK is not returned as an index
        String pkName = getPrimaryKeyName(tableName, dbMeta);

        ResultSet indexInfo = null;
        try {
            indexInfo = dbMeta.getIndexInfo(null, schema, tableName, false, true);
            Index currIndex = null;
            while (indexInfo.next()) {

                // Extract the name of the index
                String name = indexInfo.getString("INDEX_NAME");
                if (name == null) {
                    // If there is no name, we are done
                    continue;
                }

                // If this is the first time we are assigning a value to currIndex, OR
                // we have scrolled to the next row in the result set and are now on a
                // new index, we need to add to our list of indexes
                if (currIndex == null || !name.equals(currIndex.getName())) {
                    // Get a new TableIndex object
                    currIndex = getTableIndex(indexInfo, pkName);
                    // Add this index to the list if it is not the primary key index
                    // The PK is handled elsewhere
                    addIndexIfNotPK(currIndex, pkName, indexes);
                }

                // Add column information to the current index
                currIndex.getColumns().add(indexInfo.getString("COLUMN_NAME"));
            }
        } catch (SQLException e) {
            log("Could not read indexes for Table " + tableName + " : " + e.getMessage(), Project.MSG_WARN);
        } finally {
            closeQuietly(indexInfo);
        }
        return indexes;
    }

    public DocumentImpl getDoc() {
        return doc;
    }

    public void setDoc(final DocumentImpl doc) {
        this.doc = doc;
    }

    public Element getDatabaseNode() {
        return databaseNode;
    }

    public void setDatabaseNode(final Element databaseNode) {
        this.databaseNode = databaseNode;
    }

    public Map<String, String> getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(final Map<String, String> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    public boolean isProcessTables() {
        return processTables;
    }

    public boolean isProcessViews() {
        return processViews;
    }

    public boolean isProcessSequences() {
        return processSequences;
    }

    public File getSchemaXMLFile() {
        return schemaXMLFile;
    }

    public void setSchemaXMLFile(final File schemaXMLFile) {
        this.schemaXMLFile = schemaXMLFile;
    }

    public void setProcessTables(final boolean processTables) {
        this.processTables = processTables;
    }

    public void setProcessViews(final boolean processViews) {
        this.processViews = processViews;
    }

    public void setProcessSequences(final boolean processSequences) {
        this.processSequences = processSequences;
    }
}
