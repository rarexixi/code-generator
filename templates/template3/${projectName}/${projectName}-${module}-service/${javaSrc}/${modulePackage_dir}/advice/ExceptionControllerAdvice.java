package ${modulePackage}.advice;

import ${commonPackage}.exception.DmpDataNotFoundException;
import ${commonPackage}.exception.DmpException;
import ${commonPackage}.models.ResponseError;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.stream.Collectors;

@RestControllerAdvice
@CrossOrigin
public class ExceptionControllerAdvice {

    // region 参数校验异常

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String error = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
        return getError(HttpStatus.UNPROCESSABLE_ENTITY, error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseError> ConstraintViolationExceptionHandler(ConstraintViolationException e) {
        String error = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));
        return getError(HttpStatus.UNPROCESSABLE_ENTITY, error);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ResponseError> ValidationExceptionHandler(ValidationException e) {
        String error = e.getMessage();
        return getError(HttpStatus.UNPROCESSABLE_ENTITY, error);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResponseError> BindExceptionHandler(BindException e) {
        String error = e.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
        return getError(HttpStatus.UNPROCESSABLE_ENTITY, error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseError> MethodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e) {
        String error = "参数转换错误";
        return getError(HttpStatus.UNPROCESSABLE_ENTITY, error);
    }

    // endregion

    // 权限异常
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ResponseError> AuthorizationExceptionHandler(AuthorizationException e) {
        String error = "权限不足";
        return getError(HttpStatus.UNAUTHORIZED, error);
    }

    // 数据唯一索引或主键冲突
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ResponseError> DuplicateKeyExceptionHandler(DuplicateKeyException e) {
        String error = "数据唯一约束校验失败";
        return getError(HttpStatus.FORBIDDEN, error);
    }

    // 查询数据不存在
    @ExceptionHandler(DmpDataNotFoundException.class)
    public ResponseEntity<ResponseError> ExceptionHandler(DmpDataNotFoundException e) {
        String error = e.getMessage();
        return getError(HttpStatus.NOT_FOUND, error);
    }

    // 自定义权限
    @ExceptionHandler(DmpException.class)
    public ResponseEntity<ResponseError> ExceptionHandler(DmpException e) {
        String error = e.getMessage();
        return getError(HttpStatus.INTERNAL_SERVER_ERROR, error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> ExceptionHandler(Exception e) {
        String error = e.getMessage();
        return getError(HttpStatus.INTERNAL_SERVER_ERROR, error);
    }

    private ResponseEntity<ResponseError> getError(HttpStatus httpStatus, String message) {
        ResponseError responseError = new ResponseError();
        responseError.setMessage(message);
        return ResponseEntity.status(httpStatus).body(responseError);
    }
}