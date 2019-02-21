
val list = List(1,2,3)
list.find(x=> x==3)


def getNormalizedSearchTermWithoutStar(searchTerm: String): String = {
  searchTerm match {
    case term if(term== "*" || searchTerm == "?") =>
    searchTerm
    case _=>
    searchTerm.replace("?", " ").replace("*", " ").replaceAll("\\s+", " ").trim
  }
}


val list2 = List(1,2,3)
val map1 = list2.map {
  def f(x: Int) : Int = {
    333
  }

  f
}

val map2 = list2.map (
  _ => 2
)

val map3 = list2.map {
  _ => 2
}

list2

getNormalizedSearchTermWithoutStar("who m i?")
