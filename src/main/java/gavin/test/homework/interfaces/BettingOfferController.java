package gavin.test.homework.interfaces;


import gavin.test.homework.application.BettingOfferApplication;
import gavin.test.homework.config.interceptor.SessionInterceptor;
import jakarta.servlet.ServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BettingOfferController {

    @Autowired
    private BettingOfferApplication bettingOfferApplication;


    // curl -X POST -H "Content-Type: text/plain" http://localhost:8080/123/stake?sessionKey=42838d4f-2f50-46bd-8e9c-65ce22520281 -d 5
    @PostMapping("/{offerId}/stake")
    public void buyStake(ServletRequest request, @PathVariable("offerId") String offerId, @RequestBody String stake) {
        bettingOfferApplication.buyStake(request.getAttribute(SessionInterceptor.REQ_ATTR_USER_ID).toString(), offerId, stake);
    }

    @GetMapping("/{offerId}/highstakes")
    public String highestStakes(@PathVariable("offerId") String offerId) {
        return bettingOfferApplication.topXStakes(offerId, 3);
    }

}
