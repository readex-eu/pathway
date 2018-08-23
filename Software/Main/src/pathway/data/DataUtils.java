package pathway.data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.joda.time.DateTime;
import org.orm.PersistentException;
import org.orm.PersistentTransaction;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import pathway.data.persistence.Application;
import pathway.data.persistence.Experiment;
import pathway.data.persistence.ExperimentAddInfo;
import pathway.data.persistence.HistoricalNotes;
import pathway.data.persistence.PathwayPersistentManager;
import pathway.data.persistence.PscProperty;
import pathway.data.persistence.PscRegion;
import pathway.eclipse.ExecutionLog;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.regex.*;


@NonNullByDefault
public abstract class DataUtils {
    public static void RemoveApplication(Application application) throws PersistentException
    {
    	PersistentTransaction trans = PathwayPersistentManager.instance().getSession().beginTransaction();
    	
    	try
    	{
    		application.delete();
    		trans.commit();
            ExecutionLog.get().writeLog("Application removed : " + application);
    	}
    	catch(Exception e)
    	{
            StringBuffer sb = new StringBuffer();
            sb.append("Excepion while removing applicaton from DB: ");
            sb.append(e.getMessage());
            if( e.getCause() != null )
                sb.append(" --> ").append(e.getCause().getMessage());
            System.err.println(sb.toString());
            e.printStackTrace();
            ExecutionLog.get().writeLog("RemoveApplication Exception message: " + sb.toString());

            trans.rollback();    		
    	}
    }

	public static void StoreApplication(Application application) throws PersistentException {
        PersistentTransaction trans = PathwayPersistentManager.instance().getSession().beginTransaction();

        try 
        {
            application.save();

            // Commit the transaction
            trans.commit();
            ExecutionLog.get().writeLog("Application saved : " + application);
        }
        catch( Exception e ) {
            StringBuffer sb = new StringBuffer();
            sb.append("Exception while storing application to DB: ");
            sb.append(e.getMessage());
            if( e.getCause() != null )
                sb.append(" --> ").append(e.getCause().getMessage());
            System.err.println(sb.toString());
            e.printStackTrace();
            ExecutionLog.get().writeLog("StoreApplication Exception message: " + sb.toString());

            trans.rollback();
        }
    }
	
	
    public static void RemoveExperiment(Experiment experiment) throws PersistentException
    {
    	PersistentTransaction trans = PathwayPersistentManager.instance().getSession().beginTransaction();
    	
    	try
    	{
    		experiment.deleteAndDissociate();
    		trans.commit();
            ExecutionLog.get().writeLog("Experiment removed : " + experiment.getID());
    	}
    	catch(Exception e)
    	{
            StringBuffer sb = new StringBuffer();
            sb.append("Excepion while removing experiment from DB: ");
            sb.append(e.getMessage());
            if( e.getCause() != null )
                sb.append(" --> ").append(e.getCause().getMessage());
            System.err.println(sb.toString());
            e.printStackTrace();
            ExecutionLog.get().writeLog("RemoveExperiment Exception message: " + sb.toString());

            trans.rollback();    		
    	}
    }
    
