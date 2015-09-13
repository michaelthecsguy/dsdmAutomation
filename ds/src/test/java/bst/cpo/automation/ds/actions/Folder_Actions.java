package bst.cpo.automation.ds.actions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.sikuli.script.*;
import org.testng.Assert;

import bst.cpo.automation.ds.tests.BaseTest;
import bst.cpo.automation.framework.ReusableCode;


/*
 * This class contains the methods used to access and interact files and folders.
 * Specific methods are available for file manipulation with the different CPO DS folders.
 */

public class Folder_Actions extends BaseTest {
	ReusableCode reusableCode;
	Screen sikuliScreen;

	WebDriver folder_ActionDriver;
	private Match m; // Add for BSTCPO-2651 and BSTCPO-3010 - 2/6/15

	public Folder_Actions(WebDriver driver) {
		folder_ActionDriver = driver;
		reusableCode = new ReusableCode(folder_ActionDriver);
		sikuliScreen = new Screen();
	}

	/*
	 * Opens the My Docs folder when viewing the CPO folder root
	 */
	public void cpoFolder_OpenMyDocs() throws FindFailed {
		sikuliScreen.mouseMove("cpofolder_mydocs.png");
		sikuliScreen.wait(0.4);
		sikuliScreen.doubleClick("cpofolder_mydocs.png");
	}

	/*
	 * Opens the Other Docs folder when viewing the CPO folder root
	 */
	public void cpoFolder_OpenOtherDocs() throws FindFailed {
		sikuliScreen.mouseMove("cpofolder_otherdocs.png");
		sikuliScreen.wait(0.4);
		sikuliScreen.doubleClick("cpofolder_otherdocs.png");
	}

	/*
	 * Closes any folder or window using the Windows 7 close icon
	 */
	public void closeFolderWin7() throws FindFailed {
		sikuliScreen.mouseMove("ds2_windows7_folder_close.png");
		sikuliScreen.wait(0.4);
		sikuliScreen.mouseDown(Button.LEFT);
		sikuliScreen.mouseUp(Button.LEFT);
	}

	/*
	 * Closes any folder or window using the Windows shortcut
	 */
	public void closeFolderShortcut() {
		sikuliScreen.wait(0.4);
		sikuliScreen.type(Key.F4, KeyModifier.ALT);
	}

	/*
	 * This method will move any full path file given to My Docs It will catch
	 * any exceptions thrown when the file move fails or file to move does not
	 * exist
	 */
	public void moveFileToMyDocs(String fileToMove) {
		logThis("Moving file to MyDocs: " + fileToMove);
		Path original = Paths.get(fileToMove); // file to move
		String destinationFile = cpoMyDocFolder
				+ fileToMove.substring(fileToMove.lastIndexOf("\\"),
						fileToMove.length());
		Path destination = Paths.get(destinationFile);
		try {
			Files.move(original, destination, LinkOption.NOFOLLOW_LINKS);
		} catch (NoSuchFileException e) {
			logThis("EXCEPTION: File to move did not exist at: " + fileToMove);
		} catch (IOException e) {
			logThis("EXCEPTION: Move file operation failed");
		}
	}

	// Added with BSTCPO 3187 - 2/11/15	
	/*
	 * This method will copy any full path file given to to any full path file destination.
	 * It will catch any exceptions thrown when the file copy fails or file to copy does not exist
	 */
	public void copyFile(String fileToCopy, String destinationFile)
	{
		logThis("Copying file from: " + fileToCopy);
		Path original = Paths.get(fileToCopy); // file to copy

		logThis("Copying file to: " + destinationFile);
		Path destination = Paths.get(destinationFile);
				
		try {
			Files.copy(original, destination, LinkOption.NOFOLLOW_LINKS);
		}
		catch (NoSuchFileException e) {
			logThis("EXCEPTION: File to copy did not exist at: " + fileToCopy );    		
		} 
		catch (IOException e) {
			logThis("EXCEPTION: Copy file operation failed ");    		
		}
	}    
	
	/*
	 * This method will copy any full path file given to My Docs It will catch
	 * any exceptions thrown when the file copy fails or file to copy does not
	 * exist
	 */
	public void copyFileToMyDocs(String fileToCopy) {
		logThis("Copying file to MyDocs: " + fileToCopy);
		Path original = Paths.get(fileToCopy); // file to copy
		String destinationFile = cpoMyDocFolder
				+ fileToCopy.substring(fileToCopy.lastIndexOf("\\"),
						fileToCopy.length());
		Path destination = Paths.get(destinationFile);
		try {
			Files.copy(original, destination, LinkOption.NOFOLLOW_LINKS);
		} catch (NoSuchFileException e) {
			logThis("EXCEPTION: File to copy did not exist at: " + fileToCopy);
		} catch (IOException e) {
			logThis("EXCEPTION: Copy file operation failed ");
		}
	}
	
