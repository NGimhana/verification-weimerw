package raykernel.util;

import java.util.LinkedList;
import java.util.List;

public class Permute
{

	@SuppressWarnings("unchecked")
	public static List[] determinitsticPartition(List list, int parts)
	{
		List[] lists = new LinkedList[parts];

		for (int i = 0; i < parts; i++)
		{
			lists[i] = new LinkedList<Object>();
		}

		int count = 0;

		for (Object o : list)
		{
			lists[count % parts].add(o);

			count++;
		}

		return lists;
	}

	//	 swaps array elements i and j
	@SuppressWarnings("unchecked")
	static void exch(List l, int i, int j)
	{
		Object swap = l.get(i);
		l.set(i, l.get(j));
		l.set(j, swap);
	}

	public static List[] randomPartition(List list, int parts)
	{
		shuffle(list);
		return determinitsticPartition(list, parts);
	}

	// take as input an array of strings and rearrange them in random order
	public static void shuffle(List list)
	{
		int N = list.size();
		for (int i = 0; i < N; i++)
		{
			int r = i + (int) (Math.random() * (N - i)); // between i and N-1
			exch(list, i, r);
		}
	}

}
