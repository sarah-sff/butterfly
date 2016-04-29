package job;

import play.jobs.Every;
import play.jobs.Job;
import servise.Stabler;

/**
 * Created by Administrator on 2016/4/29.
 */
@Every("1s")
public class SerialChecking extends Job {

    @Override
    public void doJob() throws Exception {

        Stabler.getInstance().check();

    }
}
