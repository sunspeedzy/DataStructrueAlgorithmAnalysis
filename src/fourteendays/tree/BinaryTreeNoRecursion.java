package fourteendays.tree;

/**
 * 这个树的遍历，求高、求节点数等操作，不使用递归迭代执行
 * @author zhangyan
 */
public class BinaryTreeNoRecursion<T> extends BinaryTree<T> {
    private TreeNode<T> root;

    @Override
    public void setRoot(TreeNode<T> root) {
        this.root = root;
    }

    /**
     * 使用 Morris Traversal方法 进行中序遍历
     *
     * 1. 如果当前节点的左孩子为空，则输出当前节点并将其右孩子作为当前节点。
     * 2. 如果当前节点的左孩子不为空，在当前节点的左子树中找到当前节点在中序遍历下的前驱节点。
     *   （前驱节点即“当前节点左子树的最右叶子节点”，此时最右节点的右儿子有两种情况，一种是指向当前节点，一种是为空）
     *   a) 如果前驱节点的右孩子为空，将它的右孩子设置为当前节点。当前节点更新为当前节点的左孩子。
     *   b) 如果前驱节点的右孩子为当前节点，将它的右孩子重新设为空（恢复树的形状）。输出当前节点。当前节点更新为当前节点的右孩子。
     *
     * 重复以上1、2直到当前节点为空。
     *
     * 总结：中序遍历，当前节点的左子树->当前节点->当前节点的右子树。
     *     1. 左为空，则输出自己，自己再向右；
     *     2. 左不空，则找到前驱，前驱为自己左树的最右节点，先向左再一直向右直到右空或右为己
     *           a)  找到前驱，若前驱右空则右连己，自己再向左，因为自己最前驱的节点在自己的左子树
     *           b)  找到前驱，若前驱已右连己，则断连接输出自己，自己再向右，因为自己已经被遍历过了
     *     重复以上 1、2 直到当前节点为空。
     * @return 返回节点个数
     */
    public int inorderMorrisTraversal() {
        // 当前节点初始化为根节点
        TreeNode<T> curr = root;
        // 当前节点的前驱节点
        TreeNode<T> prev;
        int size = 0;

        while (curr != null) {
            if (curr.getLeftChild() == null) {
                // 1. 如果当前节点的左孩子为空，则输出当前节点并将其右孩子作为当前节点。
                System.out.println(curr.getData());
                size++;
                curr = curr.getRightChild();
            } else {
                // 2. 如果当前节点的左孩子不为空，在当前节点的左子树中找到当前节点在中序遍历下的前驱节点。
                // 查找当前节点的前驱节点，中序遍历，当前节点的前驱为其左子树的最右节点
                prev = curr.getLeftChild();
                while (prev.getRightChild() != null && prev.getRightChild() != curr) {
                    prev = prev.getRightChild();
                }
                // a) 如果前驱节点的右孩子为空，将它的右孩子设置为当前节点。当前节点更新为当前节点的左孩子。
                if (prev.getRightChild() == null) {
                    prev.setRightChild(curr);
                    curr = curr.getLeftChild();
                } else {
                    // b) 如果前驱节点的右孩子为当前节点，将它的右孩子重新设为空（恢复树的形状）。
                    // 输出当前节点。当前节点更新为当前节点的右孩子。
                    // 这时 前驱节点的右孩子已经指向了当前节点   即 prev.getRightChild() == curr
                    prev.setRightChild(null);
                    System.out.println(curr.getData());
                    size++;
                    curr = curr.getRightChild();
                }
            }
        }
        return size;
    }

    /**
     * 使用 Morris Traversal方法 进行前序遍历
     * 与中序遍历相比，在2a处输出当前节点，在2b处不再输出当前节点
     */
    public void preorderMorrisTraversal() {
        TreeNode<T> curr = root;
        TreeNode<T> prev;
        int height, tmpHeight = 0;

        while (curr != null) {
            if (curr.getLeftChild() == null) {
                System.out.println(curr.getData());
                curr = curr.getRightChild();
            } else {
                prev = curr.getLeftChild();
                while (prev.getRightChild() != null && prev.getRightChild() != curr) {
                    prev = prev.getRightChild();
                }

                if (prev.getRightChild() == null) {
                    prev.setRightChild(curr);
                    System.out.println(curr.getData());
                    curr = curr.getLeftChild();
                } else {
                    prev.setRightChild(null);
                    curr = curr.getRightChild();
                }
            }
        }
    }

    /**
     * 使用 Morris Traversal方法 进行后序遍历
     * 与中序遍历相比，改动较大
     * 需要建立一个临时节点dump，令其左孩子是root
     * 还需要一个子过程 printReverse，就是倒序输出某两个节点之间路径上的各个节点。
     */
    public void postorderMorrisTraversal() {
        TreeNode<T> dump = new TreeNode<>();
        dump.setLeftChild(root);

        TreeNode<T> curr = dump, prev;
        while (curr != null) {
            if (curr.getLeftChild() == null) {
                curr = curr.getRightChild();
            } else {
                prev = curr.getLeftChild();
                while (prev.getRightChild() != null && prev.getRightChild() != curr) {
                    prev = prev.getRightChild();
                }
                if (prev.getRightChild() == null) {
                    prev.setRightChild(curr);
                    curr = curr.getLeftChild();
                } else {
                    printReverse(curr.getLeftChild(), prev);
                    curr = curr.getRightChild();
                }
            }
        }
    }

    private void printReverse(TreeNode<T> from, TreeNode<T> to) {
        // 将 from节点 至 to节点的所有节点倒序输出
        reverse(from, to);

        TreeNode<T> node = to;
        while (true) {
            System.out.println(node.getData());
            if (node == from) {
                break;
            }
            node = node.getRightChild();
        }

        reverse(to, from);
    }

    private void reverse(TreeNode<T> from, TreeNode<T> to) {
        if (from == to) {
            return;
        }
        // 将 from节点 至 to节点的所有节点倒序
        TreeNode<T> x = from, y = from.getRightChild(), z;
        while (x != to) {
            z = y.getRightChild();
            y.setRightChild(x);
            x = y;
            y = z;
        }
    }


    public static void main(String[] args) {
        BinaryTreeNoRecursion<String> tree = new BinaryTreeNoRecursion<>();
        String[] nodeValues = {"A", "B", "C", "D", "E", "F", "G"};
        /*
               A
           B       C
         D   E        F
                     G
         */
        tree.createBinaryTreeInSimpleWay(nodeValues);
        System.out.println("=========中序遍历===========");
        int treeSize = tree.inorderMorrisTraversal();
        System.out.println("树的节点数为：" + treeSize);
        System.out.println("=========前序遍历===========");
        tree.preorderMorrisTraversal();
        System.out.println("=========后序遍历===========");
        tree.postorderMorrisTraversal();
    }
}
