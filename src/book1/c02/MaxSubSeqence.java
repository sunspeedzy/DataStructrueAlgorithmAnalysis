package book1.c02;
/**
 * 这个代码是从给定的int数组中，找到连续的最大的子序列
 * @author zhangyan_g
 *
 */
public class MaxSubSeqence {

	// 算法2，时间复杂度 O(N平方)
	public static int maxSubSum2(int[] a) {
		
		int maxSum = Integer.MIN_VALUE;
		int startPos = 0;
		int endPos = a.length-1;
		
		for (int i=0; i<a.length; i++) {
			
			int thisSum = 0;
			
			for (int j=i; j<a.length; j++) {
				thisSum += a[j];
				
				if (thisSum > maxSum) {
					maxSum = thisSum;
					startPos = i;
					endPos = j;
				}
			}
		}
		StringBuilder sb = new StringBuilder("最大子序列是从"+startPos+"到"+endPos+": ");
		for(int i=startPos; i<endPos; i++) {
			sb.append(a[i]+" ");
		}
		sb.append(a[endPos]+"。最大子序列和是: "+maxSum);
		
		System.out.println(sb.toString());
		return maxSum;
	}
	
	// 算法3，时间复杂度 O(N*logN)
	public static int maxSubSum3(int[] a) {
		return maxSumRec(a, 0, a.length-1);
	}
	private static int maxSumRec(int [] a, int left, int right) {
		if (left == right) { // Base case 基准情形
//			if (a[left] > 0)
//				return a[left];
//			else
//				return 0;
			return a[left];
		}
		
		int center = (left+right)/2;
		int maxLeftSum = maxSumRec(a, left, center);
		int maxRightSum = maxSumRec(a, center+1, right);
		
		int maxLeftBorderSum = Integer.MIN_VALUE, leftBorderSum = Integer.MIN_VALUE;
		for ( int i = center; i >= left; i-- ) {
			leftBorderSum += a[i];
			if (leftBorderSum > maxLeftBorderSum) {
				maxLeftBorderSum = leftBorderSum;
			}
		}
		int maxRightBorderSum = Integer.MIN_VALUE, rightBorderSum = Integer.MIN_VALUE;
		for ( int i = center+1; i <= right; i++ ) {
			rightBorderSum += a[i];
			if (rightBorderSum > maxRightBorderSum) {
				maxRightBorderSum = rightBorderSum;
			}
		}
		
		return Math.max(
				Math.max(maxLeftSum, maxRightSum),
				maxLeftBorderSum+maxRightBorderSum);
	}
	
	// 算法4，时间复杂度 O(N)。此方法适用于存在正数的数组序列，如果所有元素都为负数，那么次此算法不正确
	public static int maxSubSum4(int[] a) {
		int startPos = 0, endPos = a.length-1;
		// 为应对全部为负数的情况，先找出数组中的最大值，如果最大值为负数，直接返回最大值
		int maxItem = Integer.MIN_VALUE;
		for (int i=0; i<a.length; i++) {
			if (a[i] > maxItem) {
				maxItem = a[i];
				startPos = i; endPos = i;
			}
		}
		if (maxItem < 0) {
			System.out.println("算法4，最大子序列是从"+startPos+"到"+endPos+": "+maxItem);
			return maxItem;
		}
		
		int tmpStartPos = -1;
		int maxSum = 0, thisSum = 0;
		startPos = 0;
		for( int i=0; i<a.length; i++) {
			thisSum += a[i];
			
			if (thisSum > maxSum) {
				maxSum = thisSum;
				endPos = i;
				if (tmpStartPos > startPos) {
					startPos = tmpStartPos;
				}
			} else if (thisSum<0) {
				thisSum = 0;
				tmpStartPos = i+1;
			}
		}
		StringBuilder sb = new StringBuilder("算法4，最大子序列是从"+startPos+"到"+endPos+": ");
		for(int i=startPos; i<endPos; i++) {
			sb.append(a[i]+" ");
		}
		sb.append(a[endPos]+"。最大子序列和是: "+maxSum);

		System.out.println(sb.toString());
		return maxSum;
	}
	
	public static void main(String[] args) {
		int[] a = {-8, 2, -3, 5, -2, -1, 2, 6, -2};
		System.out.print("输入数组为：");
		for (int b: a) {
			System.out.print(b+" ");
		}
		System.out.println();
		
		maxSubSum2(a);
		int maxSubSeq = maxSubSum3(a);
		System.out.println("算法3最大子序列和是: " + maxSubSeq);

		maxSubSeq = maxSubSum4(a);
		System.out.println("算法4最大子序列和是: " + maxSubSeq);
	}

}
