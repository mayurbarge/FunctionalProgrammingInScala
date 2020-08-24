trait List[+A]
case class Cons[+A](h: A, tail:List[A]) extends List[A]
case object Nil extends List[Nothing]

object List {
  def apply[A](as:A*): List[A] = {
    if(as.isEmpty) Nil
    else Cons(as.head, apply(as.tail:_*))
  }

  def foldRight[A,B](xs: List[A], z:B)(f:(A,B)=>B):B = {
    xs match {
      case Nil => z
      case Cons(h, xs) => f(h, foldRight(xs,z)(f)) // f(A,B) => B foldRight() gives B
    }
  }

  def length[A](as: List[A]):Int = foldRight(as, 0)((_,acc)=>acc+1)

  def foldLeft[A,B](xs:List[A], z:B)(f:(A,B)=>B):B = {
    xs match {
      case Nil => z
      //case Cons(h, t) => f(h, foldLeft(t,z)(f)) //recursive
      case Cons(h, t) => foldLeft(t,f(h,z))(f) //tail-recursive
    }
  }

  def reverse[A](xs:List[A]): List[A] = {
    foldLeft(xs, Nil:List[A])((e, acc) => Cons(e,acc))
  }

  def foldLeftViaRight[A,B](xs:List[A], z:B)(f:(A,B)=>B):B = {
    //val zz: B => B = (b:B) => b
    foldRight(xs,(b:B)=>b)((a, g) => {
      val x: A = a
      val y: B => B = g
      b => {
        val p1: B = f(b,a)
        val p2: B = g(f(b,a))
        g(f(b,a))
      }
    }
    )(z)
    /*
      Type B in foldRight for accumulator is defined here as function B => B
      Hence pattern matching results in a function of type B => B and in the end as return type
      of foldLeftViaRight is B we call this function B => B using (z) and return B
     */
    val foldResult: B => B = foldRight(xs, (b:B)=>b)((a, g) => b => g(f(b,a)))
    foldRight(xs,(b:B)=>b)((a, g) => b => g(f(b,a)))(z)
  }

  def append[A](l: List[A], r: List[A]):List[A] = {
    foldRight(l, r)(Cons(_,_))
  }
}

List.foldRight(List(1,2,3),  Nil:List[Int])(Cons(_,_))
List.foldLeft(List(1,2,3), 0)(_+_)
List.foldLeft(List(1,2,3), 0)((_,acc)=>1+acc)
List.reverse(List(1,2,3))
