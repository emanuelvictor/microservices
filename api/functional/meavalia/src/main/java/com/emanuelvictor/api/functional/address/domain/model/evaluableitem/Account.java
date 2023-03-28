package com.emanuelvictor.api.functional.address.domain.model.evaluableitem;

import com.emanuelvictor.api.functional.address.domain.model.generic.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Entity
@lombok.EqualsAndHashCode(callSuper = true)
public class Account extends AbstractEntity {

    /**
     *
     */
    @NotNull
    private boolean administrator;

    /**
     *
     */
    @NotNull
    private boolean client;

    /**
     *
     */
    @NotNull
    private boolean root;

    /**
     *
     */
    @Email
    @Column(unique = true)
    private String email;

    /**
     *
     */
    private String password;

    /**
     *
     */
    private LocalDateTime lastLogin;

    /**
     *
     */
    @OneToOne(mappedBy = "account", optional = false, fetch = FetchType.EAGER)
    private EvaluableItem evaluableItem;

    /**
     *
     */
    public Account() {
    }

    /**
     * @param id            long
     * @param administrator boolean
     * @param root          boolean
     * @param email         String
     * @param password      String
     * @param lastLogin     LocalDateTime
     * @param evaluableItem       Usuario
     */
    public Account(final long id, final @NotNull boolean administrator,
                   @NotNull final boolean root, @Email final String email,
                   final String password, final LocalDateTime lastLogin, final EvaluableItem evaluableItem) {
        this.id = id;
        this.administrator = administrator;
        this.root = root;
        this.email = email;
        this.password = password;
        this.lastLogin = lastLogin;
        this.evaluableItem = evaluableItem;
    }

    /**
     *
     */
    @PrePersist
    public void prePersist() {
        this.client = this.getTenant().equals(this.getEmail());
    }

    /**
     * @return boolean
     */
    public boolean getIsRoot() {
        return root;
    }

    /**
     * @return boolean
     */
    public boolean getIsAdministrator() {
        return administrator;
    }

    /**
     * @return boolean
     */
    public boolean getIsOperator() {
        return this.evaluableItem != null && this.evaluableItem.isOperator();
    }

    /**
     * @return String
     */
    public String getEmail() {
        return email != null ? email.toLowerCase() : null;
    }

    /**
     * @param email String
     */
    public void setEmail(final String email) {
        this.email = email != null ? email.toLowerCase() : null;
    }

}
