package servise;

import utils.WebSocketUtil;

import java.util.Observable;

/**
 * Created by Administrator on 2016/4/20.
 */
public class RegisterObserver  extends SerialObserver {


    @Override
    public void update(Observable o, Object arg) {

        byte[] bytes = (byte[]) arg;
        System.out.println("RegisterObserver--------------------------");

        Integer totalDb = Integer.valueOf(bytes[2]);

        String a="";

        System.out.println("");
        for(byte b:bytes){
            System.out.print(b+",");
        }
        System.out.println("");

        int dataIndex=4;
        while (dataIndex<9){
            if(bytes[dataIndex]<0) {
                a+=(bytes[dataIndex]+256)+"-";
            }else{
                a+=bytes[dataIndex]+"-";
            }
            dataIndex=dataIndex+2;
        }


        System.out.println("a:"+a);

        if (WebSocketUtil.isConnected()) {
            //String val = new StringBuilder(Integer.toBinaryString(value)).reverse().toString();
            WebSocketUtil.send("V_"+a);
        }
    }
    /**
     * 往串口发送数据,实现双向通讯.
     */
    public static SerialObserver send(byte[] message) {
        SerialObserver test = new RegisterObserver();
        test.openSerialPort(message);
        return test;
    }

}
