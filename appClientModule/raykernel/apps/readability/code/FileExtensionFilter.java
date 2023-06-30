package raykernel.apps.readability.code;

import java.io.File;
import java.io.FileFilter;

/**
 * Merges differing file filter representations don't ask me why the java api
 * has 2 of them.
 * 
 * @author buse
 * 
 */
public class FileExtensionFilter extends javax.swing.filechooser.FileFilter implements FileFilter
{

	String desc;
	String exten;

	public FileExtensionFilter(String exten)
	{
		this.desc = exten;
		this.exten = "." + exten;
	}

	public FileExtensionFilter(String desc, String exten)
	{
		this.desc = desc;
		this.exten = "." + exten;
	}

	public boolean accept(File arg0)
	{
		return arg0.getName().endsWith(exten);
	}

	public String getDescription()
	{
		return desc;
	}

}
