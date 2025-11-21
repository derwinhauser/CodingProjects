import java.util.ArrayList;

public class StudentTest{
    public static void main(String[] args){
        // Variables
        ArrayList <Student> students = new ArrayList <Student>();
        Student s1 = new Student("John", 3.68);
        Student s2 = new Student("Mary", 4.00);
        Student s3 = new Student("Sam", 3.5);
        double tempgpa = 0.0;

        //test introduce function
        s1.introduce();
        // add students to ArrayList
        students.add(s1);
        students.add(s2);
        students.add(s3);

        //Calculate avg
        for (int i=0; i<students.size(); i++){
            Student temp = students.get(i);
            tempgpa = tempgpa+temp.getGpa();
        }
        double avg = tempgpa/students.size();
        // print avg
        System.out.println("The average GPA of the students is: " + avg);
    }
}