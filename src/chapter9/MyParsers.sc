type ParserError
class Parser[A](data:A)
/*object Parser {
  def apply[A](data:A): Parser[A] = new Parser(a)
}*/

def char(c:Char):Parser[Char]

trait Parsers[A] {
  def run(p: Parser[A])(data:String): Either[ParserError,A] = ???
  def lift(v:A): Parser[A] = new Parser(v)
}
val lifted = new Parsers[String] {}.lift("AA")
