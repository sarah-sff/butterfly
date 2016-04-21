package utils;

/**
 * Created by Administrator on 2016/4/19.
 */

import org.apache.commons.lang.StringUtils;
import play.Logger;
import play.Play;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 功能描述：Web Socket使用类
 * <p> 版权所有：优视科技 - 超能战队</p>
 * <p> 未经本公司许可，不得以任何方式复制或使用本程序任何部分 </p>
 *
 * @author <a href="mailto:qunying.sqy@alibaba-inc.com">宋群英</a>
 * @version 1.0.0
 * @create on: 2016-2-19
 */
public class WebSocketUtil {

    /**
     * TCP SOCKET连接对象
     */
    private static Socket socket;

    /**
     * 输出流对象
     */
    private static DataOutputStream dos;

    /**
     * 是否连接标识
     */
    private static boolean connected = false;

    /**
     * WEB SOCKET服务器地址
     */
    private static final String WEB_SOCKET_TCP_ADDRESS = Play.configuration.getProperty("web.socket.server.tcp.address");

    /**
     * 功能描述：初始化WEB SOCKET服务器连接
     */
    public static void init() {
        try {
            if (!StringUtils.isBlank(WEB_SOCKET_TCP_ADDRESS)) {
                String[] address = WEB_SOCKET_TCP_ADDRESS.split(":");
                if (address.length == 2) {
                    String host = address[0].trim();
                    int port = Integer.parseInt(address[1].trim());
                    socket = new Socket(host, port);
                    dos = new DataOutputStream(socket.getOutputStream());
                    connected = true;
                    Logger.info("Connected to web socket server: %s", WEB_SOCKET_TCP_ADDRESS);
                }
            }
        } catch (IOException e) {
            connected = false;
            Logger.error(e, "[WebSocketUtil.init]Web Socket服务器没启动或连接异常, 连接地址：%s", WEB_SOCKET_TCP_ADDRESS);
        }
    }

    /**
     * 功能描述：向已连接的Web Socket服务器发送消息
     *
     * @param message 消息内容
     */
    public static void send(String message) {
        try {
            if (connected && dos != null) {
                dos.writeUTF(message);
            }
        } catch (IOException e) {
            Logger.error(e, "[WebSocketUtil.send]推送消息至Web Socket服务器出错，连接地址：%s, 消息内容：%s", WEB_SOCKET_TCP_ADDRESS, message);
        }
    }

    /**
     * 功能描述：判断是否已经连接了Web Socket服务器
     *
     * @return true如果已连接
     */
    public static boolean isConnected() {
        return connected;
    }
}