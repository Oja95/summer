package ee.summer.context.descriptor;

public class ThingAnnotatedTypeDescriptor implements Descriptor {
  private final String name;
  private final Class type;

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
}
