package chapter2;
public class AnnonymousClassInJava {
    interface Foo {}
    public static void main(String[] args) {
        new Foo(){
            public void tellYourName() {
                System.out.println("My name is Foo");
            }
        }.tellYourName();

        /*
        scala shorthand
        object Foo {
            def tellYourName = println("My name is Foo")
        }
        Foo.tellYourName
         */

    }
}

/*
object keyword in scala creates new singleton type
Declaring an object in scala is like creating a new instance of anonymous class
 */