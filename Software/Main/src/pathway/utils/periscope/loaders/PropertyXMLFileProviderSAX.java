package pathway.utils.periscope.loaders;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.eclipse.core.runtime.FileLocator;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import pathway.data.ExperimentBuilder;
import pathway.data.PscPropAddInfoBuilder;
import pathway.data.PscRegionBuilder;
import pathway.data.persistence.Experiment;
import pathway.data.persistence.PscProperty;

public class PropertyXMLFileProviderSAX {
	private final boolean traceOn = false;
	private final String SCHEMA_FILE = "schemas/psc_properties.xsd";

	private Experiment experiment;

	/**
	 * 
	 * @param inputStream
	 * @param severityThreshold
	 */
	public PropertyXMLFileProviderSAX(InputStream inputStream) throws IOException {

		try {
			// create schema by from an XSD file:
			String schemaLang = "http://www.w3.org/2001/XMLSchema";
			SchemaFactory jaxp = SchemaFactory.newInstance(schemaLang);

			File schemaFile = new File( FileLocator.toFileURL(getClass().getClassLoader().getResource(SCHEMA_FILE)).getFile());
			Schema schema = jaxp.newSchema(new StreamSource(schemaFile));
			if (traceOn)
				System.out.println("[sax] Using schema: "+schemaFile.getAbsolutePath());

			// prepare document validator:
			Validator validator = schema.newValidator();

			// prepare SAX handler and SAX result receiving validate data:
			PropertySAXHandler handler = new PropertySAXHandler();
			SAXResult sax = new SAXResult(handler);

			// at last send valid data to out SAX handler:
			SAXSource source = new SAXSource(new InputSource(inputStream));
			validator.validate(source, sax);

		}catch (SAXException ex) {
			// we are here if the document is not valid:
			// ... process validation error...
			System.err.println("Validation Error: " + ex.getMessage());

			if (ex instanceof SAXParseException){
				SAXParseException saxParseEx = (SAXParseException) ex;
				System.err.println("SAX Parse exception occured ("
						+"col: " + saxParseEx.getColumnNumber()
						+" line: "+saxParseEx.getLineNumber()
						+"):"+saxParseEx.getMessage());
			}

			throw new IOException(ex);
		}
	}

	public Experiment parse() {
		return experiment;
	}

	private class PropertySAXHandler extends DefaultHandler {

		private ExperimentBuilder expBuilder;
		private PscPropAddInfoBuilder addInfoBuilder;

		private PscProperty property;
		/**
		 * temp Property ref
		 */
		private Boolean addInfoElement = false;
		private String currentElement = null;
		/**
		 * current element name
		 */
		private String parentElement = new String();

		@Override
		public void startDocument() throws SAXException {
			expBuilder = new ExperimentBuilder();
			parentElement = XmlPscTags.XML_PSC_PROP_EXPERIMENT_TAG;
		}

		@Override
		public void endDocument() throws SAXException {
			experiment = expBuilder.build();
		}

		/**
		 * 
		 * @param uri
		 * @param localName
		 * @param qName
		 * @param attributes
		 */
		@Override
		public void startElement(String uri, String localName, String qName, org.xml.sax.Attributes attributes) throws SAXException {
			if (traceOn)
				System.out.println( "[fileProvider:SAX] <start elem:"+qName+">");

			// 	<context FileID="42" FileName="add.f90" RFL="11" Region="USER_REGION" RegionID="11-734" >
			if( qName.equalsIgnoreCase(XmlPscTags.XML_PSC_PROP_CONTEXT_TAG) ) {
				parentElement = XmlPscTags.XML_PSC_PROP_CONTEXT_TAG;

				property.setFileID( Integer.valueOf(attributes.getValue(XmlPscTags.XML_PSC_PROP_CONTEXT_FILEID_TAG)).intValue() );
				property.setFileName(attributes.getValue(XmlPscTags.XML_PSC_PROP_CONTEXT_FILENAME_TAG));
				property.setRFL( Integer.valueOf(attributes.getValue(XmlPscTags.XML_PSC_PROP_CONTEXT_RFL_TAG)).intValue() );
				property.setRegionType( attributes.getValue(XmlPscTags.XML_PSC_PROP_CONTEXT_REGION_TAG) );
				property.setRegion(new PscRegionBuilder(attributes.getValue(XmlPscTags.XML_PSC_PROP_CONTEXT_REGIONID_TAG)).addProperty(property).build());

				if (attributes.getValue(XmlPscTags.XML_PSC_PROP_CONTEXT_CONFIG_TAG) != null)
					property.setConfiguration(attributes.getValue(XmlPscTags.XML_PSC_PROP_CONTEXT_CONFIG_TAG));
				else
					property.setConfiguration("-");
			}
			else if (qName.equalsIgnoreCase(XmlPscTags.XML_PSC_PROP_EXECOBJ_TAG)) {
				if (Integer.valueOf(attributes.getValue(XmlPscTags.XML_PSC_PROP_EXECOBJ_THREAD_TAG)) >= 0) {
					if (property.getClustered())
						throw new UnsupportedOperationException("Processing of clustered properties is currently not available");

					property.setProcess(Integer.valueOf(attributes.getValue(XmlPscTags.XML_PSC_PROP_EXECOBJ_PROC_TAG)));
					property.setThread(Integer.valueOf(attributes.getValue(XmlPscTags.XML_PSC_PROP_EXECOBJ_THREAD_TAG)));
					//		    		property.addExecObj(Integer.valueOf(attributes.getValue(XmlPscTags.XML_PSC_PROP_EXECOBJ_PROC_TAG)).intValue(),
					//		    						Integer.valueOf(attributes.getValue(XmlPscTags.XML_PSC_PROP_EXECOBJ_THREAD_TAG)).intValue());
				}
			}
			else if( qName.equalsIgnoreCase(XmlPscTags.XML_PSC_PROP_TAG) ) {
				parentElement = XmlPscTags.XML_PSC_PROP_TAG;

				property = new PscProperty();
				property.setType(attributes.getValue(XmlPscTags.XML_PSC_PROP_ID_TAG));

				property.setClustered(Boolean.valueOf(attributes.getValue(XmlPscTags.XML_PSC_PROP_CLUSTER_TAG)).booleanValue());
			}
			else if ( qName.equalsIgnoreCase(XmlPscTags.XML_PSC_PROP_ADDINFO_TAG) ) {
				addInfoElement = true;
				addInfoBuilder = new PscPropAddInfoBuilder(property);
			}
			currentElement = qName;
		}

