package pathway.workflows.workitems;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.runtime.process.WorkItem;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.orm.PersistentException;

import pathway.data.DataUtils;
import pathway.data.persistence.Application;
import pathway.data.persistence.ApplicationCriteria;
import pathway.data.persistence.Experiment;
import pathway.eclipse.ExecutionLog;
import pathway.eclipse.UIUtils;
import pathway.eclipse.WorkflowInstanceData;
import pathway.utils.PrepareExperimentsList;
import pathway.workflows.WorkflowErrorException;
import pathway.workflows.WorkflowUserAbortException;


public class SetExperimentsInputsWorkItem extends PathwayWorkItem {
    @Override
    public Map<String, Object> execute(WorkItem workItem, WorkflowInstanceData data)
    {
        Object appObj = getParameter(workItem, "Application");
        Object hpcSystemObj = getParameter(workItem, "HPCSystem");
        String perfToolObj = (String)workItem.getParameter("PerfTool");
        List<String> envVarGroups = (List<String>)workItem.getParameter("EnvVarGroups");
        String mpiProcs = getParameter(workItem, "MPIProcs");
        String ompThreads = getParameter(workItem, "OMPThreads");

        if( envVarGroups == null )
            envVarGroups = new ArrayList<String>();

        List<Experiment> experiments = (List<Experiment>)workItem.getParameter("Experiments");  
       	List<Experiment> experimentsWithInputs = new ArrayList<Experiment>();
    	Map<String, Object> results = new HashMap<String, Object>();

        String appName = (String)appObj;
        Application application = null;
        if( !appName.contains("|") ) appName += "|default";
        String[] appNameConfig = appName.split("\\|");
        try
        {
			application = (Application)new ApplicationCriteria().add(Restrictions.eq("name", appNameConfig[0])).add(Restrictions.eq("config", appNameConfig[1])).uniqueResult();
	        if( application == null )
	        {
	            String message = "The specified application has not been found in the database: " + appName;
	            ExecutionLog.get().writeErr(message);
	            UIUtils.showErrorMessage(message);
	            throw new WorkflowErrorException();
	        }
	        application.refresh();

	        String inputDataFileNames = application.getInputDataFileNames();
        	List<String> inputDataFileNamesList = Arrays.asList(inputDataFileNames.split("\\s*,\\s*"));
        	String ratInputDataFile=application.getRatInputDataFileName();
        	List<String> rat_inputDataFileNamesList= new ArrayList<String>();
        	rat_inputDataFileNamesList.add(ratInputDataFile);
        	String[] toolConfig = perfToolObj.split("\\|");
        	
        	if (toolConfig[0].equalsIgnoreCase("READEX RAT")) {
        		
        		if(!ratInputDataFile.equals("")){
        			String description = "";
                	
        			experimentsWithInputs = createExperimentsWithInputs(appObj, hpcSystemObj, perfToolObj, envVarGroups, mpiProcs, ompThreads, description, rat_inputDataFileNamesList);
        			
        			results.put("Experiments", experimentsWithInputs);
					ExecutionLog.get().writeLog("SetExperimentsInputs: multiplied to " + experimentsWithInputs.size() + " experiments.");
					for(Experiment e : experiments)
					{
						DataUtils.RemoveExperiment(e);
					}
					for(Experiment eI : experimentsWithInputs)
					{
						DataUtils.StoreExperiment(eI);
						ExecutionLog.get().writeLog("SetExperimentsInputs: Experiments with Input: "+ eI.getInputDataFileName());
					}
        			
        		} else if(ratInputDataFile.equals("") && !experiments.get(0).getApplication().getStartArgs().contains("{#APP_INPUT#}"))
            	{
            		
        	        results.put("Experiments", experiments);
            	}
            	else
            	{
            		ExecutionLog.get().writeErr("SetExperimentsInputs: Application inputDataFileNames cannot be empty/null for {#APP_INPUT#}.");
            		throw new WorkflowErrorException();	
            	}
        		
        	} else {
        		if(!inputDataFileNames.equals("")){
        			String description = "";
					experimentsWithInputs = createExperimentsWithInputs(appObj, hpcSystemObj, perfToolObj, envVarGroups, mpiProcs, ompThreads, description, inputDataFileNamesList);
					
					results.put("Experiments", experimentsWithInputs);
					ExecutionLog.get().writeLog("SetExperimentsInputs: multiplied to " + experimentsWithInputs.size() + " experiments.");
					for(Experiment e : experiments)
					{
						DataUtils.RemoveExperiment(e);
					}
					for(Experiment eI : experimentsWithInputs)
					{
						DataUtils.StoreExperiment(eI);
						ExecutionLog.get().writeLog("SetExperimentsInputs: Experiments with Input: "+ eI.getInputDataFileName());
					}
        			
        		} else if(inputDataFileNames.equals("") && !experiments.get(0).getApplication().getStartArgs().contains("{#APP_INPUT#}"))
            	{
            		
        	        results.put("Experiments", experiments);
            	}
            	else
            	{
            		ExecutionLog.get().writeErr("SetExperimentsInputs: Application inputDataFileNames cannot be empty/null for {#APP_INPUT#}.");
            		throw new WorkflowErrorException();	
            	}
        		
        	}
        	
        }
        catch (HibernateException e1)
        {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
        catch (PersistentException e1)
        {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        return results;
    }
    
    @NonNullByDefault
    private static List<Experiment> createExperimentsWithInputs(Object appObj, Object hpcSystemObj, String perfTool, List<String> envVarGroups, String mpiProcs,
            String ompThreads, @Nullable String experimentDescription, List<String> inputDataFileNamesList) {

        String description;
        if( experimentDescription == null || experimentDescription.isEmpty() )
        {
            description = "";
        }
        else
            description = experimentDescription;

        return PrepareExperimentsList.generateExpimentsWithInputs(appObj, mpiProcs, ompThreads, hpcSystemObj, perfTool, description, envVarGroups, inputDataFileNamesList);
    }

}