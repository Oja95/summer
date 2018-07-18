package ee.summer.context.descriptor;

import java.lang.annotation.Annotation;

public class AnnotatedTypeDescriptor {
  private final Annotation annotation;
  private final Class type;

  public AnnotatedTypeDescriptor(Annotation annotation, Class type) {
    this.annotation = annotation;
    this.type = type;
  }

  public Annotation getAnnotation() {
    return annotation;
  }

  public Class getType() {
    return type;
  }
}
