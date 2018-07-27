package ee.summer;

import ee.summer.annotation.AutoWire;

public class SomeAnotherThing {
  @AutoWire
  public SomeAnotherThing(SomeThingDependency someThingDependency, SomeThingDependencyDependency someThingDependencyDependency) {

  }
}
