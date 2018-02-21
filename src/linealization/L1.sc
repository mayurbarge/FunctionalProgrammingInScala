
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait A {
  def test[A](future:Future[A]) : Future[A]
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
class D extends C with B

val futureX = new D().test(Future{throw new Exception})


