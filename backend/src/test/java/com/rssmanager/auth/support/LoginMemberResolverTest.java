package com.rssmanager.auth.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import com.rssmanager.exception.UnauthorizedException;
import com.rssmanager.member.domain.Member;
import com.rssmanager.support.ServiceTest;
import com.rssmanager.util.SessionManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

class LoginMemberResolverTest extends ServiceTest {
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private LoginMemberResolver loginMemberResolver;

    @DisplayName("LoginMemberResolver의 ResolveArgument메서드는")
    @Nested
    class ResolveArgument {
        private MethodParameter parameter = mock(MethodParameter.class);
        private ModelAndViewContainer mavContainer = mock(ModelAndViewContainer.class);
        private NativeWebRequest webRequest = mock(NativeWebRequest.class);
        private WebDataBinderFactory binderFactor = mock(WebDataBinderFactory.class);

        @DisplayName("로그인 된 요청일 경우 로그인한 회원 정보를 반환한다")
        @Test
        void return_member_info_if_logged_in() throws Exception {
            // given
            final var member = new Member(1L, 1L, "Richard", "richard.png");
            sessionManager.login(member);

            // when
            final var actual = loginMemberResolver.resolveArgument(
                    parameter, mavContainer, webRequest, binderFactor
            );

            // then
            assertThat(actual).usingRecursiveComparison().isEqualTo(member);
        }

        @DisplayName("로그인되지 않은 요청일 경우 예외가 발생한다")
        @Test
        void throws_exception_if_not_logged_in() {
            // given & when & then
            assertThatThrownBy(
                    () -> loginMemberResolver.resolveArgument(parameter, mavContainer, webRequest, binderFactor)
            ).isInstanceOf(UnauthorizedException.class);
        }
    }
}
