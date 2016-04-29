package servise;

import dto.InsRecord;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 串口消息接收稳定性监控
 */
public class Stabler {
    //记录发送已指令的队列
    public ArrayBlockingQueue<InsRecord> insRecordQueen = new ArrayBlockingQueue<InsRecord>(256);

    //允许超时的最大时间
    public static int MAX_TIME_PERIOD = 2000;

    /**
     * 清除队列所有指令
     */
    public void clear(){
        insRecordQueen.clear();
    }

    /**
     * 消息接收情况检测
     */
    public void check(){
        try {

            System.out.println("-----已发送但是未收到回复的指令个数----"+insRecordQueen.size());

            InsRecord last = insRecordQueen.take();

            if( last == null) return;
            long passedTime = last.hasSendOutTimeLong();

            //超过既定时间,重新往串口发起消息；否则重新放回队列
            if(passedTime > MAX_TIME_PERIOD){

                System.out.println("超过"+MAX_TIME_PERIOD+"毫秒没有收到来自串口的消息，尝试再发送一次");

                SerialService.sendNextInstruction();
            }else{
                insRecordQueen.add(last);
            }
        }catch (InterruptedException e){

        }

    }

    /**
     * 接收消息
     */
    public void recMsg(){
        try {
            InsRecord last = insRecordQueen.take();
        }catch (InterruptedException e){

        }

    }

    /**
     * 发送消息
     */
    public void sendMsg(byte[] bytes){
        insRecordQueen.add(new InsRecord(bytes));
    }



    //单例
    private Stabler() {
    }

    private static final Stabler single = new Stabler();

    public static Stabler getInstance() {
        return single;
    }

}
