package gavin.test.homework.entities;

import gavin.test.homework.infra.dto.Stake;

import java.util.List;

public interface BettingOffer {

    String getId();
    List<Stake> topXStakes(int n);
    void buyStake(Stake stake);

}
