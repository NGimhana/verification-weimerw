package raykernel.ml.feature;

public class BinaryFeature extends Feature
{

	String name;
	boolean val;

	public BinaryFeature(String name, boolean val)
	{
		this.val = val;
		this.name = name;

	}

	public String name()
	{
		return name;

	}

	/*
	public static Attribute getAttribute( String fName )
	{
		Attribute a = attrMap.get(fName);
		if( a != null )
			return a;
		
		FastVector vec = new FastVector(2);
		vec.addElement( "false" );
		vec.addElement( "true" );
		
		a = new Attribute( "bug", vec );
		
		attrMap.put( fName, a );
		
		return a;
	}
	*/

	public float value()
	{
		if (val)
			return 1;
		return 0;
	}
}
