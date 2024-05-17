package a4;

public class QTreeNode {
	int x, y;; // Coordinates of the upper left corner of the quadrant
	int size; // Size of the quadrant
	int color; // Avg colour of pixels stored in the quadrant
	QTreeNode parent; // Parent of this QTreeNode object
	QTreeNode[] children; // Children of this QTreeNode object: internal node has 4 children; leaf node has no children
	
	public QTreeNode() { // First constructor for QTreeNode
		this.parent = null;
		this.children = new QTreeNode[4]; // Every entry set to null
		this.x = 0;
		this.y = 0;
		this.size = 0;
		this.color = 0;
		
	}
	public QTreeNode(QTreeNode[] theChildren, int xcoord, int ycoord, int theSize, int theColor) { // Second constructor for QTreeNode
		this.children = theChildren; // Set all of the QTreeNode variables to the parameters
		this.x = xcoord;
		this.y = ycoord;
		this.size = theSize;
		this.color = theColor;
	}
	public boolean contains (int xcoord, int ycoord) { // Return true if the point is contained inside the quadrant
		int xMax = x + size - 1; // Calculate the maximum x and y coordinates, since these will be the boundaries of the quadrant for..
        int yMax = y + size - 1; // ...which we will check if the point lies in
        return xcoord >= x && xcoord <= xMax && ycoord >= y && ycoord <= yMax; // If the points lies anywhere within the bounderies of the quadrant, return true
	
    }
	public int getx() { // Getter methods for x, y, size, color and parent
		return x;
	}
	public int gety() {
		return y;
	}
	public int getSize() {
		return size;
	}
	public int getColor() {
		return color; 
	}
	public QTreeNode getParent() {
		return parent;
	}
	public QTreeNode getChild(int index) throws QTreeException { // Getter method for child at specified index
		if (children == null || index < 0 || index > 3) { // If children is null or the index is less than 0/greater than 3, throw exception
			throw new QTreeException("children is null/bad index");
		}
		QTreeNode child = children[index];
		return child;
	}
	public void setx (int newx) { // Setter methods for x, y, size, color and parent
		x = newx;
	}
	public void sety (int newy) {
		y = newy;
	}
	public void setSize(int newSize) {
		size = newSize;
	}
	public void setColor(int newColor) {
		color = newColor;
	}
	public void setParent(QTreeNode newParent) {
		parent = newParent;
	}
	public void setChild(QTreeNode newChild, int index) { // Setter method for child at specified idnex
		if (children == null || index < 0 || index > 3) {
			throw new QTreeException("children is null/bad index");
		}
		children[index] = newChild;
	}
	public boolean isLeaf() { // Boolean method checks if a tree node is a leaf (has no children)
		if (children == null) {
			return true;
		}
		for (int i = 0; i < children.length; i++) { // Iterate through children array
			if (children[i] == null) {
				return true; // Return true if one of the entries if null
			}
		}
		return false;
	}
}
