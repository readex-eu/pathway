package pathway.data;

import java.util.HashSet;
import java.util.Set;

import pathway.data.persistence.PscPropAddInfo;
import pathway.data.persistence.PscProperty;

public class PscPropAddInfoBuilder implements Builder<Set<PscPropAddInfo>> {
	private PscProperty parentProperty;
	private Set<PscPropAddInfo> addInfoEntries;
	
	public PscPropAddInfoBuilder(PscProperty parentProperty) {
		this.parentProperty = parentProperty;
		addInfoEntries = new HashSet<PscPropAddInfo>();
	}
	
	public PscPropAddInfoBuilder addEntry(String name, String value) {
		PscPropAddInfo addInfo = new PscPropAddInfo();
		addInfo.setName(name);
		addInfo.setValue(value);
		addInfo.setPscProperty(parentProperty);
		addInfoEntries.add(addInfo);
		
		return this;
	} 
	
	@Override
	public Set<PscPropAddInfo> build() {
		return addInfoEntries;
	}

}
