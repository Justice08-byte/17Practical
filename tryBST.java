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
// Build perfect BST using divide and conquer
public void buildPerfectBST(int[] numbers, int start, int end) {
    if (start > end) {
        return;
    }

    int mid = start + (end - start) / 2;
    insert(numbers[mid]);

    buildPerfectBST(numbers, start, mid - 1);
    buildPerfectBST(numbers, mid + 1, end);
}
// Delete nodes with even values
public void deleteEvenNumbers(){
    root = deleteEvenRec(root);
}
private tNode deleteEvenRec(tNode node){
    if (node == null) {
        return null;
    }

    node.left = deleteEvenRec(node.left);
    node.right = deleteEvenRec(node.right);

    if (node.data % 2 == 0) {
        return deleteNode(node);
    }

    return node;
}
private tNode deleteNode(tNode node){
    // Case 1: Leaf node
    if (node.left == null && node.right == null) {
        return null;
    }

    // Case 2: Only right child
    if (node.left == null) {
        return node.right;
    }

    // Case 3: Only left child
    if (node.right == null) {
        return node.left;
    }

    // Case 4: Two children - find inorder successor
    tNode successor = findMin(node.right);
    node.data = successor.data;
    node.right = deleteNodeRec(node.right, successor.data);

    return node;
}