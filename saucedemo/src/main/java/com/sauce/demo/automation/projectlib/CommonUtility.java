package com.sauce.demo.automation.projectlib;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.sauce.demo.automation.base.BaseInitialiser;

public class CommonUtility extends BaseInitialiser {
	private static Logger log = Logger.getLogger(CommonUtility.class);

	/*
	 * Function Name: winFileUpload
	 * Description: This function is used to enter file path on windows file upload popup
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 24/12/2020
	 * @param filePath - Holds file path which need to be uploaded 
	 * Usage: FileUtility.winFileUpload("C:\\WORK_SYS\\Sample_TD1.xml");
	 */
	public static void winFileUpload(String filePath) {
		try {
			Thread.sleep(1500);
			Robot robot = new Robot();
			robot.setAutoDelay(3000);
			StringSelection stringSelection = new StringSelection(filePath.trim());
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
			robot.setAutoDelay(1000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);
			robot.setAutoDelay(1000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			log.info("Uploaded file: " + filePath);
		} catch (AWTException | InterruptedException e) {
			e.printStackTrace();
		}

	}

	/*
	 * Function Name: updateXmlByNodeXpath
	 * Description: This function is used to update any tag value based on passed xml xpath
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 22/12/2020
	 * @param xmlFilePath - Holds xml file path which need to be modified
	 * @param nodeXpath - xpath of the tag in the xml file
	 * @param setValue - value to which the node/ tag value to be set
	 * @param newFilePath - New xml file path
	 * Usage: CommonUtility.updateXmlByNodeXpath(filePath, nodeXpath, xmlSetValue, newFilePath);
	 */
	public static void updateXmlByNodeXpath(String xmlFilePath, String nodeXpath, String setValue, String newFilePath) {
		String filePath = xmlFilePath.trim();
		File xmlFile = new File(filePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			XPath xpath = XPathFactory.newInstance().newXPath();
			Node node = (Node) xpath.compile(nodeXpath.trim()).evaluate(doc, XPathConstants.NODE);
			String currentValue = node.getTextContent();
			node.setTextContent(setValue.trim());
			// Write to XML
			doc.getDocumentElement().normalize();
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(newFilePath.trim()));
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
			log.info("XML file updated successfully for Node. Previous Value: " + currentValue + " Updated Value: " + setValue.trim());
		} catch (SAXException | ParserConfigurationException | IOException | TransformerException | XPathExpressionException e) {
			log.info(e);
			e.printStackTrace();
		}
	}

	

	

	/*
	 * Function Name: getCurrentDay
	 * Description: This function is used to get current day date
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 08/01/2021
	 * @return date in dd/mm/yyyy format
	 * Usage: FileUtility.getCurrentDay();
	 */
	public static String getCurrentDay() {
		LocalDateTime ldt = LocalDateTime.now();
		String intraDay = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(ldt);
		return intraDay;
	}

	/*
	 * Function Name: getDayAhead
	 * Description: This function is used to get day ahead date
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 08/01/2021
	 * @return date in dd/mm/yyyy format
	 * Usage: FileUtility.getDayAhead();
	 */
	public static String getDayAhead() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, +1);
		String dayAhead = dateFormat.format(cal.getTime());
		return dayAhead;
	}

	public static String CurrentDate_YYYYMMDD() {
		LocalDateTime ldt = LocalDateTime.now();
		String intraDay = DateTimeFormatter.ofPattern("yyyyMMdd").format(ldt);
		return intraDay;
	}

	public static String intradayRPA() {
		LocalDateTime ldt = LocalDateTime.now();
		String intraDay = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(ldt);
		return intraDay;
	}

	public static String dayaheadRPA() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, +1);
		String dayAhead = dateFormat.format(cal.getTime());
		return dayAhead;
	}

	public static String dayAhead() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, +1);
		String dayAhead = dateFormat.format(cal.getTime());
		return dayAhead;
	}

	/*
	 * Function Name: addXmlNode
	 * Description: This function is used to add a new chlid node in existing xml file
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 13/01/2021
	 * @parm existingXmlFile - current xml file/ path where the node need to be added
	 * @parm updateXmlFile - file/ path name where to be saved after update
	 * @parm parentNode - Parent node in xml under which the new node need to be created
	 * @parm newNode - New node name which has to be created
	 * @parm child1 - child 1 element name
	 * @parm child2 - child 2 element name if required
	 * @parm child1Val - child 1 element value
	 * @parm child2Val - child 2 element value
	 * Usage: CommonUtility.addXmlNode("c:\\a.xml", "c:\\b.xml", "Period", "point", "position", "quantity", "20", "320");
	 */
	public static void addXmlNode(String existingXmlfile, String updateXmlFile, String parentNode, String newNode, String child1, String child2,
			String child1Val, String child2Val) {
		try {
			String filePath = existingXmlfile;
			String newFilePath = updateXmlFile;
			File xmlFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			Document doc = null;
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(xmlFile);
			Element root = doc.getDocumentElement();
			Element ele = (Element) root.getElementsByTagName(parentNode).item(0);
			Element point = doc.createElement(newNode);
			Element position = doc.createElement(child1);
			position.setTextContent(child1Val);
			Element quantity = doc.createElement(child2);
			quantity.setTextContent(child2Val);
			point.appendChild(position);
			point.appendChild(quantity);
			ele.appendChild(point);
			// Write to XML
			doc.getDocumentElement().normalize();
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(newFilePath.trim()));
			transformer.transform(source, result);
			log.info("XML file updated successfully");
		} catch (SAXException | ParserConfigurationException | IOException | TransformerException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Function Name: getFirstFileExtension
	 * Description: This function is used to get the first file extension in a dir
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 02/02/2021
	 * @parm dirPath - directory path
	 * @return - returns filename and extention in string array
	 * Usage: String[] file = CommonUtility.getFirstFileExtension("C:\\work");
	 */
	public static String[] getFirstFileExtension(String dirPath) {
		File dir = new File(dirPath);
		File[] dirContents = dir.listFiles();
		String fileName = dirContents[0].getName();
		String ext = FilenameUtils.getExtension(dirContents[0].getName());
		String[] file = new String[2];
		file[0] = fileName;
		file[1] = ext;		
		return file;
	}

	/*
	 * Function Name: deleteAllDirFiles
	 * Description: This function is used to delete all the files in a dir
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 02/02/2021
	 * @parm dirPath - directory path
	 * Usage: CommonUtility.deleteAllDirFiles("C:\\work");
	 */
	public static void deleteAllDirFiles(String dirPath) {
		File dir = new File(dirPath);
		File[] dirContents = dir.listFiles();
		if(dirContents!=null) {
		for (int i = 0; i < dirContents.length; i++) {
			dirContents[i].delete();
		}
		log.info("Existing files deleted from: " + dirPath);
	}
 }
}
