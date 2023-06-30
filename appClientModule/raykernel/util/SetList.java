package raykernel.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class SetList<T> implements Iterable<T>
{

	LinkedList<T> list = new LinkedList<T>();
	HashSet<T> set = new HashSet<T>();

	public void add(T t)
	{
		addLast(t);

	}

	public void addAll(Collection<T> methods)
	{

		for (T t : methods)
		{
			add(t);
		}

	}

	public void addFirst(T t)
	{
		if (!set.contains(t))
		{
			set.add(t);
			list.addFirst(t);
		}
	}

	public void addLast(T t)
	{
		if (!set.contains(t))
		{
			set.add(t);
			list.addLast(t);
		}
	}

	public boolean contains(T t)
	{
		return set.contains(t);
	}

	public boolean equals(Object o)
	{
		//io.Out.println("call to equals");

		if (o instanceof SetList) //could be
		{
			SetList other = (SetList) o;

			if (this.size() == other.size()) //still could be
			{
				Iterator iter1 = this.iterator();
				Iterator iter2 = other.iterator();

				while (iter1.hasNext()) //already know same size
				{
					if (!iter1.next().equals(iter2.next()))
						return false;
				}

				//	io.Out.println("true");

				return true;

			}
		}
		return false;
	}

	public int hashCode()
	{
		return set.hashCode() * list.hashCode();
	}

	public boolean isEmpty()
	{
		return list.isEmpty();
	}

	public Iterator<T> iterator()
	{
		return list.iterator();
	}

	public T pop()
	{
		T first = list.remove();
		set.remove(first);
		return first;
	}

	public int size()
	{
		return list.size();
	}

}
