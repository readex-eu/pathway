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
public class HPCSystem_CPUDetachedCriteria extends AbstractORMDetachedCriteria {
	public final StringExpression nameId;
	public final AssociationExpression name;
	public final StringExpression processorType;
	public final StringExpression model;
	public final StringExpression microarchitecture;
	public final IntegerExpression coresPerSocket;
	public final FloatExpression peakFrequencyPerCore;
	public final FloatExpression peakPerformancePerCore;
	public final IntegerExpression l1Cache;
	public final IntegerExpression l2Cache;
	public final IntegerExpression l3Cache;
	public final IntegerExpression process;
	public final IntegerExpression dieSize;
	public final LongExpression transistors;
	public final IntegerExpression memoryChannels;
	public final FloatExpression memoryBandwidth;
	public final StringExpression moreInfo;
	
	public HPCSystem_CPUDetachedCriteria() {
		super(pathway.data.persistence.HPCSystem_CPU.class, pathway.data.persistence.HPCSystem_CPUCriteria.class);
		nameId = new StringExpression("name.name", this.getDetachedCriteria());
		name = new AssociationExpression("name", this.getDetachedCriteria());
		processorType = new StringExpression("processorType", this.getDetachedCriteria());
		model = new StringExpression("model", this.getDetachedCriteria());
		microarchitecture = new StringExpression("microarchitecture", this.getDetachedCriteria());
		coresPerSocket = new IntegerExpression("coresPerSocket", this.getDetachedCriteria());
		peakFrequencyPerCore = new FloatExpression("peakFrequencyPerCore", this.getDetachedCriteria());
		peakPerformancePerCore = new FloatExpression("peakPerformancePerCore", this.getDetachedCriteria());
		l1Cache = new IntegerExpression("l1Cache", this.getDetachedCriteria());
		l2Cache = new IntegerExpression("l2Cache", this.getDetachedCriteria());
		l3Cache = new IntegerExpression("l3Cache", this.getDetachedCriteria());
		process = new IntegerExpression("process", this.getDetachedCriteria());
		dieSize = new IntegerExpression("dieSize", this.getDetachedCriteria());
		transistors = new LongExpression("transistors", this.getDetachedCriteria());
		memoryChannels = new IntegerExpression("memoryChannels", this.getDetachedCriteria());
		memoryBandwidth = new FloatExpression("memoryBandwidth", this.getDetachedCriteria());
		moreInfo = new StringExpression("moreInfo", this.getDetachedCriteria());
	}
	
	public HPCSystem_CPUDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, pathway.data.persistence.HPCSystem_CPUCriteria.class);
		nameId = new StringExpression("name.name", this.getDetachedCriteria());
		name = new AssociationExpression("name", this.getDetachedCriteria());
		processorType = new StringExpression("processorType", this.getDetachedCriteria());
		model = new StringExpression("model", this.getDetachedCriteria());
		microarchitecture = new StringExpression("microarchitecture", this.getDetachedCriteria());
		coresPerSocket = new IntegerExpression("coresPerSocket", this.getDetachedCriteria());
		peakFrequencyPerCore = new FloatExpression("peakFrequencyPerCore", this.getDetachedCriteria());
		peakPerformancePerCore = new FloatExpression("peakPerformancePerCore", this.getDetachedCriteria());
		l1Cache = new IntegerExpression("l1Cache", this.getDetachedCriteria());
		l2Cache = new IntegerExpression("l2Cache", this.getDetachedCriteria());
		l3Cache = new IntegerExpression("l3Cache", this.getDetachedCriteria());
		process = new IntegerExpression("process", this.getDetachedCriteria());
		dieSize = new IntegerExpression("dieSize", this.getDetachedCriteria());
		transistors = new LongExpression("transistors", this.getDetachedCriteria());
		memoryChannels = new IntegerExpression("memoryChannels", this.getDetachedCriteria());
		memoryBandwidth = new FloatExpression("memoryBandwidth", this.getDetachedCriteria());
		moreInfo = new StringExpression("moreInfo", this.getDetachedCriteria());
	}
	
	public HPCSystemDetachedCriteria createNameCriteria() {
		return new HPCSystemDetachedCriteria(createCriteria("name"));
	}
	
	public HPCSystem_CPU uniqueHPCSystem_CPU(PersistentSession session) {
		return (HPCSystem_CPU) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public HPCSystem_CPU[] listHPCSystem_CPU(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (HPCSystem_CPU[]) list.toArray(new HPCSystem_CPU[list.size()]);
	}
}

