

case class
Item(id: String, name: String, price: Float)

val item = Item("AA","BB",2f)
val items = List(item,item)
val opt = Some(Some(Some("1")))

opt.flatMap(a=>a.toInt)
