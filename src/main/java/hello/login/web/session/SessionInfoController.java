package hello.login.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;

@Slf4j
@RestController
public class SessionInfoController {
    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "세션이 없습니다.";
        }

        //세션 데이터 출력
        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name={}, value={}",
                        name, session.getAttribute(name)));

        //새션을 무한정 보관하면 탈취나 메모리 측면에서 단점이 있다.
        log.info("sessionId={}", session.getId());
        log.info("maxInactiveInterval={}", session.getMaxInactiveInterval());

        //생성 시점으로부터 30분으로 잡으면 세션의 종료시점을 잡을 수 있지만 계속 30분마다 로그인이 필요하다
        log.info("creationTime={}", new Date(session.getCreationTime()));
        //사용자가 서버에 최근에 요청한 시간(다음 링크나 버튼을 누른다거나)을 기준으로 30분 정도 유지해준다.
        //HttpSession의 사용 방식임
        log.info("lastAccessedTime={}", new Date(session.getLastAccessedTime()));
        log.info("isNew={}", session.isNew());

        return "세션 출력";
    }
}
