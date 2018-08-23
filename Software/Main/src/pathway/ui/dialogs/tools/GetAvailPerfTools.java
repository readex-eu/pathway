package pathway.ui.dialogs.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListDialog;
import org.orm.PersistentException;
import pathway.data.persistence.Tool;




public class GetAvailPerfTools {
    public static String showDialog() throws PersistentException {
        final List<String> toolNames = queryTools();
        final String[] answer = new String[1];

        PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
            @Override
            public void run() {
                Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
                ListDialog dlg = new ListDialog(activeShell);
                dlg.setTitle("Perf Tool Selection");
                dlg.setMessage("Please choose the performance analysis tool:");
                dlg.setContentProvider(new ArrayContentProvider());
                dlg.setLabelProvider(new LabelProvider());
                dlg.setInput(toolNames.toArray());

                if( Window.OK == dlg.open() && dlg.getResult().length > 0 ) {
                    answer[0] = (String)dlg.getResult()[0];
                }
                else
                    answer[0] = null;
            }
        });
        return answer[0];

    }


    private static List<String> queryTools() throws PersistentException {
        List<Tool> tools = Tool.queryTool(null, null);
        List<String> toolNames = new ArrayList<String>();

        for( Tool tool: tools )
            toolNames.add(tool.getName() + "|" + tool.getVersion());
        Collections.sort(toolNames);

        return toolNames;
    }


    public static boolean verifyTool(String defaultValue) throws PersistentException {
        List<String> toolNames = queryTools();
        return toolNames.contains(defaultValue);
    }
}
