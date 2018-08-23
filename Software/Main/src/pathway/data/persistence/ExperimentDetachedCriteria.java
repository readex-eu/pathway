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
public class ExperimentDetachedCriteria extends AbstractORMDetachedCriteria {
	public final StringExpression ID;
	public final TimestampExpression expDate;
	public final IntegerExpression toolId;
	public final AssociationExpression tool;
	public final StringExpression HPCSystemId;
	public final AssociationExpression HPCSystem;
	public final StringExpression userName;
	public final StringExpression resultsURI;
	public final AssociationExpression application;
	public final IntegerExpression historicalNotesId;
	public final AssociationExpression historicalNotes;
	public final StringExpression jobId;
	public final StringExpression jobState;
	public final StringExpression inputDataFileName;
	public final IntegerExpression mpiProcs;
	public final IntegerExpression ompThreads;
	public final StringExpression startupFolder;
	public final StringExpression codeVersion;
	public final StringExpression execCmd;
	public final StringExpression stdOut;
	public final StringExpression stdErr;
	public final StringExpression compileLog;
	public final StringExpression loadedModules;
	public final StringExpression environment;
	public final StringExpression comment;
	public final StringExpression interPhaseDynamism;
	public final StringExpression intraPhaseDynamism;
	public final StringExpression tuningModel;
	public final CollectionExpression pscProperty;
	public final CollectionExpression experimentAddInfo;
	
	public ExperimentDetachedCriteria() {
		super(pathway.data.persistence.Experiment.class, pathway.data.persistence.ExperimentCriteria.class);
		ID = new StringExpression("ID", this.getDetachedCriteria());
		expDate = new TimestampExpression("expDate", this.getDetachedCriteria());
		toolId = new IntegerExpression("ORM_Tool.ID", this.getDetachedCriteria());
		tool = new AssociationExpression("ORM_Tool", this.getDetachedCriteria());
		HPCSystemId = new StringExpression("ORM_HPCSystem.name", this.getDetachedCriteria());
		HPCSystem = new AssociationExpression("ORM_HPCSystem", this.getDetachedCriteria());
		userName = new StringExpression("userName", this.getDetachedCriteria());
		resultsURI = new StringExpression("resultsURI", this.getDetachedCriteria());
		application = new AssociationExpression("ORM_Application", this.getDetachedCriteria());
		historicalNotesId = new IntegerExpression("ORM_HistoricalNotes.ID", this.getDetachedCriteria());
		historicalNotes = new AssociationExpression("ORM_HistoricalNotes", this.getDetachedCriteria());
		jobId = new StringExpression("jobId", this.getDetachedCriteria());
		jobState = new StringExpression("jobState", this.getDetachedCriteria());
		inputDataFileName = new StringExpression("inputDataFileName", this.getDetachedCriteria());
		mpiProcs = new IntegerExpression("mpiProcs", this.getDetachedCriteria());
		ompThreads = new IntegerExpression("ompThreads", this.getDetachedCriteria());
		startupFolder = new StringExpression("startupFolder", this.getDetachedCriteria());
		codeVersion = new StringExpression("codeVersion", this.getDetachedCriteria());
		execCmd = new StringExpression("execCmd", this.getDetachedCriteria());
		stdOut = new StringExpression("stdOut", this.getDetachedCriteria());
		stdErr = new StringExpression("stdErr", this.getDetachedCriteria());
		compileLog = new StringExpression("compileLog", this.getDetachedCriteria());
		loadedModules = new StringExpression("loadedModules", this.getDetachedCriteria());
		environment = new StringExpression("environment", this.getDetachedCriteria());
		comment = new StringExpression("comment", this.getDetachedCriteria());
		interPhaseDynamism = new StringExpression("interPhaseDynamism", this.getDetachedCriteria());
		intraPhaseDynamism = new StringExpression("intraPhaseDynamism", this.getDetachedCriteria());
		tuningModel = new StringExpression("tuningModel", this.getDetachedCriteria());
		pscProperty = new CollectionExpression("pscProperty", this.getDetachedCriteria());
		experimentAddInfo = new CollectionExpression("experimentAddInfo", this.getDetachedCriteria());
	}
	
