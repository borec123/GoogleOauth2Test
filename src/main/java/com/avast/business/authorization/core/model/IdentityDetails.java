package com.avast.business.authorization.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

/**
 * Provide identity details obtained from identity provider.
 *
 */
@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
public class IdentityDetails {
    
    /**
     * Identification if identity on identity provider.
     */
    private String id;
    
    /**
     * Primary email.
     */
    private String email;

    /**
     * Id of identity provider.
     */
    private String identityProviderId;

    /**
     * User name.
     */
    private String firstName;

    /**
     * User Last name.
     */
    private String lastName;
    
    /**
     * Locale on identity provider.
     */
    private String locale;

    /**
     * IsVerifiedUser signals verified email on identity provider.
     */
    private boolean isVerifiedUser;

}
