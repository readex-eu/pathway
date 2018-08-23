package pathway.eclipse.views;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PartInitException;
import org.joda.time.format.DateTimeFormat;
import pathway.PAThWayPlugin;
import pathway.data.persistence.Experiment;
import pathway.eclipse.UIUtils;




/** Displays the provided experiments in a nice table. */
class ExperimentTable extends Composite {
    public ExperimentTable(Composite parent, int style) {
        super(parent, style);

        TableColumnLayout layout = new TableColumnLayout();
        setLayout(layout);

        table = new Table(this, SWT.SINGLE | SWT.V_SCROLL | SWT.BORDER | SWT.VIRTUAL);
        dataProvider = new SetDataListener(table);
        table.addListener(SWT.SetData, dataProvider);
        table.addSelectionListener(new ExperimentSelectionListener());
        table.getVerticalBar().setVisible(true);
        createExperimentColumns(table, layout);
    }


    public void setInput(@Nullable Experiment[] input) {
        dataProvider.setData(input);

        table.setRedraw(false);
        for( TableItem item: table.getItems() )
            dataProvider.updateRow(item);
        table.setRedraw(true);
    }


    public int getSelection() {
        return table.getSelectionIndex();
    }


    public void setSelection(int index) {
        table.setSelection(index);
    }


    private final Table table;
    private final SetDataListener dataProvider;


    @NonNullByDefault
    private static void createExperimentColumns(Table table, TableColumnLayout layout) {
        for( int i = 0; i != titles.length; ++i ) {
            TableColumn column = new TableColumn(table, SWT.NONE);
            column.setText(titles[i]);
            column.setResizable(true);
            column.setMoveable(true);
            column.setAlignment(SWT.LEFT);

            layout.setColumnData(column, new ColumnWeightData(weights[i]));
        }

        table.setHeaderVisible(true);
        table.setLinesVisible(true);
    }


    private static void updateExperimentData(@Nullable Experiment experiment) {
        ExperimentDetails view = (ExperimentDetails)UIUtils.findView(PAThWayPlugin.VIEW_EXPERIMENT_DETAILS);
        if( view == null )
            return;

        view.setShownExperiment(experiment);
    }


    private static final String[] titles = { "Time", "HPC System", "Application", "MPI Processes", "OMP Threads", "Performance Tool", "Job status" };
    private static final int[] weights = { 4, 2, 2, 1, 1, 2, 2 };


    private class ExperimentSelectionListener implements SelectionListener {
        @Override
        public void widgetSelected(SelectionEvent e) {
            int index = table.getSelectionIndex();
            Experiment experiment = dataProvider.data[index];
            updateExperimentData(experiment);
        }


        @Override
        public void widgetDefaultSelected(SelectionEvent e) {
            int index = table.getSelectionIndex();
            Experiment experiment = dataProvider.data[index];

            try {
                ExperimentDetails view = (ExperimentDetails)UIUtils.getView(PAThWayPlugin.VIEW_EXPERIMENT_DETAILS);
                view.setShownExperiment(experiment);
            }
            catch( PartInitException ex ) {
                ex.printStackTrace();
                UIUtils.showErrorMessage("Unable to display experiment details.", ex);
            }
        }
    }


    private static final class SetDataListener implements Listener {
        public SetDataListener(Table table) {
            this.table = table;
        }


        @Override
        public void handleEvent(Event event) {
            TableItem item = (TableItem)event.item;
            updateRow(item);
        }


        public void updateRow(TableItem item) {
            Experiment experiment = data[table.indexOf(item)];

            item.setText(0, DateTimeFormat.longDateTime().print(experiment.getExpDate().getTime()));
            item.setText(1, experiment.getHPCSystem().getName());
            item.setText(2, experiment.getApplication().getName());
            item.setText(3, Integer.toString(experiment.getMpiProcs()));
            item.setText(4, Integer.toString(experiment.getOmpThreads()));
            item.setText(5, experiment.getTool() == null ? "(none)" : experiment.getTool().getName());
            item.setText(6, experiment.getJobState() == null ? "" : experiment.getJobState());
        }


        public void setData(@Nullable Experiment[] data) {
            if( data == null )
                data = new Experiment[0];

            this.data = data;
            table.setItemCount(data.length);
        }


        private final Table table;
        private Experiment[] data;
    }
}
