package fourteendays.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 二叉树，操作由递归迭代实现
 * @author zhangyan
 */
public class BinaryTree<T> {
    /**
     * Tree的根节点。树只需要返回根节点就可以遍历整棵树
     */
    private TreeNode<T> root;

    /**
     * 初始化一棵树就是初始化它的根节点
     */
    public BinaryTree(TreeNode<T> root) {
        this.root = root;
    }

    /**
     * 用数据内容初始化一棵树
     */
    public BinaryTree(T data) {
        this.root = new TreeNode<>(0, data);
    }

    /**
     * 构造一棵空树
     */
    public BinaryTree() {
        this.root = null;
    }

    protected void setRoot(TreeNode<T> root) {
        this.root = root;
    }

    /**
     * 构建二叉树，简单方式构建一个String类型的二叉树。创建每一个节点，然后再指定每个节点的左右子树
     *       A
     *   B      C
     * D   E       F
     *            G
     */
    void createBinaryTreeInSimpleWay(T[] data) {
        int lenSimpleTreeConstruct = 7;
        if (data.length != lenSimpleTreeConstruct) {
            System.err.println("简单构造方法的参数值应该为 [A, B, C, D, E, F, G]");
            return;
        }

        TreeNode<T> nodeA = new TreeNode<>(0, data[0]);
        TreeNode<T> nodeB = new TreeNode<>(1, data[1]);
        TreeNode<T> nodeC = new TreeNode<>(2, data[2]);
        TreeNode<T> nodeD = new TreeNode<>(3, data[3]);
        TreeNode<T> nodeE = new TreeNode<>(4, data[4]);
        TreeNode<T> nodeF = new TreeNode<>(5, data[5]);
        TreeNode<T> nodeG = new TreeNode<>(6, data[6]);

        setRoot(nodeA);

        nodeA.leftChild = nodeB; nodeA.rightChild = nodeC;
        nodeB.leftChild = nodeD; nodeB.rightChild = nodeE;
        nodeC.rightChild = nodeF;
        nodeF.leftChild = nodeG;
    }

    /**
     * 求二叉树的高度，树的高度就是值根节点到所有叶子节点距离的最大值
     *
     */
    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(TreeNode<T> node) {
        // 迭代求高度
        if (node == null || (node.getLeftChild() == null && node.getRightChild() == null)) {
            return 0;
        }
        int leftHeight = getHeight(node.leftChild);
        int rightHeight = getHeight(node.rightChild);

        return (leftHeight > rightHeight) ? leftHeight + 1 : rightHeight + 1;
    }

    /**
     * 获取二叉树的节点数
     * @return 一棵二叉树的节点数
     */
    public int getTreeSize() {
        return getTreeSize(root);
    }

    private int getTreeSize(TreeNode<T> node) {
        // 迭代获取一棵树的节点数
        if (node == null) {
            return 0;
        }
        int leftTreeSize = getTreeSize(node.leftChild);
        int rightTreeSize = getTreeSize(node.rightChild);

        return leftTreeSize + rightTreeSize + 1;
    }

    public void preOrderTraversal() {
        preOrderTraversal(root);
    }
    private void preOrderTraversal(TreeNode<T> node) {
        if (node == null) {
            return;
        }

        System.out.println(node.getData());

        preOrderTraversal(node.getLeftChild());
        preOrderTraversal(node.getRightChild());
    }

    final String NULL_NODE_DATA = "#";
    /**
     * 通过前序遍历的数据序列反向生成二叉树
     * 叶子节点的左右子节点用 '#' 表示
     * 前序数据序列为 A B D # # E # # C # F G # # #
     *        A
     *    B       C
     *  D   E       F
     *             G
     *
     * @param data 输入的序列，叶子节点的左右子节点用 '#' 表示
     */
    public void createTreeWithPreorderSeq(T[] data) {
        List<T> dataList = new ArrayList<>(data.length);
        Collections.addAll(dataList, data);

        root = createTreeNodeWithPreorder(data.length, dataList);
    }

    /**
     * 迭代创建树
     * 1. 输入序列为空时，返回 null，否则进入2
     * 2. 将序列中的第一个元素取出后，创建一个TreeNode节点。如果值为"#"，则返回null；否则，进入3
     * 3. 当前节点的index值设定为原始序列size减去当前序列的size，
     *    当前节点的data 为当前序列第一个元素的值，以此创建TreeNode节点，当前序列删除第一个元素，进入4
     * 4. 输入原始序列的size和当前序列迭代创建节点的左子节点和右子节点
     *
     * @param size 输入原始序列的size
     * @param data 输入的序列
     */
    private TreeNode<T> createTreeNodeWithPreorder(int size, List<T> data) {
        if (data.isEmpty()) {
            return null;
        }
        int index = size - data.size();
        T nodeData = data.get(0);
        data.remove(0);

        if (NULL_NODE_DATA.equals(nodeData.toString())) {
            return null;
        }
        TreeNode<T> node = new TreeNode<>(index, nodeData);
        node.leftChild = createTreeNodeWithPreorder(size, data);
        node.rightChild = createTreeNodeWithPreorder(size, data);

        return node;
    }

    /**
     * 定义TreeNode类。Tree是由TreeNode节点组成的
     * @param <T>
     */
    static class TreeNode<T> {
        private int index;   // 节点索引
        private T data;      // 节点值
        private TreeNode<T> leftChild;   // 节点左子树
        private TreeNode<T> rightChild;  // 节点右子树

        TreeNode() {
            this.index = -1;
        }

        TreeNode(int index, T data) {
            this.index = index;
            this.data = data;
            this.leftChild = null;
            this.rightChild = null;
        }

        public int getIndex() {
            return index;
        }

        public T getData() {
            return data;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public void setData(T data) {
            this.data = data;
        }

        public void setLeftChild(TreeNode<T> leftChild) {
            this.leftChild = leftChild;
        }

        public void setRightChild(TreeNode<T> rightChild) {
            this.rightChild = rightChild;
        }

        public TreeNode<T> getLeftChild() {
            return leftChild;
        }

        public TreeNode<T> getRightChild() {
            return rightChild;
        }
    }

    public static void main(String[] args) {
        BinaryTree<String> tree = new BinaryTree<>();
        String[] nodeValues = {"A", "B", "C", "D", "E", "F", "G"};
        tree.createBinaryTreeInSimpleWay(nodeValues);

        int height = tree.getHeight();
        System.out.println("Tree Height: " + height);

        int size = tree.getTreeSize();
        System.out.println("Tree Size: " + size);
        System.out.println("==========================");
        BinaryTree<String> tree2 = new BinaryTree<>();
        String[] nodeValues2 = {"A", "B", "D", "#", "#", "E", "#", "#", "C", "#", "F", "G", "#", "#", "#"};
        System.out.println("前序遍历序列反向生成二叉树，前序序列为: ");
        for (String value: nodeValues2) {
            System.out.print(value + " ");
        }
        System.out.println();
        tree2.createTreeWithPreorderSeq(nodeValues2);
        tree2.preOrderTraversal();

    }
}
