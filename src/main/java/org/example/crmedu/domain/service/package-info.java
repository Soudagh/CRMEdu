/**
 * This package contains service interfaces and their implementations responsible for handling business logic and interacting with repositories.
 * <p>
 * The {@code organization}, {@code schedule}, {@code student}, {@code subject}, {@code tutor}, and {@code user} packages contain services that implement the
 * core business logic for each respective entity. These services interact with the corresponding repositories to perform CRUD operations and enforce business
 * rules.
 * </p>
 * <p>
 * The {@code jwt} and {@code auth} packages contain services that are the rules that needed to implemented for work with authentication. {@code jwt} package
 * provides security utilities, {@code auth} includes authentication-related services.
 * </p>
 * The {@code BaseCrudService} interface contains methods for implement CRUD-operations.
 */
package org.example.crmedu.domain.service;
