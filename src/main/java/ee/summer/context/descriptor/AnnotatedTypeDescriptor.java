package ee.summer.context.descriptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

import ee.summer.annotation.Thing;

public class AnnotatedTypeDescriptor {

  private final Class type;
  private final Constructor autowireAnnotatedConstructor;
  private final Thing thingAnnotation;

  public AnnotatedTypeDescriptor(Thing thingAnnotation, Class type, Constructor autowireAnnotatedConstructors) {
    this.thingAnnotation = thingAnnotation;
    this.type = type;
    this.autowireAnnotatedConstructor = autowireAnnotatedConstructors;
  }

  public Annotation getThingAnnotation() {
    return thingAnnotation;
  }

  public Class getType() {
    return type;
  }

  public Constructor getAutowireAnnotatedConstructor() {
    return autowireAnnotatedConstructor;
  }

  public static class Builder {
    private Class type;
    private Constructor autowireAnnotatedConstructor;
    private Thing thingAnnotation;

    public void setType(Class type) {
      this.type = type;
    }

    public void setAutowireAnnotatedConstructor(Constructor autowireAnnotatedConstructor) {
      this.autowireAnnotatedConstructor = autowireAnnotatedConstructor;
    }

    public void setThingAnnotation(Thing thingAnnotation) {
      this.thingAnnotation = thingAnnotation;
    }

    public AnnotatedTypeDescriptor build() {
      if (thingAnnotation == null && autowireAnnotatedConstructor == null) {
        return null; // TODO: refactor
      }
      return new AnnotatedTypeDescriptor(thingAnnotation, type, autowireAnnotatedConstructor);
    }
  }

  @Override
  public String toString() {
    return "AnnotatedTypeDescriptor{" +
        "type=" + type +
        ", autowireAnnotatedConstructor=" + autowireAnnotatedConstructor +
        ", thingAnnotation=" + thingAnnotation +
        '}';
  }
}