	// Add for BSTCPO-2651 and BSTCPO-3010 - 2/6/15
	/*
	 * This method will delete My Docs folder ASSUMPTION is that "details" view
	 * is in place for viewing folders
	 */
	public void deleteMyDocsFolder() throws FindFailed {
		logThis("Deleting MyDocs folder");
		sikuliScreen.rightClick("cpofolder_mydocs.png");
		sikuliScreen.click("file_delete.png");
		sikuliScreen.wait(0.4);
		sikuliScreen.click("yes_button.png");
		sikuliScreen.wait(0.4);
		// Verify it is gone
		m = sikuliScreen.exists("cpofolder_mydocs.png", 0);
		if (m != null)
			logThis("The My Docs folder was NOT deleted as expected");
	}

	// Add for BSTCPO-2651 and BSTCPO-3010 - 2/6/15
	/*
	 * This method will delete Guest subfolder from My Docs folder ASSUMPTION is that "details" view
	 * is in place for viewing folders.
	 */
	public void deleteGuestFolder() throws FindFailed {
		logThis("Deleting Guest folder");
		sikuliScreen.doubleClick("cpofolder_mydocs.png");
		sikuliScreen.rightClick("cpofolder_guest.png");
		sikuliScreen.click("file_delete.png");
		sikuliScreen.wait(0.4);
		sikuliScreen.click("yes_button.png");
		sikuliScreen.wait(0.4);
		// Verify it is gone
		m = sikuliScreen.exists("cpofolder_guest.png", 0);
		if (m != null)
			logThis("The Guest folder was NOT deleted as expected");
	}

	// Add for BSTCPO-2651 and BSTCPO-3010 - 2/6/15
	/*
	 * This method will delete Other Docs folder ASSUMPTION is that "details"
	 * view is in place for viewing folders
	 */
	public void deleteOtherDocsFolder() throws FindFailed {
		logThis("Deleting OtherDocs folder");
		sikuliScreen.rightClick("cpofolder_otherdocs.png");
		sikuliScreen.click("file_delete.png");
		sikuliScreen.wait(0.4);
		sikuliScreen.click("yes_button.png");
		sikuliScreen.wait(0.4);
		// Verify it is gone
		m = sikuliScreen.exists("cpofolder_otherdocs.png", 0);
		if (m != null)
			logThis("The Other Docs folder was NOT deleted as expected");
	}

	/*
	 * This method will delete any file given from My Docs It will catch
	 * exceptions thrown when the file does not exist, path given is a
	 * directory, or when any other IO exception occurs.
	 */
	public void deleteFileFromMyDocs(String fileToDelete) {
		logThis("Deleting file from MyDocs: " + fileToDelete);
		String deletionFile = cpoMyDocFolder + fileToDelete;
		Path deleteFilePath = Paths.get(deletionFile);
		try {
			Files.delete(deleteFilePath);
		} catch (NoSuchFileException e) {
			logThis("File to delete did not exist at: " + fileToDelete);
		} catch (DirectoryNotEmptyException e) {
			logThis("EXCEPTION: Path given points to a non empty folder! : "
					+ fileToDelete);
		} catch (IOException e) {
			logThis("EXCEPTION: Attempt to delete file field: " + fileToDelete);
		}
	}

	/*
	 * This method will delete any full path file given. It will catch
	 * exceptions thrown when the file does not exist, path given is a
	 * directory, or when any other IO exception occurs.
	 */
	public void deleteFile(String fileToDelete) {
		logThis("Deleting file: " + fileToDelete);
		String deletionFile = fileToDelete;
		Path deleteFilePath = Paths.get(deletionFile);
		try {
			Files.delete(deleteFilePath);
		} catch (NoSuchFileException e) {
			logThis("EXCEPTION: File to delete did not exist at: "
					+ fileToDelete);
		} catch (DirectoryNotEmptyException e) {
			logThis("EXCEPTION: Path given points to a non empty folder! : "
					+ fileToDelete);
		} catch (IOException e) {
			logThis("EXCEPTION: Attempt to delete file field: " + fileToDelete);
		}
	}

