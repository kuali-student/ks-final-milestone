/*-
 * Copyright (c) 2009-2010, Oleg Estekhin
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  * Neither the names of the copyright holders nor the names of their
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */

package oe.maven.plugins.revision;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNErrorCode;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.admin.SVNEntry;
import org.tmatesoft.svn.core.wc.ISVNStatusHandler;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.wc.SVNStatusClient;
import org.tmatesoft.svn.core.wc.SVNStatusType;

/**
 * Retrieves the revision number and the status of a file or a directory under the Subversion version control.
 *
 * @goal revision
 * @phase initialize
 * @requiresProject
 */
public class RevisionMojo extends AbstractMojo {

    static {
        DAVRepositoryFactory.setup(); // http, https
        SVNRepositoryFactoryImpl.setup(); // svn, svn+xxx
        FSRepositoryFactory.setup(); // file
    }


    /**
     * The maven project.
     *
     * @parameter expression="${project}"
     * @readonly
     */
    private MavenProject project;

    /**
     * Specifies the list of entries to inspect. Each entry has a separate configuration consisting of the local path,
     * report options and the prefix for the output properties.
     * <p/>
     * The following example shows the entry configured with the default properties:
     * <pre>
     * &lt;entries&gt;
     *   &lt;entry&gt;
     *     &lt;path&gt;${project.basedir}&lt;path&gt;
     *     &lt;prefix&gt;${project.artifactId}&lt;prefix&gt;
     *     &lt;depth&gt;infinity&lt;depth&gt;
     *     &lt;reportUnversioned&gt;true&lt;reportUnversioned&gt;
     *     &lt;reportIgnored&gt;false&lt;reportIgnored&gt;
     *     &lt;reportOutOfDate&gt;false&lt;reportOutOfDate&gt;
     *   &lt;entry&gt;
     * &lt;entries&gt;
     * </pre>
     * <p/>
     * If entries configuration is not specified then the goal will operate on the default entry with the entry path
     * equal to the project basedir and the properties prefix equal to the project artifactId.
     *
     * @parameter
     */
    private Entry[] entries;

    /**
     * Specifies whether the goal runs in verbose mode.
     *
     * @parameter
     */
    private boolean verbose;


    public void execute() throws MojoExecutionException, MojoFailureException {
        if ( entries == null ) {
            logDebug( "the entries configuration is missing, using default values" );
            // defaulting to the path = project.basedir, prefix = project.artifactId, depth = infinity, reporting unversioned
            entries = new Entry[] {
                    new Entry( project.getBasedir(), project.getArtifactId() ),
            };
        } else if ( entries.length == 0 ) {
            throw new MojoExecutionException( "the entries configuration list is empty" );
        }

        SVNStatusClient statusClient = SVNClientManager.newInstance().getStatusClient();

        for ( Entry entry : entries ) {
            if ( entry.getPath() == null ) {
                logDebug( "the entry path is not specified, using project.basedir: " + project.getBasedir() );
                entry.setPath( project.getBasedir() );
            }
            logInfo( "inspecting " + entry.getPath() );
            if ( entry.getPrefix() == null ) {
                logDebug( "the entry prefix is not specified, using project.artifactId: " + project.getArtifactId() );
                entry.setPrefix( project.getArtifactId() );
            }
            logDebugInfo( "  prefix = " + entry.getPrefix() );
            logDebugInfo( "  depth = " + entry.getDepth() );
            logDebugInfo( "  report unversioned = " + entry.reportUnversioned() );
            logDebugInfo( "  report ignored = " + entry.reportIgnored() );
            logDebugInfo( "  report out-of-date = " + entry.reportOutOfDate() );
            entry.validate();
            Map<String, Object> entryProperties = getEntryProperties( entry, statusClient );
            setProjectProperties( entry.getPrefix(), entryProperties );
        }
    }


    private Map<String, Object> getEntryProperties( Entry entry, SVNStatusClient statusClient ) throws MojoExecutionException {
        try {
            SVNStatus status = statusClient.doStatus( entry.getPath(), false );
            return createVersionedEntryProperties( entry, status, statusClient );
        } catch ( SVNException e ) {
            SVNErrorCode errorCode = e.getErrorMessage() == null ? null : e.getErrorMessage().getErrorCode();
            if ( SVNErrorCode.WC_NOT_DIRECTORY.equals( errorCode ) ) {
                if ( getLog().isDebugEnabled() ) {
                    getLog().debug( e );
                }
                return createUnversionedEntryProperties();
            } else {
                if ( getLog().isErrorEnabled() ) {
                    getLog().error( e );
                }
                return createSpecialEntryProperties( entry, statusClient );
            }
        }
    }

