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

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;

/** Describes a file or a directory to be inspected. */
public class Entry {

    /** The entry local path. */
    private File path;

    /** The prefix for entry properties. */
    private String prefix;

    /** Specifies the depth of the entries whose status information should be collected. */
    private String depth = "infinity";

    /** Specifies whether to report items that are not under version control. */
    private boolean reportUnversioned = true;

    /** Specifies whether to report items that were set to be ignored. */
    private boolean reportIgnored = false;

    /** Specifies whether to check the remote repository and report local out-of-date items. */
    private boolean reportOutOfDate = false;


    /** Creates a new {@code Entry} object with the default parameters. */
    public Entry() {
    }

    /**
     * Creates a new {@code Entry} object with the specified path and properties prefix.
     *
     * @param path the local path
     * @param prefix the properties prefix
     */
    Entry( File path, String prefix ) {
        this.path = path;
        this.prefix = prefix;
    }


    /**
     * Returns the entry local path.
     *
     * @return the local path
     */
    public File getPath() {
        return path;
    }

    /**
     * Sets the entry local path.
     *
     * @param path the new local path
     */
    void setPath( File path ) {
        this.path = path;
    }

    /**
     * Returns the prefix for entry properties.
     *
     * @return the properties prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Sets the the prefix for entry properties.
     *
     * @param prefix the new properties prefix
     */
    void setPrefix( String prefix ) {
        this.prefix = prefix;
    }

    /**
     * Specifies the depth of the entries whose status information should be collected.
     *
     * @return the depth of operation
     */
    public String getDepth() {
        return depth;
    }

    /**
     * Specifies whether to report items that are not under version control.
     *
     * @return {@code true} if unversioned items should be reported
     */
    public boolean reportUnversioned() {
        return reportUnversioned;
    }

    /**
     * Specifies whether to report items that were set to be ignored.
     *
     * @return {@code true} if ignored items should be reported
     */
    public boolean reportIgnored() {
        return reportIgnored;
    }

    /**
     * Specifies whether to check the remote repository and report local out-of-date items.
     *
     * @return {@code true} if out-of-date items should be reported
     */
    public boolean reportOutOfDate() {
        return reportOutOfDate;
    }


    /**
     * Validates the entry configuration.
     *
     * @throws MojoExecutionException if the entry configuration is invalid
     */
    public void validate() throws MojoExecutionException {
        if ( path == null ) {
            throw new MojoExecutionException( "entry path is not specified" );
        }
        if ( prefix == null ) {
            throw new MojoExecutionException( "entry prefix is not specified" );
        }
        if ( depth == null ) {
            throw new MojoExecutionException( "entry depth is not specified" );
        }
        if ( !"empty".equals( depth ) && !"files".equals( depth ) && !"immediates".equals( depth ) && !"infinity".equals( depth ) ) {
            throw new MojoExecutionException( "entry depth is not one of {empty, files, immediates, infinity}" );
        }
    }

}