	public ExperimentDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, pathway.data.persistence.ExperimentCriteria.class);
		ID = new StringExpression("ID", this.getDetachedCriteria());
		expDate = new TimestampExpression("expDate", this.getDetachedCriteria());
		toolId = new IntegerExpression("ORM_Tool.ID", this.getDetachedCriteria());
		tool = new AssociationExpression("ORM_Tool", this.getDetachedCriteria());
		HPCSystemId = new StringExpression("ORM_HPCSystem.name", this.getDetachedCriteria());
		HPCSystem = new AssociationExpression("ORM_HPCSystem", this.getDetachedCriteria());
		userName = new StringExpression("userName", this.getDetachedCriteria());
		resultsURI = new StringExpression("resultsURI", this.getDetachedCriteria());
		application = new AssociationExpression("ORM_Application", this.getDetachedCriteria());
		historicalNotesId = new IntegerExpression("ORM_HistoricalNotes.ID", this.getDetachedCriteria());
		historicalNotes = new AssociationExpression("ORM_HistoricalNotes", this.getDetachedCriteria());
		jobId = new StringExpression("jobId", this.getDetachedCriteria());
		jobState = new StringExpression("jobState", this.getDetachedCriteria());
		inputDataFileName = new StringExpression("inputDataFileName", this.getDetachedCriteria());
		mpiProcs = new IntegerExpression("mpiProcs", this.getDetachedCriteria());
		ompThreads = new IntegerExpression("ompThreads", this.getDetachedCriteria());
		startupFolder = new StringExpression("startupFolder", this.getDetachedCriteria());
		codeVersion = new StringExpression("codeVersion", this.getDetachedCriteria());
		execCmd = new StringExpression("execCmd", this.getDetachedCriteria());
		stdOut = new StringExpression("stdOut", this.getDetachedCriteria());
		stdErr = new StringExpression("stdErr", this.getDetachedCriteria());
		compileLog = new StringExpression("compileLog", this.getDetachedCriteria());
		loadedModules = new StringExpression("loadedModules", this.getDetachedCriteria());
		environment = new StringExpression("environment", this.getDetachedCriteria());
		comment = new StringExpression("comment", this.getDetachedCriteria());
		interPhaseDynamism = new StringExpression("interPhaseDynamism", this.getDetachedCriteria());
		intraPhaseDynamism = new StringExpression("intraPhaseDynamism", this.getDetachedCriteria());
		tuningModel = new StringExpression("tuningModel", this.getDetachedCriteria());
		pscProperty = new CollectionExpression("pscProperty", this.getDetachedCriteria());
		experimentAddInfo = new CollectionExpression("experimentAddInfo", this.getDetachedCriteria());
	}
	
	public ToolDetachedCriteria createToolCriteria() {
		return new ToolDetachedCriteria(createCriteria("tool"));
	}
	
	public HPCSystemDetachedCriteria createHPCSystemCriteria() {
		return new HPCSystemDetachedCriteria(createCriteria("HPCSystem"));
	}
	
	public ApplicationDetachedCriteria createApplicationCriteria() {
		return new ApplicationDetachedCriteria(createCriteria("application"));
	}
	
	public HistoricalNotesDetachedCriteria createHistoricalNotesCriteria() {
		return new HistoricalNotesDetachedCriteria(createCriteria("historicalNotes"));
	}
	
	public PscPropertyDetachedCriteria createPscPropertyCriteria() {
		return new PscPropertyDetachedCriteria(createCriteria("pscProperty"));
	}
	
	public ExperimentAddInfoDetachedCriteria createExperimentAddInfoCriteria() {
		return new ExperimentAddInfoDetachedCriteria(createCriteria("experimentAddInfo"));
	}
	
	public Experiment uniqueExperiment(PersistentSession session) {
		return (Experiment) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public Experiment[] listExperiment(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (Experiment[]) list.toArray(new Experiment[list.size()]);
	}
}

