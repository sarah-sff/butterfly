package servise;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 指令队列
 * urgentQueen：存放紧急指令
 * commonQueen：存放常规指令
 */
public class InstructionQueen {

    public  ArrayBlockingQueue urgentQueen = new ArrayBlockingQueue(10);
    public  ArrayBlockingQueue commonQueen = new ArrayBlockingQueue(2);


    /**
     * 增加紧急element
     *
     * @param element
     */
    public void addUrgent(Object element) {

        if (commonQueen.size() < 10) {
            urgentQueen.add(element);
        }
    }


    /**
     * 增加普通element
     *
     * @param element
     */
    public void addCommon(Object element) {

        if (commonQueen.size() < 2) {
            commonQueen.add(element);
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
                Object e = commonQueen.take();
                commonQueen.add(e);
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


    //单例
    private InstructionQueen() {
    }

    private static final InstructionQueen single = new InstructionQueen();

    public static InstructionQueen getInstance() {
        return single;
    }

}
