/*
 * Huffman tree definition
 */

class HuffmanNode
{
	Integer key_freq;
	Integer value;
	HuffmanNode leftChild;
	HuffmanNode rightChild;
	
	public HuffmanNode(Integer freq, Integer val, HuffmanNode lc, HuffmanNode rc) {
		key_freq=freq;
		value=val;
		leftChild=lc;
		rightChild=rc;
	}
}

class HuffmanTree
{
	HuffmanNode root;
	HuffmanTree()
	{
		root=null;
	}
}