package com.teaminabox.eclipse.wiki.text;

import com.teaminabox.eclipse.wiki.WikiConstants;
import com.teaminabox.eclipse.wiki.editors.WikiDocumentContext;

public class PAThWayMatcher extends AbstractTextRegionMatcher {
	private String	fPrefix;

	public PAThWayMatcher() {
		fPrefix = WikiConstants.PATHWAY_PREFIX;
	}

	public TextRegion createTextRegion(String text, WikiDocumentContext context) {
		if (text.startsWith(fPrefix)) {
			return new PAThWayTextRegion(new String(text.substring(0, matchLength(text, context))));
		}
		return null;
	}

	protected int matchLength(String candidate, WikiDocumentContext context) {
		String text = candidate;
		// get rid of the next link if there is one.
		if (text.indexOf(fPrefix, 1) > 0) {
			text = new String(text.substring(0, text.indexOf(fPrefix, 1)));
		}
		// now try to find the longest match
		String subCommand = new String(text.substring(fPrefix.length()));
		if (subCommand.toLowerCase().startsWith("note:"))
			return fPrefix.length() + subCommand.length();
		else if (subCommand.toLowerCase().startsWith("exp:"))
			return fPrefix.length() + subCommand.length();

		//		for (int i = text.length(); i >= fPrefix.length(); i--) {
		//			String section = new String(text.substring(fPrefix.length(), i));
		//			if (section.length() > 0 && section.charAt(section.length() - 1) == WikiConstants.LINE_NUMBER_SEPARATOR) {
		//				continue;
		//			}
		//			File resource = findResourceFromPath(context, section);
		//			if (resource != null && resource.exists()) {
		//				// is there a line number too?
		//				if (i < text.length() && WikiConstants.LINE_NUMBER_SEPARATOR == text.charAt(i)) {
		//					// add 1 for the colon before the line number
		//					return fPrefix.length() + section.length() + getLineNumberLength(text, i) + 1;
		//				}
		//				return i;
		//			}
		//	}

		return fPrefix.length();
	}

	@Override
	protected boolean accepts(char c, boolean firstCharacter) {
		if (firstCharacter) {
			return c == fPrefix.charAt(0);
		}
		return true;
	}
}