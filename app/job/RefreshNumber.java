package job;

import play.jobs.Every;
import play.jobs.Job;
import servise.DeviceOperator;

/**
 * Created by Administrator on 2016/4/19.
 */
@Every("2s")
public class RefreshNumber extends Job {

    @Override
    public void doJob() throws Exception {

        DeviceOperator.getStatus();
    }
}
