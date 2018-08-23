package pathway.ui.dialogs.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import com.google.gson.JsonSyntaxException;
import pathway.data.DataUtils;
import pathway.eclipse.UIUtils;




/** A composite that displays the environment table for a specific tool configuration. */
class ToolEnvironmentTable extends Composite {
    @NonNullByDefault
    public ToolEnvironmentTable(Composite parent, int style) {
        super(parent, style);

        setLayout(new GridLayout(2, false));

        Composite tableComposite = new Composite(this, SWT.NONE);
        tableComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        TableColumnLayout layout = new TableColumnLayout();
        tableComposite.setLayout(layout);
        envVarsTable = new TableViewer(tableComposite);
        envVarsTable.setContentProvider(envVars);
        createColumns(envVarsTable, layout);

        Composite buttonComposite = new Composite(this, SWT.NONE);
        buttonComposite.setLayoutData(new GridData(SWT.NONE, SWT.FILL, false, true));
        buttonComposite.setLayout(new GridLayout(1, false));
        Button addButton = new Button(buttonComposite, SWT.PUSH);
        addButton.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
        addButton.setText("Add");
        addButton.addSelectionListener(new AddButtonListener());
        Button removeButton = new Button(buttonComposite, SWT.PUSH);
        removeButton.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
        removeButton.setText("Remove");
        removeButton.addSelectionListener(new RemoveButtonListener());
    }


    public String getSerializedEnvVars() {
        return envVars.getSerializedForm();
    }


    public void setSerializedEnvVars(String input) {
        envVarsTable.setInput(input);
    }


    private final EnvVarProvider envVars = new EnvVarProvider();
    private final TableViewer envVarsTable;


    @NonNullByDefault
    private static void createColumns(TableViewer viewer, TableColumnLayout layout) {
        final TableViewerColumn viewerColumn1 = new TableViewerColumn(viewer, SWT.NONE);
        viewerColumn1.setLabelProvider(new EnvVarNameProvider());
        viewerColumn1.setEditingSupport(new NameEditingSupport(viewer));
        final TableColumn column1 = viewerColumn1.getColumn();
        column1.setResizable(true);
        column1.setAlignment(SWT.LEFT);
        layout.setColumnData(column1, new ColumnWeightData(2));

        final TableViewerColumn viewerColumn2 = new TableViewerColumn(viewer, SWT.NONE);
        viewerColumn2.setLabelProvider(new EnvVarValueProvider());
        viewerColumn2.setEditingSupport(new ValueEditingSupport(viewer));
        final TableColumn column2 = viewerColumn2.getColumn();
        column2.setResizable(true);
        column2.setAlignment(SWT.LEFT);
        layout.setColumnData(column2, new ColumnWeightData(3));
    }


    /** Represents an environment variable. */
    @NonNullByDefault
    private static class EnvVar {
        public EnvVar(String name, String value) {
            this.name = name;
            this.value = value;
        }


        public String name;
        public String value;
    }


    /** Provides environment variables for the table viewer. */
    private static class EnvVarProvider implements IStructuredContentProvider {
        @Override
        public void dispose() {
        }


        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            final String input = (String)newInput;
            entries.clear();
            if( input == null )
                return;

            try {
                Map<String, String> map = DataUtils.EnvironmentVars2Map(input);
                
                for( Entry<String, String> entry: map.entrySet() )
                    entries.add(new EnvVar(entry.getKey(), entry.getValue()));
            }
            catch( JsonSyntaxException ex ) {
                ex.printStackTrace();
                UIUtils.showErrorMessage("Unable to load the environment variables for this tool. The environment has been reset.");
            }
        }


        @Override
        public Object[] getElements(Object inputElement) {
            return entries.toArray();
        }


        public String getSerializedForm() {
            TreeMap<String, String> map = new TreeMap<String, String>();
            for( EnvVar entry: entries )
                map.put(entry.name, entry.value);

            return DataUtils.EnvironmentVars2Str(map);
        }


        public EnvVar addNew() {
            EnvVar envVar = new EnvVar("VARIABLE", "value");
            entries.add(envVar);
            return envVar;
        }


        public void remove(EnvVar var) {
            entries.remove(var);
        }


        private final ArrayList<EnvVar> entries = new ArrayList<EnvVar>();
    }


    private static class EnvVarNameProvider extends ColumnLabelProvider {
        @Override
        public String getText(Object element) {
            EnvVar entry = (EnvVar)element;
            return entry.name;
        }
    }


    private static class EnvVarValueProvider extends ColumnLabelProvider {
        @Override
        public String getText(Object element) {
            EnvVar entry = (EnvVar)element;
            return entry.value;
        }
    }


    private static abstract class EditingSupportBase extends EditingSupport {
        public EditingSupportBase(TableViewer viewer) {
            super(viewer);
            this.editor = new TextCellEditor(viewer.getTable());
        }


        @Override
        protected CellEditor getCellEditor(Object element) {
            return editor;
        }


        @Override
        protected boolean canEdit(Object element) {
            return true;
        }


        private final CellEditor editor;
    }


    private static class NameEditingSupport extends EditingSupportBase {
        public NameEditingSupport(TableViewer viewer) {
            super(viewer);
        }


        @Override
        protected Object getValue(Object element) {
            return ((EnvVar)element).name;
        }


        @Override
        protected void setValue(Object element, Object userInputValue) {
            if( userInputValue == null )
                throw new IllegalArgumentException();

            String newName = (String)userInputValue;
            if( !containsOnlyLetters(newName) )
                UIUtils.showErrorMessage("A variable name may only contain letters, digits and underscores.");
            else {
                ((EnvVar)element).name = newName;
                getViewer().update(element, null);
            }
        }


        private static boolean containsOnlyLetters(String name) {
            char[] chars = name.toCharArray();
            for( char c: chars )
                if( !Character.isLetter(c) && !Character.isDigit(c) && c != '_' )
                    return false;

            return true;
        }
    }


    private static class ValueEditingSupport extends EditingSupportBase {
        public ValueEditingSupport(TableViewer viewer) {
            super(viewer);
        }


        @Override
        protected Object getValue(Object element) {
            return ((EnvVar)element).value;
        }


        @Override
        protected void setValue(Object element, Object userInputValue) {
            if( userInputValue == null )
                throw new IllegalArgumentException();

            ((EnvVar)element).value = (String)userInputValue;
            getViewer().update(element, null);
        }
    }


    private class AddButtonListener implements SelectionListener {
        @Override
        public void widgetSelected(SelectionEvent e) {
            EnvVar var = envVars.addNew();
            envVarsTable.refresh();
            envVarsTable.editElement(var, 0);
        }


        @Override
        public void widgetDefaultSelected(SelectionEvent e) {
            widgetSelected(e);
        }
    }


    private class RemoveButtonListener implements SelectionListener {
        @Override
        public void widgetSelected(SelectionEvent e) {
            StructuredSelection selection = (StructuredSelection)envVarsTable.getSelection();
            Iterator<EnvVar> iterator = selection.iterator();

            while( iterator.hasNext() )
                envVars.remove(iterator.next());

            envVarsTable.refresh();
        }


        @Override
        public void widgetDefaultSelected(SelectionEvent e) {
            widgetSelected(e);
        }
    }
}
