package com.iluwatar.strategy.shortestpath;

import com.iluwatar.strategy.shortestpath.impl.BfsShortestPath;
import com.iluwatar.strategy.shortestpath.impl.DfsShortestPath;
import com.iluwatar.strategy.shortestpath.impl.DijkstraShortestPath;

public enum ShortestPathStrategy {

  BFS(new BfsShortestPath()),
  DFS(new DfsShortestPath()),
  DIJKSTRA(new DijkstraShortestPath())
  ;

  private final ShortestPath strategy;
  ShortestPathStrategy(ShortestPath shortestPath) {
    this.strategy = shortestPath;
  }

  public ShortestPath getShortestPathStrategy() {
    return strategy;
  }
}