	/*
	 * This overloaded method will verify that any file given exists. 
	 * It will fail the test if the file does not exist. 
	 * Set failNotFound to true if you unconditionally expect the file 
	 * to exist before or after an operation.
	 * Set failNotFound to false if you only want to check if the file exists.
	 */
	public void verifyFileExists(String fileToVerify, boolean failNotFound) {
		logThis("Verifying if this file exists: " + fileToVerify);
		File f = new File(fileToVerify);
		if (f.exists() && !f.isDirectory()) {
			logThis("Expected file exists: " + fileToVerify);
		} else {
			logThis("Expected file DOES NOT exist: " + fileToVerify);
			if(failNotFound)
				Assert.fail();
		}
	}

	// Added by Josh - 2/11/15
	/*
	 * This method will verify if any file given exists and will 
	 * return true or false.
	 * Use this method when you simply want to check if a file exists
	 * in order to perform actions based on the result.
	 * fileStatus String is currently not critical
	 */
	public boolean verifyFileExists(String fileToVerify) {
		logThis("Verifying if file exists : " + fileToVerify);
		File f = new File(fileToVerify);
		if (f.exists() && !f.isDirectory()) {
			logThis("Expected file exists: " + fileToVerify);
			return true;
		} else {
			logThis("Expected file DOES NOT exist: " + fileToVerify);
			return false;
		}
	}

	/*
	 * This method will verify that any file given does not exist. It will fail
	 * the test if the file exists. Use this method when you unconditionally
	 * expect the file to not exist after an operation.
	 */
	public void verifyFileDoesNotExist(String fileToVerify) {
		logThis("Verifying if this file exists: " + fileToVerify);
		File f = new File(fileToVerify);
		if (!f.exists() && !f.isDirectory()) {
			logThis("File has been deleted as expected: " + fileToVerify);
		} else {
			logThis("Expected file still exists: " + fileToVerify);
			Assert.fail();
		}
	}

	// Added for BSTCPO-2683 and BSTCPO-2684 - 2/6/14
	public void verifyFolderExists(String folderToVerify)
	{
		logThis("Verifying if these folders exists: " + folderToVerify);
		File f = new File(folderToVerify);
		if(f.exists() && f.isDirectory()) {
			logThis("Expected folder exists: " + folderToVerify);
		}
		else {
			logThis("Expected folder DOES NOT exist: " + folderToVerify);
			Assert.fail();
		}
	}

	// Added for BSTCPO-2697 - 2/10/15
	public void verifyFolderDoesNotExist(String folderToVerify)
	{
		logThis("Verifying if this file exists: " + folderToVerify);
		File f = new File(folderToVerify);
		if(!f.exists() && !f.isDirectory()) { 
			logThis("Folder has been removed as expected: " + folderToVerify);
		}
		else {
			logThis("Expected folder still exists: " + folderToVerify);
			Assert.fail();
		}
	}

       
	// Added for BSTCPO_2622 - 2/9/15 
	/* This is double clicking a txt file, adding text to the file, saving it and then closing out of the window. */    
	public void doubleClickToOpenAddLineFile() throws FindFailed
	{ 
		sikuliScreen.doubleClick("cpofolder_mydocs_addline.png");
		sikuliScreen.wait(0.4);
		sikuliScreen.type("I am adding a new line to this document");
		sikuliScreen.wait(0.4);
		sikuliScreen.type("s",KeyModifier.CTRL);
		sikuliScreen.wait(0.4);
		sikuliScreen.type(Key.F4,KeyModifier.ALT);
	}

