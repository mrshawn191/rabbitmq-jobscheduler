package com.services;

import com.rabbitmq.client.ConnectionFactory;
import com.services.publishers.SmsMessagePublisher;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

public class Scheduler
{

    final static Logger logger = LoggerFactory.getLogger(Scheduler.class);

    final static ConnectionFactory factory = new ConnectionFactory();

    public static void main(String[] args) throws Exception
    {
        factory.setUri(System.getenv("CLOUDAMQP_URL"));
        org.quartz.Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        scheduler.start();

        JobDetail jobDetail = newJob(RabbitMQJob.class).build();

        Trigger trigger = newTrigger()
                //                .startNow()
                .startAt(DateBuilder.tomorrowAt(15, 0, 0)) // first fire time 15:00:00 tomorrow
                .withSchedule(simpleSchedule()
                        .withIntervalInHours(24)
                        .repeatForever())
                .build();

        scheduler.scheduleJob(jobDetail, trigger);

    }

    public static class RabbitMQJob implements Job
    {

        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException
        {
            logger.info("Starting a new rabbitmqjob at " + System.currentTimeMillis());
            SmsMessagePublisher publisher = new SmsMessagePublisher();
            publisher.publish();
        }
    }
}
