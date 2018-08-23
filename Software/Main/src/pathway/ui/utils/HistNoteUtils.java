package pathway.ui.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.orm.PersistentException;
import pathway.PAThWayPlugin;
import pathway.data.persistence.Experiment;
import pathway.data.persistence.HistoricalNotes;

public class HistNoteUtils {
	private static final String PATHWAY_NOTE_PREFIX = "pathway_note.";

	private HistNoteUtils()
	{
	}

	public static void openWikiEditor(Date noteDate)
	{
		DateTime date = new DateTime(noteDate);

		HistoricalNotes note = null;

		try {
			note = HistoricalNotes.loadHistoricalNotesByQuery(HistoricalNotes.PROP_NOTE_DATE + "= '" + date.toString("yyyy-MM-dd") + "'",
					HistoricalNotes.PROP_NOTE_DATE + " DESC");
		} catch (PersistentException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "There was an error while querying the note for " + date.toString("yyyy-MM-dd")  + " from the internal database:\n" + e.getLocalizedMessage(), "Error querying the internal database",
					JOptionPane.ERROR_MESSAGE);
		}

		if (note == null)
			createNewHistNoteWiki(date);

		try {
			openWikiEditor(note, false);
		} catch (Exception e) {
			ErrorDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error openning note",
					"There was an error while openning the note for " + date + "...", new Status(IStatus.ERROR, PAThWayPlugin.PLUGIN_ID, e.getMessage()));
			e.printStackTrace();
		}
	}

	public static void openWikiEditor(HistoricalNotes note) throws CoreException
	{
		openWikiEditor(note, false);
	}

	public static void openWikiEditor(HistoricalNotes note, boolean fullRefresh) throws CoreException
	{
		IWorkspace ws = ResourcesPlugin.getWorkspace();
		IProject project = ws.getRoot().getProject(PAThWayPlugin.PATHWAY_INTERNAL_PROJECT);
		if (!project.exists())
			project.create(null, IResource.HIDDEN, null);

		if (!project.isOpen())
			project.open(null);

		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		project.refreshLocal(IProject.DEPTH_INFINITE, null);

		IFolder histNotesFolder = project.getFolder(PAThWayPlugin.PATHWAY_PROJ_HISTNOTES_FOLDER);
		if (!histNotesFolder.exists())
			histNotesFolder.create(false, true, null);

		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		IFile file = histNotesFolder.getFile(PATHWAY_NOTE_PREFIX + df.format(note.getNoteDate()) + ".wiki");

		// Refresh the old copy with the data from the database
		if (fullRefresh)
			file.delete(true, null);

		if (!file.exists()) {
			InputStream is = null;
			is = new ByteArrayInputStream(formatNoteWiki(note).getBytes());
			file.create(is, true, null);
		}

		IEditorInput editorInput = new FileEditorInput(file);
		IWorkbenchPage page = window.getActivePage();

		if (page != null)
			page.openEditor(editorInput, PAThWayPlugin.VIEW_WIKI_EDITOR);
	}

	public static String formatNoteWiki(HistoricalNotes note)
	{
		StringBuilder sb = new StringBuilder();

		// Create a header for the document
		DateFormat df = new SimpleDateFormat("dd. MMMM yyyy");
		sb.append("'''").append("Experiments for ").append(df.format(note.getNoteDate())).append("'''");
		sb.append("\n");

		// List of experiments
		Set<Experiment> experiments = note.getExperiment();

		for (Experiment exp : experiments) {
			sb.append("*"); // level 1 buller list entry

			// ExperimentType:ParentExpID (with link Pathway:Exp:<ExpGUID>)
			sb.append("Experiment");
			sb.append(" - ");
			sb.append("\n");

			if (exp.getApplication() != null) {
				sb.append(" ").append("Application: ").append(exp.getApplication().getName()).append(" - ");
				sb.append(exp.getApplication().getConfig()).append("\n");
			}

			if (exp.getTool() != null)
				sb.append(" ").append("Tool: ").append(exp.getTool().getName()).append("\n");

			if (exp.getApplication() != null && exp.getApplication().getEclipseProject() != null && exp.getApplication().getEclipseProject().length() > 0)
				sb.append(" ").append("Project: ").append("Eclipse:/").append(exp.getApplication().getEclipseProject()).append("/").append("\n");

			sb.append("\n");
		}

		sb.append("----").append("\n"); // make 2 horizontal rules as a separator
		sb.append("----").append("\n");

		// Hist note data
		if (note.getNotes() != null)
			sb.append(note.getNotes());

		return sb.toString();
	}

	public static String extractNoteFromWiki(File wikiFile)
	{
		Scanner scanner;
		@SuppressWarnings("unused")
		String header = null;
		String userData = null;

		try {
			scanner = new Scanner(wikiFile);

			// Skip the Pathway Header
			scanner.useDelimiter("----\n----\n|\\Z");
			if (scanner.hasNext())
				header = scanner.next();

			if (scanner.hasNext())
				userData = scanner.next();
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return (userData != null ? userData : "");

	}

	public static void storeAllNotesToDB()
	{
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();

		IWorkspace ws = ResourcesPlugin.getWorkspace();
		IProject project = ws.getRoot().getProject(PAThWayPlugin.PATHWAY_INTERNAL_PROJECT);
		if (!project.exists()) {
			ErrorDialog.openError(shell, "No notes found", "No notes found in the local store", null);
			return;
		}

		if (!project.isOpen())
			try {
				project.open(null);
			} catch (CoreException e) {
				ErrorDialog.openError(shell, "Internal Error", "Exception while opening internal project", new Status(IStatus.ERROR,
						PAThWayPlugin.PLUGIN_ID, e.getMessage()));
				e.printStackTrace();
			}

		IFolder histNotesFolder = project.getFolder(PAThWayPlugin.PATHWAY_PROJ_HISTNOTES_FOLDER);
		if (!histNotesFolder.exists()) {
			ErrorDialog.openError(shell, "No notes found", "No notes found in the local store", null);
			return;
		}

		Collection<File> files = FileUtils.listFiles(histNotesFolder.getLocation().toFile(), new String[] { "wiki" }, false);
		for (File file : files) {
			String fileName = FilenameUtils.getBaseName(file.getName());
			String dateStamp = null;
			if (fileName.length() > PATHWAY_NOTE_PREFIX.length())
				dateStamp = fileName.substring(PATHWAY_NOTE_PREFIX.length());

			if (dateStamp == null) {
				System.err.println("Incorrect file name format for historical note: " + fileName);
				continue;
			}
			DateTime noteDate = DateTime.parse(dateStamp, DateTimeFormat.forPattern("yyyyMMdd"));

			HistoricalNotes histNote = null;
			try {
				histNote = HistoricalNotes.loadHistoricalNotesByQuery(HistoricalNotes.PROP_NOTE_DATE + "= '" + noteDate.toString("yyyy-MM-dd") + "'",
						HistoricalNotes.PROP_NOTE_DATE + " DESC");
			} catch (PersistentException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "There was an error while querying the note for " + noteDate.toString("yyyy-MM-dd")  + " from the internal database:\n" + e.getLocalizedMessage(), "Error querying the internal database",
						JOptionPane.ERROR_MESSAGE);
			}

			if (histNote == null) {
				histNote = HistoricalNotes.createHistoricalNotes();
				histNote.setNoteDate(noteDate.toDate());
			}

			String note = extractNoteFromWiki(file);
			histNote.setNotes(note);
			System.out.println("Will store: " + note);
			try {
				histNote.save();
			} catch (PersistentException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "There was an error while saving the note for " + noteDate.toString("yyyy-MM-dd")  + " to the internal database:\n" + e.getLocalizedMessage(), "Error saving to the internal database",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public static void storeSelNoteToDB(HistoricalNotes note)
	{
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();

		IWorkspace ws = ResourcesPlugin.getWorkspace();
		IProject project = ws.getRoot().getProject(PAThWayPlugin.PATHWAY_INTERNAL_PROJECT);
		if (!project.exists()) {
			ErrorDialog.openError(shell, "Note not found", "Note was not found in the local store", null);
			return;
		}

		if (!project.isOpen())
			try {
				project.open(null);
			} catch (CoreException e) {
				ErrorDialog.openError(shell, "Internal Error", "Exception while opening internal project", new Status(IStatus.ERROR,
						PAThWayPlugin.PLUGIN_ID, e.getMessage()));
				e.printStackTrace();
			}

		IFolder histNotesFolder = project.getFolder(PAThWayPlugin.PATHWAY_PROJ_HISTNOTES_FOLDER);
		if (!histNotesFolder.exists()) {
			ErrorDialog.openError(shell, "Note not found", "The note was not found in the local store", null);
			return;
		}

		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		IFile file = histNotesFolder.getFile(PATHWAY_NOTE_PREFIX + df.format(note.getNoteDate()) + ".wiki");

		if (!file.exists()) {
			ErrorDialog.openError(shell, "Note not found", "The note was not found in the local store", null);
			return;
		}

		String userData = extractNoteFromWiki(file.getLocation().toFile());

		try {
			note.refresh();
			note.setNotes(userData);
			note.save();
		} catch (PersistentException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "There was an error while saving the note for " + note.getNoteDate() + " to the internal database:\n" + e.getLocalizedMessage(), "Error saving to the internal database",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void createNewHistNoteWiki(DateTime date)
	{
		if (date == null) {
			do {
				String answer = JOptionPane.showInputDialog("Please enter a date for the new note (format: dd.MM.yyyy):",
						DateTime.now().toString("dd.MM.yyyy"));
				date = DateTime.parse(answer, DateTimeFormat.forPattern("dd.MM.yyyy"));
			} while (date == null);
		}

		HistoricalNotes note = null;
		try {
			note = HistoricalNotes.loadHistoricalNotesByQuery(HistoricalNotes.PROP_NOTE_DATE + "= '" + date.toString("yyyy-MM-dd") + "'",
					HistoricalNotes.PROP_NOTE_DATE + " DESC");
		} catch (PersistentException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "There was an error while querying the note for " + date.toString("yyyy-MM-dd")  + " from the internal database:\n" + e.getLocalizedMessage(), "Error querying the internal database",
					JOptionPane.ERROR_MESSAGE);
		}

		if (note == null) {
			note = HistoricalNotes.createHistoricalNotes();
			note.setNoteDate(date.toDate());
		}

		try {
			note.save();
		} catch (PersistentException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "There was an error while saving the note for " + note.getNoteDate() + " to the internal database:\n" + e.getLocalizedMessage(), "Error saving to the internal database",
					JOptionPane.ERROR_MESSAGE);
		}

		try {
			openWikiEditor(note, true);
		} catch (Exception  e) {
			ErrorDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error creating new note",
					"There was an error while creating a new note...", new Status(IStatus.ERROR, PAThWayPlugin.PLUGIN_ID, e.getMessage()));
			e.printStackTrace();
		}
	}

	public static List<HistoricalNotes> getAvailHistNotes()
	{
		List<HistoricalNotes> allNotes = new ArrayList<HistoricalNotes>();
		try {
			allNotes = HistoricalNotes.queryHistoricalNotes(null, HistoricalNotes.PROP_NOTE_DATE + " DESC");
		} catch (PersistentException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "There was an error while querying the available notes from the internal database:\n" + e.getLocalizedMessage(), "Error querying the internal database",
					JOptionPane.ERROR_MESSAGE);
		}
		return allNotes;
	}

	public static boolean overwriteAllNotesFromDB()
	{
		int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete all local notes?");
		if (answer != JOptionPane.YES_OPTION) {
			JOptionPane.showMessageDialog(null, "Update canceled!");
			return false;
		}

		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		IWorkspace ws = ResourcesPlugin.getWorkspace();
		IProject project = ws.getRoot().getProject(PAThWayPlugin.PATHWAY_INTERNAL_PROJECT);

		if (!project.exists())
			return false;
		if (!project.isOpen())
			try {
				project.open(null);
			} catch (CoreException e) {
				ErrorDialog.openError(shell, "Internal Error", "Exception while opening internal project", new Status(IStatus.ERROR,
						PAThWayPlugin.PLUGIN_ID, e.getMessage()));
				e.printStackTrace();
			}

		IFolder histNotesFolder = project.getFolder(PAThWayPlugin.PATHWAY_PROJ_HISTNOTES_FOLDER);
		if (!histNotesFolder.exists())
			return false;
		Collection<File> files = FileUtils.listFiles(histNotesFolder.getLocation().toFile(), new String[] { "wiki" }, false);
		for (File file : files)
			if (file.getName().startsWith(PATHWAY_NOTE_PREFIX))
				file.delete();

		return true;
	}
}

