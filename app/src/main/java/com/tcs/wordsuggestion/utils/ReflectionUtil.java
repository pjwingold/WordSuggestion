package com.tcs.wordsuggestion.utils;

import android.app.Activity;
import android.view.View;

import com.tcs.wordsuggestion.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 */
public class ReflectionUtil {
    /**
     * You may use this method to automatically call findViewById() to initialise the member views of the given class.
     * Useful when there are a large number of fields that need to be initialised
     * The view id in the layout xml file must be identical to the field name declared in the activity
     *
     * @param context View or Activity subclasses
     * @param fieldClass The class that has no findViewById(), but with fields to be initialised, ie fragment, adapter etc. If it's null then the context will contain the fields to be initialised
     */
    public static void initViews(Object context, Object fieldClass) {
    /*Initialise fields in activity/view (with findViewById() method)
		 * Loop through each field of the context or the fieldClass
		 * If the field is a subclass of View, get its "fieldName"
		 * Retrieve an instance of the method "findViewById" from the context
		 * Get the value of the id : R.id.fieldName, the id declared in the layout file must be the same as the variable name in the context class
		 * R.id.fieldName is in the R.java class
		 * Invoke findViewById()
		 * Before setting the field value, set it to accessible
		 */
        Class contextClass = context.getClass();
        //if the context is not a subclass of activity or view, only these 2 seem to have findViewById()
        if (!(View.class.isAssignableFrom(contextClass) || Activity.class.isAssignableFrom(contextClass))) {
            throw new IllegalArgumentException("The context argument must be subclass of View or Activity.");
        }

        Field[] fields = fieldClass == null ? contextClass.getDeclaredFields() : fieldClass.getClass().getDeclaredFields();

        for (Field field : fields) {
            //Check if the field type is a subclass of View, ie Button btn = (Button) view;
            if (View.class.isAssignableFrom(field.getType())) {
                //ie image
                String fieldName = field.getName();
                //R.java - public static final class id {}
                Class idClz = R.id.class;

                try {
                    //must use getMethod() instead of getDeclaredMethod() as we have to sub class activity and findViewById() is from the parent Activity class
                    Method method = contextClass.getMethod("findViewById", int.class);

                    //R.java - public static final int image=0x7f0b0029;
                    Field idField = idClz.getDeclaredField(fieldName);
                    Object value = idField.get("");

                    //ie View v = activity.findViewById(R.id.image)
                    Object control = method.invoke(context, value);

                    //Since most fields are declared as private, to assign a value to it, we must set it be accessible first
                    field.setAccessible(true);
                    //Assign the result from findViewById() to the view control
                    field.set(fieldClass == null ? context : fieldClass, control);

                } catch (NoSuchMethodException e) {
                    L.log(e.getMessage());
                } catch (NoSuchFieldException e) {
                    L.log(e.getMessage());
                } catch (IllegalAccessException e) {
                    L.log(e.getMessage());
                } catch (InvocationTargetException e) {
                    L.log(e.getMessage());
                }
            }
        }
    }
}
