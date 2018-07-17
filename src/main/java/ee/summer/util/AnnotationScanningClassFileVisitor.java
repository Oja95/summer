package ee.summer.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ee.summer.annotation.Thing;

public class AnnotationScanningClassFileVisitor extends SimpleFileVisitor<Path> {

  private static final Logger log = Logger.getLogger(AnnotationScanningClassFileVisitor.class.getName());
  private static final String DOT_CLASS_STRING = ".class";

  private final List<Class<?>> thingAnnotatedClasses;
  private final Path rootPath;

  public AnnotationScanningClassFileVisitor(Path rootPath) {
    this.thingAnnotatedClasses = new ArrayList<>();
    this.rootPath = rootPath;
  }

  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

    var classFile = rootPath.relativize(file);
    if (classFile.endsWith(DOT_CLASS_STRING)) {
      try {
        var aClass = Class.forName(classFilePathToClassName(classFile));
        if (aClass.isAnnotationPresent(Thing.class)) {
          thingAnnotatedClasses.add(aClass);
        }
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

  public List<Class<?>> getThingAnnotatedClasses() {
    return thingAnnotatedClasses;
  }
}
