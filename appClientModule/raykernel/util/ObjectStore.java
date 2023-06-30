package raykernel.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import raykernel.config.Config;

public class ObjectStore
{
	private static String location = Config.get("objectstore") + "/";

	public static Object get(String key)
	{
		if (Config.getBoolean("obstore_output"))
			raykernel.io.Out.println("OBSTORE", "reading: " + key);

		// Read from disk using FileInputStream
		Object in = null;
		try
		{
			Tools.makeDir(location);

			FileInputStream f_in = new FileInputStream(location + key + ".dat");

			// Read object using ObjectInputStream
			ObjectInputStream obj_in = new ObjectInputStream(f_in);

			// Read an object
			in = obj_in.readObject();

		}
		catch (Exception e)
		{
			System.err.println("unable to load data: " + e);
		}

		if (Config.getBoolean("obstore_output"))
			raykernel.io.Out.println("OBSTORE", "loaded: " + in.toString());

		return in;
	}

	public static void main(String[] args)
	{
		/*
		String s = "hello";

		ObjectStore.put("yo", s);

		String out = (String) ObjectStore.get("yo");

		io.Out.println("world? " + out);
		*/
		//ExceptionInstance ei = new ExceptionInstance(new JClass("hi"), new JMethod(
		//"foo.util.TestB void computeMore(int,)"));
		raykernel.io.Out.println("worked");

	}
	public static void put(String key, Object o)
	{
		if (Config.getBoolean("obstore_output"))
			raykernel.io.Out.println("OBSTORE", "writing: " + key);

		// Write to disk with FileOutputStream
		FileOutputStream f_out;
		try
		{
			f_out = new FileOutputStream(location + key + ".dat");

			// Write object with ObjectOutputStream
			ObjectOutputStream obj_out = new ObjectOutputStream(f_out);

			// Write object out to disk
			obj_out.writeObject(o);
		}
		catch (IOException e)
		{
			raykernel.io.Out.println("failed to write data: " + e);
		}

		if (Config.getBoolean("obstore_output"))
			raykernel.io.Out.println("OBSTORE", "put: " + key);
	}

}
