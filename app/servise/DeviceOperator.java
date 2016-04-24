package servise;

import utils.CRC16;

/**
 * Created by Administrator on 2016/4/19.
 */
public class DeviceOperator {

    public static void getStatus(){
//        byte[] bytes = new byte[]{4, 4, 0, 0, 0, 3, -80, 94};

        byte[] bytes = new byte[]{4,2, 0, 0, 0, 7};
        byte[] bytes2 = new byte[]{4,4, 0, 0, 0, 3};

        System.out.println("3333333333333333");
        CoilObserver.send(CRC16.addCRCChecker(bytes));
//        RegisterObserver.send(CRC16.addCRCChecker(bytes2));
    }


    /**
     * 读线圈（位变量）
     */
    public static void getCoil(){
        byte[] bytes = new byte[]{4,1, 0, 0, 0, 7};

        CoilObserver.send(CRC16.addCRCChecker(bytes));
    }

    /**
     * 读输入离散量（位变量）
     */
    public static void getDiscreteInput(){
        byte[] bytes = new byte[]{4,2, 0, 0, 0, 3};
        CoilObserver.send(CRC16.addCRCChecker(bytes));
    }

    public static void getRegister(){

    }









}
