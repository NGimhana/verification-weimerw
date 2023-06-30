package raykernel.apps.readability.code;

import java.util.LinkedList;

public class Function extends TrainableCodeBlock
{
	String classname, methodname;

	public Function()
	{
		codeLines = new LinkedList<CodeLine>();
	}

	public Function(String body)
	{

		codeLines = new LinkedList<CodeLine>();

		for (String line : body.split("\n"))
		{
			codeLines.add(new CodeLine(line));
		}

		//io.Out.println("----new function-----\n" + this + "---------");
	}

	public Function(String classname, String methodname, String body)
	{
		this.classname = classname;
		this.methodname = methodname;

		codeLines = new LinkedList<CodeLine>();

		for (String line : body.split("\n"))
		{
			codeLines.add(new CodeLine(line));
		}
	}

	public void appendLine(CodeLine l)
	{
		this.codeLines.add(l);
	}

	public String getClassName()
	{
		return classname;
	}

	public String getMethodName()
	{
		return methodname;
	}

	public boolean isEmpty()
	{
		return codeLines.isEmpty();
	}

}
