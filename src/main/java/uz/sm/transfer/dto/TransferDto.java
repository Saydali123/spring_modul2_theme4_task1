package uz.sm.transfer.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;

@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {
    public Integer userID;
    public String toPan;
    public Double amount;
    public Integer fromCardId;
}
