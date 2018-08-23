package com.teaminabox.eclipse.wiki.renderer;

import com.teaminabox.eclipse.wiki.WikiConstants;
import com.teaminabox.eclipse.wiki.editors.WikiDocumentContext;
import com.teaminabox.eclipse.wiki.text.EclipseResourceTextRegion;
import com.teaminabox.eclipse.wiki.text.JavaTypeTextRegion;
import com.teaminabox.eclipse.wiki.text.PAThWayTextRegion;
import com.teaminabox.eclipse.wiki.text.PluginResourceTextRegion;
import com.teaminabox.eclipse.wiki.text.UrlTextRegion;
import com.teaminabox.eclipse.wiki.text.WikiLinkTextRegion;
import com.teaminabox.eclipse.wiki.text.WikiUrlTextRegion;

public class IdeLinkMaker extends LinkMaker {

	public IdeLinkMaker(WikiDocumentContext context) {
		setContext(context);
	}

	@Override
	public String make(WikiLinkTextRegion wikiNameTextRegion) {
		if (getContext().hasWikiSibling(wikiNameTextRegion)) {
			return getLink(WikiConstants.WIKI_HREF + wikiNameTextRegion.getWikiDocumentName(), wikiNameTextRegion.getDisplayText());
		}
		return wikiNameTextRegion.getText() + getLink(WikiConstants.WIKI_HREF + wikiNameTextRegion.getWikiDocumentName(), AbstractContentRenderer.NEW_WIKIDOC_HREF);
	}

	@Override
	public String make(WikiUrlTextRegion wikiUrlTextRegion) {
		String link;
		if (wikiUrlTextRegion.getLink().startsWith(WikiConstants.ECLIPSE_PREFIX)) {
			link = WikiConstants.WIKI_HREF + wikiUrlTextRegion.getLink();
		} else if (wikiUrlTextRegion.getLink().startsWith(WikiConstants.PLUGIN_PREFIX)) {
			link = WikiConstants.WIKI_HREF + wikiUrlTextRegion.getLink();
		} else {
			link = wikiUrlTextRegion.getLink();
		}
		return getLink(link, wikiUrlTextRegion.getText());
	}

	@Override
	public String make(EclipseResourceTextRegion eclipseResourceTextRegion) {
		return getLink(WikiConstants.WIKI_HREF + eclipseResourceTextRegion.getText(), eclipseResourceTextRegion.getText());
	}

	@Override
	public String make(PluginResourceTextRegion pluginResourceTextRegion) {
		return getLink(WikiConstants.WIKI_HREF + pluginResourceTextRegion.getText(), pluginResourceTextRegion.getText());
	}

	@Override
	public String make(JavaTypeTextRegion region) {
		String url = WikiConstants.WIKI_HREF + WikiConstants.JAVA_LINK_PREFIX + region.getType().getFullyQualifiedName();
		return getLink(url, getTextForJavaType(region));
	}

	@Override
	public String make(UrlTextRegion urlTextRegion) {
		return getLink(urlTextRegion.getText(), urlTextRegion.getText());
	}

	@Override
	public String make(PAThWayTextRegion pathwayTextRegion) {
		return getLink(WikiConstants.WIKI_HREF + pathwayTextRegion.getText(), pathwayTextRegion.getText());
	}
}
