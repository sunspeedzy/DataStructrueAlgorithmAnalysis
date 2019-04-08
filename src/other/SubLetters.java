package other;

public class SubLetters {

	static void SubSet(int n)
	{
		final String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int maxnum = 1<<n;          //2^n
		for(int i=0;i<maxnum;i++)//处理0到2^n  -1之间的数字
		{
			for(int j=0;j<n;j++)//j表示二进制右数第几位
			{
				int a = i<<j;
				if( (i&a) != 0)//表示当前位不为0,则需要打印相应的字母
				{
					System.out.print(str.charAt(j)+" ");
				}
			} 
			System.out.print("\n");
		}
	}
	
	public static void main(String[] args) {
		SubSet(4);
	}

}
