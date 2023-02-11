package com.dstreltsov.testtask.service;

import com.dstreltsov.testtask.model.Quote;
import com.dstreltsov.testtask.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Quote service.
 *
 * @author dstreltsov
 * @since 11.02.2023
 */
@Service
@Transactional(readOnly = true)
public class QuoteService {

    private final QuoteRepository quoteRepository;

    @Autowired
    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @Transactional
    public void createQuote(Quote quote) {
        enrichQuote(quote);
        quoteRepository.save(quote);
    }

    @Transactional
    public void deleteQuote(Quote quote) {
        quoteRepository.delete(quote);
    }

    @Transactional
    public void editQuote(Quote old, Quote toUpdate) {
        updateQuote(old, toUpdate);
        quoteRepository.save(toUpdate);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void voteUp(Integer quoteId) {
        Quote quote = quoteRepository.findById(quoteId).get();
        quote.setVotes(quote.getVotes() + 1);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void voteDown(Integer quoteId) {
        Quote quote = quoteRepository.findById(quoteId).get();
        quote.setVotes(quote.getVotes() - 1);
    }

    public Optional<Quote> findById(String id) {
        return quoteRepository.findById(Integer.valueOf(id));
    }

    public Quote randomQuote() {
        int id = 1 + new Random().nextInt((int) quoteRepository.count());
        return quoteRepository.findById(id).orElseGet(this::randomQuote);
    }

    public List<Quote> bestQuotes() {
        return quoteRepository.findTop10ByOrderByVotesDesc();
    }

    public List<Quote> worstQuotes() {
        return quoteRepository.findTop10ByOrderByVotesAsc();
    }

    private void updateQuote(Quote old, Quote toUpdate) {
        toUpdate.setContent(old.getContent());
        toUpdate.setUpdatedAt(new Date());
    }

    private void enrichQuote(Quote quote) {
        Date date = new Date();
        quote.setVotes(0);
        quote.setCreatedAt(date);
        quote.setUpdatedAt(date);
    }
}
