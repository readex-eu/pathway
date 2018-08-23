package pathway.workflows.workitems;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.runtime.process.WorkItem;

import pathway.data.persistence.Experiment;
import pathway.eclipse.ExecutionLog;
import pathway.eclipse.WorkflowInstanceData;
import pathway.workflows.WorkflowErrorException;


public class Pick4PTFWorkItem extends PathwayWorkItem {
    
	@Override
    public Map<String, Object> execute(WorkItem workItem, WorkflowInstanceData data)
    {
    	
        Map<String, Object> results = new HashMap<String, Object>();
        List<Experiment> experiments = (List<Experiment>)workItem.getParameter("Experiments");
        List<Experiment> experimentsTM = (List<Experiment>)workItem.getParameter("ExperimentsTM");
        
        if (experimentsTM == null) 
        	 experimentsTM = new ArrayList<Experiment>();
        	
        if(!experiments.isEmpty())
        {
        	for(Experiment e : experiments)
        		ExecutionLog.get().writeLog("Pick Experiment inputs: " + e.getInputDataFileName());
        	Experiment experiment = experiments.remove(0);
        	experimentsTM.add(experiment);
        	
        	ExecutionLog.get().writeLog("Picked Experiment with input: " + experiment.getInputDataFileName());
 
        	results.put("Experiments", experiments);
        	results.put("Experiment", experiment); 
        	results.put("ExperimentsTM", experimentsTM);
        	
        	return results;
        }
        else
        {
        	ExecutionLog.get().writeErr("PickExperiment: Cannot pick from empty list of Experiments.");
        	throw new WorkflowErrorException();
        }
    } 
	
}