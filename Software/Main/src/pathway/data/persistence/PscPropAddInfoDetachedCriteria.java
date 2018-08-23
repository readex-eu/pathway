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
public class PscPropAddInfoDetachedCriteria extends AbstractORMDetachedCriteria {
	public final LongExpression pscPropertyId;
	public final AssociationExpression pscProperty;
	public final StringExpression name;
	public final StringExpression value;
	
	public PscPropAddInfoDetachedCriteria() {
		super(pathway.data.persistence.PscPropAddInfo.class, pathway.data.persistence.PscPropAddInfoCriteria.class);
		pscPropertyId = new LongExpression("ORM_PscProperty.ID", this.getDetachedCriteria());
		pscProperty = new AssociationExpression("ORM_PscProperty", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		value = new StringExpression("value", this.getDetachedCriteria());
	}
	
	public PscPropAddInfoDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, pathway.data.persistence.PscPropAddInfoCriteria.class);
		pscPropertyId = new LongExpression("ORM_PscProperty.ID", this.getDetachedCriteria());
		pscProperty = new AssociationExpression("ORM_PscProperty", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		value = new StringExpression("value", this.getDetachedCriteria());
	}
	
	public PscPropertyDetachedCriteria createPscPropertyCriteria() {
		return new PscPropertyDetachedCriteria(createCriteria("pscProperty"));
	}
	
	public PscPropAddInfo uniquePscPropAddInfo(PersistentSession session) {
		return (PscPropAddInfo) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public PscPropAddInfo[] listPscPropAddInfo(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (PscPropAddInfo[]) list.toArray(new PscPropAddInfo[list.size()]);
	}
}

