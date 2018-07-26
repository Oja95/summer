package ee.summer.context;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import ee.summer.context.descriptor.AnnotatedTypeDescriptor;
import ee.summer.graph.Graph;
import ee.summer.visitor.ClassPathScanningFileVisitor;

public class AppContext {

  private static final Logger log = Logger.getLogger(AppContext.class.getName());

  // TODO: ensure singleton
  public AppContext() {
    Graph<AnnotatedTypeDescriptor> dependencyGraph = createDependencyGraph(this.scanThingsClasses());
  }

  private List<AnnotatedTypeDescriptor> scanThingsClasses() {
    return getClasspathRoots().stream()
        .map(this::doClasspathScan)
        .flatMap(List::stream)
        .collect(Collectors.toList());
  }

  private Graph<AnnotatedTypeDescriptor> createDependencyGraph(List<AnnotatedTypeDescriptor> thingClasses) {
    Graph<AnnotatedTypeDescriptor> graph = new Graph<>();
    for (AnnotatedTypeDescriptor descriptor : thingClasses) {
      log.log(Level.INFO, "Registered class: {0}", descriptor.getType());
      graph.addVertex(descriptor);

      Constructor autowireAnnotatedConstructor = descriptor.getAutowireAnnotatedConstructor();
      if (autowireAnnotatedConstructor != null) {
        for (Class typeClass : autowireAnnotatedConstructor.getParameterTypes()) {
          AnnotatedTypeDescriptor dependencyDescriptor = findThingAnnotatedDescriptorByType(thingClasses, typeClass);
          if (dependencyDescriptor == null) {
            throw new IllegalStateException("AutoWire constructor missing dependency!");
          }

          graph.addEdge(dependencyDescriptor, descriptor);
        }
      }
    }

    return graph;
  }

  private AnnotatedTypeDescriptor findThingAnnotatedDescriptorByType(List<AnnotatedTypeDescriptor> descriptors, Class aClass) {
    for (AnnotatedTypeDescriptor descriptor : descriptors) {
      if (descriptor.getType().equals(aClass) && descriptor.getThingAnnotation() != null) {
        return descriptor;
      }
    }

    return null;
  }

  private List<AnnotatedTypeDescriptor> doClasspathScan(Path path) {
    try {
      var visitor = new ClassPathScanningFileVisitor(path);
      Files.walkFileTree(path, visitor);
      return visitor.getScannedDescriptors();
    }
    catch (IOException e) {
      log.log(Level.WARNING, "Classpath scanning failed", e);
      throw new UncheckedIOException(e);
    }
  }

  private List<Path> getClasspathRoots() {
    var javaClassPath = System.getProperty("java.class.path");
    log.log(Level.INFO, "Class path roots: {0}", javaClassPath);
    Objects.requireNonNull(javaClassPath);
    return Arrays.stream(javaClassPath.split(File.pathSeparator)).map(x -> Paths.get(x)).collect(Collectors.toList());
  }
}
