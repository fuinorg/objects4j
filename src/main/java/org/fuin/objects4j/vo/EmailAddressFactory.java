package org.fuin.objects4j.vo;

import javax.enterprise.context.ApplicationScoped;

import org.fuin.objects4j.common.ThreadSafe;

/**
 * Creates a {@link EmailAddress}.
 */
@ThreadSafe
@ApplicationScoped
public final class EmailAddressFactory implements SimpleValueObjectFactory<String, EmailAddress> {

	@Override
	public final Class<EmailAddress> getSimpleValueObjectClass() {
		return EmailAddress.class;
	}

	@Override
	public final boolean isValid(final String value) {
		return EmailAddressStrValidator.isValid(value);
	}

	@Override
	public final EmailAddress create(final String value) {
		return new EmailAddress(value);
	}

}
