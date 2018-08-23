package pathway.eclipse.views;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.PartInitException;
import pathway.PAThWayPlugin;
import pathway.eclipse.UIUtils;




/** A composite that displays a hint that no experiment is currently selected. */
class NoExperimentComposite extends Composite {
    @NonNullByDefault
    public NoExperimentComposite(Composite parent, int style) {
        super(parent, style);

        setLayout(new GridLayout());

        Composite composite = new Composite(this, SWT.NONE);
        composite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
        composite.setLayout(new GridLayout());

        Label label = new Label(composite, SWT.NONE);
        label.setText("No experiment is currently shown. Select an experiment in the experiment browser.");
        label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));

        Link link = new Link(composite, SWT.NONE);
        link.setText("<a>Open experiment browser</a>");
        link.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
        link.addListener(SWT.Selection, new LinkListener());

    }


    private static class LinkListener implements Listener {
        @Override
        public void handleEvent(Event event) {
            try {
                UIUtils.activateView(PAThWayPlugin.VIEW_EXPERIMENT_BROWSER);
            }
            catch( PartInitException ex ) {
                ex.printStackTrace();
                UIUtils.showErrorMessage("Unable to open the experiment browser.", ex);
            }
        }
    }
}
