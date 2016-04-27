package job;

import play.jobs.Job;
import play.jobs.OnApplicationStart;
import servise.InstructionQueen;
import servise.SerialService;

/**
 * Created by Administrator on 2016/4/19.
 */
@OnApplicationStart
public class RefreshNumber extends Job {

    @Override
    public void doJob() throws Exception {

        //增加一个读取电流电压数字的指令
        byte[] numberInstruction = new byte[]{4, 4, 0, 0, 0, 3};
        InstructionQueen.getInstance().addCommon(numberInstruction);

        //增加一个查询led状态的指令
        byte[] ledInstruction = new byte[]{4, 2, 0, 0, 0, 7};
        InstructionQueen.getInstance().addCommon(ledInstruction);

        //开始发送指令
        SerialService.sendNextInstruction();

    }
}
