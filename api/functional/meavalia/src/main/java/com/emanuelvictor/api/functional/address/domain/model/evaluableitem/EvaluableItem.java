package com.emanuelvictor.api.functional.address.domain.model.evaluableitem;

import com.emanuelvictor.api.functional.address.domain.model.unit.Unit;
import com.emanuelvictor.api.functional.address.domain.model.evaluableitem.link.Evaluable;
import com.emanuelvictor.api.functional.address.domain.model.evaluableitem.link.Operator;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class EvaluableItem extends Person {

    /*
     * -----------------------------------------------------------
     *                           Photo
     * -----------------------------------------------------------
     */
    /**
     *
     */
    @Transient
    private String recap;

    /**
     *
     */
    @Column
    private byte[] photo;

    /**
     *
     */
    @Column
    private String photoPath;

    /**
     *
     */
    @Column
    private byte[] avatar;

    /**
     *
     */
    @Column
    private String avatarPath;

    /**
     *
     */
    @Column
    private byte[] thumbnail;

    /**
     *
     */
    @Column
    private String thumbnailPath;

    /**
     * Lista auxiliar que serve para informar se o usuário é um operador
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "evaluable_item")
    private List<Operator> operators;

    /**
     * Lista auxiliar que serve para informar se o usuário é um avaliável
     */
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "evaluable_item")
    private Set<Evaluable> evaluables;

    /**
     *
     */
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Account account;

    /*-------------------------------------------------------------------
     *						CONSTRUCTOR´S
     *-------------------------------------------------------------------*/

    /**
     *
     */
    public EvaluableItem() {
    }

    /**
     *
     */
    public EvaluableItem(final long id, final Object average) {
        this.id = id;
        this.average = average;
    }

    /**
     *
     */
    public EvaluableItem(final long id, final String name, final String thumbnailPath,
                         final String avatarPath, final String photoPath, final Object average,
                         final long countAssessments, final long assessmentsOne, final long assessmentsTwo,
                         final long assessmentsThree, final long assessmentsFour, final long assessmentsFive, final Account account, final String document) {

        this.account = account;

        this.document = document;

        this.id = id;
        this.name = name;
        this.thumbnailPath = thumbnailPath;
        this.avatarPath = avatarPath;
        this.photoPath = photoPath;

        this.average = average;
        this.countAssessments = countAssessments;
        this.assessmentsOne = assessmentsOne;
        this.assessmentsTwo = assessmentsTwo;
        this.assessmentsThree = assessmentsThree;
        this.assessmentsFour = assessmentsFour;
        this.assessmentsFive = assessmentsFive;
    }

    /**
     *
     */
    public EvaluableItem(final long id, final String name, final String thumbnailPath,
                         final String avatarPath, final String photoPath, final Account account, final String document) {

        this.account = account;

        this.document = document;

        this.id = id;
        this.name = name;
        this.thumbnailPath = thumbnailPath;
        this.avatarPath = avatarPath;
        this.photoPath = photoPath;

    }

    /*-------------------------------------------------------------------
     *						BEHAVIORS
     *-------------------------------------------------------------------*/

    /**
     * Remove '.', '/' e '-'
     *
     * @param document {String}
     * @return {String}
     */
    private static String prepareDocumento(String document) {
        if (document == null) {
            return null;
        }
        if (document.contains("@")) {
            return document;
        }

        document = document.replaceAll(Pattern.quote("."), "");
        document = document.replaceAll(Pattern.quote("/"), "");
        return document.replaceAll(Pattern.quote("-"), "");
    }

    /**
     * @param document {String}
     * @return {String}
     */
    private static String validateDocumento(final String document) {
        if (document == null || document.length() < 1) {
            return null;
        }
        if (document.contains("@")) {
            return document;
        }

        final String doc = EvaluableItem.prepareDocumento(document);

//        final CNPJValidator cnpjValidator = new CNPJValidator();
//        final CPFValidator cpfValidator = new CPFValidator();
//
//
//        if (cpfValidator.isEligible(doc)) {
//            try {
//                cpfValidator.assertValid(doc);
//            } catch (Exception e) {
//                throw new Usuario.CpfException();
//            }
//        } else if (cnpjValidator.isEligible(doc)) {
//            try {
//                cnpjValidator.assertValid(doc);
//            } catch (Exception e) {
//                throw new Usuario.CnpjException();
//            }
//        } else {
//            throw new CpfCnpjException();
//        }
        return doc;
    }

    /**
     * @return String
     */
    @Transient
    public String getUnits() {

        final Set<Unit> units = new HashSet<>();

        if (this.evaluables != null && !Objects.requireNonNull(this.evaluables).isEmpty())
            units.addAll(this.evaluables.stream().map(evaluable -> evaluable.getUnitAssessmentTypeQuiz().getUnitAssessmentType().getUnit()).collect(Collectors.toSet()));
        if (this.operators != null)
            units.addAll(this.operators.stream().map(Operator::getUnit).collect(Collectors.toSet()));

        if (units.isEmpty())
            return null;
        return units.stream().map(a -> a.name).collect(Collectors.joining(", "));
    }

    /**
     *
     */
    public boolean isOperator() {
        return this.operators != null && !this.operators.isEmpty();
    }

    /**
     *
     */
    public boolean isAttendant() {
        return this.evaluables != null && !this.evaluables.isEmpty();
    }

    /**
     *
     */
    public boolean getIsOperator() {
        return this.isOperator();
    }

    /**
     *
     */
    public boolean getIsAttendant() {
        return this.isAttendant();
    }

    /**
     *
     */
    public boolean getIsAdministrator() {
        return this.account != null && this.account.isAdministrator();
    }

    /**
     * Valida o document
     */
    public void validateDocumento() {
        this.document = validateDocumento(this.document);
    }


