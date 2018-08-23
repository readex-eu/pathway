package pathway.ui.dialogs.applications;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import pathway.eclipse.UIUtils;




/** Represents a dialog that adds a new empty application configuration to the list of applications. */
public class AddApplicationDialog extends Dialog {
    /** Initializes a new instance. */
    @NonNullByDefault
    public AddApplicationDialog(Shell parentShell) {
        super(parentShell);
    }


    @Override
    protected Control createDialogArea(Composite parent) {
        // add the controls
        final Composite container = (Composite)super.createDialogArea(parent);
        container.setLayout(createGridLayout());
        final GridData fillData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);

        // spacer
        new Label(container, SWT.NONE).setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));

        Label nameLabel = new Label(container, SWT.NONE);
        nameLabel.setText("Name:");
        nameText = new Text(container, SWT.BORDER);
        nameText.setLayoutData(fillData);

        Label versionLabel = new Label(container, SWT.NONE);
        versionLabel.setText("Configuration:");
        configText = new Text(container, SWT.BORDER);
        configText.setLayoutData(fillData);

        return container;
    }


    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Add new application");
    }


    @Override
    protected boolean isResizable() {
        return true;
    }


    @Override
    protected Point getInitialSize() {
        return new Point(400, 200);
    }


    @Override
    protected void okPressed() {
        appName = nameText.getText().trim();
        appConfig = configText.getText().trim();
        if( appConfig.isEmpty() )
            appConfig = "default";

        if( appName.isEmpty() || appConfig.isEmpty() ) {
            UIUtils.showErrorMessage("Please provide a name for the application.");
            return;
        }

        super.okPressed();
    }


    @NonNullByDefault
    private static GridLayout createGridLayout() {
        GridLayout layout = new GridLayout();
        layout.marginHeight = 5;
        layout.marginWidth = 5;
        layout.numColumns = 2;
        layout.horizontalSpacing = 40;
        layout.verticalSpacing = 10;

        return layout;
    }


    public String appName;
    public String appConfig;

    private Text nameText;
    private Text configText;
}
