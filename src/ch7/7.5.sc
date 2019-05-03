import java.util.concurrent.{Callable, ExecutorService, Future}

import scala.concurrent.duration.TimeUnit
import scala.util.Try

type Par[A] = (ExecutorService) => Future[A]

private case class UnitFuture[A](get:A) extends Future[A] {
  def isDone = true
  def isCancelled = false
  def cancel(evenIfRunning:Boolean) = false
  def get(timeout:Long, units:TimeUnit): A = get

}


object Par {
  def unit[A](a:A):Par[A] = (es:ExecutorService) => UnitFuture(a)
  def map2[A,B,C](a:Par[A],b:Par[B])(f:(A,B) => C):Par[C] = {
    (es:ExecutorService) => {
      val af = a(es)
      val bf = b(es)
      UnitFuture(f(af.get,bf.get))
    }

  }

  def lazyUnit[A](a: => A):Par[A] = fork(unit(a))

  def fork[A](a: =>Par[A]) = {
    (es:ExecutorService) => {
      es.submit(new Callable[A] {
        override def call() = a(es).get
      })
    }
  }

  def map[A,B](pa:Par[A])(f:A=>B):Par[B] = {
    map2(pa, unit())((a,_)=> f(a))
  }

  def asyncF[A,B](f: A => B):A => Par[B] =  (a:A)=>lazyUnit(a)


  def parMap[A,B](ps: List[A])(f: A => B): Par[List[B]] = {
    val fbs: List[Par[B]] = ps.map(asyncF(f))
    sequence(fbs)
  }

  def parFilter[A](as: List[A])(f: A => Boolean): Par[List[A]] = as.filter(f)

  def sequence[A](l: List[Par[A]]): Par[List[A]] = {
    l.foldRight[Par[List[A]]](unit(List()))((h,t) => map2(h,t)(_ :: _))
  }
}