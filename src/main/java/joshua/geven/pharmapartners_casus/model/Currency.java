package joshua.geven.pharmapartners_casus.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticker;

    private String name;

    @Column(name = "number_of_coins")
    private Long numberOfCoins;

    @Column(name = "market_cap", columnDefinition = "BIGINT")
    private Long marketCap;
}