    private Map<String, Object> createUnversionedEntryProperties() {
        Map<String, Object> properties = new LinkedHashMap<String, Object>();
        properties.put( "repository", "" );
        properties.put( "path", "" );
        properties.put( "revision", -1L );
        properties.put( "mixedRevisions", "false" );
        properties.put( "committedRevision", -1L );
        properties.put( "status", EntryStatusSymbols.DEFAULT.getStatusSymbol( SVNStatusType.STATUS_UNVERSIONED ) );
        properties.put( "specialStatus", EntryStatusSymbols.SPECIAL.getStatusSymbol( SVNStatusType.STATUS_UNVERSIONED ) );
        return properties;
    }

    private Map<String, Object> createVersionedEntryProperties( Entry entry, SVNStatus status, SVNStatusClient statusClient ) throws MojoExecutionException {
        VersionedEntryStatusHandler entryStatusHandler = new VersionedEntryStatusHandler();
        entryStatusHandler.setRepositoryProperties( status.getEntry() );
        try {
            logDebugInfo( " collecting status information" );
            statusClient.doStatus( entry.getPath(),
                    SVNRevision.WORKING, SVNDepth.fromString( entry.getDepth() ),
                    entry.reportOutOfDate(), true, entry.reportIgnored(), false,
                    entryStatusHandler,
                    null );
        } catch ( SVNException e ) {
            throw new MojoExecutionException( e.getMessage(), e );
        }

        Map<String, Object> properties = new LinkedHashMap<String, Object>();
        properties.put( "repository", entryStatusHandler.getRepositoryRoot() );
        properties.put( "path", entryStatusHandler.getRepositoryPath() );
        properties.put( "revision", entryStatusHandler.getMaximumRevisionNumber() );
        properties.put( "mixedRevisions", entryStatusHandler.isMixedRevisions() );
        properties.put( "committedRevision", entryStatusHandler.getMaximumCommittedRevisionNumber() );
        properties.put( "status", createStatusString( entry, entryStatusHandler.getLocalStatusTypes(), entryStatusHandler.getRemoteStatusTypes(), EntryStatusSymbols.DEFAULT ) );
        properties.put( "specialStatus", createStatusString( entry, entryStatusHandler.getLocalStatusTypes(), entryStatusHandler.getRemoteStatusTypes(), EntryStatusSymbols.SPECIAL ) );
        return properties;
    }

    private Map<String, Object> createSpecialEntryProperties( final Entry entry, SVNStatusClient statusClient ) throws MojoExecutionException {
        // hack: obtain the status of a single "problem" entry by asking the entry's parent
        SpecialEntryStatusHandler entryStatusHandler = new SpecialEntryStatusHandler( entry.getPath().getName() );
        try {
            logDebugInfo( " collecting status information from the entry parent" );
            statusClient.doStatus( entry.getPath().getParentFile(),
                    SVNRevision.WORKING, SVNDepth.IMMEDIATES,
                    false, true, entry.reportIgnored(), false,
                    entryStatusHandler,
                    null );
        } catch ( SVNException e ) {
            throw new MojoExecutionException( e.getMessage(), e );
        }

        Map<String, Object> properties = new LinkedHashMap<String, Object>();
        properties.put( "repository", entryStatusHandler.getRepositoryRoot() );
        properties.put( "path", entryStatusHandler.getRepositoryPath() );
        properties.put( "revision", entryStatusHandler.getMaximumRevisionNumber() );
        properties.put( "mixedRevisions", entryStatusHandler.isMixedRevisions() );
        properties.put( "committedRevision", entryStatusHandler.getMaximumCommittedRevisionNumber() );
        properties.put( "status", createStatusString( entry, entryStatusHandler.getLocalStatusTypes(), entryStatusHandler.getRemoteStatusTypes(), EntryStatusSymbols.DEFAULT ) );
        properties.put( "specialStatus", createStatusString( entry, entryStatusHandler.getLocalStatusTypes(), entryStatusHandler.getRemoteStatusTypes(), EntryStatusSymbols.SPECIAL ) );
        return properties;
    }


    private void setProjectProperties( String prefix, Map<String, Object> entryProperties ) {
        logDebugInfo( " setting properties" );
        for ( Map.Entry<String, Object> entryProperty : entryProperties.entrySet() ) {
            setProjectProperty( prefix + '.' + entryProperty.getKey(), String.valueOf( entryProperty.getValue() ) );
        }
    }

