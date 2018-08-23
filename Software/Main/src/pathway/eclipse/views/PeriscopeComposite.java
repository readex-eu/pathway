package pathway.eclipse.views;

import java.util.ArrayList;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import pathway.data.persistence.Experiment;
import pathway.data.persistence.PscProperty;




/** A composite that displays information about the currently selected experiment. */
class PeriscopeComposite extends Composite implements ExperimentComposite {
    @NonNullByDefault
    public PeriscopeComposite(Composite parent, int style) {
        super(parent, style);

        TableColumnLayout layout = new TableColumnLayout();
        setLayout(layout);
        this.properties = new TableViewer(this, SWT.SINGLE | SWT.V_SCROLL | SWT.BORDER);
        properties.setContentProvider(ArrayContentProvider.getInstance());
        createColumns(properties, layout);
    }


    @Override
    public void setShownExperiment(Experiment experiment) {
        ArrayList<PscProperty> content = new ArrayList<PscProperty>();
        for( PscProperty property: experiment.getPscProperty() )
            content.add(property);

        properties.setInput(content);
        layout();
    }


    private final TableViewer properties;


    @NonNullByDefault
    private static void createColumns(TableViewer viewer, TableColumnLayout layout) {
        for( int i = 0; i != titles.length; ++i ) {
            final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
            viewerColumn.setLabelProvider(getLabelProvider(i));

            final TableColumn column = viewerColumn.getColumn();
            column.setText(titles[i]);
            column.setResizable(true);
            column.setMoveable(true);
            column.setAlignment(SWT.LEFT);

            layout.setColumnData(column, new ColumnWeightData(1));
        }

        final Table table = viewer.getTable();
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
    }


    private static CellLabelProvider getLabelProvider(int index) {
        switch( index ) {
        case 0:
            return new ColumnLabelProvider() {
                @Override
                public String getText(Object element) {
                    return ((PscProperty)element).getName();
                }
            };
        case 1:
            return new ColumnLabelProvider() {
                @Override
                public String getText(Object element) {
                    return ((PscProperty)element).getFileName();
                }
            };
        case 2:
            return new ColumnLabelProvider() {
                @Override
                public String getText(Object element) {
                    return ((PscProperty)element).getConfiguration();
                }
            };
        case 3:
            return new ColumnLabelProvider() {
                @Override
                public String getText(Object element) {
                    return ((PscProperty)element).getProcess().toString();
                }
            };
        case 4:
            return new ColumnLabelProvider() {
                @Override
                public String getText(Object element) {
                    return ((PscProperty)element).getThread().toString();
                }
            };
        case 5:
            return new ColumnLabelProvider() {
                @Override
                public String getText(Object element) {
                    return ((PscProperty)element).getSeverity().toString();
                }
            };
        case 6:
            return new ColumnLabelProvider() {
                @Override
                public String getText(Object element) {
                    return ((PscProperty)element).getRFL().toString();
                }
            };
        case 7:
            return new ColumnLabelProvider() {
                @Override
                public String getText(Object element) {
                    return ((PscProperty)element).getRegionType();
                }
            };
        default:
            return new ColumnLabelProvider();
        }
    }


    private static final String[] titles = { "Name", "File", "Config", "Proc", "Thread", "Severity", "RFL", "Region" };
}
