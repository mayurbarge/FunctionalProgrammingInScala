sealed trait List[+A]
case object Nil extends List[Nothing]
case class Cons[A](head:A, tail: List[A]) extends List[A]

object List {
  def sum(list: List[Int]): Int = {
    list match  {
      case Nil => 0
      case Cons(x,tail) => x + sum(tail)
    }
  }

  def product(list: List[Double]):Double = {
    list match {
      case Nil => 1.0
      case Cons(0.0, _) => 0.0
      case Cons(x, tail) => x*product(tail)
    }
  }

  //its a common idiom to have a variadic method apply in the companion object
  // to create instances of data type
  def apply[A](as:A*):List[A] = {
    if(as.isEmpty) Nil
    else Cons(as.head, apply(as.tail:_*))
    //_* type annotation : allows us to pass Seq to variadic function
  }
}

List.sum(Cons[Int](1,Cons(2, Nil)))
List.product(Cons[Double](1.1,Cons(2.2, Nil)))

/*
List[+A] menas A is Covariant
List[A] means A is invariant
as Nothing is subtype of other types
List[Double], List[Int] can be assigned Nil
*/
/*
?? Why Cons needs +A ?
 */
val x:List[Int] = Nil
val y:List[Double] = Nil

/*
List.apply(1,2,3) is same as List(1,2,3)
 */
List(1,2,3,4,5) match {
  case Cons(x, Cons(2, Cons(4, _))) => x
  case Nil => 42
  case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => {
    println("case 3: ");
    x + y
  }
  case Cons(h, t) => h + List.sum(t)
  case _ => 101
}

List apply 1
