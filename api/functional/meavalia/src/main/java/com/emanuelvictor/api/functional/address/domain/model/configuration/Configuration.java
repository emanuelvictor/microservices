package com.emanuelvictor.api.functional.address.domain.model.configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;  //TODO coupling
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.emanuelvictor.api.functional.address.domain.model.generic.AbstractEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.Base64;

/**
 * Created by Emanuel Victor on 15/03/2017.
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Configuration extends AbstractEntity {

    /**
     *
     */
    public Boolean requiredFeedback;
    /**
     *
     */
    @Enumerated(EnumType.ORDINAL)
    public FeedbackType feedbackType = FeedbackType.TEXT;
    /**
     *
     */
    private String one;
    /**
     *
     */
    private String two;
    /**
     *
     */
    private String three;
    /**
     *
     */
    private String four;
    /**
     *
     */
    private String five;
    /**
     *
     */
    @JsonIgnore
    private byte[] logo;
    /**
     *
     */
    private String logoPath;
    /**
     *
     */
    @JsonIgnore
    private byte[] backgroundImage;
    /**
     *
     */
    private String backgroundImagePath;
    /**
     *
     */
    private String thanksText;
    /**
     *
     */
    private String feedbackWording;
    /**
     *
     */
    private boolean feedback;
    /**
     *
     */
    private boolean brokenLineOnSelectionOfEvaluableItem;

    /**
     * @return byte[]
     */
    @JsonIgnore
    public byte[] getLogoFile() {
        return this.logo;
    }

    /**
     * @return byte[]
     */
    @JsonIgnore
    public byte[] getBackgroundImageFile() {
        return this.backgroundImage;
    }

    /**
     * @return byte[]
     */
    public String getLogoPath() {
        if (logo != null)
            return "./configurations/logo";
        return null;
    }

    /**
     * @return byte[]
     */
    public String getLogoInBase64() {
        if (logo != null)
            return Base64.getEncoder().withoutPadding().encodeToString(logo);
        return null;
    }

    /**
     * @return byte[]
     */
    public String getBackgroundImagePath() {
        if (backgroundImage != null)
            return "./configurations/background";
        return null;
    }

    /**
     * TODO remove
     * @return byte[]
     */
    public String getBackgroundImageInBase64() {
        if (backgroundImage != null)
            return Base64.getEncoder().withoutPadding().encodeToString(backgroundImage);
        return null;
    }

    /**
     * @return String
     */
    public String getThanksText() {
        return thanksText == null ? "Obrigado, agradecemos a participação" : thanksText;
    }

    /**
     * @return String
     */
    public String getOne() {
        return one == null ? "Péssimo" : one;
    }

    /**
     * @return String
     */
    public String getTwo() {
        return two == null ? "Ruim" : two;
    }

    /**
     * @return String
     */
    public String getThree() {
        return three == null ? "Regular" : three;
    }

    /**
     * @return String
     */
    public String getFour() {
        return four == null ? "Bom" : four;
    }

    /**
     * @return String
     */
    public String getFive() {
        return five == null ? "Ótimo" : five;
    }

}
