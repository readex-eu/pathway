package pathway.eclipse.views;

import java.util.ArrayList;
import java.util.Map;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import pathway.data.DataUtils;
import pathway.data.persistence.Experiment;




/** A composite that displays information about the currently selected experiment. */
class EnvironmentComposite extends Composite implements ExperimentComposite {
    @NonNullByDefault
    public EnvironmentComposite(Composite parent, int style) {
        super(parent, style);

        TableColumnLayout layout = new TableColumnLayout();
        setLayout(layout);
        this.variables = new TableViewer(this, SWT.SINGLE | SWT.V_SCROLL | SWT.BORDER);
        variables.getTable().getHorizontalBar().setVisible(false);
        variables.setContentProvider(ArrayContentProvider.getInstance());
        createColumns(variables, layout);
    }


    @Override
    public void setShownExperiment(Experiment experiment) {
        ArrayList<EnvironmentVariable> content = new ArrayList<EnvironmentComposite.EnvironmentVariable>();
        if( !experiment.getEnvironment().isEmpty() ) {
            Map<String, String> envMap = DataUtils.EnvironmentVars2Map(experiment.getEnvironment());
            for( String envVar: envMap.keySet() )
                content.add(new EnvironmentVariable(envVar, envMap.get(envVar)));
        }

        variables.setInput(content);
        layout();
    }


    private final TableViewer variables;


    @NonNullByDefault
    private static void createColumns(TableViewer viewer, TableColumnLayout layout) {
        TableViewerColumn viewerColumn1 = new TableViewerColumn(viewer, SWT.NONE);
        viewerColumn1.setLabelProvider(new VariableNameProvider());
        TableColumn column1 = viewerColumn1.getColumn();
        column1.setText("Variable");
        column1.setResizable(true);
        column1.setMoveable(true);
        column1.setAlignment(SWT.LEFT);
        layout.setColumnData(column1, new ColumnWeightData(3));

        TableViewerColumn viewerColumn2 = new TableViewerColumn(viewer, SWT.NONE);
        viewerColumn2.setLabelProvider(new VariableValueProvider());
        TableColumn column2 = viewerColumn2.getColumn();
        column2.setText("Value");
        column2.setResizable(true);
        column2.setMoveable(true);
        column2.setAlignment(SWT.LEFT);
        layout.setColumnData(column2, new ColumnWeightData(10));

        Table table = viewer.getTable();
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
    }


    @NonNullByDefault
    private static class EnvironmentVariable {
        public EnvironmentVariable(String name, String value) {
            this.name = name;
            this.value = value;
        }


        public final String name;
        public final String value;
    }


    private static class VariableNameProvider extends ColumnLabelProvider {
        @Override
        public String getText(Object element) {
            return ((EnvironmentVariable)element).name;
        }
    }


    private static class VariableValueProvider extends ColumnLabelProvider {
        @Override
        public String getText(Object element) {
            return ((EnvironmentVariable)element).value;
        }
    }
}
