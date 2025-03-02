package gavin.test.homework.entities;

import gavin.test.homework.infra.dto.Stake;
import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;


public class BettingOfferImpl implements BettingOffer{

    private String id;
    private PriorityBlockingQueue<Stake> stakes;
    private Map<String, Stake> customerStakeMapping;


    public BettingOfferImpl(String id) {
        this.id = id;
        this.stakes = new PriorityBlockingQueue<>();
        this.customerStakeMapping = new ConcurrentHashMap<>();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public List<Stake> topXStakes(int n) {
        return stakes.stream()
                .sorted(Comparator.reverseOrder()).limit(n).toList();
    }

    @Override
    public void buyStake(Stake stake) {

        if(customerStakeMapping.containsKey(stake.getCustomerId())) {
            customerStakeMapping.get(stake.getCustomerId()).setAmount(stake.getAmount());
            stakes.clear();
            stakes.addAll(customerStakeMapping.values());
        } else {
            customerStakeMapping.put(stake.getCustomerId(), stake);
            stakes.add(stake);
        }


    }
}
