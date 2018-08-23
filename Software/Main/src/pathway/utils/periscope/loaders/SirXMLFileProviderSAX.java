package pathway.utils.periscope.loaders;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import pathway.data.persistence.PscRegion;

public class SirXMLFileProviderSAX {
	private class SirSAXHandler extends DefaultHandler {

		private PscRegion region = null;
		private Stack<PscRegion> parentStack;

		@Override
		public void startDocument() throws SAXException {
			PscRegion sirRegion = new PscRegion();
			sirRegion.setName("sir fname");
			sirRegion.setType("SIR File");
			sirRegion.setID("0");

			regions.add(sirRegion);

			parentStack = new Stack<PscRegion>();
			parentStack.push(sirRegion);
		}

		@Override
		public void endDocument() throws SAXException {	}

		/**
		 * 
		 * @param uri
		 * @param localName
		 * @param qName
		 * @param attributes
		 */
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			PscRegion parentRegion = parentStack.peek();

			if (traceOn)
				System.out.println( "[fileProvider:SIR:SAX] <start elem:"+qName+">");

			if( qName.equalsIgnoreCase(XmlPscTags.XML_PSC_SIR_UNIT_TAG) ) {
				//create a new instance of region
				region = new PscRegion();
				region.setName(attributes.getValue(XmlPscTags.XML_PSC_SIR_REG_NAME_TAG));
				region.setType(attributes.getValue(XmlPscTags.XML_PSC_SIR_REG_TYPE_TAG));
				region.setID(attributes.getValue(XmlPscTags.XML_PSC_SIR_REG_ID_TAG));
				region.setDataScope(attributes.getValue(XmlPscTags.XML_PSC_SIR_REG_DATASCOPE_TAG));

				region.setPscRegionParent(parentRegion);
				//parentRegion.addChild(region.getUniqueID());
				parentStack.push(region);
				regions.add(region);
			}
			else if( qName.equalsIgnoreCase(XmlPscTags.XML_PSC_SIR_REGION_TAG) ) {
				//create a new instance of region
				region = new PscRegion();
				region.setName(attributes.getValue(XmlPscTags.XML_PSC_SIR_REG_NAME_TAG));
				region.setType(attributes.getValue(XmlPscTags.XML_PSC_SIR_REG_TYPE_TAG));
				region.setID(attributes.getValue(XmlPscTags.XML_PSC_SIR_REG_ID_TAG));
				region.setDataScope(attributes.getValue(XmlPscTags.XML_PSC_SIR_REG_DATASCOPE_TAG));

				region.setPscRegionParent(parentRegion);
				//parentRegion.addChild(region.getUniqueID());
				parentStack.push(region);
				regions.add(region);
			}
			else if (qName.equalsIgnoreCase(XmlPscTags.XML_PSC_SIR_POSITION_TAG)) {
				region.setStartLine(Integer.valueOf(attributes.getValue(XmlPscTags.XML_PSC_SIR_POS_START_TAG)).intValue());
				region.setEndLine(Integer.valueOf(attributes.getValue(XmlPscTags.XML_PSC_SIR_POS_END_TAG)).intValue());
			}
			else if (qName.equalsIgnoreCase(XmlPscTags.XML_PSC_SIR_FILE_TAG))
				region.setSourceFile(attributes.getValue(XmlPscTags.XML_PSC_SIR_FILE_NAME_TAG));

			else if ( qName.equalsIgnoreCase(XmlPscTags.XML_PSC_SIR_ARRAY_TAG) )
				if (traceOn)
					System.out.println("[fileProvider:SIR:SAX] Skipping array declarations");

		}

		/**
		 * 
		 * @param uri
		 * @param localName
		 * @param qName
		 */
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {

			if( qName.equalsIgnoreCase(XmlPscTags.XML_PSC_SIR_UNIT_TAG) )
				parentStack.pop();
			else if( qName.equalsIgnoreCase(XmlPscTags.XML_PSC_SIR_REGION_TAG) )
				parentStack.pop();

			if (traceOn)
				System.out.println( "[fileProvider:SIR:SAX] <end elem:"+qName+">");
		}

	}

	private final boolean traceOn = false;
	private final String SCHEMA_FILE = "schemas/sir.xsd";

	private List<PscRegion> regions;

	/**
	 * 
	 * @param inputStream
	 */
	public SirXMLFileProviderSAX(InputStream inputStream) {
		// create an empty regions set
		regions = new ArrayList<PscRegion>();

		try {
			// create schema by from an XSD file:
			String schemaLang = "http://www.w3.org/2001/XMLSchema";
			SchemaFactory jaxp = SchemaFactory.newInstance(schemaLang);


			File schemaFile = new File( getClass().getClassLoader().getResource(SCHEMA_FILE).getFile());
			Schema schema = jaxp.newSchema(new StreamSource(schemaFile));
			if (traceOn)
				System.out.println("[sax] Using schema: "+schemaFile.getAbsolutePath());

			// prepare document validator:
			Validator validator = schema.newValidator();

			// prepare SAX handler and SAX result receiving validate data:
			SirSAXHandler handler = new SirSAXHandler();
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
			ex.printStackTrace();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	public List<PscRegion> parse() {
		return regions;
	}

}