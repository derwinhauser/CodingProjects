/*
 * Person.java
 * name - String
 * age - int
 */

 public class Person{
    // part 1: properties/states/members/attributes
    private String name;
    private int age;

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
    // getters
    public String getName(){
        return name;
    }

    public int getAge(){
        return age;
    }
    // setters
    public void setName(String s){
        name = s;
    }
    public void setAge(int n){
        age = n;
    }

    public void greeting(){
        System.out.print("hello, my name is " + name + ", and I am " + age + " years old\n");
    }
 }