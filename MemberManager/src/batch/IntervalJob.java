package batch;

import java.util.Calendar;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class IntervalJob implements Job{
	private static int count = 0;
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		count++;
		System.out.println(Calendar.getInstance().getTime()+"IntervalJob - " + count);
	}

}
