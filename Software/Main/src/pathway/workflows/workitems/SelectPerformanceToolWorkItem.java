package pathway.workflows.workitems;

import java.util.HashMap;
import java.util.Map;

import org.drools.runtime.process.WorkItem;
import org.orm.PersistentException;

import pathway.eclipse.ExecutionLog;
import pathway.eclipse.UIUtils;
import pathway.eclipse.WorkflowInstanceData;
import pathway.eclipse.remotetools.BatchSystemManager;
import pathway.ui.dialogs.tools.GetAvailPerfTools;
import pathway.workflows.WorkflowErrorException;
import pathway.workflows.WorkflowUserAbortException;

import pathway.data.persistence.Tool;




/** Displays a dialog that lets the user select a performance analysis tool. */
public class SelectPerformanceToolWorkItem extends PathwayWorkItem {
    @Override
    public Map<String, Object> execute(WorkItem workItem, WorkflowInstanceData data) {
    	
        String tool;
        try {
            tool = selectTool(data.defaultTool);
        }
        catch( PersistentException ex ) {
            ExecutionLog.get().writeErr("Unable to query configured tools.");
            throw new WorkflowErrorException();
        }

        Map<String, Object> results = new HashMap<String, Object>();
        results.put("PerfTool", tool);
        ExecutionLog.get().writeLog("Selected performance tool: " + tool);
        return results;
    }


    private static String selectTool(String defaultValue) throws PersistentException {
        if( defaultValue == null ) {
            String tool = GetAvailPerfTools.showDialog();
            if( tool == null )
                throw new WorkflowUserAbortException();

            return tool;
        }

        if( !GetAvailPerfTools.verifyTool(defaultValue) ) {
            ExecutionLog.get().writeErr("The selected performance tool is not configured.");
            UIUtils.showErrorMessage("The selected performance tool is not configured.");
            throw new WorkflowErrorException();
        }

        return defaultValue;
    }
}
