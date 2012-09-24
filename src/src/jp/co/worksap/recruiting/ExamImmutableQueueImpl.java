package jp.co.worksap.recruiting;

import java.util.NoSuchElementException;

public class ExamImmutableQueueImpl<E> implements ExamimmutableQueue<E> {

	Object[] array ;
	int head;
	int tail;
	
	ExamImmutableQueueImpl(){
		array = new Object[10000];//Integer.MAX_VALUE
		head = 0;
		tail = 0;
	}
	
	@Override
	public ExamimmutableQueue<E> enqueue(E e) {
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
		return this;
	}

	@Override
	public ExamimmutableQueue<E> dequeue() {
		if( tail - head == 0)
			throw new NoSuchElementException();
		else{
			head++;
			return this;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public E peek() {
		if( tail - head == 0)
			throw new NoSuchElementException();
		else{
			return (E) array[head];
		}
	}

	@Override
	public int size() {
		return tail - head;
	}

}
