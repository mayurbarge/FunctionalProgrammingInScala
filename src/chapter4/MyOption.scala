package chapter4

sealed trait Option[+A] {
  def map[B](f: A => B): Option[B] = {
    this match {
      case Some(get) => Some(f(get))
      case None => None
    }
  }
  def getOrElse[B >: A](default: => B): B = {
    this match {
      case Some(get) => get
      case None => default
    }
  }

  // flatMap is applied only on Option[Option[B]] two consecutive wrappers
  def flatMap[B](f: A => Option[B]): Option[B] = map(f).getOrElse(None)
  /*{
    val mapped: Option[Option[B]] = map(f)
    // map(f) here actual f passed is A => Option[B]
    // and map has definition f: A => B
    mapped.getOrElse(None)
  }*/

  //Don’t evaluate ob unless needed
  def orElse[B >: A](ob: => Option[B]): Option[B] = this.map(Some(_)).getOrElse(ob)
  /*{
    val mapped: Option[Some[A]] = this.map(a=> Some(a))
    mapped.getOrElse(ob)
  }*/

  def filter(f: A => Boolean): Option[A] = flatMap(a=>if(f(a))Some(a)else None)

  def map2[A,B,C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] = {
    // 1 . this is my implementation and 2. is the solution
    //1
    a.flatMap(aa => b.flatMap(bb => {
      Some(f(aa, bb))
    }))
    // 2
    // a flatMap (aa => b map (bb => f(aa, bb)))
  }


  def lift[A,B](f: A => B): Option[A] => Option[B] = _.map(f)

  /*
  Write a function sequence that combines a list of Options into one Option containing a list of all the Some values in the original list.
  If the original list contains None even once, the result of the function should be None; otherwise the result should be Some with a list of all the values.
   */
  def sequence[A](a: List[Option[A]]): Option[List[A]] = {
    a match {
      case Nil => Some(Nil)
      case head::tail =>{ val a: Option[A] =head.flatMap(a=>Some(a))
        val b: Option[List[A]] = sequence(tail)

        val combine = (x:A,y:List[A]) => x::y
         map2(a,b)(combine)
      }
    }

    /*a match {
      case Nil => Some(Nil)
      case h :: t => h flatMap (hh => sequence(t) map (hh :: _))
    }*/
  }

  def traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] = {
    a match {
      case Nil => Some(Nil)
      case h::t => {
        map2(f(h), traverse(t)(f))(_::_)
      }
    }
  }

  def sequenceViaTraverse[A](a: List[Option[A]]): Option[List[A]] = {traverse(a)(x=>x)
  }
}
case class Some[+A](get: A) extends Option[A]
case object None extends Option[Nothing]

object MyOptionDemo extends App {
  val someOpt = Some("AA")
  val noneOpt:Option[String] = None

  someOpt.map(println)
  println(noneOpt.getOrElse("CC"))

  val someOfSomeOfNone = Some(Some(Some(None)))
  println(someOfSomeOfNone.flatMap(e=>{e.map(a=>a).getOrElse(Some("X"))}))

  // flatten is different from flatMap
  // flatten wont be possible in List(List("A"),"B")

  val listOfLists = List(List("A"),List("B"))
  println(listOfLists.flatMap(e=>{

    "D"
  }))

  println(listOfLists.flatten)


  val someOfSomeOfSome = Some(Some(Some("yes"))) // flatMap wont work with many nested somes hence its not a pure flattening function
  // ideally faltMap should flatten and then map but here flatten step is happening only for first wrapper not subsecuent ones
  // even in Scala option class flatten works at outer wrapper only
  val someString = Some("yes") // flatMap wont work with many nested somes flatMap = flatten + map

  println("flatMap " + someString.flatMap(str => {
    if(str.equals("yes"))
      Some(true)
    else None
  }))

  val opt1 = Some("1")
  val opt2 = Some("1.1")

  println("Map2 " + opt1.map2(opt1,opt2)((a,b)=>{a+b}))
  println("Map2(both None) -  " + opt1.map2(None,None)((a,b)=>{""}))

  val seqOptWithNone = List(Some("1"),Some("2"),None,Some("3"))
  val seqOpt = List(Some("1"),Some("2"),Some("3"))

  println("Sequence with None - " + None.sequence(seqOptWithNone))
  println("Sequence - " + None.sequence(seqOpt))


}


