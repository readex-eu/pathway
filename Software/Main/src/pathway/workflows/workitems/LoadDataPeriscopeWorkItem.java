package pathway.workflows.workitems;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.drools.runtime.process.WorkItem;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.orm.PersistentException;
import pathway.data.DataUtils;
import pathway.data.persistence.Experiment;
import pathway.data.persistence.PscProperty;
import pathway.eclipse.ExecutionLog;
import pathway.eclipse.UIUtils;
import pathway.eclipse.WorkflowInstanceData;
import pathway.eclipse.remotetools.BatchSystemManager;
import pathway.utils.Resources;
import pathway.utils.periscope.loaders.PropertyXMLFileProviderSAX;




/** Fetches the Periscope properties from the remote properties file and stores them in the database. */
public class LoadDataPeriscopeWorkItem extends PathwayWorkItem {
    @Override
    public Map<String, Object> execute(WorkItem workItem, WorkflowInstanceData data) {
        Experiment experiment = (Experiment)workItem.getParameter("Experiment");
        Collection<Experiment> experiments = (Collection<Experiment>)workItem.getParameter("Experiments");
        if( experiment == null && experiments == null )
            throw new IllegalArgumentException("Either an experiment or an experiment collection must be provided.");

        BatchSystemManager batchSysManager = getParameter(workItem, "BatchSysManager");
        String resultUri = getParameter(workItem, "ResultURI");

        if( experiment != null )
            impl(batchSysManager, experiment, resultUri);
        if( experiments != null )
            impl(batchSysManager, experiments, resultUri);

        return null;
    }


    @NonNullByDefault
    public void impl(BatchSystemManager batchSysManager, Collection<Experiment> experiments, String resultUri) {
        for( Experiment experiment: experiments )
            impl(batchSysManager, experiment, resultUri);
    }


    @NonNullByDefault
    public void impl(BatchSystemManager batchSysManager, Experiment experiment, String resultUri) {
        resultUri = Helper.fillPlaceholders(resultUri, experiment);

        try {
            PropertyXMLFileProviderSAX fileReader = openFile(batchSysManager, resultUri);
            Set<PscProperty> properties = fileReader.parse().getPscProperty();
            for( PscProperty property: properties )
                property.setExperiment(experiment);

            experiment.setPscProperty(properties);
            experiment.setResultsURI(resultUri);
            DataUtils.StoreExperiment(experiment);
        }
        catch( IOException ex ) {
            ExecutionLog.get().writeErr(ex);
            UIUtils.showErrorMessage("There was an error reading the properties file. Workflow will continue without properties read.", ex);
        }
        catch( PersistentException ex ) {
            throw new RuntimeException(ex);
        }
    }


    @NonNullByDefault
    public PropertyXMLFileProviderSAX openFile(BatchSystemManager batchSysManager, String path) throws IOException {
        InputStream input = null;
        try {
            input = batchSysManager.getConnectionManager().getFileManager().getResource(path).openInputStream(EFS.NONE, null);
            return new PropertyXMLFileProviderSAX(input);
        }
        catch( CoreException ex ) {
            throw new RuntimeException(ex);
        }
        finally {
            Resources.close(input);
        }
    }
}
