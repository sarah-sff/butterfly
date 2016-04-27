package servise;

import gnu.io.SerialPort;
import utils.CRC16;
import utils.SerialReader;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/4/21.
 */
public  class SerialService {

    static SerialReader sr = new SerialReader();


    /**
     * 往串口发送数据,实现双向通讯.
     */
    public static void send(byte[] message) {
        SerialService observer = new SerialService();
        observer.openSerialPort(CRC16.addCRCChecker(message));
    }

    /**
     * 打开串口
     */
    public void openSerialPort(byte[] message) {
        HashMap<String, Comparable> params = new HashMap<String, Comparable>();
        String port = "COM3";
        String rate = "9600";
        String dataBit = "" + SerialPort.DATABITS_8;
        String stopBit = "" + SerialPort.STOPBITS_1;
        String parity = "" + SerialPort.PARITY_NONE;
        int parityInt = SerialPort.PARITY_NONE;
        params.put(SerialReader.PARAMS_PORT, port); // 端口名称
        params.put(SerialReader.PARAMS_RATE, rate); // 波特率
        params.put(SerialReader.PARAMS_DATABITS, dataBit); // 数据位
        params.put(SerialReader.PARAMS_STOPBITS, stopBit); // 停止位
        params.put(SerialReader.PARAMS_PARITY, parityInt); // 无奇偶校验
        params.put(SerialReader.PARAMS_TIMEOUT, 100); // 设备超时时间 1秒
        params.put(SerialReader.PARAMS_DELAY, 100); // 端口数据准备时间 1秒
        try {
            sr.open(params);

            if (message != null && message.length != 0) {
                sr.start();
                sr.run(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

