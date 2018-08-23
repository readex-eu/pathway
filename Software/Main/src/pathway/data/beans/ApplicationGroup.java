package pathway.data.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.criterion.Restrictions;

import pathway.data.beans.ExperimentGroups.GROUP_BY;
import pathway.data.persistence.Application;
import pathway.data.persistence.ApplicationCriteria;
import pathway.data.persistence.Experiment;
import pathway.workflows.WorkflowErrorException;

public class ApplicationGroup extends AbstractUIModelObject {
	private List<Application> m_applications = new ArrayList<Application>();
	
    public ApplicationGroup() {
    	
    	try{
    		ApplicationCriteria appCriteria = new ApplicationCriteria();
    		for( Application application: appCriteria.listApplication() ) {
    			m_applications.add(application);        
    		}            	
    	}
    	catch( Exception ex ) {
    		throw new WorkflowErrorException();
    	}	
    }
    
	public void refresh() {
		
		try{
			for( Application application: m_applications ) {
				application.refresh();        
			} 
			
		}
		catch( Exception ex ) {
			throw new WorkflowErrorException();
		}	
	}
	
	public Application getApplication(String nameGroup){
        for( Application application: m_applications ) {
        	if (application.getName() == nameGroup)
        		return application;

        } 
        return null;
		
	}
}


