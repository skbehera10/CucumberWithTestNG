package com.sauce.demo.automation.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;

public class FileUtility {
	/**
	 * Name: FileUtility
	 * 
	 * @author Karanveer Kour
	 * @creation date : 21st Oct'20
	 * @version 1.0
	 * 
	 *          Description: This utility is used to
	 *          Create/Read/Write/Copy/Delete/Modify for Word(.doc and .txt
	 *          extension)
	 */
	private Logger logger = Logger.getLogger(FileUtility.class);	
	
	/*
	 * Name: readTextFileAsString.
	 * 
	 * @author Karanveer Kour
	 * 
	 * @creation date : 21st Oct'20
	 * 
	 * Description - This function opens and reads a file, and returns the
	 * contents as one String.
	 * 
	 * @param - String source: Path of the file, String extension: extension of
	 * the file that you want to read (extensions like txt and doc)
	 * 
	 * @return String: the read content of the file will be returned as string;
	 */
	public String readTextFileAsString(String path, String extension) throws IOException {
		String readLine = new String();
		if (fileExist(path)) {
			switch (extension) {
			case "txt":
				File myObj = new File(path);
				Scanner myReader = new Scanner(myObj);
				try {
					while (myReader.hasNextLine()) {
						readLine = readLine + myReader.nextLine();
						logger.info("File is read by the user at " + LocalDateTime.now());
					}
				} catch (Exception e) {
					logger.info("Error occured while reading file at " + LocalDateTime.now() + e);

				} finally {
					myReader.close();
				}
				break;
			case "doc":
				File file = new File(path);
				FileInputStream fis = new FileInputStream(file.getAbsolutePath());
				try {
					XWPFDocument doc = new XWPFDocument(OPCPackage.open(fis));
					@SuppressWarnings("resource")
					XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
					readLine = extractor.getText();
				} catch (Exception e) {
					logger.info("Error while reading file " + file.getName() + e);
				} finally {
					fis.close();
				}
				break;
			default:
				logger.info("Entered extension is incorrect or functionality to read this extension is not in place");
				break;
			}
		}
		return readLine.toString();
	}

	/*
	 * Name: readHeaderWordFile.
	 * 
	 * @author Karanveer Kour
	 * 
	 * @creation date : 21st Oct'20
	 * 
	 * Description - This function helps to read Header of .doc and .docx file.
	 * 
	 * @param - String source: Path of the file
	 * 
	 * @return String: the read header of the file will be returned as string;
	 */
	public String readHeaderWordFile(String path) {
		FileInputStream fis = null;
		String headerText = null;
		if (fileExist(path)) {
			try {
				fis = new FileInputStream(path);
				XWPFDocument doc = new XWPFDocument(OPCPackage.open(fis));
				XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(doc);
				XWPFHeader header = policy.getDefaultHeader();
				if (header != null) {
					logger.info("File's Header read by the user at " + LocalDateTime.now());
					headerText = header.getText();
				} else {
					logger.info("Problem reading File's Header by the user at " + LocalDateTime.now());
				}
			} catch (Exception ex) {
				logger.info("Error while reading header of .doc file " + ex);
			} finally {
				try {
					fis.close();
				} catch (IOException e) {
					logger.info("Error while trying to close file input stream " + e);
				}
			}
		}
		return headerText;
	}

	/*
	 * Name: readFooterWordFile.
	 * 
	 * @author Karanveer Kour
	 * 
	 * @creation date : 21st Oct'20
	 * 
	 * Description - This function helps to read footer of .doc and .docx file.
	 * 
	 * @param - String source: Path of the file
	 * 
	 * @return String: the read footer of the file will be returned as string;
	 */
	public String readFooterWordFile(String path) {
		try {
			FileInputStream fis = new FileInputStream(path);
			XWPFDocument doc = new XWPFDocument(OPCPackage.open(fis));
			XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(doc);
			XWPFFooter footer = policy.getDefaultFooter();
			if (footer != null) {
				logger.info("File's Footer read by the user at " + LocalDateTime.now());
				return footer.getText();
			} else {
				logger.info("Problem reading File's Footer by the user at " + LocalDateTime.now());
				return null;
			}
		} catch (Exception ex) {
			logger.info("Error while reading header of .doc file " + ex);
			return null;
		}
	}

