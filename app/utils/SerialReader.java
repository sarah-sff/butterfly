package utils;

import gnu.io.*;
import servise.SerialService;
import servise.Stabler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TooManyListenersException;

public class SerialReader  implements Runnable, SerialPortEventListener {
    static CommPortIdentifier portId;
    int delayRead = 100;
    int numBytes; // buffer中的实际数据字节数
    private static byte[] readBuffer = new byte[1024]; // 4k的buffer空间,缓存串口读入的数据
    static Enumeration portList;
    InputStream inputStream;
    OutputStream outputStream;
    static SerialPort serialPort;
    HashMap serialParams;
    Thread readThread;// 本来是static类型的
    // 端口是否打开了
    boolean isOpen = false;
    // 端口读入数据事件触发后,等待n毫秒后再读取,以便让数据一次性读完
    public static final String PARAMS_DELAY = "delay read"; // 延时等待端口数据准备的时间
    public static final String PARAMS_TIMEOUT = "timeout"; // 超时时间
    public static final String PARAMS_PORT = "port name"; // 端口名称
    public static final String PARAMS_DATABITS = "data bits"; // 数据位
    public static final String PARAMS_STOPBITS = "stop bits"; // 停止位
    public static final String PARAMS_PARITY = "parity"; // 奇偶校验
    public static final String PARAMS_RATE = "rate"; // 波特率

    public boolean isOpen() {
        return isOpen;
    }

    /**
     * 初始化端口操作的参数.
     *
     * @see
     */
    public SerialReader() {
        isOpen = false;
    }

    public void open(HashMap params) {
        serialParams = params;
        if (isOpen) {
//            close();
            // 重复利用端口，陈玩杰  todo 验证可靠性
            serialParams.clear();
            Thread readThread = new Thread(this);
            readThread.start();
            return;
        }
        try {
            // 参数初始化
            int timeout = Integer.parseInt(serialParams.get(PARAMS_TIMEOUT).toString());
            int rate = Integer.parseInt(serialParams.get(PARAMS_RATE).toString());
            int dataBits = Integer.parseInt(serialParams.get(PARAMS_DATABITS).toString());
            int stopBits = Integer.parseInt(serialParams.get(PARAMS_STOPBITS).toString());
            int parity = Integer.parseInt(serialParams.get(PARAMS_PARITY).toString());
            delayRead = Integer.parseInt(serialParams.get(PARAMS_DELAY).toString());
            String port = serialParams.get(PARAMS_PORT).toString();
            // 打开端口
            portId = CommPortIdentifier.getPortIdentifier(port);

            if (portId.isCurrentlyOwned()) {
                System.out.println("Error: Port is currently in use");
                System.out.println(portId.getCurrentOwner());
            } else {
                serialPort = (SerialPort) portId.open(getPortTypeName(CommPortIdentifier.PORT_SERIAL), timeout);
                inputStream = serialPort.getInputStream();
                serialPort.addEventListener(this);
                serialPort.notifyOnDataAvailable(true);
                serialPort.setSerialPortParams(rate, dataBits, stopBits, parity);

                isOpen = true;
            }
        } catch (PortInUseException e) {
            e.printStackTrace();
            System.out.println("端口" + serialParams.get(PARAMS_PORT).toString() + "已经被占用");
            // 端口"+serialParams.get( PARAMS_PORT ).toString()+"已经被占用";
        } catch (TooManyListenersException e) {
            System.out.println("端口" + serialParams.get(PARAMS_PORT).toString() + "监听者过多");
            // "端口"+serialParams.get( PARAMS_PORT ).toString()+"监听者过多";
        } catch (UnsupportedCommOperationException e) {
            System.out.println("端口操作命令不支持");
            // "端口操作命令不支持";
        } catch (NoSuchPortException e) {
            System.out.println("端口" + serialParams.get(PARAMS_PORT).toString() + "不存在");
            // "端口"+serialParams.get( PARAMS_PORT ).toString()+"不存在";
        } catch (IOException e) {
            System.out.println("打开端口" + serialParams.get(PARAMS_PORT).toString() + "失败");
            // "打开端口"+serialParams.get( PARAMS_PORT ).toString()+"失败";
        }
        serialParams.clear();
        Thread readThread = new Thread(this);
        readThread.start();
    }

