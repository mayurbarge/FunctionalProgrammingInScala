
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait A {
  def test[A](future:Future[A]) : Future[A]
}

trait Z extends A {
  // 'abstract override' modifier required as
  // the test() method is not yet implemented
  abstract override def test[A](future:Future[A]) : Future[A] = {
    super.test(future) transform(s=>s,
      e =>{
        println("Z")
        e
      }
    )
  }
}

trait B extends A {
  // 'abstract override' modifier required as
  // the test() method is not yet implemented
  abstract override def test[A](future:Future[A]) : Future[A] = {
    super.test(future) transform(s=>s,
      e =>{
        println("B")
        e
      }
    )
  }
}

//class C extends A { override def test = { "C" } }


class C extends A {
  override def test[A](future:Future[A]) : Future[A] = {
    future
  }
}
abstract  class D extends C with Z with B

object Main {

  def main(args: Array[String]): Unit = {
    val futureX = new D {} .test(Future{
      println("call to service")
      Thread.currentThread().wait(5)
      throw new Exception})
    //val statsCollector =  new TimeoutStatsCollector with TimeoutStats with TimeoutStats2
    await(futureX)

  }
}

/**
  * Linearizations
  * StatsCollector, ServiceStats
  */