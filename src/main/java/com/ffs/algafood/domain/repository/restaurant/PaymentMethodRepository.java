package com.ffs.algafood.domain.repository.restaurant;

import com.ffs.algafood.domain.model.restaurant.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

/**
 * @author francisco
 */
@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

    @Query("SELECT MAX(dateUpdated) FROM PaymentMethod")
    OffsetDateTime findLatestUpdatedDate();
}
