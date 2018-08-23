package pathway.utils.workitems;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;




public class FilesFindWorkItemHandler implements WorkItemHandler {
    @Override
    public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        String path = (String)workItem.getParameter("Path");
        String[] suffixes = ((String)workItem.getParameter("Suffix")).split(",");
        Boolean recursive = (Boolean)workItem.getParameter("Recursive");

        Collection<File> files = new ArrayList<File>(0);
        if( new File(path).exists() )
            files = FileUtils.listFiles(new File(path), suffixes, recursive);

        Map<String, Object> results = new HashMap<String, Object>();
        results.put("Files", files);
        manager.completeWorkItem(workItem.getId(), results);
    }


    @Override
    public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
    }
}
