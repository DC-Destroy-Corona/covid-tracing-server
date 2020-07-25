package covid.tracing.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NestedEmailException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NestedEmailException() {
        super(String.format("이미 사용중인 이메일 입니다."));
    }
}
