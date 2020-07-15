package musobek.shodmonov.developer_test.util;

import musobek.shodmonov.developer_test.annotations.VideoValidator;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class VideoFileValidator implements ConstraintValidator<VideoValidator, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        String contentType = multipartFile.getContentType();
        assert contentType != null;
        if (!isContentTypeSupported(contentType))
        {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Only mp4 format are allowed");
            return false;
        }
        else return true;
    }

    @Override
    public void initialize(VideoValidator constraintAnnotation) {

    }
    private boolean isContentTypeSupported(String content)
    {
        return content.equals("video/mp4");
    }
}
