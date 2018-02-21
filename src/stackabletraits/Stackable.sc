type Module

val pluginAModule = new FinalModules()
val pluginBFirstModule = new FinalModules()
val pluginBSecondModule = new FinalModules()

trait Modules {
  def modules: Vector[Module]
}

trait EmptyModules extends Modules {
  override def modules: Vector[Module] =
    Vector.empty
}

trait PluginAModules extends Modules {
  abstract override def modules: Vector[Module] =
    super.modules :+ pluginAModule
}

trait PluginBModules extends Modules {
  abstract override def modules: Vector[Module] =
    super.modules ++ Vector(pluginBFirstModule, pluginBSecondModule)
}



class FinalModules
  extends EmptyModules
    with PluginAModules
    with PluginBModules

println(new FinalModules().modules)