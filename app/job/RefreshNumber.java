package job;

import play.jobs.Job;
import play.jobs.OnApplicationStart;
import servise.SerialService;

/**
 * Created by Administrator on 2016/4/19.
 */
@OnApplicationStart
public class RefreshNumber extends Job {

    @Override
    public void doJob() throws Exception {

        //串口开始发送消息
        SerialService.sendout();

    }
}
