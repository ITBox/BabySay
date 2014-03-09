package eu.inmite.android.lib.validations.form.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for validators - this way we can specify what validation is validator able to perform.
 *
 * @author Tomas Vondracek
 */
@Inherited
@Target(value= ElementType.TYPE)
@Retention(value= RetentionPolicy.RUNTIME)
public @interface ValidatorFor {

	Class<? extends Annotation>[] value();
}
