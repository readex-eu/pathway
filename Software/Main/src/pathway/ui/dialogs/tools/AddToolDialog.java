package pathway.ui.dialogs.tools;

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




/** Represents a dialog that adds a new empty tool to the list of performance tools. */
public class AddToolDialog extends Dialog {
    /** Initializes a new instance. */
    @NonNullByDefault
    public AddToolDialog(Shell parentShell) {
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
        versionLabel.setText("Version:");
        versionText = new Text(container, SWT.BORDER);
        versionText.setLayoutData(fillData);

        return container;
    }


    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Add new performance tool");
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
        toolName = nameText.getText().trim();
        toolVersion = versionText.getText().trim();
        if( toolName.isEmpty() || toolVersion.isEmpty() ) {
            UIUtils.showErrorMessage("Please provide a name and version description for the tool.");
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


    public String toolName;
    public String toolVersion;

    private Text nameText;
    private Text versionText;
}
