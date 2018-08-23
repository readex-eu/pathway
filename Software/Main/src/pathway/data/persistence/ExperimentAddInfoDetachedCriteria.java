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
public class ExperimentAddInfoDetachedCriteria extends AbstractORMDetachedCriteria {
	public final StringExpression experimentId;
	public final AssociationExpression experiment;
	public final StringExpression name;
	public final StringExpression value;
	
	public ExperimentAddInfoDetachedCriteria() {
		super(pathway.data.persistence.ExperimentAddInfo.class, pathway.data.persistence.ExperimentAddInfoCriteria.class);
		experimentId = new StringExpression("ORM_Experiment.ID", this.getDetachedCriteria());
		experiment = new AssociationExpression("ORM_Experiment", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		value = new StringExpression("value", this.getDetachedCriteria());
	}
	
	public ExperimentAddInfoDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, pathway.data.persistence.ExperimentAddInfoCriteria.class);
		experimentId = new StringExpression("ORM_Experiment.ID", this.getDetachedCriteria());
		experiment = new AssociationExpression("ORM_Experiment", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		value = new StringExpression("value", this.getDetachedCriteria());
	}
	
	public ExperimentDetachedCriteria createExperimentCriteria() {
		return new ExperimentDetachedCriteria(createCriteria("experiment"));
	}
	
	public ExperimentAddInfo uniqueExperimentAddInfo(PersistentSession session) {
		return (ExperimentAddInfo) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public ExperimentAddInfo[] listExperimentAddInfo(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (ExperimentAddInfo[]) list.toArray(new ExperimentAddInfo[list.size()]);
	}
}

