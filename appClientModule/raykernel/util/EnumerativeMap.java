package raykernel.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class EnumerativeMap<T> implements Iterable<T>, Comparator<T>, Serializable
{
	private static final long serialVersionUID = 5895741080487844100L;
	HashMap<T, Float> map = new HashMap<T, Float>();

	public void add(T t)
	{
		Float current = getCount(t);

		current++;

		map.put(t, current);
	}

	public void addAll(Collection<? extends T> ts)
	{
		for (T t : ts)
		{
			add(t);
		}
	}

	public int compare(T arg0, T arg1)
	{
		return (int) (getCount(arg0) - getCount(arg1));
	}

	public float getCount(T t)
	{
		Float current = map.get(t);

		if (current == null)
		{
			current = (float) 0.0;
		}

		return current;
	}

	public List<T> getSortedList()
	{
		List<T> keys = new LinkedList<T>();
		keys.addAll(keySet());

		Collections.sort(keys, this);

		return keys;
	}

	public Iterator<T> iterator()
	{
		return this.keySet().iterator();
	}

	public List<T> keys()
	{
		List<T> keys = new LinkedList<T>();

		keys.addAll(map.keySet());

		return keys;

	}

	public Set<T> keySet()
	{
		return map.keySet();
	}

	public void remove(T t)
	{
		map.remove(t);

	}

	public void setCount(T t, float val)
	{
		map.put(t, val);
	}

	public int size()
	{
		return keySet().size();
	}

}
