package cn.wanli.guicedemo.annobinding;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author wanli
 * @BindingAnnotation tells Guice that this is a binding annotation. Guice will produce an error if ever multiple binding annotations apply to the same member.
 * @Target({FIELD, PARAMETER, METHOD}) is a courtesy to your users. It prevents @PayPal from being accidentally being applied where it serves no purpose.
 * @Retention(RUNTIME) makes the annotation available at runtime.
 * @date 2019-09-26 16:29
 */
@BindingAnnotation
@Target({FIELD, PARAMETER, METHOD})
@Retention(RUNTIME)
public @interface Apple {
}
