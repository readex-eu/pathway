package pathway.workflows.workitems;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.drools.runtime.process.WorkItem;
import org.eclipse.remote.core.exception.RemoteConnectionException;
import pathway.data.persistence.Application;
import pathway.data.persistence.Experiment;
import pathway.eclipse.ExecutionLog;
import pathway.eclipse.UIUtils;
import pathway.eclipse.WorkflowInstanceData;
import pathway.eclipse.remotetools.BatchSystemManager;
import pathway.eclipse.remotetools.ConnectionManager;
import pathway.eclipse.remotetools.FileUtils;
import pathway.workflows.WorkflowErrorException;

/**
 * This WorkItem builds the readex_config.xml file (PTF input) and stores relevant dynamism information.
 *
 */
public class SetupForPTFWorkItem extends PathwayWorkItem {
	
	@Override
	public Map<String, Object> execute(WorkItem workItem, WorkflowInstanceData data) {

		//Get Params
		BatchSystemManager batchSysManager = getParameter(workItem, "BatchSysManager");
		List<Experiment> experiments = (List<Experiment>)workItem.getParameter("Experiments");	

		String configFile = getParameter(workItem, "ConfigFile");
		if( configFile.isEmpty() )
			throw new IllegalArgumentException("It is necessary to provide the READEX configFile name.");

		String workingDir = (String)workItem.getParameter("WorkingDir");
		if(workingDir.isEmpty() )
			throw new IllegalArgumentException("WorkingDir cannot be empty.");

		ConnectionManager connection=null;
		try {
			connection = batchSysManager.getConnectionManager(); 	//Get ConnectionManager
			if( !connection.getConnection().isOpen() )
				connection.getConnection().open(null);
		} catch (RemoteConnectionException rce) {
			UIUtils.showErrorMessage("SetupForPTFWorkItem execution failed due to a communication problem with the HPCSystem.");
		}

		ExecutionLog.get().writeLog("Before File creation");
		String [] split =configFile.split(Pattern.quote("."));
		String tempFilePrefix= split[0];
		String tempFileSuffix= split[1];
		String separator=".";

		try {
			File tempFile = File.createTempFile(tempFilePrefix, separator.concat(tempFileSuffix));
			String remote = FileUtils.combinePath(workingDir,configFile);
			FileUtils.copyResourceToLocal(connection,remote, tempFile.getAbsolutePath(), true, data.monitor);	//Move file to local system

			if (experiments.isEmpty()==false) {				//Update readex_config.xml
				Application app=experiments.get(0).getApplication();
				updateReadexConfig(tempFile, app);
			} else {
				UIUtils.showErrorMessage("SetupForPTFWorkItem execution failed because experiments list is empty.");
				throw new IllegalArgumentException("At least a experiment is necessary.");
			}

			FileUtils.copyResourceToRemote(connection, tempFile.getAbsolutePath(), remote, true, null);		// Move file to HPC system
			readReadexConfig(tempFile, experiments);
			tempFile.delete();
			
		} catch( Exception ex ) {
			UIUtils.showErrorMessage("SetupForPTFWorkItem execution failed. The return value is non-zero. See the console log for more details.");
			throw new WorkflowErrorException();
		}

		return null;
	}
    
