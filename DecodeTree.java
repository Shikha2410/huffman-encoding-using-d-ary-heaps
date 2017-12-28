/*
 * Decode tree definition
 */


class DTreeNode
{
	Integer value;
	DTreeNode leftChild;
	DTreeNode rightChild;
	
	public DTreeNode( Integer val, DTreeNode lc, DTreeNode rc) {
		value=val;
		leftChild=lc;
		rightChild=rc;
	}
}

class DecodeTree 
{
	DTreeNode root;
	DecodeTree()
	{
		root=null;
	}
}
