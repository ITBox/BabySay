/*
 * Copyright (c) 2013, Inmite s.r.o. (www.inmite.eu).
 *
 * All rights reserved. This source code can be used only for purposes specified
 * by the given license contract signed by the rightful deputy of Inmite s.r.o.
 * This source code can be used only by the owner of the license.
 *
 * Any disputes arising in respect of this agreement (license) shall be brought
 * before the Municipal Court of Prague.
 */

package eu.inmite.android.lib.validations.form.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Parse input string as {@link java.math.BigDecimal} and validate it for minimum value.
 * @author Tomas Vondracek
 */
@Target(value= ElementType.FIELD)
@Retention(value= RetentionPolicy.RUNTIME)
public @interface MinNumberValue {

	/**
	 * Number as string, e.g. "100.3" <br/>
	 * This value will be used in {@link java.math.BigDecimal} constructor
	 */
	String value();
	ComparingPolicy policy() default ComparingPolicy.INCLUSIVE;
	int messageId() default 0;
	int order() default 1000;
}
