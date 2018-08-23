package pathway.workflows;

import java.util.ArrayList;
import java.util.List;
import org.drools.eclipse.debug.ProcessInstanceViewer2;
import org.drools.event.process.ProcessCompletedEvent;
import org.drools.event.process.ProcessEventListener;
import org.drools.event.process.ProcessNodeLeftEvent;
import org.drools.event.process.ProcessNodeTriggeredEvent;
import org.drools.event.process.ProcessStartedEvent;
import org.drools.event.process.ProcessVariableChangedEvent;
import org.drools.runtime.process.NodeInstance;
import org.drools.runtime.process.WorkflowProcessInstance;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.swt.widgets.Display;
import pathway.PAThWayPlugin;
import pathway.eclipse.UIUtils;




public class ProcessConsoleLogger implements ProcessEventListener {
    @Override
    public void beforeProcessStarted(ProcessStartedEvent event) {
        System.out.println("[ProcessStdLogger] Before Process started event received for node: " + event.getProcessInstance().getProcessName());
    }


    @Override
    public void afterProcessStarted(ProcessStartedEvent event) {
    }


    @Override
    public void beforeProcessCompleted(ProcessCompletedEvent event) {
    }


    @Override
    public void afterProcessCompleted(ProcessCompletedEvent event) {
        System.out.println("[ProcessStdLogger] After Process completed event received for node: " + event.getProcessInstance().getProcessName());
    }


    @Override
    public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {
        System.out.println("[ProcessStdLogger] Before Node triggered event received for node: " + event.getNodeInstance().getNodeName());
    }


    @Override
    public void afterNodeTriggered(ProcessNodeTriggeredEvent event) {
    }


    @Override
    public void beforeNodeLeft(final ProcessNodeLeftEvent event) {
        Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
                updateProcessInstanceView((WorkflowProcessInstance)event.getProcessInstance());
            }
        });
    }


    @Override
    public void afterNodeLeft(ProcessNodeLeftEvent event) {
        System.out.println("[ProcessStdLogger] After Node left event received for node: " + event.getNodeInstance().getNodeName());
    }


    @Override
    public void beforeVariableChanged(ProcessVariableChangedEvent event) {
    }


    @Override
    public void afterVariableChanged(ProcessVariableChangedEvent event) {
        System.out.println("[ProcessStdLogger] After Variable changed event received for node: " + event.getVariableId() + ": " + event.getOldValue() + " -> "
                + event.getNewValue());
    }


    @NonNullByDefault
    private static void updateProcessInstanceView(WorkflowProcessInstance processInstance) {
        ProcessInstanceViewer2 processInstanceView = (ProcessInstanceViewer2)UIUtils.findView(PAThWayPlugin.VIEW_PROCESS_INSTANCES);
        if( processInstanceView == null )
            return;

        List<String> nodeIds = new ArrayList<String>();
        for( NodeInstance node: processInstance.getNodeInstances() )
            nodeIds.add(String.valueOf(node.getNodeId()));

        processInstanceView.drawProcessInstance(processInstance.getProcessId(), nodeIds, null);
    }
}
