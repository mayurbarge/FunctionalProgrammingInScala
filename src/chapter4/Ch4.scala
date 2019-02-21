package chapter4

trait MyTry[+A] {
  def map[B](f: A=>B ):MyTry[B] ={
    this match {
      case MySuccess(data) => MySuccess(f(data))
      case MyFailure(data) => MyFailure(f(data))
    }
  }

}
case class MySuccess[+A](data:A) extends MyTry[A]
case class MyFailure[+A](data:A) extends MyTry[A]

