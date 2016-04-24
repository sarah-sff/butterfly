package servise;

import utils.CRC16;

import java.util.Observable;

/**
 * Created by Administrator on 2016/4/22.
 */
public class OperationObserver extends  SerialObserver {


    @Override
    public void update(Observable o, Object arg) {
        byte[] bytes = (byte[]) arg;
        System.out.println("RegisterObserver--------------------------");

        System.out.println("");
        for(byte b:bytes){
            System.out.print(b+",");
        }
        System.out.println("");
    }
    /**
     * 往串口发送数据,实现双向通讯.
     */
    public static OperationObserver send(byte[] message) {
        OperationObserver test = new OperationObserver();
        test.openSerialPort(message);
        return test;
    }

    /**
     * 自动/手动开关
     */
    public static void chooseAutomaticMode(){
        byte[] instruction = new byte[]{4,5, 0, 0, -1,0};
        RegisterObserver.send(CRC16.addCRCChecker(instruction));
    }

    /**
     * 自动/手动开关
     */
    public static void chooseManualMode(){
        byte[] instruction = new byte[]{4,5, 0, 1, -1,0};
        RegisterObserver.send(CRC16.addCRCChecker(instruction));
    }

    /**
     * 巡检
     */
    public static void inspect(){
        byte[] instruction = new byte[]{4,5, 0, 4, -1,0};
        RegisterObserver.send(CRC16.addCRCChecker(instruction));
    }

    /**
     * 启动设备1
     */
    public static void chooseDevice1(){
        byte[] instruction = new byte[]{4,5, 0, 2, -1,0};
        RegisterObserver.send(CRC16.addCRCChecker(instruction));
    }

    /**
     * 启动设备2
     */
    public static void chooseDevice2(){
        byte[] instruction = new byte[]{4,5, 0, 3, -1,0};
        RegisterObserver.send(CRC16.addCRCChecker(instruction));
    }


    /**
     * 设置信息
     */
    public static void getSettings(){
        byte[] instruction = new byte[]{4,3, 0, 0, 0,32};
        RegisterObserver.send(CRC16.addCRCChecker(instruction));
    }
}
