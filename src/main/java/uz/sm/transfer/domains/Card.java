package uz.sm.transfer.domains;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String pan;

    private LocalDate expiry;

    private double balance;

    @Column(columnDefinition = "boolean default false")
    private boolean blocked;

    @ManyToOne(optional = false)
    private AuthUser owner;

}
