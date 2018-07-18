package ee.summer.context.descriptor;

import ee.summer.visitor.GraphConstructingDescriptorVisitor;

public interface Descriptor {
  Descriptor accept(GraphConstructingDescriptorVisitor visitor);
}
