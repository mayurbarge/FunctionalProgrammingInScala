def findFirst[A](arr:Array[A], p:(A)=>Boolean) = {
  def loop(index:Int): Int = {
    if(index >= arr.length) -1
    else if(p(arr(index))) index
    else loop(index+1)
  }
  loop(0)
  // Recursive helper functions inside function are named
  // loop or go by convention
  // method names f,g, h or p are conventions
  // for parameters to HOF because HOF's are so general
  // and have no opinion on what arguments should do
}
/*
loop call is tail recursive
When last call simply calls method itself with new params then it can be called
tail recursive
if it was else 1 + loop() then first run would have to wait before
 */

findFirst(Array(1,2,2,4,5), (x: Int) => x==4)

(x:Int) => x==4

/*
above definition creates a function object as
new Function1[Int,Boolean] = {
  def apply(x:Int):Boolean = {x ==4}
}

The way calling List(1,2,3) calls List.apply with parameters 1,3,3
is same as calling
predicate(array(index)) which invokes apply method in newly created function object

 */
object A


(x:Int) => x*2


// andThen can be applied on function1
val oneParamFun = (x:Int) => x*2
oneParamFun.andThen(x => x *5)

val twoParamFun = (x:Int,y:Int) => x*y
twoParamFun.tupled andThen(x => x+5)

// for two param function you need to make tupled and then apply
// twoParamFun andThen(x => x+5) fails
// (x:Int):Int => x*2 fails --???

// partial functions
def partial1[A,B,C](a:A,f:(A,B)=>C):B=>C = (b:B)=>f(a,b)
// in implementation (b:B)=>f(a,b) we can even omit type B as its inferred in definion of f
//redbook says b can be inferred somehow not working
//def partialInferredB[A,B,C](a:A,f:(A,B)=>C) = {b=>f(a,b)}

def curry[A,B,C](f:(A,B)=>C): A=>(B=>C) = (a:A) => (b:B) =>f(a,b)
val curriedFun = curry((a:Int,b:Double)=>s"Int - ${a} Double - ${b}")
curriedFun(1)(2)

def uncurry[A,B,C](f:A=>B=>C):(A,B)=>C = (a:A,b:B) => f(a)(b)
val uncurriedFun = uncurry( (reading:String) => (multiplier:Double) => s"${reading.toInt * multiplier}  Farenhite")
uncurriedFun("12",1.1)
uncurriedFun("2",3.2)

/*
? Does stackable trait behaviour can be modelled with currying ?
 */
def compose[A,B,C](f:B=>C,g:A=>B):A=>C = (a:A)=>f(g(a))
val composedFun = compose( (readingValue:Double) => s"${readingValue} Celcius",(reading:String) => reading.toDouble)
composedFun("12.3")

/*
**IMP**
Thre definitions partial, curry, uncurry and compose can be used as
building blocks for any kind of functional programs
 */



trait C
val original = new C {
  val age: Int = 30;
  val name:String = "name"
}
var refined: C {val age:Int} = original

original.name