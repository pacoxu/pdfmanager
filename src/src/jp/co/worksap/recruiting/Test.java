package jp.co.worksap.recruiting;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExamPeekableQueueImpl<Integer> epqi = new ExamPeekableQueueImpl<Integer>();
		epqi.enqueue(1);
		epqi.enqueue(9);
		epqi.enqueue(10);
		epqi.enqueue(12);
		epqi.enqueue(3);
		epqi.enqueue(14);
		epqi.enqueue(34);
		epqi.enqueue(24);
		System.out.println(epqi.peekMaximum());
		System.out.println(epqi.peekMedian());
		System.out.println(epqi.peekMinimum());
		System.out.println(epqi.dequeue());
		ExamImmutableQueueImpl<Integer> dd = new ExamImmutableQueueImpl<Integer>(); 
		dd.enqueue(1);
		dd.enqueue(11);
		dd.enqueue(12);
		dd.enqueue(1);
		dd.enqueue(4);
		System.out.println( dd.peek()) ;
		System.out.println( dd.peek()) ;
		System.out.println( dd.dequeue().peek()) ;
	}

}
