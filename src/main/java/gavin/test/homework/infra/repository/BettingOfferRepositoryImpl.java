package gavin.test.homework.infra.repository;

import gavin.test.homework.entities.BettingOffer;
import gavin.test.homework.entities.BettingOfferImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class BettingOfferRepositoryImpl implements BettingOfferRepository, InitializingBean {

    private Map<String, BettingOfferImpl> memoryRepository;


    @Override
    public BettingOffer getBettingOfferById(String id) {

        if(memoryRepository.get(id) == null) {
            BettingOfferImpl offer = new BettingOfferImpl(id);
            memoryRepository.put(id, offer);
        }

        return memoryRepository.get(id);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.memoryRepository = new ConcurrentHashMap<>();
    }
}