//    /**
//     * Devolve os pontos e barra ao document
//     *
//     * @param document {String}
//     * @return {String}
//     */
//    public static String formatDocumento(final String document) {
//        if (document == null || document.length() < 1) {
//            return null;
//        }
//        if (document.contains("@")) {
//            return document;
//        }
//
//        final String doc = Usuario.prepareDocumento(document);
//
//        final CNPJValidator cnpjValidator = new CNPJValidator();
//        final CPFValidator cpfValidator = new CPFValidator();
//
//
//        final CNPJFormatter cnpjFormatter = new CNPJFormatter();
//        final CPFFormatter cpfFormatter = new CPFFormatter();
//
//        if (cpfValidator.isEligible(doc)) {
//            try {
//                return cpfFormatter.format(doc);
//            } catch (Exception e) {
//                throw new Usuario.CpfException();
//            }
//        } else if (cnpjValidator.isEligible(doc)) {
//            try {
//                return cnpjFormatter.format(doc);
//            } catch (Exception e) {
//                throw new Usuario.CnpjException();
//            }
//        } else {
//            throw new CpfCnpjException();
//        }
//    }

    /**
     *
     */
    @PreUpdate
    @PrePersist
    public void handlePathPhoto() {
        if (this.photo != null) {
            this.photoPath = "./usuarios/" + id + "/photo";
            this.avatarPath = "./usuarios/" + id + "/avatar";
            this.thumbnailPath = "./usuarios/" + id + "/thumbnail";
        } else {
            this.photoPath = null;
            this.avatarPath = null;
            this.thumbnailPath = null;
        }
    }

    /*-------------------------------------------------------------------
     *						EXCEPTIONS
     *-------------------------------------------------------------------*/

    /**
     *
     */
    public static class CpfException extends RuntimeException {
        /**
         *
         */
        private static final long serialVersionUID = -5267258386816809448L;

        CpfException() {
            super("CPF inválido!");
        }
    }

    /**
     *
     */
    public static class CnpjException extends RuntimeException {
        /**
         *
         */
        private static final long serialVersionUID = -4954516819219451502L;

        CnpjException() {
            super("CNPJ inválido!");
        }
    }

    /**
     *
     */
    public static class CpfCnpjException extends RuntimeException {
        /**
         *
         */
        private static final long serialVersionUID = -4954516329219451502L;

        CpfCnpjException() {
            super("CPF ou CNPJ inválido!");
        }
    }
}
