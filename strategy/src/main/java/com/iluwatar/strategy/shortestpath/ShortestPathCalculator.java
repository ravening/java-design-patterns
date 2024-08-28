package com.iluwatar.strategy.shortestpath;

import java.util.List;

public class ShortestPathCalculator {
  public static void main(String[] args) {
    ShortestPathCalculator shortestPathCalculator = new ShortestPathCalculator();

    System.out.println(shortestPathCalculator
        .findShortestPath(ShortestPathStrategy.BFS, "a", "b"));
    System.out.println(shortestPathCalculator
        .findShortestPath(ShortestPathStrategy.DFS, "a", "b"));
    System.out.println(shortestPathCalculator
        .findShortestPath(ShortestPathStrategy.DIJKSTRA, "a", "b"));
  }

  public List<String> findShortestPath(ShortestPathStrategy shortestPathStrategy, String source, String destination) {
    return shortestPathStrategy.getShortestPathStrategy().findShortestPath(source, destination);
  }
}
