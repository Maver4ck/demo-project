package com.dstreltsov.testtask.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.Nullable;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * Account data transfer object.
 *
 * @author dstreltsov
 * @since 11.02.2023
 */
public class AccountDTO {

    @Nullable
    private Integer id;

    @NotEmpty(message = "Login should not be empty.")
    @Size(min = 2, max = 100, message = "Login should be between 2 and 100 characters.")
    private String login;

    @NotEmpty(message = "Password should not be empty.")
    private String password;

    @Email(message = "Email should be well-formatted.")
    @NotEmpty(message = "Email should not be empty.")
    private String email;

    @Min(value = 1900, message = "Birth year should be greater than 1900.")
    private Integer birthYear;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date createdAt;

    @JsonIgnore
    List<QuoteDTO> quotes;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<QuoteDTO> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<QuoteDTO> quotes) {
        this.quotes = quotes;
    }
}