		/**
		 * 
		 * @param uri
		 * @param localName
		 * @param qName
		 */
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {

			if( qName.equalsIgnoreCase(XmlPscTags.XML_PSC_PROP_TAG) ) {
				parentElement = XmlPscTags.XML_PSC_PROP_EXPERIMENT_TAG;
				expBuilder.addProperty(property);
				property = null;

				if (traceOn)
					System.out.println("[fileProvider:SAX] added "+property);
			}
			else if ( qName.equalsIgnoreCase(XmlPscTags.XML_PSC_PROP_ADDINFO_TAG) ) {
				property.setPscPropAddInfo( addInfoBuilder.build() );
				addInfoElement = false;
			}
			else if (qName.equalsIgnoreCase(XmlPscTags.XML_PSC_PROP_CONTEXT_TAG) )
				parentElement = XmlPscTags.XML_PSC_PROP_TAG;

			if (traceOn)
				System.out.println( "[fileProvider:SAX] <end elem:"+qName+">");
		}

		/**
		 * 
		 * @param ch
		 * @param start
		 * @param length
		 */
		@Override
		public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
			if (traceOn)
				System.out.println( "[fileProvider:SAX] ignorableWhitespace: "+length);
		}

		/**
		 * 
		 * @param ch
		 * @param start
		 * @param length
		 */
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			String value = new String( ch , start , length );

			if(!value.trim().equals("")) {
				if (traceOn)
					System.out.println( "[fileProvider:SAX] characters:"+value);

				if ( currentElement.equalsIgnoreCase(XmlPscTags.XML_PSC_PROP_NAME_TAG) )
					property.setName(property.getName() == null ? value : property.getName() + value);
				else if ( currentElement.equalsIgnoreCase(XmlPscTags.XML_PSC_PROP_SEVERITY_TAG))
					property.setSeverity(Double.valueOf(value).doubleValue());
				else if ( currentElement.equalsIgnoreCase(XmlPscTags.XML_PSC_PROP_CONFIDENCE_TAG))
					property.setConfidence(Double.valueOf(value).doubleValue());
				else if ( addInfoElement )
					addInfoBuilder.addEntry(currentElement, value);
				else
					if ( parentElement.equalsIgnoreCase(XmlPscTags.XML_PSC_PROP_EXPERIMENT_TAG) )
						if( currentElement.equalsIgnoreCase(XmlPscTags.XML_PSC_PROP_DATE_TAG) )
							expBuilder.expDate( DateTime.parse(value, ISODateTimeFormat.date()) );
						else if( currentElement.equalsIgnoreCase(XmlPscTags.XML_PSC_PROP_TIME_TAG) ) {
							DateTimeFormatter formatter1 = DateTimeFormat.forPattern("HH:mm:ss");
							DateTime expTime = formatter1.parseDateTime(value);
							expBuilder.expTime(expTime);
						}
						else if( currentElement.equalsIgnoreCase(XmlPscTags.XML_PSC_PROP_DIR_TAG) )
							expBuilder.workingDir(value);
			}
		}

	}

}