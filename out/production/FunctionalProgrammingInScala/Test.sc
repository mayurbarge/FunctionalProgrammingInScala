class A {
  def eat = "Animal eating"
}

class B extends A  {
  override def eat = "Bird eating"
  def fly = "Bird flying"
}

val animal:A = new A
animal.eat



val bird2: B = new B
bird2.eat
bird2.fly


val bird1:B = bird2
bird1.eat
bird1.fly



val list = List("SP","BP","SMI","ADS")



None.get

Ordering