package org.example.lunchvote.web;

import org.example.lunchvote.util.ValidationUtil;
import org.example.lunchvote.util.exception.ErrorInfo;
import org.example.lunchvote.util.exception.ErrorType;
import org.example.lunchvote.util.exception.IllegalRequestDataException;
import org.example.lunchvote.util.exception.VotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.example.lunchvote.util.exception.ErrorType.*;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static final Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    public static final String EXCEPTION_DUPLICATE_EMAIL = "User with this email already exists";
    public static final String EXCEPTION_DUPLICATE_RESTAURANT_NAME = "Restaurant with this name already exists";
    private static final String EXCEPTION_DUPLICATE_VOTE = "Re-vote is not possible already for today";
    private static final String EXCEPTION_DUPLICATE_DISH_IN_MENU = "This dish is already in menu";
    private static final String EXCEPTION_DUPLICATE_MENU_RESTAURANT = "Menu for this restaurant and date already exists";

    private static final Map<String, String> CONSTRAINS_MAP = Map.of(
            "users_unique_email_idx", EXCEPTION_DUPLICATE_EMAIL,
            "restaurants_unique_name_idx", EXCEPTION_DUPLICATE_RESTAURANT_NAME,
            "votes_unique_date_user_idx", EXCEPTION_DUPLICATE_VOTE,
            "dishes_unique_menu_idx", EXCEPTION_DUPLICATE_DISH_IN_MENU,
            "menus_unique_restaurant_date_idx", EXCEPTION_DUPLICATE_MENU_RESTAURANT);

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  //422
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorInfo notFoundError(HttpServletRequest req, RuntimeException e) {
        return logAndGetErrorInfo(req, e, false, DATA_NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT)  // 418
    @ExceptionHandler(VotingException.class)
    public ErrorInfo VotingError(HttpServletRequest req, RuntimeException e) {
        return logAndGetErrorInfo(req, e, false, VOTING_ERROR);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        String rootMsg = ValidationUtil.getRootCause(e).getMessage();
        if (rootMsg != null) {
            String lowerCaseMsg = rootMsg.toLowerCase();
            for (Map.Entry<String, String> entry : CONSTRAINS_MAP.entrySet()) {
                if (lowerCaseMsg.contains(entry.getKey())) {
                    return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR, entry.getValue());
                }
            }
        }
        return logAndGetErrorInfo(req, e, true, DATA_ERROR);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ErrorInfo bindValidationError(HttpServletRequest req, Exception e) {
        BindingResult result = e instanceof BindException ?
                ((BindException) e).getBindingResult() : ((MethodArgumentNotValidException) e).getBindingResult();

        String[] details = result.getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toArray(String[]::new);

        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR, details);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({IllegalRequestDataException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ErrorInfo illegalRequestDataError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true, APP_ERROR);
    }

    //    https://stackoverflow.com/questions/538870/should-private-helper-methods-be-static-if-they-can-be-static
    private ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType, String... details) {
        Throwable rootCause = ValidationUtil.logAndGetRootCause(log, req, e, logException, errorType);
        return new ErrorInfo(req.getRequestURL(), errorType,
                errorType.getErrorCode(),
                details.length != 0 ? details : new String[]{ValidationUtil.getMessage(rootCause)});
    }
}