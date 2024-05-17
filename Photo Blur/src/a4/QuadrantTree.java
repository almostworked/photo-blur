package a4;

public class QuadrantTree {
	QTreeNode root;
	
	public QuadrantTree(int[][] thePixels) { // QuadrantTree's constructor 
		int size = thePixels.length;
		int averageColor = (Gui.averageColor(thePixels, 0, 0, size)); // Get the average colour by invoking Gui method
		root = new QTreeNode(null, 0, 0, size, averageColor); // Initialize the root with the first QTree node
		root.setParent(null); // Root node has no predecessors
		if (size > 1) build(root, thePixels); // build() method is called to build the Quadrant Tree recursively
		
	}
	private void build(QTreeNode node, int[][] pixels) { // Recursive method to build the tree
        int size = node.getSize();
        if (size == 1) { // Base case for the build method, prevents infinite loop and stops building if the node's size is 1 (leaf node)
           node.children = null; // A leaf node has no children
            return;
        }

        int split = size / 2; // The photo's pixels will be recursively divided by two to blur the overall image
        int x = node.getx(); // Get the current node's x and y coordinates
        int y = node.gety();

        node.children = new QTreeNode[4]; // Initialize the node's children
        // The children are created at each quadrant
        // children[0] (the first child) is set with the generic x and y coordinates, split (size / 2) and average colour because it is the upper left sub-quadrant of the quadrant Q
        node.children[0] = new QTreeNode(new QTreeNode[4], x, y, split, Gui.averageColor(pixels, x, y, split));
        // children[1] (second child) is the upper right node, so x = x + split
        node.children[1] = new QTreeNode(new QTreeNode[4], x + split, y, split, Gui.averageColor(pixels, x + split, y, split));
        // children[2] (third child) is the bottom left node, so y = y + split
        node.children[2] = new QTreeNode(new QTreeNode[4], x, y + split, split, Gui.averageColor(pixels, x, y + split, split));
        // Finally, children[3] (fourth child) is the bottom right node, so x = x + split and y = y + split
        node.children[3] = new QTreeNode(new QTreeNode[4], x + split, y + split, split, Gui.averageColor(pixels, x + split, y + split, split));

        for (QTreeNode child : node.children) {
            if (child != null) {
                child.setParent(node); // Set each parent of the child to the node (ensure no child has a null parent)
            }
        }

        for (QTreeNode child : node.children) {
            if (child != null) { // Recursively build each child
                build(child, pixels);
            }
        }
    }
	public QTreeNode getRoot() { // getRoot() method returns the root of this quadrant tree
		return this.root;
	}
	public ListNode<QTreeNode> getPixels(QTreeNode r, int theLevel) {
	    ListNode<QTreeNode> list = new ListNode<>(null);
	    traverseTree(r, theLevel, 0, list);
	    return list.getNext();
	}
	private void traverseTree(QTreeNode node, int theLevel, int currentLevel, ListNode<QTreeNode> list) {
	    if (node == null) { // Base case for traverseTree method, returns if the current node is null
	        return;
	    }
	    if (currentLevel == theLevel) {
	        while (list.getNext() != null) { // Traverse to the end of the list
	            list = list.getNext();
	        }
	        list.setNext(new ListNode<>(node)); // The node gets added to the list
	        return;
	    }
	    // If the node is a leaf but we haven't reached the desired level, return
	    if (node.children == null) {
	        return;
	    }
	    // Recursively call the method on all the children that aren't null
	    for (int i = 0; i < node.children.length; i++) {
	        traverseTree(node.children[i], theLevel, currentLevel + 1, list);
	    }
	}

	public Duple findMatching(QTreeNode r, int theColor, int theLevel) {
	    Duple result = new Duple();
	    traverseTree2(r, theColor, theLevel, 0, result); // Call the recursive helper method below (traverseTree2)
	    return result; // Return the Duple object
	}

	private void traverseTree2(QTreeNode node, int theColor, int theLevel, int currentLevel, Duple result) {
	    if (node == null) {
	        return; // The base case for this method is node == null
	    }
	    if (Gui.similarColor(node.getColor(), theColor) && (currentLevel == theLevel || node.children == null)) { // If the node has similar colour to theColor and same same level as...
	        ListNode<QTreeNode> newNode = new ListNode<>(node); // ...theLevel, or if the node has no children, continue ->
	        if (result.getFront() == null) { //...-> Set the front of the Duple object to the newNode being created 
	            result.setFront(newNode);
	        } else {
	            ListNode<QTreeNode> current = result.getFront(); // Initialize current point to the front of the Duple
	            while (current.getNext() != null) { // Ensure there is a next node
	                current = current.getNext(); // Current becomes the next node
	            }
	            current.setNext(newNode); // Set current's next pointer to the new node
	        }
	        result.setCount(result.getCount() + 1); // Set the count of the Duple to the initial count, but increment 
	    }
	    if (node.children != null) {
	        for (int i = 0; i < node.children.length; i++) { // Iterate through the node's children
	            if (node.children[i] != null) {
	                traverseTree2(node.children[i], theColor, theLevel, currentLevel + 1, result); // Recursively call the method until node is null
	            }
	        }
	    }
	}

	public QTreeNode findNode(QTreeNode r, int theLevel, int x, int y) {
	    // Base case: if the node is null or if we've reached the desired level
	    if (r == null || theLevel == 0) {
	        // Check if the point (x, y) is within the quadrant of the current node
	        if (r != null && r.contains(x, y)) {
	            return r; // The point is within this quadrant
	        } else {
	            return null; // The point is not within this quadrant or the node is null
	        }
	    }

	    // Recursive case: traverse to the next level
	    if (r.children != null) { // Ensure children array is not null
	        for (int i = 0; i < r.children.length; i++) { // Use length of array (avoid an out of bounds error)
	            QTreeNode child = r.children[i];
	            if (child != null && child.contains(x, y)) {
	                // Call the method recursively with the child node, decrementing the level
	                return findNode(child, theLevel - 1, x, y);
	            }
	        }
	    }

	    return null; // The point is not within any of the children's quadrants at the specified level
	}
	public static void goose(String[] args) {
		int[][] pixels = new int[1][1];
		pixels[0][0] = 8;
		QuadrantTree tree = new QuadrantTree(pixels);
	
	}

}
