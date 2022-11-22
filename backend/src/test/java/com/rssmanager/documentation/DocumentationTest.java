package com.rssmanager.documentation;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import com.rssmanager.auth.application.AuthService;
import com.rssmanager.member.application.MemberService;
import com.rssmanager.rss.service.BookmarkService;
import com.rssmanager.rss.service.FeedService;
import com.rssmanager.rss.service.RssService;
import com.rssmanager.rss.service.SubscribeService;
import com.rssmanager.util.SessionManager;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest
public class DocumentationTest {
    @MockBean
    protected RssService rssService;
    @MockBean
    protected AuthService authService;
    @MockBean
    protected MemberService memberService;
    @MockBean
    protected FeedService feedService;
    @MockBean
    protected SessionManager sessionManager;
    @MockBean
    protected BookmarkService bookmarkService;
    @MockBean
    protected SubscribeService subscribeService;
    protected MockMvcRequestSpecification docsGiven;

    @BeforeEach
    void setDocsGiven(final WebApplicationContext webApplicationContext,
                      final RestDocumentationContextProvider restDocumentation) {
        docsGiven = RestAssuredMockMvc.given()
                .mockMvc(MockMvcBuilders.webAppContextSetup(webApplicationContext)
                        .apply(documentationConfiguration(restDocumentation)
                                .operationPreprocessors()
                                .withRequestDefaults(prettyPrint())
                                .withResponseDefaults(prettyPrint()))
                        .build()).log().all();
    }
}
