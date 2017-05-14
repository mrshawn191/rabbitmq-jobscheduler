## rabbitmq-jobscheduler

This is a jobscheduler that sends jobs with RabbitMQ producer/consumer messaging. 

The jobscheduler fires a job each day to the producer which will query the firebase database and get all smsMessages that are needed to be sent today.  

For all valid smsMessages we send them as payload to our rabbitmq queue.

Our consumer will receive the payload and send the sms through an external request via twilio api. 

It will then save all successfully sent sms to firebase as a separate json child key, so that we can display these in a dashboard.

The project is hosted on Heroku and is a part of our Homeruns project that can be found here [Homeruns](http://homeruns.io)

## Project Design

![alt text](https://github.com/mrshawn191/rabbitmq-jobscheduler/blob/master/jobscheduler-rabbitmq2.png "Logo Title Text 1")

## Dependencies used

- Quartz
- Firebase
- RabbitMQ
- Twilio SDK

## Links

- [Quartz](https://github.com/mzabriskie/axios)
- [Firebase](https://github.com/mzabriskie/axios)
- [RabbitMQ](https://github.com/mzabriskie/axios)
- [Twilio SDK](https://github.com/mzabriskie/axios)
