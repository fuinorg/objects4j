package org.fuin.objects4j.vo;

import javax.enterprise.context.ApplicationScoped;

import org.fuin.objects4j.common.ThreadSafe;

/**
 * Creates a {@link UserName}.
 */
@ThreadSafe
@ApplicationScoped
public final class UserNameFactory implements
		SimpleValueObjectFactory<String, UserName> {

	@Override
	public final Class<UserName> getSimpleValueObjectClass() {
		return UserName.class;
	}

	@Override
	public final boolean isValid(final String value) {
		return UserNameStrValidator.isValid(value);
	}

	@Override
	public final UserName create(final String value) {
		return new UserName(value);
	}

}
