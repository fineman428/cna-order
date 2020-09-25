package mall;

import mall.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    OrderRepository OrderRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverShiped_UpdateStatus(@Payload Shiped shiped){

        if(shiped.isMe()){
            Optional<Order> orderOptional = OrderRepository.findById(shiped.getOrderId());
            Order order = orderOptional.get();
            order.setStatus(shiped.getStatus());

            OrderRepository.save(order);
        }
    }

}
