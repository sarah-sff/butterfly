package job;

import play.jobs.Every;
import play.jobs.Job;
import servise.RegisterObserver;
import utils.CRC16;

/**
 * Created by Administrator on 2016/4/19.
 */
@Every("2s")
public class RefreshNumber extends Job {

    @Override
    public void doJob() throws Exception {

//        DeviceOperator.getStatus();
        byte[] bytes = new byte[]{4,2, 0, 0, 0, 7};
        byte[] bytes2 = new byte[]{4,4, 0, 0, 0, 3};

//        CoilObserver.send(CRC16.addCRCChecker(bytes));
        RegisterObserver.send(CRC16.addCRCChecker(bytes2));

    }
}
