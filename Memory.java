import java.io.*;
import java.util.*;
/**Memory.java
 * This file holds the main method of the program, and serves to 
 * populate the array with the code form input.txt
 * And output.txt file is also created for the stack states from every
 * subroutine and end of program
 */
public class Memory {

    public static String[] mem;
    File outFile = new File("output.txt");
    Scanner of = new Scanner("output.txt");
    public static void main(String[] args)
    {
        Memory obj = new Memory();
        CPU j = new CPU();
        String fLine;
        int semiIndex = 0; //used to find the semicolon in a delimited string
        String memLoc; //used to store memory location
        int hexToDec;
        String temp;
        Scanner inp = new Scanner(System.in);
        System.out.println("Input name of input file: ");
        temp = inp.nextLine();
        mem = new String[4096];
        String[] sep = new String[10];
        File inF = new File(temp);

        try{
        Scanner in = new Scanner(inF);

        while(in.hasNextLine())//reading each line of the file and storing OpCode and Address
        {

            fLine = in.nextLine();
            if(fLine.contains("=="))
                continue;
            if(fLine.contains(";")) //Differentiate the line of code from blank lines
            {
                sep = fLine.split(" ");
                for(int i = 0; i< sep.length;i++)
                {
                    if(sep[i].contains(";")){
                        semiIndex = i;
                        break;
                    }
                }
                memLoc = sep[semiIndex - 2];
                hexToDec = obj.hexConvert(memLoc);//hex String to integer
                mem[hexToDec] = sep[semiIndex - 1];
            }
        }

        j.cpuRun("400");
        in.close();
    }
    catch(FileNotFoundException e){
        System.out.println("File Not Found");
    }
    
    }

    //This method converts a hex string into decimal form
    public Integer hexConvert(String i){
        String hex;
        int k;
        hex = "0x" + i;
        k = Integer.decode(hex);
        return k;
    }

    /**Method aided by Stack Overflow Ankur Anand 
     * https://stackoverflow.com/questions/29878237/java-how-to-clear-a-text-file-without-deleting-it*/
    public void fileClear(){
        try{
            FileWriter fw = new FileWriter(outFile, false);
            PrintWriter pw = new PrintWriter(fw, false);
            pw.flush();
            pw.close();
            fw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}

