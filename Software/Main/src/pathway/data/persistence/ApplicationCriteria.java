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

import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

@SuppressWarnings({ "all", "unchecked" })
public class ApplicationCriteria extends AbstractORMCriteria {
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
	
	public ApplicationCriteria(Criteria criteria) {
		super(criteria);
		name = new StringExpression("name", this);
		config = new StringExpression("config", this);
		executable = new StringExpression("executable", this);
		inputDataFileNames = new StringExpression("inputDataFileNames", this);
		startArgs = new StringExpression("startArgs", this);
		currentCodeVersion = new StringExpression("currentCodeVersion", this);
		codeLocation = new StringExpression("codeLocation", this);
		execLocation = new StringExpression("execLocation", this);
		reqModules = new StringExpression("reqModules", this);
		reqEnvVars = new StringExpression("reqEnvVars", this);
		eclipseProject = new StringExpression("eclipseProject", this);
		wallclockLimit = new IntegerExpression("wallclockLimit", this);
		interPhaseDynamism = new StringExpression("interPhaseDynamism", this);
		intraPhaseDynamism = new StringExpression("intraPhaseDynamism", this);
		tuningPotential = new StringExpression("tuningPotential", this);
		DTATuningModel = new StringExpression("DTATuningModel", this);
		RATSwitchingDecisions = new StringExpression("RATSwitchingDecisions", this);
		calibratedTuningModel = new StringExpression("calibratedTuningModel", this);
		phaseRegionName = new StringExpression("phaseRegionName", this);
		tuneParamNames = new StringExpression("tuneParamNames", this);
		CPUFreqParamValues = new StringExpression("CPUFreqParamValues", this);
		uncoreFreqParamValues = new StringExpression("uncoreFreqParamValues", this);
		CPUFreqParamEnable = new BooleanExpression("CPUFreqParamEnable", this);
		uncoreFreqParamEnable = new BooleanExpression("uncoreFreqParamEnable", this);
		info = new IntegerExpression("info", this);
		selectiveInfo = new StringExpression("selectiveInfo", this);
		OMPThreadsParamCount = new StringExpression("OMPThreadsParamCount", this);
		OMPThreadsParamEnable = new BooleanExpression("OMPThreadsParamEnable", this);
		ATPlibParamEnable = new BooleanExpression("ATPlibParamEnable", this);
		ATPexhaustive = new BooleanExpression("ATPexhaustive", this);
		energy = new BooleanExpression("energy", this);
		time = new BooleanExpression("time", this);
		EDP = new BooleanExpression("EDP", this);
		ED2P = new BooleanExpression("ED2P", this);
		CPUEnergy = new BooleanExpression("CPUEnergy", this);
		TCO = new BooleanExpression("TCO", this);
		costPerJoule = new StringExpression("costPerJoule", this);
		costPerCoreHour = new StringExpression("costPerCoreHour", this);
		exhaustive = new BooleanExpression("exhaustive", this);
		random = new BooleanExpression("random", this);
		individual = new BooleanExpression("individual", this);
		genetic = new BooleanExpression("genetic", this);
		samples = new StringExpression("samples", this);
		keep = new StringExpression("keep", this);
		population = new StringExpression("population", this);
		maxGenerations = new StringExpression("maxGenerations", this);
		timer = new StringExpression("timer", this);
		normalizedEnergy = new BooleanExpression("normalizedEnergy", this);
		normalizedTime = new BooleanExpression("normalizedTime", this);
		normalizedEDP = new BooleanExpression("normalizedEDP", this);
		normalizedED2P = new BooleanExpression("normalizedED2P", this);
		normalizedCPUEnergy = new BooleanExpression("normalizedCPUEnergy", this);
		normalizedTCO = new BooleanExpression("normalizedTCO", this);
		energyPlugName = new StringExpression("energyPlugName", this);
		energyMetNames = new StringExpression("energyMetNames", this);
		ratInputDataFileName = new StringExpression("ratInputDataFileName", this);
		ATPindividual = new BooleanExpression("ATPindividual", this);
		pscRegion = new CollectionExpression("pscRegion", this);
		experiment = new CollectionExpression("experiment", this);
	}
	
	public ApplicationCriteria(PersistentSession session) {
		this(session.createCriteria(Application.class));
	}
	
	public ApplicationCriteria() throws PersistentException {
		this(PathwayPersistentManager.instance().getSession());
	}
	
	public PscRegionCriteria createPscRegionCriteria() {
		return new PscRegionCriteria(createCriteria("pscRegion"));
	}
	
	public ExperimentCriteria createExperimentCriteria() {
		return new ExperimentCriteria(createCriteria("experiment"));
	}
	
	public Application uniqueApplication() {
		return (Application) super.uniqueResult();
	}
	
	public Application[] listApplication() {
		java.util.List list = super.list();
		return (Application[]) list.toArray(new Application[list.size()]);
	}
}

