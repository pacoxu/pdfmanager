package jp.co.worksap.recruiting;

public class ExamPeekableQueueImpl<E extends Comparable<E>> implements ExamPeekableQueue<E> {
	
	Node<E> median;
	Node<E> head;
	Node<E> tail;

	ExamPeekableQueueImpl(){
		
	}

	ExamPeekableQueueImpl(E e){
		median = head = tail  = new Node<E>(e);
	}
	
	@Override
	public void enqueue(E e) {
		if(median == null){
			median = head = tail  = new Node<E>(e);			
		}
		tail = new Node<E>(e);
		if( median.e.compareTo(e) > 0 ){
			if(median.small <= median.big){
				median.right(tail);
				median.small++;
			}else{
				//failuer
//				tail.big = median.big + 1;
//				tail.small = median.small;
//				tail.bigger = median.bigger;
//				tail.smaller = median.smaller;
//				tail.bigger.right(median);
//				median.big = 0;
//				median.small = 0;
//				median.smaller = null;
//				median.bigger = null;
			}
		}else{
			if(median.small > median.big){
				median.left(tail);
				median.big++;
			}else{
//				tail.big = median.big + 1;
//				tail.small = median.small;
//				tail.bigger = median.bigger;
//				tail.smaller = median.smaller;
//				tail.bigger.right(median);
//				median.big = 0;
//				median.small = 0;
//				median.smaller = null;
//				median.bigger = null;
			}
		}
			
			
	}

	@Override
	public E dequeue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E peekMedian() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E peekMaximum() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E peekMinimum() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	@SuppressWarnings("hiding")
	class Node<E extends Comparable<E>> {
		E e;
		Node<E> bigger;
		Node<E> smaller;
		Node<E> next;
		int big = 0 ;
		int small = 0;
		Node(E n){
			e = n;
		}
		public void right(Node<E> tail) {
			if(smaller == null){
				smaller = tail;
			}
			else if(smaller.e.compareTo(tail.e) >= 0 ){
				smaller.right(tail);
				smaller.small++;
			}
			else{
				smaller.left(tail);
				smaller.big++;
			}
		}
		private void left(Node<E> tail) {
			if(bigger == null)
				bigger = tail;
			else if(smaller.e.compareTo(tail.e) >= 0 ){
				bigger.right(tail);
				bigger.small++;
			}
			else{
				bigger.left(tail);
				bigger.big++;
			}
		}
	}
	
}
