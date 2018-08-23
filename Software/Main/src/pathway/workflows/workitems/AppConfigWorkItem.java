package pathway.workflows.workitems;

import java.util.HashMap;
import java.util.Map;
import org.drools.runtime.process.WorkItem;
import pathway.eclipse.ExecutionLog;
import pathway.eclipse.WorkflowInstanceData;
import pathway.ui.dialogs.GetAvailApps;
import pathway.workflows.WorkflowUserAbortException;




/** Asks the user to pick an application configuration. */
public class AppConfigWorkItem extends PathwayWorkItem {
    @Override
    public Map<String, Object> execute(WorkItem workItem, WorkflowInstanceData data) {
        String applicationName = data.defaultApp == null ? GetAvailApps.showDialog() : data.defaultApp;
        if( applicationName == null )
            throw new WorkflowUserAbortException();

        Map<String, Object> results = new HashMap<String, Object>();
        results.put("Application", applicationName);
        ExecutionLog.get().writeLog("Selected application: " + applicationName);
        
        return results;
    }
}
