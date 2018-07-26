package ee.summer.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

import ee.summer.annotation.AutoWire;
import ee.summer.annotation.Thing;
import ee.summer.context.descriptor.AnnotatedTypeDescriptor;

public class ClassIntrospectorUtil {
  public static AnnotatedTypeDescriptor introspect(Class aClass) {
    var builder = new AnnotatedTypeDescriptor.Builder();
    builder.setType(aClass);

    Annotation thingAnnotation = aClass.getAnnotation(Thing.class);
    if (thingAnnotation != null) {
      builder.setName(((Thing) thingAnnotation).thingName());
    }

    for (Constructor constructor : aClass.getDeclaredConstructors()) {
      Annotation[] declaredAnnotations = constructor.getDeclaredAnnotations();
      for (Annotation declaredAnnotation : declaredAnnotations) {
        if (declaredAnnotation.annotationType().equals(AutoWire.class)) {
          builder.setAutowireAnnotatedConstructor(constructor);
        }
      }
    }

    return builder.build();
  }

}
