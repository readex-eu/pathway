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
public class PscPropertyDetachedCriteria extends AbstractORMDetachedCriteria {
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
	
	public PscPropertyDetachedCriteria() {
		super(pathway.data.persistence.PscProperty.class, pathway.data.persistence.PscPropertyCriteria.class);
		ID = new LongExpression("ID", this.getDetachedCriteria());
		experimentId = new StringExpression("ORM_Experiment.ID", this.getDetachedCriteria());
		experiment = new AssociationExpression("ORM_Experiment", this.getDetachedCriteria());
		regionId = new StringExpression("ORM_Region.ID", this.getDetachedCriteria());
		region = new AssociationExpression("ORM_Region", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		type = new StringExpression("type", this.getDetachedCriteria());
		clustered = new BooleanExpression("clustered", this.getDetachedCriteria());
		configuration = new StringExpression("configuration", this.getDetachedCriteria());
		process = new IntegerExpression("process", this.getDetachedCriteria());
		thread = new IntegerExpression("thread", this.getDetachedCriteria());
		severity = new DoubleExpression("severity", this.getDetachedCriteria());
		confidence = new DoubleExpression("confidence", this.getDetachedCriteria());
		fileID = new IntegerExpression("fileID", this.getDetachedCriteria());
		fileName = new StringExpression("fileName", this.getDetachedCriteria());
		RFL = new IntegerExpression("RFL", this.getDetachedCriteria());
		regionType = new StringExpression("regionType", this.getDetachedCriteria());
		pscPropAddInfo = new CollectionExpression("pscPropAddInfo", this.getDetachedCriteria());
	}
	
	public PscPropertyDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, pathway.data.persistence.PscPropertyCriteria.class);
		ID = new LongExpression("ID", this.getDetachedCriteria());
		experimentId = new StringExpression("ORM_Experiment.ID", this.getDetachedCriteria());
		experiment = new AssociationExpression("ORM_Experiment", this.getDetachedCriteria());
		regionId = new StringExpression("ORM_Region.ID", this.getDetachedCriteria());
		region = new AssociationExpression("ORM_Region", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		type = new StringExpression("type", this.getDetachedCriteria());
		clustered = new BooleanExpression("clustered", this.getDetachedCriteria());
		configuration = new StringExpression("configuration", this.getDetachedCriteria());
		process = new IntegerExpression("process", this.getDetachedCriteria());
		thread = new IntegerExpression("thread", this.getDetachedCriteria());
		severity = new DoubleExpression("severity", this.getDetachedCriteria());
		confidence = new DoubleExpression("confidence", this.getDetachedCriteria());
		fileID = new IntegerExpression("fileID", this.getDetachedCriteria());
		fileName = new StringExpression("fileName", this.getDetachedCriteria());
		RFL = new IntegerExpression("RFL", this.getDetachedCriteria());
		regionType = new StringExpression("regionType", this.getDetachedCriteria());
		pscPropAddInfo = new CollectionExpression("pscPropAddInfo", this.getDetachedCriteria());
	}
	
	public ExperimentDetachedCriteria createExperimentCriteria() {
		return new ExperimentDetachedCriteria(createCriteria("experiment"));
	}
	
	public PscRegionDetachedCriteria createRegionCriteria() {
		return new PscRegionDetachedCriteria(createCriteria("region"));
	}
	
	public PscPropAddInfoDetachedCriteria createPscPropAddInfoCriteria() {
		return new PscPropAddInfoDetachedCriteria(createCriteria("pscPropAddInfo"));
	}
	
	public PscProperty uniquePscProperty(PersistentSession session) {
		return (PscProperty) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public PscProperty[] listPscProperty(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (PscProperty[]) list.toArray(new PscProperty[list.size()]);
	}
}

