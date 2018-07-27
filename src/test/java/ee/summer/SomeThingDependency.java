package ee.summer;

import ee.summer.annotation.AutoWire;
import ee.summer.annotation.Thing;

@Thing
public class SomeThingDependency {
  @AutoWire
  public SomeThingDependency(SomeThingDependencyDependency someThingDependencyDependency) {

  }
}
