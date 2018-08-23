package pathway.eclipse.views;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.orm.PersistentException;

import pathway.data.beans.ApplicationGroup;
import pathway.data.beans.ExperimentGroup;
import pathway.data.beans.ExperimentGroups;
import pathway.data.beans.ExperimentGroups.GROUP_BY;
import pathway.data.persistence.Application;
import pathway.data.persistence.ApplicationCriteria;
import pathway.data.persistence.Experiment;
import pathway.data.persistence.ExperimentCriteria;
import pathway.eclipse.ExecutionLog;
import pathway.eclipse.UIUtils;
import pathway.workflows.WorkflowErrorException;


/** Implements a view that shows all performed experiments. */
public class ReadexBrowser extends ViewPart {
    @Override
    public void createPartControl(Composite parent) {
        parent.setLayout(new FillLayout());
        
        ScrolledComposite sc = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
        Composite child = new Composite(sc, SWT.NONE);
        
        GridLayout layout = new GridLayout();
        layout.marginHeight = 5;
        layout.marginWidth = 5;
        layout.numColumns = 2;
        layout.horizontalSpacing = 10;
        layout.verticalSpacing = 10;
        child.setLayout(layout);
       
        createGroupTable(child, (2*7));
        readexExperimentTable = createExperimentComposite(child);
        readexInterPhaseTable = createInterPhaseComposite (child);
        readexIntraPhaseTable = createIntraPhaseComposite (child);
        dtaTuningModelText = createText(child, "DTA Tuning Model");
        
        sc.setContent(child);
        sc.setMinSize(1024, 1024);
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        updateGroupList();

    }


    @Override
    public void setFocus() {
    }


    /** Updates all displayed data from the database. 
     * @throws PersistentException */
    public void reloadData() {
        // update information from the database
        experimentGroups.refresh();
    }

    /** Updates the contents of the groups table. */
    private void updateGroupList() {
    	experimentGroups.setExpGroupBy(GROUP_BY.APPLICATION);
        groupTable.setInput(experimentGroups.getGroups());
    }

    /** Updates the experiment list in this view. */
    private void updateExperimentList(@Nullable ExperimentGroup group)
    {	
    	if (group==null)
    		readexExperimentTable.setInput(null,readexIntraPhaseTable,readexInterPhaseTable,dtaTuningModelText);
    	else
    		readexExperimentTable.setInput(group.getExperiments().toArray(new Experiment[0]),readexIntraPhaseTable,readexInterPhaseTable,dtaTuningModelText);

    }

    @NonNullByDefault
    private void createGroupTable(Composite parent, int n) {
        assert groupTable == null;
        Composite composite = createGroupComposite(parent);
        GridData data = new GridData();
        data.horizontalAlignment = SWT.BEGINNING;
        data.verticalAlignment = SWT.FILL;
        data.grabExcessHorizontalSpace = false;
        data.grabExcessVerticalSpace = true;
        data.verticalSpan = n;
        composite.setLayoutData(data);

        groupTable = new TableViewer(composite, SWT.SINGLE | SWT.V_SCROLL | SWT.BORDER);
        createGroupColumn(groupTable);
        groupTable.setContentProvider(ArrayContentProvider.getInstance());
        groupTable.addPostSelectionChangedListener(new GroupSelectionListener());
       
    }

    private ReadexIntraPhaseTable readexIntraPhaseTable;
    private ReadexInterPhaseTable readexInterPhaseTable;
    private ReadexExperimentTable readexExperimentTable;
    private final ExperimentGroups experimentGroups = new ExperimentGroups();
    private TableViewer groupTable;
    private Text dtaTuningModelText;
   
    @NonNullByDefault
    private static Composite createGroupComposite(Composite parent) {
        Composite composite = new Composite(parent, 0);
        composite.setLayout(new FillLayout());

        GridData data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        data.grabExcessHorizontalSpace = true;
        data.grabExcessVerticalSpace = true;
        composite.setLayoutData(data);

        return composite;
    }

    
    private static Text createText(Composite parent, String label)
    {
    	Label l = new Label(parent, SWT.NONE);
    	l.setText(label + ":");
    	Text t = new Text(parent, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
    	t.setEditable(false);
    	t.setVisible(true);

    	GridData data = new GridData();
    	data.horizontalAlignment = GridData.FILL;
    	data.verticalAlignment = GridData.FILL;
    	data.grabExcessHorizontalSpace = true;
    	data.grabExcessVerticalSpace = true;
    	data.heightHint = 644;
    	data.verticalSpan = 1;
    	
    	t.setLayoutData(data);
    	t.setText("Refresh clicking on the left Application button ");  	
    
		return t;
    }
    
  
    @NonNullByDefault
    private static ReadexInterPhaseTable createInterPhaseComposite(Composite parent) {
        
    	Label l = new Label(parent, SWT.NONE);
    	l.setText("Inter-Phase Dynamism" + ":");
    	
    	ReadexInterPhaseTable table = new ReadexInterPhaseTable(parent, SWT.NONE);

        GridData data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        data.grabExcessHorizontalSpace = true;
        data.heightHint = 100;
        data.grabExcessVerticalSpace = true;
        data.verticalSpan = 1;
        table.setLayoutData(data);

        return table;
    }
  
    @NonNullByDefault
    private static ReadexIntraPhaseTable createIntraPhaseComposite(Composite parent) {
        
    	Label l = new Label(parent, SWT.NONE);
    	l.setText("Intra-Phase Dynamism" + ":");
    	
    	ReadexIntraPhaseTable table = new ReadexIntraPhaseTable(parent, SWT.NONE);

        GridData data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        data.grabExcessHorizontalSpace = true;
        data.heightHint = 140;
        data.grabExcessVerticalSpace = true;
        data.verticalSpan = 1;
        table.setLayoutData(data);

        return table;
    }
   
    
    @NonNullByDefault
    private static ReadexExperimentTable createExperimentComposite(Composite parent) {
    	
        ReadexExperimentTable table = new ReadexExperimentTable(parent, SWT.NONE);

        GridData data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        data.grabExcessHorizontalSpace = true;
        data.heightHint = 140;
        data.grabExcessVerticalSpace = true;
        data.verticalSpan = 1;
        table.setLayoutData(data);

        return table;
    }


    @NonNullByDefault
    private static void createGroupColumn(TableViewer viewer) {
        final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
        viewerColumn.setLabelProvider(new GroupLabelProvider());
        final TableColumn column = viewerColumn.getColumn();
        column.setText("Applications");
        column.setWidth(100);
        column.setToolTipText(null);
        column.setResizable(false);
        column.setMoveable(false);
        column.setAlignment(SWT.LEFT);

        final Table table = viewer.getTable();
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
    }


    private static class GroupLabelProvider extends ColumnLabelProvider {
        @Override
        public String getText(Object element) {
            return ((ExperimentGroup)element).getName();
        }
    }
    
    private class GroupSelectionListener implements ISelectionChangedListener {
    @Override
    public void selectionChanged(SelectionChangedEvent event) {
    	reloadData();
    	updateGroupList();
        IStructuredSelection selection = (IStructuredSelection)event.getSelection();
        ExperimentGroup group = (ExperimentGroup)selection.getFirstElement();            
        updateExperimentList(group);
        
    	}
	}
}