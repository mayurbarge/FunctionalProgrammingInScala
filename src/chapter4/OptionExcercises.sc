
object OptionExcercises {

  sealed trait Option[+A] {
    def map[B](f: A => B): Option[B] = {
      this match {
        case Some(a) => Some(f(a))
        case None => None
      }
    }

    def getOrElse[B >: A](default: => B): B = {
      this match {
        case Some(a) => a
        case None => default
      }
    }

    def flatMap[B](f: A => Option[B]): Option[B] = map(f).getOrElse(None)

    // return original option if defined or return default when not defined
    def orElse[B >: A](default: => Option[B]): Option[B] = flatMap(_ => default)

    def filter(f: A => Boolean):Option[A] = {
      flatMap(e => if(f(e))  Some(e) else None)
    }

    /*
    _ map(f) is equivalent to following call made on Function1 class
    it returns back a function of type T => T that is the return type of lift
    It expands into function literal with one parameter.
    Function literal is compiled into a class which that when instantiated at runtime is a function value.
    Function literal exists at compile time, function values at runtime.
    Every function value is an instance of FunctionN class which has apply method used to invoke function.

    Function values are objects, so you can store them in variables.
    They are also functions so you can call them using () syntax which calls FunctionN.apply()

    val f1Instance = new Function1[Int,Int](){
        override def apply(v1: Int) = 1
    }.apply(_)
     */
    def lift[A,B](f: A => B): Option[A] => Option[B] = {
      /*e => {
        val x: Option[A] = e
        e map(f)
      }*/
        _ map(f)
    }

    def map2[A,B,C](opt1: Option[A], opt2: Option[B])(f: (A,B)=>C):Option[C] = {
      for {
       a <- opt1
       b <- opt2
      } yield f(a,b)
    }

    def map2_curry[A,B,C](opt1: Option[A])(opt2: Option[B])(f: (A,B)=>C):Option[C] = {
        map2(opt1,opt2)(f)
    }

    def sequence[A](a: List[Option[A]]): Option[List[A]] = {
      a match {
        case Nil => Some(Nil)
        case h :: t => h flatMap (hh => sequence(t) map (hh :: _))
      }
    }
}


case class Some[+A](get: A) extends Option[A]

case object None extends Option[Nothing]
object Option{
    def traverse[A,B](a:List[A])(f: A => Option[B]):Option[List[B]] = {
      a match {
        case Nil => Some(Nil)
        case h :: tail => f(h) flatMap(hh => traverse(tail)(f).map(hh::_) )
      }
    }

  def traverse2[A,B](a:List[A])(f: A => Option[B]):Option[List[B]] = {
    a match {
      case Nil => Some(Nil)
      case h :: tail => f(h) flatMap {b =>
        traverse(tail)(f) flatMap(e => Some(b :: e))
      }
    }
  }

}


  val o1 = Some(1)
  val o2 = Some(2)
  val n1 = None
  val list = List(o1,o2)

}
OptionExcercises.n1.getOrElse(OptionExcercises.o1)
OptionExcercises.n1.filter(e => e == 1)
OptionExcercises.o1.filter(e => e == 1)
OptionExcercises.o2.filter(e => e == 1)
OptionExcercises.n1.sequence(OptionExcercises.list)
OptionExcercises.Option.traverse[Int,Int](List[Int](1,2))((x:Int)=> OptionExcercises.Some(x))

def sum(a:Int,b:Int) = a+b
val a: (Int, Int) => Int = sum _

val f1Instance = new Function1[Int,Int](){
  override def apply(v1: Int) = 1
}.apply(_)


def interestRateQuote(age:Int, numberOfSpeedingTickets: Int):Double = {age * numberOfSpeedingTickets}

//val curried = OptionExcercises.None.lift(interestRateQuote _ curried)
