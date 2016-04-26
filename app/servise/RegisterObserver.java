package servise;

import utils.CRC16;
import utils.WebSocketUtil;

import java.util.Observable;

/**
 * Created by Administrator on 2016/4/20.
 */
public class RegisterObserver extends SerialObserver {
    static SerialObserver serialObserver = new RegisterObserver();

    @Override
    public void update(Observable o, Object arg) {

        byte[] bytes = (byte[]) arg;
        System.out.println(" RegisterObserver updating ...... ");

        if (bytes[1] != 4) {
            return;
        }

        String a = "";

        for (int i = 0; i < bytes.length; i++) {
            System.out.printf(" " + bytes[i]);
        }

        int dataIndex = 4;
        while (dataIndex < 9) {
            if (bytes[dataIndex] < 0) {
                a += (bytes[dataIndex] + 256) + "-";
            } else {
                a += bytes[dataIndex] + "-";
            }
            dataIndex = dataIndex + 2;
        }

        if (WebSocketUtil.isConnected()) {
            //String val = new StringBuilder(Integer.toBinaryString(value)).reverse().toString();
            WebSocketUtil.send("V_" + a);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        CoilObserver.send();
    }

    /**
     * 往串口发送数据,实现双向通讯.
     */
    public static SerialObserver send(byte[] message) {
        serialObserver.openSerialPort(message);
        return serialObserver;
    }

    public static SerialObserver send() {
        System.out.println("RegisterObserver send...");
        return send(CRC16.addCRCChecker(new byte[]{4, 4, 0, 0, 0, 3}));
    }

}
