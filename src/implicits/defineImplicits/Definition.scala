package implicits.defineImplicits

object Definition {
  implicit class AppendABC(s: String) {
    def appendWithABC = s + "ABC"
  }
}
