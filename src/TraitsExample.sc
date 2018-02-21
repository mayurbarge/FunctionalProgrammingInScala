trait A {

}
trait B extends  A {

}

val xy = new AnyRef with A with B