	/*
	 * Name: writeTextFile.
	 * 
	 * @author Karanveer Kour
	 * 
	 * @creation date : 21st Oct'20
	 * 
	 * Description - This function helps to write in the word(files with
	 * extension txt and doc) document .
	 * 
	 * @param - String source: Path of the file,InputStream text: pass input
	 * stream to write lines in word document
	 * 
	 * @return null
	 */
	public void writeTextFile(String path, InputStream text) {
		if (fileExist(path)) {
			File file = new File(path);

			BufferedWriter out = null;
			BufferedReader in = new BufferedReader(new InputStreamReader(text));
			try {
				out = new BufferedWriter(new FileWriter(file, true));
				String line = null;
				while ((line = in.readLine()) != null) {
					out.write(line);
					out.newLine();
					System.out.println(line);
				}
				logger.info(file.getName() + " is written by the user at " + LocalDateTime.now());
			} catch (IOException e) {
				logger.info("Error while writing text file" + file.getName() + e);
			} finally {
				try {
					out.close();
				} catch (IOException e) {
					logger.info("Error while closing buffered writer for " + file.getName() + e);
				}
			}
		}
	}

	/*
	 * Name: filExist.
	 * 
	 * @author Karanveer Kour
	 * 
	 * @creation date : 21st Oct'20
	 * 
	 * Description - This function helps to check file exists or not.
	 * 
	 * @param - String source: Path of the file
	 * 
	 * @return Boolean: true if file exist and false if it doesn't
	 */
	public Boolean fileExist(String path) {
		// pass the filename or directory name to File object
		File f = new File(path);
		// apply File class methods on File object
		logger.info(f.getName() + " Exists :" + f.exists());
		if (f.exists()) {
			logger.info("Is writeable: " + f.canWrite() + " , Is readable: " + f.canRead() + " , Is a directory: "
					+ f.isDirectory() + " and File Size in bytes: " + f.length());
		}
		return f.exists();
	}

	/*
	 * Name: deleteFile.
	 * 
	 * @author Karanveer Kour
	 * 
	 * @creation date : 21st Oct'20
	 * 
	 * Description - This function helps to delete file.
	 * 
	 * @param - String source: Path of the file
	 * 
	 * @return Boolean: true if file deleted successfully and false if it
	 * doesn't
	 */
	public boolean deleteFile(String path) {
		boolean deleteFile = false;
		if (fileExist(path)) {
			File file = new File(path);
			if (file.delete()) {
				deleteFile = true;
				logger.info("File deleted successfully");
			} else {
				logger.info("Failed to delete the file");
			}
		}
		return deleteFile;
	}

	/*
	 * Name: modifyTextFile.
	 * 
	 * @author Karanveer Kour
	 * 
	 * @creation date : 21st Oct'20
	 * 
	 * Description - This function helps to replace text in file with extension
	 * .txt and .doc.
	 * 
	 * @param - String source: Path of the file,String oldString: String you
	 * want to replace, String newString: String you want to replace with
	 * 
	 * @return Boolean: void
	 */
	public void modifyTextFile(String filePath, String oldString, String newString) {
		File fileToBeModified = new File(filePath);
		String oldContent = "";
		BufferedReader reader = null;
		FileWriter writer = null;
		try {
			reader = new BufferedReader(new FileReader(fileToBeModified));
			// Reading all the lines of input text file into oldContent
			String line = reader.readLine();
			while (line != null) {
				oldContent = oldContent + line + System.lineSeparator();
				line = reader.readLine();
			}
			// Replacing oldString with newString in the oldContent
			String newContent = oldContent.replaceAll(oldString, newString);
			// Rewriting the input text file with newContent
			writer = new FileWriter(fileToBeModified);
			writer.write(newContent);
		} catch (IOException e) {
			logger.info("Error while modifying document " + fileToBeModified.getName() + e);
		} finally {
			try {
				// Closing the resources
				reader.close();
				writer.close();
			} catch (IOException e) {
				logger.info("Error while trying to close buffered reader/writer for " + fileToBeModified.getName() + e);
			}
		}
	}
}
