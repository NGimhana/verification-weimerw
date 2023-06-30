package raykernel.apps.readability.core;

import java.util.LinkedList;
import java.util.List;

import raykernel.apps.readability.code.CodeBlock;

public class TrainFromSnippets
{

	List<CodeBlock> blocks = new LinkedList<CodeBlock>();

	public void addBlock(CodeBlock b)
	{
		blocks.add(b);
	}

}
