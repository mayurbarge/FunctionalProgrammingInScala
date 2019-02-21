trait RNG {
  def nextInt : (Int, RNG)
}

type Rand[+A]  = RNG => (A,RNG)

def unit[A](a:A):Rand[A] =
  rng => (a,rng)

def map[A,B](s:Rand[A])(f:A=>B) : Rand[B] = rnd => {
    val (val1,newRnd) = s(rnd)

    val mapped = f(val1)
    (mapped,newRnd)
  }

def map2[A,B,C](p:Rand[A],q:Rand[B]) (f: (A,B) => C ): Rand[C] = {
  rng=> {
    val (a,state)= p(rng)
    val (b,finalState) = q(state)
    (f(a,b),finalState)
  }
}

def both [A,B](ra : Rand[A], rb: Rand[B]):Rand[(A,B)] = map2(ra,rb)((_,_))
