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

public BinarySearchTree() {
    root = null;
}

// Insert method
    public void insert ( int value){
        root = insertRec(root, value);
    }
    private tNode insertRec (tNode root,int value){
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
    public boolean isBST () {
        return isBSTRec(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    private boolean isBSTRec (tNode node,int min, int max){
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
    public void buildPerfectBST ( int[] numbers, int start, int end){
        if (start > end) {
            return;
        }

        int mid = start + (end - start) / 2;
        insert(numbers[mid]);

        buildPerfectBST(numbers, start, mid - 1);
        buildPerfectBST(numbers, mid + 1, end);
    }
// Delete nodes with even values
    public void deleteEvenNumbers () {
        root = deleteEvenRec(root);
    }
    private tNode deleteEvenRec (tNode node){
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
    private tNode deleteNode (tNode node){
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
    private tNode findMin (tNode node){
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    private tNode deleteNodeRec (tNode root,int value){
        if (root == null) {
            return null;
        }

        if (value < root.data) {
            root.left = deleteNodeRec(root.left, value);
        } else if (value > root.data) {
            root.right = deleteNodeRec(root.right, value);
        } else {
            // Found the node to delete
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            tNode successor = findMin(root.right);
            root.data = successor.data;
            root.right = deleteNodeRec(root.right, successor.data);
        }

        return root;
    }
//Reset tree
    public void reset () {
        root = null;
    }
// For debugging - inorder traversal
    public void inorder () {
        inorderRec(root);
        System.out.println();
    }
    private void inorderRec (tNode root){
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.data + " ");
            inorderRec(root.right);
        }
    }
// count nodes (for verification)
    public int countNodes () {
        return countNodesRec(root);
    }
    private int countNodesRec (tNode node){
        if (node == null) {
            return 0;
        }
        return 1 + countNodesRec(node.left) + countNodesRec(node.right);
    }
}
class TimingResult{
    double average;
    double stdDev;

    TimingResult(double average, double stdDev) {
        this.average = average;
        this.stdDev = stdDev;
    }
}
public class tryBST{
    private static int[] generateSortedNumbers(int numKeys){
        int[] numbers = new int[numKeys];
        for (int i = 0; i < numKeys; i++) {
            numbers[i] = i + 1;
        }
        return numbers;
    }
    private static TimingResult calculateStatistics(long[] times){
        double sum = 0;
        for (long time : times) {
            sum += time;
        }
        double mean = sum / times.length;

        double variance = 0;
        for (long time : times) {
            variance += Math.pow(time - mean, 2);
        }
        variance /= times.length;
        double stdDev = Math.sqrt(variance);

        return new TimingResult(mean, stdDev);
    }
    private static TimingResult measurePopulateTime(int numKeys, int repetitions){
        long[] times = new long[repetitions];

        for (int i = 0; i < repetitions; i++) {
            BinarySearchTree tree = new BinarySearchTree();
            int[] numbers = generateSortedNumbers(numKeys);

            long startTime = System.nanoTime();
            tree.buildPerfectBST(numbers, 0, numbers.length - 1);
            long endTime = System.nanoTime();

            times[i] = (endTime - startTime) / 1000000; // Convert to milliseconds

            // Verify BST property
            if (!tree.isBST()) {
                System.out.println("Warning: Tree is not a BST at repetition " + (i+1));
            }
        }

        return calculateStatistics(times);
    }
}