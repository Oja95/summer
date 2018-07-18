package ee.summer.visitor;

import ee.summer.context.descriptor.AbstractDescriptor;
import ee.summer.context.descriptor.AutoWireConstructorDescriptor;
import ee.summer.context.descriptor.Descriptor;
import ee.summer.context.descriptor.ThingAnnotatedTypeDescriptor;
import ee.summer.graph.Graph;

public class GraphConstructingDescriptorVisitor {

  private final Graph<AbstractDescriptor> graph = new Graph<>();

  public Descriptor visit(AutoWireConstructorDescriptor autoWireConstructorDescriptor) {

    Class[] argTypes = autoWireConstructorDescriptor.getArgTypes();
    return null;
  }

  public Descriptor visit(ThingAnnotatedTypeDescriptor thingAnnotatedTypeDescriptor) {
    return null;
  }

  public Graph<AbstractDescriptor> getGraph() {
    return graph;
  }
}
