package pathway.eclipse.views;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.themes.IThemeManager;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;

import pathway.PAThWayPlugin;
import pathway.data.persistence.HistoricalNotes;
import pathway.ui.utils.HistNoteUtils;

public class HistNotesTreeView extends ViewPart implements IPropertyChangeListener {

	private static final boolean traceOn = PAThWayPlugin.getTraceOption("/debug/histNotesView");

	protected TreeViewer viewer;
	private Action doubleClickAction;
	private Action displayNoteText;
	private Action refreshDisplayNoteText;
	private Action addNewNote;
	private Action storeNotesToDB;
	private Action storeSelNoteToDB;
	private Action overwriteAllFromDB;
	private Action refreshList;
	private MenuManager menuManager;

	List<HistoricalNotes> allNotes;

	class ViewContentProvider implements ITreeContentProvider {
		private final Object[] EMPTY_ARRAY = new Object[0];

		/**
		 * Returns the children of the current node.
		 * 
		 * Only the Unit and codeRegion nodes can have children.
		 * 
		 */
		@Override
		public Object[] getChildren(Object parentElement)
		{
			// Return an empty array - no children for now
			return this.EMPTY_ARRAY;
		}

		@Override
		public Object getParent(Object element)
		{
			return null;
		}

		/**
		 * Checks in the current element has child nodes.
		 * 
		 * Only the Unit and codeRegion nodes can have children.
		 * 
		 */
		@Override
		public boolean hasChildren(Object element)
		{
			return getChildren(element).length > 0;
		}

		@Override
		public Object[] getElements(Object inputElement)
		{
			if (allNotes == null)
				allNotes = HistNoteUtils.getAvailHistNotes();

			if (traceOn)
				System.out.println("[histNotesView] getElements: " + String.valueOf(allNotes.size()));

			return allNotes.toArray();
		}

		@Override
		public void dispose()
		{		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
		{		}
	}

	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		// Local image cache
		private Map<ImageDescriptor, Image> imageCache = new HashMap<ImageDescriptor, Image>(0);

		@Override
		public Image getImage(Object element) {

			if (element instanceof HistoricalNotes) {
				ImageDescriptor descriptor = null;
				HistoricalNotes note = (HistoricalNotes) element;

				if (DateTimeComparator.getDateOnlyInstance().compare(note.getNoteDate(), DateTime.now()) == 0)
				{
					descriptor = PAThWayPlugin.getImageDescriptor(PAThWayPlugin.ICONS_PATH + "arrowright.GIF");
				} else {
					descriptor = PAThWayPlugin.getImageDescriptor(PAThWayPlugin.ICONS_PATH + "archive.gif");
				}

				// obtain the cached image corresponding to the descriptor
				Image image = this.imageCache.get(descriptor);
				if (image == null) {
					image = descriptor.createImage();
					this.imageCache.put(descriptor, image);
				}
				return image;
			}
			return null;
		}

		@Override
		public void dispose()
		{
			super.dispose();
			for (Image image : this.imageCache.values()) {
				(image).dispose();
			}
			this.imageCache.clear();
		}

		@Override
		public String getText(Object element)
		{
			if (element instanceof HistoricalNotes) {
				DateFormat df = new SimpleDateFormat("dd. MMMM yyyy");
				return df.format( ((HistoricalNotes) element).getNoteDate() );
			} else if (element instanceof String) {
				return (String) element;
			}
			return "-";
		}

		@Override
		public Image getColumnImage(Object element, int columnIndex)
		{
			return getImage(element);
		}

		@Override
		public String getColumnText(Object element, int columnIndex)
		{
			return getText(element);
		}

	}

	@Override
	public void setFocus()
	{
		this.viewer.setInput(allNotes);
		this.viewer.getControl().setFocus();
		this.viewer.refresh();
		// this.viewer.expandToLevel(3);
	}

