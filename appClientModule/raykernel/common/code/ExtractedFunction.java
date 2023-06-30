package raykernel.common.code;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
/**
 * A function that lives on its own but still remembers where it came from
 * 
 * @author buse
 * 
 */
public class ExtractedFunction
{
	String pack, type, returntype, name, body;

	public ExtractedFunction(MethodDeclaration meth, String currentFile)
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

		//type
		ASTNode parent = meth.getParent();
		type = "anonymous";
		pack = "nopackage";

		if (parent instanceof TypeDeclaration)
		{
			TypeDeclaration typedec = (TypeDeclaration) parent;

			type = typedec.getName().toString();
			CompilationUnit n = (CompilationUnit) typedec.getParent();
			pack = n.getPackage().getName().toString();
		}

		//return type
		returntype = meth.getReturnType2().toString();

		//body
		body = currentFile.substring(start, meth.getStartPosition() + meth.getLength());

		//param string
		List<SingleVariableDeclaration> params = meth.parameters();
		StringBuffer paramString = new StringBuffer();
		for (SingleVariableDeclaration svd : params)
		{
			paramString.append(makeSimple(svd.getType().toString()) + ",");
		}

		if (meth.isConstructor())
		{
			name = "<init>";
		}
		else
		{
			name = meth.getName().toString();
		}
	}

	public ExtractedFunction(String pack, String type, String returntype, String name, String body)
	{
		this.pack = pack;
		this.type = type;
		this.returntype = returntype;
		this.name = name;
		this.body = body;
	}

	public String getBody()
	{
		return body;
	}

	public String getSimpleSignature()
	{
		return pack + "." + type + ":" + name;
	}

	String makeSimple(String name)
	{
		return name.substring(name.lastIndexOf('.') + 1);
	}
}
