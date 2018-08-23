package pathway.ui.dialogs;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class BindingPanelTab extends JPanel {
	private static final long serialVersionUID = -3009954202645524760L;

	private Boolean tabEnabled = true;
	private Boolean tabVisible= true;
	private int tabIndex = 0;

	public BindingPanelTab(int tabIndex) {
		super();
		this.tabIndex = tabIndex;
	}

	public void setEnabledTab(Boolean enabled) {
		Boolean oldValue = tabEnabled;
		tabEnabled = enabled;
		super.setEnabled(enabled);
		JTabbedPane parent = (JTabbedPane) getParent();
		parent.setEnabledAt(tabIndex, enabled);
		firePropertyChange("enabledTab", oldValue, enabled);
	}

	public void setVisibleTab(Boolean visible) {
		Boolean oldValue = tabVisible;
		tabVisible = visible;
		super.setVisible(visible);
		JTabbedPane parent = (JTabbedPane) getParent();
		parent.setEnabledAt(tabIndex, visible);
		firePropertyChange("visibleTab", oldValue, visible);
	}
}
