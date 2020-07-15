package musobek.shodmonov.developer_test.annotations;

import musobek.shodmonov.developer_test.util.VideoFileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {VideoFileValidator.class})
public @interface VideoValidator {
    String message() default "Invalid video file";
    Class<?>[] groups() default {};
    Class<? extends Payload>[]payload() default {};
}

