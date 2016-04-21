package servise;

import utils.WebSocketUtil;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Administrator on 2016/4/19.
 */
public class CoilObserver extends SerialObserver implements Observer {

    @Override
    public void update(Observable o, Object arg) {

        System.out.println("CoilObserver----------------");

        byte[] bytes = (byte[]) arg;

        Integer value = Integer.valueOf(bytes[3]);

        if (WebSocketUtil.isConnected()) {
            String val = new StringBuilder(Integer.toBinaryString(value)).reverse().toString();
            WebSocketUtil.send("L_"+val);
        }
    }

    /**
     * 往串口发送数据,实现双向通讯.
     */
    public static SerialObserver send(byte[] message) {
        SerialObserver test = new CoilObserver();
        test.openSerialPort(message);
        return test;
    }

}
