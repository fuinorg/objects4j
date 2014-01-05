package org.fuin.objects4j.vo;

import javax.enterprise.context.ApplicationScoped;

import org.fuin.objects4j.common.ThreadSafe;

/**
 * Creates a {@link Password}.
 */
@ThreadSafe
@ApplicationScoped
public class PasswordFactory implements SimpleValueObjectFactory<String, Password> {

	@Override
	public final Class<Password> getSimpleValueObjectClass() {
		return Password.class;
	}

	@Override
	public final boolean isValid(final String value) {
		return PasswordStrValidator.isValid(value);
	}

	@Override
	public final Password create(final String value) {
		return new Password(value);
	}
	
}
