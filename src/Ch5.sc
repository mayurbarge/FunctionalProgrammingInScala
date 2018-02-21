
val ll = List.empty
val ll1 = List("LB")
val zipped = ll1.zipWithIndex
val unzipped = zipped.unzip


val z = ll1.map(s"lcase(" + _ + ")").mkString(",")

val x = ll1 match {
  case x if(x.isEmpty)=> ""
  case _=> {
    println("333")
    ll1.map(s"lcase(" + _ + ")").mkString(",")
  }
}

val list = List("benzine", "benzol", "benziiine")

list.indexOf("benzine")
val zipped2 = list.zipWithIndex
val unzipped2 = zipped2.unzip


def relaxScientificNotationForSmallRanges(significantDigits: Int, propertyValue: Double): String = {
  val formattedValue: String = exponentialNotation(significantDigits, propertyValue)
  val exponent = formattedValue.split("e").last.toInt

  if(exponent >= -2 && exponent <= 4 )
    formattedValue.toFloat.toString
  else formattedValue
}

def exponentialNotation(significantDigits: Int, propertyValue: Double) = ("%."+significantDigits+"e").format(propertyValue)


relaxScientificNotationForSmallRanges(5,21.1)

case class Header(name:String)

val headers = List(Header("A"), Header("D"),Header("C"))

headers.groupBy(header => header.name).toList.map(keyValue => keyValue._2.head)




