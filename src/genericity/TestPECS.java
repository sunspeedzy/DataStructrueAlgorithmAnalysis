package genericity;

/**
 * 此代码是《Java核心技术 卷1 第十版》第8章 泛型程序设计 的合成代码示例
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class  Stack<E>{
	private List<E> values;
	
    public Stack() {
    	values = new ArrayList<>();
    };
    public void push(E e) {
    	values.add(e);
    }
    public E pop() {
    	return values.remove(0);
    }
    public boolean isEmpty() {
    	return values.isEmpty();
    }
    public void pushAll(Iterable<? extends E> src){ //<? extends>实现了泛型的协变，src是producer
        for(E e : src)
            push(e);
    }
    // popAll method without wildcard type - deficient!
    public void popAll(Collection<? super E> dst) { //<? super>实现了泛型的逆变，dst是consumer
        while (!isEmpty())
            dst.add(pop());   
    }
    
}

public class TestPECS {

	public static void main(String[] args) {
		Stack<Number> stackNumber = new Stack<>();
		
		List<Integer> integers = new ArrayList<>();
		integers.add(15);integers.add(16);
		
		stackNumber.pushAll(integers);
		
		List<Number> floats = new ArrayList<>();
		stackNumber.popAll(floats);
		
		Stack<Number>[] table = (Stack<Number>[])new Stack[10]; 
	}

}
