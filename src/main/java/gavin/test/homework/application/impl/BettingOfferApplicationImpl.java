package gavin.test.homework.application.impl;

import gavin.test.homework.application.BettingOfferApplication;
import gavin.test.homework.entities.BettingOffer;
import gavin.test.homework.infra.dto.Stake;
import gavin.test.homework.infra.repository.BettingOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BettingOfferApplicationImpl implements BettingOfferApplication {

    @Autowired
    private BettingOfferRepository bettingOfferRepository;


    @Override
    public void buyStake(String userId, String offerId, String stake) {
        BettingOffer offer = bettingOfferRepository.getBettingOfferById(offerId);
        offer.buyStake(new Stake(userId, Integer.parseInt(stake)));
    }

    @Override
    public String topXStakes(String offerId, int n) {
        BettingOffer offer = bettingOfferRepository.getBettingOfferById(offerId);
        return offer.topXStakes(n).stream().map(Stake::toString).collect(Collectors.joining(","));
    }
}
