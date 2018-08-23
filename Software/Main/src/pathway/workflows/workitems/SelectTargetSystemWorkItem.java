package pathway.workflows.workitems;

import java.util.HashMap;
import java.util.Map;
import org.drools.runtime.process.WorkItem;
import pathway.eclipse.ExecutionLog;
import pathway.eclipse.WorkflowInstanceData;
import pathway.ui.dialogs.GetAvailHPCSystems;
import pathway.workflows.WorkflowUserAbortException;




public class SelectTargetSystemWorkItem extends PathwayWorkItem {
    @Override
    public Map<String, Object> execute(WorkItem workItem, WorkflowInstanceData data) {
        String hpcSystem = data.defaultSystem == null ? GetAvailHPCSystems.showDialog() : data.defaultSystem;
        if( hpcSystem == null )
            throw new WorkflowUserAbortException();

        Map<String, Object> results = new HashMap<String, Object>();
        results.put("HPCSystem", hpcSystem);
        ExecutionLog.get().writeLog("Selected HPC system: " + hpcSystem);
        return results;
    }
}
