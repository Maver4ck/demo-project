package com.dstreltsov.testtask.controller;

import com.dstreltsov.testtask.dto.QuoteDTO;
import com.dstreltsov.testtask.service.QuoteService;
import com.dstreltsov.testtask.util.HTTPMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that handles all request regarding votes.
 *
 * @author dstreltsov
 * @since 11.02.2023
 */
@RestController
@RequestMapping("/api/vote")
public class VoteController {

    private final QuoteService quoteService;

    @Autowired
    public VoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @PostMapping("/up")
    public ResponseEntity<?> voteUp(@RequestBody QuoteDTO quoteDTO) {
        rejectIfIdAbsent(quoteDTO.getId());
        quoteService.voteUp(quoteDTO.getId());

        return ResponseEntity.ok(new HTTPMessage("Vote counted."));
    }

    @PostMapping("/down")
    public ResponseEntity<?> voteDown(@RequestBody QuoteDTO quoteDTO) {
        rejectIfIdAbsent(quoteDTO.getId());
        quoteService.voteDown(quoteDTO.getId());

        return ResponseEntity.ok(new HTTPMessage("Vote counted."));
    }

    private void rejectIfIdAbsent(Integer id) {
        if (id == null) {
            throw new RuntimeException("Quote ID is absent.");
        }
    }
}
