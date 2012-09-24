package com.xplore.pdf;

import java.util.NoSuchElementException;

import jp.co.worksap.recruiting.ExamPeekableQueue;

public class CopyOfExamPeekableQueueImpl<E extends Comparable<E>> implements ExamPeekableQueue<E> {
	
	AVLNode<E> median;
	AVLNode<E> head;
	AVLNode<E> tail;

	CopyOfExamPeekableQueueImpl(){
		
	}

	CopyOfExamPeekableQueueImpl(E e){
		median = head = tail  = new AVLNode<E>(e);
	}
	
	private AVLNode<E> input;
	
	@Override
	public void enqueue(E e) {
		input =  new AVLNode<E>(e);
		tail.next = input;
		tail = tail.next;
		if(median == null){
			median = head = tail  = new AVLNode<E>(e);			
		}
		else{
			insert();			
		}		
	}
	private void insertTem() {
		tem.bigger = null;
		tem.smaller = null;
		tem.father = null;		
		if( median.e.compareTo(tem.e) > 0 ){
			if(median.small <= median.big){
				median.right(tail);
				median.small++;
			}else{
				AVLNode<E> nextMedian = median.smaller;
				while(nextMedian.bigger != null){
					nextMedian = nextMedian.bigger;
				}
				if(nextMedian.e.compareTo(tem.e) <= 0 ){
					tem.smaller = median.smaller;
					tem.bigger = median.bigger;
					tem.big = median.big;
					tem.small = median.small;
					median.bigger = null;
					median.smaller = null;
					median.big = 0;
					median.small = 0;
					tem.left(median);
					median = tem;
				}else{
					if( nextMedian.father.bigger != null)
						nextMedian.father.bigger = null;
//					else
//						nextMedian.father.smaller = nextMedian.smaller;
					nextMedian.father = null;
					nextMedian.bigger = median.bigger;
					nextMedian.big = median.big;
					nextMedian.small = median.small;
					median.smaller = null;
					median.bigger = null;
					nextMedian.big = 0;
					nextMedian.small = 0;					
					nextMedian.left(median);
					median = nextMedian;
				}					
			}
		}else{
			if(median.small >= median.big){
				median.left(tail);
				median.big++;
			}else{
				AVLNode<E> nextMedian = median.bigger;
				while(nextMedian.smaller != null){
					nextMedian = nextMedian.smaller;
				}
				if(nextMedian.e.compareTo(tem.e) >= 0 ){
					tem.smaller = median.smaller;
					tem.bigger = median.bigger;
					tem.big = median.big;
					tem.small = median.small;
					median.bigger = null;
					median.smaller = null;
					median.big = 0;
					median.small = 0;
					tem.right(median);
					median = tem;
				}else{
					if( nextMedian.father.smaller != null)
						nextMedian.father.smaller = null;
//					else
//						nextMedian.father.smaller = nextMedian.smaller;
					nextMedian.father = null;
					nextMedian.bigger = median.bigger;
					nextMedian.big = median.big;
					nextMedian.small = median.small;
					median.smaller = null;
					median.bigger = null;
					nextMedian.big = 0;
					nextMedian.small = 0;					
					nextMedian.right(median);
					median = nextMedian;
				}								
			}
		}				
		
	}
	private void insert() {
		input.bigger = null;
		input.smaller = null;
		input.father = null;		
		if( median.e.compareTo(input.e) > 0 ){
			if(median.small <= median.big){
				median.right(tail);
				median.small++;
			}else{
				AVLNode<E> nextMedian = median.smaller;
				while(nextMedian.bigger != null){
					nextMedian = nextMedian.bigger;
				}
				if(nextMedian.e.compareTo(input.e) <= 0 ){
					input.smaller = median.smaller;
					input.bigger = median.bigger;
					input.big = median.big;
					input.small = median.small;
					median.bigger = null;
					median.smaller = null;
					median.big = 0;
					median.small = 0;
					input.left(median);
					median = input;
				}else{
					if( nextMedian.father.bigger != null)
						nextMedian.father.bigger = null;
//					else
//						nextMedian.father.smaller = nextMedian.smaller;
					nextMedian.father = null;
					nextMedian.bigger = median.bigger;
					nextMedian.big = median.big;
					nextMedian.small = median.small;
					median.smaller = null;
					median.bigger = null;
					nextMedian.big = 0;
					nextMedian.small = 0;					
					nextMedian.left(median);
					median = nextMedian;
				}					
			}
		}else{
			if(median.small > median.big){
				median.left(tail);
				median.big++;
			}else{
				AVLNode<E> nextMedian = median.bigger;
				while(nextMedian.smaller != null){
					nextMedian = nextMedian.smaller;
				}
				if(nextMedian.e.compareTo(input.e) >= 0 ){
					input.smaller = median.smaller;
					if( input != median.bigger){
						input.bigger = median.bigger;
						input.big = median.big;
						input.small = median.small;
						median.bigger = null;
						median.smaller = null;
						median.big = 0;
						median.small = 0;
						input.right(median);
						median = input;
					}
						
				}else{
					if( nextMedian.father.smaller != null)
						nextMedian.father.smaller = null;
//					else
//						nextMedian.father.smaller = nextMedian.smaller;
					nextMedian.father = null;
					if( nextMedian != median.bigger){
						nextMedian.bigger = median.bigger;
						nextMedian.big = median.big;
						nextMedian.small = median.small;
						median.smaller = null;
						median.bigger = null;
						nextMedian.big = 0;
						nextMedian.small = 0;					
						nextMedian.right(median);
						median = nextMedian;
					}
				}								
			}
		}				
	}

