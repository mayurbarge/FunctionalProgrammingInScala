sealed trait List[+A] {
  // foldLeft is tail-recursive
  def foldLeft[B](z: B)(op: (A, B) => B): B = {
    this match {
      case Nil => z
      case Cons(h,t) => t.foldLeft(op(h,z))(op)
    }
  }

  // foldRight is non tail-recursive
  // evaluation is recursive and depends upon foldRight to return value for evaluating f
  def foldRight[B](z:B)(f:(A,B)=>B):B = {
    this match {
      case Nil => z
      case Cons(x, xs) => f(x, xs.foldRight(z)(f))
    }
  }

}
case object Nil extends List[Nothing]
case class Cons[+A](head:A, tail: List[A]) extends List[A]

/*
Companion is separation of concerns
Statics goto Companion, Instance related things to Class
 */
object List {
  def apply[A](as:A*):List[A] = {
    as match {
      case as if(as.isEmpty) => Nil
      case _=> Cons(as.head, apply(as.tail:_*))
    }
  }
  def head[A](list:List[A]):A = {
    list match {
      case Nil => throw new Exception("Tail of empty list")
      case Cons(head, tail) => head
    }
  }
  def tail[A](list: List[A]):List[A] = {
    list match  {
      case Nil => throw new Exception("Tail of empty list")
      case Cons(head, tail) => tail
    }
  }
  def setHead[A](head:A, list:List[A]):List[A] = {
    Cons(head,list)
  }

  def drop[A](l:List[A], n:Int): List[A] = {
    if(n == 0) l
    else drop(List.tail(l), n-1)
  }

  def dropUntilConditionMatches[A](l:List[A], f:A=>Boolean):List[A] = {
    if(f(List.head(l))) l
    else dropUntilConditionMatches(List.tail(l),f)
  }

  def append[A](l1: List[A], l2:List[A]):List[A] = {
    l1 match {
      case Nil => l2
      case Cons(h,t) => Cons(h, append(t,l2))
        /**
          * Because of the structure of a singly linked list, any time we want to replace the tail of a Cons, even if it’s the last Cons in the list, we must copy all the previous Cons objects.
          */
    }
  }
  def init[A](l:List[A]):List[A] = {
    // init function can not run in constant time
    // as it needs to traverse all the way till the end of list
    // to calculate the init of a list

    /**
      * Because of the structure of a singly linked list, any time we want to replace the tail of a Cons, even if it’s the last Cons in the list, we must copy all the previous Cons objects.
      */
    l match {
      case Cons(head, Nil) => Nil
      case Cons(head, tail) => Cons(head,init(tail))
    }
  }

  def dropWhile[A](l:List[A])(f:A=>Boolean):List[A] = {
    l match {
      case Cons(h,t) if f(h) => dropWhile(t)(f)
      case _=> l
    }
  }
}
val list1: List[Int] = Cons[Int](1,Cons(2, Nil))
List.tail(List.tail(list1))
//List.tail(List.tail(List.tail(list1)))

val list2: List[Int] = List setHead(0,list1)

// DSL's in scala
List drop(list2,2)
List dropUntilConditionMatches[Int](list2, (x)=> x==2 )
List dropUntilConditionMatches (list2, (x:Int)=> x==2 )

// explicit type passing x:Int can be avoided with curried functions (type inference)
// example of dropWhile
val dropWhileResult = List.dropWhile(list2)(x=> x<2)
List init list2


// analysis of type inference with curried function
// type of i is inferred automatically
val i = 10
val partialResult: ((Int) => Boolean) => List[Int] = List dropWhile list2


List(1,2,3).foldLeft[Int](0)((acc,e)=>acc+e)
List(1,2,3).foldRight[Int](1)((acc,e)=>acc*e)

/*
def foldRight[A,B](as:List[A],z:B)(f:(A,B)=>B):B = {
  as match {
    case Nil => z
    case Cons(x,xs) =>  f(x,foldRight(xs,z)(f))
  }
}

def foldLeft[A,B](as:List[A],z: B)(op: (A, B) => B): B = {
  as match {
    case Nil => z
    case Cons(h,t) => foldLeft(t,op(h,z))(op)
  }
}*/

