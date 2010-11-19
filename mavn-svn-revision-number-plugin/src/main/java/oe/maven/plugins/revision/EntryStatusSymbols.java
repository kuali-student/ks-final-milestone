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

import org.tmatesoft.svn.core.wc.SVNStatusType;

/** A mapping from the status type to the symbol that represents it. */
class EntryStatusSymbols {

    /** The default mapping that is consistent with the {@literal svn status} command output. */
    public static final EntryStatusSymbols DEFAULT = new EntryStatusSymbols();

    /** The mapping that uses lower-case letters instead of the characters not allowed in the file names. */
    public static final EntryStatusSymbols SPECIAL = new EntryStatusSymbols() {
        @Override
        public char getStatusSymbol( SVNStatusType svnStatusType ) {
            if ( SVNStatusType.STATUS_UNVERSIONED.equals( svnStatusType ) ) {
                return 'u';
            } else if ( SVNStatusType.STATUS_MISSING.equals( svnStatusType ) ) {
                return 'm';
            } else if ( SVNStatusType.STATUS_INCOMPLETE.equals( svnStatusType ) ) {
                return 'i';
            } else if ( SVNStatusType.STATUS_OBSTRUCTED.equals( svnStatusType ) ) {
                return 'o';
            } else {
                return super.getStatusSymbol( svnStatusType );
            }
        }

        @Override
        public char getOutOfDateSymbol() {
            return 'd';
        }
    };


    private EntryStatusSymbols() {
    }


    public char getStatusSymbol( SVNStatusType svnStatusType ) {
        return svnStatusType.getCode();
    }

    public char getOutOfDateSymbol() {
        return '*';
    }

}