	/**
	 * Rebuilds the readex_config.xml file using the user configuration of the application
	 * @param file
	 * @param app
	 */
	public void updateReadexConfig (File file, Application app) { 

		SAXBuilder saxBuilder = new SAXBuilder(); 
		Document document;

		String CPUFreqParamValues="";
		String min_freq="";
		String max_freq="";
		String freq_step="";
		String UncoreFreqParamValues="";
		String umin_freq="";
		String umax_freq="";
		String ufreq_step="";
		String OMPThreadsParamCount="";
		String lower_value="";
		String step="";
		String EnergyMetNames="";
		String node_energy="";
		String cpu0_energy="";
		String cpu1_energy="";
		

		try {
			
			//Parse and load file into memory
			document = saxBuilder.build(file);

			//Getting the root element
			Element rootElement = document.getRootElement();

			//Remove child elements
			rootElement.removeChild("tuningParameter");
			rootElement.removeChild("objectives");
			rootElement.removeChild("CostPerJoule");
			rootElement.removeChild("CostPerCoreHour");
			rootElement.removeChild("periscope");
			rootElement.removeChild("scorep");

			//Add new ones
			List<Element> childList = rootElement.getChildren();
			childList.add(new Element("tuningParameter"));
			childList.add(new Element("objectives"));
			childList.add(new Element("CostPerJoule"));
			childList.add(new Element("CostPerCoreHour"));
			childList.add(new Element("periscope"));
			childList.add(new Element("scorep"));

			//Get-add Plugin values
			Element tuningParam = rootElement.getChild("tuningParameter");

			if (app.getCPUFreqParamEnable()==false && app.getUncoreFreqParamEnable()==false && app.getOMPThreadsParamEnable()==false) {
					UIUtils.showErrorMessage("Wrong HPC Application Configuration. Please, select and tune a tuning parameter in the Edit Application Dialog.");
					throw new WorkflowErrorException();
			}
			
			//CPU Freq
			if (app.getCPUFreqParamEnable()==true) {
				CPUFreqParamValues=app.getCPUFreqParamValues();
				String [] CPUFreqValues=CPUFreqParamValues.split(",");
				min_freq=CPUFreqValues[0];
				max_freq=CPUFreqValues[1];
				freq_step=CPUFreqValues[2];

				tuningParam.addContent(new Element("frequency").addContent(new Element("min_freq").addContent(min_freq)));
				tuningParam.getChild("frequency").addContent(new Element("max_freq").addContent(max_freq));
				tuningParam.getChild("frequency").addContent(new Element("freq_step").addContent(freq_step));

			} 

			//Uncore 
			if (app.getUncoreFreqParamEnable()==true) {
				UncoreFreqParamValues=app.getUncoreFreqParamValues();
				String [] UncoreFreqValues=UncoreFreqParamValues.split(",");
				umin_freq=Integer.valueOf(Integer.parseInt(UncoreFreqValues[0])/100000).toString();
				umax_freq=Integer.valueOf(Integer.parseInt(UncoreFreqValues[1])/100000).toString();
				ufreq_step=Integer.valueOf(Integer.parseInt(UncoreFreqValues[2])/100000).toString();

				tuningParam.addContent(new Element("uncore").addContent(new Element("min_freq").addContent(umin_freq)));
				tuningParam.getChild("uncore").addContent(new Element("max_freq").addContent(umax_freq));
				tuningParam.getChild("uncore").addContent(new Element("freq_step").addContent(ufreq_step));

			} 

			//OpenMPThreads
			if (app.getOMPThreadsParamEnable()==true) {
				OMPThreadsParamCount=app.getOMPThreadsParamCount();
				String [] OMPThreadsCountValues=OMPThreadsParamCount.split(",");
				lower_value=OMPThreadsCountValues[0];
				step=OMPThreadsCountValues[1];

				tuningParam.addContent(new Element("openMPThreads").addContent(new Element("lower_value").addContent(lower_value)));
				tuningParam.getChild("openMPThreads").addContent(new Element("step").addContent(step));

			} 

			//Add objectives
			Element objectives = rootElement.getChild("objectives");
			
			if (app.getEnergy()==false && app.getNormalizedEnergy()==false && app.getTime()==false && app.getNormalizedTime()==false && app.getEDP()==false 
				&& app.getNormalizedEDP()==false && app.getED2P()==false && app.getNormalizedED2P()==false && app.getCPUEnergy()==false && app.getNormalizedCPUEnergy()==false
				&& app.getTCO()==false && app.getNormalizedTCO()==false) {
				
				objectives.addContent(new Element("objective").addContent("Energy"));
			}
												
			if (app.getEnergy())
				objectives.addContent(new Element("objective").addContent("Energy"));
			if (app.getNormalizedEnergy())
				objectives.addContent(new Element("objective").addContent("NormalizedEnergy"));
			
			if (app.getTime())
				objectives.addContent(new Element("objective").addContent("Time"));
			if (app.getNormalizedTime())
				objectives.addContent(new Element("objective").addContent("NormalizedTime"));
			
			if (app.getEDP())
				objectives.addContent(new Element("objective").addContent("EDP"));
			if (app.getNormalizedEDP())
				objectives.addContent(new Element("objective").addContent("NormalizedEDP"));
			
			if (app.getED2P())
				objectives.addContent(new Element("objective").addContent("ED2P"));
			if (app.getNormalizedED2P())
				objectives.addContent(new Element("objective").addContent("NormalizedED2P"));
			
			if (app.getCPUEnergy())
				objectives.addContent(new Element("objective").addContent("CPUEnergy"));
			if (app.getNormalizedCPUEnergy())
				objectives.addContent(new Element("objective").addContent("NormalizedCPUEnergy"));
			
			if (app.getTCO())
				objectives.addContent(new Element("objective").addContent("TCO"));
			if (app.getNormalizedTCO())
				objectives.addContent(new Element("objective").addContent("NormalizedTCO"));

			//Add cost per Joule and cost per core/hour
			 if (app.getTCO()==true || app.getNormalizedTCO()==true) {
				 Element costPerJoule = rootElement.getChild("CostPerJoule");
				 costPerJoule.addContent(app.getCostPerJoule());					
				 Element costPerCoreHour = rootElement.getChild("CostPerCoreHour");
				 costPerCoreHour.addContent(app.getCostPerCoreHour());				
			 } else {
				 rootElement.removeChild("CostPerJoule");
				 rootElement.removeChild("CostPerCoreHour");
			 }
			 
			 
			 if (app.getEnergyPlugName().equalsIgnoreCase("") || app.getEnergyMetNames().equalsIgnoreCase("")) {	
					UIUtils.showErrorMessage("Wrong HPC Application Configuration. Please, specify plugin name and metrics names for PTF in the Edit Application Dialog.");
					throw new WorkflowErrorException();
			}
			 
			//Add energy metrics, search algorithm, atp library configuration and tuningModel path
			Element periscope = rootElement.getChild("periscope");
			periscope.addContent(new Element("metricPlugin").addContent(new Element("name").addContent(app.getEnergyPlugName())));
			
			EnergyMetNames=app.getEnergyMetNames();
			String [] EMetricNames=EnergyMetNames.split(",");
			node_energy=EMetricNames[0];
			cpu0_energy=EMetricNames[1];
			cpu1_energy=EMetricNames[2];
			
			periscope.addContent(new Element("metrics").addContent(new Element("node_energy").addContent(node_energy)));
			periscope.getChild("metrics").addContent(new Element("cpu0_energy").addContent(cpu0_energy));
			periscope.getChild("metrics").addContent(new Element("cpu1_energy").addContent(cpu1_energy));
			
			if (app.getExhaustive()==false && app.getIndividual()==false && app.getRandom()==false && app.getGenetic()==false) {
				UIUtils.showErrorMessage("Wrong HPC Application Configuration. Please, select PTF search algorithm in the Edit Application Dialog.");
				throw new WorkflowErrorException();
			}
			
			if (app.getExhaustive()==true)
				periscope.addContent(new Element("searchAlgorithm").addContent(new Element("name").addContent("exhaustive")));
			
			if (app.getIndividual()==true) {
				periscope.addContent(new Element("searchAlgorithm").addContent(new Element("name").addContent("individual")));
				periscope.getChild("searchAlgorithm").addContent(new Element("keep").addContent(app.getKeep()));
			}
			
			if (app.getRandom()==true) {
				periscope.addContent(new Element("searchAlgorithm").addContent(new Element("name").addContent("random")));
				periscope.getChild("searchAlgorithm").addContent(new Element("samples").addContent(app.getSamples()));
			}
			
			if (app.getGenetic()==true) {
				periscope.addContent(new Element("searchAlgorithm").addContent(new Element("name").addContent("gde3")));
				periscope.getChild("searchAlgorithm").addContent(new Element("populationSize").addContent(app.getPopulation()));
				periscope.getChild("searchAlgorithm").addContent(new Element("maxGenerations").addContent(app.getMaxGenerations()));
				periscope.getChild("searchAlgorithm").addContent(new Element("timer").addContent(app.getTimer()));
			}
			
			if (app.getATPlibParamEnable()==true){
				if (app.getATPexhaustive()==true) {
					periscope.addContent(new Element("atp").addContent(new Element("searchAlgorithm").addContent(new Element("name").addContent("exhaustive_atp"))));
				} else {
					periscope.addContent(new Element("atp").addContent(new Element("searchAlgorithm").addContent(new Element("name").addContent("individual_atp"))));
					periscope.getChild("atp").getChild("searchAlgorithm").addContent(new Element("keep").addContent("1"));
				}
			}
			
			String tuningModelPath = FileUtils.combinePath(app.getExecLocation(),"tuning_model.json");
			periscope.addContent(new Element("tuningModel").addContent(new Element("file_path").addContent(tuningModelPath)));

			//Add substrate
			Element scorep = rootElement.getChild("scorep");
			scorep.addContent(new Element("tuningSubstrate").addContent("rrl"));

			XMLOutputter xmlOutput = new XMLOutputter();

			// display nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(document, new FileWriter(file.getAbsolutePath()));

		} catch (JDOMException e) {
			UIUtils.showErrorMessage("SetupForPTFWorkItem (updateReadexConfig method): JDOMException when updating readex_config.xml file");
		} catch (IOException ioe) {
			UIUtils.showErrorMessage("SetupForPTFWorkItem (updateReadexConfig method): IOException when updating readex_config.xml file");
		}
		
	}

