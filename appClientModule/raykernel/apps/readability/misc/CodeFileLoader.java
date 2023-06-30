package raykernel.apps.readability.misc;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import raykernel.apps.readability.code.CodeFile;

public class CodeFileLoader
{

	public static void attachAuxVersions(Collection<CodeFile> files, File aux_vers_dir, String type)
	{

	}

	public static Collection<CodeFile> getCodeFiles(File src_dir, int max_files)
	{

		List<File> files = raykernel.io.FileFinder.findAll(src_dir, "java");

		LinkedList<CodeFile> blocks = new LinkedList<CodeFile>();

		for (File f : files)
		{
			//read in code blocks
			CodeFile cb = new CodeFile(f);
			blocks.add(cb);

			if (blocks.size() >= max_files)
			{
				break;
			}
		}

		return blocks;

	}

	public static String getSimpleName(File f)
	{
		String name = f.getName();

		int end;

		if (name.contains("$"))
		{
			end = name.indexOf('$');
		}
		else if (name.endsWith(".class"))
		{
			end = name.length() - 6;
		}
		else if (name.endsWith(".java"))
		{
			end = name.length() - 5;
		}
		else
		{
			end = name.length();
		}

		return name.substring(0, end);
	}

}
