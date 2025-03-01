/**
 * Contains infrastructure components that integrate the CRM system with framework-specific functionality.
 * <p>
 * This package includes entity models, data mappings, repository interfaces, authentication mechanisms, and configuration settings.
 * </p>
 *
 * <p>
 * Key components:
 * <ul>
 *   <li>{@code entity} – defines entity classes for persistence layer mapping.</li>
 *   <li>{@code mapping} – handles conversion between domain models and database entities.</li>
 *   <li>{@code repository} – provides interfaces for database interaction.</li>
 *   <li>{@code auth.jwt} – manages JWT-based authentication and security.</li>
 *   <li>{@code configuration} – contains global application settings, including security and Swagger setup.</li>
 *   <li>{@code service} – implements core business logic with framework integrations (e.g., email, authentication).</li>
 * </ul>
 * </p>
 */
package org.example.crmedu.infrastructure;