	@Override
	public void createPartControl(Composite parent)
	{
		allNotes = HistNoteUtils.getAvailHistNotes();

		Composite treeContainer = new Composite(parent, SWT.NONE);
		TreeColumnLayout layout = new TreeColumnLayout();
		treeContainer.setLayout(layout);

		this.viewer = new TreeViewer(treeContainer, SWT.SINGLE|SWT.FULL_SELECTION);
		Tree tree = viewer.getTree();
		tree.setHeaderVisible(true);

		TreeColumn expDateCol = new TreeColumn(tree, SWT.NONE);
		expDateCol.setText("Note's Date");
		expDateCol.setMoveable(false);
		layout.setColumnData(expDateCol, new ColumnWeightData(10));

		this.viewer.setContentProvider(new ViewContentProvider());
		this.viewer.setLabelProvider(new ViewLabelProvider());
		this.viewer.setInput(ResourcesPlugin.getWorkspace().getRoot());

		makeActions();
		hookDoubleClickAction();

		this.menuManager = new MenuManager();
		this.menuManager.setRemoveAllWhenShown(true);
		this.menuManager.createContextMenu(parent);
		this.menuManager.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager)
			{
				setupMenu();
			}
		});
		// Add the context menu
		this.viewer.getTree().setMenu(this.menuManager.getMenu());

		updateTheme();
		IThemeManager mgr = PlatformUI.getWorkbench().getThemeManager();
		mgr.addPropertyChangeListener(this);
	}

	private void makeActions()
	{
		makeDoubleClickAction();

		this.displayNoteText = new Action() {
			@Override
			public void run()
			{
				if (HistNotesTreeView.this.viewer.getSelection() instanceof IStructuredSelection) {
					HistoricalNotes note = (HistoricalNotes) ((IStructuredSelection) HistNotesTreeView.this.viewer.getSelection()).getFirstElement();
					if (traceOn)
						System.out.println("Will display note: " + note.getNotes());

					try {
						HistNoteUtils.openWikiEditor(note);
					} catch (Exception e) {
						ErrorDialog.openError(getViewSite().getShell(), "Error displaying note",
								"There was an error while displaying the selected note...", new Status(IStatus.ERROR, PAThWayPlugin.PLUGIN_ID, e.getMessage()));
						e.printStackTrace();
					}
				}
			}
		};
		this.displayNoteText.setText("Display Note");
		this.displayNoteText.setToolTipText("Get the note from the database and displays it in an editor.");

		this.refreshDisplayNoteText = new Action() {
			@Override
			public void run()
			{
				if (HistNotesTreeView.this.viewer.getSelection() instanceof IStructuredSelection) {
					HistoricalNotes note = (HistoricalNotes) ((IStructuredSelection) HistNotesTreeView.this.viewer.getSelection()).getFirstElement();
					if (traceOn)
						System.out.println("Will refresh and display note: " + note.getNotes());

					try {
						HistNoteUtils.openWikiEditor(note, true);
					} catch (Exception e) {
						ErrorDialog.openError(getViewSite().getShell(), "Error displaying note",
								"There was an error while displaying the selected note...", new Status(IStatus.ERROR, PAThWayPlugin.PLUGIN_ID, e.getMessage()));
						e.printStackTrace();
					}
				}
			}
		};
		this.refreshDisplayNoteText.setText("Refresh and Display Note");
		this.refreshDisplayNoteText.setToolTipText("Get the updated note from the database and displays it in an editor.");

		this.addNewNote = new Action() {
			@Override
			public void run()
			{
				HistNoteUtils.createNewHistNoteWiki(null);
				allNotes.clear();
				allNotes = HistNoteUtils.getAvailHistNotes();
				setFocus();
			}
		};
		this.addNewNote.setText("Add New Note");
		this.addNewNote.setToolTipText("Create a new note.");

		this.storeNotesToDB = new Action() {
			@Override
			public void run()
			{
				HistNoteUtils.storeAllNotesToDB();
				allNotes.clear();
				allNotes = HistNoteUtils.getAvailHistNotes();
				setFocus();
			}
		};
		this.storeNotesToDB.setText("Store All Notes to Database");
		this.storeNotesToDB.setToolTipText("Store all notes in the local store to the database.");

		this.storeSelNoteToDB = new Action() {
			@Override
			public void run()
			{
				if (HistNotesTreeView.this.viewer.getSelection() instanceof IStructuredSelection) {
					HistoricalNotes note = (HistoricalNotes) ((IStructuredSelection) HistNotesTreeView.this.viewer.getSelection()).getFirstElement();
					if (traceOn)
						System.out.println("Will store note: " + note.getNotes());

					HistNoteUtils.storeSelNoteToDB(note);
				}
			}
		};
		this.storeSelNoteToDB.setText("Store Selected Note to Database");
		this.storeSelNoteToDB.setToolTipText("Store selected note to the database.");

		this.overwriteAllFromDB = new Action() {
			@Override
			public void run()
			{
				if (HistNoteUtils.overwriteAllNotesFromDB())
				{
					allNotes.clear();
					allNotes = HistNoteUtils.getAvailHistNotes();
					setFocus();
				}
			}
		};
		this.overwriteAllFromDB.setText("Overwrite from Database");
		this.overwriteAllFromDB.setToolTipText("Remove all notes in the local store and get the all from the database.");

		this.refreshList = new Action() {
			@Override
			public void run()
			{
				allNotes.clear();
				allNotes = HistNoteUtils.getAvailHistNotes();
				setFocus();
			}
		};
		this.refreshList.setText("Refresh notes list");
		this.refreshList.setToolTipText("Refresh the list of notes from the database.");
	}

	/**
	 * Make double-click action, which moves editor to the artifact instance in the source code (editor to line in source code)
	 * 
	 */
	private void makeDoubleClickAction()
	{

		this.doubleClickAction = new Action() {
			@Override
			public void run()
			{
				ISelection selection = HistNotesTreeView.this.viewer.getSelection();
				Object obj = ((IStructuredSelection) selection).getFirstElement();
				HistoricalNotes note = (HistoricalNotes) obj;

				if (traceOn)
					System.out.println("Will display note: " + note.getNotes());

				try {
					HistNoteUtils.openWikiEditor(note);
				} catch (Exception e) {
					ErrorDialog.openError(getViewSite().getShell(), "Error displaying note",
							"There was an error while displaying the selected note...", new Status(IStatus.ERROR, PAThWayPlugin.PLUGIN_ID, e.getMessage()));
					e.printStackTrace();
				}
			}
		};
	}

	private void hookDoubleClickAction()
	{
		this.viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event)
			{
				HistNotesTreeView.this.doubleClickAction.run();
			}
		});
	}

	private void setupMenu()
	{
		this.menuManager.add(this.displayNoteText);
		this.menuManager.add(this.refreshDisplayNoteText);
		this.menuManager.add(new Separator());
		this.menuManager.add(this.addNewNote);
		this.menuManager.add(new Separator());
		this.menuManager.add(this.storeSelNoteToDB);
		this.menuManager.add(this.storeNotesToDB);
		this.menuManager.add(this.overwriteAllFromDB);
		this.menuManager.add(this.refreshList);
	}

	public void updateTheme()
	{
		//		IThemeManager themeManager = PlatformUI.getWorkbench().getThemeManager();
		//		Font viewFont = themeManager.getCurrentTheme().getFontRegistry().get(PAThWayPlugin.SIR_FONT_ID);
		//		viewer.getControl().setFont(viewFont);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event)
	{
		updateTheme();
	}

	@Override
	public void dispose()
	{
		IThemeManager mgr = PlatformUI.getWorkbench().getThemeManager();
		mgr.removePropertyChangeListener(this);
		super.dispose();
	}

}
