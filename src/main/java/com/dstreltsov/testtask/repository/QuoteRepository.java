package com.dstreltsov.testtask.repository;

import com.dstreltsov.testtask.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Quote repository.
 *
 * @author dstreltsov
 * @since 11.02.2023
 */
@Repository
public interface QuoteRepository extends JpaRepository<Quote, Integer> {

    List<Quote> findTop10ByOrderByVotesDesc();

    List<Quote> findTop10ByOrderByVotesAsc();
}
