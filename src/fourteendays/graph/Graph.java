package fourteendays.graph;

import java.util.LinkedList;
import java.util.Stack;

/**
 * 图
 * 介绍了获取 出度、入度、深度优先遍历DFS（递归和栈）、广度优先遍历BFS（队列）
 * @author zhangyan
 */
public class Graph {
    /**
     * 顶点数量
     */
    private int vertexSize;
    /**
     * 顶点数组
     */
    private int[] vertexes;
    /**
     * 邻接矩阵
     */
    private int[][] matrix;
    /**
     * 邻接矩阵中顶点无连接时的权值填充值，0 则表示顶点与自己无连接关系
     */
    private static final int MAX_WEIGHT = 1000;
    /**
     * 记录顶点是否被遍历过
     */
    private boolean[] isVisited;

    /**
     * 构造函数，构造图的过程是：初始化顶点数量，初始化邻接矩阵，初始化顶点数组
     * @param vertexSize 顶点个数
     */
    public Graph(int vertexSize) {
        // 初始化顶点数量
        this.vertexSize = vertexSize;
        // 初始化邻接矩阵
        this.matrix = new int[vertexSize][vertexSize];
        // 初始化顶点数组
        this.vertexes = new int[vertexSize];
        for (int i = 0; i < vertexes.length; i++) {
            vertexes[i] = i;
        }
        // 初始化记录顶点被遍历过的数组
        this.isVisited = new boolean[vertexSize];
    }

    /**
     * 计算某个顶点的出度，即邻接矩阵中当前顶点的横向内容中，有多少个顶点有权值
     * @param index 输入的顶点索引
     */
    public int getOutDegree(int index) {
        int outDegree = 0;
        for(int j = 0; j < vertexSize; j++) {
            int weight = matrix[index][j];
            if (weight > 0 && weight < MAX_WEIGHT) {
                outDegree++;
            }
        }
        return outDegree;
    }

    /**
     * 计算某个顶点的入度，即邻接矩阵中当前顶点的纵向内容中，有多少个顶点有权值
     * @param index 输入的顶点索引
     */
    public int getInDegree(int index) {
        int inDegree = 0;

        for (int i = 0; i < vertexSize; i++) {
            int weight = matrix[i][index];
            if (weight > 0 && weight < MAX_WEIGHT) {
                inDegree++;
            }
        }

        return inDegree;
    }

    /**
     * 返回两个顶点从 v1 到 v2的权值
     * @param v1 顶点v1
     * @param v2 顶点v2
     * @return 权值
     */
    public int getWeight(int v1, int v2) {
        int weight = matrix[v1][v2];

        return weight == MAX_WEIGHT ? -1 : weight;
    }

    /**
     * 获取顶点的第一个邻接点
     * @param index 顶点索引
     * @return -1 表示该顶点没有邻接点，大于0的整数表示该顶点的第一个邻接点
     */
    public int getFirstNeighbor(int index) {
        for (int j = 0; j < this.vertexSize; j++) {
            if (matrix[index][j] > 0 && matrix[index][j] < MAX_WEIGHT) {
                return j;
            }
        }
        return -1;
    }

    /**
     * 根据给定顶点的前一个邻接点的下标来取得下一个邻接点
     * @param currVertex 当前顶点
     * @param prevNeighbor 当前顶点的前一个邻接点
     * @return 返回下一个邻接点，如果不存在则返回 -1
     */
    public int getNextNeighbor(int currVertex, int prevNeighbor) {
        for (int j = prevNeighbor + 1; j < this.vertexSize; j++) {
            if (matrix[currVertex][j] > 0 && matrix[currVertex][j] < MAX_WEIGHT) {
                return j;
            }
        }
        return -1;
    }

