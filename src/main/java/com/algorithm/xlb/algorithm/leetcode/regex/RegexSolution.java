package com.algorithm.xlb.algorithm.leetcode.regex;

/**
 * https://leetcode-cn.com/problems/regular-expression-matching/
 * @author xingliangbo
 * @version $Id: v 0.1 2019-09-06 11:51 xingliangbo Exp $
 */
public class RegexSolution {


  /**
   *   text =  abc   pattern = a*b
   *         *是0个的时候 a*b = b  =>  （b 匹配 abc） 递归
   *  匹配
   *         *是多个时候  a 匹配 a 且  （bc 匹配  a*b）递归
   */
  public static boolean isMatch(String text, String pattern) {

    if (pattern.isEmpty()) {
      return text.isEmpty();
    }
    //判断首字符是否符合匹配
    boolean isFirstMatch = !text.isEmpty() && text.charAt(0) == pattern.charAt(0) || pattern.charAt(0) == '.';
    //处理*   存在*的话有2中情况 1 中 0个 ,  1种最大匹配n个
    if (pattern.length() >= 2 && pattern.charAt(1) == '*') {

      return isMatch(text, pattern.substring(2)) //如果是0个*
          ||
          (!text.isEmpty() && isFirstMatch && isMatch(text.substring(1), pattern));//匹配最大个*
    } else {
      return isFirstMatch && isMatch(text.substring(1), pattern.substring(1));
    }
  }

  public static void main(String[] args) {
    System.out.println(isMatch("aaa", "a*"));
    System.out.println(isMatch("aaaB", "a*B"));
    System.out.println(isMatch("abcd", ".*B"));
    System.out.println(isMatch("abcdB", ".*B"));
  }

}

