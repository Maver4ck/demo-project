package com.dstreltsov.testtask.model;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;

/**
 * Quote table.
 *
 * @author dstreltsov
 * @since 12.02.2023
 */
@Entity
@Table
public class Quote {

    @Column(updatable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String content;

    @Column
    @Audited
    private Integer votes;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "login")
    private Account owner;

    public Quote() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Quote{" +
               "id=" + id +
               ", content='" + content + '\'' +
               ", votes=" + votes +
               ", updatedAt=" + updatedAt +
               ", createdAt=" + createdAt +
               ", owner=" + owner +
               '}';
    }
}
