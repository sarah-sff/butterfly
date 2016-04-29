package servise;

import dto.Settings;
import gnu.io.SerialPort;
import org.apache.commons.lang.StringUtils;
import utils.ByteUtil;
import utils.CRC16;
import utils.SerialReader;
import utils.WebSocketUtil;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/4/21.
 */
public class SerialService {

    static SerialReader sr = new SerialReader();


    /**
     * 往串口发送数据,实现双向通讯.
     */
    private static void send(byte[] message) {
        SerialService observer = new SerialService();
        observer.openSerialPort(CRC16.addCRCChecker(message));

        //记录发送时间
        Stabler.getInstance().sendMsg(message);
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

    /**
     * 开始向串口发送短信
     */
    public static void sendout(){
        InstructionQueen.getInstance().refreshPollingInstruction();
        //开始发送指令
        SerialService.sendNextInstruction();
    }

    /**
     * 发送下一条指令
     */
    public static void sendNextInstruction() {

        //指令队列为空，不发送
        if(InstructionQueen.getInstance().size()<=0){
            return;
        }
        System.out.println("继续发送");
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }
        byte[] nextInstruction = (byte[]) InstructionQueen.getInstance().take();
        SerialService.send(nextInstruction);
    }

    /**
     * 解析返回结果
     *
     * @param message
     */
    public static void doAnalyse(byte[] message) {

        if (message.length < 3) return;

        byte actionCode = message[1];

        String socketStr = "";

        if (actionCode == 2) {
            socketStr = ledDecode(message);
        } else if (actionCode == 4) {
            socketStr = numDecode(message);
        }else if (actionCode == 3){
            socketStr = getSettings(message);
        }


        if (WebSocketUtil.isConnected() && !StringUtils.isBlank(socketStr)) {
            System.out.println("即将向socket发送消息 ----> "+socketStr);
            WebSocketUtil.send(socketStr);
        }


    }


    /**
     * @param bytes
     * @return
     */
    private static String ledDecode(byte[] bytes) {
        String binaryStr= Integer.toBinaryString(bytes[3]);

        int len = binaryStr.length();

        int i=0;
        while (i<7-len){
            binaryStr = "0" +binaryStr ;
            i++;
        }

        return "L_" + binaryStr;
    }

    /**
     * 电流电压解析
     * @return
     */
    private static String numDecode(byte[] bytes) {

        String r = "V_";
        //电压值
        int voltage = ByteUtil.getIntWith2Byte(bytes[3], bytes[4]);
        //电流1
        int current1 = ByteUtil.getIntWith2Byte(bytes[5], bytes[6]);
        //电流2
        int current2 = ByteUtil.getIntWith2Byte(bytes[7], bytes[8]);

        return r + voltage + "-" + current1 + "-" + current2;
    }


    /**
     * 获取设置列表信息
     * @param bytes
     * @return
     */
    public static String getSettings(byte[] bytes){
        return "S_"+(new Settings(bytes).toString());
    }



}

