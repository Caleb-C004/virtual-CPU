import java.io.*;
/**Stack.java
 * This file serves to hold the CPU registers while in a sub-routine
 * This file also will print out the current contents of the stack
 * at the end of a subroutine
 */
class Stack extends Memory{
    public java.util.Stack<String> regStack;
    public String stk = "300";
    public Memory oj = new Memory();
    public Stack()
    {
        regStack = new java.util.Stack<String>();
    }
    /**PushStack
     * 
     * Pushing String i into the stack object
     */
    public void pushStack(String i)
    {
        regStack.push(i);
    }
    /**popStack
     * 
     * Returns the top of the stack object
     */
    public String popStack()
    {
        return regStack.pop();
    }
    /**pushCPU
     * Inputs: String, String, String, String
     * Pushing all 4 strings into the stack, and then pop's the stack into memory
     * 
     * OUTPUT: pc, ir, ac, and reg are put into subsequent memory locations
     */
    public void pushCPU(String pc, String ir, String ac, String reg)
    {
        pushStack(reg);
        pushStack(ac);
        pushStack(ir);
        pushStack(pc);
        mem[hexConvert(stk)] = popStack();
        incStk();
        mem[hexConvert(stk)] = popStack();
        incStk();
        mem[hexConvert(stk)] = popStack();
        incStk();
        mem[hexConvert(stk)] = popStack();
        incStk();
    }
    /**popCPUPrep
     * Loads the stack from memory to the stack object
     * It then deletes the data from memory
     * 
     * OUTPUT: all register that were in memory are now stored in the stack object
     */
    public void popCPUPrep()
    {
        decStk();
        pushStack(mem[hexConvert(stk)]);
        mem[hexConvert(stk)] = "";
        decStk();
        pushStack(mem[hexConvert(stk)]);
        mem[hexConvert(stk)] = "";
        decStk();
        pushStack(mem[hexConvert(stk)]);
        mem[hexConvert(stk)] = "";
        decStk();
        pushStack(mem[hexConvert(stk)]);
        mem[hexConvert(stk)] = "";
    }
    public void printStack()
    {
        try{
            FileWriter fw = new FileWriter(outFile, true);
            decStk();
        if(mem[hexConvert(stk)] == null || mem[oj.hexConvert(stk)].equals(""))
        {
            fw.write("No Data in Stack!"+ "\n");
            incStk();
        }
        else{
            fw.write("Stack contents at " + stk + "  = " + mem[hexConvert(stk)]+ "\n");
            decStk();
            fw.write("Stack contents at " + stk + " = " + mem[hexConvert(stk)]+ "\n");
            decStk();
            fw.write("Stack contents at " + stk + " = " + mem[hexConvert(stk)]+ "\n");
            decStk();
            fw.write("Stack contents at " + stk + " = " + mem[hexConvert(stk)]+ "\n");
            incStk();
            incStk();
            incStk();
            incStk();
        }
        fw.close();
    }
    catch(IOException e){
        e.printStackTrace();
    }

    /**Increments the stack by 1 space */
    }
    public void incStk()
    {
        int numBuf = oj.hexConvert(stk);
        numBuf++;
        stk = Integer.toHexString(numBuf);
    }
    
    /**Decrements the stack by 1 space */
    public void decStk()
    {
        int numBuf = oj.hexConvert(stk);
        numBuf--;
        stk = Integer.toHexString(numBuf);
    }
}