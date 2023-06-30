package raykernel.util;

import java.util.Collection;
import java.util.LinkedList;

public class Filter<T>
{

	Predicate<T> pred;

	public Filter(Predicate<T> p)
	{
		this.pred = p;
	}

	public Collection<T> filter(Collection<T> l)
	{
		return filter(l, Integer.MAX_VALUE);
	}

	public Collection<T> filter(Collection<T> l, int max)
	{
		Collection<T> filtered = new LinkedList<T>();

		for (T o : l)
		{
			if (pred.getBoolean(o))
			{
				filtered.add(o);
			}

			if (filtered.size() >= max)
			{
				break;
			}
		}

		return filtered;
	}

}
