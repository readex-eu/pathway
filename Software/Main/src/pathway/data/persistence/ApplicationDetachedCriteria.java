/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: 
 * License Type: Evaluation
 */
package pathway.data.persistence;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentSession;
import org.orm.criteria.*;

@SuppressWarnings({ "all", "unchecked" })
public class ApplicationDetachedCriteria extends AbstractORMDetachedCriteria {
	public final StringExpression name;
	public final StringExpression config;
	public final StringExpression executable;
	public final StringExpression inputDataFileNames;
	public final StringExpression startArgs;
	public final StringExpression currentCodeVersion;
	public final StringExpression codeLocation;
	public final StringExpression execLocation;
	public final StringExpression reqModules;
	public final StringExpression reqEnvVars;
	public final StringExpression eclipseProject;
	public final IntegerExpression wallclockLimit;
	public final StringExpression interPhaseDynamism;
	public final StringExpression intraPhaseDynamism;
	public final StringExpression tuningPotential;
	public final StringExpression DTATuningModel;
	public final StringExpression RATSwitchingDecisions;
	public final StringExpression calibratedTuningModel;
	public final StringExpression phaseRegionName;
	public final StringExpression tuneParamNames;
	public final StringExpression CPUFreqParamValues;
	public final StringExpression uncoreFreqParamValues;
	public final BooleanExpression CPUFreqParamEnable;
	public final BooleanExpression uncoreFreqParamEnable;
	public final IntegerExpression info;
	public final StringExpression selectiveInfo;
	public final StringExpression OMPThreadsParamCount;
	public final BooleanExpression OMPThreadsParamEnable;
	public final BooleanExpression ATPlibParamEnable;
	public final BooleanExpression ATPexhaustive;
	public final BooleanExpression energy;
	public final BooleanExpression time;
	public final BooleanExpression EDP;
	public final BooleanExpression ED2P;
	public final BooleanExpression CPUEnergy;
	public final BooleanExpression TCO;
	public final StringExpression costPerJoule;
	public final StringExpression costPerCoreHour;
	public final BooleanExpression exhaustive;
	public final BooleanExpression random;
	public final BooleanExpression individual;
	public final BooleanExpression genetic;
	public final StringExpression samples;
	public final StringExpression keep;
	public final StringExpression population;
	public final StringExpression maxGenerations;
	public final StringExpression timer;
	public final BooleanExpression normalizedEnergy;
	public final BooleanExpression normalizedTime;
	public final BooleanExpression normalizedEDP;
	public final BooleanExpression normalizedED2P;
	public final BooleanExpression normalizedCPUEnergy;
	public final BooleanExpression normalizedTCO;
	public final StringExpression energyPlugName;
	public final StringExpression energyMetNames;
	public final StringExpression ratInputDataFileName;
	public final BooleanExpression ATPindividual;
	public final CollectionExpression pscRegion;
	public final CollectionExpression experiment;
	
