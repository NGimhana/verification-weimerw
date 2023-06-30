package raykernel.apps.readability.misc;

import raykernel.apps.readability.code.CodeBlock;
import raykernel.apps.readability.code.CodeLine;

public class BugCountEvaluator implements BlockEvaluator
{

	String bug_feature_name;

	public BugCountEvaluator(String bug_feature_name)
	{
		this.bug_feature_name = bug_feature_name;
	}

	public float evaluateBlock(CodeBlock b)
	{

		float bugs = 0;

		for (CodeLine l : b)
		{
			bugs += l.getFeature(bug_feature_name).value();
		}

		return bugs;
	}

}