    public void run() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
        }
    }

    /**
     * 初始化
     */
    public void start() {
        try {
            outputStream = serialPort.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            readThread = new Thread(this);
//            readThread.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 发送数据
     *
     * @param bytes
     */
    public void run(byte[] bytes) {
        try {
            Thread.sleep(4);
        } catch (InterruptedException e) {
        }
        try {
            String data = "";
            for (byte b : bytes) {
                data += Integer.valueOf(b);
            }
            System.out.println("指令发送中......，数据----->" + data);
            outputStream.write(bytes); // 往串口发送数据，是双向通讯的。
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (isOpen) {
            try {
                System.out.println("串口即将关闭！");
                serialPort.notifyOnDataAvailable(false);
                serialPort.removeEventListener();
                inputStream.close();
                serialPort.close();
                isOpen = false;
            } catch (IOException ex) {
                // "关闭串口失败";
            }
        }
    }

    public void serialEvent(SerialPortEvent event) {

        try {
            Thread.sleep(delayRead);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        switch (event.getEventType()) {
            case SerialPortEvent.BI: // 10
            case SerialPortEvent.OE: // 7
            case SerialPortEvent.FE: // 9
            case SerialPortEvent.PE: // 8
            case SerialPortEvent.CD: // 6
            case SerialPortEvent.CTS: // 3
            case SerialPortEvent.DSR: // 4
            case SerialPortEvent.RI: // 5
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2
                break;
            case SerialPortEvent.DATA_AVAILABLE: // 1
                try {

                    // 多次读取,将所有数据读入
                    while (inputStream.available() > 0) {
                        numBytes = inputStream.read(readBuffer);
                    }

                    changeMessage(readBuffer, numBytes);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    // 通过observer pattern将收到的数据发送给observer
    // 将buffer中的空字节删除后再发送更新消息,通知观察者
    public void changeMessage(byte[] message, int length) {

        for (int i = 0; i < length; i++) {
            System.out.printf(" " + message[i]);
        }
        System.out.println();

        byte[] temp = new byte[length];
        System.arraycopy(message, 0, temp, 0, length);

        SerialService.doAnalyse(temp);

        Stabler.getInstance().recMsg();

        SerialService.sendNextInstruction();

    }

    static String getPortTypeName(int portType) {
        switch (portType) {
            case CommPortIdentifier.PORT_I2C:
                return "I2C";
            case CommPortIdentifier.PORT_PARALLEL:
                return "Parallel";
            case CommPortIdentifier.PORT_RAW:
                return "Raw";
            case CommPortIdentifier.PORT_RS485:
                return "RS485";
            case CommPortIdentifier.PORT_SERIAL:
                return "Serial";
            default:
                return "unknown type";
        }
    }

    public HashSet<CommPortIdentifier> getAvailableSerialPorts() {

        HashSet<CommPortIdentifier> h = new HashSet<CommPortIdentifier>();
        Enumeration thePorts = CommPortIdentifier.getPortIdentifiers();
        while (thePorts.hasMoreElements())

        {
            CommPortIdentifier com = (CommPortIdentifier) thePorts.nextElement();
            switch (com.getPortType()) {
                case CommPortIdentifier.PORT_SERIAL:
                    try {
                        CommPort thePort = com.open("CommUtil", 50);
                        thePort.close();
                        h.add(com);
                    } catch (PortInUseException e) {
                        System.out.println("Port, " + com.getName() + ", is in use.");
                    } catch (Exception e) {
                        System.out.println("Failed to open port " + com.getName() + e);
                    }
            }
        }

        return h;
    }
}
