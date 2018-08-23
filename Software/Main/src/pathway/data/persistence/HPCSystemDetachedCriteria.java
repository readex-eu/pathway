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
public class HPCSystemDetachedCriteria extends AbstractORMDetachedCriteria {
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
	
	public HPCSystemDetachedCriteria() {
		super(pathway.data.persistence.HPCSystem.class, pathway.data.persistence.HPCSystemCriteria.class);
		name = new StringExpression("name", this.getDetachedCriteria());
		organisation = new StringExpression("organisation", this.getDetachedCriteria());
		website = new StringExpression("website", this.getDetachedCriteria());
		batchSystem = new StringExpression("batchSystem", this.getDetachedCriteria());
		totalCores = new IntegerExpression("totalCores", this.getDetachedCriteria());
		nodes = new IntegerExpression("nodes", this.getDetachedCriteria());
		processorsPerNode = new IntegerExpression("processorsPerNode", this.getDetachedCriteria());
		systemPeakPerformance = new FloatExpression("systemPeakPerformance", this.getDetachedCriteria());
		memoryPerCore = new FloatExpression("memoryPerCore", this.getDetachedCriteria());
		fileSystem = new StringExpression("fileSystem", this.getDetachedCriteria());
		netTechnology = new StringExpression("netTechnology", this.getDetachedCriteria());
		netTopology = new StringExpression("netTopology", this.getDetachedCriteria());
		availableModules = new StringExpression("availableModules", this.getDetachedCriteria());
		experiment = new CollectionExpression("experiment", this.getDetachedCriteria());
		HPCSystems_CPUId = new StringExpression("HPCSystems_CPU.name", this.getDetachedCriteria());
		HPCSystems_CPU = new AssociationExpression("HPCSystems_CPU", this.getDetachedCriteria());
	}
	
	public HPCSystemDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, pathway.data.persistence.HPCSystemCriteria.class);
		name = new StringExpression("name", this.getDetachedCriteria());
		organisation = new StringExpression("organisation", this.getDetachedCriteria());
		website = new StringExpression("website", this.getDetachedCriteria());
		batchSystem = new StringExpression("batchSystem", this.getDetachedCriteria());
		totalCores = new IntegerExpression("totalCores", this.getDetachedCriteria());
		nodes = new IntegerExpression("nodes", this.getDetachedCriteria());
		processorsPerNode = new IntegerExpression("processorsPerNode", this.getDetachedCriteria());
		systemPeakPerformance = new FloatExpression("systemPeakPerformance", this.getDetachedCriteria());
		memoryPerCore = new FloatExpression("memoryPerCore", this.getDetachedCriteria());
		fileSystem = new StringExpression("fileSystem", this.getDetachedCriteria());
		netTechnology = new StringExpression("netTechnology", this.getDetachedCriteria());
		netTopology = new StringExpression("netTopology", this.getDetachedCriteria());
		availableModules = new StringExpression("availableModules", this.getDetachedCriteria());
		experiment = new CollectionExpression("experiment", this.getDetachedCriteria());
		HPCSystems_CPUId = new StringExpression("HPCSystems_CPU.name", this.getDetachedCriteria());
		HPCSystems_CPU = new AssociationExpression("HPCSystems_CPU", this.getDetachedCriteria());
	}
	
	public ExperimentDetachedCriteria createExperimentCriteria() {
		return new ExperimentDetachedCriteria(createCriteria("experiment"));
	}
	
	public HPCSystem_CPUDetachedCriteria createHPCSystems_CPUCriteria() {
		return new HPCSystem_CPUDetachedCriteria(createCriteria("HPCSystems_CPU"));
	}
	
	public HPCSystem uniqueHPCSystem(PersistentSession session) {
		return (HPCSystem) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public HPCSystem[] listHPCSystem(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (HPCSystem[]) list.toArray(new HPCSystem[list.size()]);
	}
}

