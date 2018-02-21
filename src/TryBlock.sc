

import java.io.File

import scala.util.{Failure, Success, Try}

def transformFile = {
  find match {
    case Success(isFileExpanded) =>
      println("Success >> " + isFileExpanded)
    case Failure(ex) =>
      println("Failed >> " + ex)
  }
}
def find: Try[Boolean] = {
  Try {
    transformationFunction
    true
  }
}
def transformationFunction: Unit = {
  throw new Throwable("my exception")
}

val x = transformFile

def throwError = {
  try {
//throw new Error("111111")
    println("Done successfully...")
  }catch{
    case e => throw e
  }finally {
    try {
      throw new Exception("22222")
    } catch {
      case e:Throwable =>
       // throw e
    }
    try {
      throw new Exception("333333")
    } catch {
      case e:Throwable =>
    }
  }
}

val z = throwError

