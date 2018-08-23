package pathway.eclipse.remotetools;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchDelegate;




public class CustomLaunchConfiguration implements ILaunchConfiguration {
    @Override
    public boolean contentsEqual(ILaunchConfiguration configuration) {
        return false;
    }


    @Override
    public ILaunchConfigurationWorkingCopy copy(String name) throws CoreException {
        return null;
    }


    @Override
    public void delete() throws CoreException {
    }


    @Override
    public boolean exists() {
        return true;
    }


    @Override
    public Object getAdapter(Class adapter) {
        return null;
    }


    @Override
    public boolean getAttribute(String attributeName, boolean defaultValue) throws CoreException {
        Boolean value = (Boolean)store.get(attributeName);
        if( value == null ) {
            value = defaultValue;
        }
        return value;
    }


    @Override
    public int getAttribute(String attributeName, int defaultValue) throws CoreException {
        Integer value = (Integer)store.get(attributeName);
        if( value == null ) {
            value = defaultValue;
        }
        return value;
    }


    @Override
    @SuppressWarnings("rawtypes")
    public List getAttribute(String attributeName, List defaultValue) throws CoreException {
        List value = (List)store.get(attributeName);
        if( value == null ) {
            value = defaultValue;
        }
        return value;
    }


    @Override
    @SuppressWarnings("rawtypes")
    public Map getAttribute(String attributeName, Map defaultValue) throws CoreException {
        Map value = (Map)store.get(attributeName);
        if( value == null ) {
            value = defaultValue;
        }
        return value;
    }


    @Override
    @SuppressWarnings("rawtypes")
    public Set getAttribute(String attributeName, Set defaultValue) throws CoreException {
        Set value = (Set)store.get(attributeName);
        if( value == null ) {
            value = defaultValue;
        }
        return value;
    }


    @Override
    public String getAttribute(String attributeName, String defaultValue) throws CoreException {
        String value = (String)store.get(attributeName);
        if( value == null ) {
            value = defaultValue;
        }
        return value;
    }


    @Override
    public Map<String, Object> getAttributes() throws CoreException {
        return store;
    }


    @Override
    public String getCategory() throws CoreException {
        return null;
    }


    @Override
    public IFile getFile() {
        return null;
    }


    @Override
    public IPath getLocation() {
        return null;
    }


    @Override
    public IResource[] getMappedResources() throws CoreException {
        return null;
    }


    @Override
    public String getMemento() throws CoreException {
        return null;
    }


    @Override
    public Set<String> getModes() throws CoreException {
        return null;
    }


    @Override
    public String getName() {
        return null;
    }


    @Override
    public ILaunchDelegate getPreferredDelegate(Set<String> modes) throws CoreException {
        return null;
    }


    @Override
    public ILaunchConfigurationType getType() throws CoreException {
        return null;
    }


    @Override
    public ILaunchConfigurationWorkingCopy getWorkingCopy() throws CoreException {
        return null;
    }


    @Override
    public boolean hasAttribute(String attributeName) throws CoreException {
        return false;
    }


    @Override
    public boolean isLocal() {
        return false;
    }


    @Override
    public boolean isMigrationCandidate() throws CoreException {
        return false;
    }


    @Override
    public boolean isReadOnly() {
        return false;
    }


    @Override
    public boolean isWorkingCopy() {
        return false;
    }


    @Override
    public ILaunch launch(String mode, IProgressMonitor monitor) throws CoreException {
        return null;
    }


    @Override
    public ILaunch launch(String mode, IProgressMonitor monitor, boolean build) throws CoreException {
        return null;
    }


    @Override
    public ILaunch launch(String mode, IProgressMonitor monitor, boolean build, boolean register) throws CoreException {
        return null;
    }


    @Override
    public void migrate() throws CoreException {

    }


    @Override
    public boolean supportsMode(String mode) throws CoreException {
        return false;
    }


    private final Map<String, Object> store = new TreeMap<String, Object>();
}
