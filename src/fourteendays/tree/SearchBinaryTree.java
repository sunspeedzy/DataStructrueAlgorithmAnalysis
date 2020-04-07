package fourteendays.tree;

import java.util.Stack;

/**
 * 查找二叉树
 * @author zhangyan
 */
public class SearchBinaryTree {
    private SearchTreeNode root;
    /**
     * 构建查找二叉树，空方法
     */
    public SearchBinaryTree() {

    }

    /**
     * 创建查找二叉树，添加节点
     * @param data 要添加节点的数据
     * @return 返回新添加的节点
     */
    public SearchTreeNode put(int data) {
        // 用来存储当前节点
        SearchTreeNode node = null;
        // 用来存储当前节点的父节点
        SearchTreeNode parent = null;

        if (root == null) {
            // 空树，则将新添加的节点设置为根节点
            node = new SearchTreeNode(data);
            root = node;
        } else {
            node = root;
            // 循环查找新节点要添加到的位置
            while (node != null) {
                parent = node;
                if (data > node.data) {
                    node = node.rightChild;
                } else if (data < node.data) {
                    node = node.leftChild;
                } else {
                    return node;
                }
            }
            // 表示将此节点添加到相应位置
            node = new SearchTreeNode(data);
            if (data > parent.data) {
                parent.rightChild = node;
            } else {
                parent.leftChild = node;
            }
            node.parent = parent;
        }
        return node;
    }

    /**
     * 用栈实现中序遍历
     */
    public void inorderTraversal() {
        Stack<SearchTreeNode> nodeStack = new Stack<>();
        SearchTreeNode node = root;
        while (node != null || !nodeStack.isEmpty()) {
            while (node != null) {
                nodeStack.push(node);
                node = node.leftChild;
            }

            if (!nodeStack.isEmpty()) {
                node = nodeStack.pop();
                System.out.println(node.data);
                node = node.rightChild;
            }
        }
    }

    static class SearchTreeNode {
        private int data;    // 节点值
        private SearchTreeNode leftChild;   // 节点左子树
        private SearchTreeNode rightChild;  // 节点右子树
        private SearchTreeNode parent; // 指向父节点

        public SearchTreeNode(int data) {
            this.data = data;
            this.leftChild = null;
            this.rightChild = null;
            this.parent = null;
        }

        public int getData() {
            return data;
        }

    }

    public static void main(String[] args) {
        SearchBinaryTree tree = new SearchBinaryTree();
        int[] intArray = new int[]{50, 30, 20, 44, 88, 33, 87, 16, 7, 77};
        for (int data: intArray) {
            tree.put(data);
        }
        /*
                      50
               30            88
            20    44     87
          16  33       77
         7
         */
        tree.inorderTraversal();
    }
}
