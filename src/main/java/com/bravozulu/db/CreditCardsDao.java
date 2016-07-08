package com.bravozulu.db;
/**
 * Created by Melody on 7/1/16.
 */
import com.bravozulu.core.CreditCards;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class CreditCardsDao extends AbstractDAO<CreditCards> {
    public CreditCardsDao(SessionFactory factory) {
        super(factory);
    }

    public Optional<CreditCards> findByCardNum(String Num) {
        return Optional.ofNullable(get(Num));
    }
    public Optional<CreditCards> findByuserId(String userId) {
        return Optional.ofNullable(get(userId));
    }



}