    /**
     * 图的深度优先遍历算法
     * 1、首先以一个未被访问过的顶点作为起始顶点，沿当前顶点的边（即第一个邻接节点）走到未访问过的顶点；
     * 2、当没有未访问过的顶点时，则回到上一个顶点，继续试探别的顶点（即下一个邻接节点），直至所有的顶点都被访问过。
     * @param index 开始遍历的顶点索引
     */
    private void depthFirstSearch(int index) {
        // 当前顶点已经被遍历
        isVisited[index] = true;
        System.out.println("访问到了：" + index + "顶点");
        // 获取当前顶点的第一个邻接节点
        int w = getFirstNeighbor(index);
        while (w != -1) {
            if (!isVisited[w]) {
                // 当第一个邻接节点没有被遍历过，则从该顶点继续遍历
                depthFirstSearch(w);
            }
            // 之后从下一个邻接节点开始遍历
            w = getNextNeighbor(index, w);
        }
    }

    private void depthFirstSearchWithStack(int index) {
        Stack<Integer> stack = new Stack<>();
        int currInd = index;
        while (currInd != -1 || !stack.isEmpty()) {
            while (currInd != -1) {
                if (!isVisited[currInd]) {
                    System.out.println("访问到了：" + currInd + "顶点");
                    isVisited[currInd] = true;

                    stack.push(currInd);
                    currInd = getFirstNeighbor(currInd);
                } else {
                    currInd = getNextNeighbor(stack.peek(), currInd);
                }
            }
            if (!stack.isEmpty()) {
                int prevNeighbor = stack.pop();
                if (!stack.isEmpty()) {
                    int currV = stack.peek();
                    currInd = getNextNeighbor(currV, prevNeighbor);
                }
            }

        }
    }

    /**
     * 对外公开的深度优先遍历 DFS
     */
    public void depthFirstSearch() {
        // 先初始化顶点访问标记数组
        for (int i = 0; i < vertexSize; i++) {
            isVisited[i] = false;
        }
        // 开始对每个没有遍历过的顶点进行深度优先遍历，直到每个顶点都遍历过，
        // 可以防止未连通的顶点不被遍历到
        for (int i = 0; i < vertexSize; i++) {
            if (!isVisited[i]) {
                depthFirstSearch(i);
            }
        }
    }

    /**
     * 对外公开的深度优先遍历 DFS
     */
    public void depthFirstSearchWithStack() {
        // 先初始化顶点访问标记数组
        for (int i = 0; i < vertexSize; i++) {
            isVisited[i] = false;
        }
        // 开始对每个没有遍历过的顶点进行深度优先遍历，直到每个顶点都遍历过，
        // 可以防止未连通的顶点不被遍历到
        for (int i = 0; i < vertexSize; i++) {
            if (!isVisited[i]) {
                depthFirstSearchWithStack(i);
            }
        }
    }

    /**
     * 对外公开的广度优先遍历 BFS
     */
    public void broadFirstSearch() {
        // 先初始化顶点访问标记数组
        for (int i = 0; i < vertexSize; i++) {
            isVisited[i] = false;
        }
        // 开始对每个没有遍历过的顶点进行深度优先遍历，直到每个顶点都遍历过，
        // 可以防止未连通的顶点不被遍历到
        for (int i = 0; i < vertexSize; i++) {
            if (!isVisited[i]) {
                broadFirstSearch(i);
            }
        }
    }

    /**
     * 图的广度优先遍历算法
     * 1、首先以一个未被访问过的顶点作为起始顶点，先访问当前顶点的连通顶点（即所有邻接节点）；
     * 2、当没有未访问过的顶点时，则将第一个邻接顶点作为起始顶点，继续试探它的连通顶点（即邻接节点），直至所有的顶点都被访问过。
     * @param index 开始遍历的顶点索引
     */
    private void broadFirstSearch(int index) {
        System.out.println("访问到了："+ index + "顶点");
        isVisited[index] = true;

        LinkedList<Integer> queue = new LinkedList<>();
        queue.push(index);
        while (!queue.isEmpty()) {
            int i = queue.removeLast();
            for (int j = 0; j < vertexSize; j++) {
                if (matrix[i][j] > 0 && matrix[i][j] < MAX_WEIGHT) {
                    if (!isVisited[j]) {
                        System.out.println("访问到了："+ j + "顶点");
                        isVisited[j] = true;
                        queue.push(j);
                    }
                }
            }
        }
    }

