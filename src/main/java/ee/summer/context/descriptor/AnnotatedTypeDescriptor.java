package ee.summer.context.descriptor;

import java.lang.reflect.Constructor;

public class AnnotatedTypeDescriptor {

  private final Class type;
  private final Constructor autowireAnnotatedConstructor;
  private final String name;

  public AnnotatedTypeDescriptor(String name, Class type, Constructor autowireAnnotatedConstructors) {
    this.name = name;
    this.type = type;
    this.autowireAnnotatedConstructor = autowireAnnotatedConstructors;
  }

  public String getName() {
    return name;
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
    private String name;

    public void setType(Class type) {
      this.type = type;
    }

    public void setAutowireAnnotatedConstructor(Constructor autowireAnnotatedConstructor) {
      this.autowireAnnotatedConstructor = autowireAnnotatedConstructor;
    }

    public void setName(String name) {
      this.name = name;
    }

    public AnnotatedTypeDescriptor build() {
      if (name == null && autowireAnnotatedConstructor == null) {
        return null; // TODO: refactor
      }
      return new AnnotatedTypeDescriptor(name, type, autowireAnnotatedConstructor);
    }
  }
}
