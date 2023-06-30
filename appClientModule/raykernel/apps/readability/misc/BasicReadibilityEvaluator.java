package raykernel.apps.readability.misc;

import raykernel.apps.readability.code.CodeBlock;
import raykernel.apps.readability.code.CodeLine;

public class BasicReadibilityEvaluator implements BlockEvaluator
{

	public float evaluateBlock(CodeBlock b)
	{

		float ifs = 0;

		for (CodeLine l : b)
		{
			if (l.toString().contains("//"))
			{
				ifs++;
			}
		}

		return ifs;
	}

}
