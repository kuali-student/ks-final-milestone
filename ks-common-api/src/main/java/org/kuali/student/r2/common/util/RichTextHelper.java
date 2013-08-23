
/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.common.util;

import org.kuali.student.r2.common.dto.RichTextInfo;

/**
 * Utility to convert text between plain and formatted
 * @author nwright
 */
public class RichTextHelper {

    public static RichTextInfo buildRichTextInfo(String plain, String formatted) {
        if (plain == null) {
            return null;
        }
        RichTextInfo info = new RichTextInfo();
        info.setFormatted(formatted);
        info.setPlain(plain);
        return info;
    }

    public RichTextInfo toRichTextInfo(String plain, String formatted) {
        if (plain == null) {
            return null;
        }
        RichTextInfo info = new RichTextInfo();
        info.setFormatted(formatted);
        info.setPlain(plain);
        return info;
    }

    public RichTextInfo fromFormatted(String plain) {
        return toRichTextInfo(plain, formatted2Plain(plain));
    }

    public RichTextInfo fromPlain(String plain) {
        return toRichTextInfo(plain, plain2Formatted(plain));
    }

    public String formatted2Plain(String forematted) {
        if (forematted == null) {
            return null;
        }
        // TODO: actually implement a real conversion
        return forematted.replaceAll("<br>", "\n");
    }

    public String plain2Formatted(String plain) {
        if (plain == null) {
            return null;
        }
        // TODO: actually implement a real conversion
        return plain.replaceAll("\n", "<br>");
    }
}
