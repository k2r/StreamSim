/**
 * 
 */
package core.network.kafka.producer;

import java.util.Properties;
import java.util.logging.Logger;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import core.element.IElement;

/**
 * @author Roland
 *
 */
public class KafkaStreamProducer implements IKafkaStreamProducer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8709997102791884152L;
	
	private int packetCounter;
	private Properties properties;
	private KafkaProducer<String, String> kProducer;
	private IElement[] packet;
	private static final Logger logger = Logger.getLogger("KafkaStreamProducer");
	
	public KafkaStreamProducer(String host, Integer port) {
		this.properties = new Properties();
		this.properties.put("bootstrap.servers", host + ":" + port);
		this.properties.put("acks", "all");
		this.properties.put("retries", 0);
		this.properties.put("batch.size", 16384);
		this.properties.put("linger.ms", 1);
		this.properties.put("buffer.memory", 33554432);
		this.properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		this.properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		this.packetCounter = 0;
	}

	@Override
	public void connect() {
		this.kProducer = new KafkaProducer<>(this.properties);
		logger.info("Connection to the Kafka cluster...");
	}

	@Override
	public void produce(String streamName, IElement[] packet) {
		this.packet = packet;
		for(IElement element : packet){
			String elemToString = element.toString();
			ProducerRecord<String, String> record = new ProducerRecord<String, String>(streamName, elemToString);
			this.kProducer.send(record);
		}
		logger.fine("Packet with id " + this.packetCounter + " has been submitted properly");
		this.packetCounter++;
		
	}

	@Override
	public void disconnect() {
		this.kProducer.close();
		logger.info("Disconnection from the Kafka cluster on host(s) " + this.properties.getProperty("bootstrap.servers"));
	}

	@Override
	public IElement[] getPacket() {
		return this.packet;
	}

	@Override
	public int getPacketCounter() {
		return this.packetCounter;
	}

}