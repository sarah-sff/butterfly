package servise;

import dto.InsRecord;
import play.Logger;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 串口消息接收稳定性监控
 */
public class Stabler {
    private static final Stabler single = new Stabler();
    //允许超时的最大时间
    public static int MAX_TIME_PERIOD = 2000;
    //记录发送已指令的队列
    public ArrayBlockingQueue<InsRecord> insRecordQueen = new ArrayBlockingQueue<InsRecord>(512);

    //单例
    private Stabler() {
    }

    public static Stabler getInstance() {
        return single;
    }

    /**
     * 清除队列所有指令
     */
    public void clear() {
        insRecordQueen.clear();
    }

    /**
     * 消息接收情况检测
     */
    public void check() {
        try {

            Logger.info("-----已发送但是未收到回复的指令个数----" + insRecordQueen.size());

            if (insRecordQueen.size() <= 0) {
                return;
            }

            InsRecord last = insRecordQueen.take();
            // 阻塞指定时间,是不是更安全
//            InsRecord last = insRecordQueen.poll(500L, TimeUnit.MILLISECONDS);

            if (last == null) return;
            long passedTime = last.hasSendOutTimeLong();

            //超过既定时间,重新往串口发起消息；否则重新放回队列
            if (passedTime > MAX_TIME_PERIOD) {

                Logger.info("超过" + MAX_TIME_PERIOD + "毫秒没有收到来自串口的消息，尝试再发送一次");

                SerialService.sendNextInstruction();
            } else {
                insRecordQueen.add(last);
            }
        } catch (InterruptedException e) {

        }

    }

    /**
     * 接收消息
     */
    public void removeFromInsRecordQueue() {
        try {
            insRecordQueen.take();
        } catch (InterruptedException e) {
            Logger.error(e.getMessage());
        }
    }

    /**
     * 发送消息
     */
    public void sendMsg(byte[] bytes) {
        insRecordQueen.add(new InsRecord(bytes));
    }

}
