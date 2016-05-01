package controllers;

import dto.DeviceData;
import exception.IllegalDeviceAddrException;
import play.Logger;
import play.mvc.Controller;
import servise.InstructionQueen;
import servise.SerialService;
import servise.Stabler;

import java.util.HashMap;
import java.util.Map;

public class Application extends Controller {

    public static void test(byte addr) {

        try {
            DeviceData.setSelectedDeviceAddr(addr);
        } catch (IllegalDeviceAddrException e) {
            Logger.error(e.getMessage());
        }

        InstructionQueen.getInstance().refreshPollingInstruction();
    }


    /**
     * 首页
     */
    public static void index() {
        render();
    }


    /**
     * 查询配置
     */
    public static void getSettings() {

        InstructionQueen.getInstance().addUrgent(new byte[]{DeviceData.getSelectedDeviceAddr(), 3, 0, 0, 0, 32});

    }


    /**
     * 设置模式
     *
     * @param autoMode   自动
     * @param manualMode 手动
     */
    public static void setMode(boolean autoMode, boolean manualMode) {
        //自动换手动
        if (autoMode) {
            InstructionQueen.getInstance().addUrgent(new byte[]{DeviceData.getSelectedDeviceAddr(), 5, 0, 1, -1, 0});
        }

        //手动换自动
        if (manualMode) {
            InstructionQueen.getInstance().addUrgent(new byte[]{DeviceData.getSelectedDeviceAddr(), 5, 0, 0, -1, 0});

        }
    }

    /**
     * 巡检
     */

    public static void inspect() {
        InstructionQueen.getInstance().addUrgent(new byte[]{DeviceData.getSelectedDeviceAddr(), 5, 0, 4, -1, 0});
    }


    /**
     * 马达开关1
     */
    public static void motor1() {
        Logger.info("马达开关1");

        InstructionQueen.getInstance().addUrgent(new byte[]{DeviceData.getSelectedDeviceAddr(), 5, 0, 2, -1, 0});
    }

    /**
     * 马达开关2
     */
    public static void motor2() {
        Logger.info("马达开关1");
        InstructionQueen.getInstance().addUrgent(new byte[]{DeviceData.getSelectedDeviceAddr(), 5, 0, 3, -1, 0});
    }


    /**
     * 保存设置
     *
     * @param index
     * @param key
     * @param value
     */
    public static void saveSettings(byte index, String key, byte value) {

        //防止地址被篡改
        if (index == 1) return;

        InstructionQueen.getInstance().addUrgent(new byte[]{DeviceData.getSelectedDeviceAddr(), 6, 0, index, 0, value});
    }

    /**
     * 选择地址
     *
     * @param address
     */
    public static void setAddress(byte address) {

        Logger.info(Integer.valueOf(address) + "");

        try {
            DeviceData.setSelectedDeviceAddr(address);
        } catch (IllegalDeviceAddrException e) {
            Logger.error(e.getMessage());
        }

        //清除Stabler队列记录的所有已发送指令
        Stabler.getInstance().clear();
        SerialService.sendout();
    }

    /**
     * 获取当前地址
     */
    public static void getAddress() {

        Map<String, Byte> result = new HashMap<String, Byte>();
        result.put("address", DeviceData.getSelectedDeviceAddr());

        renderJSON(result);
    }


}