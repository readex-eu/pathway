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
public class PscPropertyCriteria extends AbstractORMCriteria {
	public final LongExpression ID;
	public final StringExpression experimentId;
	public final AssociationExpression experiment;
	public final StringExpression regionId;
	public final AssociationExpression region;
	public final StringExpression name;
	public final StringExpression type;
	public final BooleanExpression clustered;
	public final StringExpression configuration;
	public final IntegerExpression process;
	public final IntegerExpression thread;
	public final DoubleExpression severity;
	public final DoubleExpression confidence;
	public final IntegerExpression fileID;
	public final StringExpression fileName;
	public final IntegerExpression RFL;
	public final StringExpression regionType;
	public final CollectionExpression pscPropAddInfo;
	
	public PscPropertyCriteria(Criteria criteria) {
		super(criteria);
		ID = new LongExpression("ID", this);
		experimentId = new StringExpression("ORM_Experiment.ID", this);
		experiment = new AssociationExpression("ORM_Experiment", this);
		regionId = new StringExpression("ORM_Region.ID", this);
		region = new AssociationExpression("ORM_Region", this);
		name = new StringExpression("name", this);
		type = new StringExpression("type", this);
		clustered = new BooleanExpression("clustered", this);
		configuration = new StringExpression("configuration", this);
		process = new IntegerExpression("process", this);
		thread = new IntegerExpression("thread", this);
		severity = new DoubleExpression("severity", this);
		confidence = new DoubleExpression("confidence", this);
		fileID = new IntegerExpression("fileID", this);
		fileName = new StringExpression("fileName", this);
		RFL = new IntegerExpression("RFL", this);
		regionType = new StringExpression("regionType", this);
		pscPropAddInfo = new CollectionExpression("pscPropAddInfo", this);
	}
	
	public PscPropertyCriteria(PersistentSession session) {
		this(session.createCriteria(PscProperty.class));
	}
	
	public PscPropertyCriteria() throws PersistentException {
		this(PathwayPersistentManager.instance().getSession());
	}
	
	public ExperimentCriteria createExperimentCriteria() {
		return new ExperimentCriteria(createCriteria("ORM_Experiment"));
	}
	
	public PscRegionCriteria createRegionCriteria() {
		return new PscRegionCriteria(createCriteria("ORM_Region"));
	}
	
	public PscPropAddInfoCriteria createPscPropAddInfoCriteria() {
		return new PscPropAddInfoCriteria(createCriteria("pscPropAddInfo"));
	}
	
	public PscProperty uniquePscProperty() {
		return (PscProperty) super.uniqueResult();
	}
	
	public PscProperty[] listPscProperty() {
		java.util.List list = super.list();
		return (PscProperty[]) list.toArray(new PscProperty[list.size()]);
	}
}

