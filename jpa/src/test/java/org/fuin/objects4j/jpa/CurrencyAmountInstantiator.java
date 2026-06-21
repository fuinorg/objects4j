/**
 * Copyright (C) 2015 Michael Schnell. All rights reserved.
 * http://www.fuin.org/
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see http://www.gnu.org/licenses/.
 */
package org.fuin.objects4j.jpa;

import java.math.BigDecimal;
import java.util.Currency;

import org.fuin.objects4j.common.ThreadSafe;
import org.fuin.objects4j.core.CurrencyAmount;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.spi.EmbeddableInstantiator;
import org.hibernate.metamodel.spi.ValueAccess;
import org.jspecify.annotations.Nullable;

/**
 * Allows the immutable {@link CurrencyAmount} to be used as a Hibernate {@code @Embeddable}. Instead of creating an instance via a no-arg
 * constructor and then writing the (final) fields, Hibernate builds the value through its constructor. Reference it with
 * {@code @org.hibernate.annotations.EmbeddableInstantiator(CurrencyAmountInstantiator.class)} on the {@code @Embedded} attribute. The value
 * order matches the embeddable's attributes sorted alphabetically: 'amount' (0) and 'currency' (1).
 */
@ThreadSafe
public class CurrencyAmountInstantiator implements EmbeddableInstantiator {

    @Override
    public boolean isInstance(final Object object, final SessionFactoryImplementor sessionFactory) {
        return object instanceof CurrencyAmount;
    }

    @Override
    public boolean isSameClass(final Object object, final SessionFactoryImplementor sessionFactory) {
        return object.getClass() == CurrencyAmount.class;
    }

    @Override
    public @Nullable Object instantiate(final ValueAccess valueAccess, final SessionFactoryImplementor sessionFactory) {
        final BigDecimal amount = valueAccess.getValue(0, BigDecimal.class);
        final Currency currency = valueAccess.getValue(1, Currency.class);
        if (amount == null && currency == null) {
            // An all-null row represents a null embeddable.
            return null;
        }
        return new CurrencyAmount(amount, currency);
    }

}
