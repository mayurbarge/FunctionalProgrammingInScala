case class Price(amount:Double)
case class Charge(cc:CreditCard, price: Price) {
  def combine(other: Charge):Charge = {
    if(cc == other.cc) {
      Charge(cc, Price(price.amount+other.price.amount))
    } else {
      throw new Exception("Cannot add amounts from different cards.")
    }
  }

  def coalesce(charges:List[Charge]): List[Charge] = {
    charges.groupBy(_.cc).values.toList.map(_.reduce(_ combine _))
  }
}
case class Coffee() {val price = Price(12.3)}

case class CreditCard() {
}

class Cafe {
  def buyCoffee(cc: CreditCard): (Coffee, Charge) = {
    val cup = Coffee()
    val charge = Charge(cc,cup.price)
    (cup, charge)
  }
  def buyCoffees(n:Int, cc:CreditCard): (List[Coffee], Charge) = {
    val purchases:List[(Coffee,Charge)] = List.fill(n)(buyCoffee(cc))
    val (coffees, charges) = purchases.unzip

    (coffees, charges.reduce((c1,c2) => c1.combine(c2)))
  }
}

/*
So we now have a Cafe which need not know how to process payments.
We can still have Payments class but Cafe does not need to know about it at all.
A kind of Separation of payments logic from Cafe is done

Implement the program with a pure core and a thin layer on the outside that handles effects
 */