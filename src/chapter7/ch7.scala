import java.util.concurrent.{Callable, ExecutorService, Future}

import scala.concurrent.duration.TimeUnit
import scala.util.Try

type Par[Either] = (ExecutorService) => Future[Either]

private case class UnitFuture[E,A](get:A) extends Future[Either[E,A]] {
  def isDone = true
  def isCancelled = false
  def cancel(evenIfRunning:Boolean) = false
  def get(timeout:Long, units:TimeUnit): Either[E,A] = Right(get)

}


private case class TimedFuture[E,A](get:A) extends Future[Either[E,A]] {
  def isDone = true
  def isCancelled = false
  def cancel(evenIfRunning:Boolean) = false
  def get(timeout:Long, units:TimeUnit): Either[E,A] = Right(get)
}

object Par {
  def unit[B,A](a:A):Par[Either[B,A]] = (es:ExecutorService) => UnitFuture(a)
  def map2[A,B,C,E](a:Par[Either[E,A]],b:Par[Either[E,B]])(f:(A,B) => C):Par[Either[E,C]] = {
    (es:ExecutorService) => {
      val af = a(es)
      val bf = b(es)
      UnitFuture(f(af.get,bf.get))
    }

  }

  def fork[A,E](a: =>Par[Either[E,A]])(timeout:Long, units:TimeUnit) = {
    (es:ExecutorService) => {
      es.submit(new Callable[Either[E,A]] {
        override def call() =  {
          Try { a(es).get(timeout,units) }.toOption.getOrElse("Timed out")
        }
      })
    }
  }



}
