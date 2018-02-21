
/*
Issues with Type Erasure in Scala
 */
case class Cons[+A](head:A)
class Food
class Coffee extends Food
val foodList: Cons[Food] = Cons(new Food())
val coffeeList:Cons[Coffee] = Cons(new Coffee())
// this returns true even if you use +A or A becasue of generic type erasure at runtime
coffeeList.isInstanceOf[Cons[Food]]
// is same as passing only Cons
coffeeList.isInstanceOf[Cons[_]]


// ?? why testing with Cons[Int] also gives true ?
/*
Warning:(9, 25) fruitless type test: a value of type
A$A45.this.Cons[A$A45.this.Coffee]
cannot also be a A$A45.this.Cons[Int] (but still might match its erasure)
coffeeList.isInstanceOf[Cons[Int]];
 */
coffeeList.isInstanceOf[Cons[Int]] //warning shown : fruitless type test, matching erasure


// List of Int still matches with List[String] type
List(1,2,3) match {
case l : List[String] => println("A list of strings?!")
  case _ => println("Ok")
}
/*
At run time, only the class exists, not its type parameters.
In the example, JVM knows it is handling a scala.collection.immutable.List,
but not that this list is parameterized with Int.
 */
List((1, "One"),2,"Two") match {
  case a:List[(Int, Int)] => "Type matches (Int, Int)"
  case a:List[(Int, String)] => "Type matches (Int, String)"
  case _=> "No match"
}
// Type erasure does not occur to arrays
Array((1,"One"),(2,"Two")) match {
  case number:Array[(Int, String)] => "Matches number (Int, String)"
  case _=> "Match failed"
}

//----------------

sealed trait Result
case class Ok[T](values: List[T]) extends Result
case class Fail(message: String) extends Result

def handleResult(result: Result): Unit = result match {
  case Fail(message) => println("ERROR: " + message)
  case Ok(vs: List[String]) => println("Got strings of total length: " + vs.map(_.size).sum)
  case _ => println("Got something else")
}

handleResult(Fail("something happened")) // output: "ERROR: something happened"
handleResult(Ok(List("this", "works")))  // output: "Got strings of total length: 9
handleResult(Ok(List("doesn't", "work", 4))) // ClassCastException... oops

