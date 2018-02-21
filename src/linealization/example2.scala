package linealization


object example2 {

  class A{
    def test = {
      "this is the base job"
    }
  }

  trait B extends A {
    // 'abstract override' modifier required as
    // the test() method is not yet implemented
    override def test = {
      s"B${super.test}"
    }
  }

  //class C extends A { override def test = { "C" } }


  class C {
    def test = { s"C" }
  }
  //class D extends C with B




  def main(args: Array[String]): Unit = {
    //val x = new C with B // works as expected // C, B, A
    //x.test

   // println(new D().test)

  }
}
