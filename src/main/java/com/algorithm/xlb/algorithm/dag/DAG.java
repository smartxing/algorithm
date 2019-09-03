package com.algorithm.xlb.algorithm.dag;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 *
 * @author xingliangbo
 * @version $Id: v 0.1 2019-09-03 15:21 xingliangbo Exp $
 */
public final class DAG {


  private final LinkedHashSetMultimap outDegree;
  private final LinkedHashSetMultimap inDegree;

  public DAG(final LinkedHashSetMultimap outDegree, final LinkedHashSetMultimap inDegree) {
    this.outDegree = outDegree;
    this.inDegree = inDegree;
  }

  /**
   *  拓扑排序检测 是否有环
   */
  public boolean isCircularity() {
    Map<Object, AtomicInteger> inDegree = getObjectAtomicIntegerMap();
    //入度为0的节点
    Set sources = getSources();
    LinkedList<Object> queue = new LinkedList();
    queue.addAll(sources);
    while (!queue.isEmpty()) {
      Object o = queue.removeFirst();
      outDegree.get(o)
          .forEach(so -> {
            if (inDegree.get(so).decrementAndGet() == 0) {
              queue.add(so);
            }
          });
    }
    return inDegree.values().stream().filter(x -> x.intValue() > 0).count() > 0;
  }


  private void chain_(Set sources, final LinkedHashSetMultimap foutChain, final LinkedHashSetMultimap finChain) {
    sources.forEach(sourceNode -> {

      ArrayList<Object> maxStage = Lists.newArrayList();
      findMaxStage(sourceNode, maxStage);
      if (maxStage.size() > 1) { //存在需要合并的stage
        addVertex(foutChain, finChain, maxStage);//添加一个新节点
        Object o = maxStage.get(maxStage.size() - 1); //最后一个节点
        reChain_(foutChain, finChain, maxStage, o);
      }
      if (maxStage.size() == 1) {
        //不存在需要合并的stage
        addVertex(foutChain, finChain, sourceNode);//添加一个新节点
        Set subNodes = outDegree.get(sourceNode);
        addSubNodeage(foutChain, finChain, sourceNode, subNodes);
      }
    });
  }

  private void addSubNodeage(final LinkedHashSetMultimap foutChain, final LinkedHashSetMultimap finChain,
      final Object sourceNode, final Set subNodes) {
    if (CollectionUtils.isNotEmpty(subNodes)) { //多个出度
      subNodes.forEach(snode -> {
        addEdge(foutChain, finChain, sourceNode, snode);
      });
      chain_(subNodes, foutChain, finChain);
    } else { //最后一个节点了 把节点添加进去 即可
      addVertex(foutChain, finChain, sourceNode);
    }
  }

  private void reChain_(final LinkedHashSetMultimap foutChain, final LinkedHashSetMultimap finChain,
      ArrayList<Object> maxStage, Object o) {
    Set set = outDegree.get(o); //最后一个节点的子节点
    Object header = maxStage.get(0); //第一个stage节点
    //处理父节点
    Set parentSet = finChain.get(header);
    //下面操作就是相当于 链表中添加一个节点  A -> B    ==>  A -> C -> B  先删掉老的A->B 添加新的A->C 在添加新的C->B
    if (CollectionUtils.isNotEmpty(parentSet)) {
      parentSet.forEach(h -> {
        //删除老的边
        removeEage(foutChain, finChain, h, header);
        //添加新的表
        addEdge(foutChain, finChain, h, maxStage);
      });
    }
    addSubNodeage(foutChain, finChain, maxStage, set);
  }

  /**
   *  寻找最大的stage
   */
  public void findMaxStage(Object o, List maxStage) {
    maxStage.add(o);
    Set setOut = outDegree.get(o);
    if (setOut.size() == 1) {
      Object subNode = setOut.iterator().next();
      if (inDegree.get(subNode).size() == 1) {
        findMaxStage(subNode, maxStage);
      }
    }
  }


  /**
   * stage 划分
   * eg:
   *
   *    -> B ->  C -> E -> F             -> B -> C,E,F
   * A        /                   =>  A        /              A -> B -> C -> D ==   A,B,C -> D
   *    -> D                             -> D                             \E               \ E
   */
  public DAG chain() {
    Set sources = getSources();
    final LinkedHashSetMultimap outDegreeChain = new LinkedHashSetMultimap();
    final LinkedHashSetMultimap inDegreeChain = new LinkedHashSetMultimap();
    chain_(sources, outDegreeChain, inDegreeChain);
    return new DAG(outDegreeChain, inDegreeChain);
  }


