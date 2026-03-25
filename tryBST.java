import java.util.Random;

class tNode {
    int data;
    tNode left, right;
}
public tNode(int data){
    this.data = data;
    this.left = null;
    this.right = null;
}
class BinarySearchTree{
    private tNode root;
}
public BinarySearchTree(){
    root = null;
}
// Insert method
public void insert(int value) {
    root = insertRec(root, value);
}
private tNode insertRec(tNode root, int value){
    if (root == null) {
        return new tNode(value);
    }

    if (value < root.data) {
        root.left = insertRec(root.left, value);
    } else if (value > root.data) {
        root.right = insertRec(root.right, value);
    }

    return root;
}
// Check if tree is BST
public boolean isBST(){
    return isBSTRec(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
}
private boolean isBSTRec(tNode node, int min, int max){
    if (node == null) {
        return true;
    }

    if (node.data < min || node.data > max) {
        return false;
    }

    return isBSTRec(node.left, min, node.data - 1) &&
            isBSTRec(node.right, node.data + 1, max);
}
