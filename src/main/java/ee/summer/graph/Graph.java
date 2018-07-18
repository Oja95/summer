package ee.summer.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Directed unweighted graph implementation to represent dependency relations
 * @param <V> vertex node type
 */
class Graph<V> {
  private final Set<V> vertices;
  private final Map<V, Set<V>> edges;

  public Graph() {
    this.vertices = new HashSet<>();
    this.edges = new HashMap<>();
  }

  void addVertex(V vertice) {
    vertices.add(vertice);
  }

  void addEdge(V from, V to) {
    edges.computeIfAbsent(from, key -> new HashSet<>()).add(to);
  }

  Set<V> getVertices() {
    return vertices;
  }

  Set<V> getOutgoingAdjacentVertices(V vertice) {
    return edges.get(vertice);
  }

  Set<V> getIncomingAdjacentVertices(V vertice) {
    return vertices.stream().filter(v -> edges.getOrDefault(v, new HashSet<>()).contains(vertice)).collect(Collectors.toSet());
  }

  boolean hasEdge(V from, V to) {
    return edges.getOrDefault(from, new HashSet<>()).contains(to);
  }


}
