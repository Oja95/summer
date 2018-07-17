package ee.summer.context;

public class ManagedThingDescriptor {
  private final String name;
  private final Class type;

  public ManagedThingDescriptor(String name, Class type) {
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
