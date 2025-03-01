/**
 * This package contains classes responsible for handling JSON Web Token (JWT) authentication, including token generation, validation, and integration with
 * Spring Security.
 *
 * <p>
 * Key components:
 * <ul>
 *   <li>{@code JwtAuthentication} – represents an authenticated user based on a JWT.</li>
 *   <li>{@code JwtFilter} – extracts and validates JWT tokens from incoming HTTP requests.</li>
 *   <li>{@code JwtProvider} – generates and validates JWT access and refresh tokens.</li>
 *   <li>{@code JwtUtils} – utility class for extracting user details from JWT claims.</li>
 *   <li>{@code PasswordEncodeImpl} – handles password encryption for secure authentication.</li>
 * </ul>
 * </p>
 */
package org.example.crmedu.infrastructure.auth.jwt;
