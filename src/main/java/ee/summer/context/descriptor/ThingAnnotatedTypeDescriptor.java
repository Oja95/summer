package ee.summer.context.descriptor;

import ee.summer.visitor.GraphConstructingDescriptorVisitor;

public class ThingAnnotatedTypeDescriptor extends AbstractDescriptor {
  private final String name;

  public ThingAnnotatedTypeDescriptor(String name, Class type) {
    this.name = name;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public Class getType() {
    return type;
  }

  @Override
  public Descriptor accept(GraphConstructingDescriptorVisitor visitor) {
    return visitor.visit(this);
  }
}
