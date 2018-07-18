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
public class Graph<V> {
  private final Set<V> vertices;
  private final Map<V, Set<V>> edges;

  public Graph() {
    this.vertices = new HashSet<>();
    this.edges = new HashMap<>();
  }

  public void addVertex(V vertex) {
    vertices.add(vertex);
  }

  public void addEdge(V from, V to) {
    edges.computeIfAbsent(from, key -> new HashSet<>()).add(to);
  }

  public Set<V> getVertices() {
    return vertices;
  }

  public Set<V> getOutgoingAdjacentVertices(V vertex) {
    return edges.get(vertex);
  }

  public Set<V> getIncomingAdjacentVertices(V vertex) {
    return vertices.stream().filter(v -> edges.getOrDefault(v, new HashSet<>()).contains(vertex)).collect(Collectors.toSet());
  }

  public boolean hasEdge(V from, V to) {
    return edges.getOrDefault(from, new HashSet<>()).contains(to);
  }

  public boolean hasVertex(V vertex) {
    return vertices.contains(vertex);
  }

  public void removeEdge(V from, V to) {
    edges.getOrDefault(from, new HashSet<>()).remove(to);
  }


}
