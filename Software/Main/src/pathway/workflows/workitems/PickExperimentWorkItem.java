package pathway.workflows.workitems;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.runtime.process.WorkItem;

import pathway.data.persistence.Experiment;
import pathway.eclipse.ExecutionLog;
import pathway.eclipse.WorkflowInstanceData;
import pathway.workflows.WorkflowErrorException;


public class PickExperimentWorkItem extends PathwayWorkItem {
    
	//Pick4RDD implementation
	
	@Override
    public Map<String, Object> execute(WorkItem workItem, WorkflowInstanceData data)
    {
    	//ExecutionLog.get().writeLog("Pick Experiment... starting");
    	
        Map<String, Object> results = new HashMap<String, Object>();
        List<Experiment> experiments = (List<Experiment>)workItem.getParameter("Experiments");
        
        if(!experiments.isEmpty())
        {
        	for(Experiment e : experiments)
        		ExecutionLog.get().writeLog("Pick Experiment inputs: " + e.getInputDataFileName());
        	Experiment experiment = experiments.remove(0);
        	ExecutionLog.get().writeLog("Picked Experiment with input: " + experiment.getInputDataFileName());
 
        	results.put("Experiments", experiments);
        	results.put("Experiment", experiment); 
        	
        	
        	return results;
        }
        else
        {
        	ExecutionLog.get().writeErr("PickExperiment: Cannot pick from empty list of Expeirments.");
        	throw new WorkflowErrorException();
        }
    } 
}