	public ApplicationDetachedCriteria() {
		super(pathway.data.persistence.Application.class, pathway.data.persistence.ApplicationCriteria.class);
		name = new StringExpression("name", this.getDetachedCriteria());
		config = new StringExpression("config", this.getDetachedCriteria());
		executable = new StringExpression("executable", this.getDetachedCriteria());
		inputDataFileNames = new StringExpression("inputDataFileNames", this.getDetachedCriteria());
		startArgs = new StringExpression("startArgs", this.getDetachedCriteria());
		currentCodeVersion = new StringExpression("currentCodeVersion", this.getDetachedCriteria());
		codeLocation = new StringExpression("codeLocation", this.getDetachedCriteria());
		execLocation = new StringExpression("execLocation", this.getDetachedCriteria());
		reqModules = new StringExpression("reqModules", this.getDetachedCriteria());
		reqEnvVars = new StringExpression("reqEnvVars", this.getDetachedCriteria());
		eclipseProject = new StringExpression("eclipseProject", this.getDetachedCriteria());
		wallclockLimit = new IntegerExpression("wallclockLimit", this.getDetachedCriteria());
		interPhaseDynamism = new StringExpression("interPhaseDynamism", this.getDetachedCriteria());
		intraPhaseDynamism = new StringExpression("intraPhaseDynamism", this.getDetachedCriteria());
		tuningPotential = new StringExpression("tuningPotential", this.getDetachedCriteria());
		DTATuningModel = new StringExpression("DTATuningModel", this.getDetachedCriteria());
		RATSwitchingDecisions = new StringExpression("RATSwitchingDecisions", this.getDetachedCriteria());
		calibratedTuningModel = new StringExpression("calibratedTuningModel", this.getDetachedCriteria());
		phaseRegionName = new StringExpression("phaseRegionName", this.getDetachedCriteria());
		tuneParamNames = new StringExpression("tuneParamNames", this.getDetachedCriteria());
		CPUFreqParamValues = new StringExpression("CPUFreqParamValues", this.getDetachedCriteria());
		uncoreFreqParamValues = new StringExpression("uncoreFreqParamValues", this.getDetachedCriteria());
		CPUFreqParamEnable = new BooleanExpression("CPUFreqParamEnable", this.getDetachedCriteria());
		uncoreFreqParamEnable = new BooleanExpression("uncoreFreqParamEnable", this.getDetachedCriteria());
		info = new IntegerExpression("info", this.getDetachedCriteria());
		selectiveInfo = new StringExpression("selectiveInfo", this.getDetachedCriteria());
		OMPThreadsParamCount = new StringExpression("OMPThreadsParamCount", this.getDetachedCriteria());
		OMPThreadsParamEnable = new BooleanExpression("OMPThreadsParamEnable", this.getDetachedCriteria());
		ATPlibParamEnable = new BooleanExpression("ATPlibParamEnable", this.getDetachedCriteria());
		ATPexhaustive = new BooleanExpression("ATPexhaustive", this.getDetachedCriteria());
		energy = new BooleanExpression("energy", this.getDetachedCriteria());
		time = new BooleanExpression("time", this.getDetachedCriteria());
		EDP = new BooleanExpression("EDP", this.getDetachedCriteria());
		ED2P = new BooleanExpression("ED2P", this.getDetachedCriteria());
		CPUEnergy = new BooleanExpression("CPUEnergy", this.getDetachedCriteria());
		TCO = new BooleanExpression("TCO", this.getDetachedCriteria());
		costPerJoule = new StringExpression("costPerJoule", this.getDetachedCriteria());
		costPerCoreHour = new StringExpression("costPerCoreHour", this.getDetachedCriteria());
		exhaustive = new BooleanExpression("exhaustive", this.getDetachedCriteria());
		random = new BooleanExpression("random", this.getDetachedCriteria());
		individual = new BooleanExpression("individual", this.getDetachedCriteria());
		genetic = new BooleanExpression("genetic", this.getDetachedCriteria());
		samples = new StringExpression("samples", this.getDetachedCriteria());
		keep = new StringExpression("keep", this.getDetachedCriteria());
		population = new StringExpression("population", this.getDetachedCriteria());
		maxGenerations = new StringExpression("maxGenerations", this.getDetachedCriteria());
		timer = new StringExpression("timer", this.getDetachedCriteria());
		normalizedEnergy = new BooleanExpression("normalizedEnergy", this.getDetachedCriteria());
		normalizedTime = new BooleanExpression("normalizedTime", this.getDetachedCriteria());
		normalizedEDP = new BooleanExpression("normalizedEDP", this.getDetachedCriteria());
		normalizedED2P = new BooleanExpression("normalizedED2P", this.getDetachedCriteria());
		normalizedCPUEnergy = new BooleanExpression("normalizedCPUEnergy", this.getDetachedCriteria());
		normalizedTCO = new BooleanExpression("normalizedTCO", this.getDetachedCriteria());
		energyPlugName = new StringExpression("energyPlugName", this.getDetachedCriteria());
		energyMetNames = new StringExpression("energyMetNames", this.getDetachedCriteria());
		ratInputDataFileName = new StringExpression("ratInputDataFileName", this.getDetachedCriteria());
		ATPindividual = new BooleanExpression("ATPindividual", this.getDetachedCriteria());
		pscRegion = new CollectionExpression("pscRegion", this.getDetachedCriteria());
		experiment = new CollectionExpression("experiment", this.getDetachedCriteria());
	}
	
