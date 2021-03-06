package com.infogen.etl;

import org.apache.commons.cli.ParseException;

import com.infogen.mapper.InfoGen_Mapper;
import com.infogen.yarn.InfoGen_Container;
import com.infogen.yarn.Job_Configuration;

/**
 * 独立部署示例程序,可以直接传入参数启动
 * 
 * @author larry/larrylv@outlook.com/创建时间 2015年12月21日 下午1:12:07
 * @since 1.0
 * @version 1.0
 */
public class Kafka_To_Hdfs_Standalone {

	public static void main(String[] args) throws InterruptedException, ClassNotFoundException, ParseException {
		Job_Configuration job_configuration = Job_Configuration.get_configuration(args);
//		props.put("serializer.class", "kafka.serializer.StringEncoder");  
//        props.put("metadata.broker.list", "192.168.202.34:9092,192.168.202.35:9092,192.168.202.36:9092");  
//        props.put("zk.connect", "192.168.202.16:2181,192.168.202.17:2181,192.168.202.18:2181");
		// job_configuration.zookeeper = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
		// job_configuration.topic = "topic6";
		// job_configuration.group = "group1";
		job_configuration.zookeeper = "192.168.202.16:2181,192.168.202.17:2181,192.168.202.18:2181";//"172.16.8.97:2181,172.16.8.98:2181,172.16.8.99:2181";
		job_configuration.topic = "infogen_topic_tracking";
		job_configuration.group = "infogen_etl";
		@SuppressWarnings("unchecked")
		Class<? extends InfoGen_Mapper> mapper_clazz = (Class<? extends InfoGen_Mapper>) Class.forName("com.infogen.etl.Kafka_To_Hdfs_Mapper");
		job_configuration.mapper = mapper_clazz;
		job_configuration.output = "hdfs://spark101:8020/infogen/output_uat/";
		job_configuration.numContainers = 5;

		for (int i = 0; i < job_configuration.numContainers; i++) {
			new Thread(() -> {
				try {
					InfoGen_Container infogen_container = new InfoGen_Container(job_configuration);
					infogen_container.submit();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
		}
		Thread.currentThread().join();
	}

}
