
val JSONString: String = scala.io.Source.fromURL("http://sm-api-graph-search.dev.cf.private.springer.com/search?query=refractive+index+%3C+100&propertyFacet=").mkString

class CC[T] { def unapply(a:Any):Option[T] = Some(a.asInstanceOf[T]) }

object M extends CC[Map[String, Any]]
object L extends CC[List[Any]]
object S extends CC[String]
Int

val result = for {
  Some(M(map)) <- List(JSON.parseFull(JSONString))
  L(activeInstances) = map(“activeInstances”)


  M(activeInstance) <- activeInstances
  S(id) = activeInstance(“id”)
} yield id