public class AreaSine{
    public static void main(String[] args){
        
        int N = 100000;
        double pi = Math.PI;
        double area = 0;

        for (int i = 0; i<= N; i++){
            area = area + pi/N*(Math.sin((pi/N)*i));
        }
        System.out.println(area);
    }
}