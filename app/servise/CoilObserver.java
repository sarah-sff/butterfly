package servise;

import utils.CRC16;
import utils.WebSocketUtil;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Administrator on 2016/4/19.
 */
public class CoilObserver extends SerialObserver implements Observer {

    static SerialObserver serialObserver = new CoilObserver();

    @Override
    public void update(Observable o, Object arg) {

        System.out.println(" CoilObserver updating ...... ");

        byte[] bytes = (byte[]) arg;
        Integer value = Integer.valueOf(bytes[3]);

        if (WebSocketUtil.isConnected()) {
            String val = new StringBuilder(Integer.toBinaryString(value)).reverse().toString();
            WebSocketUtil.send("L_" + val);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        RegisterObserver.send();
    }

    /**
     * 往串口发送数据,实现双向通讯.
     */
    public static SerialObserver send(byte[] message) {
        serialObserver.openSerialPort(message);
        return serialObserver;
    }

    public static SerialObserver send() {
        System.out.println("CoilObserver send...");
        return send(CRC16.addCRCChecker(new byte[]{4, 2, 0, 0, 0, 7}));
//        return send(CRC16.addCRCChecker(new byte[]{4, 3, 0, 0, 0, 32}));
    }

}
