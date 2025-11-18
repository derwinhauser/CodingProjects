/*
 * Person Test.java
 */

 public class PersonTest{
    //main
    public static void main(String[] args){
        Person p1 = new Person();
        Person p2 = new Person("Derwin", 22);
        Person p3 = new Person(25, "Emily");
        p1.greeting();
        p2.greeting();
        p3.greeting();
    }
 }