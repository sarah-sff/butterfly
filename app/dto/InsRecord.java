package dto;

/**
 * 记录指令发送时间
 * Created by Administrator on 2016/4/29.
 */
public class InsRecord {

    //modbus指令前两个字节（可唯一确定一条指令），即：地址和功能码
    byte[] instruction = new byte[2];

    //发送时间
    Long sendTime = System.currentTimeMillis();


    public InsRecord(byte[] message) {
        if (message == null || message.length < 2) {
            return;
        }

        for (int i = 0; i < 2; i++) {
            this.instruction[i] = message[i];
        }
    }

    /**
     * 判断相等（指令相等即可）
     *
     * @param insRecord
     * @return
     */
    public boolean equals(InsRecord insRecord) {
        return insRecord.instruction[0] == this.instruction[0]
                && insRecord.instruction[1] == this.instruction[1];
    }

    /**
     * 已经发送多久（毫秒）
     *
     * @return
     */
    public long hasSendOutTimeLong() {

        Long now = System.currentTimeMillis();
        return now - this.sendTime;
    }
}
