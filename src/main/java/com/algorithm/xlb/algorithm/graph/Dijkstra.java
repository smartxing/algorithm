package com.algorithm.xlb.algorithm.graph;

import com.google.common.base.Strings;

import java.util.Arrays;

/**
 *
 * 最小路径之Dijkstra算法
 * @author xingliangbo
 * @version $Id: v 0.1 2019-09-09 16:36 xingliangbo Exp $
 */
public class Dijkstra {

  /**
   *          2    --3--   4
   *      1/  |          / |  \15
   *      /   9       4   13
   *  1       |    /            6
   *      12\   /             /4
   *          3   --5--    5
   *
   *             0               1               2               3               4               5
   * 	0          0               1               12              999999          999999          999999
   * 	1          999999          0               9               3               999999          999999
   * 	2          999999          999999          0               999999          5               999999
   * 	3          999999          999999          4               0               3               5
   * 	4          999999          999999          999999          999999          0               4
   * 	5          999999          999999          999999          999999          999999          0
   *
   *
   */
  public int[][] init() {

    int[][] graph = new int[6][6];
    int inf = 999999; //用inf 存储一个我们认为的正无穷值
    for (int i = 0; i < graph.length; i++) {
      for (int j = 0; j < graph[i].length; j++) {
        if (i == j) {
          graph[i][j] = 0;//自己到自己 肯定是0
        } else {
          graph[i][j] = inf;//默认值
        }
      }
    }
    graph[0][1] = 1;//默认值
    graph[0][2] = 12;//默认值
    graph[1][2] = 9;//默认值
    graph[1][3] = 3;//默认值
    graph[2][4] = 5;//默认值
    graph[3][2] = 4;//默认值
    graph[3][4] = 13;//默认值
    graph[3][5] = 15;//默认值
    graph[4][5] = 4;//默认值
    return graph;
  }



  public void compute(int[][] graph) {
    int[] dis = new int[6];
    int[] flag = new int[6];
    int inf = 999999;

    for (int i = 0; i < 6; i++) {
      flag[i] = 0;
      dis[i] = graph[0][i];
    }
    //原点代表以及在dis 集合中了
    flag[0] = 1;
    dis[0] = 0;
    System.out.println("初始最小路径： 从原点0  到各节点的路径" + Arrays.toString(dis));

    //遍历，每次找出一个顶点点最短路径
    int k = 0;
    for (int i = 1; i < 6; i++) {
      int min = inf;

      //寻找最短路径
      for (int j = 0; j < 6; j++) {
        if (flag[j] == 0 && dis[j] < min) {//
          k = j;
          min = dis[j];
        }
      }
      flag[k] = 1;

      //更新 graph 值
      for (int j = 0; j < 6; j++) {
        //原点到k的距离  +  k到j距离
        int len = graph[k][j] == inf ? inf : min + graph[k][j];

        if (flag[j] == 0 && len < dis[j]) {
          dis[j] = len;
        }
      }
      System.out.println("正在寻找从原点0 "+ i +" dis " +   Arrays.toString(dis) +  "   flag " + Arrays.toString(flag) );

    }

    System.out.println("最终计算： 从原点 到i点的路径" + Arrays.toString(dis));
  }


  public void print(int[][] graph) {
    String tab = Strings.repeat(" ", 10);
    StringBuilder sb = new StringBuilder();
    sb.append(tab + "   ");
    for (int i = 0; i < 6; i++) {
      String v = i + Strings.repeat(" ", 6 - (i + "").length());
      sb.append(v).append(tab);
    }
    sb.append("\n\t");
    for (int i = 0; i < graph.length; i++) {
      sb.append(i).append(tab);
      for (int j = 0; j < graph[i].length; j++) {
        String v = graph[i][j] + Strings.repeat(" ", 6 - (graph[i][j] + "").length());
        sb.append(v).append(tab);
      }
      sb.append("\n\t");
    }
    System.out.println(sb.toString());
  }




  public static void main(String[] args) {
    Dijkstra dijkstra = new Dijkstra();
    int[][] graph = dijkstra.init();
    dijkstra.print(graph);//打印图
    dijkstra.compute(graph);

  }


}
