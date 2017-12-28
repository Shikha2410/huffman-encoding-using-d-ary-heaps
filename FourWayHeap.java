/*
 *   Priority queue: 4-way cache optimized heap implementation
 */

/** Class Four Way Heap **/
class FourWayHeap 
{
	int heapSize=0;
	HuffmanNode[] heap;
	
	/** Constructor **/
	public FourWayHeap() 
	{
		heap=new HuffmanNode[20];
		heapSize=0;
	}
	
	/** Returns true if heap is empty, and false otherwise **/
	public boolean isEmpty() 
	{
		return heapSize == 0;
	}
	
	/** Inserts tree with root node z into the min heap **/
	public void insert(HuffmanNode z) 
	{
		if (heapSize==0) 
		{
			heap[0] = z;
			heapSize++;
			return;
		}
		
		if (heapSize == heap.length) 
		{
			HuffmanNode[] newArr = new HuffmanNode[heapSize*2];
			for(int i=0; i<heapSize; i++)
				newArr[i] = heap[i];
			heap = newArr;
		}
		
		int i = heapSize;
		
		for(; heap[(i-1)/4].key_freq > z.key_freq; i=(i-1)/4) 
		{
			if (i==0) 
				break;
			heap[i] = heap[(i-1)/4];
		}
		
		heap[i] = z;
		heapSize++;
	}
	
	/** Removes tree from heap with minimum frequency value at root 
     * Returns the root node**/
	public HuffmanNode removeMin() {
		if (heapSize == 0) 
			throw new java.lang.IllegalStateException("Empty Heap"); 
		
		HuffmanNode toReturn = heap[0];
		HuffmanNode last = heap[heapSize-1];
		
		int minChild;
		
		int i=0;
		
		for(; (i*4)+1 < heapSize; i=minChild) 
		{
			minChild = (i*4)+1;
			if (minChild > heapSize) 
				break;
			int j=1, currentMinChild = minChild;
			for(; j<4; j++) 
			{
				if (minChild+j == heapSize) 
					break;
				if(heap[currentMinChild].key_freq > heap[minChild+j].key_freq)
					currentMinChild = minChild+j;
			}
			
			minChild = currentMinChild;
			if (last.key_freq > heap[minChild].key_freq) 
				heap[i] = heap[minChild];
			else
				break;
		}
		
		heap[i] = last;
		heapSize--;
		return toReturn;
	}
}