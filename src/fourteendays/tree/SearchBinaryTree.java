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

    /**
     * 删除查找二叉树的节点
     * 1. 在查找二叉树中查找与该值相同的节点
     * 2. 如果找到该节点，要继续查找该节点的后继节点
     * 3. 找到后继节点后，将后继节点的值放入到2中查找到的节点，并删除后继节点
     * @param key 输入要删除的节点
     */
    public void deleteNode(int key) throws Exception {
        SearchTreeNode node = searchNode(key);
        if (node == null) {
            throw new Exception("该节点无法找到");
        } else {
            delete(node);
        }
    }
    /**
     * 在查找二叉树中查找与该值相同的节点
     * @param key
     * @return
     */
    private SearchTreeNode searchNode(int key) {
        SearchTreeNode node = root;
        while (node != null && node.getData() != key) {
            if (node.getData() < key) {
                node = node.rightChild;
            } else if (node.getData() > key) {
                node = node.leftChild;
            }
        }
        return node;
    }

    /**
     * 删除节点。三种情况
     * 1. 要删除节点没有左子节点也没有右子节点，则直接剪断此节点与其父节点的联系，父节点的左子节点或右子节点指向null即可
     * 2. 要删除节点有左子节点但没有右子节点，则此节点的父节点的左子节点或右子节点指向其左子节点即可
     * 3. 要删除节点没有左子节点但有右子节点，则此节点的父节点的左子节点或右子节点指向其右子节点即可
     * 4. 要删除节点既有左子节点也有右子节点，则需要找到此节点的后继节点，将后继节点的值赋给要删除的节点，然后将后继节点移除
     * @param node 要删除的节点
     */
    private void delete(SearchTreeNode node) throws Exception {
        if (node == null) {
            throw new Exception("该节点无法找到");
        } else {
            SearchTreeNode parent = node.parent;
            if (node.leftChild == null && node.rightChild == null) {
                // 被删除节点无左右孩子
                if (parent.leftChild == node) {
                    parent.leftChild = null;
                } else {
                    parent.rightChild = null;
                }
            } else if (node.leftChild != null && node.rightChild == null) {
                // 被删除节点有左无右
                if (parent.leftChild == node) {
                    parent.leftChild = node.leftChild;
                } else {
                    parent.rightChild = node.leftChild;
                }
                node.leftChild.parent = parent;
            } else if (node.leftChild == null && node.rightChild != null) {
                // 被删除节点无左有右
                if (parent.leftChild == node) {
                    parent.leftChild = node.rightChild;
                } else {
                    parent.rightChild = node.rightChild;
                }
                node.rightChild.parent = parent;
            } else {
                // 被删除节点有左有右
                SearchTreeNode next = getNextNode(node);
                parent = next.parent;
                // 后继节点切断与其父节点的关系
                if (parent.leftChild == next) {
                    parent.leftChild = null;
                } else {
                    parent.rightChild = null;
                }
                next.parent = null;
                // 将后继节点的值赋给要删除的节点
                node.data = next.data;
            }
        }
    }

    /**
     * 查找node节点的后继节点，中序遍历方式的后继节点
     * 情况1：节点有右子树时，后继节点可以是此节点右子树的最小节点
     * 情况2：节点没右子树时，后继节点是以node节点为左子树最大节点的根节点
     *
     * @param node 要删除的节点
     * @return 返回后继节点
     */
    private SearchTreeNode getNextNode(SearchTreeNode node) {
        if (node == null) {
            return null;
        } else {
            SearchTreeNode currNode, parent;
            if (node.rightChild != null) {
                currNode = node.rightChild;
                parent = node;
                while (currNode != null) {
                    parent = currNode;
                    currNode = currNode.leftChild;
                }
            } else {
                parent = node.parent;
                currNode = node;
                while(parent.rightChild == currNode) {
                    currNode = parent;
                    parent = parent.parent;
                }
            }
            return parent;
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

    public static void main(String[] args) throws Exception {
        SearchBinaryTree tree = new SearchBinaryTree();
        int[] intArray = new int[]{50, 30, 20, 44, 88, 33, 87, 16, 7, 77};
        for (int data: intArray) {
            tree.put(data);
        }
        /*
                      50
               30            88
            20    44     87
          16    33     77
         7
         */
        tree.inorderTraversal();
        System.out.println("========= 删除节点 44 =======");
        tree.deleteNode(44);
        tree.inorderTraversal();
        System.out.println("========= 查找节点 33的后继节点 =======");
        System.out.println(tree.getNextNode(tree.searchNode(33)).getData());
        System.out.println("========= 删除节点 50 =======");
        tree.deleteNode(50);
        tree.inorderTraversal();

    }
}
