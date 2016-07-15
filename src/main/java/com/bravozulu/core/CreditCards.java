package com.bravozulu.core;

/**
 * Created by Melody on 6/29/16.
 * */
import javax.persistence.*;


@Entity
@Table(name="creditcard")
@NamedQueries(value = {
        @NamedQuery(
                name = "com.bravozulu.core.CreditCards.findAll",
                query = "SELECT u FROM CreditCards u"
        )
})

public class CreditCards {
    @Id
    @Column(name = "creditCardNum", nullable = false)
    private String creditCardNum;

    @Column(name = "userId", nullable = false)
    private long userId;

    @Column(name = "expDate", nullable = false)
    private String expDate;

    public CreditCards(){}
    public CreditCards(String creditCardNum) {
        this.creditCardNum = creditCardNum;
    }
    public CreditCards(String creditCardNum, long userId, String expDate) {
        this.creditCardNum = creditCardNum;
        this.userId = userId;
        this.expDate = expDate;
    }

    public String getcreditCardNum() {
        return creditCardNum;
    }
    public long getuserId() {return userId;}
    public String getexpDate() {
        return expDate;
    }

    public void setcreditCardNum(String creditCardNum) {this.creditCardNum = creditCardNum;}
    public void setuserId(long userId) {this.userId = userId;}
    public void setExpDate(String expDate){this.expDate = expDate;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreditCards creditCard = (CreditCards) o;

        if(creditCardNum != creditCard.creditCardNum) return false;
        if(userId != creditCard.userId) return false;
        return (expDate.equals(creditCard.expDate));
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + creditCardNum.hashCode();
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + expDate.hashCode();

        return result;
    }


}

