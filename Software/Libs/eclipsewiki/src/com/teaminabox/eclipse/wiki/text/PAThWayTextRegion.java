package com.teaminabox.eclipse.wiki.text;

import org.eclipse.jface.text.rules.IToken;

import com.teaminabox.eclipse.wiki.WikiConstants;
import com.teaminabox.eclipse.wiki.editors.ColourManager;

/**
 * A region of text representing a hyperlink.
 */
public class PAThWayTextRegion extends TextRegion {

	public PAThWayTextRegion(String text) {
		super(text);
	}

	@Override
	public IToken getToken(ColourManager colourManager) {
		return getToken(WikiConstants.PLUGIN_RESOURCE, colourManager);
	}

	@Override
	public <T> T accept(TextRegionVisitor<T> textRegionVisitor) {
		return textRegionVisitor.visit(this);
	}

	/**
	 * This is a special Wiki region of text.
	 * 
	 * @return <code>true</code>
	 */
	@Override
	public boolean isLink() {
		return true;
	}

}