	// Added for BSTCPO_2622 - 2/9/15    
	/*This is adding a date and a line of text to an existing file.*/    
	public void writeToFile(String fileToWrite, String addLineOfText) 
	{ 
		File file = new File(fileToWrite);
		FileWriter writer;

		try {
			writer = new FileWriter(file, true);
			PrintWriter printer = new PrintWriter(writer);
			printer.append(addLineOfText);
			printer.close();
			System.out.println("Done, new line of text has been added.");

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
	}

	// Added for BSTCPO 3187 - 2/11/15	
	/*
	 * This method will update the file "\\My Docs\\Force_Sync.txt" with the current date time.
	 * If the file does not exist, it is created.
	 * This is a cheap and easy way to get DS to identify a change and perform a sync to DM.
	 * It will catch any exceptions thrown
	 */
	public void forceSync() 
	{ 
		File file = new File(cpoMyDocFolder + "\\Force_Sync.txt");
		FileWriter writer;
		Date date = new Date();

		try {
			if(file.exists() && !file.isDirectory()) { 
				writer = new FileWriter(file);
				writer.write("Last force update on " + date);
				writer.flush();
				writer.close();
				logThis("Updated file Force_Sync.txt with current date time.");
			} else {
				file.createNewFile();
				logThis("Force_Sync.txt file created");
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
	}

	// Added with BSTCPO-3732 - 2/11/15
	/*
	 * This method creates a directory by creating all nonexistent parent directories first.
	 * Useful if you need to create a path multiple directories deep. 
	 * No errors if folder(s) already exist, so it is pretty forgiving.
	 * Pass in the full path of the directory (relative or absolute)
	 */
	public void createDirectoryPath(String strPath) throws Exception
	{
		try {
			Path directoriesPath = Paths.get(strPath);
			Files.createDirectories(directoriesPath);
			//System.out.println("Directory sequence created: " + strPath);
			logThis("Directory sequence created: " + strPath);
		}
		catch (IOException e) {
			logThis("EXCEPTION: I/O error or dir exists but is not a directory");    		
		}
	}
	
	// Josh
	public void createFolderExplorer(String folderName)
	{
		sikuliScreen.type(Key.ALT);
		sikuliScreen.wait(0.4);
		sikuliScreen.type("f");
		sikuliScreen.wait(0.4);
		sikuliScreen.type("w");
		sikuliScreen.wait(0.4);
		sikuliScreen.type("f");
		sikuliScreen.wait(0.4);
		sikuliScreen.type(folderName);
		sikuliScreen.wait(0.4);
		sikuliScreen.type(Key.ENTER);				
	}

	// Added with BSTCPO-3732 - 2/11/15
	/*
	 * This method deletes a directory and all files in it.
	 * Useful if you need to create a path multiple directories deep. 
	 * No errors if folder(s) already exist, so it is pretty forgiving.
	 * Pass in the full path of the directory (relative or absolute)
	 */
	public boolean deleteDirectory(File dir) {
		if (dir.isDirectory()) {
			for (File file : dir.listFiles()) {
				if (file.isDirectory()) {
					try {
						if (file.getCanonicalFile().getParentFile().equals(dir.getCanonicalFile())) {
							deleteDirectory(file);
							if (file.exists() && !file.delete()) {
								System.out.println("Can't delete: " + file);
							}
						} else {
							System.out.println("Warning: " + file + " may be a symlink.  Ignoring.");
						}
					} catch (IOException e) {
						System.out.println("Warning: Cannot determine canonical file for " + file + " - ignoring.");
					}
				} else {
					if (file.exists() && !file.delete()) {
						System.out.println("Can't delete: " + file);
					}
				}
			}
			return dir.delete();
		}
		return false;
	}

	// Added with BSTCPO-3732 - 2/11/15
	/*
	 * This method creates an empty text file.
	 * No exception thrown if file exists already.
	 * Pass in the full path of the directory (relative or absolute) and the name of the file.
	 */
	public void createFile(String strPath, String strName) 
	{ 
		File file = new File(strPath + "\\" + strName);
		try {
			if(!file.exists()) { 
				file.createNewFile();
				logThis("File created: " + strName);
			} else {
				logThis("File already exists: " + strName);
			}
		} 
		catch (IOException e) 
		{
			logThis("EXCEPTION: createNewFile failed. ");
		}
	}


	// Added for Russ on 3/24/15
    //Returns file size in bytes.
    public long getFileSize(String filename) {
        File file = new File(filename);        
        	if (!file.exists() || !file.isFile()) {
        		System.out.println("Attempted to get size of file that does not exist.");
        		return -1;
        	}
        return file.length();
      }
	// Added for Russ on 3/24/15
	public void createFileWithSize(String filePath, String fileName, Integer fileSize) throws IOException {
		 
        //A really ugly way to create a text file of a given size.
		//Assuming a kilo is 1024....  1 MB = 1048576 bytes.
		//I believe DM counts 1024, Windows Explorer counts 1000.
        String newline = System.getProperty("line.separator");
 
        // Create file
		Writer output = null;
		File file = new File(filePath + "\\" + fileName);
		file.createNewFile();
		output = new BufferedWriter(new FileWriter(file, true));
 
		// Get file size in bytes
        long size = file.length();
        // Write file whilst the size is smaller than setSize
		while(size < fileSize){
			output.write("QA Automation Line Filler...");
			output.write(newline);
			output.flush();
			size = file.length();
		}
		output.close();
		logThis("Final file size is " + size + " bytes");
 
	}
}
