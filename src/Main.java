import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Тестовое задание Калькулятор_Java");
        Scanner scanner=new Scanner(System.in);
        System.out.println("Введите вычислямое выражение:\nДля выхода из программы введите q");
        String expression="";
        final String exitCode="q";
        // mail loop
        while (true){
            expression = scanner.nextLine();
            //exit code
            if (Objects.equals(expression.toLowerCase(), exitCode)){
                break;
            }
            if (!expression.isEmpty()) { // if non empty string
                System.out.println(calc(expression));
            }

        }
        System.out.println("Goodbye!");
    }

    //receive a string with expression
    // returns a string with result
    public static String calc(String input) throws Exception {
        String result="";
        EvaluateInput ev=new EvaluateInput();
        ev.setExp(input);
        if (!ev.Validate()){
            throw new Exception("Введено недопустимое выражение");
        }
        else{
           return ev.calc();
        }
    }

}