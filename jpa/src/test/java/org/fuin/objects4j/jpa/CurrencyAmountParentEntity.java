/**
 * Copyright (C) 2013 Future Invent Informationsmanagement GmbH. All rights
 * reserved. <http://www.fuin.org/>
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
 * along with this library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fuin.objects4j.jpa;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.fuin.objects4j.core.CurrencyAmount;

@Entity(name = "CURRENCY_AMOUNT_PARENT")
public class CurrencyAmountParentEntity {

    @Id
    @Column(name = "ID")
    private long id;

    // @formatter:off
	@Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "AMOUNT", precision = 12, scale = 2, nullable = true))
    @AttributeOverride(name = "currency", column = @Column(name = "CURRENCY", columnDefinition = "varchar(255)", nullable = true))
	// @formatter:on
    private CurrencyAmount amount;

    @Convert(converter = CurrencyAmountAttributeConverter.class)
    @Column(name = "PRICE", nullable = true)
    @Basic
    private CurrencyAmount price;

    public CurrencyAmountParentEntity() {
        super();
    }

    public CurrencyAmountParentEntity(long id) {
        super();
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CurrencyAmount getAmount() {
        return amount;
    }

    public void setAmount(CurrencyAmount amount) {
        this.amount = amount;
    }

    public CurrencyAmount getPrice() {
        return price;
    }

    public void setPrice(CurrencyAmount price) {
        this.price = price;
    }

}
