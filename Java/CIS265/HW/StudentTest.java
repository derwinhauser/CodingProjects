import java.util.ArrayList;

public class StudentTest{
    public static void main(String[] args){
        ArrayList <Student> students = new ArrayList <Student>();
        Student s1 = new Student("John", 3.68);
        Student s2 = new Student("Mary", 4.00);
        Student s3 = new Student("Sam", 3.5);
        double tempgpa = 0.0;
        s1.introduce();
        students.add(s1);
        students.add(s2);
        students.add(s3);
        for (int i=0; i<students.size(); i++){
            Student temp = students.get(i);
            tempgpa = tempgpa+temp.getGpa();
        }
        double avg = tempgpa/students.size();
        System.out.println("The average GPA of the students is: " + avg);
    }
}