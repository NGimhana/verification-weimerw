package raykernel.util;

import java.io.Serializable;

public class Tuple<T1, T2> implements Serializable
{

	public T1 first;
	public T2 second;

	public Tuple()
	{
		// empty tuple
	}

	public Tuple(T1 first, T2 second)
	{
		this.first = first;
		this.second = second;
	}

	@SuppressWarnings("unchecked")
	public boolean equals(Object o)
	{
		if (!(o instanceof Tuple))
			return false;

		Tuple t = (Tuple) o;

		return first.equals(t.first) && second.equals(t.second);
	}

	public int hashCode()
	{
		return first.hashCode() * second.hashCode();
	}

}
