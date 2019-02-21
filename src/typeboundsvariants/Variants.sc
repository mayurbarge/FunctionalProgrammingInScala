class Foo[+A] // A covariant class
abstract class Animal {
  def name: String
}
case class Cat(name: String) extends Animal
case class Dog(name: String) extends Animal
val cats: List[Cat] = List(Cat("meo"))
val animals: List[Animal] = List(Dog("Doggy"),Cat("Fiss"))
cats.isInstanceOf[List[Animal]]


class Bar[-A] // A contravariant class
class Baz[A]  // An invariant class
