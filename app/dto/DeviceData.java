package dto;


import exception.IllegalDeviceAddrException;

/**
 * Created by Administrator on 2016/4/21.
 */
public class DeviceData {
    /**
     * 设备地址,表示当前操作设备
     */
    private static byte selectedDeviceAddr = 1;

    /**
     * 读取当前操作设备地址
     *
     * @return
     */
    public synchronized static byte getSelectedDeviceAddr() {

        if (selectedDeviceAddr < 0) {
            throw new RuntimeException("设备地址错误");
        }

        return selectedDeviceAddr;
    }

    /**
     * 设置当前操作设备地址
     *
     * @param selectedDeviceAddr
     */
    public synchronized static void setSelectedDeviceAddr(byte selectedDeviceAddr) throws IllegalDeviceAddrException {

        if (selectedDeviceAddr < 0) {
            throw new IllegalDeviceAddrException("设备地址错误");
        }

        DeviceData.selectedDeviceAddr = selectedDeviceAddr;
    }

}


