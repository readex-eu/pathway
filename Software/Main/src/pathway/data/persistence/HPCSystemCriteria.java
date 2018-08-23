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
public class HPCSystemCriteria extends AbstractORMCriteria {
	public final StringExpression name;
	public final StringExpression organisation;
	public final StringExpression website;
	public final StringExpression batchSystem;
	public final IntegerExpression totalCores;
	public final IntegerExpression nodes;
	public final IntegerExpression processorsPerNode;
	public final FloatExpression systemPeakPerformance;
	public final FloatExpression memoryPerCore;
	public final StringExpression fileSystem;
	public final StringExpression netTechnology;
	public final StringExpression netTopology;
	public final StringExpression availableModules;
	public final CollectionExpression experiment;
	public final StringExpression HPCSystems_CPUId;
	public final AssociationExpression HPCSystems_CPU;
	
	public HPCSystemCriteria(Criteria criteria) {
		super(criteria);
		name = new StringExpression("name", this);
		organisation = new StringExpression("organisation", this);
		website = new StringExpression("website", this);
		batchSystem = new StringExpression("batchSystem", this);
		totalCores = new IntegerExpression("totalCores", this);
		nodes = new IntegerExpression("nodes", this);
		processorsPerNode = new IntegerExpression("processorsPerNode", this);
		systemPeakPerformance = new FloatExpression("systemPeakPerformance", this);
		memoryPerCore = new FloatExpression("memoryPerCore", this);
		fileSystem = new StringExpression("fileSystem", this);
		netTechnology = new StringExpression("netTechnology", this);
		netTopology = new StringExpression("netTopology", this);
		availableModules = new StringExpression("availableModules", this);
		experiment = new CollectionExpression("experiment", this);
		HPCSystems_CPUId = new StringExpression("HPCSystems_CPU.name", this);
		HPCSystems_CPU = new AssociationExpression("HPCSystems_CPU", this);
	}
	
	public HPCSystemCriteria(PersistentSession session) {
		this(session.createCriteria(HPCSystem.class));
	}
	
	public HPCSystemCriteria() throws PersistentException {
		this(PathwayPersistentManager.instance().getSession());
	}
	
	public ExperimentCriteria createExperimentCriteria() {
		return new ExperimentCriteria(createCriteria("experiment"));
	}
	
	public HPCSystem_CPUCriteria createHPCSystems_CPUCriteria() {
		return new HPCSystem_CPUCriteria(createCriteria("HPCSystems_CPU"));
	}
	
	public HPCSystem uniqueHPCSystem() {
		return (HPCSystem) super.uniqueResult();
	}
	
	public HPCSystem[] listHPCSystem() {
		java.util.List list = super.list();
		return (HPCSystem[]) list.toArray(new HPCSystem[list.size()]);
	}
}

