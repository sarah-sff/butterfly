package servise;

import dto.DeviceData;
import play.Logger;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 指令队列
 * urgentQueen：存放紧急指令
 * pollingQueen: 存放轮询指令
 */
public class InstructionQueen {

    private static final InstructionQueen single = new InstructionQueen();

    /**
     * 紧急队列,用于设置和配置信息(写操作)
     */
    public ArrayBlockingQueue urgentQueen = new ArrayBlockingQueue(256);
    /**
     * 循环队列,用于读取指示灯和电源/电压
     */
    public ArrayBlockingQueue pollingQueen = new ArrayBlockingQueue(2);

    //单例
    private InstructionQueen() {
    }

    public static InstructionQueen getInstance() {
        return single;
    }

    /**
     * 队列大小
     *
     * @return
     */
    public int size() {
        return urgentQueen.size() + pollingQueen.size();
    }

    /**
     * 增加紧急element
     *
     * @param element
     */
    public void addUrgent(Object element) {

        if (urgentQueen.size() < 256) {
            urgentQueen.add(element);
        } else {
            Logger.error("紧急队列元素过多,直接抛弃新指令... %s " + element.toString());
        }
    }

    /**
     * 增加普通element
     *
     * @param element
     */
    public void addCommon(Object element) {

        if (pollingQueen.size() < 2) {
            pollingQueen.add(element);
        }

    }

    /**
     * 获取元素
     * 如果紧急队列里面有，就拿紧急队列的
     * 否则，拿普通队列的
     *
     * @return
     * @throws InterruptedException
     */
    public Object take() {
        try {
            if (urgentQueen.isEmpty()) {
                Object e = pollingQueen.take();
                // 循环放入队列,等待下次执行
                pollingQueen.add(e);
                return e;
            } else {
                Object e = urgentQueen.take();
                return e;
            }
        } catch (InterruptedException e) {
            Logger.error(e.getMessage());
        }

        return null;
    }

    /**
     * 刷新循环指令队列
     */
    public void refreshPollingInstruction() {
        pollingQueen.clear();

        byte[] cycleInstruction1 = new byte[]{DeviceData.getSelectedDeviceAddr(), 2, 0, 0, 0, 7};
        byte[] cycleInstruction2 = new byte[]{DeviceData.getSelectedDeviceAddr(), 4, 0, 0, 0, 3};

        pollingQueen.add(cycleInstruction1);
        pollingQueen.add(cycleInstruction2);
    }

}
