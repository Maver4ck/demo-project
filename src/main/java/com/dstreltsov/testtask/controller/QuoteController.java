package com.dstreltsov.testtask.controller;

import com.dstreltsov.testtask.dto.QuoteDTO;
import com.dstreltsov.testtask.model.Account;
import com.dstreltsov.testtask.model.Quote;
import com.dstreltsov.testtask.service.AccountService;
import com.dstreltsov.testtask.service.QuoteService;
import com.dstreltsov.testtask.util.ChartUtil;
import com.dstreltsov.testtask.util.ErrorHandler;
import com.dstreltsov.testtask.util.HTTPMessage;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeriesCollection;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller that handles all request regarding Quote entity.
 *
 * @author dstreltsov
 * @since 11.02.2023
 */
@RestController
@RequestMapping("api/quotes")
public class QuoteController {

    private final QuoteService   quoteService;
    private final AccountService accountService;
    private final ModelMapper    modelMapper;
    private final ErrorHandler   errorHandler;
    private final EntityManager  entityManager;

    @Autowired
    public QuoteController(QuoteService quoteService,
                           AccountService accountService,
                           ModelMapper modelMapper,
                           ErrorHandler errorHandler,
                           EntityManager entityManager) {
        this.quoteService = quoteService;
        this.accountService = accountService;
        this.modelMapper = modelMapper;
        this.errorHandler = errorHandler;

        this.entityManager = entityManager;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createQuote(@RequestBody @Valid QuoteDTO quoteDTO, BindingResult bindingResult) {

        Optional<Account> owner = accountService.findByLogin(quoteDTO.getLogin());
        if (!owner.isPresent()) {
            bindingResult.rejectValue("login", "Correct login of user who tries to create a quote is required.");
        }
        if (bindingResult.hasErrors()) {
            errorHandler.returnErrorsToClient(bindingResult);
        }
        Quote toCreate = convertToQuote(quoteDTO);
        toCreate.setOwner(owner.get());

        quoteService.createQuote(toCreate);

        return ResponseEntity
                .ok()
                .body(new HTTPMessage("Success."));
    }

    @PostMapping("/edit/id/{id}")
    public ResponseEntity<?> editQuote(@RequestBody @Valid QuoteDTO quoteDTO,
                                       BindingResult bindingResult,
                                       @PathVariable String id) {
        Optional<Quote> fromDB = quoteService.findById(id);
        if (!fromDB.isPresent()) {
            bindingResult.rejectValue("id", "No quotes found by this ID.");
        }
        if (!Objects.equals(quoteDTO.getLogin(), fromDB.get().getOwner().getLogin())) {
            bindingResult.rejectValue("login", "Quote does not belong to specified account.");
        }
        if (bindingResult.hasErrors()) {
            errorHandler.returnErrorsToClient(bindingResult);
        }
        quoteService.editQuote(convertToQuote(quoteDTO), fromDB.get());

        return ResponseEntity.ok().body(new HTTPMessage("Success"));
    }

    @PostMapping("/delete/id/{id}")
    public ResponseEntity<?> deleteQuote(@PathVariable String id,
                                         @RequestBody QuoteDTO quoteDTO) {
        if (quoteDTO.getLogin() == null) {
            throw new RuntimeException("Login is required.");
        }
        Optional<Quote> fromDB = quoteService.findById(id);
        if (!fromDB.isPresent()) {
            throw new RuntimeException("No quotes found by ID: " + id);
        }

        if (Objects.equals(quoteDTO.getLogin(), fromDB.get().getOwner().getLogin())) {
            quoteService.deleteQuote(fromDB.get());
        } else {
            throw new RuntimeException("Quote does not belong to specified account.");
        }

        return ResponseEntity.ok().body(new HTTPMessage("Success"));
    }

    @GetMapping("/view/id/{id}")
    public QuoteDTO viewQuoteDetails(@PathVariable String id) {
        Optional<Quote> fromDB = quoteService.findById(id);
        if (fromDB.isPresent()) {
            return convertToQuoteDTO(fromDB.get());
        }
        throw new RuntimeException("No quote by this ID.");
    }

    @GetMapping("/view-random")
    public QuoteDTO viewRandomQuote() {
        return convertToQuoteDTO(quoteService.randomQuote());
    }

    @GetMapping("/view/best")
    public List<QuoteDTO> bestQuotes() {
        return convertToListDTO(quoteService.bestQuotes());
    }

    @GetMapping("/view/worst")
    public List<QuoteDTO> worstQuotes() {
        return convertToListDTO(quoteService.worstQuotes());
    }

    @GetMapping("/view/history/id/{id}")
    @SuppressWarnings("unchecked")
    public HTTPMessage viewQuoteHistory(@PathVariable String id) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        List<Quote> resultList = auditReader.createQuery()
                .forRevisionsOfEntity(Quote.class, true, false)
                .add(AuditEntity.id().eq(Integer.valueOf(id)))
                .getResultList();

        List<Integer> histMap = resultList.stream()
                .map(Quote::getVotes)
                .collect(Collectors.toList());

        if (histMap.isEmpty()) {
            return new HTTPMessage("Quote has not been updated yet.");
        }

        XYSeriesCollection dataset = ChartUtil.createChartDataset(histMap);
        JFreeChart         chart   = ChartUtil.createXYChart(dataset);

        try {
            return new HTTPMessage(ChartUtil.chartToBase64(chart));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private List<QuoteDTO> convertToListDTO(List<Quote> quotes) {
        return quotes.stream()
                .map(this::convertToQuoteDTO)
                .collect(Collectors.toList());
    }

    private Quote convertToQuote(QuoteDTO quoteDTO) {
        return modelMapper.map(quoteDTO, Quote.class);
    }

    private QuoteDTO convertToQuoteDTO(Quote quote) {
        return modelMapper.map(quote, QuoteDTO.class);
    }
}
