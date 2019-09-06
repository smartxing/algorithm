package com.algorithm.xlb.algorithm.cbo;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 贪心算法： 每次选取最大的
 *
 * 问题： 举个列子 比如有 1 5 10 20 硬币  如果需要凑到37块怎么凑 而且硬币数最少
 *
 * 1 第一步剪支 选择最优的 即选择最大的  20
 * 2 选了20 还差17 在选最大的 10
 * 3 还差7块 ，在选择最大的5
 * 4 还差2块 ，只能选择1 2
 * 所以贪心算法最终结果是  20  10  5 1  1
 *
 * 不考虑 每次选择max 找不到解 ，如何考虑找不到解需要DFS 搜索  详见 DFSGreedy.java
 * @author xingliangbo
 * @version $Id: v 0.1 2019-09-04 20:12 xingliangbo Exp $
 */
public class Greedy {


  public List<Integer> find() {
    //定义硬币
    Integer[] coins = new Integer[]{1, 2, 5, 10, 20, 50, 100};
    //目标 需要38
    Integer target = 625;
    Arrays.sort(coins, Collections.reverseOrder());

    return findMax(coins, target);

  }

  public List<Integer> findMax(Integer[] coins, Integer target) {

    List<Integer> result = Lists.newArrayList();
    for (int j = 0; j < coins.length; j++) {
      if (coins[j] <= target) { //每次寻找最大的max
        Integer count = target / coins[j];
        target = target % coins[j];
        for (int k = 0; k < count; k++) {
          result.add(coins[j]);
        }
      }
    }
    return result;

  }


  public static void main(String[] args) {
    Greedy greedy = new Greedy();
    System.out.println(greedy.find());

  }

}
