def factRec(i:Int) = {
  @annotation.tailrec
  def go(n: Int, acc: Int):Int = {
    if(n<=0) acc
    else go(n-1, n*acc)
  }
  go(i,1)
}
factRec(0)
factRec(1)
factRec(2)
factRec(3)

// 0       1  1  2  3   5
// f(0) f(1) ...   fib(4)
def fibo(i:Int) = {
  @annotation.tailrec
  def go(n: Int, acc: Int):Int = {
    //println(s"i : ${i} n: ${n} acc: ${acc}")
    if(n == i) acc
    else if(n == 0 || n == 1) go(n+1, 1)
    else go(n+1, acc + n)

  }
  go(0,0)

}
//0 1 2 3 4 5 6  7  8
//0 1 1 2 3 5 8 13


fibo(3)

