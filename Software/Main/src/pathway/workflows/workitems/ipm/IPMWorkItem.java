package pathway.workflows.workitems.ipm;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.drools.runtime.process.WorkItem;
import org.eclipse.jdt.annotation.NonNullByDefault;
import pathway.data.DataUtils;
import pathway.data.persistence.Experiment;
import pathway.eclipse.ExecutionLog;
import pathway.eclipse.UIUtils;
import pathway.eclipse.WorkflowInstanceData;
import pathway.eclipse.remotetools.BatchSystemManager;
import pathway.eclipse.remotetools.FileUtils;
import pathway.workflows.WorkflowErrorException;
import pathway.workflows.workitems.Helper;
import pathway.workflows.workitems.PathwayWorkItem;




public abstract class IPMWorkItem extends PathwayWorkItem {
    @Override
    public Map<String, Object> execute(WorkItem workItem, WorkflowInstanceData data) {
        final BatchSystemManager mgr = getParameter(workItem, "BatchSysManager");
        final Collection<Experiment> experiments = getParameter(workItem, "Experiments");
        final String resultUri = getParameter(workItem, "ipmFile");
        final ArrayList<File> documents = new ArrayList<File>();
        final Map<String, Float> properties = new TreeMap<String, Float>();

        try {
            for( Experiment experiment: experiments ) {
                experiment.setResultsURI(Helper.fillPlaceholders(resultUri, experiment));
                DataUtils.StoreExperiment(experiment);

                File tempFile = File.createTempFile("pathway_doc.", ".xml");
                FileUtils.copyResourceToLocal(mgr.getConnectionManager(), experiment.getResultsURI(), tempFile.getAbsolutePath(), true, data.monitor);
                documents.add(tempFile);
            }

            calculate(documents, properties);
        }
        catch( Exception ex ) {
            ExecutionLog.get().writeErr(ex);
            UIUtils.showErrorMessage("Unable to evaluate the IPM log files!", ex);
            throw new WorkflowErrorException();
        }

        for( File file: documents )
            file.delete();

        Map<String, Object> result = new TreeMap<String, Object>();
        result.put("Properties", properties);
        return result;
    }


    /** Evaluates the IPM files and returns performance properties. */
    @NonNullByDefault
    protected abstract void calculate(List<File> ipmFiles, Map<String, Float> results);
}
