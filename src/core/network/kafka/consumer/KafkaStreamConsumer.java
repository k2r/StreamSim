/**
 * 
 */
package core.network.kafka.consumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import core.element.IElement;
import core.element.relational.RelationalStreamElement;

/**
 * @author Roland
 *
 */
public class KafkaStreamConsumer implements IKafkaStreamConsumer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2297621515521121352L;
	private String topic;
	private Properties properties;
	private KafkaConsumer<String, String> kConsumer;
	private static final Logger logger = Logger.getLogger("KafkaStreamConsumer");
	
	public KafkaStreamConsumer(String host, Integer port, String topic) {
		this.topic = topic;
		this.properties = new Properties();
		this.properties.put("bootstrap.servers", host + ":" + port);
		this.properties.put("group.id", "streamsim_consumer");
		this.properties.put("enable.auto.commit", "false");
		this.properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		this.properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	}
	
	@Override
	public void connect() {
		 this.kConsumer = new KafkaConsumer<>(this.properties);
	     this.kConsumer.subscribe(Arrays.asList(this.topic));
	     logger.info("Connection to the Kafka cluster (topic " + this.topic + ")...");
	}

	
	@Override
	public IElement[] consume() {
		IElement[] result = null;
		try{
			ConsumerRecords<String, String> allRecords = this.kConsumer.poll(1000);
			if(allRecords != null){
				ArrayList<ConsumerRecord<String, String>> records = (ArrayList<ConsumerRecord<String, String>>) allRecords.records(this.topic);
				int nbRecords = records.size();
				logger.info("Consuming " + nbRecords + " records from the Kafka cluster on topic " + this.topic);
				result = new IElement[nbRecords];
				for(int i = 0; i < nbRecords; i++){
					IElement element = ((IElement) new RelationalStreamElement(records.get(i).value())); //generate a new relational stream element from a string
					result[i] = element;
				}
			}
		}catch(Exception e){
			logger.severe("New records cannot be consumed from the Kafka cluster because " + e);
		}
		this.kConsumer.commitSync();
		return result;
	}

	@Override
	public void disconnect() {
		this.kConsumer.close();
		logger.info("Closing the Kafka consumer client reading from cluster on host " + this.properties.getProperty("bootstrap.servers") + "...") ;
	}

	@Override
	public String getServerProperty(String property) {
		return this.properties.getProperty(property);
	}

}