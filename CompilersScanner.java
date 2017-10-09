package compilersscanner;

import java.util.ArrayList;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author David
 */
public class CompilersScanner {

    public static void main(String[] args) {
        String text = null;
        try{
            text = new String(Files.readAllBytes(Paths.get("tiny_sample_code.txt")));
        }
        catch(IOException ex){
            
        }
        System.out.println("-------input code--------");
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
        //looks for special symbols storing them in the arraylist & replaces them with a space
        ArrayList <String> symbols = new ArrayList();
        for(int i=0; i<text.length(); i++){
            char x = text.charAt(i);
            if(x=='+' || x=='-' || x=='*' || x=='/' || x=='=' || x=='<' || x == '(' || x==')'
                    || x==';'){
                symbols.add(""+x);
                String str = text.replace(x, ' ');
                text = str;
            }
            else if(x == ':')
                if(text.charAt(i+1) == '='){
                    symbols.add(":=");
                    String str = text.replace(':', ' ');
                    text = str;
                    String str2 = text.replace('=', ' ');
                    text = str2;
                }
        }
        
        System.out.println("--------removed comments and replaced symbols with spaces-------");
        System.out.println(text);
        
        //converting the whole block of code to a single line
        String str3 = text.replaceAll("\n", " ").replaceAll("\r", " ");
        text = str3;
        
        String [] copyStr = text.split(" ");
        ArrayList <String> finalStr = new ArrayList();
        for(int i=0; i<copyStr.length; i++){
            if(!copyStr[i].equals("")){
                finalStr.add(copyStr[i]);
            }
        }
        //finding the reserved words and other (identifiers/numbers)
        ArrayList <String> Reserved = new ArrayList();
        ArrayList <String> Other = new ArrayList();
        for(int i=0; i<finalStr.size(); i++){
            String s = finalStr.get(i);
            if(s.equals("if") || s.equals("then") || s.equals("else") || s.equals("end") || s.equals("repeat") || 
                    s.equals("until") || s.equals("read") || s.equals("write")){
                Reserved.add(s);
            }
            else
                Other.add(s);
        }
        //removing dublicates from the arraylists
        Set <String> order = new LinkedHashSet<>(symbols);
        order.addAll(symbols);
        symbols.clear();
        symbols.addAll(order);
        order.clear();
        
        order = new LinkedHashSet<>(Reserved);
        order.addAll(Reserved);
        Reserved.clear();
        Reserved.addAll(order);
        order.clear();
        
        order = new LinkedHashSet<>(Other);
        order.addAll(Other);
        Other.clear();
        Other.addAll(order);
        order.clear();
        
        System.out.println("--------Collected symbols-------");
        for(int i=0; i<symbols.size(); i++){
            System.out.println(symbols.get(i));
        }
        System.out.println("-------Collected reserved words--------");
        for(int i=0; i<Reserved.size(); i++){
            System.out.println(Reserved.get(i));
        }
        System.out.println("-------others--------");
        for(int i=0; i<Other.size(); i++){
            System.out.println(Other.get(i));
        }
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("scanner_output.txt"));
            bw.write("---------Reserved words---------");
            bw.newLine();
            for(int i=0; i<Reserved.size(); i++){
                bw.write(Reserved.get(i));
                bw.newLine();
            }
            bw.write("---------Special symbols---------");
            bw.newLine();
            for(int i=0; i<symbols.size(); i++){
                bw.write(symbols.get(i));
                bw.newLine();
            }
            bw.write("---------Other---------");
            bw.newLine();
            for(int i=0; i<Other.size(); i++){
                bw.write(Other.get(i));
                bw.newLine();
            }
            bw.close();
        }
        catch(IOException ex){
            
        }
    }
    
}