    public static void StoreExperiment(Experiment experiment) throws PersistentException {
        PersistentTransaction trans = PathwayPersistentManager.instance().getSession().beginTransaction();

        try {
            // Remove the set with properties first as they depend on an
            // existing Experiment. Create them after adding the Experiment obj
            // to the DB
            Set<PscProperty> properties = experiment.getPscProperty();
            experiment.setPscProperty(new HashSet<PscProperty>(0));
            experiment.save();

            for( PscProperty property: properties ) {
                // Check and maybe create a new prop type or give an error
                PscRegion region = PscRegion.loadPscRegionByQuery(PscRegion.PROP_ID + "= '" + property.getRegion().getID() + "'", null);
                if( region == null ) {
                    System.err.println("Adding missing PscRegion to db: " + property.getRegion().getID());
                    property.getRegion().save();
                }
                else
                    property.setRegion(region);

                property.save();
            }
            experiment.setPscProperty(properties);

            // Check and maybe create a new prop type or give an error
            HistoricalNotes note = HistoricalNotes.loadHistoricalNotesByQuery(HistoricalNotes.PROP_NOTE_DATE + "= '" + DateTime.now().toString("yyyy-MM-dd")
                    + "'", null);
            if( note == null ) {
                note = HistoricalNotes.createHistoricalNotes();
                note.setNoteDate(DateTime.now().toDate());

                Set<Experiment> explist = new HashSet<Experiment>();
                explist.add(experiment);
                note.setExperiment(explist);
                note.save();
            }
            else {
                Set<Experiment> explist = note.getExperiment();
                explist.add(experiment);
                note.save();
            }
            experiment.setHistoricalNotes(note);

            experiment.save();

            // Commit the transaction
            trans.commit();
            ExecutionLog.get().writeLog("Experiment saved : " + experiment.getID());
        }
        catch( Exception e ) {
            StringBuffer sb = new StringBuffer();
            sb.append("Excepion while storing data to DB: ");
            sb.append(e.getMessage());
            if( e.getCause() != null )
                sb.append(" --> ").append(e.getCause().getMessage());
            System.err.println(sb.toString());
            e.printStackTrace();
            ExecutionLog.get().writeLog("StoreExperiment Exception message: " + sb.toString());

            trans.rollback();
        }
    }


    public static void addExpAddInfo(Experiment experiment, String name, String value) {
        Set<ExperimentAddInfo> experimentAddInfo = experiment.getExperimentAddInfo();

        if( experimentAddInfo == null )
            experimentAddInfo = new HashSet<ExperimentAddInfo>();

        ExperimentAddInfo addInfo = new ExperimentAddInfo();
        addInfo.setName(name);
        addInfo.setValue(value);

        experimentAddInfo.add(addInfo);
        experiment.setExperimentAddInfo(experimentAddInfo);
    }


    /**
     * Convert a map with environment variables to a JSON string
     * 
     * @param envVars
     *            a map with environment variable as key and their values as values
     * @return JSON representation of the environment map
     */
    public static String EnvironmentVars2Str(Map<String, String> envVars) {
    	Gson gson = new Gson();
        String strJson = gson.toJson(envVars);

        assert strJson != null;
        return strJson;
    }


    /**
     * Convert a JSON string with environment variables to a Java Map
     * 
     * @param jsonStr
     *            JSON representation of the environment map
     * @return a map with environment variable as key and their values as values
     */
    public static Map<String, String> EnvironmentVars2Map(String jsonStr) {
    	
        Gson gson = new Gson();
        Map<String, String> envVars = gson.fromJson(jsonStr, new TypeToken<Map<String, String>>() {
        }.getType());
        return (envVars == null ? new HashMap<String, String>(0) : envVars);
    }
    
    
    public static List<String> modulesJson2List(String jsonStr) {
        List<String> modules = null;
        Gson gson = new Gson();
        if( jsonStr.length() > 0 ) {
            try {
                modules = gson.fromJson(jsonStr, new TypeToken<List<String>>() {
                }.getType());
                modules.removeAll(Collections.singleton(null));
            }
            catch( JsonSyntaxException ex ) {
                System.err.println("Error parsing modules configuration string: \'" + jsonStr + "\'");
            }
        }

        return (modules == null ? new ArrayList<String>(0) : modules);
    }


    public static String modulesList2Json(List<String> modules) {
        Gson gson = new Gson();
        String strJson = gson.toJson(modules);

        assert strJson != null;
        return strJson;
    }


