package com.teaminabox.eclipse.wiki.text;

import static org.apache.commons.lang3.StringUtils.join;

import com.teaminabox.eclipse.wiki.WikiConstants;

public class UrlMatcher extends PatternMatcher {

	private static final String	URL_REGEX	= "(" + join(WikiConstants.URL_PREFIXES, '|') + "):(//)?([-_\\.!~*';/?:@#&=+$%,\\p{Alnum}])+";

	public UrlMatcher() {
		super(UrlMatcher.URL_REGEX);
	}

	@Override
	protected boolean accepts(char c, boolean firstCharacter) {
		if (firstCharacter) {
			for (String element : WikiConstants.URL_PREFIXES) {
				if (c == element.charAt(0)) {
					return true;
				}
			}
		}
		return c != ' ';
	}

	@Override
	protected TextRegion createTextRegion(String text) {
		return new UrlTextRegion(text);
	}

}