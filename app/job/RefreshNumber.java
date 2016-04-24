package job;

import play.jobs.Job;

/**
 * Created by Administrator on 2016/4/19.
 */
//@Every("1s")
public class RefreshNumber extends Job {

    @Override
    public void doJob() throws Exception {

////        DeviceOperator.getStatus();
//        byte[] bytes = new byte[]{4,2, 0, 0, 0, 7};
//        byte[] bytes2 = new byte[]{4,3, 0, 0, 0, 32};
//
//        CoilObserver.send(CRC16.addCRCChecker(bytes));
//        RegisterObserver.send(CRC16.addCRCChecker(bytes2));
//        OperationObserver.chooseMode();

    }
}
