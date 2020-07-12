package covid.tracing.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundUserAuthenticationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotFoundUserAuthenticationException(String authKey, String email) {
        super(String.format("당신의 정보를 찾을 수 없습니다. (이메일 = %s, 인증 번호 = %s)", email, authKey));
    }
}
