/*
 *   Priority queue: pairing heap implementation
 */

/** Class Heap Node **/
class HeapNode
{
	HuffmanNode element;
	HeapNode leftChild;
	HeapNode nextSibling;
	HeapNode previous;
 
    /** Constructor **/
    public HeapNode(HuffmanNode x)
    {
        element = x;
        leftChild = null;
        nextSibling = null;
        previous = null;
    }
}
 
/** Class Pairing Heap **/
class PairingHeap
{
    HeapNode root; 
    HeapNode[] heap = new HeapNode[ 5 ];
    
    /** Constructor **/
    public PairingHeap()
    {
        root = null;
    }
    
    /** Returns true if heap is empty, and false otherwise **/
    public boolean isEmpty() 
    {
        return root == null;
    }
    
    /** Inserts tree with root node z into the min heap **/
    public HeapNode insert(HuffmanNode z)
    {
    	HeapNode newNode = new HeapNode(z);
        if (root == null)
            root = newNode;
        else
            root = compareAndLink(root, newNode);
        return newNode;
    }
    
    /** Performs compareLink operation **/
    private HeapNode compareAndLink(HeapNode first, HeapNode second)
    {
        if (second == null)
            return first;
 
        if (second.element.key_freq < first.element.key_freq)
        {
            second.previous = first.previous;
            first.previous = second;
            first.nextSibling = second.leftChild;
            if (first.nextSibling != null)
                first.nextSibling.previous = first;
            second.leftChild = first;
            return second;
        }
        else
        {
            second.previous = first;
            first.nextSibling = second.nextSibling;
            if (first.nextSibling != null)
                first.nextSibling.previous = first;
            second.nextSibling = first.leftChild;
            if (second.nextSibling != null)
                second.nextSibling.previous = second;
            first.leftChild = second;
            return first;
        }
    }
    
    /** Combines siblings beginning at firstSibling **/
    private HeapNode combineSiblings(HeapNode firstSibling)
    {
        if(firstSibling.nextSibling == null)
            return firstSibling;
        int countOfSiblings = 0;
        for (;firstSibling != null;countOfSiblings++)
        {
            heap = increaseSizeIfFull(heap,countOfSiblings);
            heap[ countOfSiblings ] = firstSibling;
            firstSibling.previous.nextSibling = null;  
            firstSibling = firstSibling.nextSibling;
        }
        heap = increaseSizeIfFull(heap, countOfSiblings);
        heap[countOfSiblings] = null;
        int i = 0;
        for ( ;i + 1 < countOfSiblings; i += 2)
            heap[i] = compareAndLink(heap[i], heap[i+1]);
        int j = i-2;
        if (j == countOfSiblings-3)
            heap[j] = compareAndLink( heap[j], heap[j+2]);
        for ( ;j >= 2; j -= 2)
            heap[j-2] = compareAndLink(heap[j-2], heap[j]);
        return heap[0];
    }
    
    /** Doubles the size of the tree array **/
    private HeapNode[] increaseSizeIfFull(HeapNode[] arr, int index)
    {
        if (index == arr.length)
        {
        	HeapNode[] oldArr = arr;
            arr = new HeapNode[index*2];
            for(int i = 0; i < index; i++)
                arr[i] = oldArr[i];
        }
        return arr;
    }
    
    /** Removes tree from heap with minimum frequency value at root 
     * Returns the root node**/
    public HuffmanNode removeMin()
    {
        if (isEmpty())
            return null;
        HuffmanNode z = root.element;
        if (root.leftChild == null)
            root = null;
        else
            root = combineSiblings(root.leftChild);
        return z;
    }
}