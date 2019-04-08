package book1.c02;

/**
 * 这个程序代码是《数据结构与算法分析 java语言描述 第3版》的 2.4.4节 运行时间中的对数 中的代码
 * 包括二分查找、 欧几里得算法(求最大公约数)、幂运算
 *
 */

public class LogarithmTime {

	/**
	 * 执行一个标准的 二分查找（折中查找）
	 * 输入一个已排好序的数组array，和一个同类型的值x，
	 * 如果能在 array 中找到 x，则返回下标，否则返回 -1
	 * 
	 * 时间复杂度为 O(logN)
	 * @param array
	 * @param x
	 * @return
	 */
	public static <T extends Comparable<? super T>> int binarySeach(T[] array, T x) {
		int low = 0, high = array.length-1;
		while(low<=high) {
			int mid = (low + high) / 2;
			if (array[mid].compareTo(x) < 0) {
				low = mid;
			} else if (array[mid].compareTo(x) > 0) {
				high = mid;
			} else {
				return mid;  // Found
			}
		}
		
		return -1;   // Not Found
	}
	
	/**
	 * m 要比 n大，否则需要先进行 m 和 n的替换。
	 * 算法连续计算 m % n 的值，并且 m 保持最大，n 保持为最小，知道 n为0为止，返回 m（即最小的不为0的余数）
	 * @param m
	 * @param n
	 * @return
	 */
	public static long gcd(long m, long n) {
		if (n>m) {
			long tmp = m;
			m = n;
			n = tmp;
		}
		while( n!=0) {
			long remainder = m % n;
			m = n;
			n = remainder;
		}
		
		return m;
	}
	
	/**
	 * 高效率的幂运算
	 * X的N次方：N为偶数，有    X的N次方=X的N/2次方 * X的N/2次方
	 *          N为奇数，有    X的N次方=X的(N-1)/2次方 * X的(N-1)/2次方 * X
	 *                或者有 X的N次方=X的(N-1)次方 * X
	 * @param x
	 * @param n
	 * @return
	 */
	public static long pow(long x, int n) {
		if (n==0)
			return 1;
		else if (n==1)
			return x;
	    else if (n%2==0)
			return pow(x*x, n/2);
		else
			return pow(x*x, (n-1)/2) * x; // 可以替换成 pow(x, n-1) * x
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
