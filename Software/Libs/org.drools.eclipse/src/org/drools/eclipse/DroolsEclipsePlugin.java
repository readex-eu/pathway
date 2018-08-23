/*
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.eclipse;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.drools.builder.CompositeKnowledgeBuilder;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.impl.KnowledgeBuilderImpl;
import org.drools.compiler.DrlParser;
import org.drools.compiler.DroolsError;
import org.drools.compiler.DroolsParserException;
import org.drools.compiler.PackageBuilder;
import org.drools.compiler.PackageBuilderConfiguration;
import org.drools.compiler.PackageRegistry;
import org.drools.core.util.StringUtils;
import org.drools.definition.process.Node;
import org.drools.eclipse.DRLInfo.FunctionInfo;
import org.drools.eclipse.DRLInfo.RuleInfo;
import org.drools.eclipse.builder.DroolsBuilder;
import org.drools.eclipse.builder.ResourceDescr;
import org.drools.eclipse.builder.Util;
import org.drools.eclipse.dsl.editor.DSLAdapter;
import org.drools.eclipse.editors.AbstractRuleEditor;
import org.drools.eclipse.preferences.IDroolsConstants;
import org.drools.eclipse.util.ProjectClassLoader;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.drools.lang.descr.AttributeDescr;
import org.drools.lang.descr.BaseDescr;
import org.drools.lang.descr.EnumDeclarationDescr;
import org.drools.lang.descr.FunctionDescr;
import org.drools.lang.descr.FunctionImportDescr;
import org.drools.lang.descr.GlobalDescr;
import org.drools.lang.descr.ImportDescr;
import org.drools.lang.descr.PackageDescr;
import org.drools.lang.descr.RuleDescr;
import org.drools.lang.descr.TypeDeclarationDescr;
import org.drools.rule.Package;
import org.drools.rule.builder.dialect.java.JavaDialectConfiguration;
import org.drools.template.parser.DecisionTableParseException;
import org.drools.xml.SemanticModules;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.jbpm.bpmn2.xml.BPMNDISemanticModule;
import org.jbpm.bpmn2.xml.BPMNExtensionsSemanticModule;
import org.jbpm.bpmn2.xml.BPMNSemanticModule;
import org.jbpm.compiler.ProcessBuilderImpl;
import org.jbpm.compiler.xml.ProcessSemanticModule;
import org.jbpm.compiler.xml.XmlProcessReader;
import org.jbpm.process.core.Process;
import org.jbpm.ruleflow.core.RuleFlowProcess;
import org.jbpm.workflow.core.node.RuleSetNode;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class DroolsEclipsePlugin extends AbstractUIPlugin {

    public static final int             INTERNAL_ERROR             = 120;
    public static final String          PLUGIN_ID                  = "org.drools.eclipse";
    public static final String          BUILD_RESULT_PACKAGE       = "Package";
    public static final String          BUILD_RESULT_PACKAGE_DESCR = "PackageDescr";

    //The shared instance.
    private static DroolsEclipsePlugin  plugin;
    //Resource bundle.
    private ResourceBundle              resourceBundle;
    private Map<String, Color>          colors                     = new HashMap<String, Color>();
    private Map<IResource, DRLInfo>     parsedRules                = new HashMap<IResource, DRLInfo>();
    private Map<IResource, DRLInfo>     compiledRules              = new HashMap<IResource, DRLInfo>();
    private Map<String, RuleInfo>       ruleInfoByClassNameMap     = new HashMap<String, RuleInfo>();
    private Map<String, FunctionInfo>   functionInfoByClassNameMap = new HashMap<String, FunctionInfo>();
    private Map<IResource, ProcessInfo> processInfos               = new HashMap<IResource, ProcessInfo>();
    private Map<String, ProcessInfo>    processInfosById           = new HashMap<String, ProcessInfo>();
    private boolean                     useCachePreference;

    private FormColors                  ruleBuilderFormColors;
    
    private boolean 					forceFullBuild;

    /**
     * The constructor.
     */
    public DroolsEclipsePlugin() {
        super();
        plugin = this;
    }

    /**
     * This method is called upon plug-in activation
     */
    public void start(BundleContext context) throws Exception {
        super.start( context );
        IPreferenceStore preferenceStore = getPreferenceStore();
        useCachePreference = preferenceStore.getBoolean( IDroolsConstants.CACHE_PARSED_RULES );
        preferenceStore.addPropertyChangeListener( new IPropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent event) {
                if ( IDroolsConstants.CACHE_PARSED_RULES.equals( event.getProperty() ) ) {
                    useCachePreference = ((Boolean) event.getNewValue()).booleanValue();
                    if ( !useCachePreference ) {
                        clearCache();
                    }
                }
            }
        } );

    }

    public void clearCache() {
        parsedRules.clear();
        compiledRules.clear();
        ruleInfoByClassNameMap.clear();
        functionInfoByClassNameMap.clear();
        processInfos.clear();
        processInfosById = null;
    }

    /**
     * This method is called when the plug-in is stopped
     */
    public void stop(BundleContext context) throws Exception {
        super.stop( context );
        plugin = null;
        resourceBundle = null;
        parsedRules = null;
        compiledRules = null;
        processInfos = null;
        processInfosById = null;
        for (Color color: colors.values()) {
            color.dispose();
        }
    }

    /**
     * Returns the shared instance.
     */
    public static DroolsEclipsePlugin getDefault() {
        return plugin;
    }

    /**
     * Returns the string from the plugin's resource bundle,
     * or 'key' if not found.
     */
    public static String getResourceString(String key) {
        ResourceBundle bundle = DroolsEclipsePlugin.getDefault().getResourceBundle();
        try {
            return (bundle != null) ? bundle.getString( key ) : key;
        } catch ( MissingResourceException e ) {
            return key;
        }
    }

    /**
     * Returns the plugin's resource bundle,
     */
    public ResourceBundle getResourceBundle() {
        try {
            if ( resourceBundle == null ) resourceBundle = ResourceBundle.getBundle( "droolsIDE.DroolsIDEPluginResources" );
        } catch ( MissingResourceException x ) {
            resourceBundle = null;
        }
        return resourceBundle;
    }

    /**
     * Returns an image descriptor for the image file at the given
     * plug-in relative path.
     * Uses the plug ins image registry to "cache" it.
     *
     * @param path the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {

        DroolsEclipsePlugin plugin = getDefault();
        ImageRegistry reg = plugin.getImageRegistry();
        ImageDescriptor des = reg.getDescriptor( path );
        if ( des == null ) {
            des = AbstractUIPlugin.imageDescriptorFromPlugin( "org.drools.eclipse",
                                                              path );
            reg.put( path,
                     des );
        }
        return des;
    }

    public static String getUniqueIdentifier() {
        if ( getDefault() == null ) {
            return PLUGIN_ID;
        }
        return getDefault().getBundle().getSymbolicName();
    }

    public static void log(Throwable t) {
        Throwable top = t;
        if ( t instanceof DebugException ) {
            DebugException de = (DebugException) t;
            IStatus status = de.getStatus();
            if ( status.getException() != null ) {
                top = status.getException();
            }
        }
        log( new Status( IStatus.ERROR,
                         getUniqueIdentifier(),
                         INTERNAL_ERROR,
                         "Internal error in Drools Plugin: ",
                         top ) );
    }

    public static void log(IStatus status) {
        getDefault().getLog().log( status );
    }

    public Color getColor(String type) {
        return (Color) colors.get( type );
    }

    public void setColor(String type,
                         Color color) {
        colors.put( type,
                    color );
    }

    protected void initializeDefaultPreferences(IPreferenceStore store) {
        store.setDefault( IDroolsConstants.BUILD_ALL,
                          false );
        store.setDefault( IDroolsConstants.CROSS_BUILD,
        				  false );
        store.setDefault( IDroolsConstants.EDITOR_FOLDING,
                          true );
        store.setDefault( IDroolsConstants.CACHE_PARSED_RULES,
                          true );
        store.setDefault( IDroolsConstants.DSL_RULE_EDITOR_COMPLETION_FULL_SENTENCES,
                          true );
        store.setDefault( IDroolsConstants.SKIN,
                          "BPMN2" );
        store.setDefault( IDroolsConstants.ALLOW_NODE_CUSTOMIZATION,
                          false );
        store.setDefault( IDroolsConstants.INTERNAL_API,
                          2 );
        store.setDefault( IDroolsConstants.FLOW_NODES, 
                          "1111111111111" );
    }
    
    public List<DRLInfo> parseResources(List<ResourceDescr> resources) {
    	List<ResourceDescr> toBeCompiled = new ArrayList<ResourceDescr>();
    	List<DRLInfo> infos = new ArrayList<DRLInfo>();
    	
    	for (ResourceDescr resourceDescr : resources) {
	        DRLInfo result = getExistingInfoForResource(resourceDescr.getResource(), true);
	        if ( result != null ) {
	        	infos.add(result);
	        } else {
	        	toBeCompiled.add(resourceDescr);
	        }
    	}

    	infos.addAll(buildResources(toBeCompiled));
    	return infos;
    }
    
    private List<DRLInfo> buildResources(List<ResourceDescr> resources) {
    	if (resources.isEmpty()) return Collections.emptyList();

        ClassLoader oldLoader = Thread.currentThread().getContextClassLoader();
        
        try {
        	Map<Resource, ResourceDescr> resourceMap = new HashMap<Resource, ResourceDescr>();
        	KnowledgeBuilder kbuilder = compositeBuild(resources, resourceMap);

            PackageBuilder packageBuilder = ((KnowledgeBuilderImpl)kbuilder).getPackageBuilder();
            Map<IResource, DRLInfo> infoMap = collectDRLInfo(resourceMap, packageBuilder);
    		collectErrors(resourceMap, kbuilder, packageBuilder, infoMap);
    		
    		for (DRLInfo drlInfo : infoMap.values()) {
                for ( RuleInfo ruleInfo : drlInfo.getRuleInfos() ) {
                    ruleInfoByClassNameMap.put( ruleInfo.getClassName(), ruleInfo );
                }
                for ( FunctionInfo functionInfo : drlInfo.getFunctionInfos() ) {
                    functionInfoByClassNameMap.put( functionInfo.getClassName(), functionInfo );
                }
    		}
    		
    		compiledRules = infoMap;
    		
    		return new ArrayList<DRLInfo>(infoMap.values());
        } catch ( CoreException e ) {
            log( e );
        } finally {
            Thread.currentThread().setContextClassLoader( oldLoader );
        }
        
        return Collections.emptyList();
    }

	private KnowledgeBuilder compositeBuild(List<ResourceDescr> resources, Map<Resource, ResourceDescr> resourceMap) throws CoreException {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(getBuilderConfiguration(resources));
		CompositeKnowledgeBuilder compositeKBuilder = kbuilder.batch();
		for (ResourceDescr resourceDescr : resources) {
			Resource resource = resourceDescr.getContentAsDroolsResource();
			resourceMap.put(resource, resourceDescr);
			compositeKBuilder.add(resource, resourceDescr.getType());
		}
		try {
			compositeKBuilder.build();
		} catch (DecisionTableParseException dtpe) {
			// swallow
		}
		return kbuilder;
	}

	private Map<IResource, DRLInfo> collectDRLInfo( Map<Resource, ResourceDescr> resourceMap,
													PackageBuilder packageBuilder) {
		Map<IResource, DRLInfo> infoMap = new HashMap<IResource, DRLInfo>();

		for (Map.Entry<Resource, PackageDescr> entry : groupPackageDescrByResource(packageBuilder).entrySet()) {
			ResourceDescr resourceDescr = resourceMap.get(entry.getKey());
			if (resourceDescr == null) {
				continue;
			}
			PackageDescr packageDescr = entry.getValue();

			DRLInfo info = new DRLInfo( resourceDescr.getSourcePathName(),
		            packageDescr,
		            new ArrayList<DroolsError>(),
		            packageBuilder.getPackageRegistry(packageDescr.getNamespace()).getPackage(),
		            new DroolsError[0],
		            packageBuilder.getPackageRegistry( packageDescr.getNamespace() ).getDialectCompiletimeRegistry() );

			info.setResource(resourceDescr.getResource());
			infoMap.put(resourceDescr.getResource(), info);
		}
		return infoMap;
	}

	private void collectErrors( Map<Resource, ResourceDescr> resourceMap, 
								KnowledgeBuilder kbuilder, 
								PackageBuilder packageBuilder,
								Map<IResource, DRLInfo> infoMap) {
		for (KnowledgeBuilderError error : (Collection<KnowledgeBuilderError>)kbuilder.getErrors()) {
			if (!(error instanceof DroolsError)) {
				continue;
			}
			Resource resource = error.getResource();
			if (resource == null) {
				continue;
			}
			ResourceDescr resourceDescr = resourceMap.get(resource);
			
			final DroolsError droolsError = (DroolsError)error;
			String pkgName = droolsError.getNamespace();
			List<PackageDescr> packageDescrs = packageBuilder.getPackageDescrs(pkgName);
			if (packageDescrs == null || packageDescrs.isEmpty()) {
				continue;
			}
			PackageDescr packageDescr = packageDescrs.get(0);
			
			DRLInfo info = infoMap.get(resourceDescr.getResource());
			if (info == null) {
				info = new DRLInfo( resourceDescr.getResource().getName(),
		                packageDescr,
		                new ArrayList<DroolsError>() {{
		                	add(droolsError);
		                }},
		                packageBuilder.getPackageRegistry( packageDescr.getNamespace() ).getDialectCompiletimeRegistry() );
				info.setResource(resourceDescr.getResource());
				infoMap.put(resourceDescr.getResource(), info);
			} else {
				info.addError(droolsError);
			}
		}
	}
    
    private Map<Resource, PackageDescr> groupPackageDescrByResource(PackageBuilder packageBuilder) {
    	Map<Resource, PackageDescr> map = new HashMap<Resource, PackageDescr>();
    	for (String pkgName : packageBuilder.getPackageNames()) {
    		for (PackageDescr pkgDescr : packageBuilder.getPackageDescrs(pkgName)) {
    			for (ImportDescr importDescr : pkgDescr.getImports()) {
    				getPkgDescr(map, importDescr, pkgName).addImport(importDescr);
    			}
    			for (FunctionImportDescr function : pkgDescr.getFunctionImports()) {
    				getPkgDescr(map, function, pkgName).addFunctionImport(function);
    			}
    			for (AttributeDescr attribute : pkgDescr.getAttributes()) {
    				getPkgDescr(map, attribute, pkgName).addAttribute(attribute);
    			}
    			for (GlobalDescr global : pkgDescr.getGlobals()) {
    				getPkgDescr(map, global, pkgName).addGlobal(global);
    			}
    			for (FunctionDescr function : pkgDescr.getFunctions()) {
    				getPkgDescr(map, function, pkgName).addFunction(function);
    			}
    			for (TypeDeclarationDescr type : pkgDescr.getTypeDeclarations()) {
    				getPkgDescr(map, type, pkgName).addTypeDeclaration(type);
    			}
    			for (RuleDescr rule : pkgDescr.getRules()) {
    				getPkgDescr(map, rule, pkgName).addRule(rule);
    			}
    			for (EnumDeclarationDescr enumDescr : pkgDescr.getEnumDeclarations()) {
    				getPkgDescr(map, enumDescr, pkgName).addEnumDeclaration(enumDescr);
    			}
    		}
    	}
    	return map;
    }
    
    private PackageDescr getPkgDescr(Map<Resource, PackageDescr> map, BaseDescr descr, String pkgName) {
    	Resource resource = descr.getResource();
		PackageDescr resourceDescr = map.get(resource);
		if (resourceDescr == null) {
			resourceDescr = new PackageDescr();
			resourceDescr.setNamespace(pkgName);
			map.put(resource, resourceDescr);
		}
		return resourceDescr;
    }
    
    private PackageBuilderConfiguration getBuilderConfiguration(List<ResourceDescr> resources) throws CoreException {
        ClassLoader newLoader = DroolsBuilder.class.getClassLoader();
        IResource firstResource = resources.get(0).getResource();
        String level = null;
        if ( firstResource.getProject().getNature( "org.eclipse.jdt.core.javanature" ) != null ) {
            IJavaProject project = JavaCore.create( firstResource.getProject() );
            newLoader = ProjectClassLoader.getProjectClassLoader( project );
            level = project.getOption( JavaCore.COMPILER_COMPLIANCE, true );
        }

        Thread.currentThread().setContextClassLoader( newLoader );
        PackageBuilderConfiguration builderConfiguration = new PackageBuilderConfiguration();
        builderConfiguration.getClassLoader().addClassLoader( newLoader );
        if ( level != null ) {
            JavaDialectConfiguration javaConf = (JavaDialectConfiguration) builderConfiguration.getDialectConfiguration( "java" );
            javaConf.setJavaLanguageLevel( level );
        }
        return builderConfiguration;
    }

    public DRLInfo parseResource(IResource resource,
                                 boolean compile) throws DroolsParserException {
        DRLInfo result = getExistingInfoForResource(resource, compile);
        if ( result != null ) {
            return result;
        }
        return generateParsedResource( resource,
                                       compile );
    }
    
    private DRLInfo getExistingInfoForResource(IResource resource, boolean compile) {
        DRLInfo result = (DRLInfo) compiledRules.get( resource );
        if ( result == null && !compile ) {
            result = (DRLInfo) parsedRules.get( resource );
        }
        return result;
    }

    public DRLInfo parseResource(AbstractRuleEditor editor,
                                 boolean useUnsavedContent,
                                 boolean compile) throws DroolsParserException {
        IResource resource = editor.getResource();
        if ( !editor.isDirty() || !useUnsavedContent ) {
            DRLInfo result = (DRLInfo) compiledRules.get( resource );
            if ( result == null && !compile ) {
                result = (DRLInfo) parsedRules.get( resource );
            }
            if ( result != null ) {
                return result;
            }
        }
        if ( !editor.isDirty() ) {
            return generateParsedResource( editor.getContent(),
                                           resource,
                                           true,
                                           compile );
        }
        // TODO: can we cache result when using unsaved content as well? 
        return generateParsedResource( editor.getContent(),
                                       resource,
                                       !useUnsavedContent,
                                       compile );
    }

    public DRLInfo parseXLSResource(String content,
                                    IResource resource) throws DroolsParserException {
        DRLInfo result = (DRLInfo) compiledRules.get( resource );
        if ( result != null ) {
            return result;
        }
        return generateParsedResource( content,
                                       resource,
                                       false,
                                       true );
    }

    public DRLInfo parseBRLResource(String content,
                                    IResource resource) throws DroolsParserException {
        DRLInfo result = (DRLInfo) compiledRules.get( resource );
        if ( result != null ) {
            return result;
        }
        return generateParsedResource( content,
                                       resource,
                                       false,
                                       true );
    }

    public DRLInfo parseGDSTResource(String content, IResource resource) throws DroolsParserException {
        DRLInfo result = (DRLInfo) compiledRules.get(resource);
        if (result != null) {
            return result;
        }
        return generateParsedResource(content, resource, false, true);
    }

    public void invalidateResource(IResource resource) {
        DRLInfo cached = (DRLInfo) compiledRules.remove( resource );
        if ( cached != null ) {
            RuleInfo[] ruleInfos = cached.getRuleInfos();
            for ( int i = 0; i < ruleInfos.length; i++ ) {
                ruleInfoByClassNameMap.remove( ruleInfos[i].getClassName() );
            }
            FunctionInfo[] functionInfos = cached.getFunctionInfos();
            for ( int i = 0; i < functionInfos.length; i++ ) {
                functionInfoByClassNameMap.remove( functionInfos[i].getClassName() );
            }
        }
        parsedRules.remove( resource );
        ProcessInfo processInfo = processInfos.remove( resource );
        if ( processInfo != null ) {
            processInfosById.remove( processInfo.getProcessId() );
        }
    }

    private DRLInfo generateParsedResource(IResource resource,
                                           boolean compile) throws DroolsParserException {
        if ( resource instanceof IFile ) {
            IFile file = (IFile) resource;
            try {
                String content = new String( Util.getResourceContentsAsCharArray( file ) );
                return generateParsedResource( content,
                                               file,
                                               true,
                                               compile );
            } catch ( CoreException e ) {
                log( e );
            }
        }
        return null;
    }

    public DRLInfo generateParsedResource(String content,
                                          IResource resource,
                                          boolean useCache,
                                          boolean compile) throws DroolsParserException {
        useCache = useCache && useCachePreference;
        try {
            ClassLoader oldLoader = Thread.currentThread().getContextClassLoader();
            ClassLoader newLoader = DroolsBuilder.class.getClassLoader();
            String level = null;
            // resource could be null when opening a read-only remote file 
            if ( resource != null && resource.getProject().getNature( "org.eclipse.jdt.core.javanature" ) != null ) {
                IJavaProject project = JavaCore.create( resource.getProject() );
                newLoader = ProjectClassLoader.getProjectClassLoader( project );
                level = project.getOption( JavaCore.COMPILER_COMPLIANCE, true );
            }
            try {
                Thread.currentThread().setContextClassLoader( newLoader );
                PackageBuilderConfiguration builder_configuration = new PackageBuilderConfiguration();
                if ( level != null ) {
                    JavaDialectConfiguration javaConf = (JavaDialectConfiguration) builder_configuration.getDialectConfiguration( "java" );
                    javaConf.setJavaLanguageLevel( level );
                }

                // first parse the source
                PackageDescr packageDescr = null;
                List<DroolsError> parserErrors = null;
                if ( useCache && resource != null) {
                    DRLInfo cachedDrlInfo = (DRLInfo) parsedRules.get( resource );
                    if ( cachedDrlInfo != null ) {
                        packageDescr = cachedDrlInfo.getPackageDescr();
                        parserErrors = cachedDrlInfo.getParserErrors();
                    }
                }

                DrlParser parser = new DrlParser();
                if ( packageDescr == null ) {
                    Reader dslReader = DSLAdapter.getDSLContent( content, resource );
                    if ( dslReader != null ) {
                        packageDescr = parser.parse( true, content, dslReader );
                    } else {
                        packageDescr = parser.parse( true, content );
                    }
                    parserErrors = parser.getErrors();
                }
                PackageBuilder builder = new PackageBuilder( builder_configuration );
                DRLInfo result = null;
                // compile parsed rules if necessary
                if ( packageDescr != null && compile && !parser.hasErrors()) {
                    // check whether a .package file exists and add it
                    if ( resource != null && resource.getParent() != null ) {
                        MyResourceVisitor visitor = new MyResourceVisitor();
                        resource.getParent().accept( visitor,
                                                     IResource.DEPTH_ONE,
                                                     IResource.NONE );
                        IResource packageDef = visitor.getPackageDef();
                        if ( packageDef != null ) {
                            PackageDescr desc = parseResource( packageDef,
                                                               false ).getPackageDescr();
                            if (desc != null){
                                builder.addPackage( desc);
                            }
                        }
                    }

                    builder.addPackage( packageDescr );
                                        
                    // make sure the namespace is set, use default if necessary, as this is used to build the DRLInfo
                    if ( StringUtils.isEmpty( packageDescr.getNamespace() ) ) {
                        packageDescr.setNamespace( builder.getPackageBuilderConfiguration().getDefaultPackageName() );
                    }
                    
                    result = new DRLInfo( resource == null ? "" : resource.getProjectRelativePath().toString(),
                                          packageDescr,
                                          parserErrors,
                                          builder.getPackageRegistry(packageDescr.getNamespace()).getPackage(),
                                          builder.getErrors().getErrors(),                                          
                                          builder.getPackageRegistry( packageDescr.getNamespace() ).getDialectCompiletimeRegistry() );
                } else {
                    result = new DRLInfo( resource == null ? "" : resource.getProjectRelativePath().toString(),
                                          packageDescr,
                                          parserErrors,
                                          new PackageRegistry(builder, new Package("")).getDialectCompiletimeRegistry() );
                }

                // cache result
                if ( useCache && resource != null) {
                    if ( compile && !parser.hasErrors() ) {
                        parsedRules.remove( resource );
                        compiledRules.put( resource,
                                           result );
                        RuleInfo[] ruleInfos = result.getRuleInfos();
                        for ( int i = 0; i < ruleInfos.length; i++ ) {
                            ruleInfoByClassNameMap.put( ruleInfos[i].getClassName(),
                                                        ruleInfos[i] );
                        }
                        FunctionInfo[] functionInfos = result.getFunctionInfos();
                        for ( int i = 0; i < functionInfos.length; i++ ) {
                            functionInfoByClassNameMap.put( functionInfos[i].getClassName(),
                                                            functionInfos[i] );
                        }
                    } else {
                        parsedRules.put( resource,
                                         result );
                    }
                }
                return result;
            } finally {
                Thread.currentThread().setContextClassLoader( oldLoader );
            }
        } catch ( CoreException e ) {
            log( e );
        }
        return null;
    }

    public RuleInfo getRuleInfoByClass(String ruleClassName) {
        return (RuleInfo) ruleInfoByClassNameMap.get( ruleClassName );
    }

    public FunctionInfo getFunctionInfoByClass(String functionClassName) {
        return (FunctionInfo) functionInfoByClassNameMap.get( functionClassName );
    }

    public ProcessInfo parseProcess(String input,
                                    IResource resource) throws Exception {
        try {
            ClassLoader oldLoader = Thread.currentThread().getContextClassLoader();
            ClassLoader newLoader = this.getClass().getClassLoader();
            String level = null;
            if ( resource.getProject().getNature( "org.eclipse.jdt.core.javanature" ) != null ) {
                IJavaProject project = JavaCore.create( resource.getProject() );
                newLoader = ProjectClassLoader.getProjectClassLoader( project );
                level = project.getOption( JavaCore.COMPILER_COMPLIANCE,
                                           true );
            }
            try {
                Thread.currentThread().setContextClassLoader( newLoader );
                PackageBuilderConfiguration configuration = new PackageBuilderConfiguration();
                if ( level != null ) {
                    JavaDialectConfiguration javaConf = (JavaDialectConfiguration) configuration.getDialectConfiguration( "java" );
                    javaConf.setJavaLanguageLevel( level );
                }
                configuration.getClassLoader().addClassLoader( newLoader );
                
                SemanticModules modules = configuration.getSemanticModules();
                modules.addSemanticModule( new BPMNSemanticModule() );
                modules.addSemanticModule( new BPMNDISemanticModule() );
                modules.addSemanticModule( new BPMNExtensionsSemanticModule() );
                modules.addSemanticModule( new ProcessSemanticModule() );
                
                XmlProcessReader xmlReader = new XmlProcessReader( modules, Thread.currentThread().getContextClassLoader() );
                List<org.drools.definition.process.Process> processes = 
                    (List<org.drools.definition.process.Process>) xmlReader.read( new StringReader( input ) );
                if (processes != null) {
                    for (org.drools.definition.process.Process process: processes) {
                        if ( process != null ) {
                            return parseProcess( (Process) process,
                                                 resource,
                                                 configuration );
                        } else {
                            throw new IllegalArgumentException( "Could not parse process " + resource );
                        }
                    }
                }
            } finally {
                Thread.currentThread().setContextClassLoader( oldLoader );
            }
        } catch ( Exception e ) {
            log( e );
            throw e;
        }
        return null;
    }

    public ProcessInfo getProcessInfo(String processId) {
        return processInfosById.get( processId );
    }

    public Map<ProcessInfo,List<RuleSetNode>> getRuleSetNodeByFlowGroup(String flowGroup) {
    	Map<ProcessInfo,List<RuleSetNode>> result = new HashMap<ProcessInfo,List<RuleSetNode>>();
    	for (ProcessInfo processInfo : processInfosById.values()) {
    		Node[] nodes = ((RuleFlowProcess)processInfo.getProcess()).getNodes();
    		for (int i = 0; i < nodes.length; i++) {
    			if(nodes[i] instanceof RuleSetNode)
    				if(flowGroup.equals(((RuleSetNode)nodes[i]).getRuleFlowGroup())) {
    					if(!result.containsKey(processInfo))
    						result.put(processInfo, new ArrayList<RuleSetNode>());
    					result.get(processInfo).add((RuleSetNode) nodes[i]);
    				}
    			
			}
		}
        return result;
    }
    
    public IResource findProcessResource(String processId) {
        if (processId == null) {
            return null;
        }
        for (Map.Entry<IResource, ProcessInfo> entry: processInfos.entrySet()) {
            if (processId.equals(entry.getValue().getProcessId())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public ProcessInfo parseProcess(Process process,
                                    IResource resource,
                                    PackageBuilderConfiguration config) {
        PackageBuilder packageBuilder = new PackageBuilder( config );
        ProcessBuilderImpl processBuilder = new ProcessBuilderImpl( packageBuilder );
        processBuilder.buildProcess( process, ResourceFactory.newUrlResource(
            "file://" + resource.getLocation().toString() ) );
        ProcessInfo processInfo = new ProcessInfo( process.getId(),
                                                   process );
        List<DroolsError> errors = new ArrayList<DroolsError>();
        errors.addAll( processBuilder.getErrors() );
        errors.addAll( Arrays.asList( packageBuilder.getErrors().getErrors() ) );
        processInfo.setErrors( errors );
        if ( useCachePreference ) {
            processInfos.put( resource,
                              processInfo );
            processInfosById.put( process.getId(),
                                  processInfo );
        }
        return processInfo;
    }

    /**
     * Form Colors, default colors for now.
     * 
     * @param display
     * @return
     */
    public FormColors getRuleBuilderFormColors(Display display) {
        if ( ruleBuilderFormColors == null ) {
            ruleBuilderFormColors = new FormColors( display );
            ruleBuilderFormColors.markShared();
        }
        return ruleBuilderFormColors;
    }

    public boolean resetForceFullBuild() {
    	boolean isFullBuild = forceFullBuild;
    	forceFullBuild = false;
		return isFullBuild;
	}

	public void setForceFullBuild() {
		forceFullBuild = true;
	}

	private class MyResourceVisitor
        implements
        IResourceVisitor {
        private IResource packageDef;

        public boolean visit(IResource resource) throws CoreException {
            if ( "package".equals( resource.getFileExtension() ) ) {
                packageDef = resource;
            }
            return true;
        }

        public IResource getPackageDef() {
            return packageDef;
        }
    }
}
