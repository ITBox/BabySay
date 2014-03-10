package eu.inmite.android.lib.validations.form.adapters;

import java.lang.annotation.Annotation;

import android.widget.Spinner;
import eu.inmite.android.lib.validations.form.iface.IFieldAdapter;

/**
 * @author Tomas Vondracek
 */
public class SpinnerAdapter implements IFieldAdapter<Spinner, Object> {

	@Override
	public Object getFieldValue(final Annotation annotation, final Object target, final Spinner fieldView) {
		return fieldView.getSelectedItem();
	}
}
