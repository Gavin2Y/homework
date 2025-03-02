package gavin.test.homework.infra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Stake implements Comparable<Stake>{

    private String customerId;
    private int amount;


    @Override
    public int compareTo(Stake o) {
        return Integer.compare(this.amount, o.amount);
    }

    @Override
    public String toString() {
        return customerId + "=" + amount;
    }
}
