import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EvaluateInput {
    private String exp;
    private  boolean isRoman = false;
    private  boolean isArabic=false;
    private  char calc_operator=0;
    private int first=0;
    private int second=0;
    private final Pattern roman_pattern;
    private final Pattern arabic_pattern;

    //finals
    final  private String roman_regex= "^[XIV]{1,4}[\\+\\-\\/\\*\\\\]{1}[XIV]{1,4}$";
    final private String arabic_regex="^[0-9]{1,2}[\\+\\-\\/\\*\\\\]{1}[0-9]{1,2}$";
    final private String[] romanNumbers = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX","X"};
    // impossible to create result bigger than 100 when you have only X*X
    final private String[] hundreds = {"", "C"};
    final private String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
    private final CharSequence  plus="+";
    private final CharSequence  minus="-";
    private final CharSequence  div="/";
    private final CharSequence  div2="\\";
    private  final CharSequence  mult="*";


    // constructor
    EvaluateInput(){
        this.roman_pattern = Pattern.compile(roman_regex);
        this.arabic_pattern = Pattern.compile(arabic_regex);
    }

    // set string expression
    public void setExp(String exp){
        // replace whitespaces and save the string
        this.exp=exp.replaceAll("\\s+","");
    }

    // validate expression
    public boolean Validate(){
        this.isArabic=false;
        this.isRoman=false;
        // test for Roman numbers expression
        // empty input
        if (this.exp.length()==0){
           // System.out.println("Введите выражение...");
            return false;
        }
        boolean maybeRoman=false;
        Matcher roman_matcher = roman_pattern.matcher(this.exp);
        maybeRoman = roman_matcher.matches();
        // looks like Roman
        if (maybeRoman){
            //System.out.println("ma bee Roman!");
            if (this.parseRoman()){ // try to parse
                this.isRoman=true;
               // System.out.println("Roman!");
                return  true;
            }
        }
        // not Roman
        isRoman=false;
        boolean mayBeArabic=false;
        Matcher arabic_matcher = arabic_pattern.matcher(this.exp);
        mayBeArabic = arabic_matcher.matches();
        if (mayBeArabic){
           // System.out.println("ma bee Arabic!");
            if (this.parseArabic()){ // try to parse
                this.isArabic=true;
             //   System.out.println("Arabic!");
                return  true;
            }
        }

        return  false;
    }

    // try to parse String as Roman numbers exp
    // return true if success;
    boolean parseRoman(){
        String[] ex_parts=this.exp.split("[\\+\\-\\/\\*\\\\]{1}");
        //System.out.println(ex_parts.length);
        // expression is supposed to look like 2+2
        if (ex_parts.length!=2){ return false;}
        //find operator
        calc_operator=findOperator();
        if (calc_operator==0){ return false;} // if operator not found
        // parse first number
        try {
           first= romanToInt(ex_parts[0]);
           second=romanToInt(ex_parts[1]);
        }
        catch ( Exception e){ return false; }
        return true;
    }

    boolean parseArabic(){
        String[] ex_parts=this.exp.split("[\\+\\-\\/\\*\\\\]{1}");
        if (ex_parts.length!=2){ return false;} // split string
        calc_operator=findOperator(); // find operator (*,-, etc.)
        if (calc_operator==0){ return false;} // if operator not found
       // System.out.println(ex_parts[0]);
        int tmp=Integer.parseInt(ex_parts[0]);
        if (tmp>0 && tmp<11){first=tmp;} else {return false;}
        tmp=Integer.parseInt(ex_parts[1]);
        if (tmp>0 && tmp<11){second=tmp;} else {return false;}
        return true;
    }

    int romanToInt(String str) throws Exception {
        for (int i=0;i<romanNumbers.length;++i){
            if (Objects.equals(romanNumbers[i],str)){
                return i;
            }
        }
        throw new Exception("Cannot parse roman");
    }

    // convert int to Roman format number
    // return String
    String intToRoman(int x) throws  Exception{
        if (x<1){throw new Exception("Результат меньше единицы");}
        return hundreds[x / 100] + tens[(x % 100) / 10] + romanNumbers[x % 10];
    }

    char findOperator(){
        int counter=0;
        char  result=0;
        if (this.exp.contains(plus)){
            ++counter;
            result=plus.charAt(0);
        }
        if (this.exp.contains(minus)){
            ++counter;
            result=minus.charAt(0);
        }
        if (this.exp.contains(div) ){
            ++counter;
            result=div.charAt(0);
        }
        if (this.exp.contains(div2)){
            ++counter;
            result=div.charAt(0);
        }
        if (this.exp.contains(mult)){
            ++counter;
            result=mult.charAt(0);
        }

        if (counter==1){
            return  result;
        }
        return 0;
    }


    public String calc() throws Exception {
        int res = switch (calc_operator) {
            case '+' -> first + second;
            case '/' -> first / second;
            case '*' -> first * second;
            case '-' -> first - second;
            default -> 0;
        };
        //System.out.println(res);
        // if we need to convert result to Roman format
        if (isRoman){
            return intToRoman(res);
        }
        return Integer.toString(res);
    }
}
