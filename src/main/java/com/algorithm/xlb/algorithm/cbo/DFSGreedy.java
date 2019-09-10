package com.algorithm.xlb.algorithm.cbo;

import com.google.common.base.Joiner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

/**
 * 贪心算法： 每次选取最大的 ，寻找相对最优解
 * DFS算法实现
 *
 * 可以用A*搜索算法是实现
 * f = g + h
 * g => 其实点到目标点的代价
 * h => 目标点到终点的代价
 * 领f = Max() 寻找最大的价值
 *
 * A*其实是基于BFS的  如下是基于DFS的
 *
 * @author xingliangbo
 * @version $Id: v 0.1 2019-09-04 20:12 xingliangbo Exp $
 */
public class DFSGreedy {


  /**
   * 如果你要寻找到全局最优解 ，其实需要回朔所有的子问题解 选出最优的解
   * 相对最优解实现只需要DFS每次按照最大的选取:
   * 构造二维树组
   * eg  x1 代表解的第一位 ，x2第二位
   *  x1   x2   x2    x4  ....
   *  10   10
   *  9    9
   *  7    7
   *  4    4
   *  2    2
   */
  public static void main(String[] args) {
    DFSGreedy greedy = new DFSGreedy();
    Integer[] coins = new Integer[]{2, 4, 6, 8};
    Integer target = 17;
    greedy.findMaxResult(coins,target);
    System.out.println("--------------------------");
    coins = new Integer[]{2 ,4 ,7 ,9 ,10};
    target = 16;
    greedy.findMaxResult(coins,target);
    System.out.println("--------------------------");
    coins = new Integer[]{2 ,4 ,7 ,9 ,10};
    target = 15;
    greedy.findMaxResult(coins,target);
    coins = new Integer[]{2 ,4 ,7 ,9 ,10};
    target = 17;
    greedy.findMaxResult(coins,target);


  }

  /**
   *  DFS相对最优解
   */
  public void findMaxResult(Integer[] coins,Integer target) {
    Arrays.sort(coins, Collections.reverseOrder());
    int weight = target / coins[coins.length - 1] + 1;
    int hight = coins.length;
    int[][] path = new int[weight][hight];//初始化值都是0
    Stack<Pair> stack = new Stack();
    //初始化素组
    findByDfs(stack, path, coins, target, 0);
    StringBuilder sb  = new StringBuilder();
    sb.append("coins ").append("("+Joiner.on(",").join(coins)+")").append(" target: " + target);
    if(stack.isEmpty()){
      sb.append(" 无解");
      System.out.println(sb.toString());
    }else {
      sb.append(" 本次相对最优解:");
      Pair[] p = new Pair[stack.size()];
      stack.toArray(p);
      for (int i = 0; i < p.length; i++) {
        sb.append(p[i].v +" ");
      }
//      for (int i = p.length-1; i >= 0; i--) {
//        sb.append(p[i].v +" ");
//      }
      System.out.println(sb.toString());
    }


  }


  public void findByDfs(Stack<Pair> stack, int[][] path, Integer[] coins, Integer target, int x) {

    for (int y = 0; y < path[x].length; y++) {
      //找到第一个 为用过的数字 且 比 目标数字要小
      //System.out.println("target " + target + " 坐标 ( " + x + " , " + y + ") v : " + coins[y]);
      if (path[x][y] == 0 && target >= coins[y]) {
        target = target - coins[y];
        stack.push(new Pair(x, y, coins[y]));
        //System.out.println("选择了 " + coins[y] + " target  " + target + " (" + x + " , " + y + ")");
        path[x][y] = 1;//标记已使用
        if (target != 0) {
          findByDfs(stack, path, coins, target, x + 1);
        } else {
          //System.out.println("找到相对最优化解");
        }
        return;
      } else if (y == path[x].length - 1) {//当前最后一个了 还是没找到
        //回到上一步
        if(checkIsNotResult(path,x-1)){//发现上一步都已经全部用过了 任务已经无解了
          stack.clear();
          return;
        }
        for (int i = 0; i < y; i++) {
          path[x][i]=0; //设置未使用过
        }

        Pair point = stack.pop();
        //System.out.println("pop " + point.v + "target  " + target + " (" + point.x + " , " + point.y + ")");
        target = target + point.v;
        findByDfs(stack, path, coins, target, x - 1);
      }


    }
  }

  public boolean checkIsNotResult(int[][] path, int x) {
    for (int i = 0; i < path[x].length; i++) {
      if (path[x][i] == 0) {
        return false;
      }
    }
    return true;
  }


  public class Pair {
    int x;
    int y;
    int v;

    public Pair(final int x, final int y, final int v) {
      this.x = x;
      this.y = y;
      this.v = v;
    }

  }



}
