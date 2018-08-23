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
public class ExperimentAddInfoCriteria extends AbstractORMCriteria {
	public final StringExpression experimentId;
	public final AssociationExpression experiment;
	public final StringExpression name;
	public final StringExpression value;
	
	public ExperimentAddInfoCriteria(Criteria criteria) {
		super(criteria);
		experimentId = new StringExpression("ORM_Experiment.ID", this);
		experiment = new AssociationExpression("ORM_Experiment", this);
		name = new StringExpression("name", this);
		value = new StringExpression("value", this);
	}
	
	public ExperimentAddInfoCriteria(PersistentSession session) {
		this(session.createCriteria(ExperimentAddInfo.class));
	}
	
	public ExperimentAddInfoCriteria() throws PersistentException {
		this(PathwayPersistentManager.instance().getSession());
	}
	
	public ExperimentCriteria createExperimentCriteria() {
		return new ExperimentCriteria(createCriteria("ORM_Experiment"));
	}
	
	public ExperimentAddInfo uniqueExperimentAddInfo() {
		return (ExperimentAddInfo) super.uniqueResult();
	}
	
	public ExperimentAddInfo[] listExperimentAddInfo() {
		java.util.List list = super.list();
		return (ExperimentAddInfo[]) list.toArray(new ExperimentAddInfo[list.size()]);
	}
}