    private void setProjectProperty( String name, String value ) {
        Properties projectProperties = project.getProperties();
        if ( projectProperties.getProperty( name ) != null ) {
            logWarning( "the \"" + name + "\" property is already defined, its value will be overwritten. Consider another value for the entry properties prefix." );
        }
        projectProperties.setProperty( name, value );
        logDebugInfo( "  " + name + " = " + value );
    }


    private String createStatusString( Entry entry, Set<SVNStatusType> localStatusTypes, Set<SVNStatusType> remoteStatusTypes, EntryStatusSymbols symbols ) {
        StringBuilder status = new StringBuilder();

        localStatusTypes.remove( SVNStatusType.STATUS_NONE );
        localStatusTypes.remove( SVNStatusType.STATUS_NORMAL );
        if ( localStatusTypes.remove( SVNStatusType.STATUS_ADDED ) ) {
            status.append( symbols.getStatusSymbol( SVNStatusType.STATUS_ADDED ) );
        }
        if ( localStatusTypes.remove( SVNStatusType.STATUS_CONFLICTED ) ) {
            status.append( symbols.getStatusSymbol( SVNStatusType.STATUS_CONFLICTED ) );
        }
        if ( localStatusTypes.remove( SVNStatusType.STATUS_DELETED ) ) {
            status.append( symbols.getStatusSymbol( SVNStatusType.STATUS_DELETED ) );
        }
        if ( localStatusTypes.remove( SVNStatusType.STATUS_IGNORED ) && entry.reportIgnored() ) {
            status.append( symbols.getStatusSymbol( SVNStatusType.STATUS_IGNORED ) );
        }
        if ( localStatusTypes.remove( SVNStatusType.STATUS_MODIFIED ) ) {
            status.append( symbols.getStatusSymbol( SVNStatusType.STATUS_MODIFIED ) );
        }
        if ( localStatusTypes.remove( SVNStatusType.STATUS_REPLACED ) ) {
            status.append( symbols.getStatusSymbol( SVNStatusType.STATUS_REPLACED ) );
        }
        if ( localStatusTypes.remove( SVNStatusType.STATUS_EXTERNAL ) ) {
            status.append( symbols.getStatusSymbol( SVNStatusType.STATUS_EXTERNAL ) );
        }
        if ( localStatusTypes.remove( SVNStatusType.STATUS_UNVERSIONED ) && entry.reportUnversioned() ) {
            status.append( symbols.getStatusSymbol( SVNStatusType.STATUS_UNVERSIONED ) );
        }
        if ( localStatusTypes.remove( SVNStatusType.STATUS_MISSING ) ) {
            status.append( symbols.getStatusSymbol( SVNStatusType.STATUS_MISSING ) );
        }
        if ( localStatusTypes.remove( SVNStatusType.STATUS_INCOMPLETE ) ) {
            status.append( symbols.getStatusSymbol( SVNStatusType.STATUS_INCOMPLETE ) );
        }
        if ( localStatusTypes.remove( SVNStatusType.STATUS_OBSTRUCTED ) ) {
            status.append( symbols.getStatusSymbol( SVNStatusType.STATUS_OBSTRUCTED ) );
        }
        if ( !localStatusTypes.isEmpty() ) {
            // future proofing
            logWarning( "the following svn statuses are not taken into account: " + localStatusTypes );
        }

        remoteStatusTypes.remove( SVNStatusType.STATUS_NONE );
        if ( !remoteStatusTypes.isEmpty() && entry.reportOutOfDate() ) {
            status.append( symbols.getOutOfDateSymbol() );
        }

        return status.toString();
    }


    private void logInfo( CharSequence message ) {
        if ( getLog().isInfoEnabled() ) {
            getLog().info( message );
        }
    }

    private void logWarning( CharSequence message ) {
        if ( getLog().isWarnEnabled() ) {
            getLog().warn( message );
        }
    }

    private void logDebug( CharSequence message ) {
        if ( getLog().isDebugEnabled() ) {
            getLog().debug( message );
        }
    }

    private void logDebugInfo( CharSequence message ) {
        if ( verbose ) {
            logInfo( message );
        } else {
            logDebug( message );
        }
    }


    private abstract class AbstractStatusHandler implements ISVNStatusHandler {

        private String repositoryRoot;

        private String repositoryPath;


        private long maximumRevisionNumber;

        private long minimumRevisionNumber;


        private long maximumCommittedRevisionNumber;


        private final Set<SVNStatusType> localStatusTypes;

        private final Set<SVNStatusType> remoteStatusTypes;


        private AbstractStatusHandler() {
            repositoryRoot = "";
            repositoryPath = "";

            maximumRevisionNumber = Long.MIN_VALUE;
            minimumRevisionNumber = Long.MAX_VALUE;

            maximumCommittedRevisionNumber = Long.MIN_VALUE;

            localStatusTypes = new HashSet<SVNStatusType>();
            remoteStatusTypes = new HashSet<SVNStatusType>();
        }


