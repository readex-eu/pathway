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
public class ToolDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression ID;
	public final StringExpression name;
	public final StringExpression version;
	public final BooleanExpression profiling;
	public final BooleanExpression tracing;
	public final StringExpression instrumentCMD;
	public final StringExpression instrSuffix;
	public final StringExpression profileCMD;
	public final StringExpression profileArgs;
	public final StringExpression traceCMD;
	public final StringExpression traceArgs;
	public final StringExpression reqModules;
	public final StringExpression reqEnvVars;
	public final StringExpression website;
	public final StringExpression unsetEnvVars;
	public final IntegerExpression column;
	public final CollectionExpression experiment;
	
	public ToolDetachedCriteria() {
		super(pathway.data.persistence.Tool.class, pathway.data.persistence.ToolCriteria.class);
		ID = new IntegerExpression("ID", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		version = new StringExpression("version", this.getDetachedCriteria());
		profiling = new BooleanExpression("profiling", this.getDetachedCriteria());
		tracing = new BooleanExpression("tracing", this.getDetachedCriteria());
		instrumentCMD = new StringExpression("instrumentCMD", this.getDetachedCriteria());
		instrSuffix = new StringExpression("instrSuffix", this.getDetachedCriteria());
		profileCMD = new StringExpression("profileCMD", this.getDetachedCriteria());
		profileArgs = new StringExpression("profileArgs", this.getDetachedCriteria());
		traceCMD = new StringExpression("traceCMD", this.getDetachedCriteria());
		traceArgs = new StringExpression("traceArgs", this.getDetachedCriteria());
		reqModules = new StringExpression("reqModules", this.getDetachedCriteria());
		reqEnvVars = new StringExpression("reqEnvVars", this.getDetachedCriteria());
		website = new StringExpression("website", this.getDetachedCriteria());
		unsetEnvVars = new StringExpression("unsetEnvVars", this.getDetachedCriteria());
		column = new IntegerExpression("column", this.getDetachedCriteria());
		experiment = new CollectionExpression("experiment", this.getDetachedCriteria());
	}
	
	public ToolDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, pathway.data.persistence.ToolCriteria.class);
		ID = new IntegerExpression("ID", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		version = new StringExpression("version", this.getDetachedCriteria());
		profiling = new BooleanExpression("profiling", this.getDetachedCriteria());
		tracing = new BooleanExpression("tracing", this.getDetachedCriteria());
		instrumentCMD = new StringExpression("instrumentCMD", this.getDetachedCriteria());
		instrSuffix = new StringExpression("instrSuffix", this.getDetachedCriteria());
		profileCMD = new StringExpression("profileCMD", this.getDetachedCriteria());
		profileArgs = new StringExpression("profileArgs", this.getDetachedCriteria());
		traceCMD = new StringExpression("traceCMD", this.getDetachedCriteria());
		traceArgs = new StringExpression("traceArgs", this.getDetachedCriteria());
		reqModules = new StringExpression("reqModules", this.getDetachedCriteria());
		reqEnvVars = new StringExpression("reqEnvVars", this.getDetachedCriteria());
		website = new StringExpression("website", this.getDetachedCriteria());
		unsetEnvVars = new StringExpression("unsetEnvVars", this.getDetachedCriteria());
		column = new IntegerExpression("column", this.getDetachedCriteria());
		experiment = new CollectionExpression("experiment", this.getDetachedCriteria());
	}
	
	public ExperimentDetachedCriteria createExperimentCriteria() {
		return new ExperimentDetachedCriteria(createCriteria("experiment"));
	}
	
	public Tool uniqueTool(PersistentSession session) {
		return (Tool) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public Tool[] listTool(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (Tool[]) list.toArray(new Tool[list.size()]);
	}
}