    public static EnvFile parseRecordedEnvStr(String jsonStr) {
    	//Added
		String subs = "\"$@\"";            
		String replacement="\\\\\\\\\\\\\\\"\\$@\\\\\\\\\\\\\\\""; 
		String result="";
		
		Pattern regex = Pattern.compile("\\B" + Pattern.quote(subs) + "\\B", Pattern.CASE_INSENSITIVE);
		Matcher match = regex.matcher(jsonStr);

		//search and modify BASH() env variable value
		if (match.find()) {  
			//ExecutionLog.get().writeLog("Found: " + match.group() + " in " + jsonStr + " position: " + match.start());
		    result = match.replaceAll(replacement);    
		} else {
			ExecutionLog.get().writeLog(subs + " not found in " + jsonStr);
		}
		
		//ExecutionLog.get().writeLog(jsonStr);
		//ExecutionLog.get().writeLog(replacement);
	    //ExecutionLog.get().writeLog(result);
		
    	//End
    	
        Gson gson = new Gson();
        try{
        	
        	EnvFile envFile = gson.fromJson(result, EnvFile.class); 	//jsonStr
        	
        	if( envFile != null ) {
        		
        		envFile.getEnvironment().remove("_pathway_env");
        		envFile.getModules().remove("_pathway_modules_end");
        		
        		return envFile;
        	}
        }
        catch( JsonSyntaxException ex ) {
        	ExecutionLog.get().writeLog(jsonStr);
            throw new RuntimeException(ex);
        }
        
        
        return new EnvFile();
    }


    public static EnvFile parseRecordedEnvFile(String filePath) {
        Gson gson = new Gson();
        EnvFile envFile = null;
        try {
            envFile = gson.fromJson(new FileReader(filePath), EnvFile.class);
        }
        catch( JsonSyntaxException ex ) {
            throw new RuntimeException(ex);
        }
        catch( JsonIOException ex ) {
            throw new RuntimeException(ex);
        }
        catch( FileNotFoundException ex ) {
            throw new RuntimeException(ex);
        }

        envFile.getEnvironment().remove("_pathway_env");
        envFile.getModules().remove("_pathway_modules_end");
        return envFile;
    }


    /**
     * Convert an XML string with environment variables to a Java Map
     * 
     * @param jsonStr
     *            xmlStr representation of the environment map
     * @return a map with environment variable as key and their values as values
     */
    public static Map<String, String> EnvironmentVarsXML2Map(String xmlStr) {
        Document doc = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xmlStr));
            doc = db.parse(is);
        }
        catch( ParserConfigurationException ex ) {
            throw new RuntimeException(ex);
        }
        catch( SAXException ex ) {
            throw new RuntimeException(ex);
        }
        catch( IOException ex ) {
            throw new RuntimeException(ex);
        }

        Map<String, String> envVars = new HashMap<String, String>();

        Node rootNode = doc.getFirstChild();
        if( rootNode.getNodeName().equalsIgnoreCase("environment") ) {
            NodeList nodeList = rootNode.getChildNodes();

            for( int i = 0; i < nodeList.getLength(); i++ ) {
                Node childNode = nodeList.item(i);

                if( childNode.getNodeName() == "variable" ) {
                    NamedNodeMap attribs = childNode.getAttributes();
                    for( int j = 0; j < attribs.getLength(); j++ ) {
                        Node attrib = attribs.item(j);
                        envVars.put(attrib.getNodeName(), attrib.getNodeValue());
                    }
                }
            }
        }

        return envVars;
    }


    /**
     * Convert a map with environment variables to an XML string
     * 
     * @param envVars
     *            a map with environment variable as key and their values as values
     * @return XML representation of the environment map
     */
    public static String EnvironmentVars2XMLStr(Map<String, String> envVars) {
        // Parse the environment table and store it as XML in the DB
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try {
            docBuilder = docFactory.newDocumentBuilder();
        }
        catch( ParserConfigurationException ex ) {
            ex.printStackTrace();
            return "";
        }

        // root element
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("environment");
        doc.appendChild(rootElement);

        // Iterates through data rows and exports their data
        for( String envName: envVars.keySet() ) {
            Element envVar = doc.createElement("variable");

            Attr attrName = doc.createAttribute(envName);
            attrName.setValue(envVars.get(envName));
            envVar.setAttributeNode(attrName);
            rootElement.appendChild(envVar);
        }

        if( rootElement.getChildNodes().getLength() > 0 ) {
            Source source = new DOMSource(doc);
            StringWriter stringWriter = new StringWriter();
            Result result = new StreamResult(stringWriter);
            try {
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.transform(source, result);
            }
            catch( TransformerException ex ) {
                ex.printStackTrace();
                return "";
            }

            String string = stringWriter.getBuffer().toString();
            assert string != null;
            return string;
        }

        throw new RuntimeException("An internal error occurred.");
    }


    private DataUtils() {
    }
}
