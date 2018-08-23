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
public class ExperimentCriteria extends AbstractORMCriteria {
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
	
	public ExperimentCriteria(Criteria criteria) {
		super(criteria);
		ID = new StringExpression("ID", this);
		expDate = new TimestampExpression("expDate", this);
		toolId = new IntegerExpression("ORM_Tool.ID", this);
		tool = new AssociationExpression("ORM_Tool", this);
		HPCSystemId = new StringExpression("ORM_HPCSystem.name", this);
		HPCSystem = new AssociationExpression("ORM_HPCSystem", this);
		userName = new StringExpression("userName", this);
		resultsURI = new StringExpression("resultsURI", this);
		application = new AssociationExpression("ORM_Application", this);
		historicalNotesId = new IntegerExpression("ORM_HistoricalNotes.ID", this);
		historicalNotes = new AssociationExpression("ORM_HistoricalNotes", this);
		jobId = new StringExpression("jobId", this);
		jobState = new StringExpression("jobState", this);
		inputDataFileName = new StringExpression("inputDataFileName", this);
		mpiProcs = new IntegerExpression("mpiProcs", this);
		ompThreads = new IntegerExpression("ompThreads", this);
		startupFolder = new StringExpression("startupFolder", this);
		codeVersion = new StringExpression("codeVersion", this);
		execCmd = new StringExpression("execCmd", this);
		stdOut = new StringExpression("stdOut", this);
		stdErr = new StringExpression("stdErr", this);
		compileLog = new StringExpression("compileLog", this);
		loadedModules = new StringExpression("loadedModules", this);
		environment = new StringExpression("environment", this);
		comment = new StringExpression("comment", this);
		interPhaseDynamism = new StringExpression("interPhaseDynamism", this);
		intraPhaseDynamism = new StringExpression("intraPhaseDynamism", this);
		tuningModel = new StringExpression("tuningModel", this);
		pscProperty = new CollectionExpression("pscProperty", this);
		experimentAddInfo = new CollectionExpression("experimentAddInfo", this);
	}
	
	public ExperimentCriteria(PersistentSession session) {
		this(session.createCriteria(Experiment.class));
	}
	
	public ExperimentCriteria() throws PersistentException {
		this(PathwayPersistentManager.instance().getSession());
	}
	
	public ToolCriteria createToolCriteria() {
		return new ToolCriteria(createCriteria("ORM_Tool"));
	}
	
	public HPCSystemCriteria createHPCSystemCriteria() {
		return new HPCSystemCriteria(createCriteria("ORM_HPCSystem"));
	}
	
	public ApplicationCriteria createApplicationCriteria() {
		return new ApplicationCriteria(createCriteria("ORM_Application"));
	}
	
	public HistoricalNotesCriteria createHistoricalNotesCriteria() {
		return new HistoricalNotesCriteria(createCriteria("ORM_HistoricalNotes"));
	}
	
	public PscPropertyCriteria createPscPropertyCriteria() {
		return new PscPropertyCriteria(createCriteria("pscProperty"));
	}
	
	public ExperimentAddInfoCriteria createExperimentAddInfoCriteria() {
		return new ExperimentAddInfoCriteria(createCriteria("experimentAddInfo"));
	}
	
	public Experiment uniqueExperiment() {
		return (Experiment) super.uniqueResult();
	}
	
	public Experiment[] listExperiment() {
		java.util.List list = super.list();
		return (Experiment[]) list.toArray(new Experiment[list.size()]);
	}
}

