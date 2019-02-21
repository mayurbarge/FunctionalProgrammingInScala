
val fun1 = (x:Int) => x*2
val fun2 = (x:Int, y:Int) => x*y

def a = 5
val b = a _
val funZero = () => 3
fun1 andThen ( (y:Int) => y+1 )

fun2.tupled

//fun1 andThen(b)



def addUmm(x:String) = x + " umm"
def addAhem(x:String) = x + " ahem"

//val ummThenAhem = addUmm(_).compose(addUmm(_)) does not work
val ummThenAhem = addUmm _ compose(addAhem _)

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
def  ifElseWithConditionCheck(
  s:String, i:Int,

  inverserSearch:(String, Int) => Future[Set[Int]],
  spSearch:(String, Int) => Future[Set[Int]],
                          condition:(Future[Set[Int]]) => Future[Boolean])
= {
  val inverSearchResult  = inverserSearch(s,i)

  condition(inverSearchResult).flatMap {
    case true => inverSearchResult
    case false => spSearch(s,i)
  }
}
