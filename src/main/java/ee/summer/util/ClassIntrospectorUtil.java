package ee.summer.util;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ee.summer.annotation.AutoWire;
import ee.summer.annotation.Thing;
import ee.summer.context.descriptor.AutoWireConstructorDescriptor;
import ee.summer.context.descriptor.Descriptor;
import ee.summer.context.descriptor.ThingAnnotatedTypeDescriptor;

public class ClassIntrospectorUtil {
  public static List<Descriptor> introspect(Class aClass) {
    List<Descriptor> result = Arrays.stream(aClass.getAnnotations())
        .filter(annotation -> annotation.annotationType().equals(Thing.class))
        .map(annotation -> new ThingAnnotatedTypeDescriptor(((Thing) annotation).thingName(), aClass))
        .collect(Collectors.toList());

    var descriptors = Arrays.stream(aClass.getDeclaredConstructors())
        .filter(constructor -> Arrays.stream(constructor.getAnnotations())
            .map(Annotation::annotationType)
            .anyMatch(annotationType -> annotationType.equals(AutoWire.class))
        ).map(constructor -> new AutoWireConstructorDescriptor(constructor, aClass, constructor.getParameterTypes()))
        .collect(Collectors.toList());

    result.addAll(descriptors);
    return result;
  }

}
