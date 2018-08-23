package pathway.data;

import java.util.HashSet;
import java.util.Set;

import pathway.data.persistence.PscProperty;
import pathway.data.persistence.PscRegion;

public class PscRegionBuilder implements Builder<PscRegion> {
	private final String ID;
	private Set<PscProperty> properties;
	
	private int startLine = 0;
	private int endLine = 0;
	private String name = "Default Region";
	private String sourceFileName;
	private String regionType;
	
	
	public PscRegionBuilder(String id){
		ID = id;
		properties = new HashSet<PscProperty>();
	}
	
	public PscRegionBuilder addProperty(PscProperty property) {
		properties.add(property);
		return this;
	}
	
	public PscRegionBuilder name(String regionName) {
		name = regionName;
		return this;
	}
	
	public PscRegionBuilder startLine(int startLine) {
		this.startLine = startLine;
		return this;
	}
	
	public PscRegionBuilder endLine(int endLine) {
		this.endLine = endLine;
		return this;
	}
	
	public PscRegionBuilder sourceFile(String sourceFileName) {
		this.sourceFileName = sourceFileName;
		return this;
	}
	
	public PscRegionBuilder regionType(String regionType) {
		this.regionType = regionType;
		return this;
	}
	
	@Override
	public PscRegion build() {
		PscRegion region = new PscRegion();
		region.setID(ID);
		region.setName(name);
		region.setStartLine(startLine);
		region.setEndLine(endLine);
		region.setSourceFile(sourceFileName);
		region.setType(regionType);	
		region.setPscProperty(properties);
		
		return region;
	}

}
