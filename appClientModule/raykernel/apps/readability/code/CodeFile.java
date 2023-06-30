package raykernel.apps.readability.code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import raykernel.ml.feature.Feature;

/**
 * Essentially a wrapper around a text file with methods to allow each line to
 * be treated as a CodeLine.
 * 
 * Includes some data structures to make access fast.
 * 
 * Also, keeps a record of associated .class files. This is probably not a great
 * design but its convienient for now, in the future this class should be
 * specific to java files - it would be better to use a subclass for that, or to
 * put that code in the FinBugsDetector class (this is the best solution).
 * 
 */
public class CodeFile extends TrainableCodeBlock
{

	Vector<File> classFiles = new Vector<File>(); //associated class files
	//the file associated with this clode block
	File file; //java src file

	boolean hasBug = false;

	boolean loaded = false;
	File newVersion = null;

	/**
	 * Create from a file
	 * 
	 * @param f
	 */
	public CodeFile(File f)
	{
		file = f;

		load();

	}

	public void attachClassFile(File cf)
	{
		if (cf.exists())
		{
			classFiles.add(cf);
			//io.Out.println( "added class file: " + cf.getName() );
		}
	}

	public void attachNewVersion(File nf)
	{
		if (nf.exists())
		{
			newVersion = nf;
		}
	}

	public Function extractFunction(int start, int end)
	{
		Function fun = new Function();

		for (CodeLine l : listContaining(start, end))
		{
			fun.appendLine(l);
		}

		return fun;
	}

	public Iterator<File> getClassFiles()
	{
		return classFiles.iterator();
	}

	/**
	 * Get the underlying file object
	 * 
	 */
	public File getFile()
	{
		return file;
	}

	public File getNewVersion()
	{
		return newVersion;
	}

	public boolean hasBug()
	{

		return hasBug;

	}

	public boolean hasClassFile()
	{
		return !classFiles.isEmpty();
	}

	public boolean hasNewVersion()
	{
		return newVersion != null;
	}

	public void load()
	{
		if (loaded)
			return;

		BufferedReader reader;
		codeLines = new LinkedList<CodeLine>();

		try
		{
			reader = new BufferedReader(new FileReader(file));

			while (reader.ready())
			{
				CodeLine line = new CodeLine(reader.readLine());
				codeLines.add(line);
			}

			reader.close();
		}
		catch (IOException e)
		{
			System.err.println("Could not load file: " + file.getAbsolutePath());
			return;
		}

		loaded = true;
	}

	public void printReport()
	{
		raykernel.io.Out.println(" *** File " + file.getName() + " ***\n");

		Iterator<CodeLine> iter = iterator();
		int i = 0;

		while (iter.hasNext())
		{
			CodeLine l = iter.next();
			System.out.print(i + ".) ");

			Iterator<Feature> iter2 = l.featureIterator();

			while (iter2.hasNext())
			{
				Feature f = iter2.next();;
				System.out.print("[" + f + "] ");
			}

			raykernel.io.Out.println(l);

			i++;
		}

	}

	public void setBug(boolean b)
	{
		hasBug = b;
	}

	public void unload()
	{
		if (!loaded)
			return;

		this.codeLines = null;

		loaded = false;
		System.gc();
	}

	public void writeAnnotatedFile(File outfile, boolean bugs, String... features)
	{
		try
		{

			BufferedWriter br = new BufferedWriter(new FileWriter(outfile));

			for (CodeLine l : this)
			{
				StringBuffer line = new StringBuffer();

				for (String f : features)
				{
					line.append(featureAnnotation(l.getFeature(f)));
				}

				line.append(" " + l);

				br.write(line.toString() + "\n");

				br.flush();
			}
		}
		catch (Exception e)
		{
			System.err.println("unable to write file!");
		}
	}

}
