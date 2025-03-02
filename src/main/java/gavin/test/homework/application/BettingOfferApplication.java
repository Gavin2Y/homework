package gavin.test.homework.application;

public interface BettingOfferApplication {

    void buyStake(String userId, String offerId, String stake);
    String topXStakes(String offerId, int n);

}
