package job;

import play.jobs.Job;
import play.jobs.OnApplicationStart;
import utils.WebSocketUtil;

/**
 * Created by Administrator on 2016/4/19.
 */
@OnApplicationStart
public class AppBootstrapJob extends Job {


    @Override
    public void doJob() throws Exception {

        // 1. 连接WEB SOCKET应用服务器
        WebSocketUtil.init();
    }
}
