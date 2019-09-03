package com.algorithm.xlb.algorithm;

import com.algorithm.xlb.algorithm.dag.DAG;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 *
 * @author xingliangbo
 * @version $Id: v 0.1 2019-09-03 19:06 xingliangbo Exp $
 */
public class DAGExecTest {


  public static void main(String[] args) {

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    DAG dag = DAG.create();
    Task a = new Task("a");
    Task b = new Task("b");
    Task c = new Task("c");
    Task d = new Task("d");
    Task e = new Task("e");
    Task f = new Task("f");
    Task g = new Task("g");
    Task h = new Task("h");
    Task j = new Task("j");
    dag.addVertex(a);
    dag.addVertex(b);
    dag.addVertex(c);
    dag.addVertex(d);
    dag.addVertex(e);
    dag.addVertex(f);
    dag.addVertex(g);
    dag.addVertex(h);
    dag.addVertex(j);
    dag.addEdge(h, g);
    dag.addEdge(g, b);
    dag.addEdge(a, b);
    dag.addEdge(b, f);
    dag.addEdge(c, d);
    dag.addEdge(d, e);
    dag.addEdge(e, f);
    dag.addEdge(f, j);
    DAG chain = dag.chain();
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
    System.out.println(chain);


    chain.execute(col -> {
      Set set = (Set) col;
      List<CompletableFuture> completableFutures = Lists.newArrayList();
      StringBuilder sb = new StringBuilder();
      set.stream().forEach(x -> {
        if (x instanceof Task) {
          CompletableFuture<Void> future = CompletableFuture.runAsync((Task) x, executorService);
          completableFutures.add(future);
          sb.append(" task detached:" + ((Task) x).getTaskName()).append(",");
        }
        if (x instanceof List) {
          List<Task> taskList = (List) x;
          CompletableFuture<Void> future = CompletableFuture.runAsync(()->
            taskList.forEach(Task::run));
          completableFutures.add(future);
          sb.append(
              " task chain " + Joiner.on("-").join(taskList.stream().map(Task::getTaskName).collect(Collectors.toList())));
        }
      });
      CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()])).join();
      System.out.println("stage 结束 ： " + sb.toString());
      System.out.println("-----------------------------------------------");
    });
  }


  public static class Task implements Runnable {

    private String taskName;

    public Task(final String taskName) {
      this.taskName = taskName;
    }

    @Override public void run() {
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("i am running  my name is " + taskName + "  finish ThreadID: " + Thread.currentThread().getId());
    }

    public String getTaskName() {
      return taskName;
    }

    @Override public String toString() {
      return  taskName;
    }
  }
}
