package ee.summer;

import ee.summer.annotation.AutoWire;
import ee.summer.annotation.Thing;

@Thing
public class SomeThing {

  @AutoWire
  public SomeThing(SomeThingDependency someThingDependency) {}
}