	/**
	 * Method invoked to read the intraPhase/interPhase dynamism information of readex_config.xml file for updating the database table of each experiment created
	 * to perform DTA analysis. 
	 * @param f
	 * @param experiments
	 */
	public void readReadexConfig(File f, List<Experiment> experiments){

		SAXBuilder saxBuilder = new SAXBuilder(); 
		Document document=null;

		List<Element> sgnRegionList=null;
		List<Element> interElements=null;

		try {

			document = saxBuilder.build(f);
			Element rootElement = document.getRootElement();
			List<Element> rddList = rootElement.getChild("readex-dyn-detect").getChildren();

			sgnRegionList = rddList.get(0).getChildren();
			String intraPhase=null;
			if (sgnRegionList.isEmpty()==false){
				for (int i = 0; i < sgnRegionList.size(); i++) {
					Element node = (Element) sgnRegionList.get(i);  
					if (i==0){
						intraPhase=node.getChildText("name")+","+node.getChildText("granularity")+","+node.getChildText("weight")+","+
								node.getChildText("time_variation_reg")+","+node.getChildText("time_variation_phase")+","+ 
								node.getChildText("compute_intensity");
					} else {
						intraPhase=intraPhase+","+node.getChildText("name")+","+node.getChildText("granularity")+","+node.getChildText("weight")+","+
								node.getChildText("time_variation_reg")+","+node.getChildText("time_variation_phase")+","+ 
								node.getChildText("compute_intensity");
					}
				}
			}

			for( int i =0; i< experiments.size();i++){
				experiments.get(i).setIntraPhaseDynamism(intraPhase);
			}

			interElements = rddList.get(1).getChildren();
			String interPhase=null;
			if (interElements.isEmpty()==false){
				for (int i = 0; i < interElements.size(); i++) {
					Element node = (Element) interElements.get(i);
					if (i==0){
						interPhase=node.getValue();
					} else {
						interPhase=interPhase+","+node.getValue();
					}
				}
			}

			for( int i =0; i< experiments.size();i++){
				experiments.get(i).setInterPhaseDynamism(interPhase);
			}

		} catch (JDOMException e) {
			UIUtils.showErrorMessage("SetupForPTFWorkItem (readReadexConfig Method): JDOMException when updating readex_config.xml file");
		} catch (IOException ioe) {
			UIUtils.showErrorMessage("SetupForPTFWorkItem (readReadexConfig Method): IOException when updating readex_config.xml file");
		}

	}

}
