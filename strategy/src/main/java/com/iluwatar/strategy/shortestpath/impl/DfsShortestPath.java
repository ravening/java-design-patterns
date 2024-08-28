package com.iluwatar.strategy.shortestpath.impl;

import com.iluwatar.strategy.shortestpath.ShortestPath;
import java.util.List;

public class DfsShortestPath implements ShortestPath {
  @Override
  public List<String> findShortestPath(String source, String destination) {
    return List.of("dfs");
  }
}
