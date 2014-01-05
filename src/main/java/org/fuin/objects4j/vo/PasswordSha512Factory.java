package org.fuin.objects4j.vo;

import javax.enterprise.context.ApplicationScoped;

import org.fuin.objects4j.common.ThreadSafe;

/**
 * Creates a {@link PasswordSha512}.
 */
@ThreadSafe
@ApplicationScoped
public class PasswordSha512Factory implements SimpleValueObjectFactory<String, PasswordSha512> {

	@Override
	public final Class<PasswordSha512> getSimpleValueObjectClass() {
		return PasswordSha512.class;
	}

	@Override
	public final boolean isValid(final String value) {
		return PasswordSha512StrValidator.isValid(value);
	}

	@Override
	public final PasswordSha512 create(final String value) {
		return new PasswordSha512(value);
	}
	
}
