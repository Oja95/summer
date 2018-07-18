package ee.summer.context.descriptor;

import java.lang.reflect.Constructor;

import ee.summer.visitor.GraphConstructingDescriptorVisitor;

public class AutoWireConstructorDescriptor extends AbstractDescriptor {

  private final Constructor constructor;
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

  @Override
  public Descriptor accept(GraphConstructingDescriptorVisitor visitor) {
    return visitor.visit(this);
  }
}