	@Override
	public E dequeue() {
		if(head == null){
			throw new NoSuchElementException();
		}
		E result = head.e;
		if( head == tail ){
			median = head = tail  = null;
		}else{
			if(head.father == null){
				deleteMedian();
			}else{
				tem = median;
				deleteMedian();
				delete();
				insertTem();
			}			
			head = head.next;	
		}
		return result;
	}

	private AVLNode<E> tem;
	
	private void delete() {
		if(head.bigger == null){
			AVLNode<E> f = head.father;
			if(f.e.compareTo(head.e) >= 0){
				f.smaller = head.smaller;
				f.small--;
			}else{
				f.bigger = head.smaller;
				f.big--;
			}
		}else{
			AVLNode<E> nextMedian = head.bigger;
			while(nextMedian.smaller != null){
				nextMedian = nextMedian.smaller;
			}
			if(nextMedian.father.smaller == null){
				nextMedian.father.bigger = null;
				nextMedian.father.big--;
			}
			else{
				nextMedian.father.smaller = null;
				nextMedian.father.small--;
			}
			if( nextMedian != head.bigger){
				nextMedian.bigger = head.bigger;
				nextMedian.smaller = head.smaller;
				nextMedian.big = head.big;
				nextMedian.small = head.small;
				AVLNode<E> f = head.father;
				if(f.e.compareTo(head.e) >= 0){
					f.smaller = nextMedian;
					f.small--;
				}else{
					f.bigger = nextMedian;
					f.big--;
				}

			}
		}
	}

	private void deleteMedian() {
		if( head.small <= head.big ){
			AVLNode<E> nextMedian = median.bigger;
			while(nextMedian.smaller != null){
				nextMedian = nextMedian.smaller;
			}
			median.bigger.father = nextMedian;
			if(median.smaller != null)
				median.smaller.father = nextMedian;
			if( nextMedian != median.bigger){
				nextMedian.father.smaller = null;
				nextMedian.big = median.big - 1;
				nextMedian.small = median.small;
				nextMedian.bigger = median.bigger;
				nextMedian.smaller = median.smaller;
				median = nextMedian;
			}
		}else{
			AVLNode<E> nextMedian = median.smaller;
			while(nextMedian.bigger != null){
				nextMedian = nextMedian.bigger;
			}
			median.smaller.father = nextMedian;
			if(median.bigger != null)
				median.bigger.father = nextMedian;
			if( nextMedian != median.bigger){
				nextMedian.father.smaller = null;
				nextMedian.big = median.big;
				nextMedian.small = median.small - 1;
				nextMedian.bigger = median.bigger;
				nextMedian.smaller = median.smaller;
				median = nextMedian;					
			}
		}
	}

	@Override
	public E peekMedian() {
		return median.e;
	}

	@Override
	public E peekMaximum() {
		AVLNode<E> tem = median;
		while(tem.bigger != null){
			tem = tem.bigger;
		}
		return tem.e;
	}

	@Override
	public E peekMinimum() {
		AVLNode<E> tem = median;
		while(tem.smaller != null){
			tem = tem.smaller;
		}
		return tem.e;
	}

	@Override
	public int size() {
		return median.small+median.big+1;
	}
	
	@SuppressWarnings("hiding")
	class AVLNode<E extends Comparable<E>> {
		E e;
		AVLNode<E> bigger;
		AVLNode<E> smaller;
		AVLNode<E> father;
		AVLNode<E> next;
		int big = 0 ;
		int small = 0;
		AVLNode(E n){
			e = n;
		}
		public void right(AVLNode<E> tail) {
			if(smaller == null){
				smaller = tail;
				smaller.father = this;
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
		private void left(AVLNode<E> tail) {
			if(bigger == null){
				bigger = tail;
				bigger.father = this;
			}
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
