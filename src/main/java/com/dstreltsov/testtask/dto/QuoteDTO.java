package com.dstreltsov.testtask.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.lang.Nullable;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Quote data transfer object.
 *
 * @author dstreltsov
 * @since 11.02.2023
 */
public class QuoteDTO {

    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonAlias(value = "quoteId")
    private Integer id;

    @NotEmpty(message = "Quote should not be empty.")
    @Size(min = 1, max = 300, message = "Quote should be between 1 and 300 characters.")
    private String content;

    private Integer votes;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    private AccountDTO owner;

    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String login;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public AccountDTO getOwner() {
        return owner;
    }

    public void setOwner(AccountDTO owner) {
        this.owner = owner;
    }
}
