package eu.inmite.android.lib.validations.form.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import eu.inmite.android.lib.validations.form.iface.ICondition;

/**
 * This annotation makes conditioned validation possible. It can be attached to single validation or all on field.
 *
 * @author Tomas Vondracek
 */
@Target(value= ElementType.FIELD)
@Retention(value= RetentionPolicy.RUNTIME)
public @interface Condition {

	Class<? extends ICondition> value();

	/**
	 * if there is no target annotation mentioned we will apply condition for all annotations
	 */
	Class<? extends Annotation> validationAnnotation() default Condition.class;
	int viewId();
}
