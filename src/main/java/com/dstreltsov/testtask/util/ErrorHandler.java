package com.dstreltsov.testtask.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

/**
 * Error handler for passed binding results.
 *
 * @author dstreltsov
 * @since 11.02.2023
 */
@Component
public class ErrorHandler {

    private final ObjectMapper objectMapper;

    @Autowired
    public ErrorHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Format all errors in bindingResult to a convenient string message.
     *
     * @param bindingResult a container for errors happened during validation
     * @throws RuntimeException to be handled in {@link com.dstreltsov.testtask.exception.ControllerExceptionHandler}
     */
    public void returnErrorsToClient(BindingResult bindingResult) {

        List<Error> errorsList = new ArrayList<>(bindingResult.getFieldErrorCount());

        for (FieldError arg : bindingResult.getFieldErrors()) {
            errorsList.add(new Error(
                    arg.getField(),
                    arg.getDefaultMessage() == null ? arg.getCode() : arg.getDefaultMessage()
            ));
        }

        String errMsg;
        try {
            errMsg = objectMapper.writeValueAsString(errorsList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
        throw new RuntimeException(errMsg);
    }
}
