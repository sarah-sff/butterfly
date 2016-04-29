package servise;

import dto.DeviceData;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 指令队列
 * urgentQueen：存放紧急指令
 * pollingQueen: 存放轮询指令
 */
public class InstructionQueen {

    public ArrayBlockingQueue urgentQueen = new ArrayBlockingQueue(256);
    public ArrayBlockingQueue pollingQueen = new ArrayBlockingQueue(2);


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
                pollingQueen.add(e);
                return e;
            } else {
                Object e = urgentQueen.take();
                return e;
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        return null;

    }


    /**
     * 刷新循环指令队列
     */
    public void refreshPollingInstruction() {
        byte[] cycleInstruction1 = new byte[]{DeviceData.selectedDeviceAddr, 2, 0, 0, 0, 7};
        byte[] cycleInstruction2 = new byte[]{DeviceData.selectedDeviceAddr, 4, 0, 0, 0, 3};

        pollingQueen.clear();
        pollingQueen.add(cycleInstruction1);
        pollingQueen.add(cycleInstruction2);
    }


    //单例
    private InstructionQueen() {
    }

    private static final InstructionQueen single = new InstructionQueen();

    public static InstructionQueen getInstance() {
        return single;
    }

}
