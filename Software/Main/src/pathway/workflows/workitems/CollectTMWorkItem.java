package pathway.workflows.workitems;

import java.io.*;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.drools.runtime.process.WorkItem;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import pathway.data.persistence.Experiment;
import pathway.eclipse.ExecutionLog;
import pathway.eclipse.UIUtils;
import pathway.eclipse.WorkflowInstanceData;
import pathway.eclipse.remotetools.BatchSystemManager;
import pathway.eclipse.remotetools.ConnectionManager;
import pathway.eclipse.remotetools.FileUtils;
import pathway.workflows.WorkflowErrorException;

public class CollectTMWorkItem extends PathwayWorkItem {
	
	 @Override
	    public Map<String, Object> execute(WorkItem workItem, WorkflowInstanceData data) {
	        
		 	//Get Params
		 	BatchSystemManager batchSysManager = getParameter(workItem, "BatchSysManager");
		 	List<Experiment> experimentsTM = (List<Experiment>)workItem.getParameter("ExperimentsTM");
		 	String tuningModel = getParameter(workItem, "TuningModel");
		 	
            if( tuningModel.isEmpty() )
                throw new IllegalArgumentException("It is necessary to provide the READEX tuning model name.");
            
		 	String workingDir = (String)workItem.getParameter("WorkingDir");
		 	
		 	 if (workingDir.isEmpty())
	                throw new IllegalArgumentException("WorkingDir cannot be empty.");
		 	
		 	//Get ConnectionManager
		 	final ConnectionManager connection = batchSysManager.getConnectionManager();
		 	
		 	try {
		 		
		 		if( !connection.getConnection().isOpen() )
		 			connection.getConnection().open(null);
	        
		 		//Move file to local system
		 		String [] split =tuningModel.split(Pattern.quote("."));
		 		String tempFilePrefix= split[0];
		 		String tempFileSuffix= split[1];
		 		String separator=".";
		 		File tempFile = File.createTempFile(tempFilePrefix, separator.concat(tempFileSuffix));
		 		
		 		String remote = FileUtils.combinePath(workingDir,tuningModel);
		 		FileUtils.copyResourceToLocal(connection,remote, tempFile.getAbsolutePath(), true, data.monitor);
		 		
		 		readTuningModel(tempFile, experimentsTM);
		 		tempFile.delete();
		 		
		 	} catch( Exception ex ) {
	            UIUtils.showErrorMessage("CollectTMWorkItem execution failed. The return value is non-zero. See the console log for more details.");
	            throw new WorkflowErrorException();
	        }
	        
	        return null;
			
	    }
	 	
	 	/**
	 	 * Method invoked to read the tuning_model.json file to update the database table of each experiment created to perform DTA analysis.
	 	 * @param f
	 	 * @param experimentsTM
	 	 */
	 	public void readTuningModel(File f, List<Experiment> experimentsTM){
	 		
	 		String pathFile=f.getAbsolutePath();
			
			// JSON to JsonElement, convert to String later.
			Gson gson = new Gson();
			JsonElement json=null;
			
			try {
				json = gson.fromJson(new FileReader(pathFile), JsonElement.class);
			} catch (JsonSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonIOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String compactJson = gson.toJson(json);
			
			for( int i =0; i< experimentsTM.size();i++){
				experimentsTM.get(i).setTuningModel(compactJson);
			}
			
	 	}
}