        protected void setRepositoryProperties( SVNEntry svnEntry ) {
            repositoryRoot = svnEntry == null ? "" : svnEntry.getRepositoryRoot();
            repositoryPath = svnEntry == null || svnEntry.getURL() == null ? "" : svnEntry.getURL().substring( repositoryRoot.length() );
            if ( repositoryPath.startsWith( "/" ) ) {
                repositoryPath = repositoryPath.substring( 1 );
            }
        }

        protected void appendStatus( SVNStatus status ) {
            long revisionNumber = status.getRevision().getNumber();
            if ( SVNRevision.isValidRevisionNumber( revisionNumber ) ) {
                maximumRevisionNumber = Math.max( maximumRevisionNumber, revisionNumber );
                if ( revisionNumber != 0 ) {
                    minimumRevisionNumber = Math.min( minimumRevisionNumber, revisionNumber );
                }
            }
            long committedRevisionNumber = status.getCommittedRevision().getNumber();
            if ( SVNRevision.isValidRevisionNumber( committedRevisionNumber ) ) {
                maximumCommittedRevisionNumber = Math.max( maximumCommittedRevisionNumber, committedRevisionNumber );
            }

            SVNStatusType contentsStatusType = status.getContentsStatus();
            localStatusTypes.add( contentsStatusType );

            SVNStatusType propertiesStatusType = status.getPropertiesStatus();
            localStatusTypes.add( propertiesStatusType );

            SVNStatusType remoteContentsStatusType = status.getRemoteContentsStatus();
            remoteStatusTypes.add( remoteContentsStatusType );
            SVNStatusType remotePropertiesStatusType = status.getRemotePropertiesStatus();
            remoteStatusTypes.add( remotePropertiesStatusType );

            boolean entryOutOfDate = !SVNStatusType.STATUS_NONE.equals( remoteContentsStatusType )
                    || !SVNStatusType.STATUS_NONE.equals( remotePropertiesStatusType );

            StringBuilder buffer = new StringBuilder();
            buffer.append( "  " );
            buffer.append( contentsStatusType.getCode() ).append( propertiesStatusType.getCode() );
            buffer.append( entryOutOfDate ? '*' : ' ' );
            buffer.append( ' ' ).append( String.format( "%6d", revisionNumber ) );
            buffer.append( ' ' ).append( String.format( "%6d", committedRevisionNumber ) );
            buffer.append( ' ' ).append( status.getFile() );
            logDebugInfo( buffer.toString() );
        }


        public String getRepositoryRoot() {
            return repositoryRoot;
        }

        public String getRepositoryPath() {
            return repositoryPath;
        }


        public long getMaximumRevisionNumber() {
            return maximumRevisionNumber == Long.MIN_VALUE ? -1L : maximumRevisionNumber;
        }

        public long getMinimumRevisionNumber() {
            return minimumRevisionNumber == Long.MAX_VALUE ? -1L : minimumRevisionNumber;
        }

        public boolean isMixedRevisions() {
            return getMaximumRevisionNumber() > 0L && getMinimumRevisionNumber() > 0L
                    && getMaximumRevisionNumber() != getMinimumRevisionNumber();
        }


        public long getMaximumCommittedRevisionNumber() {
            return maximumCommittedRevisionNumber == Long.MIN_VALUE ? -1L : maximumCommittedRevisionNumber;
        }


        public Set<SVNStatusType> getLocalStatusTypes() {
            return new HashSet<SVNStatusType>( localStatusTypes );
        }

        public Set<SVNStatusType> getRemoteStatusTypes() {
            return new HashSet<SVNStatusType>( remoteStatusTypes );
        }

    }

    private final class VersionedEntryStatusHandler extends AbstractStatusHandler {

        private VersionedEntryStatusHandler() {
        }

        public void handleStatus( SVNStatus status ) {
            appendStatus( status );
        }

    }

    private final class SpecialEntryStatusHandler extends AbstractStatusHandler {

        private final String entryName;

        private SpecialEntryStatusHandler( String entryName ) {
            if ( entryName == null ) {
                throw new IllegalArgumentException( "entryName is null" );
            }
            this.entryName = entryName;
        }

        public void handleStatus( SVNStatus status ) {
            SVNEntry svnEntry = status.getEntry();
            if ( svnEntry != null && entryName.equals( svnEntry.getName() ) ) {
                setRepositoryProperties( svnEntry );
                appendStatus( status );
            }
        }

    }

}
