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
public class PscRegionDetachedCriteria extends AbstractORMDetachedCriteria {
	public final StringExpression ID;
	public final AssociationExpression FK_Application;
	public final StringExpression name;
	public final StringExpression type;
	public final StringExpression sourceFile;
	public final IntegerExpression startLine;
	public final IntegerExpression endLine;
	public final StringExpression dataScope;
	public final StringExpression pscRegionParentId;
	public final AssociationExpression pscRegionParent;
	public final CollectionExpression pscProperty;
	public final CollectionExpression pscRegion;
	
	public PscRegionDetachedCriteria() {
		super(pathway.data.persistence.PscRegion.class, pathway.data.persistence.PscRegionCriteria.class);
		ID = new StringExpression("ID", this.getDetachedCriteria());
		FK_Application = new AssociationExpression("ORM_FK_Application", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		type = new StringExpression("type", this.getDetachedCriteria());
		sourceFile = new StringExpression("sourceFile", this.getDetachedCriteria());
		startLine = new IntegerExpression("startLine", this.getDetachedCriteria());
		endLine = new IntegerExpression("endLine", this.getDetachedCriteria());
		dataScope = new StringExpression("dataScope", this.getDetachedCriteria());
		pscRegionParentId = new StringExpression("ORM_PscRegionParent.ID", this.getDetachedCriteria());
		pscRegionParent = new AssociationExpression("ORM_PscRegionParent", this.getDetachedCriteria());
		pscProperty = new CollectionExpression("pscProperty", this.getDetachedCriteria());
		pscRegion = new CollectionExpression("pscRegion", this.getDetachedCriteria());
	}
	
	public PscRegionDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, pathway.data.persistence.PscRegionCriteria.class);
		ID = new StringExpression("ID", this.getDetachedCriteria());
		FK_Application = new AssociationExpression("ORM_FK_Application", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		type = new StringExpression("type", this.getDetachedCriteria());
		sourceFile = new StringExpression("sourceFile", this.getDetachedCriteria());
		startLine = new IntegerExpression("startLine", this.getDetachedCriteria());
		endLine = new IntegerExpression("endLine", this.getDetachedCriteria());
		dataScope = new StringExpression("dataScope", this.getDetachedCriteria());
		pscRegionParentId = new StringExpression("ORM_PscRegionParent.ID", this.getDetachedCriteria());
		pscRegionParent = new AssociationExpression("ORM_PscRegionParent", this.getDetachedCriteria());
		pscProperty = new CollectionExpression("pscProperty", this.getDetachedCriteria());
		pscRegion = new CollectionExpression("pscRegion", this.getDetachedCriteria());
	}
	
	public ApplicationDetachedCriteria createFK_ApplicationCriteria() {
		return new ApplicationDetachedCriteria(createCriteria("FK_Application"));
	}
	
	public PscRegionDetachedCriteria createPscRegionParentCriteria() {
		return new PscRegionDetachedCriteria(createCriteria("pscRegionParent"));
	}
	
	public PscPropertyDetachedCriteria createPscPropertyCriteria() {
		return new PscPropertyDetachedCriteria(createCriteria("pscProperty"));
	}
	
	public PscRegionDetachedCriteria createPscRegionCriteria() {
		return new PscRegionDetachedCriteria(createCriteria("pscRegion"));
	}
	
	public PscRegion uniquePscRegion(PersistentSession session) {
		return (PscRegion) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public PscRegion[] listPscRegion(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (PscRegion[]) list.toArray(new PscRegion[list.size()]);
	}
}

