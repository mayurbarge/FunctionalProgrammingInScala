package linealization

trait T1 {
  def foobar() = println("1")
}

trait T2 {
  def foobar() = println("2")
}

class B extends T2 {
  override def foobar() = println("42")
}

class A extends B with T1 with T2 {
  override def foobar() = super.foobar()
}

/*

L(A) = A, L(T2) +: L(T1) +: L(B)
L(B) = B, L(T2)
L(T2) = T2
L(T1) = T1
L(A) = A, T2 +: (T1, B, T2)
L(A) = A, T1, B, T2

 */
object A {

  def main(args: Array[String]): Unit = {
    new A().foobar()
  }

}
/*
trait A {
    def test : String
}
trait B extends A {
    // 'abstract override' modifier required as
    // the test() method is not yet implemented
    abstract override def test = {
        s"B${super.test}"
    }
}
class C extends A with B {
    // test method concrete implementation
    override def test = { "C" }
}

<console>:10: error: overriding method test in trait B of type => String;
method test needs `abstract override' modifiers
   class C extends A with B { override def test = { "C" } }


   Works>>>
   class C extends A { override def test = { "C" } }
new C with B // works as expected

class C extends A {
    override def test = { "C" }
}
class D extends C with B

new D().test
res5: String = BC


When we now look at the examples which work, then we'll why they actually work. In the case of C extends A with new C with B, you basically create an anonymous class Z extends C with B. The linearization of Z is

Z, B, C, A
There you see, that B can find in C an implementation of test. Thus, the code can compile. The same holds true for the example with class D.

 */