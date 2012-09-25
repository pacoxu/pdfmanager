package jp.co.worksap.recruiting;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class Hashs {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Set s1 = new HashSet();
		Set s2 = new HashSet();
		Random r = new Random();
		for (int i = 0; i < 1000000; i++) {
			s1.add( Math.abs( r.nextInt() )/ 10 );
			s2.add( Math.abs( r.nextInt() )/ 10 );
		}
		int[] a = new int[s1.size()];
		int[] b = new int[s2.size()];
		int s11 = s1.size();
		int s12 = s2.size();
		Iterator it1 = s1.iterator();
		Iterator it2 = s2.iterator();
		for (int i = 0 ; i < s11; i++) {
			a[i] = (int) it1.next();
		}
		for (int i = 0 ; i < s12; i++) {
			b[i] = (int) it2.next();
		}
		
//yanzhifa
		System.out.println(System.currentTimeMillis());
		System.out.println( getAnd( a , b ) );
//		System.out.println(list);
		System.out.println(System.currentTimeMillis());

//xujunjie
		System.out.println(System.currentTimeMillis());
		System.out.println( getAnd2( a , b ) );
//		System.out.println(list);
		System.out.println(System.currentTimeMillis());

//yankay
		System.out.println(System.currentTimeMillis());
		System.out.println( getAnd3( a , b ) );
//		System.out.println(list);
		System.out.println(System.currentTimeMillis());

		
	}
	
	private static int getAnd(int[] a, int[] b) {
		Hashtable ht = new Hashtable();
		for (int i : a) 
		{
			ht.put(i, " ");
		}
		ArrayList list = new ArrayList();
		for (int j : b)
		{
			if (ht.containsKey(j)) 
			{
				list.add(j);
			}
		}
		return list.size();

	}

	private static int getAnd2(int[] a, int[] b) {
		Arrays.sort(a);
		Arrays.sort(b);
		//for justice, I use ArrayList as the result
		ArrayList list = new ArrayList();
		int size1 = a.length;
		int size2 = b.length;
		int g = 0;
		for(int i = 0; i < size1; i ++ )
		{
			for(int j = g; j < size2; j++ )
			{
				if( a[i] < b[j])
				{
					break;
				}
				else if(a[i] == b[j])
				{
					list.add(a[i]);
					g = j;
					break;
				}
			}
			
		}
		return list.size();
	}

	
	private static int getAnd3(int[] a, int[] b) {
		BitSet a1 = new BitSet();
		BitSet a2 = new BitSet();
		for (int i : a) 
		{
			a1.set(i, true);
		}
		for (int j : b) 
		{
			a2.set(j, true);
		}
		a1.and(a2);
//		return 	a1.cardinality();
		ArrayList list = new ArrayList();
		int tem = 0;
		do{
			tem = a1.nextSetBit( tem );
			list.add( a1.nextSetBit(tem) );
			tem ++;
		}while( tem < a1.length());
		return 	list.size();
	}

	
}
