class FilledBox[A] {
  def fold[A](isEmtpty:A) = isEmtpty
}

// type parameter A and method parameter A are not same
new FilledBox[Int]().fold[String]("str")

Either