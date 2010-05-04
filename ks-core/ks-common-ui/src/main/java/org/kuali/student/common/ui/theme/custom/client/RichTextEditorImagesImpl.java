/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.ui.theme.custom.client;

import org.kuali.student.common.ui.client.theme.RichTextEditorImages;
import org.kuali.student.common.ui.client.widgets.KSImage;

public class RichTextEditorImagesImpl implements RichTextEditorImages{

	@Override
	public KSImage bold() {
		
		return new KSImage(KSClientBundle.INSTANCE.bold());
	}

	@Override
	public KSImage createLink() {
		
		return new KSImage(KSClientBundle.INSTANCE.createLink());
	}

	@Override
	public KSImage hr() {
		
		return new KSImage(KSClientBundle.INSTANCE.hr());
	}

	@Override
	public KSImage indent() {
		
		return new KSImage(KSClientBundle.INSTANCE.indent());
	}

	@Override
	public KSImage insertImage() {
		
		return new KSImage(KSClientBundle.INSTANCE.insertImage());
	}

	@Override
	public KSImage italic() {
		
		return new KSImage(KSClientBundle.INSTANCE.italic());
	}

	@Override
	public KSImage justifyCenter() {
		
		return new KSImage(KSClientBundle.INSTANCE.justifyCenter());
	}

	@Override
	public KSImage justifyLeft() {
		
		return new KSImage(KSClientBundle.INSTANCE.justifyLeft());
	}

	@Override
	public KSImage justifyRight() {
		
		return new KSImage(KSClientBundle.INSTANCE.justifyRight());
	}

	@Override
	public KSImage ol() {
		
		return new KSImage(KSClientBundle.INSTANCE.ol());
	}

	@Override
	public KSImage outdent() {
		
		return new KSImage(KSClientBundle.INSTANCE.outdent());
	}

	@Override
	public KSImage popout() {
		
		return new KSImage(KSClientBundle.INSTANCE.popout());
	}

	@Override
	public KSImage removeFormat() {
		
		return new KSImage(KSClientBundle.INSTANCE.removeFormat());
	}

	@Override
	public KSImage removeLink() {
		
		return new KSImage(KSClientBundle.INSTANCE.removeLink());
	}

	@Override
	public KSImage strikeThrough() {
		
		return new KSImage(KSClientBundle.INSTANCE.strikeThrough());
	}

	@Override
	public KSImage subscript() {
		
		return new KSImage(KSClientBundle.INSTANCE.subscript());
	}

	@Override
	public KSImage superscript() {
		
		return new KSImage(KSClientBundle.INSTANCE.superscript());
	}

	@Override
	public KSImage ul() {
		
		return new KSImage(KSClientBundle.INSTANCE.ul());
	}

	@Override
	public KSImage underline() {
		
		return new KSImage(KSClientBundle.INSTANCE.underline());
	}

}
