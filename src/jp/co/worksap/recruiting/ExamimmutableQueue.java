package jp.co.worksap.recruiting;

public interface ExamimmutableQueue<E>{
	
	public ExamimmutableQueue<E> enqueue(E e);
	
	public ExamimmutableQueue<E> dequeue();
	
	public E peek();
		
	public int size();
	
}
