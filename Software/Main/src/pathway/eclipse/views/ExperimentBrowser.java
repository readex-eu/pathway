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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;
import pathway.data.beans.ExperimentGroup;
import pathway.data.beans.ExperimentGroups;
import pathway.data.beans.ExperimentGroups.GROUP_BY;
import pathway.data.persistence.Experiment;




/** Implements a view that shows all performed experiments. */
public class ExperimentBrowser extends ViewPart {
    @Override
    public void createPartControl(Composite parent) {
        GridLayout layout = new GridLayout();
        layout.marginHeight = 5;
        layout.marginWidth = 5;
        layout.numColumns = 2;
        layout.horizontalSpacing = 10;
        layout.verticalSpacing = 10;
        parent.setLayout(layout);

        createGroupMenu(parent);
        experimentTable = createExperimentComposite(parent);
        createGroupTable(parent);

        updateGroupList();
    }


    @Override
    public void setFocus() {
    }


    /** Updates all displayed data from the database. */
    public void reloadData() {
        // update information from the database
        experimentGroups.refresh();

        // update tables and restore selection
        int groupIndex = groupTable.getTable().getSelectionIndex();
        int experimentIndex = experimentTable.getSelection();
        updateGroupList();
        groupTable.getTable().setSelection(groupIndex);
        IStructuredSelection group = (IStructuredSelection)groupTable.getSelection();
        updateExperimentList((ExperimentGroup)group.getFirstElement());
        experimentTable.setSelection(experimentIndex);
    }


    /** Sets the grouping of experiments, as if the user had selected it. */
    public void setGrouping(GROUP_BY grouping) {
        groupMenu.setText(grouping.toString());
    }


    /** Gets the experiment grouping that is currently selected by the user. */
    @NonNullByDefault
    private GROUP_BY getActiveGrouping() {
        return GROUP_BY.values()[groupMenu.getSelectionIndex()];
    }


    /** Updates the contents of the groups table. */
    private void updateGroupList() {
        experimentGroups.setExpGroupBy((GROUP_BY)groupMenu.getData());
        groupTable.setInput(experimentGroups.getGroups());
    }


    /** Updates the experiment list in this view. */
    private void updateExperimentList(@Nullable ExperimentGroup group) {
        experimentTable.setInput(group == null ? null : group.getExperiments().toArray(new Experiment[0]));
    }


    @NonNullByDefault
    private void createGroupMenu(Composite parent) {
        groupMenu = new Combo(parent, SWT.DROP_DOWN);
        for( GROUP_BY group: GROUP_BY.values() )
            groupMenu.add(group.toString());

        groupMenu.select(0);
        groupMenu.setData(GROUP_BY.values()[0]);
        groupMenu.addModifyListener(new GroupingListener());
        groupMenu.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, false, false));
    }


    @NonNullByDefault
    private void createGroupTable(Composite parent) {
        assert groupTable == null;
        Composite composite = createGroupComposite(parent);
        composite.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, true));

        groupTable = new TableViewer(composite, SWT.SINGLE | SWT.V_SCROLL | SWT.BORDER);
        createGroupColumn(groupTable);
        groupTable.setContentProvider(ArrayContentProvider.getInstance());
        groupTable.addPostSelectionChangedListener(new GroupSelectionListener());
    }


    private final ExperimentGroups experimentGroups = new ExperimentGroups();
    private ExperimentTable experimentTable;
    private Combo groupMenu;
    private TableViewer groupTable;


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


    @NonNullByDefault
    private static ExperimentTable createExperimentComposite(Composite parent) {
        ExperimentTable table = new ExperimentTable(parent, SWT.NONE);

        GridData data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        data.grabExcessHorizontalSpace = true;
        data.grabExcessVerticalSpace = true;
        data.verticalSpan = 2;
        table.setLayoutData(data);

        return table;
    }


    @NonNullByDefault
    private static void createGroupColumn(TableViewer viewer) {
        final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
        viewerColumn.setLabelProvider(new GroupLabelProvider());
        final TableColumn column = viewerColumn.getColumn();
        column.setText("Value");
        column.setWidth(100);
        column.setToolTipText(null);
        column.setResizable(false);
        column.setMoveable(false);
        column.setAlignment(SWT.LEFT);

        final Table table = viewer.getTable();
        table.setHeaderVisible(false);
        table.setLinesVisible(true);
    }


    private static class GroupLabelProvider extends ColumnLabelProvider {
        @Override
        public String getText(Object element) {
            return ((ExperimentGroup)element).getName();
        }
    }


    private class GroupingListener implements ModifyListener {
        @Override
        public void modifyText(ModifyEvent evt) {
            groupMenu.setData(getActiveGrouping());
            updateGroupList();
        }
    }


    private class GroupSelectionListener implements ISelectionChangedListener {
        @Override
        public void selectionChanged(SelectionChangedEvent event) {
        	reloadData();
        	IStructuredSelection selection = (IStructuredSelection)event.getSelection();
            ExperimentGroup group = (ExperimentGroup)selection.getFirstElement();
            updateExperimentList(group);
        }
    }
}
