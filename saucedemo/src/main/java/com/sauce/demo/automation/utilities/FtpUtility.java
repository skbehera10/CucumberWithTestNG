package com.sauce.demo.automation.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class FtpUtility {

	/**============================================================================================
	 * Function Name: ftpConnection
	 * Description: This function is used to establish connection to FTP server having server details,port no,username,password.
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 05/11/2020
	 * @param serverName - This string argument holds server name
	 * @param portNo - This string argument holds port number of the mentioned server
	 * @param userName - This string argument holds username
	 * @param passWord - This string argument holds user password
	 * @return ftpClient - FTPClient
	 * Usage: FTPClient ftpClient = ftpConnection("10.63.17.70", "1521", "user", "password");
	 *==================================================================================================*/
	public static FTPClient ftpConnection(String serverName, int portNo, String userName, String passWord) {
		boolean isFtpConnected = false;
		FTPClient ftpClient = new FTPClient();

		/* Uncomment if any config setup has to be done
		 *
		FTPClientConfig config = new FTPClientConfig();		
		config.setServerTimeZoneId(configHelper.getConfigProperty("TimeZone")); //Add additional config if required
		*/

		try {
			ftpClient.connect(serverName, portNo);
			int replyCode = ftpClient.getReplyCode();

			if (!FTPReply.isPositiveCompletion(replyCode)) {
				ftpClient.disconnect();
				System.out.println("Connection failed. Server reply code is : " + replyCode);
				throw new Exception("Exception in connecting to FTP server");

			} else {

				isFtpConnected = ftpClient.login(userName, passWord);

				if (isFtpConnected) {
					System.out.println("Logged in to server successfully");
				} else {
					System.out.println("Could not login to the server");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception caught while connecting to FTP server : " + e);
		}

		return ftpClient;
	}

	/**=========================================================================================
	 * Function Name: disconnectFTP
	 * Description: This function is used to disconnect the active session of FTP connection..
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 05/11/2020
	 * @param ftpClient - FTPClient	 
	 * @return - 
	 * Usage: disconnectFTP(ftpClient);
	 *===========================================================================================*/
	public static void disconnectFTP(FTPClient ftpClient) {

		try {
			if (ftpClient.isConnected()) {
				ftpClient.logout();
				ftpClient.disconnect();
				System.out.println("Disconnected the active session of FTP");
			} else {
				System.out.println("No active session of FTP found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception caught while disconnecting to FTP server : " + e);
		}
	}

	/**============================================================================================
	 * Function Name: sftpConnection
	 * Description: This function is used to establish connection to SFTP server having server details,port no,username,password.
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 05/11/2020
	 * @param host - This string argument holds server name
	 * @param portNo - This string argument holds port number of the mentioned server
	 * @param userName - This string argument holds user name
	 * @param passWord - This string argument holds user password
	 * @return channnelSftp - ChannelSftp
	 * Usage: ChannelSftp channnelSftp = sftpConnection("10.63.17.70", "1521", "user", "password");
	 *==================================================================================================*/
	public static ChannelSftp sftpConnection(String host, int portNo, String userName, String passWord) {
		boolean isSftpConnected = false;
		boolean isChannelConnected = false;
		Session session = null;
		JSch jsch = new JSch();
		ChannelSftp channnelSftp = null;

		// uncomment if FTP server requires certificate
		// jsch.addIdentity("private-key-path");
		try {
			session = jsch.getSession(userName, host, portNo);
			session.setPassword(passWord);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
			channnelSftp = (ChannelSftp) session.openChannel("sftp");
			channnelSftp.connect();

			isSftpConnected = session.isConnected();
			isChannelConnected = channnelSftp.isConnected();

			if (isSftpConnected & isChannelConnected) {
				System.out.println("SFTP connection & channel established successfully");
			} else {
				System.out.println("SFTP connection & channel could not be established");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception caught while connecting to FTP server : " + e);
		}

		return channnelSftp;
	}

	/**=========================================================================================
	 * Function Name: disconnectSFTP
	 * Description: This function is used to disconnect the active session of SFTP connection..
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 05/11/2020
	 * @param session - Session	 
	 * @return - 
	 * Usage: disconnectSFTP(session);
	 *===========================================================================================*/
	public void disconnectSFTP(Session session) {
		try {
			if (session != null) {
				session.disconnect();
				System.out.println("Disconnected the active session of SFTP");
			} else {
				System.out.println("No active session of FTP found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception caught while disconnecting to SFTP server : " + e);
		}
	}

	/**
	 * Function Name: readPropertiesFile
	 * Description: This function is used to read properties file and store in a file object
	 * @author : Santosh Behera
	 * @version 1.0 
	 * @param fileName - This parameter holds properties file name 
	 * @return properties file object
	 * Usage: Properties prop = readPropertiesFile("a.properties");
	 */
	public static Properties readPropertiesFile(String fileName) throws IOException {
		FileInputStream fis = null;
		Properties prop = null;
		try {
			fis = new FileInputStream(fileName);
			prop = new Properties();
			prop.load(fis);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			fis.close();
		}
		return prop;
	}

}
