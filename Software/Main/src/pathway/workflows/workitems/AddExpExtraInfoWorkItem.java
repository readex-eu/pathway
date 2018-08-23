package pathway.workflows.workitems;

import java.util.HashSet;
import java.util.Set;

import org.drools.process.instance.WorkItemHandler;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;

import pathway.data.persistence.Experiment;
import pathway.data.persistence.ExperimentAddInfo;

public class AddExpExtraInfoWorkItem implements WorkItemHandler {

	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		Experiment experiment = (Experiment) workItem.getParameter("Experiment");
		String parameter = (String) workItem.getParameter("Parameter");
		String value = (String) workItem.getParameter("Value");

		ExperimentAddInfo expAddInfoElem = new ExperimentAddInfo();
		expAddInfoElem.setName(parameter);
		expAddInfoElem.setValue(value);

		Set<ExperimentAddInfo> expAddInfo = experiment.getExperimentAddInfo();
		if (expAddInfo == null)
			expAddInfo = new HashSet<ExperimentAddInfo>();

		expAddInfo.add(expAddInfoElem);
		experiment.setExperimentAddInfo(expAddInfo);

		//		Map<String, Object> results = new HashMap<String, Object>();
		//		results.put("Experiment", experiment);
		manager.completeWorkItem(workItem.getId(), null);
	}

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		// Do nothing, this work item cannot be aborted
	}

}