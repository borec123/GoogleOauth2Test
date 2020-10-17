/*
 * Copyright (c) 2020 Avast a.s., www.avast.com
 */
package com.avast.business.authorization.core.identity;


import java.net.URI;

import com.avast.business.authorization.core.model.IdentityDetails;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

/**
 * Class represents Identity provider entity.
 *
 * @since 2020-02-25
 */
public interface IdentityProvider {

    /**
     * Generates link to identity provider.
     *
     * @param params url parameters to apply in url template
     * @return generated url
     */
    URI generateLink(Object... params);

    /**
     * Used for resolving IdentityProvider implementations by identity_provider
     * query parameter of /authorize endpoint
     *
     * @return identity provider id
     */
    String getId();

    /**
     * Retrieve more information about provided identity.
     *
     * @param identifier unique identifier of the identity (can be session id,
     *                   identity token, ...)
     * @return more data about identity
     */
    Mono<IdentityDetails> getIdentityDetails(String identifier);


    /**
     * Try find identity by the email
     *
     * @param email email
     * @return more data about identity
     */
    Mono<IdentityDetails> getIdentityDetailsByEmail(String email);

    /**
     *
     * Create identity
     *
     * @param email details about the identity to be created. id is ignored
     * @param firstName
     * @param lastName
     * @param locale
     * @param password
     * @return more data about identity
     */
    Mono<IdentityDetails> createIdentity(String email, String firstName, String lastName, String locale, String password);


    /**
     * Revoke ticket on identity provider. On authorization server side the tokens
     * are invalidated. It does not depend on result of revoke status on identity
     * provider.
     * 
     * @param identifier identity identifier on identity provider
     * 
     * @return Mono with status
     */
    Mono<HttpStatus> revoke(String identifier);

    /**
     * Find out whether the identity provider is configured.
     * 
     * @return true if settings are not null, false otherwise
     */
    boolean isConfigured();

    /**
     * Retrieve logout endpoint on given identity provider.
     *
     * @return logout url
     */
    @Deprecated
    String getLogoutUrl();

}
