package fourteendays.tree;

import java.util.Stack;

/**
 * 二叉树，操作由栈实现
 *
 * @author zhangyan
 */
public class BinaryTreeWithStack<T> extends BinaryTree<T> {
    private TreeNode<T> root;

    @Override
    public void setRoot(TreeNode<T> root) {
        this.root = root;
    }

    /**
     * 利用栈实现中序遍历
     * 1. 从根节点开始，先将当前节点压入栈，再将其左子节点作为当前节点。直到当前节点为空。
     * 2. 当前节点为空后，如果栈不空，则弹出栈中节点，输出其数据，节点数加1，把当前节点指向其右子节点。
     * @return 返回遍历的节点个数
     */
    public int inorderStackTraversal() {
        int size = 0;

        Stack<TreeNode> nodeStack = new Stack<>();
        TreeNode node = root;
        while (node != null || !nodeStack.isEmpty()) {

            while (node != null) {
                nodeStack.push(node);
                node = node.getLeftChild();
            }

            if (!nodeStack.isEmpty()) {
                node = nodeStack.pop();
                System.out.println(node.getData());
                size++;
                node = node.getRightChild();
            }
        }

        return size;
    }

    /**
     * 利用栈实现前序遍历
     * 1. 从根节点开始，先输出当前节点，节点数加1；
     *    再判断当前节点是否有右子节点，有则将其右子节点压入栈；
     *    再将其左子节点作为当前节点。
     *    直到当前节点为空。
     * 2. 当前节点为空后，如果栈不空，则弹出栈中节点，使其成为当前节点输出其数据;
     *    输出当前节点，节点数加1，再判断当前节点是否有右子节点，有则将其右子节点压入栈；
     *    再将其左子节点作为当前节点。
     * @return 节点数量
     */
    public int preorderStackTraversalM1() {
        int size = 0;
        TreeNode node = root;
        Stack<TreeNode> nodeStack = new Stack<>();

        while (node != null || !nodeStack.isEmpty()) {
            while (node != null) {
                System.out.println(node.getData());
                size++;
                if (node.getRightChild() != null) {
                    nodeStack.push(node.getRightChild());
                }
                node = node.getLeftChild();
            }
            if (!nodeStack.isEmpty()) {
                node = nodeStack.pop();
                System.out.println(node.getData());
                size++;
                if (node.getRightChild() != null) {
                    nodeStack.push(node.getRightChild());
                }
                node = node.getLeftChild();
            }
        }
        return size;
    }
    /**
     * 利用栈实现前序遍历，方法2
     * 1. 从根节点开始，先输出当前节点，节点数加1，将当前节点入栈；
     *    再将其左子节点作为当前节点。
     *    直到当前节点为空。
     * 2. 当前节点为空后，如果栈不空，则弹出栈中节点，使其成为当前节点输出其数据;
     *    再将当前节点指向其右子节点
     * @return 节点数量
     */
    public int preorderStackTraversalM2() {
        int size = 0;
        TreeNode node = root;
        Stack<TreeNode> nodeStack = new Stack<>();

        while (node != null || !nodeStack.isEmpty()) {
            while (node != null) {
                System.out.println(node.getData());
                size++;

                nodeStack.push(node);

                node = node.getLeftChild();
            }

            if (!nodeStack.isEmpty()) {
                node = nodeStack.pop();

                node = node.getRightChild();
            }
        }
        return size;
    }

    /**
     * 利用栈实现后序遍历
     * 遍历完左子树，遍历完右子树，最后才去访问根节点。
     * 这样栈顶结点可能会从他的左子树返回，也有可能从他的右子树返回，需要区分这种情况，
     * 如果是第一次从左子树返回，那么还需要去遍历其右子树，
     * 如果是从右子树返回，那么直接返回该结点就可以了。这里使用辅助指针来区分来源。
     *
     * @return 节点数量
     */
    public int postorderStackTraversal() {
        int size = 0;
        // node为工作引用，rightNode为辅助引用
        TreeNode node = root, assistNode = null;
        Stack<TreeNode> nodeStack = new Stack<>();

        while(node != null || !nodeStack.isEmpty()) {
            if (node != null) {
                // 从根节点到最左下角的左子树都依次入栈
                nodeStack.push(node);
                node = node.getLeftChild();
            } else {
                // 当左子节点不存在时，工作引用指向栈顶节点
                node = nodeStack.peek();

                if (node.getRightChild() != null && node.getRightChild() != assistNode) {
                    // 当栈顶节点有右子节点，且其右子节点之前没有入栈，则工作引用指向右子节点
                    node = node.getRightChild();
                } else {
                    // 将栈顶节点弹出，工作引用指向弹出的节点
                    node = nodeStack.pop();
                    // 输出当前节点的内容
                    System.out.println(node.getData());
                    size++;
                    // 辅助引用指向当前节点，在将当前节点赋空
                    assistNode = node;
                    node = null;
                }
            }
        }

        return size;
    }

    /**
     * 获取树的高度，节点最大栈长为二叉树的高度
     *
     * @return 树的高度
     */
    @Override
    public int getHeight() {
        if (root == null) {
            return 0;
        }

        int height = 0;
        TreeNode node = root;

        Stack<TreeNode> nodeStack = new Stack<>();
        Stack<Integer> tagStack = new Stack<>();

        while (node != null || !nodeStack.isEmpty()) {
            while (node != null) {
                nodeStack.push(node);
                tagStack.push(0);
                node = node.getLeftChild();
            }

            if (tagStack.peek() == 1) {
                height = Math.max(height, nodeStack.size());
                nodeStack.pop();
                tagStack.pop();
                node = null;
            } else {
                node = nodeStack.peek();
                node = node.getRightChild();
                tagStack.pop();
                tagStack.push(1);
            }
        }


        return height;
    }

    public static void main(String[] args) {
        BinaryTreeWithStack<String> tree = new BinaryTreeWithStack<>();
        String[] nodeValues = {"A", "B", "C", "D", "E", "F", "G"};
        /*
               A
           B       C
         D   E        F
                     G
         */
        tree.createBinaryTreeInSimpleWay(nodeValues);
        System.out.println("=========中序遍历===========");
        int treeSize = tree.inorderStackTraversal();
        System.out.println("树的节点数为：" + treeSize);
        System.out.println("=========前序遍历1===========");
        treeSize = tree.preorderStackTraversalM1();
        System.out.println("树的节点数为：" + treeSize);
        System.out.println("=========前序遍历2===========");
        treeSize = tree.preorderStackTraversalM2();
        System.out.println("树的节点数为：" + treeSize);
        System.out.println("=========后序遍历===========");
        treeSize = tree.postorderStackTraversal();
        System.out.println("树的节点数为：" + treeSize);
        System.out.println("=========求树的高度===========");
        System.out.println(tree.getHeight());
    }
}
