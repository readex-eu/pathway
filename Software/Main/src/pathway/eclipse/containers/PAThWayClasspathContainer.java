package pathway.eclipse.containers;

import java.io.IOException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import pathway.PAThWayPlugin;




/** Contains the class-paths to Pathway library. All Pathway projects need to reference this library, in order to its work items in the editor. */
public class PAThWayClasspathContainer implements IClasspathContainer {
    public final static Path ID = new Path("PATHWAY");


    public PAThWayClasspathContainer(IJavaProject project, IPath path) {
        javaProject = null;
        javaProject = project;
        this.containerPath = path;
    }


    @Override
    public IClasspathEntry[] getClasspathEntries() {
        return getPathwayLib(javaProject);
    }


    @Override
    public String getDescription() {
        return PAThWayClasspathContainer.description;
    }


    @Override
    public int getKind() {
        return IClasspathEntry.CPE_CONTAINER;
    }


    @Override
    public IPath getPath() {
        return containerPath;
    }


    @Override
    public String toString() {
        return getDescription();
    }


    private static IClasspathEntry[] getPathwayLib(IJavaProject project) {
        IPath resourcesFolder;
        try {
            resourcesFolder = new Path(FileLocator.resolve(PAThWayPlugin.getDefault().getBundle().getResource("resources")).getPath());
            if( traceOn )
                System.out.println("[classpathContainer] Adding '" + resourcesFolder.toOSString() + "' to project '" + project.getProject().getName() + "'");

            IClasspathEntry[] entryArray = new IClasspathEntry[] { JavaCore.newLibraryEntry(resourcesFolder, null, null) };
            return entryArray;
        }
        catch( IOException ex ) {
            ex.printStackTrace();
            return new IClasspathEntry[0];
        }
    }


    private static final String description = "Pathway Extensions for jBPM";
    private static final boolean traceOn = PAThWayPlugin.getTraceOption("/debug/classpathContainer");

    private IPath containerPath;
    private IJavaProject javaProject;
}
