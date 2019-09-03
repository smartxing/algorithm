package com.algorithm.xlb.algorithm;

import com.algorithm.xlb.algorithm.dag.DAG;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author xingliangbo
 * @version $Id: v 0.1 2019-09-03 16:52 xingliangbo Exp $
 */
public class DAGChainTest {

  /**
   *  A -> B -> C   D    ==>  A,B,C     D
   */
  @Test
  public void testDag() {
    DAG dag = DAG.create();
    dag.addVertex("A");
    dag.addVertex("B");
    dag.addVertex("C");
    dag.addVertex("D");
    dag.addEdge("A", "B");
    dag.addEdge("B", "C");
    System.out.println(dag);
    System.out.println("入度为0 的节点 ： " + dag.getSources());
    System.out.println("拓扑排序检测是否有环 : " + dag.isCircularity());
    DAG chainDag = dag.chain();
    System.out.println(chainDag);
    System.out.println("chain入度为0 的节点 ： " + chainDag.getSources());
    System.out.println("chain拓扑排序检测是否有环 : " + chainDag.isCircularity());
    Assert.assertTrue(chainDag.toString().equals("OutDegree: {[A, B, C]=[], D=[]} InDegree: {[A, B, C]=[], D=[]}"));
  }


  /**
   *  A -> B -> C
   *          \    \
   *           D  - >E ->F
   *
   *           ==>
   *
   *    (A,B)  -> C
   *          \    \
   *           D  - > (E,F)
   *
   */
  @Test
  public void testDag2() {
    DAG dag = DAG.create();
    dag.addVertex("A");
    dag.addVertex("B");
    dag.addVertex("C");
    dag.addVertex("D");
    dag.addVertex("E");
    dag.addVertex("F");
    dag.addVertex("G");
    dag.addEdge("A", "B");
    dag.addEdge("B", "C");
    dag.addEdge("B", "D");
    dag.addEdge("C", "E");
    dag.addEdge("D", "E");
    dag.addEdge("E", "F");
    System.out.println(dag);
    System.out.println("入度为0 的节点 ： " + dag.getSources());
    System.out.println("拓扑排序检测是否有环 : " + dag.isCircularity());
    DAG chainDag = dag.chain();
    System.out.println(chainDag);
    System.out.println("chain入度为0 的节点 ： " + chainDag.getSources());
    System.out.println("chain拓扑排序检测是否有环 : " + chainDag.isCircularity());
    Assert.assertTrue(chainDag.toString().equals("OutDegree: {[A, B]=[C, D], [E, F]=[], C=[[E, F]], D=[[E, F]], G=[]} InDegree: {[A, B]=[], C=[[A, B]], D=[[A, B]], [E, F]=[C, D], G=[]}"));
  }



  /**
   *  G
   *    \
   *  A -> B -> C
   *          \    \
   *           D  - >E ->F
   *
   *           ==>
   *
   *    (A,B)  -> C
   *          \    \
   *           D  - > (E,F)
   *
   */
  @Test
  public void testDag3() {
    DAG dag = DAG.create();
    dag.addVertex("A");
    dag.addVertex("B");
    dag.addVertex("C");
    dag.addVertex("D");
    dag.addVertex("E");
    dag.addVertex("F");
    dag.addVertex("G");
    dag.addEdge("A", "B");
    dag.addEdge("B", "C");
    dag.addEdge("B", "D");
    dag.addEdge("C", "E");
    dag.addEdge("D", "E");
    dag.addEdge("E", "F");
    dag.addEdge("G", "B");
    System.out.println(dag);
    System.out.println("入度为0 的节点 ： " + dag.getSources());
    System.out.println("拓扑排序检测是否有环 : " + dag.isCircularity());
    DAG chainDag = dag.chain();
    System.out.println(chainDag);
    System.out.println("chain入度为0 的节点 ： " + chainDag.getSources());
    System.out.println("chain拓扑排序检测是否有环 : " + chainDag.isCircularity());
    Assert.assertTrue(chainDag.toString().equals("OutDegree: {A=[B], B=[C, D], [E, F]=[], C=[[E, F]], D=[[E, F]], G=[B]} InDegree: {A=[], B=[A, G], C=[B], D=[B], [E, F]=[C, D], G=[]}"));
  }




  /**
   *  H
   *    \
   *      G
   *        \
   *     A -> B
   *            \
   *  C- D  -E  - F-> J
   *
   *
   *
   *      ==>
   *
   *     (H,G)
   *         \
   *     A -> B
   *            \
   *  (C,D,E)  - (F,J)
   *
   */
  @Test
  public void testDag4() {
    DAG dag = DAG.create();
    dag.addVertex("A");
    dag.addVertex("B");
    dag.addVertex("C");
    dag.addVertex("D");
    dag.addVertex("E");
    dag.addVertex("F");
    dag.addVertex("G");
    dag.addVertex("H");
    dag.addVertex("J");
    dag.addEdge("H", "G");
    dag.addEdge("G", "B");
    dag.addEdge("A", "B");
    dag.addEdge("B", "F");
    dag.addEdge("C", "D");
    dag.addEdge("D", "E");
    dag.addEdge("E", "F");
    dag.addEdge("F", "J");
    System.out.println(dag);
    System.out.println("入度为0 的节点 ： " + dag.getSources());
    System.out.println("拓扑排序检测是否有环 : " + dag.isCircularity());
    DAG chainDag = dag.chain();
    System.out.println(chainDag);
    System.out.println("chain入度为0 的节点 ： " + chainDag.getSources());
    System.out.println("chain拓扑排序检测是否有环 : " + chainDag.isCircularity());
    Assert.assertTrue(chainDag.toString().equals("OutDegree: {A=[B], [F, J]=[], B=[[F, J]], [C, D, E]=[[F, J]], [H, G]=[B]} InDegree: {A=[], B=[A, [H, G]], [F, J]=[B, [C, D, E]], [C, D, E]=[], [H, G]=[]}"));
  }


  /**
   *     A -> B
   *            \
   *  C- D  -E  - F-> J
   *
   *
   *
   *      ==>

   *        (A,B)
   *            \
   *  (C,D,E) - (F,J)
   *
   */
  @Test
  public void testDag5() {
    DAG dag = DAG.create();
    dag.addVertex("A");
    dag.addVertex("B");
    dag.addVertex("C");
    dag.addVertex("D");
    dag.addVertex("E");
    dag.addVertex("F");
    dag.addVertex("G");
    dag.addVertex("H");
    dag.addVertex("J");
    dag.addEdge("A", "B");
    dag.addEdge("B", "F");
    dag.addEdge("C", "D");
    dag.addEdge("D", "E");
    dag.addEdge("E", "F");
    dag.addEdge("F", "J");
    System.out.println(dag);
    System.out.println("入度为0 的节点 ： " + dag.getSources());
    System.out.println("拓扑排序检测是否有环 : " + dag.isCircularity());
    DAG chainDag = dag.chain();
    System.out.println(chainDag);
    System.out.println("chain入度为0 的节点 ： " + chainDag.getSources());
    System.out.println("chain拓扑排序检测是否有环 : " + chainDag.isCircularity());
    Assert.assertTrue(chainDag.toString().equals("OutDegree: {A=[B], [F, J]=[], B=[[F, J]], [C, D, E]=[[F, J]], [H, G]=[B]} InDegree: {A=[], B=[A, [H, G]], [F, J]=[B, [C, D, E]], [C, D, E]=[], [H, G]=[]}"));
  }
}
