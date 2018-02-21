/*
Scala Ref
Trait is a class that is to be added to some other class as mixin
 */
// Unlike normal classes, traits cannot have constructor parameters.
// Furthermore, no con- structor arguments are passed to the superclass of the trait.
// This is not necessary as traits are initialized after the superclass is initialized.
//trait Serpant(legs:Int){}
abstract class Animal(name:String) {}


