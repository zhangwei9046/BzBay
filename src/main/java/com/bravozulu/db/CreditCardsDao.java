package com.bravozulu.db;
/**
 * Created by Melody on 7/1/16.
 */
import com.bravozulu.core.CreditCards;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

public class CreditCardsDao extends AbstractDAO<CreditCards> {
    public CreditCardsDao(SessionFactory factory) {
        super(factory);
    }
    public CreditCards create(CreditCards creditcard) { return persist(creditcard); }

    public List<CreditCards> findAll() {
        return list(namedQuery("com.bravozulu.core.CreditCards.findAll"));
    }
    public Optional<CreditCards> findByCardNum(String Num) {
        return Optional.ofNullable(get(Num));
    }
    public Optional<CreditCards> findByUserId(Long userId) {
        return Optional.ofNullable(get(userId));
    }

    public void delete(String cardnum) {
        CreditCards card = findByCardNum(cardnum).orElseThrow(() -> new NotFoundException("No such creditcard."));
        currentSession().delete(card);
    }

}
