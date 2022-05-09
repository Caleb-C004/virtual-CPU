import java.io.*;
/**CPU.java
 * This file holds all the operations and readings for the CPU
 * Depending on the Op Code read from memory, the CPU will either perform
 * an arithmetic operation, retrive from memory, load to memory, or 
 * push registers to stack and jump to a sub-routine
 */
class CPU extends Memory{
    private String PC;
    private String opCode;
    private String address;
    private String AC;
    private String IR;
    private String memLocation;
    private String register;
    private Integer num;
    private Integer subRoutine;
    private Integer instructions;
    private Integer subNest;
    FileWriter fw;
    Stack oj = new Stack();
    /**CPU initializes all variables*/
    public CPU()
    {
        address ="";
        AC = "";
        opCode = "";
        PC = "";
        IR = "";
        memLocation = "";
        register = "";
        num = 0;
        subRoutine = 0;
        instructions = 0;
        subNest = 0;
        fileClear();
    }
    /**cpuRun(String i)
     * input: Starting memory location "400"
     * runs from memory on a while loop
     * reads Opcode to determine correct step in switch statment
     */
    public void cpuRun(String i)
    {
        setPC(i);
        Memory obj = new Memory();
        memLocation = i;
        IR = mem[obj.hexConvert(memLocation)];
        opCode = IR.substring(0,1);
        //This reads the Op Code and determines what the CPU will do next
        while(!opCode.equals("D"))
        {
            instructions++;
            switch(opCode){ 
            case "1"://load AC from memory
             address = IR.substring(1,4);
             setAC(mem[obj.hexConvert(address)]);
             break;
            case "2"://Store AC to memory
                address = IR.substring(1,4);
                 mem[obj.hexConvert(address)] = AC; 
                 break;
            case "3"://Load AC from REG
                setAC(register);
                break;
            case "4"://Store AC to Reg
                setReg(AC);
                break;
            case "5"://Add to AC from memory
                address = IR.substring(1,4);
                AC = Integer.toString(Integer.parseInt(AC) + Integer.parseInt(mem[obj.hexConvert(address)]));
                break;
            case "6"://Load REG with operand
                setReg(Integer.toString(obj.hexConvert(IR.substring(1,4)))); 
                break;
            case "7"://Add Reg to AC
                addNum();
                break;
            case "8"://Multiply REG to AC
                multNum();
                break;
            case "9"://Subtract REG from AC
                subtNum();
                break;
            case "A"://Divide AC by REG value
                divNum();
                break;
            case "B"://Jump to subroutine starting at the address
                oj.pushCPU(PC,IR, AC, register);
                PC = IR.substring(1,4);
                subRoutine++;
                subNest++; //subNest == 1 in subroutine, 2 in nested subroutine, 3 in nested nested subroutine, etc.
                break;
            case "C"://Return from subroutine
                printReg();
                oj.popCPUPrep();
                PC = oj.popStack();
                IR = oj.popStack();
                AC = oj.popStack();
                register = oj.popStack();
                subNest--;
                break;
            case "F": 
                printReg();
                System.exit(0);
                break;
            default: break;
            }
            if(!opCode.equals("B")) //if program jumps to subroutine do NOT increment PC
            {
            num = hexConvert(PC);
            num++;
            PC = Integer.toHexString(num);
            }
            memLocation = PC;
            IR = mem[obj.hexConvert(memLocation)];
            opCode = IR.substring(0,1);
        }

    }
    /** setPC
     * sets the value of PC to the input String i
    */
    public void setPC(String i){
        PC = i;
    }

    /** getPC
     * returns PC after ensuring uppercase
    */
    public String getPC() {
        return PC.toUpperCase();
    }

    /** getIR
     * returns IR
    */
    public String getIR() {
        return IR;
    }

    /** getAC
     * returns the value of AC after shortening leading 0's, converting to hex string, and ensuring Uppercase
    */
    public String getAC(){
        return (Integer.toHexString(Integer.parseInt(AC)).toUpperCase());
    }

    /** setAC
     * sets the value of AC to the input String a
    */
    public void setAC(String a){
        AC = a;
    }

    /** getOp
     * returns opCode
    */
    public String getOp(){
        return opCode;
    }

    /** setOp
     * set opCode to the input String o
    */
    public void setOp(String o){
        opCode = o;
    }

    /** getReg
     * returns the register after shortening leading 0's, converting to hex string, and ensuring Uppercase
    */
    public String getReg(){
        return (Integer.toHexString(Integer.parseInt(register)).toUpperCase());
    }

    /** setReg
     * Sets the value of register to the input String r
    */
    public void setReg(String r){
        register = r;
    }

    /** addNum
     * Adds register to AC and returns the output to AC
    */
    public void addNum(){
        AC = Integer.toString(Integer.parseInt(AC) + Integer.parseInt(register));
    }

    /** multNum
     * Multiplies AC by register and returns the output to AC
    */
    public void multNum(){
        AC = Integer.toString(Integer.parseInt(AC) * Integer.parseInt(register));
    }

    /** subtNum
     * Subtracts register from AC and returns the output to AC
    */
    public void subtNum(){
        AC = Integer.toString(Integer.parseInt(AC) - Integer.parseInt(register));
    }

    /**divNum
     * Divides the AC by register and returns the output to AC
    */
    public void divNum(){
        AC = Integer.toString(Integer.parseInt(AC) / Integer.parseInt(register));
    }

    /**printReg
     * Prints all the registers and memory locations before exiting a subroutine
     */
    public void printReg()
    {
        try{

            fw = new FileWriter(outFile, true);
            if(opCode.equals("C"))
            {
                if(subNest.equals(subRoutine))
                    fw.write("======Before Return from Subroutine " + subRoutine + " Status=======" + "\n");
                else if(subNest < subRoutine){
                    fw.write("======Before Return from Subroutine " + subNest + " Status=======" + "\n");
                }
                
            }
            else if(opCode.equals("F"))
                fw.write("===========End of Program Status=========="+ "\n");
            
            fw.write("==================Stack Status =================================="+ "\n");
            fw.close();
            oj.printStack();
            fw = new FileWriter(outFile, true);
            fw.write("==================Registers & Memory Status======================"+ "\n");
            fw.write("AC = " + getAC()+ "\n");
            fw.write("Register = " + getReg()+ "\n");
            fw.write("PC = " + getPC()+ "\n");
            fw.write("IR = " + getIR()+ "\n");
            fw.write("Memory 940 = " + (Integer.toHexString(Integer.parseInt(mem[hexConvert("940")]))).toUpperCase()+ "\n");
            fw.write("Memory 941 = " + (Integer.toHexString(Integer.parseInt(mem[hexConvert("941")]))).toUpperCase()+ "\n");
            fw.write("Memory 942 = " + (Integer.toHexString(Integer.parseInt(mem[hexConvert("942")]))).toUpperCase()+ "\n");
            fw.write("Number of instruction executed = " + instructions+ "\n");
            fw.close();
        }

        catch(IOException e){
            e.printStackTrace();
        }
    }
}