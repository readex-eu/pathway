package pathway.workflows.workitems;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.drools.runtime.process.WorkflowProcessInstance;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

import pathway.data.persistence.Application;
import pathway.data.persistence.Experiment;
import pathway.eclipse.ExecutionLog;




@NonNullByDefault
public class Helper {
    /** Fills in experiment variables, such as {#MPI_PROCS#}. */
    public static String fillPlaceholders(String string, Experiment experiment) {
        return fillPlaceholders(string, experiment, null);
    }


    /** Fills in experiment variables, such as {#MPI_PROCS#}. Can also fill in workflow variables. */
    public static String fillPlaceholders(String string, Experiment experiment, @Nullable WorkflowProcessInstance instance) {
        string = StringUtils.replace(string, "{#APP_NAME#}", String.valueOf(experiment.getExecCmd()));
        string = StringUtils.replace(string, "{#APP_EXECUTION_DIR#}", String.valueOf(experiment.getStartupFolder()));
        string = StringUtils.replace(string, "{#APP_ARGS#}", String.valueOf(experiment.getApplication().getStartArgs()));
        string = StringUtils.replace(string, "{#APP_INPUT#}", experiment.getInputDataFileName());
        string = StringUtils.replace(string, "{#APP_CONFIG_NAME#}", String.valueOf(experiment.getApplication().getConfig()));
        string = StringUtils.replace(string, "{#MPI_PROCS#}", String.valueOf(experiment.getMpiProcs()));
        string = StringUtils.replace(string, "{#OMP_THREADS#}", String.valueOf(experiment.getOmpThreads()));
        string = StringUtils.replace(string, "{#OUTPUT_LOC#}", experiment.getResultsURI());
        string = StringUtils.replace(string, "{#EXPERIMENT_ID#}", String.valueOf(experiment.getID()));
        string = StringUtils.replace(string, "{#PHASE_REGION_NAME#}", String.valueOf(experiment.getApplication().getPhaseRegionName()));
        Map<String, String> mapTuneParamNamesAndValues= getTuneParamNamesAndValues(experiment.getApplication());
        
        
        string = StringUtils.replace(string, "{#TUNING_LIBRARY#}" , "readex_tuning");
        //string = StringUtils.replace(string, "{#TUNE_PARAM_NAMES#}", StringUtils.join(mapTuneParamNamesAndValues.keySet(), ","));
        //string = StringUtils.replace(string, "{#TUNE_PARAM_VALUES#}", StringUtils.join(mapTuneParamNamesAndValues.values(), " "));
        
        string = StringUtils.replace(string, "{#READEX_CONFIG_FILE_NAME#}" , "readex_config.xml");
        
        string = StringUtils.replace(string, "{#INFO#}", String.valueOf(experiment.getApplication().getInfo()));
        string = StringUtils.replace(string, "{#SELECTIVE_INFO#}", String.valueOf(experiment.getApplication().getSelectiveInfo()));

        if( instance != null ) {
            int i = 0;
            while( i != -1 ) {
                i = string.indexOf("{#VARIABLE:");
                if( i == -1 )
                    break;
                int j = string.indexOf("#}", i);
                if( j == -1 )
                    break;

                String variableName = string.substring(i + 11, j);
                String variableValue = (String)instance.getVariable(variableName);
                string = string.substring(0, i) + variableValue + string.substring(j + 2);
            }
        }

        return string;
    }
    
    private static Map<String, String> getTuneParamNamesAndValues(Application application){
    	Map<String, String> mapNamesValues = new HashMap<String, String>();
    	if (application.getCPUFreqParamEnable()){
    	List<String> cpuFreqParamValuesList = Arrays.asList(application.getCPUFreqParamValues().split("\\s*,\\s*"));      
    		if(cpuFreqParamValuesList.isEmpty()||(cpuFreqParamValuesList.size() < 3))
    			ExecutionLog.get().writeErr("CPU freq param values should not be empty.");
    		else {
    			mapNamesValues.put("dvfs_taurus", "--min_freq="+cpuFreqParamValuesList.get(0)+" --max_freq"+cpuFreqParamValuesList.get(1)+" --freq_step="+cpuFreqParamValuesList.get(2));
    		}       	
    	}
    	
    	if (application.getUncoreFreqParamEnable()){
        	List<String> UncoreFreqParamValuesList = Arrays.asList(application.getUncoreFreqParamValues().split("\\s*,\\s*"));      
        		if(UncoreFreqParamValuesList.isEmpty()||(UncoreFreqParamValuesList.size() < 2))
        			ExecutionLog.get().writeErr("Uncore Frequency param values should not be empty.");
        		else {
        			mapNamesValues.put("uncore_taurus", "--min_ratio="+UncoreFreqParamValuesList.get(0)+" --max_ratio"+UncoreFreqParamValuesList.get(1));
        		}       	
        	}
    	
    	return mapNamesValues;
    }

    private Helper() {
    }
}
