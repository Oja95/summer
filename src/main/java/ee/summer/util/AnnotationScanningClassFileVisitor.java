package ee.summer.util;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ee.summer.context.descriptor.AnnotatedTypeDescriptor;

public class AnnotationScanningClassFileVisitor<T extends Annotation> extends SimpleFileVisitor<Path> {

  private static final Logger log = Logger.getLogger(AnnotationScanningClassFileVisitor.class.getName());
  private static final String DOT_CLASS_STRING = ".class";

  private final List<AnnotatedTypeDescriptor> thingAnnotatedClasses;
  private final Path rootPath;
  private final Class<T> annotationType;

  public AnnotationScanningClassFileVisitor(Path rootPath, Class<T> annotationType) {
    this.annotationType = annotationType;
    this.thingAnnotatedClasses = new ArrayList<>();
    this.rootPath = rootPath;
  }

  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

    var classFile = rootPath.relativize(file);
    if (classFile.endsWith(DOT_CLASS_STRING)) {
      try {
        var aClass = Class.forName(classFilePathToClassName(classFile));
        var annotation = aClass.getAnnotation(annotationType);
        if (annotation != null) {
          thingAnnotatedClasses.add(new AnnotatedTypeDescriptor(annotation, aClass));
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

  public List<AnnotatedTypeDescriptor> getThingAnnotatedClasses() {
    return thingAnnotatedClasses;
  }
}
