package rw.ac.rca.gradesclassb.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MathLib {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double number1;
    private Double number2;
    @Transient
    private Double sum;
    @Transient
    private Double product;

    public MathLib(Double number1, Double number2) {
        this.number1 = number1;
        this.number2 = number2;
    }
}
