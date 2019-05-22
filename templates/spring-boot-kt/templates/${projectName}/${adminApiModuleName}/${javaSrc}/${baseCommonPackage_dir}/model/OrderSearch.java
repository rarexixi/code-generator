package ${baseCommonPackage}.model;

import lombok.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderSearch<T, O extends OrderCondition> implements Serializable {

    private T condition;
    private O order;
}