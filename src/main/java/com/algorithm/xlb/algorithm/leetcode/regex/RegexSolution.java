package com.algorithm.xlb.algorithm.leetcode.regex;

/**
 * isMatch 递归 isMatch2 动态规划
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


  /**
   *
   *    eg :   aaa   匹配 a*
   *      __________________
   *      |   | "" | a | a  |
   *      |"" | __ | __| __ |
   *      | a | __ | __| __ |
   *      | * | __ | __| __ |
   *
   *
   * 如果s1的第 i 个字符和s2的第 j 个字符相同，或者s2的第 j 个字符为 “.”
   *f[i][j] = f[i - 1][j - 1]
   *  如果s2的第 j 个字符为 *
   * 若s2的第 j 个字符匹配 0 次第 j - 1 个字符
   * f[i][j] = f[i][j - 2] 比如(ab, abc*)
   * 若s2的第 j 个字符匹配至少 1 次第 j - 1 个字符,
   * f[i][j] = f[i - 1][j] and s1[i] == s2[j - 1] or s[j - 1] == '.'
   * 这里注意不是 f[i - 1][j - 1]， 举个例子就明白了 (abbb, ab*) f[4][3] = f[3][3]
   */
  public static boolean isMatch2(String s, String p) {
    int m = s.length(), n = p.length();
    boolean[][] f = new boolean[m + 1][n + 1];

    f[0][0] = true;
    for (int i = 2; i <= n; i++) {
      //如果匹配0个*
      f[0][i] = f[0][i - 2] && p.charAt(i - 1) == '*';
    }
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '.') {
          f[i][j] = f[i - 1][j - 1];
        }
        if (p.charAt(j - 1) == '*') {
          f[i][j] = f[i][j - 2] ||
              f[i - 1][j] && (s.charAt(i - 1) == p.charAt(j - 2) || p.charAt(j - 2) == '.');
        }
      }
    }

    return f[m][n];


  }


  public static void main(String[] args) {
    System.out.println(isMatch2("aaa", "a*"));
    System.out.println(isMatch2("aaaB", "a*B"));
    System.out.println(isMatch2("abcd", ".*B"));
    System.out.println(isMatch2("abcdB", ".*B"));
  }

}

