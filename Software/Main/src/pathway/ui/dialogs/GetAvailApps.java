package pathway.ui.dialogs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListDialog;
import org.orm.PersistentException;
import pathway.data.persistence.Application;




public class GetAvailApps {
    public static String showDialog() {
        List<Application> applications = new ArrayList<Application>();
        try {
            applications = Application.queryApplication(null, null);
        }
        catch( PersistentException ex ) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "There was an error while querying the available applications from the internal database:\n" + ex.getLocalizedMessage(),
                    "Error querying the internal database", JOptionPane.ERROR_MESSAGE);
        }

        final List<String> applicationNames = (List<String>)CollectionUtils.collect(applications, new Transformer() {
            @Override
            public Object transform(Object o) {
                Application app = ((Application)o);
                return app.getName() + (app.getConfig().equalsIgnoreCase("default") ? "" : "|" + app.getConfig());
            }
        });
        Collections.sort(applicationNames);

        final String[] answer = new String[1];
        PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
            @Override
            public void run() {
                Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
                ListDialog dlg = new ListDialog(activeShell);
                dlg.setTitle("Application Selection");
                dlg.setMessage("Please choose an application to analyse:");
                dlg.setContentProvider(new ArrayContentProvider());
                dlg.setLabelProvider(new LabelProvider());
                dlg.setInput(applicationNames.toArray());

                if( Window.OK == dlg.open() && dlg.getResult().length > 0 )
                    answer[0] = (String)dlg.getResult()[0];
                else
                    answer[0] = null;
            }
        });
        return answer[0];
    }
}
