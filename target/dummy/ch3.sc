List("a", "b")
// List types are defined as covariants
// +A means if S is a subtype of T then List[S] is subtype of List[T]
List[String]("a").isInstanceOf[scala.collection.immutable.List[Object]]
// Nil is defined as a subtype of List[Nothing] hence it is a subtype of every other lsit
Nil.isInstanceOf[List[Int]]
Nil.isInstanceOf[List[String]]

val searchTerm = "tin zinc sulfate acid some text carbon some2"
val matList = List("tin zinc", "carbon")
val envList = List("sulfate", "acid")
val ncList = List("some text", "some2")


val x =searchTerm.split(" ").toList

//searchTerm.split(" ").sliding(3).map(_.mkString(" ")).toList
val list = ("mat","b")::("mat", "a"):: Nil

def search(matList: List, envList: List, ncList: List, x: List) {
  x.foldLeft(("", List(("", "")))) {
    (a, b) =>
      //println(s"A is ${a}" )
      //println(s"B is ${b}")
      val s = (a._1 + " " + b).trim
      //println(s"S is ${s} \n")

      if (matList.contains(s)) {
        println("Mat - " + a._1 + " " + b)
        val appended = ("mat", (a._1 + " " + b).trim) :: a._2
        println(s"Appended list is  ${appended}")
        ("", appended)

      } else if (envList.contains(s)) {
        println("Env - " + a + " " + b)
        val appended = ("env", (a._1 + " " + b)) :: a._2
        println(s"Appended list is  ${appended}")
        ("", appended)
      } else if (ncList.contains(s)) {
        val appended = ("nc", (a._1 + " " + b)) :: a._2
        println(s"Appended list is  ${appended}")
        ("", appended)
      } else {
        ((a._1 + " " + b).trim, a._2)
      }
    //(a + " " + b).trim
    //(a + " " + b).trim

  }
}


val numbers = List(5, 4, 8, 6, 2)
numbers.fold(0) { (z, i) =>
  z + i
}

val sentence = List("Mary", "had", "a", "little", "lamb")
sentence.foldLeft("start")((a,b) => {
  println("[a:" + a + "][b:"+ b + "]");
  a + b
})