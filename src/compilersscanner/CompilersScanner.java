package compilersscanner;

import java.util.ArrayList;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author David
 */
public class CompilersScanner {
    
    public static boolean isNumeric(String str)  {  
      try  
      {  
        double d = Double.parseDouble(str);  
      }  
      catch(NumberFormatException e)  
      {  
        return false;  
      }  
      return true;  
    }
    
    public static void main(String[] args) {
        String text = null;
        try{
            text = new String(Files.readAllBytes(Paths.get("tiny_sample_code.txt")));
        }
        catch(IOException ex){
            
        }
        System.out.println("-------Input code--------");
        System.out.println(text);
        //looks for comments between { } and removes them
        for(int i=0; i<text.length(); i++){
            if(text.charAt(i) == '{'){
                for(int j=i+1; j<text.length(); j++){
                    if(text.charAt(j) == '}'){
                        String toBeReplaced = text.substring(i, j+1);
                        String replace = text.replace(toBeReplaced, "");
                        text = replace;
                        break;
                    }
                }
            }
        }
        //looks for special symbols inserting a space before & after them
        for(int i=0; i<text.length(); i++){
            char x = text.charAt(i);
            if(x=='+' || x=='-' || x=='*' || x=='/' || x=='=' || x=='<' || x == '(' || x==')'
                    || x==';'){
                StringBuilder s = new StringBuilder(text);
                s.insert(i, " ");
                s.insert(i+2," ");
                text = new String(s);
                i+=1;
            }
            else if(x == ':')
                if(text.charAt(i+1) == '='){
                    StringBuilder s = new StringBuilder(text);
                    s.insert(i, " ");
                    s.insert(i+3," ");
                    text = new String(s);
                    i+=2;
                }
        }
        System.out.println("----Inserted spaces & removed comments----");
        System.out.println(text);
        
        //converting the whole block of code to a single line
        String str3 = text.replaceAll("\n", " ").replaceAll("\r", " ");
        text = str3;
        //spliting the code into tokens (smaller strings)
        String [] copyStr = text.split(" ");
        ArrayList <String> finalStr = new ArrayList();
        for(int i=0; i<copyStr.length; i++){
            if(!copyStr[i].equals("")){
                finalStr.add(copyStr[i]);
            }
        }
        //scanning each token for its type
        String [] type = new String [finalStr.size()];
        for(int i=0; i<finalStr.size(); i++){
            String str = finalStr.get(i);
            if(str.equals("if") || str.equals("then") || str.equals("else") || str.equals("end") || str.equals("repeat")
                    || str.equals("until") || str.equals("read") || str.equals("write")){
                //type[i] = "reserved word";
                type [i] = str;
            }
            else if (str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/") || str.equals(":=")
                    || str.equals("=") || str.equals("(") || str.equals(")") || str.equals("<") || str.equals(";")){
                //type[i] = "special symbol";
                type [i] = str;
            }
            else if (isNumeric(str)){
                type[i] = "number";
            }
            else{
                type[i] = "identifier";
            }
        }
        System.out.println("---------Output---------");
        for(int i=0; i<finalStr.size(); i++){
            System.out.println(finalStr.get(i)+" --> "+type[i]);
        }
        //writing the output to the file
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\NetBeans Projects\\CompilersParser\\scanner_output.txt"));
            for(int i=0; i<finalStr.size(); i++){
                //bw.write(finalStr.get(i)+" --> "+type[i]);
                bw.write(type[i]+" ");
                //bw.newLine();
            }
            bw.close();
        }
        catch(IOException ex){
            
        }
    }
}
