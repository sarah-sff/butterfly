package utils;

/**
 * Created by Administrator on 2016/4/26.
 */
public class ByteUtil {

    /**
     * 将两个字节的byte转成int类型
     *
     * @param high
     * @param low
     * @return
     */
    public static int getIntWith2Byte(byte high, byte low) {


        return high * 256 + low;
    }

    /**
     * 将连个字节的byte转成二进制字符串
     *
     * @param bytes
     * @return
     */
    public static String getBinaryStringWith2Byte(byte[] bytes) {

        if (bytes.length < 2) return "";

        if (bytes[0] == 0) {
            return Integer.toBinaryString(bytes[1]);
        }

        return Integer.toBinaryString(bytes[0]) + Integer.toBinaryString(bytes[1]);

    }

}
