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
    int overCurrentProtectVal;

    //过流保护延时
    int overCurrentProtectTime;

    //欠流保护值
    int underCurrentProtectVal;

    //欠流保护延时
    int underCurrentProtectTime;

    //电压选择
    int voltage;

    //过压保护值
    int overVoltageProtectVal;

    //过压保护延时
    int overVoltageProtectTime;

    //欠压保护值
    int underVoltageProtectVal;

    //欠压保护延时
    int underVoltageProtectTime;

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
    int currentCalibrationMode;

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
        this.choosePump = ByteUtil.getIntWith2Byte(modbusBytes[7], modbusBytes[8]);

        //保留字段1
        this.reserved1 = 0;

        //保留字段2
        this.reserved2= 0;

        //屏蔽启动时间
        this.restartTime = ByteUtil.getIntWith2Byte(modbusBytes[13], modbusBytes[14]);


        //保护选择
        this.protectMode = ByteUtil.getIntWith2Byte(modbusBytes[15], modbusBytes[16]);

        //电机功率选择
        this.motorPower = ByteUtil.getIntWith2Byte(modbusBytes[17], modbusBytes[18]);


        //过流保护值
        this.overCurrentProtectVal = ByteUtil.getIntWith2Byte(modbusBytes[19], modbusBytes[20]);


        //过流保护延时
        this.overCurrentProtectTime = ByteUtil.getIntWith2Byte(modbusBytes[21], modbusBytes[22]);


        //欠流保护值
        this.underCurrentProtectVal = ByteUtil.getIntWith2Byte(modbusBytes[23], modbusBytes[24]);


        //欠流保护延时
        this.underCurrentProtectTime = ByteUtil.getIntWith2Byte(modbusBytes[25], modbusBytes[26]);

        //电压选择
        this.voltage = ByteUtil.getIntWith2Byte(modbusBytes[27], modbusBytes[28]);

        //过压保护值
        this.overVoltageProtectVal = ByteUtil.getIntWith2Byte(modbusBytes[29], modbusBytes[30]);


        //过压保护延时
        this.overVoltageProtectTime = ByteUtil.getIntWith2Byte(modbusBytes[31], modbusBytes[32]);


        //欠压保护值
        this.underVoltageProtectVal = ByteUtil.getIntWith2Byte(modbusBytes[33], modbusBytes[34]);


        //欠压保护延时
        this.underVoltageProtectTime = ByteUtil.getIntWith2Byte(modbusBytes[35], modbusBytes[36]);


        //巡检周期
        this.inspectPeriod = ByteUtil.getIntWith2Byte(modbusBytes[37], modbusBytes[38]);


        //巡检时间
        this.inspectTime = ByteUtil.getIntWith2Byte(modbusBytes[39], modbusBytes[40]);


        //切换延时
        this.switchDelay = ByteUtil.getIntWith2Byte(modbusBytes[41], modbusBytes[42]);


        //电压校准
        this.voltageCalibration = ByteUtil.getIntWith2Byte(modbusBytes[43], modbusBytes[44]);


        //电流1校准
        this.current1Calibration = ByteUtil.getIntWith2Byte(modbusBytes[45], modbusBytes[46]);


        //电流2校准
        this.current2Calibration = ByteUtil.getIntWith2Byte(modbusBytes[47], modbusBytes[48]);


        //led亮度调整
        this.ledBrightness = ByteUtil.getIntWith2Byte(modbusBytes[49], modbusBytes[50]);


        //电流校准方式
        this.currentCalibrationMode = ByteUtil.getIntWith2Byte(modbusBytes[51], modbusBytes[52]);


        //软件版本
        this.version = ByteUtil.getIntWith2Byte(modbusBytes[53], modbusBytes[54]) + "";
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
                +",\"overCurrentProtectVal\":"+overCurrentProtectVal
                +",\"overCurrentProtectTime\":"+overCurrentProtectTime
                +",\"underCurrentProtectVal\":"+underCurrentProtectVal
                +",\"underCurrentProtectTime\":"+underCurrentProtectTime
                +",\"voltage\":"+voltage
                +",\"overVoltageProtectVal\":"+overVoltageProtectVal
                +",\"overVoltageProtectTime\":"+overVoltageProtectTime
                +",\"underVoltageProtectVal\":"+underVoltageProtectVal
                +",\"underVoltageProtectTime\":"+underVoltageProtectTime
                +",\"inspectPeriod\":"+inspectPeriod
                +",\"inspectTime\":"+inspectTime
                +",\"switchDelay\":"+switchDelay
                +",\"voltageCalibration\":"+voltageCalibration
                +",\"current1Calibration\":"+current1Calibration
                +",\"current2Calibration\":"+current2Calibration
                +",\"ledBrightness\":"+ledBrightness
                +",\"currentCalibrationMode\":"+currentCalibrationMode
                +",\"version\":"+version
                +"}";

    }
}
