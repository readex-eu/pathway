package pathway.workflows.workitems;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.runtime.process.WorkItem;

import pathway.data.persistence.Experiment;
import pathway.eclipse.ExecutionLog;
import pathway.eclipse.WorkflowInstanceData;
import pathway.workflows.WorkflowErrorException;


public class Pick4SAFWorkItem extends PathwayWorkItem {
    @Override
    public Map<String, Object> execute(WorkItem workItem, WorkflowInstanceData data)
    {
    	
        Map<String, Object> results = new HashMap<String, Object>();
        List<Experiment> experiments = (List<Experiment>)workItem.getParameter("Experiments");
        if(!experiments.isEmpty())
        {
        	
        	Experiment experiment=experiments.get(0);
        	
        	ExecutionLog.get().writeLog("Picked Experiment with input: " + experiment.getInputDataFileName());
        	
        	results.put("Experiments", experiments);
        	results.put("Experiment", experiment); 

        	return results;
        }
        else
        {
        	ExecutionLog.get().writeErr("PickExperiment: Cannot pick from empty list of Experiments.");
        	throw new WorkflowErrorException();
        }
    } 
}