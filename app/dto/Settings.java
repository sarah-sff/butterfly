package dto;

import utils.ByteUtil;

/**
 * Created by Administrator on 2016/4/26.
 */
public class Settings {
    //密码
    String password;

    //rs485地址
    int rs485address;

    //换泵选择
    int choosePump;

    //保留字段1
    int reserved1;

    //保留字段2
    int reserved2;

    //屏蔽启动时间
    int restartTime;

    //保护选择
    int protectMode;

    //电机功率选择
    int motorPower;

    //过流保护值
    int overcurrentProtectVal;

    //过流保护延时
    int overcurrentProtectTime;

    //欠流保护值
    int undercurrentProtectVal;

    //欠流保护延时
    int undercurrentProtectTime;

    //电压选择
    int voltage;

    //过压保护值
    int overcoltageProtectVal;

    //过压保护延时
    int overcoltageProtectTime;

    //欠压保护值
    int undervoltageProtectVal;

    //欠压保护延时
    int undervoltageProtectTime;

    //巡检周期
    int inspectPeriod;

    //巡检时间
    int inspectTime;

    //切换延时
    int switchDelay;

    //电压校准
    int voltageCalibration;

    //电流1校准
    int current1Calibration;

    //电流2校准
    int current2Calibration;

    //led亮度调整
    int ledBrightness;

    //电流校准方式
    int currentBalibrationMode;

    //软件版本
    String version;

    public Settings(byte[] modbusBytes) {
        //4,3,64,0,0,0,4,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,30,0,0,0,30,0,1,1,124,0,10,1,124,0,10,0,6,0,10,0,5,1,-72,2,60,2,60,0,1,0,0,0,13,0,0,0,0,0,0,0,0,0,0,0,0,11,-12,
        if (modbusBytes.length < 3) {
            return;
        }
        int dataDb = modbusBytes[2];

        //密码
        this.password = ""+ByteUtil.getIntWith2Byte(modbusBytes[3], modbusBytes[4]);


        this.rs485address = ByteUtil.getIntWith2Byte(modbusBytes[5], modbusBytes[6]);

        //换泵选择
        int choosePump = ByteUtil.getIntWith2Byte(modbusBytes[7], modbusBytes[8]);

        //保留字段1
        int reserved1;

        //保留字段2
        int reserved2;

        //屏蔽启动时间
        int restartTime = ByteUtil.getIntWith2Byte(modbusBytes[13], modbusBytes[14]);


        //保护选择
        int protectMode = ByteUtil.getIntWith2Byte(modbusBytes[15], modbusBytes[16]);

        //电机功率选择
        int motorPower = ByteUtil.getIntWith2Byte(modbusBytes[17], modbusBytes[18]);


        //过流保护值
        int overcurrentProtectVal = ByteUtil.getIntWith2Byte(modbusBytes[19], modbusBytes[20]);


        //过流保护延时
        int overcurrentProtectTime = ByteUtil.getIntWith2Byte(modbusBytes[21], modbusBytes[22]);


        //欠流保护值
        int undercurrentProtectVal = ByteUtil.getIntWith2Byte(modbusBytes[23], modbusBytes[24]);


        //欠流保护延时
        int undercurrentProtectTime = ByteUtil.getIntWith2Byte(modbusBytes[25], modbusBytes[26]);

        //电压选择
        int voltage = ByteUtil.getIntWith2Byte(modbusBytes[27], modbusBytes[28]);

        //过压保护值
        int overcoltageProtectVal = ByteUtil.getIntWith2Byte(modbusBytes[29], modbusBytes[30]);


        //过压保护延时
        int overcoltageProtectTime = ByteUtil.getIntWith2Byte(modbusBytes[31], modbusBytes[32]);


        //欠压保护值
        int undervoltageProtectVal = ByteUtil.getIntWith2Byte(modbusBytes[33], modbusBytes[34]);


        //欠压保护延时
        int undervoltageProtectTime = ByteUtil.getIntWith2Byte(modbusBytes[35], modbusBytes[36]);


        //巡检周期
        int inspectPeriod = ByteUtil.getIntWith2Byte(modbusBytes[37], modbusBytes[38]);


        //巡检时间
        int inspectTime = ByteUtil.getIntWith2Byte(modbusBytes[39], modbusBytes[40]);


        //切换延时
        int switchDelay = ByteUtil.getIntWith2Byte(modbusBytes[41], modbusBytes[42]);


        //电压校准
        int voltageCalibration = ByteUtil.getIntWith2Byte(modbusBytes[43], modbusBytes[44]);


        //电流1校准
        int current1Calibration = ByteUtil.getIntWith2Byte(modbusBytes[45], modbusBytes[46]);


        //电流2校准
        int current2Calibration = ByteUtil.getIntWith2Byte(modbusBytes[47], modbusBytes[48]);


        //led亮度调整
        int ledBrightness = ByteUtil.getIntWith2Byte(modbusBytes[49], modbusBytes[50]);


        //电流校准方式
        int currentBalibrationMode = ByteUtil.getIntWith2Byte(modbusBytes[51], modbusBytes[52]);


        //软件版本
        String version = ByteUtil.getIntWith2Byte(modbusBytes[53], modbusBytes[54]) + "";
    }


    public String toString() {
        return "{\"password\":"+password
                +",\"rs485address\":"+rs485address
                +",\"choosePump\":"+choosePump
                +",\"reserved1\":"+reserved1
                +",\"reserved2\":"+reserved2
                +",\"restartTime\":"+restartTime
                +",\"protectMode\":"+protectMode
                +",\"motorPower\":"+motorPower
                +",\"overcurrentProtectVal\":"+overcurrentProtectVal
                +",\"overcurrentProtectTime\":"+overcurrentProtectTime
                +",\"undercurrentProtectVal\":"+undercurrentProtectVal
                +",\"undercurrentProtectTime\":"+undercurrentProtectTime
                +",\"voltage\":"+voltage
                +",\"overcoltageProtectVal\":"+overcoltageProtectVal
                +",\"overcoltageProtectTime\":"+overcoltageProtectTime
                +",\"undervoltageProtectVal\":"+undervoltageProtectVal
                +",\"undervoltageProtectTime\":"+undervoltageProtectTime
                +",\"inspectPeriod\":"+inspectPeriod
                +",\"inspectTime\":"+inspectTime
                +",\"switchDelay\":"+switchDelay
                +",\"voltageCalibration\":"+voltageCalibration
                +",\"current1Calibration\":"+current1Calibration
                +",\"current2Calibration\":"+current2Calibration
                +",\"ledBrightness\":"+ledBrightness
                +",\"currentBalibrationMode\":"+currentBalibrationMode
                +",\"version\":"+version
                +"}";

    }
}
