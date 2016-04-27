package controllers;

import play.mvc.Controller;
import servise.InstructionQueen;
import servise.SerialService;

public class Application extends Controller {

    public static void test(){
        int size1= InstructionQueen.getInstance().commonQueen.size();

        int size2 = InstructionQueen.getInstance().urgentQueen.size();

        SerialService.sendNextInstruction();

        renderText(" commonQueen size : "+size1+" \n  urgentQueen size :"+size2);
    }


    public static void index() {
        render();
    }


    public static void getSettings() {

        InstructionQueen.getInstance().addUrgent(new byte[]{4,3, 0, 0, 0, 32});
//        OperationObserver.getSettings();
    }


    public static void setMode(boolean autoMode,boolean manualMode){
        //自动换手动
        if(autoMode){
            InstructionQueen.getInstance().addUrgent(new byte[]{4,5, 0, 1, -1, 0});
        }

        //手动换自动
        if(manualMode){
            InstructionQueen.getInstance().addUrgent(new byte[]{4,5, 0, 0, -1, 0});

        }
        //InstructionQueen.getInstance().addUrgent(new byte[]{4,5, 0, 0, -1, 0});
    }

    /**
     * 巡检
     */

    public static void inspect(){
        System.out.println("巡检");
        InstructionQueen.getInstance().addUrgent(new byte[]{4,5, 0, 4, -1, 0});

    }


    /**
     * 马达开关1
     */

    public static void motor1(){
        System.out.println("马达开关1");

        InstructionQueen.getInstance().addUrgent(new byte[]{4,5, 0, 2, -1, 0});
    }

    /**
     * 马达开关2
     */

    public static void motor2(){
        System.out.println("马达开关1");
        InstructionQueen.getInstance().addUrgent(new byte[]{4,5, 0, 3, -1, 0});
    }
}