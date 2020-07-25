package covid.tracing.rabbitmq.consumer;

import covid.tracing.common.constants.RabbitMQConstants;
import covid.tracing.mappers.BeaconMapper;
import covid.tracing.rabbitmq.domain.Beacon;
import covid.tracing.rabbitmq.domain.UserLocationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

@Service
public class UserPositionMessageConsumer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BeaconMapper beaconMapper;

    @RabbitListener(queues = {RabbitMQConstants.USER_POSITION_QUEUE})
    public void consumeSampleMessage(final UserLocationMessage message) {
        logger.info("accept message ==> " + message.toString());
        Beacon beacon = message.getBeacon();

        beaconMapper.insertUserLocationMessageByBeacon(
                message.getUserId(), beacon.getUuid(),
                LocalDateTime.parse(beacon.getHead(), ISO_LOCAL_DATE_TIME),
                LocalDateTime.parse(beacon.getTail(), ISO_LOCAL_DATE_TIME));
    }
}
