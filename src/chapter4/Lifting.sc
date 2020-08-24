import scala.util.Try

val toInt:String => Int = x => x.toInt
def lift[A,B](f: A => B): Try[A] => Try[B] = _ map(f)
val toIntTry = lift(toInt)
toIntTry(Try("4"))
toIntTry(Try("ijk"))

def sequence[A](a: List[Option[A]]): Option[List[A]] = {
  a match {
    case Nil => Some(Nil)
    case h :: t => h flatMap (hh => sequence(t) map (hh :: _))
  }
}
sequence(List(Some(1),Some(2)))
sequence(List(None))
sequence(List.empty)
