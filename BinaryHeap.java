/*
 *   Priority queue: binary heap implementation
 */

import java.util.Vector;
import java.util.NoSuchElementException;

/** Class Binary Heap **/
class BinaryHeap    
{ 
    private Vector<HuffmanNode> heap;
 
    /** Constructor **/    
    public BinaryHeap()
    {
        heap = new Vector<HuffmanNode>();
    }
 
    /** Returns true if heap is empty, and false otherwise **/
    public boolean isEmpty( )
    {
        return heap.size() == 0;
    }
 
    /** Returns index of the parent node of i **/
    private int getParentIndex(int i) 
    {
        return (i - 1)/2;
    }
 
    /** Returns index of the kth child of i **/
    private int getkthChildIndex(int i, int k) 
    {
        return 2 * i + k;
    }
 
    /** Inserts tree with root node z into the min heap **/
    public void insert(HuffmanNode z)
    {
        heap.addElement(z);
    	minHeapifyUp(heap.size() - 1);
    }
 
    /** Removes tree from heap with minimum frequency value at root 
      * Returns the root node**/
    public HuffmanNode removeMin()
    {
    	if (isEmpty())
            throw new NoSuchElementException("Underflow Exception");
        HuffmanNode minItem;
        if(heap.size()>1)
        {
        	minItem = heap.elementAt(0); //since root of the min heap contains the minimum
            heap.setElementAt(heap.elementAt(heap.size()-1), 0);
            heap.setSize(heap.size()-1);
            minHeapifyDown(0);
        }
        else //Only 1 element in the heap
        {
        	minItem = heap.elementAt(0);
        	heap.setSize(heap.size()-1);
        }
        return minItem;
    }
    
    /** Adjusts elements of the heap starting at childIndex in bottom up fashion **/
    private void minHeapifyUp(int childIndex)
    { 
    	HuffmanNode temp = heap.elementAt(childIndex);    
        while (childIndex > 0 && temp.key_freq < heap.elementAt(getParentIndex(childIndex)).key_freq)
        {
        	heap.setElementAt(heap.elementAt(getParentIndex(childIndex)), childIndex);
            childIndex = getParentIndex(childIndex);
        }                   
        heap.setElementAt(temp, childIndex);
    }
 
    /** Adjusts elements of the heap starting at index in top down fashion **/
    private void minHeapifyDown(int index)
    {
        int child;
        HuffmanNode temp = heap.elementAt(index);
        while (getkthChildIndex(index, 1) < heap.size())
        {
            child = minChild(index);
            if (heap.elementAt(child).key_freq < temp.key_freq)
            	heap.setElementAt(heap.elementAt(child), index);
            else
                break;
            index = child;
        }
        heap.setElementAt(temp, index);
    }
 
    /** Returns smallest child of element at index **/
    private int minChild(int index) 
    {
        int minChild = getkthChildIndex(index, 1);
        int k = 2;
        int currentPosition = getkthChildIndex(index, k);
        if (currentPosition < heap.size() && heap.elementAt(currentPosition).key_freq < heap.elementAt(minChild).key_freq) 
                minChild = currentPosition;
        return minChild;
    }
}