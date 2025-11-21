public class Student{
    private String name;
    private double gpa;

    public Student(){
        name = "no name";
        gpa = 0.0;
    }

    public Student(String s, double d){
        name = s;
        gpa = d;
    }
    
    public Student(double d, String s){
        name = s;
        gpa = d;
    }

    public void setName(String s){
        name = s;
    }

    public void setGpa(double d){
        gpa = d;
    }

    public String getName(){
        return name;
    }

    public double getGpa(){
        return gpa;
    }

    public void introduce(){
        System.out.println("Hello. My name is " + name + " and my GPA is " + gpa);
    }
}