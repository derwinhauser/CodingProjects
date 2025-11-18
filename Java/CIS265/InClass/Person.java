/*
 * Person.java
 * name - String
 * age - int
 */

 public class Person{
    // part 1: properties/states/members/attributes
    String name;
    int age;

    // part 2: constructors
    public Person(){
        name = "no name";
        age = 0;
    }

    public Person (String s, int n){
        name = s;
        age = n;
    }
    public Person (int n, String s){
        name = s;
        age = n;
    }
    // part 3: methods
    public void greeting(){
        System.out.print("hello, my name is " + name + ", and I am " + age + " years old\n");
    }
 }