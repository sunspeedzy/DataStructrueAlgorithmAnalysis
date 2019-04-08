package genericity;

/**
 * 此代码是《Java核心技术 卷1 第十版》第8章 泛型程序设计 的合成代码示例
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.Supplier;

import java.lang.reflect.Array;

class ArrayAlg{
	@SafeVarargs
	public static <T> T getMiddle(T... a) {
		return a[a.length/2];
	}
}

class Pair<T>{
//	private static T example;  //error
//	public static T showExample() { System.out.println(""); } //error
//	private static <T> T example;  //error
	public static <T> T showExample(Class<T> cl) { 
		try {
			return cl.newInstance() ;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	} 
	private T first;
	private T second;
	
	public T getFirst() {	return first;	}
	public T getSecond() {	return second;	}

	public void setFirst(T first) {	this.first = first;	}
	public void setSecond(T second) { this.second = second;	}

	public Pair(T first, T second) {
		this.first = first;
		this.second = second;
	}
	
}

class DateInterval extends Pair<Date>{
	public DateInterval(Date d1, Date d2) {
		super(d1, d2);
	}
	
	public void setSecond(Date second) {
		if (second.compareTo(getFirst())>=0) {
			super.setSecond(second);
		}
	}
	
	public Date getSecond() {
		return super.getSecond();
	}
	
	public Object getDate() {
		return new Date(123);
	}
}

public class TestGenericity {
	// ----------JavaSE8 新特性，构造泛型对象----------
	public static <T> Pair<T> makePair(Supplier<T> constr)	{
		return new Pair<>(constr.get(), constr.get());
	}
	private static Pair<String> pairString = makePair(String::new);
	//----------------------------------------------------------
	// ----------JavaSE8以前是如何构造泛型对象的，用反射机制----------
	public static <T> Pair<T> makePairOld(Class<T> cl)	{
		try {
			return new Pair<>(cl.newInstance(), cl.newInstance());
		} catch (Exception ex) {
			return null;
		}
		
	}
	private static Pair<String> pairStringOld = makePairOld(String.class);
	//----------------------------------------------------------
	
	//----------- 返回泛型数组的方法，无法执行 ------------------------------------
//	@SuppressWarnings("unchecked")
//	public static <T extends Comparable<? super T>>T[] minmax(T... a) {
//		Object[] mm = new Object[2];
//		return (T[]) mm; // compiles with warning 到词句时程序会
//	}
//	private static String[] ss = minmax("Tom", "Dick", "Harry"); // java.lang.ClassCastException
	//----------------------------------------------------------
	//----------- 返回泛型数组的方法 ------------------------------------
	public static <T extends Comparable<? super T>>T[] minmax2(IntFunction<T[]> constr, T... a) {
		T[] mm = constr.apply(2);
		return (T[]) mm; // compiles with warning
	}
	private static String[] ss2 = minmax2(String[]::new, "Tom", "Dick", "Harry");
	//----------------------------------------------------------
	//----------- 返回泛型数组的方法，利用反射机制 ------------------------------------
	public static <T extends Comparable<? super T>>T[] minmax3(T... a) {
		T[] mm = (T[]) Array.newInstance(a.getClass().getComponentType(), 2);
		return (T[]) mm; // compiles with warning
	}
	private static String[] ss3 = minmax3("Tom", "Dick", "Harry");
	//----------------------------------------------------------
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double middle = ArrayAlg.getMiddle(3.14, 1729d, 0d);
		DateInterval di = new DateInterval(new Date(), new Date(10000000));
		Pair<Date> pair = di;
		pair.setSecond(new Date());
		System.out.println(pair.getSecond());
		
		String staticT = Pair.showExample(String.class);
		System.out.println("Pair<T>的public static <T> T showExample(Class<T> cl)方法返回是否为空String 和 是否为null "
		  + staticT.isEmpty()+"  "+(staticT==null));

		// public final class Integer extends Number
		Number num = new Integer(1);  
		List<Number> list1 = new ArrayList<>();
		list1.add(new Integer(3));
	
		// ArrayList<Integer>与ArrayList<Number>没有继承关系
//		ArrayList<Number> list2 = new ArrayList<Integer>(); //type mismatch
//		ArrayList<Integer> list22 = new ArrayList<Number>(); //type mismatch
		ArrayList<Number> list21 = new ArrayList<>();
		list21.add(new Integer(10));
		
		// 数组是协变
		Number[] numbers = new Integer[3];
//		Integer[] integers = new Number[3]; // Type mismatch
		
		
		// 协变
		List<? extends Number> list3 = new ArrayList<Number>();
//		list3.add(new Integer(1)); //error
		
		List<? extends Number> list4 = new ArrayList<Integer>(); 
		// 逆变
		List<? super Number> list001 = new ArrayList<Number>();
		List<? super Number> list002 = new ArrayList<Object>();
		list001.add(new Integer(3));
		list002.add(new Integer(3));

		System.out.println(ss2);
	}

	// -------证明返回结果的接收对象与返回值的关系是协变--------
	static Number method(Number num) {
	    return 1;
	}

	Object resultObj = method(new Integer(2)); //correct
//	Number resultNumber = method(new Object()); //error
//	Integer resultInteger = method(new Integer(2)); //error
	//-----------------------------------------------------
}