    public int[] getVertexes() {
        return vertexes;
    }

    public void setVertexes(int[] vertexes) {
        this.vertexes = vertexes;
    }

    public static void main(String[] args) {
        Graph graph = new Graph(9);

        /*
          构造邻接矩阵
              v0  v1  v2  v3  v4  v5  v6  v7  v8
          v0  0   10              11
          v1  10   0  18              16      12
          v2           0  22                   8
          v3          22   0  20          16  21
          v4              20   0  26       7
          v5  11              26   0  17
          v6      16              17   0  19
          v7              16   7      19   0
          v8      12   8  21                   0
         */
        graph.matrix[0] = new int[]{
                0, 10, MAX_WEIGHT, MAX_WEIGHT, MAX_WEIGHT, 11, MAX_WEIGHT, MAX_WEIGHT, MAX_WEIGHT};
        graph.matrix[1] = new int[]{
                10, 0, 18, MAX_WEIGHT, MAX_WEIGHT, MAX_WEIGHT, 16, MAX_WEIGHT, 12};
        graph.matrix[2] = new int[]{
                MAX_WEIGHT, MAX_WEIGHT, 0, 22, MAX_WEIGHT, MAX_WEIGHT, MAX_WEIGHT, MAX_WEIGHT, 8};
        graph.matrix[3] = new int[]{
                MAX_WEIGHT, MAX_WEIGHT, 22, 0, 20, MAX_WEIGHT, MAX_WEIGHT, 16, 21};
        graph.matrix[4] = new int[]{
                MAX_WEIGHT, MAX_WEIGHT, MAX_WEIGHT, 20, 0, 26, MAX_WEIGHT, 7, MAX_WEIGHT};
        graph.matrix[5] = new int[]{
                11, MAX_WEIGHT, MAX_WEIGHT, MAX_WEIGHT, 26, 0, 17, MAX_WEIGHT, MAX_WEIGHT};
        graph.matrix[6] = new int[]{
                MAX_WEIGHT, 16,  MAX_WEIGHT, MAX_WEIGHT, MAX_WEIGHT, 17, 0, 19, MAX_WEIGHT};
        graph.matrix[7] = new int[]{
                MAX_WEIGHT, MAX_WEIGHT, MAX_WEIGHT, 16, 7, MAX_WEIGHT, 19, 0, MAX_WEIGHT};
        graph.matrix[8] = new int[]{
                MAX_WEIGHT, 12, 8, 21, MAX_WEIGHT, MAX_WEIGHT, MAX_WEIGHT, MAX_WEIGHT, 0};

        /*int index = 2;
        System.out.println("v" + index + " 的出度：" + graph.getOutDegree(index));
        System.out.println("v" + index + " 的入度：" + graph.getInDegree(index));

        System.out.println("2 -> 3: " + graph.getWeight(2, 3));
        System.out.println("2 -> 2: " + graph.getWeight(2, 2));
        System.out.println("1 -> 3: " + graph.getWeight(1, 3));*/

        /*
          构造邻接矩阵
              v0  v1  v2  v3  v4  v5  v6  v7  v8
          v0  0   10              11
          v1  10   0  18              16      12
          v2           0  22                   8
          v3          22   0  20          16  21
          v4              20   0  26       7
          v5  11              26   0  17
          v6      16              17   0  19
          v7              16   7      19   0
          v8      12   8  21                   0
         */
        System.out.println("======深度优先遍历，递归方式======");
        graph.depthFirstSearch();
        System.out.println("======深度优先遍历，栈方式======");
        graph.depthFirstSearchWithStack();
        System.out.println("======广度优先遍历======");
        graph.broadFirstSearch();
    }
}
