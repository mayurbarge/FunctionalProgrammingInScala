import java.util.concurrent.{Callable, ExecutorService, ForkJoinPool, ForkJoinTask, Future}

import scala.concurrent.ExecutionContext

trait Par[A] {
  def get:A
  def getTask:Task[A]


}
case class ParTask[A](a: A) extends Par[A] {
  val task: Task[A] = new Task[A](a)

  override def get: A = task.result
  def getTask = task
}

class Task[A](a:A) {
  def task = MyExecutor.executor.submit(t)
  def result = MyExecutor.executor.invoke(task)

  val t = new Callable[A] {
    def call: A = {
      a
    }
  }
}

object MyExecutor {
  val executor = new ForkJoinPool(10)
}

object Par {
  //def map[A,B](f : A => B) : Par[B] = ???

  def unit[A](a: A): Par[A] = ParTask[A](a)
  //def map2[A,B,C](a: Par[A], b: Par[B])(f: (A,B) => C): Par[C] = ???
  def fork[A](a: => Par[A]): Par[A] = ParTask[A](a.getTask.result)
  // fork executes a by calling a.getTask.result hance forking actually performs task of run method in this implementation
  // hence this code has issues
  // one way to resolve this is create subtype of Par or create new Par given input Par

  def lazyUnit[A](a: => A): Par[A] = fork(unit(a))
  def run[A](a: Par[A]): A = a.get


  def asyncF[A,B](f:A=>B):A => Par[B] = {
    (a:A) => lazyUnit(f(a))
  }

}


object ParDemo extends App {
  override def main(args: Array[String]): Unit = {
      val f = (l:List[Int])=>l.sum
    println("11111")
      Par.run(Par.fork(Par.unit(f)))
    print("!!!!!!!!!!!!!!!!!!!!!!!!!")
      println(Par.run(Par.fork(Par.unit(2))))


  }
}
