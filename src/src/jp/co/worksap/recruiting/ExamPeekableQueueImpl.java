package jp.co.worksap.recruiting;

import java.util.NoSuchElementException;

public class ExamPeekableQueueImpl<E extends Comparable<E>> implements ExamPeekableQueue<E> {

	Object[] array ;
	int head;
	int tail;
	
	ExamPeekableQueueImpl(){
		array = new Object[10000];//Integer.MAX_VALUE
		head = 0;
		tail = 0;
	}
	
	@Override
	public void enqueue(E e) {
		if( tail == Integer.MAX_VALUE){
			if(head == 0){
				//4GB, more or less
				throw new ArrayIndexOutOfBoundsException();				
			}else{
				for( int i = 0; i < tail - head; i ++ ){
					array[i] = array[head + i ];
				}
				tail = tail - head;
				tail = 0 ;
			}
		}
		if(e==null)
			throw new IllegalArgumentException();
		array[tail] = e;
		tail++;
	}

	@Override
	public E dequeue() {
		if( tail - head == 0)
			throw new NoSuchElementException();
		else{
			head++;
			return (E) array[head-1];
		}
	}

	@Override
	public int size() {
		return tail - head;
	}

	@Override
	public E peekMedian() {
		return findMedian(array, head, tail );
	}

	private E findMedian(Object[] clone, int head2, int tail2) {
		int size = tail2 - head2 ;
		int m = size/2 ;
		return find(clone, head2, m, tail2);		
	}

	private E find(Object[] clone, int head2, int m, int tail2) {
		E tem = (E)clone[tail2-1];
		Object[] tem1 = new Object[tail2-head2];
		Object[] tem2 = new Object[tail2-head2];
		int j = 0;
		int k = 0;
		for(int i = head2; i < tail2 -1; i ++){
			if(((E)array[i]).compareTo(tem) < 0 ){
				tem1[j] = array[i];
				j++;
			}else{
				tem2[k] = array[i];
				k++;
			}
		}
		if(j == m){
			return tem;
		}else if( j > m){
			return find(tem1, 0, m, j);
		}else{
			return find(tem2, 0, m-j-1 , k);
		}		
	}

	@Override
	public E peekMaximum() {
		E max = (E) array[tail-1];
		for(int i = head; i < tail-1; i ++){
			if(((E)array[i]).compareTo(max) > 0 ){
				max = (E)array[i];
			}
		}
		return max;
	}

	@Override
	public E peekMinimum() {
		E min = (E) array[tail-1];
		for(int i = head; i < tail-1; i ++){
			if(((E)array[i]).compareTo(min) < 0 ){
				min = (E)array[i];
			}
		}
		return min;
	}

}
