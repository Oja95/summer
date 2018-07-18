package ee.summer.visitor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ee.summer.context.descriptor.Descriptor;
import ee.summer.util.ClassIntrospectorUtil;

public class ClassPathScanningFileVisitor extends SimpleFileVisitor<Path> {

  private static final Logger log = Logger.getLogger(ClassPathScanningFileVisitor.class.getName());
  private static final String DOT_CLASS_STRING = ".class";

  private final List<Descriptor> scannedDescriptors;
  private final Path rootPath;

  public ClassPathScanningFileVisitor(Path rootPath) {
    this.scannedDescriptors = new ArrayList<>();
    this.rootPath = rootPath;
  }

  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

    var classFile = rootPath.relativize(file);
    if (classFile.endsWith(DOT_CLASS_STRING)) {
      try {
        Class<?> aClass = Class.forName(classFilePathToClassName(classFile));
        var descriptor = ClassIntrospectorUtil.introspect(aClass);
        scannedDescriptors.addAll(descriptor);
      }
      catch (ClassNotFoundException e) {
        log.log(Level.WARNING, "Could not find class!", e);
      }
    }

    return FileVisitResult.CONTINUE;
  }

  private String classFilePathToClassName(Path classFile) {
    var s = classFile.toString();
    return s.replaceAll("/|\\\\", "\\.").substring(0, s.length() - DOT_CLASS_STRING.length());
  }

  public List<Descriptor> getScannedDescriptors() {
    return scannedDescriptors;
  }
}
