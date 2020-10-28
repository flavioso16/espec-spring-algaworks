package com.algaworks.algafood.api.exceptionhandler;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.EntityNotFound;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Throwable rootCause = ExceptionUtils.getRootCause(e);

        if(rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);

        } else if(rootCause instanceof PropertyBindingException) {
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
        }

        String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
        Problem problem = createProblemBuilder(status, ProblemType.MESSAGE_INCOMPREHENSIBLE,
                detail).build();

        return handleExceptionInternal(e, problem, headers, status, request);

    }

    private ResponseEntity<Object> handleInvalidFormatException(
            InvalidFormatException e, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String path = joinPath(e.getPath());

        String detail = String.format("A propriedade '%s' recebeu o valor '%s', " +
                "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                path, e.getValue(), e.getTargetType().getSimpleName());
        Problem problem = createProblemBuilder(status, ProblemType.MESSAGE_INCOMPREHENSIBLE,
                detail).build();

        return handleExceptionInternal(e, problem, headers, status, request);
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
    }

    private ResponseEntity<Object> handlePropertyBindingException(
            PropertyBindingException e, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String path = joinPath(e.getPath());

        ProblemType problemType = ProblemType.MESSAGE_INCOMPREHENSIBLE;
        String detail = String.format("A propriedade '%s' não existe. "
                + "Corrija ou remova essa propriedade e tente novamente.", path);
        Problem problem = createProblemBuilder(status, ProblemType.MESSAGE_INCOMPREHENSIBLE,
                detail).build();

        return handleExceptionInternal(e, problem, headers, status, request);
    }

    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(
            EntityNotFound e, WebRequest webRequest) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        Problem problem = createProblemBuilder(status, ProblemType.ENTITY_NOT_FOUND,
                e.getMessage()).build();

        return handleExceptionInternal(e, problem, new HttpHeaders(), status, webRequest);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(
            EntityInUseException e, WebRequest webRequest) {

        HttpStatus status = HttpStatus.CONFLICT;
        Problem problem = createProblemBuilder(status, ProblemType.ENTITY_IN_USE,
                e.getMessage()).build();

        return handleExceptionInternal(e, problem, new HttpHeaders(),
                status, webRequest);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleNegocioException(
            BusinessException e, WebRequest webRequest) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        Problem problem = createProblemBuilder(status, ProblemType.BUSINESS_ERROR,
                e.getMessage()).build();

        return handleExceptionInternal(e, problem, new HttpHeaders(),
                status, webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        if(body == null) {
            body = Problem.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .build();
        } else if(body instanceof String){
            body = Problem.builder()
                    .title((String) body)
                    .status(status.value())
                    .build();
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(
            HttpStatus status, ProblemType problemType, String detail) {
        return Problem.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail);
    }

}
