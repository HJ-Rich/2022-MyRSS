package com.rssmanager.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.rssmanager.auth.application.dto.LoginRequest;
import com.rssmanager.support.DatabaseCleaner;
import com.rssmanager.support.MockGithubExchange;
import io.restassured.RestAssured;
import io.restassured.internal.RestAssuredResponseImpl;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Collection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
@Import(MockGithubExchange.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {
    private static final String COOKIE_KEY_FOR_SESSION = "SESSION";

    @LocalServerPort
    private int port;
    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    public void tearDown() {
        databaseCleaner.clear();
    }

    protected String 로그인() {
        final var code = 생성요청("/api/auth/login", new LoginRequest("code"));
        return ((RestAssuredResponseImpl) code).getCookie(COOKIE_KEY_FOR_SESSION);
    }

    protected <T> ExtractableResponse<Response> 생성요청(final String url, final T body) {
        return RestAssured.given().log().all()
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(url)
                .then().log().all()
                .extract();
    }

    protected <T> ExtractableResponse<Response> 인증된_생성요청(final String url, final String cookie, final T body) {
        return RestAssured.given().log().all()
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .cookie(COOKIE_KEY_FOR_SESSION, cookie)
                .when()
                .post(url)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> 조회요청(final String url) {
        return RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> 인증된_조회요청(final String url, final String cookie) {
        return RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .cookie("SESSION", cookie)
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected <T> ExtractableResponse<Response> 수정요청(final String url, final T body) {
        return RestAssured.given().log().all()
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put(url)
                .then().log().all()
                .extract();
    }

    protected <T> ExtractableResponse<Response> 삭제요청(final String url) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(url)
                .then().log().all()
                .extract();
    }

    protected <T> Executable 리스트_데이터_검증(final Collection<?> list, final String fieldName, final T... expected) {
        return () -> assertThat(list).extracting(fieldName).containsExactly(expected);
    }

    protected <T> Executable 단일_데이터_검증(final T actual, final T expected) {
        return () -> assertThat(actual).isEqualTo(expected);
    }

    protected static Executable 단일_데이터_검증(final Object response, final String field, final Object expected) {
        return () -> assertThat(response).extracting(field).isEqualTo(expected);
    }

    protected static Executable 생성_응답(final ExtractableResponse<Response> response) {
        return () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    protected static Executable 정상_응답(final ExtractableResponse<Response> response) {
        return () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
