package ee.summer.context;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import ee.summer.context.descriptor.Descriptor;
import ee.summer.visitor.ClassPathScanningFileVisitor;

public class AppContext {

  private static final Logger log = Logger.getLogger(AppContext.class.getName());

  public AppContext() {
    createThingInstances(this.scanThingsClasses());
  }

  private List<Descriptor> scanThingsClasses() {
    return getClasspathRoots().stream()
        .map(this::doClasspathScan)
        .flatMap(List::stream)
        .collect(Collectors.toList());
  }

  private void createThingInstances(List<Descriptor> thingClasses) {

  }

  private List<Descriptor> doClasspathScan(Path path) {
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
