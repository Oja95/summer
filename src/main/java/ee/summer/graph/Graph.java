package ee.summer.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
    return edges.getOrDefault(vertex, Collections.emptySet());
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

  // TODO: Currently modifies the state of the underlying graph, avoid it
  public List<V> getTopologicalOrdering() {
    var result = new ArrayList<V>();
    var noIncomingEdgeVertices = getVerticesWithoutIncomingEdge();

    while (!noIncomingEdgeVertices.isEmpty()) {
      V next = noIncomingEdgeVertices.iterator().next();
      noIncomingEdgeVertices.remove(next);
      result.add(next);
      for (V neighbourVertex : new HashSet<>(getOutgoingAdjacentVertices(next))) {
        removeEdge(next, neighbourVertex);
        if (getIncomingAdjacentVertices(neighbourVertex).isEmpty()) {
          noIncomingEdgeVertices.add(neighbourVertex);
        }
      }
    }

    if (edges.values().stream().mapToLong(Collection::size).sum() != 0L) {
      throw new IllegalStateException("Cyclic dependency!"); // TODO: Error message to state what is causing cyclic dependency
    } else {
      return result;
    }
  }

  private Set<V> getVerticesWithoutIncomingEdge() {
    return vertices.stream().filter(vertex -> getIncomingAdjacentVertices(vertex).isEmpty()).collect(Collectors.toSet());
  }

}
