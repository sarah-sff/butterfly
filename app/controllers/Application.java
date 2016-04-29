package controllers;

import dto.DeviceData;
import play.mvc.Controller;
import servise.InstructionQueen;
import servise.SerialService;
import servise.Stabler;

import java.util.HashMap;
import java.util.Map;

public class Application extends Controller {

    public static void test(byte addr){

        DeviceData.selectedDeviceAddr = addr;

        InstructionQueen.getInstance().refreshPollingInstruction();
    }


    public static void index() {
        render();
    }


    public static void getSettings() {

        InstructionQueen.getInstance().addUrgent(new byte[]{DeviceData.selectedDeviceAddr,3, 0, 0, 0, 32});

    }


    public static void setMode(boolean autoMode,boolean manualMode){
        //自动换手动
        if(autoMode){
            InstructionQueen.getInstance().addUrgent(new byte[]{DeviceData.selectedDeviceAddr,5, 0, 1, -1, 0});
        }

        //手动换自动
        if(manualMode){
            InstructionQueen.getInstance().addUrgent(new byte[]{DeviceData.selectedDeviceAddr,5, 0, 0, -1, 0});

        }
    }

    /**
     * 巡检
     */

    public static void inspect(){
        InstructionQueen.getInstance().addUrgent(new byte[]{DeviceData.selectedDeviceAddr,5, 0, 4, -1, 0});
    }


    /**
     * 马达开关1
     */

    public static void motor1(){
        System.out.println("马达开关1");

        InstructionQueen.getInstance().addUrgent(new byte[]{DeviceData.selectedDeviceAddr,5, 0, 2, -1, 0});
    }

    /**
     * 马达开关2
     */

    public static void motor2(){
        System.out.println("马达开关1");
        InstructionQueen.getInstance().addUrgent(new byte[]{DeviceData.selectedDeviceAddr,5, 0, 3, -1, 0});
    }


    public static void saveSettings(byte index, String key,byte value){

        //防止地址被篡改
        if(index == 1) return;

        InstructionQueen.getInstance().addUrgent(new byte[]{DeviceData.selectedDeviceAddr,6, 0, index, 0, value});
    }

    /**
     * 选择地址
     * @param address
     */
    public static void setAddress(byte address){

        System.out.println(address);
        DeviceData.selectedDeviceAddr = address;

        //清除Stabler队列记录的所有已发送指令
        Stabler.getInstance().clear();
        SerialService.sendout();
    }

    /**
     * 获取当前地址
     */
    public static void getAddress(){


        Map<String,Byte> result= new HashMap<String, Byte>();
        result.put("address",DeviceData.selectedDeviceAddr);

        renderJSON(result);
    }


}