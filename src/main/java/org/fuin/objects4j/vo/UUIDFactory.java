package org.fuin.objects4j.vo;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import org.fuin.objects4j.common.ThreadSafe;

/**
 * Creates a {@link UUID}.
 */
@ThreadSafe
@ApplicationScoped
public final class UUIDFactory implements SimpleValueObjectFactory<String, UUID> {

	@Override
	public final Class<UUID> getSimpleValueObjectClass() {
		return UUID.class;
	}

	@Override
	public final boolean isValid(final String value) {
		return UUIDStrValidator.isValid(value);
	}

	@Override
	public final UUID create(final String value) {
		return UUID.fromString(value);
	}

}