  public void execute(Consumer consumer) {
    Map<Object, AtomicInteger> inDegree = getObjectAtomicIntegerMap();
    //入度为0的节点
    Set sources = getSources();
    execute_(sources, inDegree, consumer);
  }

  private Map<Object, AtomicInteger> getObjectAtomicIntegerMap() {
    Set<Object> set = inDegree.keySet();
    //入度表
    return set.stream().collect(Collectors
        .toMap(k -> k, k -> new AtomicInteger(this.inDegree.get(k).size())));
  }


  public void execute_(Set set, Map<Object, AtomicInteger> inDegree, Consumer consumer) {
    exec(set, consumer);
    Set nextSet = Sets.newLinkedHashSet();
    set.forEach(o -> {
      outDegree.get(o).forEach(so -> {
        if (inDegree.get(so).decrementAndGet() == 0) {
          nextSet.add(so);
        }
      });
    });
    if (CollectionUtils.isNotEmpty(nextSet)) {
      execute_(nextSet, inDegree, consumer);
    }
  }


  private void exec(Set set, Consumer consumer) {
    consumer.accept(set);
  }


  public boolean addEdge(Object origin, Object target) {
    if (hasPath(target, origin)) {
      return false;
    }
    addEdgeWithNoCheck(origin, target);
    return true;
  }

  public boolean addEdgeWithNoCheck(Object origin, Object target) {
    outDegree.put(origin, target);
    outDegree.put(target, null);
    inDegree.put(target, origin);
    inDegree.put(origin, null);
    return true;
  }


  private boolean addEdge(LinkedHashSetMultimap fOut, LinkedHashSetMultimap fIn, Object origin, Object target) {
    fOut.put(origin, target);
    fIn.put(target, origin);
    return true;
  }

  public boolean removeEage(LinkedHashSetMultimap fOut, LinkedHashSetMultimap fIn, Object origin, Object target) {
    fOut.remove(origin, target);
    if (CollectionUtils.isEmpty(fOut.get(origin))) {
      fOut.removeAll(origin);
    }
    fIn.remove(target, origin);
    if (CollectionUtils.isEmpty(fIn.get(target))) {
      fIn.removeAll(target);
    }
    return true;
  }


  public void addVertex(Object vertex) {
    outDegree.put(vertex, null);
    inDegree.put(vertex, null);
  }

  private void addVertex(LinkedHashSetMultimap fOut, LinkedHashSetMultimap fIn, Object vertex) {
    fOut.put(vertex, null);
    fIn.put(vertex, null);
  }


  public void removeVertex(Object vertex) {
    Set targets = outDegree.removeAll(vertex);
    for (Iterator it = targets.iterator(); it.hasNext(); ) {
      inDegree.remove(it.next(), vertex);
    }
    Set origins = inDegree.removeAll(vertex);
    for (Iterator it = origins.iterator(); it.hasNext(); ) {
      outDegree.remove(it.next(), vertex);
    }
  }


  public Set getSources() {
    return computeZeroEdgeVertices(inDegree);
  }


  public Set getSinks() {
    return computeZeroEdgeVertices(outDegree);
  }

  private Set computeZeroEdgeVertices(LinkedHashSetMultimap map) {
    Set candidates = map.keySet();
    Set roots = new LinkedHashSet(candidates.size());
    for (Iterator it = candidates.iterator(); it.hasNext(); ) {
      Object candidate = it.next();
      if (map.get(candidate).isEmpty()) {
        roots.add(candidate);
      }
    }
    return roots;
  }

  public Set getChildren(Object vertex) {
    return Collections.unmodifiableSet(outDegree.get(vertex));
  }

  private boolean hasPath(Object start, Object end) {
    if (start == end) {
      return true;
    }
    Set children = outDegree.get(start);
    for (Iterator it = children.iterator(); it.hasNext(); ) {
      if (hasPath(it.next(), end)) {
        return true;
      }
    }
    return false;
  }

  public static DAG create() {
    return new DAG(new LinkedHashSetMultimap(), new LinkedHashSetMultimap());
  }


  public String toString() {
    return "OutDegree: " + outDegree.toString() + " InDegree: " + inDegree.toString();
  }
}