package org.apache.torque.mojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.torque.mojo.morph.MorphRequest;
import org.apache.torque.mojo.morph.Morpher;
import org.apache.torque.mojo.morph.SchemaMorpher;
import org.codehaus.plexus.util.FileUtils;

/**
 * Morph the xml inside KS_DB_VERSION.xml so it has Maven friendly placeholders
 * for version info
 *
 * @goal morphversiontable
 * @phase generate-sources
 */
public class MorphVersionTableMojo extends BaseMojo {

    /**
     * The XML file containing version information
     *
     * @parameter expression="${newVersionXMLFile}" default-value=
     * "${project.build.directory}/generated-impex/KS_DB_VERSION.xml"
     * @required
     */
    private File newVersionXMLFile;

    /**
     * The path to the directory where the schema XML files are located
     *
     * @parameter expression="${oldVersionXMLFile}"
     * default-value="${basedir}/src/main/impex/KS_DB_VERSION.xml"
     * @required
     */
    private File oldVersionXMLFile;

    /**
     * Add logic to the basic skip() method for skipping based on a morph being
     * needed
     */
    @Override
    protected boolean skipMojo() {
        // We may be skipping based on packaging of type 'pom'
        if (super.skipMojo()) {
            return true;
        }

        // If a morph is needed, we can't skip
        boolean morphNeeded = isMorphNeeded();
        if (morphNeeded) {
            return false;
        } else {
            getLog().info("Skipping morph.  Nothing has changed");
            return true;
        }
    }

    protected boolean isMorphNeeded() {
        // The new file does not exist, we need to morph
        if (!newVersionXMLFile.exists()) {
            return true;
        }

        // Extract the last modified timestamps
        long oldLastModified = oldVersionXMLFile.lastModified();
        long newLastModified = newVersionXMLFile.lastModified();

        // If old file has been modified since the new file was last modified,
        // we need to morph
        return oldLastModified > newLastModified;
    }

    @Override
    protected void executeMojo() throws MojoExecutionException {
        getLog().info(
                "------------------------------------------------------------------------");
        getLog().info("Converting version table XML file");
        getLog().info(
                "------------------------------------------------------------------------");
        try {
            FileUtils.forceMkdir(new File(FileUtils.getPath(newVersionXMLFile
                    .getAbsolutePath())));
            MorphRequest request = new MorphRequest(
                    oldVersionXMLFile.getName(), new FileInputStream(
                            oldVersionXMLFile), new FileOutputStream(
                            newVersionXMLFile));
            request.setEncoding(getEncoding());
            Morpher morpher = new SchemaMorpher(request, getProject()
                    .getArtifactId());
            morpher.executeMorph();
        } catch (IOException e) {
            throw new MojoExecutionException(
                    "Unexpected error while attempting to morph "
                            + oldVersionXMLFile.getAbsolutePath(), e);
        }
    }

    /**
     * @return the newVersionXMLFile
     */
    public File getNewVersionXMLFile() {
        return newVersionXMLFile;
    }

    /**
     * @param newVersionXMLFile
     * the newVersionXMLFile to set
     */
    public void setNewVersionXMLFile(final File newVersionXMLFile) {
        this.newVersionXMLFile = newVersionXMLFile;
    }

    /**
     * @return the oldVersionXMLFile
     */
    public File getOldVersionXMLFile() {
        return oldVersionXMLFile;
    }

    /**
     * @param oldVersionXMLFile
     * the oldVersionXMLFile to set
     */
    public void setOldVersionXMLFile(final File oldVersionXMLFile) {
        this.oldVersionXMLFile = oldVersionXMLFile;
    }
}
