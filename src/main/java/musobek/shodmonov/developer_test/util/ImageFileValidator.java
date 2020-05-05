package musobek.shodmonov.developer_test.util;

import musobek.shodmonov.developer_test.annotations.ImageValidator;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageFileValidator implements ConstraintValidator<ImageValidator, MultipartFile> {
    @Override
    public void initialize(ImageValidator constraintAnnotation) {

    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        String contentType = multipartFile.getContentType();
        assert contentType != null;
        if (!isContentTypeSupported(contentType))
        {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Only png jpg jpeg are allowed");
            return false;
        }
        else return true;
    }




    private boolean isContentTypeSupported(String content)
    {
        return content.equals("image/png")
                || content.equals("image/jpg")
                || content.equals("image/jpeg");
    }
}
