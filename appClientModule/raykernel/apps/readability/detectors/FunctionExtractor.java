package raykernel.apps.readability.detectors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import raykernel.apps.readability.code.CodeFile;
import raykernel.apps.readability.code.Function;

public class FunctionExtractor extends ASTVisitor
{

	private static ASTParser parser = ASTParser.newParser(AST.JLS3);
	public static Collection<Function> getAllFunctions(Collection<CodeFile> code_files, int max)
	{

		FunctionExtractor fe = new FunctionExtractor();

		List<Function> functions = new Vector<Function>();

		for (CodeFile f : code_files)
		{
			functions.addAll(fe.getFunctions(f));
			if (functions.size() > max)
			{
				break;
			}
		}

		return functions;
	}
	public static List<Function> getAllFunctions(Collection<File> files, int max)
	{
		FunctionExtractor fe = new FunctionExtractor();

		List<Function> functions = new Vector<Function>();

		for (File f : files)
		{
			functions.addAll(fe.getFunctions(f));
			if (functions.size() > max)
			{
				break;
			}
		}

		return functions;
	}
	private boolean codeFileMode = false;
	private CodeFile currentCodeFile;

	private String currentFile;

	private LinkedList<Function> extracted;

	private FunctionExtractor()
	{
	}

	private List<Function> getFunctions(CodeFile file)
	{
		codeFileMode = true;
		currentCodeFile = file;

		extracted = new LinkedList<Function>();
		currentFile = getString(file.getFile());

		parser.setSource(currentFile.toCharArray());
		CompilationUnit ast = (CompilationUnit) parser.createAST(null);

		ast.accept(this);

		return extracted;
	}

	private List<Function> getFunctions(File file)
	{
		codeFileMode = false;
		currentCodeFile = null;

		extracted = new LinkedList<Function>();
		currentFile = getString(file);

		parser.setSource(currentFile.toCharArray());
		CompilationUnit ast = (CompilationUnit) parser.createAST(null);

		ast.accept(this);

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
		int start = meth.getStartPosition();

		while (true)
		{
			if (currentFile.charAt(start - 1) == ' ' || currentFile.charAt(start - 1) == '\t')
			{
				start--;
			}
			else
			{
				break;
			}
		}

		try
		{

			TypeDeclaration typedec = (TypeDeclaration) meth.getParent();

			String type = typedec.getName().toString();

			CompilationUnit n = (CompilationUnit) typedec.getParent();

			String pack = n.getPackage().getName().toString();

			Function f;

			//create new function from methodDec
			if (codeFileMode)
			{
				f = currentCodeFile.extractFunction(start, meth.getStartPosition() + meth.getLength());
			}
			else
			{

				f = new Function(pack + "." + type, meth.getName().toString(), currentFile.substring(start, meth
						.getStartPosition()
						+ meth.getLength()));
			}

			extracted.add(f);

		}
		catch (Exception e)
		{
		}

		return false;
	}

}
