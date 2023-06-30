package raykernel.apps.readability.bugdetectors;

import java.io.File;
import java.util.Iterator;

import raykernel.apps.readability.code.CodeFile;
import raykernel.apps.readability.code.CodeLine;
import raykernel.config.Config;
import raykernel.config.GlobalStats;
import raykernel.ml.feature.FeatureDetector;
import raykernel.ml.feature.StandardValueFeature;
import raykernel.ml.feature.Trainable;
import raykernel.util.Common;
import edu.umd.cs.findbugs.AbstractBugReporter;
import edu.umd.cs.findbugs.AnalysisError;
import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.DetectorFactoryCollection;
import edu.umd.cs.findbugs.FindBugs2;
import edu.umd.cs.findbugs.IFindBugsEngine;
import edu.umd.cs.findbugs.Priorities;
import edu.umd.cs.findbugs.Project;
import edu.umd.cs.findbugs.classfile.ClassDescriptor;
import edu.umd.cs.findbugs.config.UserPreferences;

public class FindBugsDetector extends AbstractBugReporter implements FeatureDetector
{

	CodeFile currBlock;
	IFindBugsEngine finder;

	public FindBugsDetector()
	{
		this.setPriorityThreshold(Priorities.IGNORE_PRIORITY);

		System.setProperty("findbugs.home", Config.get("findbugs_home"));

		System.setProperty("findbugs.jaws", "true");

		finder = new FindBugs2();
		finder.setBugReporter(this);

		// Load plugins
		DetectorFactoryCollection.instance().ensureLoaded();

		// Set the DetectorFactoryCollection 
		finder.setDetectorFactoryCollection(DetectorFactoryCollection.instance());

		//Set up user prefrences
		UserPreferences up = UserPreferences.createDefaultUserPreferences();
		//up.enableAllDetectors(true);
		finder.setUserPreferences(up);
	}

	protected void doReportBug(BugInstance foundBug)
	{
		if (!foundBug.getPrimarySourceLineAnnotation().getSourceFile().equals(currBlock.getFile().getName()))
			//System.err.println("Bug reported for: " + foundBug.getPrimarySourceLineAnnotation().getSourceFile() + 
			//		" but working on : " + currBlock.getFile().getName());
			return;

		if (foundBug.getPrimarySourceLineAnnotation().isUnknown())
			return;

		int start = foundBug.getPrimarySourceLineAnnotation().getStartLine();
		int end = foundBug.getPrimarySourceLineAnnotation().getEndLine();

		//io.Out.println("unknown = " + foundBug.getPrimarySourceLineAnnotation().isUnknown() );
		//io.Out.println("start-end = " + start + "-" + end );

		Iterator<CodeLine> iter = currBlock.getLines(start - 1, end);

		while (iter.hasNext())
		{
			CodeLine l = iter.next();
			l.setFeature(new StandardValueFeature(featureName(), true));

			//io.Out.println(l.toString());
		}

		currBlock.setFeature(new StandardValueFeature(featureName(), true));

		GlobalStats.bugCount++;
		raykernel.io.Out.println("fb bug " + GlobalStats.bugCount);
	}

	public String featureName()
	{
		return "findbugs";
	}

	public String[] featureNames()
	{
		return Common.makeSingletonArray("findbugs");
	}

	public void finish()
	{
		System.gc(); //probably not needed
	}

	public BugReporter getRealBugReporter()
	{
		return this;
	}

	public void observeClass(ClassDescriptor arg0)
	{

	}

	public void reportAnalysisError(AnalysisError arg0)
	{

	}

	public void reportMissingClass(String arg0)
	{

	}

	public void runDetector(Trainable block)
	{

		if (!(block instanceof CodeFile))
			throw new IllegalArgumentException();

		CodeFile codefile = (CodeFile) block;

		currBlock = codefile;

		if (!codefile.hasClassFile())
		{
			raykernel.io.Out.println("no class file for: " + codefile.getFile().getName() + ".  Cannot run FindBugs.");
			return;
		}

		//io.Out.println("running fb on " + codefile.getFile().getName());

		//create project with the .class files
		Project p = new Project();
		Iterator<File> iter = codefile.getClassFiles();

		int max = 3;
		int count = 0;
		while (iter.hasNext() && count < max)
		{
			p.addFile(iter.next().toString());
			count++;
		}

		finder.setProject(p);

		try
		{
			finder.execute();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
