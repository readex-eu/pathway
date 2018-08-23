package pathway.workflows.workitems;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.drools.runtime.process.WorkItem;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.orm.PersistentException;
import pathway.data.persistence.HPCSystem;
import pathway.eclipse.ExecutionLog;
import pathway.eclipse.UIUtils;
import pathway.eclipse.WorkflowInstanceData;
import pathway.eclipse.remotetools.BatchSystemManager;
import pathway.eclipse.remotetools.ConnectionManager;
import pathway.workflows.WorkflowErrorException;
import pathway.workflows.WorkflowUserAbortException;




public class CreateBatchSystemManagerWorkItem extends PathwayWorkItem {
    @Override
    public Map<String, Object> execute(WorkItem workItem, WorkflowInstanceData data) {
        ExecutionLog.get().writeLog("Creating batch system manager...");
        final Map<String, Object> results = new HashMap<String, Object>();
        final String hpcSystem = getParameter(workItem, "HPCSystem");

        ConnectionManager mgr;
        try {
            mgr = createConnectionManager(hpcSystem);
        }
        catch( WorkflowUserAbortException ex ) {
            throw ex;
        }
        catch( Exception ex ) {
            ex.printStackTrace();
            String message = "Unable to create the connection manager: " + ex.getMessage();
            ExecutionLog.get().writeErr(message);
            UIUtils.showErrorMessage(message);
            throw new WorkflowErrorException();
        }

        try {
            BatchSystemManager batchSysMgr = new BatchSystemManager(mgr, data.monitor);
            results.put("BatchSysManager", batchSysMgr);
            data.setBatchManager(batchSysMgr);
        }
        catch( Exception ex ) {
            ex.printStackTrace();
            String message = "Unable to create the batch system manager: " + ex.getMessage();
            ExecutionLog.get().writeErr(message);
            UIUtils.showErrorMessage(message);
            throw new WorkflowErrorException();
        }

        ExecutionLog.get().writeLog("Batch system manager created.");
        return results;
    }


    @NonNullByDefault
    private static ConnectionManager createConnectionManager(String hpcSystem) throws PersistentException, IOException {
        HPCSystem temp = HPCSystem.loadHPCSystemByQuery(HPCSystem.PROP_NAME + "= '" + hpcSystem + "'", null);
        if( temp == null )
            throw new RuntimeException("The specified HPC system configuration was not found in the database: " + hpcSystem);

        return new ConnectionManager(temp);
    }
}
