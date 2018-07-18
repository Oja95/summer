package ee.summer.context.descriptor;

import java.util.Objects;

public abstract class AbstractDescriptor implements Descriptor {
  Class type;

  @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    AbstractDescriptor that = (AbstractDescriptor) o;
    return Objects.equals(type, that.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type);
  }
}
