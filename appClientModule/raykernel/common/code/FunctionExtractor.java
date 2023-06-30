package raykernel.common.code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import raykernel.apps.docinf.code.JMethod;

/**
 * Gets ExtractedFunctions from an input java file
 * 
 * @author buse
 * 
 */

public class FunctionExtractor extends ASTVisitor
{

	private static ASTParser parser = ASTParser.newParser(AST.JLS3);

	public static List<JMethod> extractFunctions(File file)
	{
		FunctionExtractor fe = new FunctionExtractor();
		return fe.getFunctions(file);
	}

	String currentFile;

	LinkedList<JMethod> extracted;

	private FunctionExtractor()
	{
	}

	private List<JMethod> getFunctions(File file)
	{

		extracted = new LinkedList<JMethod>();

		currentFile = getString(file);

		parser.setSource(currentFile.toCharArray());

		try
		{
			CompilationUnit ast = (CompilationUnit) parser.createAST(null);
			ast.accept(this);
		}
		catch (Exception e)
		{

		}

		return extracted;
	}

	private String getString(File file)
	{
		BufferedReader reader;
		StringBuffer content = new StringBuffer();

		try
		{
			reader = new BufferedReader(new FileReader(file));

			while (reader.ready())
			{
				content.append(reader.readLine() + "\n");
			}

			reader.close();
		}
		catch (IOException e)
		{
			System.err.println("Could not load file: " + file.getAbsolutePath());
		}

		return content.toString();
	}

	public boolean visit(MethodDeclaration meth)
	{
		JMethod newj = new JMethod(meth, currentFile);

		extracted.add(newj);

		raykernel.io.Out.println("got method: " + newj);

		return false;
	}
}