	public ApplicationDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, pathway.data.persistence.ApplicationCriteria.class);
		name = new StringExpression("name", this.getDetachedCriteria());
		config = new StringExpression("config", this.getDetachedCriteria());
		executable = new StringExpression("executable", this.getDetachedCriteria());
		inputDataFileNames = new StringExpression("inputDataFileNames", this.getDetachedCriteria());
		startArgs = new StringExpression("startArgs", this.getDetachedCriteria());
		currentCodeVersion = new StringExpression("currentCodeVersion", this.getDetachedCriteria());
		codeLocation = new StringExpression("codeLocation", this.getDetachedCriteria());
		execLocation = new StringExpression("execLocation", this.getDetachedCriteria());
		reqModules = new StringExpression("reqModules", this.getDetachedCriteria());
		reqEnvVars = new StringExpression("reqEnvVars", this.getDetachedCriteria());
		eclipseProject = new StringExpression("eclipseProject", this.getDetachedCriteria());
		wallclockLimit = new IntegerExpression("wallclockLimit", this.getDetachedCriteria());
		interPhaseDynamism = new StringExpression("interPhaseDynamism", this.getDetachedCriteria());
		intraPhaseDynamism = new StringExpression("intraPhaseDynamism", this.getDetachedCriteria());
		tuningPotential = new StringExpression("tuningPotential", this.getDetachedCriteria());
		DTATuningModel = new StringExpression("DTATuningModel", this.getDetachedCriteria());
		RATSwitchingDecisions = new StringExpression("RATSwitchingDecisions", this.getDetachedCriteria());
		calibratedTuningModel = new StringExpression("calibratedTuningModel", this.getDetachedCriteria());
		phaseRegionName = new StringExpression("phaseRegionName", this.getDetachedCriteria());
		tuneParamNames = new StringExpression("tuneParamNames", this.getDetachedCriteria());
		CPUFreqParamValues = new StringExpression("CPUFreqParamValues", this.getDetachedCriteria());
		uncoreFreqParamValues = new StringExpression("uncoreFreqParamValues", this.getDetachedCriteria());
		CPUFreqParamEnable = new BooleanExpression("CPUFreqParamEnable", this.getDetachedCriteria());
		uncoreFreqParamEnable = new BooleanExpression("uncoreFreqParamEnable", this.getDetachedCriteria());
		info = new IntegerExpression("info", this.getDetachedCriteria());
		selectiveInfo = new StringExpression("selectiveInfo", this.getDetachedCriteria());
		OMPThreadsParamCount = new StringExpression("OMPThreadsParamCount", this.getDetachedCriteria());
		OMPThreadsParamEnable = new BooleanExpression("OMPThreadsParamEnable", this.getDetachedCriteria());
		ATPlibParamEnable = new BooleanExpression("ATPlibParamEnable", this.getDetachedCriteria());
		ATPexhaustive = new BooleanExpression("ATPexhaustive", this.getDetachedCriteria());
		energy = new BooleanExpression("energy", this.getDetachedCriteria());
		time = new BooleanExpression("time", this.getDetachedCriteria());
		EDP = new BooleanExpression("EDP", this.getDetachedCriteria());
		ED2P = new BooleanExpression("ED2P", this.getDetachedCriteria());
		CPUEnergy = new BooleanExpression("CPUEnergy", this.getDetachedCriteria());
		TCO = new BooleanExpression("TCO", this.getDetachedCriteria());
		costPerJoule = new StringExpression("costPerJoule", this.getDetachedCriteria());
		costPerCoreHour = new StringExpression("costPerCoreHour", this.getDetachedCriteria());
		exhaustive = new BooleanExpression("exhaustive", this.getDetachedCriteria());
		random = new BooleanExpression("random", this.getDetachedCriteria());
		individual = new BooleanExpression("individual", this.getDetachedCriteria());
		genetic = new BooleanExpression("genetic", this.getDetachedCriteria());
		samples = new StringExpression("samples", this.getDetachedCriteria());
		keep = new StringExpression("keep", this.getDetachedCriteria());
		population = new StringExpression("population", this.getDetachedCriteria());
		maxGenerations = new StringExpression("maxGenerations", this.getDetachedCriteria());
		timer = new StringExpression("timer", this.getDetachedCriteria());
		normalizedEnergy = new BooleanExpression("normalizedEnergy", this.getDetachedCriteria());
		normalizedTime = new BooleanExpression("normalizedTime", this.getDetachedCriteria());
		normalizedEDP = new BooleanExpression("normalizedEDP", this.getDetachedCriteria());
		normalizedED2P = new BooleanExpression("normalizedED2P", this.getDetachedCriteria());
		normalizedCPUEnergy = new BooleanExpression("normalizedCPUEnergy", this.getDetachedCriteria());
		normalizedTCO = new BooleanExpression("normalizedTCO", this.getDetachedCriteria());
		energyPlugName = new StringExpression("energyPlugName", this.getDetachedCriteria());
		energyMetNames = new StringExpression("energyMetNames", this.getDetachedCriteria());
		ratInputDataFileName = new StringExpression("ratInputDataFileName", this.getDetachedCriteria());
		ATPindividual = new BooleanExpression("ATPindividual", this.getDetachedCriteria());
		pscRegion = new CollectionExpression("pscRegion", this.getDetachedCriteria());
		experiment = new CollectionExpression("experiment", this.getDetachedCriteria());
	}
	
	public PscRegionDetachedCriteria createPscRegionCriteria() {
		return new PscRegionDetachedCriteria(createCriteria("pscRegion"));
	}
	
	public ExperimentDetachedCriteria createExperimentCriteria() {
		return new ExperimentDetachedCriteria(createCriteria("experiment"));
	}
	
	public Application uniqueApplication(PersistentSession session) {
		return (Application) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public Application[] listApplication(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (Application[]) list.toArray(new Application[list.size()]);
	}
}

