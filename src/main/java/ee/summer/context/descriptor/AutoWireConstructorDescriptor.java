package ee.summer.context.descriptor;

import java.lang.reflect.Constructor;

public class AutoWireConstructorDescriptor implements Descriptor {

  private final Constructor constructor;
  private final Class type;
  private final Class[] argTypes;

  public AutoWireConstructorDescriptor(Constructor constructor, Class aClass, Class[] parameterTypes) {
    this.constructor = constructor;
    this.type = aClass;
    this.argTypes = parameterTypes;
  }

  public Constructor getConstructor() {
    return constructor;
  }

  public Class getType() {
    return type;
  }

  public Class[] getArgTypes() {
    return argTypes;
  }
}
